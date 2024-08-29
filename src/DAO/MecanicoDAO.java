package DAO;

import bancoDeDados.DriverMySQL;
import model.Mecanico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MecanicoDAO {
    //funcao inserir mecanicos no BD
    public void inserirMecanico(Mecanico mecanico){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("""
            INSERT INTO  mecanicos(nome, cpf, telefone)
            VALUES ( ?, ?, ?)
            """);
            stmt.setString(1, mecanico.getNome());
            stmt.setString(2, mecanico.getCpf());
            stmt.setLong(3, mecanico.getTelefone());

            stmt.execute();
            System.out.println("Funcionário cadastrado com sucesso.");
            stmt.close();
            con.close();

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //funcao deletar registros no BD
    public boolean delMecanico(int id){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("""
            DELETE FROM mecanicos WHERE mecanico.id = ?
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

    //listar todos os mecanicos do BD
    public List<Mecanico> listarMecanicos(){
        Connection con = DriverMySQL.getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        List<Mecanico> retorno = new ArrayList<>();

        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM mecanicos");

            while (rs.next()){
                Mecanico mecanico = new Mecanico();

                mecanico.setId(rs.getInt("id"));
                mecanico.setNome(rs.getString("nome"));
                mecanico.setCpf(rs.getString("cpf"));
                mecanico.setTelefone(rs.getLong("telefone"));

                retorno.add(mecanico);
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

    //atualizar mecanico
    public boolean alterarMecanico(Mecanico mecanico){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("""
            UPDATE mecanicos
            SET nome=?,cpf=?,telefone=?
            WHERE mecanicos.id = ?

            """);

            stmt.setString(1, mecanico.getNome());
            stmt.setString(2, mecanico.getCpf());
            stmt.setLong(3, mecanico.getTelefone());
            stmt.setInt(4, mecanico.getId());

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
