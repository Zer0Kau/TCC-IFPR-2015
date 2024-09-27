package controle;

import DAO.StatusDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Status;

@SessionScoped
@ManagedBean
public class StatusControle {

    private List<Status> listas = new ArrayList<Status>();
    private Status status = new Status();
    private boolean salvar = false;


    @PostConstruct
    public void atualizarstatus() {
        try {
            listas = StatusDAO.getLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void preparaIncluirstatus() {
        salvar = true;
        status = new Status();

    }

    public List<Status> getListas() {
        return listas;
    }

    public void setListas(List<Status> listas) {
        this.listas = listas;
    }

    public Status getstatus() {
        return status; 
    }

    public void setstatus(Status status) {
        this.status = status;
    }

    public void preparaAlterarstatus() {
        salvar = false;
    }

    public void salvarstatus() {
        if (salvar) {
            try {
                StatusDAO.inserir(status);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                StatusDAO.alterar(status);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        atualizarstatus();
    }

    public void excluirstatus() {
        try {
            StatusDAO.excluir(status);
            atualizarstatus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
