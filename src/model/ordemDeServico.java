package model;

import java.security.Timestamp;
import java.util.List;

public class ordemDeServico {
    private int id;
    private List<Integer> idServicos;
    private int idMecanico;
    private int idCarro;
    private double valorTotal;
    private Timestamp dataHoraCriacao;

    //metodo construtor
    public ordemDeServico(){

    }
    public ordemDeServico(int id, List<Integer> idServicos, int idMecanico, int idCarro, double valorTotal) {
        this.id = id;
        this.idServicos = idServicos;
        this.idMecanico = idMecanico;
        this.idCarro = idCarro;
        this.valorTotal = valorTotal;
    }
    //getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<Integer> getIdServicos() {
        return idServicos;
    }
    public void setIdServicos(List<Integer> idServicos) {
        this.idServicos = idServicos;
    }
    public int getIdMecanico() {
        return idMecanico;
    }
    public void setIdMecanico(int idMecanico) {
        this.idMecanico = idMecanico;
    }
    public int getIdCarro() {
        return idCarro;
    }
    public void setIdCarro(int idCarro) {
        this.idCarro = idCarro;
    }
    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double preco) {
        this.valorTotal = preco;
    }
    public Timestamp getDataHoraCriacao() {
        return dataHoraCriacao;
    }
    public void setDataHoraCriacao(Timestamp dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }
}
