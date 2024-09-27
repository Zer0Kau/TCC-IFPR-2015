package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import util.Conexao;
import util.SessionContext;

public class UsuarioDAO {

    public static void inserir(Usuario usuario)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "insert into usuario"
                + "( nome, email, apelido, senha, tipoUsuario"
                + ") values (?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getApelido());
        stmt.setString(4, usuario.getSenha());
        stmt.setString(5, usuario.getTipoUsuario().toLowerCase());
        stmt.execute();
        stmt.close();
        con.close();
    }
    
    public static void inserirCadastro(Usuario usuario)
            throws SQLException {
        String teta = new String();
        teta="comum";
        Connection con = Conexao.getConnection();
        String sql = "insert into usuario"
                + "( nome, email, apelido, senha, tipoUsuario"
                + ") values (?,?,?,md5(?),?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getApelido());
        stmt.setString(4, usuario.getSenha());
        stmt.setString(5, teta);
        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void excluir(Usuario usuario)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "delete from usuario where idUsuario = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, usuario.getIdUsuario());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    
    public static void inativar() throws SQLException{
        Connection con = Conexao.getConnection();
        String sql="UPDATE `tccnoel`.`usuario` SET `tipoUsuario`='inativo' WHERE  `idUsuario`=?;";
        PreparedStatement stmt = con.prepareStatement(sql);      
        stmt.setInt(1, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void alterar(Usuario usuario)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "update usuario set nome=?,email=?,apelido=?, senha= md5(?), tipoUsuario=?"
                + "where idUsuario=?";
        PreparedStatement stmt = con.prepareStatement(sql);      
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getApelido());
        stmt.setString(4, usuario.getSenha());
        stmt.setString(5, usuario.getTipoUsuario().toLowerCase());
        stmt.setInt(6, usuario.getIdUsuario());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<Usuario> getLista() throws SQLException {
        List<Usuario> lista = new ArrayList<Usuario>();
        Connection con = Conexao.getConnection();
        String sql = "select * from usuario WHERE tipoUsuario LIKE CONCAT('comum') or tipoUsuario LIKE CONCAT('inativo')   order by nome";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setApelido(rs.getString("apelido"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setTipoUsuario(rs.getString("tipoUsuario"));
                   
            lista.add(usuario);
        }
        stmt.close();
        rs.close();
        con.close();
        return lista;
    }
    public static List<Usuario> pesqUsuarios(String input) throws SQLException {
        List<Usuario> listaPesq = new ArrayList<Usuario>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM usuario WHERE (tipoUsuario='comum' or tipoUsuario='inativo') and nome LIKE CONCAT('%',?,'%') or (tipoUsuario='comum' or tipoUsuario='inativo') and email LIKE CONCAT('%',?,'%') or (tipoUsuario='comum' or tipoUsuario='inativo') and apelido LIKE CONCAT('%',?,'%') or (tipoUsuario='comum' or tipoUsuario='inativo') and tipoUsuario LIKE CONCAT('%',?,'%') ORDER BY nome";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, input.toUpperCase());
        stmt.setString(2, input.toUpperCase());
        stmt.setString(3, input.toUpperCase());
        stmt.setString(4, input.toUpperCase());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setApelido(rs.getString("apelido"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setTipoUsuario(rs.getString("tipoUsuario"));
                   
            listaPesq.add(usuario);
        }
        return listaPesq;
    }
    
    public static Usuario getInfoUsu() throws SQLException {
        Usuario usuario = new Usuario();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM usuario u WHERE u.idUsuario IN(?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {                        
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));   
            usuario.setApelido(rs.getString("apelido"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setTipoUsuario(rs.getString("tipoUsuario").toLowerCase());
        }
        System.out.println("passou aqui DAO");
        System.out.println(usuario.getNome());
        return usuario;
    }
    
    public static Usuario getUsuarioById(String idUsuario) throws SQLException {
        Usuario usuario = new Usuario();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM usuario u WHERE u.idUsuario IN(?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, idUsuario);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {                        
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome").toLowerCase());
            usuario.setEmail(rs.getString("email").toLowerCase());   
            usuario.setApelido(rs.getString("apelido").toLowerCase());
            usuario.setSenha(rs.getString("senha").toLowerCase());
            usuario.setTipoUsuario(rs.getString("tipoUsuario").toLowerCase());
        }
        System.out.println("passou aqui DAO");
        System.out.println(usuario.getNome());
        return usuario;
    }
    public static Usuario getUsuarioLogado(Usuario u) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = 
       "select * from usuario u where u.apelido = ? and u.senha = md5(?) ";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, u.getApelido());
        stmt.setString(2, u.getSenha());
        
        ResultSet rs = stmt.executeQuery();

        Usuario usuariolog = null;
        if (rs.next()) {
            usuariolog = new Usuario();
            usuariolog.setIdUsuario(rs.getInt("idUsuario"));
            usuariolog.setApelido(rs.getString("apelido"));
            usuariolog.setSenha(rs.getString("senha"));
            usuariolog.setTipoUsuario(rs.getString("tipoUsuario"));
        } else {
            usuariolog = null;
        }
        rs.close();
        stmt.close();
        con.close();
        return usuariolog;
    }
    
    public static void main(String[] args) {

        try {
            List<Usuario> lista = getLista();

            for (Usuario uu : lista) {
                System.out.println("CÓDIGO....: " + uu.getIdUsuario());
                System.out.println("NOME......: " + uu.getNome());
                System.out.println("EMAIL......: " + uu.getEmail());
                System.out.println("APELIDO......: " + uu.getApelido());
                System.out.println("SENHA......: " + uu.getSenha());
                System.out.println("TIPO USUARIO......: " + uu.getTipoUsuario());
                System.out.println("-----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

    }
}