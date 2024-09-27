package controle;
import DAO.UsuarioLogDAO;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.UsuarioLog;
import util.SessionContext;

@ManagedBean
@SessionScoped
public class UsuarioLogControle {

    private UsuarioLog usuariolog = new UsuarioLog();

    public UsuarioLog getUsuarioLog() {
        return usuariolog;
    }

    public void setUsuarioLog(UsuarioLog usuariolog) {
        this.usuariolog = usuariolog;
    }

    public UsuarioLog getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(UsuarioLog usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
    private UsuarioLog usuarioLogado = null;

  public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            usuarioLogado = UsuarioLogDAO.getUsuarioLogado(usuariolog);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (usuarioLogado != null) {

            if (usuarioLogado.getTipoUsuario().equals("admin")) {
                SessionContext.getInstance().setAttribute("usuarioLogado", usuarioLogado);
                return "/faces/startXXX?faces-redirect=true";
            } else if (usuarioLogado.getTipoUsuario().equals("comum")) {
                SessionContext.getInstance().setAttribute("usuarioLogado", usuarioLogado);
                return "";
            } else {
                usuarioLogado = null;
                doLogout();
                return "/faces/login?faces-redirect=true";
            }
        } else {
            usuarioLogado = null;
            usuariolog = new UsuarioLog();
            doLogout();
            FacesMessage mensagem = new FacesMessage("Usuário/senha inválidos!");
            mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, mensagem);
//            return "/faces/inicial/loginAdm?faces-redirect=true";
            return null;
        }
    }



    public String doLogout() {
//        SessionContext.getInstance().encerrarSessao();
        return "************LINK DO LOGIN*******?faces-redirect=true";
    }


    public void sair() {
        String a = doLogout();
    }

}
