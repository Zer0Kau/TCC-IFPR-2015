package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import model.Filme;
import model.UsuarioFilme;
import model.Status;
import util.Conexao;
import util.SessionContext;

public class UsuarioFilmeDAO {

    public static void inserir(int IdFilme) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "insert into usuariofilme (Usuario_idUsuario, Filme_idFilme) values (?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        stmt.setInt(2, IdFilme);

        stmt.execute();
        stmt.close();
        con.close();
    }

    public static List<UsuarioFilme> getListaUSUARIOS() throws SQLException {
        List<UsuarioFilme> usuarioFilme = new ArrayList<UsuarioFilme>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT\n"
                + "f.*,	u.*,     lf.*, s.*\n"
                + "FROM	\n"
                + "usuariofilme lf,	usuario u,	filme f, status s\n"
                + "WHERE\n"
                + "lf.Usuario_idUsuario = u.idUsuario and	lf.Filme_idFilme = f.idFilme and f.status_idstatus=s.idstatus AND	(status_idstatus = 1 OR status_idstatus = 2) AND	u.idUsuario = ? order by f.status_idstatus";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        ResultSet rs = stmt.executeQuery();
        System.out.println(stmt);
        while (rs.next()) {
            UsuarioFilme uf = new UsuarioFilme();

            Filme filme = new Filme();

            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setApelido(rs.getString("apelido"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setTipoUsuario(rs.getString("tipoUsuario"));

            Status status = new Status();

            status.setIdStatus(rs.getInt("idstatus"));
            status.setDescricao_s(rs.getString("descricao_s"));

            filme.setStatus(status);
            uf.setContadorEmail(rs.getInt("contadorEmail"));
            uf.setFilme(filme);
            uf.setUsuario(usuario);
            uf.setIdUsuarioFilme(rs.getInt("idUsuarioFilme"));
            usuarioFilme.add(uf);
        }
        stmt.close();
        rs.close();
        con.close();
        return usuarioFilme;
    }

    public static List<UsuarioFilme> getPESQUSUARIOFILME(String input) throws SQLException {
        List<UsuarioFilme> usuarioFilmePesq = new ArrayList<UsuarioFilme>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT\n"
                + "f.*,	u.*,     lf.*, s.*\n"
                + "FROM	\n"
                + "usuariofilme lf,	usuario u,	filme f, status s\n"
                + "WHERE\n"
                + "lf.Usuario_idUsuario = u.idUsuario and	lf.Filme_idFilme = f.idFilme and f.status_idstatus=s.idstatus AND	(status_idstatus = 1 OR status_idstatus = 2) AND	u.idUsuario = ?  and f.titulo like concat ('%',?,'%') or lf.Usuario_idUsuario = u.idUsuario and	lf.Filme_idFilme = f.idFilme and f.status_idstatus=s.idstatus AND	(status_idstatus = 1 OR status_idstatus = 2) AND	u.idUsuario = ?  and descricao_s like concat ('%',?,'%') order by f.status_idstatus";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        stmt.setString(2, input.toUpperCase());
        stmt.setInt(3, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        stmt.setString(4, input.toUpperCase());
        ResultSet rs = stmt.executeQuery();
        System.out.println(stmt);
        while (rs.next()) {
            UsuarioFilme uf = new UsuarioFilme();

            Filme filme = new Filme();

            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setApelido(rs.getString("apelido"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setTipoUsuario(rs.getString("tipoUsuario"));

            Status status = new Status();

            status.setIdStatus(rs.getInt("idstatus"));
            status.setDescricao_s(rs.getString("descricao_s"));

            filme.setStatus(status);
            uf.setContadorEmail(rs.getInt("contadorEmail"));
            uf.setFilme(filme);
            uf.setUsuario(usuario);
            uf.setIdUsuarioFilme(rs.getInt("idUsuarioFilme"));
            usuarioFilmePesq.add(uf);
        }
        stmt.close();
        rs.close();
        con.close();
        return usuarioFilmePesq;
    }

    public static void excluir(int idLista)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "delete from usuariofilme where idUsuarioFilme = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, idLista);

        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(UsuarioFilme listaF, int idFilme, int idUsuario)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "update usuariofilme set contadorEmail=?, Usuario_idUsuario=?, Filme_idFilme=? "
                + "where idUsuarioFilme=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, listaF.getContadorEmail());
        stmt.setInt(2, listaF.getUsuario().getIdUsuario());
        stmt.setInt(3, listaF.getFilme().getIdFilme());
        stmt.setInt(1, listaF.getIdUsuarioFilme());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<UsuarioFilme> getListaProEmail() throws SQLException {
        List<UsuarioFilme> lista = new ArrayList<UsuarioFilme>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT	\n"
                + "	u.nome,\n"
                + "	u.email,\n"
                + "	f.titulo,\n"
                + "	f.idFilme,\n"
                + "	s.idstatus\n"
                + "FROM\n"
                + "	usuariofilme uf,\n"
                + "	usuario u,\n"
                + "	filme f,\n"
                + "	status s\n"
                + "WHERE\n"
                + "	uf.Filme_idFilme = f.idFilme AND\n"
                + "	f.status_idstatus=s.idstatus and\n"
                + "	uf.Usuario_idUsuario = u.idUsuario AND\n"
                + "	f.status_idstatus = 1 AND\n"
                + "	uf.contadorEmail = 0";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            UsuarioFilme lf = new UsuarioFilme();
            Filme filme = new Filme();
            Status status = new Status();
            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            status.setIdStatus(rs.getInt("idstatus"));
            Usuario usu = new Usuario();
            usu.setNome(rs.getString("nome"));
            usu.setEmail(rs.getString("email"));
            filme.setStatus(status);
            lf.setFilme(filme);
            lf.setUsuario(usu);
            lista.add(lf);
        }
        stmt.close();
        rs.close();
        con.close();
        return lista;
    }

    public static void insertContador() throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "		UPDATE \n"
                + "			usuariofilme uf,\n"
                + "	usuario u,\n"
                + "	filme f set uf.contadorEmail=1\n"
                + "WHERE\n"
                + "	uf.Filme_idFilme = f.idFilme AND\n"
                + "	uf.Usuario_idUsuario = u.idUsuario AND\n"
                + "	f.status_idstatus = 1 AND\n"
                + "	uf.contadorEmail = 0;";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

     public static List<UsuarioFilme> getListaRelat() throws SQLException {
        List<UsuarioFilme> lista = new ArrayList<UsuarioFilme>();
        Connection con = Conexao.getConnection();
        String sql = "select f.titulo, count(uf.Filme_idFilme) \n" +
"from usuariofilme uf, filme f\n" +
"where uf.Filme_idFilme=f.idFilme\n" +
"group by uf.Filme_idFilme\n" +
"having count(uf.Filme_idFilme)>0\n" +
"order by count(uf.Filme_idFilme) desc";
        //String sql = "SELECT * FROM filme WHERE status_idstatus = 1 OR status_idstatus = 2";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            UsuarioFilme lf = new UsuarioFilme();
            Filme filme = new Filme();
            filme.setTitulo(rs.getString("titulo"));
            lf.setFilme(filme);
            lista.add(lf);
        }
        stmt.close();
        rs.close();
        con.close();
        return lista;
    }
    
    public static List<UsuarioFilme> getLista() throws SQLException {
        List<UsuarioFilme> lista = new ArrayList<UsuarioFilme>();
        Connection con = Conexao.getConnection();
        String sql = "select * from usuariofilme lf, usuario u filme f where lf.Usuario_idUsuario = u.idUsuario"
                + " lf.Filme_idFilme = f.idFilme ";
        //String sql = "SELECT * FROM filme WHERE status_idstatus = 1 OR status_idstatus = 2";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            UsuarioFilme lf = new UsuarioFilme();
            Filme filme = new Filme();
            Status status = new Status();
            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));
            status.setIdStatus(rs.getInt("status_idstatus"));
            filme.setStatus(status);
            lista.add(lf);
        }
        stmt.close();
        rs.close();
        con.close();
        return lista;
    }

    public static Filme getFilmeByID(int idFilme) throws SQLException {
        Filme filme = new Filme();
        String sql = "SELECT * FROM filme WHERE idFilme = 1";

        return filme;
    }

    public static void main(String[] args) throws SQLException {

        List<UsuarioFilme> lista = getLista();
        for (UsuarioFilme lf : lista) {
            System.out.println("CÓDIGO....: " + lf.getIdUsuarioFilme());
            System.out.println("USUARIO......: " + lf.getUsuario().getApelido());
            System.out.println("FILME......: " + lf.getFilme().getTitulo());
            System.out.println("-----------------------------------");
        }
    }
}
