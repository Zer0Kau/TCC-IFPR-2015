package controle;

import DAO.TipoCaracDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.TipoCarac;

@SessionScoped
@ManagedBean
public class TipoCaracControle {

    private List<TipoCarac> listas = new ArrayList<TipoCarac>();
    private String descricao_tc;
    private TipoCarac tcarac = new TipoCarac();
    private boolean salvar = false;

    public String getDescricao_tc() {
        return descricao_tc;
    }

    public void setDescricao_tc(String descricao_tc) {
        this.descricao_tc = descricao_tc;
    }
    
    public void atualizartipoCarac() {
        try {
            listas = TipoCaracDAO.getLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void preparaIncluirtipoCarac() {
        salvar = true;
        tcarac = new TipoCarac();

    }

    public List<TipoCarac> getListas() {
        return listas;
    }

    public void setListas(List<TipoCarac> listas) {
        this.listas = listas;
    }

    public TipoCarac getTipoCarac() {
        return tcarac; 
    }

    public void setTipoCarac(TipoCarac tcarac) {
        this.tcarac = tcarac;
    }

    public void preparaAlterartipoCarac() {
        salvar = false;
    }

    public void salvartipoCarac() throws SQLException {
        if (salvar) {
            try {
                TipoCaracDAO.inserir(tcarac);
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A descrição informada já existe.", "Error!"));
                System.out.println("A descrição informada já existe.");
                e.printStackTrace();
            }
        } else {
            try {
                TipoCaracDAO.alterar(tcarac);
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A descrição informada já existe.", "Error!"));
                System.out.println("A descrição informada já existe.");
                e.printStackTrace();
            }
        }
        atualizartipoCarac();
    }

        public void atualizarPesq() {
        try{
            listas = TipoCaracDAO.getListaPesq(descricao_tc);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void excluirtipoCarac() throws SQLException {
        try {
            TipoCaracDAO.excluir(tcarac);
            atualizartipoCarac();
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O tipo da característica esta vinculado a uma característica.", "Error!"));
                System.out.println("O tipo da característica esta vinculado a uma característica.");
                e.printStackTrace();
        }
    }

}
