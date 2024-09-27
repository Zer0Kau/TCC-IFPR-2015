package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import static jdk.nashorn.tools.ShellFunctions.input;
import model.Caracteristica;
import model.TipoCarac;
import util.Conexao;

public class CaracteristicaDAO {

    public static void inserir(Caracteristica carac, int idTipoCarac)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "insert into caracteristica"
                + "(descricao_c, tipoCarac_idTipoCarac"
                + ") values (?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, carac.getDescricao_c());
        stmt.setInt(2, carac.getTipoCarac().getIdTipoCarac());
        

        stmt.execute();
        stmt.close();
        
        con.close();
    }

    public static void excluir(Caracteristica carac)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "delete from caracteristica where idCaracteristica = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, carac.getIdCarac());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void alterar(Caracteristica carac, int idTipoCarac)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "update caracteristica set descricao_c=?, tipoCarac_idTipoCarac=? "
                + "where idCaracteristica=?";
        PreparedStatement stmt = con.prepareStatement(sql);      
        stmt.setString(1, carac.getDescricao_c());
        stmt.setInt(2, carac.getTipoCarac().getIdTipoCarac());
        stmt.setInt(3, carac.getIdCarac());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    
    
    
    public static void emprodparaemest() throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "UPDATE\n" +
"filme\n" +
"set\n" +
"status_idstatus = 1\n" +
"where status_idstatus=2 and data_lancamento = CURRENT_DATE()";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    
    public static void emestparajalan() throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "UPDATE\n" +
"filme\n" +
"set\n" +
"status_idstatus = 3\n" +
"where status_idstatus=1 and data_lancamento < CURRENT_DATE()";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    
    public static void tudojalan() throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "UPDATE\n" +
"filme\n" +
"set\n" +
"status_idstatus = 3\n" +
"where (status_idstatus= 1 or status_idstatus = 2) and data_lancamento < CURRENT_DATE()";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    
    public static List<Caracteristica> getListaFiltredByTC(TipoCarac tipocarac) throws SQLException {
        List<Caracteristica> lista = new ArrayList<Caracteristica>();
        Connection con = Conexao.getConnection();
        String sql = "select * from caracteristica c, tipocarac tc where c.tipoCarac_idTipoCarac = tc.idTipoCarac and tc.idTipoCarac ="+tipocarac.getIdTipoCarac();
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            Caracteristica carac = new Caracteristica();
            TipoCarac a = new TipoCarac();
            a.setIdTipoCarac(rs.getInt("idTipoCarac"));
            a.setDescricao_tc(rs.getString("descricao_tc"));
            carac.setIdCarac(rs.getInt("idCaracteristica"));
            carac.setDescricao_c(rs.getString("descricao_c"));
            tipocarac.setIdTipoCarac(rs.getInt("tipoCarac_idTipoCarac"));
            tipocarac.setDescricao_tc(rs.getString("descricao_tc"));
            carac.setTipoCarac(tipocarac);
            lista.add(carac);
        }
        stmt.close();
        rs.close();
        con.close();
        return lista;
    }

    public static List<Caracteristica> getLista() throws SQLException {
        List<Caracteristica> lista = new ArrayList<Caracteristica>();
        Connection con = Conexao.getConnection();
        String sql = "select * from caracteristica c, tipocarac tc where c.tipoCarac_idTipoCarac = tc.idTipoCarac";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            Caracteristica carac = new Caracteristica();
            TipoCarac tipocarac = new TipoCarac();
            carac.setIdCarac(rs.getInt("idCaracteristica"));
            carac.setDescricao_c(rs.getString("descricao_c"));
            tipocarac.setIdTipoCarac(rs.getInt("tipoCarac_idTipoCarac"));
            tipocarac.setDescricao_tc(rs.getString("descricao_tc"));
            carac.setTipoCarac(tipocarac);
            lista.add(carac);
        }
        stmt.close();
        rs.close();
        con.close();
        return lista;
    }
    
     public static List<Caracteristica> getListaPesq(String input) throws SQLException {
        List<Caracteristica> listaPesq = new ArrayList<Caracteristica>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM caracteristica c, tipocarac t WHERE c.tipoCarac_idTipoCarac = t.idTipoCarac and descricao_c LIKE CONCAT('%',?,'%') or  c.tipoCarac_idTipoCarac = t.idTipoCarac and descricao_tc LIKE CONCAT ('%',?,'%') ORDER BY descricao_c";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, input.toUpperCase());
        stmt.setString(2, input.toUpperCase());
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            Caracteristica carac = new Caracteristica();
            TipoCarac tipocarac = new TipoCarac();
            carac.setIdCarac(rs.getInt("idCaracteristica"));
            carac.setDescricao_c(rs.getString("descricao_c"));
            tipocarac.setIdTipoCarac(rs.getInt("tipoCarac_idTipoCarac"));
            tipocarac.setDescricao_tc(rs.getString("descricao_tc"));
            carac.setTipoCarac(tipocarac);
            listaPesq.add(carac);
        }
        stmt.close();
        rs.close();
        con.close();
        return listaPesq;
    }
    
   public static void main(String[] args) {

        try {
            List<Caracteristica> lista = getLista();

            for (Caracteristica c : lista) {
                System.out.println("CÓDIGO....: " + c.getIdCarac());
                System.out.println("DESCRIÇÃO......: " + c.getDescricao_c());
                System.out.println("TIPO......: " + c.getTipoCarac().getDescricao_tc());
                System.out.println("-----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

   

    }

}
