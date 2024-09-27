package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.UsuarioLog;
import util.Conexao;


public class UsuarioLogDAO {
    
  public static UsuarioLog getUsuarioLogado(UsuarioLog u) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = 
       "select * from usuariolog u where u.apelido = ? and u.senha = ? ";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, u.getApelido());
        stmt.setString(2, u.getSenha());
        ResultSet rs = stmt.executeQuery();

        UsuarioLog usuariolog = null;
        if (rs.next()) {
            usuariolog = new UsuarioLog();
            usuariolog.setIdUsuarioLog(rs.getInt("idUsuarioLog"));
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

  
    
}
