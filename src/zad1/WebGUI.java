package zad1;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class WebGUI extends JFrame{
    private JTextField countryBar;
    private JPanel mainPanel;
    private JButton loadButton;
    private JPanel webPanel;
    private JTextField cityBar;
    private JTextField currencyBar;
    private JLabel NBPLabel;
    private JLabel rateLabel;
    private JLabel tempLabel;
    private JLabel humidityLabel;
    private JLabel descLabel;
    private JLabel pressureLabel;
    private JFXPanel jfxPanel;
    private WebView webView;

    public WebGUI() throws HeadlessException {

        jfxPanel = new JFXPanel();
        Platform.runLater(this::createJFXContent);

        webPanel.add(jfxPanel);
        this.add(mainPanel);

        this.pack();

        loadButton.addActionListener(e -> {
            Platform.runLater(this::reloadPage);
            Service service = new Service(countryBar.getText());
            NBPLabel.setText(service.getNBPRate()+"");
            rateLabel.setText(service.getRateFor(currencyBar.getText())+"");

            Gson gson = new Gson();
            WeatherDTO weatherDTO = gson.fromJson(service.getWeather(cityBar.getText()), WeatherDTO.class);
            tempLabel.setText(weatherDTO.main.temp + "");
            humidityLabel.setText(weatherDTO.main.humididty + "");
            pressureLabel.setText(weatherDTO.main.pressure + "");
            descLabel.setText(weatherDTO.weather.get(0).description);
        });

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    private void createJFXContent() {
        webView = new WebView();
        webView.getEngine().load(String.format("https://en.wikipedia.org/wiki/%s",cityBar.getText()));
        Scene scene = new Scene(webView);
        jfxPanel.setScene(scene);
        this.pack();
    }

    private void reloadPage(){
        webView.getEngine().load(String.format("https://en.wikipedia.org/wiki/%s",cityBar.getText()));
    }

}

class WeatherDTO{
    List<Weather> weather;
    Temperature main;
}

class Weather {
    String description;
}
class Temperature{
    double temp;
    int pressure;
    int humididty;
}


