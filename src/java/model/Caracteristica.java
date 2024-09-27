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
public class Caracteristica {
    private int idCarac;
    private String descricao_c;
    private TipoCarac TipoCarac;

    public TipoCarac getTipoCarac() {
        return TipoCarac;
    }

    public void setTipoCarac(TipoCarac TipoCarac) {
        this.TipoCarac = TipoCarac;
    }
    
    public int getIdCarac() {
        return idCarac;
    }

    public void setIdCarac(int idCarac) {
        this.idCarac = idCarac;
    }

    public String getDescricao_c() {
        return descricao_c;
    }

    public void setDescricao_c(String descricao_c) {
        this.descricao_c = descricao_c;
    }
    
    
}
