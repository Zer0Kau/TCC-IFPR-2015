/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Noel
 */
public class CaracFilme {
    private int idCarac_Filme;
    private Caracteristica caracteristica;
    private Filme filme;
    private String aux;

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux;
    }

    public int getIdCarac_Filme() {
        return idCarac_Filme;
    }

    public void setIdCarac_Filme(int idCarac_Filme) {
        this.idCarac_Filme = idCarac_Filme;
    }

    
    public Caracteristica getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    
    
}
