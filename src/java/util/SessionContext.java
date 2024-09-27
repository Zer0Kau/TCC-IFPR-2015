package util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.Usuario;


public class SessionContext {

    private static SessionContext instance;

    public static SessionContext getInstance() {
        if (instance == null) {
            instance = new SessionContext();
        }
        return instance;
    }

    public SessionContext() {

    }

    private ExternalContext currentExternalContext() {
        if (FacesContext.getCurrentInstance() == null) {
            throw new RuntimeException("O FacesContext não pode ser chamado fora de uma requisição HTTP");
        } else {
            return FacesContext.getCurrentInstance().getExternalContext();
        }
    }

    public Usuario getUsuarioLogado() {
        return (Usuario) getAttribute("usuarioLogado");
    }

    public void encerrarSessao() {
        currentExternalContext().invalidateSession();

    }

    public Object getAttribute(String apelido) {
        return currentExternalContext().getSessionMap().get(apelido);
    }

    public void setAttribute(String apelido, Object valor) {
        currentExternalContext().getSessionMap().put(apelido, valor);
    }

}
