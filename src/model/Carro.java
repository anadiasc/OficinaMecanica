package model;

public class Carro {
    private int id;
    private String placa;
    private int idProprietario;

    //metodo construtor
    public Carro(){

    }
    public Carro(int id, String placa, int idProprietario){
        this.id = id;
        this.placa = placa;
        this.idProprietario = idProprietario;
    }
    //getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public int getIdProprietario() {
        return idProprietario;
    }
    public void setIdProprietario(int idProprietario) {
        this.idProprietario = idProprietario;
    }
}
