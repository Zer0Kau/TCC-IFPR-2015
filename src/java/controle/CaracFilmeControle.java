package controle;

import DAO.CaracFilmeDAO;
import DAO.CaracteristicaDAO;
import DAO.FilmeDAO;
import DAO.TipoCaracDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.CaracFilme;
import model.Filme;
import model.Caracteristica;
import model.TipoCarac;

@SessionScoped
@ManagedBean
public class CaracFilmeControle {

    private List<CaracFilme> listas = new ArrayList<CaracFilme>();
    private List<CaracFilme> relat = new ArrayList<CaracFilme>();
    private CaracFilme caracFilme = new CaracFilme();
    private String descr_c;
    private int idTC;
    private Filme filminho = new Filme();
    private String titulo;
    private boolean salvar = false;
    private int idFilme;
    private int idCaracteristica;
    private int idTipoCaracteristica;
    private String descricao_s;
    private String descricao_c;
    private List<Caracteristica> cartec = new ArrayList<Caracteristica>();
    private List<TipoCarac> listaTC = new ArrayList<TipoCarac>();
    private List<CaracFilme> usandofilme = new ArrayList<CaracFilme>();
    
    

    public List<Caracteristica> getCartec() {
        return cartec;
    }

    public void setCartec(List<Caracteristica> cartec) {
        this.cartec = cartec;
    }
    
    public List<TipoCarac> getListaTC() {
        return listaTC;
    }

    public void setListaTC(List<TipoCarac> listaTC) {
        this.listaTC = listaTC;
    }

    public int getIdTipoCaracteristica() {
        return idTipoCaracteristica;
    }

    public void setIdTipoCaracteristica(int idTipoCaracteristica) {
        this.idTipoCaracteristica = idTipoCaracteristica;
    }

    public List<CaracFilme> getUsandofilme() {
        return usandofilme;
    }

    public void setUsandofilme(List<CaracFilme> usandofilme) {
        this.usandofilme = usandofilme;
    }

    public String getDescricao_s() {
        return descricao_s;
    }

    public void setDescricao_s(String descricao_s) {
        this.descricao_s = descricao_s;
    }

    
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    

        public int getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(int idFilme) {
        this.idFilme = idFilme;
    }
    
        public int getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(int idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }
    
    public void atualizarCaracFilme() {
        try {
            listas = CaracFilmeDAO.getLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void atualizarPesq() {
        try{
            listas = CaracFilmeDAO.getListaPesq(titulo);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void listaTIPOCARAC(){
        try{
          listaTC = TipoCaracDAO.getLista();
                  
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public String atualizarPaginaFilme (int idf) throws SQLException{
        filminho = CaracFilmeDAO.getInfoFilm(idf);
        usandofilme = CaracFilmeDAO.DescricaoFilmes(idf);
        return "PaginaFilme.xhtml";
    }
    
    public void pesquisar() {
        TipoCarac tiposcarc = new TipoCarac();
        tiposcarc.setIdTipoCarac(idTipoCaracteristica);
        tiposcarc.setDescricao_tc(descricao_s);
        System.out.println("Descricao: " + descricao_s);
        System.out.println("dsc: " + tiposcarc.getDescricao_tc());
        try {
            cartec = CaracteristicaDAO.getListaFiltredByTC(tiposcarc);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public String pesqRelat() throws SQLException{
           relat = CaracFilmeDAO.PesqRelat(idTC, descr_c);
           System.out.println("---------------------");
           System.out.println(idTC);
           System.out.println(descr_c);
           System.out.println("----------------------");
           return "relacarac_2.xhtml";
        }
    

    
    public void pesquisar(String cat) {
       try{ TipoCarac tiposcarc = new TipoCarac();
        tiposcarc.setIdTipoCarac(idTipoCaracteristica);
        cartec = CaracteristicaDAO.getListaFiltredByTC(tiposcarc);
    }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void preparaIncluirCaracFilme() {
        listaTIPOCARAC();
        salvar = true;
        caracFilme = new CaracFilme();

    }

    public List<CaracFilme> getListas() {
        return listas;
    }

    public void setListas(List<CaracFilme> listas) {
        this.listas = listas;
    }

    public Filme getFilminho() {
        return filminho;
    }

    public void setFilminho(Filme filminho) {
        this.filminho = filminho;
    }

    
    
    public CaracFilme getCaracFilme() {
        return caracFilme; 
    }

    public void setCaracFilme(CaracFilme caracFilme) {
        this.caracFilme = caracFilme;
    }

    public void preparaAlterarCaracFilme() {
        salvar = false;
    }

    public void salvarCaracFilme() throws SQLException {
        Filme filme = new Filme();
        filme.setIdFilme(idFilme);
        Caracteristica carac = new Caracteristica();
        carac.setIdCarac(idCaracteristica);
        
        caracFilme.setCaracteristica(carac);
        caracFilme.setFilme(filme);
        if (salvar) {
            try {
                CaracFilmeDAO.inserir(caracFilme);
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este filme já possui esta característica.", "Error!"));
                System.out.println("Este filme já possui esta característica.");
                e.printStackTrace();
            }
        } else {
            try {
                CaracFilmeDAO.alterar(caracFilme);
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este filme já possui esta característica.", "Error!"));
                System.out.println("Este filme já possui esta característica.");
                e.printStackTrace();
            }
        }
        atualizarCaracFilme();
    }

    public void excluirCaracFilme() {
        try {
            CaracFilmeDAO.excluir(caracFilme);
            atualizarCaracFilme();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getDescricao_c() {
        return descricao_c;
    }

    public void setDescricao_c(String descricao_c) {
        this.descricao_c = descricao_c;
    }

    public List<CaracFilme> getRelat() {
        return relat;
    }

    public void setRelat(List<CaracFilme> relat) {
        this.relat = relat;
    }

    public String getDescr_c() {
        return descr_c;
    }

    public void setDescr_c(String descr_c) {
        this.descr_c = descr_c;
    }

    public int getIdTC() {
        return idTC;
    }

    public void setIdTC(int idTC) {
        this.idTC = idTC;
    }

}
