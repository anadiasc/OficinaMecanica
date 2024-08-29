package DAO;

import bancoDeDados.DriverMySQL;
import model.Servicos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicosDAO {

    public void inserirServicos(Servicos servico){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("""
            INSERT INTO  (nome, valor)
            VALUES ( ?, ?)
            """);
            stmt.setString(1, servico.getName());
            stmt.setDouble(2, servico.getPreco());

            stmt.execute();
            System.out.println("Serviço cadastrado com sucesso.");
            stmt.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean delServico(int id){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("""
            DELETE FROM servicos WHERE servicos.id = ?
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

    public List<Servicos> listarServicos(){
        Connection con = DriverMySQL.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        List<Servicos> retorno = new ArrayList<>();

        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM servicos");

            while (rs.next()){
                Servicos servicos = new Servicos();

                servicos.setId(rs.getInt("id"));
                servicos.setName(rs.getString("nome"));
                servicos.setPreco(rs.getDouble("valor"));

                retorno.add(servicos);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retorno;
    }

    public boolean alterarServico(Servicos servico){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("""
            UPDATE sevicos
            SET nome=?,valor=?
            WHERE servicos.id = ?

            """);

            stmt.setString(1, servico.getName());
            stmt.setDouble(2, servico.getPreco());
            stmt.setInt(3,servico.getId());

            stmt.execute();
            stmt.close();
            con.close();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
