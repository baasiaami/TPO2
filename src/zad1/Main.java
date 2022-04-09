/**
 * @author Michalska Barbara S23345
 */

package zad1;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Service s = new Service("United Kingdom");
        String weatherJson = s.getWeather("London");
        Double rate1 = s.getRateFor("USD");
        Double rate2 = s.getNBPRate();

        // ...
        // część uruchamiająca GUI
        SwingUtilities.invokeLater(WebGUI::new);
    }
}
