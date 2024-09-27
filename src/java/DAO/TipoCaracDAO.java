package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.TipoCarac;
import util.Conexao;

public class TipoCaracDAO {

    public static void inserir(TipoCarac tipoCarac)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "insert into tipocarac"
                + "(descricao_tc"
                + ") values (?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, tipoCarac.getDescricao_tc());
  
        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void excluir(TipoCarac tipoCarac)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "delete from tipocarac where idTipoCarac = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, tipoCarac.getIdTipoCarac());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void alterar(TipoCarac tipoCarac)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "update tipocarac set descricao_tc=? "
                + "where idTipoCarac=?";
        PreparedStatement stmt = con.prepareStatement(sql);      
        stmt.setString(1, tipoCarac.getDescricao_tc());
        stmt.setInt(2, tipoCarac.getIdTipoCarac());

        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<TipoCarac> getLista() throws SQLException {
        List<TipoCarac> lista = new ArrayList<TipoCarac>();
        Connection con = Conexao.getConnection();
        String sql = "select * from tipocarac";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            TipoCarac tipocarac = new TipoCarac();
            tipocarac.setIdTipoCarac(rs.getInt("idTipoCarac"));
            tipocarac.setDescricao_tc(rs.getString("descricao_tc"));
                   
            lista.add(tipocarac);
        }
        stmt.close();
        rs.close();
        con.close();
        return lista;
    }

    public static List<TipoCarac> getListaPesq(String input) throws SQLException {
        List<TipoCarac> listaPesq = new ArrayList<TipoCarac>();
        Connection con = Conexao.getConnection();
        String sql = "select * from tipocarac where descricao_tc like concat ('%',?,'%') order by descricao_tc";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, input.toUpperCase());
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            TipoCarac tipocarac = new TipoCarac();
            tipocarac.setIdTipoCarac(rs.getInt("idTipoCarac"));
            tipocarac.setDescricao_tc(rs.getString("descricao_tc"));
                   
            listaPesq.add(tipocarac);
        }
        stmt.close();
        rs.close();
        con.close();
        return listaPesq;

}
}
