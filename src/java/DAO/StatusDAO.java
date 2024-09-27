package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Status;
import util.Conexao;

public class StatusDAO {

    public static void inserir(Status status)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "insert into status"
                + "(descricao_s"
                + ") values (?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, status.getDescricao_s());
  
        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void excluir(Status status)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "delete from status where idstatus = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, status.getIdStatus());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void alterar(Status status)
            throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "update status set descricao_s=? "
                + "where idstatus=?";
        PreparedStatement stmt = con.prepareStatement(sql);      
        stmt.setString(1, status.getDescricao_s());
        stmt.setInt(2, status.getIdStatus());

        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<Status> getLista() throws SQLException {
        List<Status> lista = new ArrayList<Status>();
        Connection con = Conexao.getConnection();
        String sql = "select * from status";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            Status status = new Status();
            status.setIdStatus(rs.getInt("idstatus"));
            status.setDescricao_s(rs.getString("descricao_s"));
                   
            lista.add(status);
        }
        stmt.close();
        rs.close();
        con.close();
        return lista;
    }
    
    public static void main(String[] args) {

        try {
            List<Status> lista = getLista();

            for (Status s : lista) {
                System.out.println("CÓDIGO....: " + s.getIdStatus());
                System.out.println("DESCRIÇÃO......: " + s.getDescricao_s());
                System.out.println("-----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


   

}
}
