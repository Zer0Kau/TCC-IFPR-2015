package model;

import javax.faces.bean.RequestScoped;

@RequestScoped
public class UsuarioLog {

    private int idUsuarioLog;
    private String apelido;
    private String senha;
    private String tipoUsuario;

    public int getIdUsuarioLog() {
        return idUsuarioLog;
    }

    public void setIdUsuarioLog(int idUsuarioLog) {
        this.idUsuarioLog = idUsuarioLog;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }


}