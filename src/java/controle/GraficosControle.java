/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import DAO.CaracFilmeDAO;
import org.primefaces.model.chart.ChartSeries;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import model.CaracFilme;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author Noelito
 */
@ManagedBean
@SessionScoped
public class GraficosControle implements Serializable {

    private BarChartModel filmesPorGen;

    private BarChartModel initBarModel(int idtc) {
        BarChartModel model = new BarChartModel();

        try {
            List<CaracFilme> lista = CaracFilmeDAO.GrafFilmeGen(idtc);
            for (CaracFilme c : lista) {
                ChartSeries s = new ChartSeries();
                s.setLabel(c.getAux());
                s.set(c.getAux(), c.getIdCarac_Filme());
                model.addSeries(s);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    private void createBarModel(int idtc) {
        filmesPorGen = initBarModel(idtc);

        filmesPorGen.setTitle("Gráfico");
        filmesPorGen.setLegendPosition("ne");

        Axis yAxis = filmesPorGen.getAxis(AxisType.Y);
        yAxis.setLabel("Quantidade de Filmes");

    }



    public BarChartModel getBarModel() {
        return filmesPorGen;
    }
    
    
    public String init(int idtc) {
        System.out.println("------------------------------");
        System.out.println(idtc);
        System.out.println("------------------------------");
        createBarModel(idtc);
        return "relafilm_2.xhtml?faces-redirect=true";
    }
}
