/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.Impressao;

/**
 *
 * @author Noelito
 */
@ManagedBean
@SessionScoped
public class RelatorioControle {
        public void gerarRelatorioSemFiltro(){
        HashMap<String,Object> lista = new HashMap<>();
        lista.put("teste", "a");
        String caminho = "/relatorios/FilmesMaisEscolhid.jasper";
        String nome = "Filmes_Usuario";

        new Impressao().gerarRelatorio(lista, caminho, nome);
    }
    
}
