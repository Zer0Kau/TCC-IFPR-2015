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
import model.CaracFilme;
import model.Caracteristica;
import model.TipoCarac;
import model.Status;
import util.Conexao;

public class CaracFilmeDAO {

    public static void inserir(CaracFilme caracf) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "insert into carac_filme (idCarac_Filme, Filme_idFilme, Caracteristica_idCaracteristica) values (?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, caracf.getIdCarac_Filme());
        stmt.setInt(2, caracf.getFilme().getIdFilme());
        stmt.setInt(3, caracf.getCaracteristica().getIdCarac());

        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(CaracFilme caracf) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update carac_filme set Filme_idFilme=?, Caracteristica_idCaracteristica=? WHERE  idCarac_Filme = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, caracf.getFilme().getIdFilme());
        stmt.setInt(2, caracf.getCaracteristica().getIdCarac());
        stmt.setInt(3, caracf.getIdCarac_Filme());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(CaracFilme caracf) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "DELETE FROM carac_filme WHERE  idCarac_Filme=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, caracf.getIdCarac_Filme());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<CaracFilme> getLista() throws SQLException {
        List<CaracFilme> lista = new ArrayList<CaracFilme>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM carac_filme cf ,filme ff, caracteristica c, tipocarac t,    status s where cf.Caracteristica_idCaracteristica = c.idCaracteristica AND cf.Filme_idFilme = ff.idFilme AND ff.status_idstatus = s.idstatus and c.tipoCarac_idTipoCarac=t.idTipoCarac";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            CaracFilme caracf = new CaracFilme();

            Caracteristica carac = new Caracteristica();

            carac.setIdCarac(rs.getInt("idCaracteristica"));
            carac.setDescricao_c(rs.getString("descricao_c"));

            TipoCarac tipocarac = new TipoCarac();

            tipocarac.setIdTipoCarac(rs.getInt("tipoCarac_idTipoCarac"));
            tipocarac.setDescricao_tc(rs.getString("descricao_tc"));

            carac.setTipoCarac(tipocarac);

            Filme filme = new Filme();

            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));

            Status status = new Status();

            status.setIdStatus(rs.getInt("status_idstatus"));
            status.setDescricao_s(rs.getString("descricao_s"));
            filme.setStatus(status);

            caracf.setFilme(filme);
            caracf.setCaracteristica(carac);
            caracf.setIdCarac_Filme(rs.getInt("idCarac_Filme"));
            lista.add(caracf);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }

    public static List<CaracFilme> GrafFilmeGen(int idtc) throws SQLException {
        List<CaracFilme> lista = new ArrayList<CaracFilme>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT\n"
                + "	count(*) as qtde,\n"
                + "	c.descricao_c\n"
                + "FROM\n"
                + "	filme f,\n"
                + "	carac_filme cf,\n"
                + "	caracteristica c,\n"
                + "	tipocarac t\n"
                + "WHERE\n"
                + "	f.idFilme = cf.Filme_idFilme AND\n"
                + "	cf.Caracteristica_idCaracteristica = c.idCaracteristica AND\n"
                + "	c.tipoCarac_idTipoCarac = t.idTipoCarac AND\n"
                + "	t.idTipoCarac = "+idtc+"\n"
                + "GROUP BY\n"
                + "	c.idCaracteristica";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            CaracFilme caracf = new CaracFilme();

            caracf.setIdCarac_Filme(rs.getInt("qtde"));
            caracf.setAux(rs.getString("descricao_c"));
            lista.add(caracf);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }

    public static List<CaracFilme> PesqRelat(int idCarac, String descr_c) throws SQLException {
        List<CaracFilme> lista = new ArrayList<CaracFilme>();
        Connection con = Conexao.getConnection();
        String sql = "select *\n"
                + "from filme f, tipocarac tc, caracteristica c, carac_filme cf where cf.Filme_idFilme=f.idFilme and cf.Caracteristica_idCaracteristica=c.idCaracteristica and c.tipoCarac_idTipoCarac=tc.idTipoCarac and tc.idTipoCarac=? and c.descricao_c like concat ('%',?,'%')";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, idCarac);
        stmt.setString(2, descr_c);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            CaracFilme caracf = new CaracFilme();

            Caracteristica carac = new Caracteristica();

            carac.setIdCarac(rs.getInt("idCaracteristica"));
            carac.setDescricao_c(rs.getString("descricao_c"));

            TipoCarac tipocarac = new TipoCarac();

            tipocarac.setIdTipoCarac(rs.getInt("tipoCarac_idTipoCarac"));
            tipocarac.setDescricao_tc(rs.getString("descricao_tc"));

            carac.setTipoCarac(tipocarac);

            Filme filme = new Filme();

            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));

            caracf.setFilme(filme);
            caracf.setCaracteristica(carac);
            caracf.setIdCarac_Filme(rs.getInt("idCarac_Filme"));
            lista.add(caracf);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }

    public static Filme getInfoFilm(int idFilm) throws SQLException {
        Filme filme = new Filme();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM filme f WHERE f.idFilme IN(?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, idFilm);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));
        }
        System.out.println("passou aqui DAO");
        System.out.println(filme.getTitulo());
        return filme;
    }

    public static List<CaracFilme> DescricaoFilmes(int idFilme) throws SQLException {
        List<CaracFilme> listaDescricoes = new ArrayList<CaracFilme>();
        Connection con = Conexao.getConnection();
        String sql = "select * from filme f, carac_filme cf, caracteristica c, tipocarac tc where cf.Filme_idFilme=f.idFilme and cf.Caracteristica_idCaracteristica=c.idCaracteristica and c.tipoCarac_idTipoCarac=tc.idTipoCarac and idFilme=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, idFilme);
        System.out.println("----------------------");
        System.out.println(stmt);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            CaracFilme caracf = new CaracFilme();

            Caracteristica carac = new Caracteristica();

            carac.setIdCarac(rs.getInt("idCaracteristica"));
            carac.setDescricao_c(rs.getString("descricao_c"));

            TipoCarac tipocarac = new TipoCarac();

            tipocarac.setIdTipoCarac(rs.getInt("tipoCarac_idTipoCarac"));
            tipocarac.setDescricao_tc(rs.getString("descricao_tc"));

            carac.setTipoCarac(tipocarac);

            Filme filme = new Filme();

            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));

            caracf.setFilme(filme);
            caracf.setCaracteristica(carac);
            caracf.setIdCarac_Filme(rs.getInt("idCarac_Filme"));
            listaDescricoes.add(caracf);
        }
        stmt.close();
        rs.close();
        con.close();

        return listaDescricoes;
    }

    public static CaracFilme DescricaozinhFilmes(int idFilme) throws SQLException {
        CaracFilme film = new CaracFilme();
        Connection con = Conexao.getConnection();
        String sql = "select * from filme f, carac_filme cf, caracteristica c, tipocarac tc where cf.Filme_idFilme=f.idFilme and cf.Caracteristica_idCaracteristica=c.idCaracteristica and c.tipoCarac_idTipoCarac=tc.idTipoCarac and idFilme=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, idFilme);
        System.out.println("----------------------");
        System.out.println(stmt);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            CaracFilme caracf = new CaracFilme();

            Caracteristica carac = new Caracteristica();

            carac.setIdCarac(rs.getInt("idCaracteristica"));
            carac.setDescricao_c(rs.getString("descricao_c"));

            TipoCarac tipocarac = new TipoCarac();

            tipocarac.setIdTipoCarac(rs.getInt("tipoCarac_idTipoCarac"));
            tipocarac.setDescricao_tc(rs.getString("descricao_tc"));

            carac.setTipoCarac(tipocarac);

            Filme filme = new Filme();

            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));

            caracf.setFilme(filme);
            caracf.setCaracteristica(carac);
            caracf.setIdCarac_Filme(rs.getInt("idCarac_Filme"));
        }
        stmt.close();
        rs.close();
        con.close();

        return film;
    }

    public static List<CaracFilme> getListaPesq(String input) throws SQLException {
        List<CaracFilme> listaPesq = new ArrayList<CaracFilme>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM carac_filme cf ,filme ff, caracteristica c, tipocarac t,    status s where cf.Caracteristica_idCaracteristica = c.idCaracteristica AND cf.Filme_idFilme = ff.idFilme AND ff.status_idstatus = s.idstatus and c.tipoCarac_idTipoCarac=t.idTipoCarac and ff.titulo like concat ('%',?,'%') or cf.Caracteristica_idCaracteristica = c.idCaracteristica AND cf.Filme_idFilme = ff.idFilme AND ff.status_idstatus = s.idstatus and c.tipoCarac_idTipoCarac=t.idTipoCarac and t.descricao_tc like concat ('%',?,'%') or cf.Caracteristica_idCaracteristica = c.idCaracteristica AND cf.Filme_idFilme = ff.idFilme AND ff.status_idstatus = s.idstatus and c.tipoCarac_idTipoCarac=t.idTipoCarac and c.descricao_c like concat ('%',?,'%') order by ff.titulo";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, input.toUpperCase());
        stmt.setString(2, input.toUpperCase());
        stmt.setString(3, input.toUpperCase());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            CaracFilme caracf = new CaracFilme();

            Caracteristica carac = new Caracteristica();

            carac.setIdCarac(rs.getInt("idCaracteristica"));
            carac.setDescricao_c(rs.getString("descricao_c"));

            TipoCarac tipocarac = new TipoCarac();

            tipocarac.setIdTipoCarac(rs.getInt("tipoCarac_idTipoCarac"));
            tipocarac.setDescricao_tc(rs.getString("descricao_tc"));

            carac.setTipoCarac(tipocarac);

            Filme filme = new Filme();

            filme.setIdFilme(rs.getInt("idFilme"));
            filme.setTitulo(rs.getString("titulo"));
            filme.setDescricao(rs.getString("descricao"));
            filme.setData_Lancamento(rs.getDate("data_Lancamento"));
            filme.setNota_IMDB(rs.getDouble("nota_IMDB"));

            Status status = new Status();

            status.setIdStatus(rs.getInt("status_idstatus"));
            status.setDescricao_s(rs.getString("descricao_s"));
            filme.setStatus(status);

            caracf.setFilme(filme);
            caracf.setCaracteristica(carac);
            caracf.setIdCarac_Filme(rs.getInt("idCarac_Filme"));
            listaPesq.add(caracf);
        }
        stmt.close();
        rs.close();
        con.close();

        return listaPesq;
    }

    public static void main(String[] args) throws SQLException {

        List<CaracFilme> lista = getLista();
        for (CaracFilme cf : lista) {
            System.out.println("CÓDIGO....: " + cf.getIdCarac_Filme());
            System.out.println("CARACTERISTICA......: " + cf.getCaracteristica());
            System.out.println("FILME......: " + cf.getFilme());
            System.out.println("-----------------------------------");
        }
    }
}
