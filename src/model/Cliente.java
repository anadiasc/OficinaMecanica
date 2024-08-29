package model;

public class Cliente extends Pessoa{
    public Cliente(){
        super();
    }
    public Cliente(int id, String nome, String cpf, long telefone) {
        super(id, nome, cpf, telefone);
    }
}
