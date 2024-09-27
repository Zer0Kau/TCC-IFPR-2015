package controle;

import DAO.CaracteristicaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Caracteristica;
import model.TipoCarac;

@SessionScoped
@ManagedBean
public class CaracteristicaControle {

    private List<Caracteristica> listas = new ArrayList<Caracteristica>();
    private Caracteristica carac = new Caracteristica();
    private String descricao_c;
    private boolean salvar = false;
    private int TipoCarac;

    public String getDescricao_c() {
        return descricao_c;
    }

    public void setDescricao_c(String descricao_c) {
        this.descricao_c = descricao_c;
    }

    public void atualizaStatus() throws SQLException{
        CaracteristicaDAO.emestparajalan();
        CaracteristicaDAO.emprodparaemest();
        CaracteristicaDAO.tudojalan();
    }
    
    public int getTipoCarac() {
        return TipoCarac;
    }

    public void setTipoCarac(int TipoCarac) {
        this.TipoCarac = TipoCarac;
    }

        public void atualizarPesq() {
        try{
            listas = CaracteristicaDAO.getListaPesq(descricao_c);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void atualizarCaracteristica() {
        try {
            listas = CaracteristicaDAO.getLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void preparaIncluirCaracteristica() {
        salvar = true;
        carac = new Caracteristica();

    }

    public List<Caracteristica> getListas() {
        return listas;
    }

    public void setListas(List<Caracteristica> listas) {
        this.listas = listas;
    }

    public Caracteristica getCaracteristica() {
        return carac; 
    }

    public void setCaracteristica(Caracteristica carac) {
        this.carac = carac;
    }

    public void preparaAlterarCaracteristica() {
        salvar = false;
    }

    public void salvarCaracteristica() throws SQLException {
        TipoCarac tcarac = new TipoCarac();
        tcarac.setIdTipoCarac(TipoCarac);

        carac.setTipoCarac(tcarac);
        if (salvar) {
            try {
                CaracteristicaDAO.inserir(carac, TipoCarac);
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A característica informada já possui este tipo.", "Error!"));
                System.out.println("A característica informada já possui este tipo");
                e.printStackTrace();
            }
        } else {
            try {
                CaracteristicaDAO.alterar(carac, TipoCarac);
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A descrição já existe.", "Error!"));
                System.out.println("A descrição já existe.");
                e.printStackTrace();
            }
        }
        atualizarCaracteristica();
    }

    public void excluirCaracteristica() throws SQLException {
        try {
            CaracteristicaDAO.excluir(carac);
            atualizarCaracteristica();
        }catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Esta característica está vinculada a um filme.", "Error!"));
            System.out.println("Esta característica está vinculada a um filme.");
            e.printStackTrace();
        }
    }

}
