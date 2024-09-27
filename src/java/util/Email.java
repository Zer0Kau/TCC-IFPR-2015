package util;

import DAO.UsuarioFilmeDAO;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import model.UsuarioFilme;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

@ManagedBean
public class Email {

    private final static String userAuthentication = "filmeintcc@gmail.com";
    private final static String passwordUserAuthentication = "tccemanoel";
    private final static String sender = "filmeintcc@gmail.com";
    private final static String smtp = "smtp.gmail.com";
    private final static boolean authentication = true;
   
    public static void sendMail(String message, String subject, String receiver)
            throws EmailException {
        SimpleEmail email = new SimpleEmail();
        email.setHostName(smtp);
        email.setAuthentication(userAuthentication, passwordUserAuthentication);
        email.setSSL(authentication);
        email.addTo(receiver);
        email.setFrom(sender);
        try {
            email.setSubject(new String(subject.getBytes("utf-8"), "utf-8"));
            email.setMsg(new String(message.getBytes("utf-8"), "utf-8"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        email.setMsg(message);
        email.send();
        email = null;
    }
    
    public void envioDeemails() throws SQLException {
        try{
            List<UsuarioFilme> lista = UsuarioFilmeDAO.getListaProEmail();
        for (UsuarioFilme uf : lista) {
            sendMail("Olá "+uf.getUsuario().getNome()+"! "+uf.getFilme().getTitulo()+" acabou de entrar em cartaz nos cinemas!"
                    + "\nQuer saber mais sobre o filme? Visite o site e veja a página dedicada!", "Um filme da sua lista de desejo está em estréia!",uf.getUsuario().getEmail());
        }   UsuarioFilmeDAO.insertContador();
        }catch(EmailException e){
           Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, e);
            
    }
    }   
 }

