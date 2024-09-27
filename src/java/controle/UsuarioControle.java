package controle;

import DAO.UsuarioDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Usuario;
import util.SessionContext;

@SessionScoped
@ManagedBean
public class UsuarioControle {

    private List<Usuario> usuarios = new ArrayList<Usuario>();
    private List<Usuario> usuarioatual = new ArrayList<Usuario>();
    private List<Usuario> pesquisa = new ArrayList<Usuario>();
    private String webInput;
    private Usuario usuario = new Usuario();
    private boolean salvar = false;
    private boolean renderQuery = false;
    private String nome;

    public List<Usuario> getUsuarioatual() {
        return usuarioatual;
    }

    public void setUsuarioatual(List<Usuario> usuarioatual) {
        this.usuarioatual = usuarioatual;
    }

    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void atualizaUsuario() {
        try {
            usuarios = UsuarioDAO.getLista();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void atualizarPesq(){
        try{
        usuarios = UsuarioDAO.pesqUsuarios(nome);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
    }

    public String getSelectedUsuario(Integer idUsuario) {
        try {
            usuario = UsuarioDAO.getUsuarioById(idUsuario.toString());
            System.out.println("passou aqui");
        } catch (SQLException q) {
            q.printStackTrace();
        }
        return "/faces/manutencaoFilmes.xhtml";
    }

    public void atualizaInfoUsu() throws SQLException{
        usuario = UsuarioDAO.getInfoUsu();
    }
    
    public void preparaIncluir() {
        salvar = true;
        usuario = new Usuario();
    }

    public void preparaAlterar() {
        salvar = false;
    }
    
    public String irpraCad() throws SQLException {
     return "cadastro.xhtml";   
    }
    
    public String cadastrar() throws SQLException {
            try{
        UsuarioDAO.inserirCadastro(usuario);
        return "cadastro_1.xhtml";
    }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login ou e-mail já cadastrado", "Error!"));
         System.out.println("Login ou e-mail já cadastrado");
                e.printStackTrace();
      }return "cadastro.xhtml";
    }
              
    
    public String alteradin() throws SQLException{
        UsuarioDAO.alterar(usuario);
        return "Usuario_1.xhtml";
    }
    
    public String inativar() throws SQLException{
        UsuarioDAO.inativar();
        return "loginErro_1.xhtml?faces-redirect=true";
    }
    
    
    public void salvar() {
        if (salvar) {
            try {
                UsuarioDAO.inserir(usuario);
            } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este Usuário esta vinculado a um ou mais filmes.", "Error!"));
            System.out.println("Este Usuário esta vinculado a um ou mais filmes.");
                e.printStackTrace();
            }
        } else {
            try {
                UsuarioDAO.alterar(usuario);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        atualizaUsuario();
    }

    public void excluir() throws SQLException {
        try {
            UsuarioDAO.excluir(usuario);
            atualizaUsuario();
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este Usuário esta vinculado a um ou mais filmes.", "Error!"));
            System.out.println("Este Usuário esta vinculado a um ou mais filmes.");
            e.printStackTrace();
        }
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(List<Usuario> pesquisa) {
        this.pesquisa = pesquisa;
    }

    public boolean isSalvar() {
        return salvar;
    }

    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }

    public String getWebInput() {
        return webInput;
    }

    public void setWebInput(String webInput) {
        this.webInput = webInput;
    }

    public boolean getRenderQuery() {
        return renderQuery;
    }

    public void setRenderQuery(boolean renderQuery) {
        this.renderQuery = renderQuery;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
    private Usuario usuarioLogado = null;

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            usuarioLogado = UsuarioDAO.getUsuarioLogado(usuario);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (usuarioLogado != null) {

            if (usuarioLogado.getTipoUsuario().equals("admin")) {
                SessionContext.getInstance().setAttribute("usuarioLogado", usuarioLogado);
                System.out.println("ADMIN");
                return "/faces/GerenciadorPaginas.xhtml?faces-redirect=true";
            } else if (usuarioLogado.getTipoUsuario().equals("comum")) {
                SessionContext.getInstance().setAttribute("usuarioLogado", usuarioLogado);
                System.out.println("COMUM");
                return "/faces/Inicio.xhtml?faces-redirect=true";
            } else {
                usuarioLogado = null;
                doLogout();
                System.out.println("ELSE");
                return "/faces/loginErro.xhtml?faces-redirect=true";
            }
        } else {
            usuarioLogado = null;
            usuario = new Usuario();
            doLogout();
            FacesMessage mensagem = new FacesMessage("Usuário/senha inválidos!");
            mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, mensagem);
            return "/faces/loginErro?faces-redirect=true";
        }
    }
    
    public String doLogout() {
        SessionContext.getInstance().encerrarSessao();
        return "login.xhtml?faces-redirect=true";
    }

}
