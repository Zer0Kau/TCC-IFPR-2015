
package model;

public class UsuarioFilme {
    private int idUsuarioFilme;
    private int contadorEmail;
    private Usuario usuario;
    private Filme filme;

    public int getContadorEmail() {
        return contadorEmail;
    }

    public void setContadorEmail(int contadorEmail) {
        this.contadorEmail = contadorEmail;
    }

    

    
    public int getIdUsuarioFilme() {
        return idUsuarioFilme;
    }

    public void setIdUsuarioFilme(int idUsuarioFilme) {
        this.idUsuarioFilme = idUsuarioFilme;
    }

    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }
    
    
}
