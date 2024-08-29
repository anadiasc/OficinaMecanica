package DAO;

import bancoDeDados.DriverMySQL;
import model.Cliente;
import model.Mecanico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void inserirCliente(Cliente cliente){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("""
            INSERT INTO  clientes(nome, cpf, telefone)
            VALUES ( ?, ?, ?)
            """);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setLong(3, cliente.getTelefone());

            stmt.execute();
            System.out.println("Cliente cadastrado com sucesso.");
            stmt.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean delCliente(int id){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("""
            DELETE FROM clientes WHERE clientes.cpf = ?
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

    public List<Cliente> listarClientes(){
        Connection con = DriverMySQL.getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        List<Cliente> retorno = new ArrayList<>();

        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM clientes");

            while (rs.next()){
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getLong("telefone"));
                retorno.add(cliente);
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

    public boolean alterarCliente(Cliente cliente){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("""
            UPDATE clientes
            SET nome=?,cpf=?,telefone=?
            WHERE clientes.id = ?

            """);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setLong(3, cliente.getTelefone());
            stmt.setInt(4, cliente.getId());

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
