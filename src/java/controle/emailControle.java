/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import DAO.UsuarioDAO;
import DAO.UsuarioFilmeDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.UsuarioFilme;
import org.apache.naming.factory.SendMailFactory;
import util.SendMail;
import util.SendMail;
import util.SessionContext;

@SessionScoped
@ManagedBean
public class emailControle {

    private List<UsuarioFilme> emailusu = new ArrayList<UsuarioFilme>();

    public void attListaEmail() {
            try {
            emailusu = UsuarioFilmeDAO.getListaProEmail();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<UsuarioFilme> getEmailusu() {
        return emailusu;
    }

    public void setEmailusu(List<UsuarioFilme> emailusu) {
        this.emailusu = emailusu;
    }

    public void enviarEmail() {

        try {
            List<UsuarioFilme> lista = UsuarioFilmeDAO.getListaProEmail();
            for (UsuarioFilme uf : lista) {
                SendMail sm = new SendMail("smtp.gmail.com", "465");
                sm.sendMail("filmeintcc@gmail.com", uf.getUsuario().getEmail(), "Um filme da sua lista de desejo está em estréia!","<body style=\"margin: 0; padding: 0;\">\n" +
" <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"  <tr>\n" +
"   <td style=\"padding: 20px 0 30px 0;\">\n" +
"  <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #cccccc;\">\n" +
" <tr>\n" +
" <td align=\"center\" bgcolor=\"#1a1a1a\" style=\"padding: 40px 0 30px 0;\">\n" +
" <img src=\"https://i.imgur.com/HcJrc13.png\" alt=\"Filmes-In\" style=\"display: block;\" />\n" +
"</td>\n" +
" </tr>\n" +
" <tr>\n" +
" <td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\n" +
" <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
" <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 24px;\">\n" +
" <b>Um filme da sua lista de desejo está em estréia!</b>\n" +
"</td>\n" +
"  <tr>\n" +
"   <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
"    Olá "+uf.getUsuario().getNome()+";\n" +
"   </td>\n" +
"  </tr>\n" +
"  <tr>\n" +
"   <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
"    O filme "+uf.getFilme().getTitulo()+" entrou em estréia na data de hoje, caso você queira saber um pouco mais sobre os detalhes do filme; \n" +
"   </td>\n" +
"  </tr>\n" +
"  <tr>\n" +
"   <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
"    Volte ao site e visite a página do filme!\n" +
"   </td>\n" +
"  </tr>\n" +
" </table>\n" +
"</td>\n" +
" </tr>\n" +
" <tr>\n" +
"  <td bgcolor=\"#336699\" style=\"padding: 30px 30px 30px 30px;\">\n" +
"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
" <tr>\n" +
"<td align=\"right\">\n" +
" <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
"  <tr>\n" +
"  <td width=\"75%\" style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px; line-height: 20px;\">\n" +
" &reg; Emanoel, Filme-In 2017<br/>"+
" Todos os direitos reservados. Caso não queira mais receber estas mensagens, compareça ao site e desative sua conta.\n" +
"</td>\n" +
"   <td>\n" +
"    <a href=\"https://twitter.com/_Noelzin\">\n" +
"     <img src=\"https://image.flaticon.com/icons/png/128/9/9148.png\" alt=\"Twitter\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
"    </a>\n" +
"   </td>\n" +
"   <td style=\"font-size: 0; line-height: 0;\" width=\"20\">&nbsp;</td>\n" +
"   <td>\n" +
"    <a href=\"https://www.facebook.com/emanoel.benedito?ref=bookmarks\">\n" +
"     <img src=\"https://image.flaticon.com/icons/png/128/61/61045.png\" alt=\"Facebook\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
"    </a>\n" +
"   </td>\n" +
"  </tr>\n" +
" </table>\n" +
"</td>\n" +
" </tr>\n" +
"</table>\n" +
"</td>\n" +
" </tr>\n" +
"</table>\n" +
"   </td>\n" +
"  </tr>\n" +
" </table>\n" +
"</body>");
            }
            UsuarioFilmeDAO.insertContador();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("PASSOUUUUUUUUUUUUAUSUIIDNASIDNSAIDNSAUIDN");
    }
}
