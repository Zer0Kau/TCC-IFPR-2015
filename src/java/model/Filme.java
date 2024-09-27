/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.Date;
/**
 *
 * @author Noel
 */
public class Filme {
    private int idFilme;
    private String titulo;
    private String descricao;
    private Date data_Lancamento;
    private Status Status;
    private double nota_IMDB;

    public int getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(int idFilme) {
        this.idFilme = idFilme;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData_Lancamento() {
        return data_Lancamento;
    }

    public void setData_Lancamento(Date data_Lancamento) {
        this.data_Lancamento = data_Lancamento;
    }

    

    public Status getStatus() {
        return Status;
    }

    public void setStatus(Status Status) {
        this.Status = Status;
    }

    


    public double getNota_IMDB() {
        return nota_IMDB;
    }

    public void setNota_IMDB(double nota_IMDB) {
        this.nota_IMDB = nota_IMDB;
    }


}
