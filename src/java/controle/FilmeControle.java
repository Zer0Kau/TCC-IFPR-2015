package controle;

import DAO.FilmeDAO;
import DAO.UsuarioFilmeDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Filme;
import model.UsuarioFilme;
import model.Status;

@SessionScoped
@ManagedBean
public class FilmeControle {
    
    private List<Filme> listausu = new ArrayList<Filme>(); 
    private List<Filme> listas = new ArrayList<Filme>();
    private List<Filme> escolhalist = new ArrayList<Filme>();
    private List<Filme> pesqlist = new ArrayList<Filme>();
    private Filme filme = new Filme();
    private boolean salvar = false;
    private int idStatus;
    private String titulo;
    private String titulo1;

    public String getTitulo1() {
        return titulo1;
    }

    public void setTitulo1(String titulo1) {
        this.titulo1 = titulo1;
    }
    
    

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Filme> getEscolhalist() {
        return escolhalist;
    }

    public void setEscolhalist(List<Filme> escolhalist) {
        this.escolhalist = escolhalist;
    }

    public boolean isSalvar() {
        return salvar;
    }

    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public void atualizarListas(){
        atualizarFilmes();
        atualizarFilmeEscolha();
    }
    
    public void atualizarFilmes() {
        try {
            listas = FilmeDAO.getLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void atualizarPesq() {
        try{
            listas = FilmeDAO.pesqFilmes(titulo);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void atualizarPesqe() {
        try{
            escolhalist = FilmeDAO.pesqFilmeEscolha(titulo1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     
     public void atualizarFilmeEscolha() {
        try {
            escolhalist = FilmeDAO.getListaESCOLHA();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     
        public void inserirUSUFILME(int idFilme) {
       try{
           UsuarioFilmeDAO.inserir(idFilme);
       }catch(SQLException ex) {
            ex.printStackTrace();
        }
          atualizarFilmeEscolha();
       }
     
    public List<Filme> getListaEscolha() {
        return escolhalist;
    }

    public void setListaEscolha(List<Filme> escolhalist) {
        this.escolhalist = escolhalist;
    } 

     
     public List<Filme> getListausu() {
        return listausu;
    }

    public void setListausu(List<Filme> listausu) {
        this.listausu = listausu;
    } 

    public void preparaIncluirFilmes() {
        salvar = true;
        filme = new Filme();

    }

   
    
    public List<Filme> getListas() {
        return listas;
    }

    public void setListas(List<Filme> listas) {
        this.listas = listas;
    }

    public Filme getFilme() {
        return filme; 
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public void preparaAlterarFilme() {
        salvar = false;
    }
    

    public void salvarFilmes() throws SQLException {
        Status status = new Status();
        status.setIdStatus(idStatus);

        filme.setStatus(status);
        if (salvar) {
            try {
                FilmeDAO.inserir(filme, idStatus);
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Já existe um filme com este titulo.", "Error!"));
                System.out.println("Já existe um filme com este titulo.");
                e.printStackTrace();
            }
        } else {
            try {
                FilmeDAO.alterar(filme, idStatus);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        atualizarFilmes();
    }
     
    public void excluirFilmes() throws SQLException {
        try {
             FilmeDAO.excluir(filme);
            atualizarFilmes();
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este filme esta vinculado a um ou mais usuários.", "Error!"));
            System.out.println("Este filme esta vinculado a um ou mais Usuários/Características.");
            e.printStackTrace();
        }
    }

}
