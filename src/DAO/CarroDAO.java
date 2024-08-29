package DAO;

import bancoDeDados.DriverMySQL;
import model.Carro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarroDAO {
    public void inserirCarro(Carro carro){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("""
            INSERT INTO  clientes(placa, idProprietario)
            VALUES ( ?, ?)
            """);
            stmt.setString(1, carro.getPlaca());
            stmt.setInt(2, carro.getIdProprietario());

            stmt.execute();
            System.out.println("Carro cadastrado com sucesso.");
            stmt.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean delCarro(int id){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("""
            DELETE FROM carros WHERE carros.id = ?
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

    public List<Carro> listarCarros(){
        Connection con = DriverMySQL.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        List<Carro> retorno = new ArrayList<>();

        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM carros");

            while (rs.next()){
                Carro carro = new Carro();

                carro.setId(rs.getInt("id"));
                carro.setPlaca(rs.getString("placa"));
                carro.setIdProprietario(rs.getInt("id_proprietario"));

                retorno.add(carro);
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
        return retorno;
    }

    public Carro buscarPorPlaca(String placa){
        Connection con = DriverMySQL.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Carro retorno = new Carro();

        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM carros where placa = '"+placa+"'");

            while (rs.next()){
                Carro carro = new Carro();

                carro.setId(rs.getInt("id"));
                carro.setPlaca(rs.getString("placa"));
                carro.setIdProprietario(rs.getInt("id_proprietario"));

                retorno = carro;
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
        return retorno;
    }

    public boolean alterarCarro(Carro carro){
        Connection con = DriverMySQL.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("""
            UPDATE carros
            SET placa=?,id_proprietario =?
            WHERE carros.id = ?

            """);

            stmt.setString(1, carro.getPlaca());
            stmt.setInt(2, carro.getIdProprietario());
            stmt.setInt(3, carro.getId());

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
