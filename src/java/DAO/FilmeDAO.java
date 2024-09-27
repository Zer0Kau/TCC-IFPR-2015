package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Filme;
import model.Status;
import util.Conexao;
import util.SessionContext;

public class FilmeDAO {

    public static void inserir(Filme filme, int idStatus)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "insert into filme"
                + "(titulo,descricao,data_Lancamento,nota_IMDB, status_idstatus"
                + ") values (?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, filme.getTitulo());
        stmt.setString(2, filme.getDescricao());
        stmt.setDate(3, new java.sql.Date(filme.getData_Lancamento().getTime()));
        stmt.setDouble(4, filme.getNota_IMDB());
        stmt.setInt(5, filme.getStatus().getIdStatus());

        stmt.execute();
        stmt.close();

        con.close();
    }

    public static void excluir(Filme filme)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "delete from filme where idFilme = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, filme.getIdFilme());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void alterar(Filme filme, int idStatus)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "update filme set titulo=? , descricao=?, data_Lancamento=?, nota_IMDB=?, status_idstatus=? "
                + "where idFilme=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, filme.getTitulo());
        stmt.setString(2, filme.getDescricao());
        stmt.setDate(3, new java.sql.Date(filme.getData_Lancamento().getTime()));
        stmt.setDouble(4, filme.getNota_IMDB());
        stmt.setInt(5, idStatus);
        stmt.setInt(6, filme.getIdFilme());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

     public static List<Filme> getLista() throws SQLException {
        List<Filme> lista = new ArrayList<Filme>();
        Connection con = Conexao.getConnection();
        String sql = "select * from filme fl, status st where fl.status_idstatus = st.idstatus order by status_idstatus";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Filme filme = new Filme();
            Status status = new Status();
            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            //Date data = rs.getDate("data_Lancamento");
            //SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            //String dataFormatada = formato.format(data);
            //filme.setData_Lancamento(rs.getDate(dataFormatada));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));
            status.setIdStatus(rs.getInt("status_idstatus"));
            status.setDescricao_s(rs.getString("descricao_s"));
            filme.setStatus(status);
            lista.add(filme);
        }
        stmt.close();
        rs.close();
        con.close();
        return lista;
    }
    
    public static Filme getFilmin(int idf) throws SQLException {
        Filme lista = new Filme();
        Connection con = Conexao.getConnection();
        String sql = "select * from filme fl, status s where fl.status_idstatus=s.idstatus and fl.idFilme=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, idf);
        System.out.println("----------------------");
         System.out.println(stmt);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Filme filme = new Filme();
            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));
            
            Status status = new Status();
            status.setDescricao_s(rs.getString("descricao_s"));
            status.setIdStatus(rs.getInt("idstatus"));
            filme.setStatus(status);
        }
        stmt.close();
        rs.close();
        con.close();
        return lista;
    }
    
    public static List<Filme> pesqFilmes(String input) throws SQLException {
        List<Filme> listaPesq = new ArrayList<Filme>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM filme f , status s WHERE f.status_idstatus = s.idstatus and titulo LIKE CONCAT('%',?,'%') or f.status_idstatus = s.idstatus and descricao_s LIKE CONCAT('%',?,'%') ORDER BY idstatus";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, input.toUpperCase());
        stmt.setString(2, input.toUpperCase());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Filme filme = new Filme();
            Status status = new Status();
            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));
            status.setIdStatus(rs.getInt("status_idstatus"));
            status.setDescricao_s(rs.getString("descricao_s"));
            filme.setStatus(status);
            listaPesq.add(filme);
        }
        return listaPesq;
    }

    public static List<Filme> pesqFilmeEscolha(String input) throws SQLException {
        List<Filme> listaPesqe = new ArrayList<Filme>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT	f.*,	uf.*,	s.* FROM	filme f	LEFT JOIN usuariofilme uf ON f.idFilme = uf.Filme_idFilme AND uf.Usuario_idUsuario = ?,	status s WHERE	f.status_idstatus = s.idstatus AND	(s.idstatus = 1 OR s.idstatus = 2) AND	uf.Usuario_idUsuario is NULL and f.titulo like concat ('%',?,'%') or f.status_idstatus = s.idstatus AND	(s.idstatus = 1 OR s.idstatus = 2) AND	uf.Usuario_idUsuario is NULL and s.descricao_s like concat ('%',?,'%') order by descricao_s";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        stmt.setString(2, input.toUpperCase());
        stmt.setString(3, input.toUpperCase());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Filme filme = new Filme();
            Status status = new Status();
            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));
            status.setIdStatus(rs.getInt("status_idstatus"));
            status.setDescricao_s(rs.getString("descricao_s"));
            filme.setStatus(status);
            listaPesqe.add(filme);
        }
        return listaPesqe;
    }

    public static List<Filme> getListaUSUARIOS() throws SQLException {
        List<Filme> listaUSU = new ArrayList<Filme>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT\n"
                + "	f.*,\n"
                + "	u.*\n"
                + "FROM\n"
                + "	listafilmes lf,\n"
                + "	usuario u,\n"
                + "	filme f\n"
                + "WHERE\n"
                + "	lf.Usuario_idUsuario = u.idUsuario AND\n"
                + "	lf.Filme_idFilme = f.idFilme AND\n"
                + "	(status_idstatus = 1 OR status_idstatus = 2) AND\n"
                + "	u.idUsuario = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        ResultSet rs = stmt.executeQuery();
        System.out.println(stmt);
        while (rs.next()) {
            Filme filme = new Filme();
            Status status = new Status();
            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));
          //status.setIdStatus(rs.getInt("status_idstatus"));
           //status.setDescricao_s(rs.getString("descricao_s"));
          // filme.setStatus(status);
            listaUSU.add(filme);
        }
        stmt.close();
        rs.close();
        con.close();
        return listaUSU;
    }
    
    public static List<Filme> getListaESCOLHA() throws SQLException {
        List<Filme> lista = new ArrayList<Filme>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT\n" +
"	f.*,\n" +
"	uf.*,\n" +
"	s.*\n" +
"FROM\n" +
"	filme f\n" +
"	LEFT JOIN usuariofilme uf ON f.idFilme = uf.Filme_idFilme AND uf.Usuario_idUsuario = ?,\n" +
"	status s\n" +
"WHERE\n" +
"	f.status_idstatus = s.idstatus AND\n" +
"	(s.idstatus = 1 OR s.idstatus = 2) AND\n" +
"	uf.Usuario_idUsuario is NULL order by status_idstatus";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Filme filme = new Filme();
            Status status = new Status();
            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));
            status.setIdStatus(rs.getInt("status_idstatus"));
            status.setDescricao_s(rs.getString("descricao_s"));
            filme.setStatus(status);
            lista.add(filme);
        }
        stmt.close();
        rs.close();
        con.close();
        return lista;
    }


    public static Filme getFilmeById(String idFilme) throws SQLException {
        Filme filme = new Filme();
        Status status = new Status();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM filme WHERE idFilme IN(?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, idFilme);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));
            status.setIdStatus(rs.getInt("status_idstatus"));
            status.setDescricao_s(rs.getString("descricao_s"));
            filme.setStatus(status);
        }
        System.out.println("passou aqui DAO filme");
        System.out.println(filme.getTitulo());
        return filme;
    }

    public static void main(String[] args) {

        try {
            List<Filme> lista = getLista();

            for (Filme ff : lista) {
                System.out.println("CÓDIGO....: " + ff.getIdFilme());
                System.out.println("TITULO......: " + ff.getTitulo());
                System.out.println("DESCRIÇÃO......: " + ff.getDescricao());
                System.out.println("DATA DE LANÇAMENTO......: " + ff.getData_Lancamento());
                System.out.println("NOTA IMDB......: " + ff.getNota_IMDB());
                System.out.println("STATUS......: " + ff.getStatus().getDescricao_s());
                System.out.println("-----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
