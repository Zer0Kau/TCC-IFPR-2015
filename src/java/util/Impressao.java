package util;

import java.sql.ResultSet;
import java.util.HashMap;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import util.Conexao;

/**
 *
 * @author Héber Morais
 * @since 16/12/2015
 */
public class Impressao {

    public void gerarRelatorio(HashMap<String, Object> lista, String caminhoRel, String nome) {
        try {
            String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath(caminhoRel);
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminho, lista, Conexao.getConnection());
            
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + nome + ".pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception erro) {
            erro.printStackTrace();
//            JOptionPane.showMessageDialog(null, "nao abriu relatorio: " + erro);
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage mensagem = new FacesMessage(erro.getMessage());
            mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, mensagem);
        }
    }
    public void gerarRelatorio(HashMap<String, Object> lista, String caminhoRel, String nome, ResultSet rs) {
        try {
            JRResultSetDataSource jrs = new JRResultSetDataSource(rs);
            String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath(caminhoRel);
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminho, lista, jrs);
            
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + nome + ".pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception erro) {
            erro.printStackTrace();
//            JOptionPane.showMessageDialog(null, "nao abriu relatorio: " + erro);
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage mensagem = new FacesMessage(erro.getMessage());
            mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, mensagem);
        }
    }

}
