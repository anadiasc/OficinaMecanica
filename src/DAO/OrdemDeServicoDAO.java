package DAO;

import bancoDeDados.DriverMySQL;
import model.Servicos;
import model.ordemDeServico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdemDeServicoDAO {
    public void inserirOrdem(ordemDeServico ordem) {
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Inserir a nova ordem de serviço
            stmt = con.prepareStatement("""
            INSERT INTO ordens_de_servico (id_carro, id_mecanico, valor_total)
            VALUES (?, ?, ?)
        """, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, ordem.getIdCarro());
            stmt.setInt(2, ordem.getIdMecanico());
            stmt.setDouble(3, ordem.getValorTotal());

            stmt.executeUpdate();

            // Obter o ID gerado da ordem de serviço
            rs = stmt.getGeneratedKeys();
            int ordemId = 0;
            if (rs.next()) {
                ordemId = rs.getInt(1);
            }

            // Inserir serviços adquiridos
            if (ordemId > 0 && ordem.getIdServicos() != null) {
                for (Integer idServico : ordem.getIdServicos()) {
                    PreparedStatement stmtServicos = con.prepareStatement("""
                    INSERT INTO servicos_adquiridos (id_ordem_servico, id_servico)
                    VALUES (?, ?)
                """);
                    stmtServicos.setInt(1, ordemId);
                    stmtServicos.setInt(2, idServico);
                    stmtServicos.executeUpdate();
                    stmtServicos.close();
                }
            }

            System.out.println("Ordem de Serviço cadastrada com sucesso.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean delOrdem(int id) {
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("""
            DELETE FROM ordens_de_servico  
            WHERE ordens_de_servico.id = ?
            """);

            stmt.setInt(1, id);

            stmt.execute();
            stmt.close();
            con.close();

            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void listarOrdem(){
        Connection con = DriverMySQL.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        List<Integer> retorno = new ArrayList<>();

        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM ordens_de_servico");

            while (rs.next()){
                Integer id = rs.getInt("id");

                retorno.add(id);
            }
            for (Integer id : retorno){
                listarPorId(id);
            }
        }catch (SQLException e){
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void listarPorId(int id) {
        Connection con = DriverMySQL.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String query = """
                SELECT ordens_de_servico.id, c2.id AS id_cliente, c2.nome AS nome_cliente, c2.cpf, c.placa, 
                       m.id AS id_mecanico, m.nome AS nome_mecanico, ordens_de_servico.valor_total, ordens_de_servico.data_hora_atualizacao
                FROM ordens_de_servico
                JOIN carros c ON c.id = ordens_de_servico.id_carro
                JOIN clientes c2 ON c2.id = c.id_proprietario
                JOIN mecanicos m ON ordens_de_servico.id_mecanico = m.id
                WHERE ordens_de_servico.id = ?;
        """;

            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int idOrdem = rs.getInt("id");
                int idCliente = rs.getInt("id_cliente");
                String nomeCliente = rs.getString("nome_cliente");
                String cpfCliente = rs.getString("cpf");
                String placa = rs.getString("placa");
                int idMecanico = rs.getInt("id_mecanico");
                String nomeMecanico = rs.getString("nome_mecanico");
                double valorTotal = rs.getDouble("valor_total");
                Timestamp dataHoraCriacao = rs.getTimestamp("data_hora_atualizacao");

                // Buscar os serviços adquiridos
                List<Servicos> servicos = new ArrayList<>();
                String servicoQuery = """
                    SELECT s.nome, s.valor
                    FROM servicos_adquiridos sa
                    JOIN servicos s ON sa.id_servico = s.id
                    WHERE sa.id_ordem_servico = ?;
            """;

                PreparedStatement pstmtServicos = con.prepareStatement(servicoQuery);
                pstmtServicos.setInt(1, idOrdem);
                ResultSet rsServicos = pstmtServicos.executeQuery();
                while (rsServicos.next()) {
                    Servicos servico = new Servicos();
                    servico.setName(rsServicos.getString("nome"));
                    servico.setPreco(rsServicos.getDouble("valor"));

                    servicos.add(servico);
                }
                rsServicos.close();
                pstmtServicos.close();

                System.out.println("=========================================");
                System.out.println("              ORDEM DE SERVIÇO              ");
                System.out.println("=========================================");
                System.out.printf("ID: %d\n", idOrdem);
                System.out.printf("Data e Hora: %s\n", dataHoraCriacao);
                System.out.println("-----------------------------------------");
                System.out.printf("Cliente: %s\n", nomeCliente);
                System.out.printf("CPF: %s\n", cpfCliente);
                System.out.printf("Placa do Carro: %s\n", placa);
                System.out.println("-----------------------------------------");
                System.out.printf("Mecânico: %s (ID: %d)\n", nomeMecanico, idMecanico);
                System.out.println("-----------------------------------------");
                System.out.println("Serviços Adquiridos:");
                for (Servicos servico : servicos) {
                    System.out.printf("- %s - %.2f\n", servico.getName(), servico.getPreco());
                }
                System.out.println("-----------------------------------------");
                System.out.printf("Valor Total: %.2f\n", valorTotal);
                System.out.println("\n");
            } else {
                System.out.println("Ordem de Serviço não encontrada.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean atualizarOrdem(ordemDeServico ordem) {
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            // Atualizar dados básicos da ordem de serviço
            stmt = con.prepareStatement("""
            UPDATE ordens_de_servico
            SET id_carro = ?, id_mecanico = ?, valor_total = ?
            WHERE id = ?
        """);
            stmt.setInt(1, ordem.getIdCarro());
            stmt.setInt(2, ordem.getIdMecanico());
            stmt.setDouble(3, ordem.getValorTotal());
            stmt.setInt(4, ordem.getId());
            stmt.executeUpdate();

            // Atualizar serviços adquiridos
            // Remover serviços existentes
            stmt = con.prepareStatement("DELETE FROM servicos_adquiridos WHERE id_ordem_servico = ?");
            stmt.setInt(1, ordem.getId());
            stmt.executeUpdate();

            // Inserir novos serviços adquiridos
            if (ordem.getIdServicos() != null) {
                for (Integer idServico : ordem.getIdServicos()) {
                    PreparedStatement stmtServicos = con.prepareStatement("""
                    INSERT INTO servicos_adquiridos (id_ordem_servico, id_servico)
                    VALUES (?, ?)
                """);
                    stmtServicos.setInt(1, ordem.getId());
                    stmtServicos.setInt(2, idServico);
                    stmtServicos.executeUpdate();
                    stmtServicos.close();
                }
            }

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}