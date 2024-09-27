/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;


@WebFilter("*.xhtml")
public class AutorizacaoFilter implements Filter {

    private static final String[][] DIREITO_ACESSO = {
        {"admin",
            "/faces/manutencaoUsuario.xhtml",
            "/faces/manutencaoFilmes.xhtml",
            "/faces/manutencaoTipoCarac.xhtml",
            "/faces/manutencaoCarac.xhtml",
            "/faces/manutencaoCaracFilme.xhtml",
            "/faces/manutencaoEmail.xhtml",
            "/faces/GerenciadorPaginas.xhtml",
            "/faces/startXXX.xhtml",
            "/faces/queryUsuario.xhtml",
            "/faces/login.xhtml",
            "/faces/loginErro.xhtml",
            "/faces/ajuda.xhtml",
            "/faces/Usuario.xhtml",
            "/faces/Exemplo.xhtml",
            "/faces/serv.xhtml",
            "faces/Usuario_1.xhtml",
            "/faces/cadastro.xhtml",
            "/faces/cadastro_1.xhtml",
            "/faces/relacarac_1.xhtml",
            "/faces/relacarac_2.xhtml",
            "/faces/relafilm_1.xhtml",
            "/faces/relafilm_2.xhtml",
            
        },
        {"comum",
            "/faces/login.xhtml",
            "/faces/Inicio.xhtml",
            "/faces/loginErro.xhtml",
            "/faces/loginErro_1.xhtml",
            "/faces/FilmesUsuario.xhtml",
            "/faces/Usuario.xhtml",
            "/faces/Usuario_1.xhtml",
            "/faces/contato.xhtml",
            "/faces/FilmesEscolher.xhtml",
            "/faces/cadastro.xhtml",
            "/faces/cadastro_1.xhtml",
                
        },          
    };


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        Usuario user = null;

        HttpSession sess = ((HttpServletRequest) req).getSession();

        if (sess != null) {
            user = (Usuario) sess.getAttribute("usuarioLogado");

        }

        if ((user == null)
                && !request.getRequestURI().endsWith("/faces/login.xhtml")
                && !request.getRequestURI().contains("/javax.faces.resource/")
                && !request.getRequestURI().endsWith("/faces/cadastro.xhtml")
                && !request.getRequestURI().endsWith("/faces/loginErro.xhtml")) {
            response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
        } else {
            try {
                boolean foi = false;
                if (user.getTipoUsuario().equals("admin")) {
                    for (int i = 1; i < DIREITO_ACESSO[0].length; i++) {
                        if (request.getRequestURI().endsWith(DIREITO_ACESSO[0][i])) {
                            System.out.println(DIREITO_ACESSO[0][i] + " - ADMINISTRADOR TEM ACESSO");
                            chain.doFilter(req, res);
                            foi = false;
                            break;
                        } else {
                            foi = true;
                        }
                    }
                    if (foi) {
                        response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
                    }
                } else if (user.getTipoUsuario().equals("comum")) {
                    for (int i = 1; i < DIREITO_ACESSO[1].length; i++) {
                        if (request.getRequestURI().endsWith(DIREITO_ACESSO[1][i])) {
                            chain.doFilter(req, res);
                            foi = false;
                            break;
                        } else {
                            foi = true;
                        }
                    }
                    if (foi) {
                        response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
                    }

                }
            } catch (NullPointerException e) {
                chain.doFilter(req, res);
            }

        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }
    
    
}
