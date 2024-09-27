package controle;

import DAO.UsuarioFilmeDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.UsuarioFilme;
import model.Usuario;
import model.Filme;
import util.SendMail;
import util.SessionContext;

@SessionScoped
@ManagedBean
public class UsuarioFilmeControle {

    private List<UsuarioFilme> listaUsuarioFilmes = new ArrayList<UsuarioFilme>();
     private List<UsuarioFilme> listaFilmesdoUsuario = new ArrayList<UsuarioFilme>();
     private List<UsuarioFilme> listaRelat = new ArrayList<UsuarioFilme>();     
    private UsuarioFilme usuarioFilme = new UsuarioFilme();
    private boolean salvar = false;
    private int filme;
    private int Usuario;
    private String titulo2;
    private int cont=0;

    public List<UsuarioFilme> getListaRelat() {
        return listaRelat;
    }
    
    public void setListaRelat(List<UsuarioFilme> listaRelat) {
        this.listaRelat = listaRelat;
    }
    
    

    public String getTitulo2() {
        return titulo2;
    }

    public void setTitulo2(String titulo2) {
        this.titulo2 = titulo2;
    }
    
    
    
    @PostConstruct
    public void atualizarListas(){
        atualizarFilme2();
        atualizarListaUsuarioFilmes();
    }
    
    public List<UsuarioFilme> getListaFilmesdoUsuario() {
        return listaFilmesdoUsuario;
    }

    public void setListaFilmesdoUsuario(List<UsuarioFilme> listaFilmesUsuario) {
        this.listaFilmesdoUsuario = listaFilmesUsuario;
    }
    
    public void contAdd(){
    cont++;    
    }
    
    public void atualizarFilme2() {
        try {
            listaFilmesdoUsuario = UsuarioFilmeDAO.getListaUSUARIOS();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void atualizaRelat() throws SQLException{
        cont = 0;
        listaRelat = UsuarioFilmeDAO.getListaRelat();
        System.out.println("passou");
    }
    
    public void atualizarPesqUSUF() {
        try{
            listaFilmesdoUsuario = UsuarioFilmeDAO.getPESQUSUARIOFILME(titulo2);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public int getFilme() {
        return filme;
    }

    public void setFilme(int Filme) {
        this.filme = Filme;
    }

    public int getUsuario() {
        return Usuario;
    }

    public void setUsuario(int Usuario) {
        this.Usuario = Usuario;
    }

    public void atualizarListaUsuarioFilmes() {
        try {
            listaUsuarioFilmes = UsuarioFilmeDAO.getListaUSUARIOS();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<UsuarioFilme> getListaUsuarioFilmes() {
        return listaUsuarioFilmes;
    }

    public void setListaUsuarioFilmes(List<UsuarioFilme> listaUsuarioFilmes) {
        this.listaUsuarioFilmes = listaUsuarioFilmes;
    }

    public UsuarioFilme getUsuarioFilme() {
        //antigamente era um objeto u
        return usuarioFilme;
    }

    public void setUsuarioFilme(UsuarioFilme usuarioFilme) {
        this.usuarioFilme = usuarioFilme;
    }

    public void excluirUsuarioFilme(int idLista) {
        try {
            UsuarioFilmeDAO.excluir(idLista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        atualizarFilme2();
    }

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

}
