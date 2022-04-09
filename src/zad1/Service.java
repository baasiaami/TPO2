/**
 * @author Michalska Barbara S23345
 */

package zad1;


import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    String country;
    String currency;
    HashMap<String, String> hashMap;

    public Service(String country) {
        this.country = country;
        hashMap = getHashMap();
        currency = hashMap.get(country);
    }

    public String getWeather(String city) {
        String info = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=3406488b2443fdf7553e90f151881ef6", city);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(info).openConnection().getInputStream()));) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double getRateFor(String currency) {
        String info = String.format("https://api.exchangerate.host/latest?base=%s", this.currency);
        String ratesJson = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(info).openConnection().getInputStream()));) {
            ratesJson = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Rates rates = gson.fromJson(ratesJson, RatesDTO.class).rates;
        Field field;
        Object value = null;
        try {
            field = rates.getClass().getDeclaredField(currency);
            field.setAccessible(true);
            value = field.get(rates);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (double) value;
    }

    public Double getNBPRate() {

        if (currency.equals("PLN")) {
            return 1.0;
        }

        String info1 = "https://www.nbp.pl/kursy/xml/a067z220406.xml";
        String info2 = "https://www.nbp.pl/kursy/xml/b014z220406.xml";

        String npbRates1 = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(info1).openConnection().getInputStream()))) {
            npbRates1 = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String npbRates2 = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(info2).openConnection().getInputStream()))) {
            npbRates2 = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject xmlJSONObj1 = XML.toJSONObject(npbRates1);
        JSONObject xmlJSONObj2 = XML.toJSONObject(npbRates2);

        JSONArray jsonArray = new JSONArray();
        jsonArray.putAll(xmlJSONObj1.getJSONObject("tabela_kursow").get("pozycja"));
        jsonArray.putAll(xmlJSONObj2.getJSONObject("tabela_kursow").get("pozycja"));

//        System.out.println(jsonArray.toString(4));

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject o = jsonArray.getJSONObject(i);

            if (o.get("kod_waluty").equals(currency)) {
                return o.getDouble("przelicznik") / Double.parseDouble(o.get("kurs_sredni").toString().replace(",","."));
            }
        }

        return -1.0;
    }

    private HashMap<String, String> getHashMap() {
        List<String[]> list = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader("currencies.txt"))) {

            list = bf
                    .lines()
                    .map(l -> l.split("\t"))
                    .map(t -> {
                        String[] str = new String[2];
                        str[0] = t[0];
                        str[1] = t[2];
                        return str;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<String, String> currencies = new HashMap<>();
        list.forEach(t -> currencies.put(t[0], t[1]));

        return currencies;
    }

}


class RatesDTO {
    Rates rates;
}

class Rates {
    double AED;
    double AFN;
    double ALL;
    double AMD;
    double ANG;
    double AOA;
    double ARS;
    double AUD;
    double AWG;
    double AZN;
    double BAM;
    double BBD;
    double BDT;
    double BGN;
    double BHD;
    double BIF;
    double BMD;
    double BND;
    double BOB;
    double BRL;
    double BSD;
    double BTC;
    double BTN;
    double BWP;
    double BYN;
    double BZD;
    double CAD;
    double CDF;
    double CHF;
    double CLF;
    double CLP;
    double CNH;
    double CNY;
    double COP;
    double CRC;
    double CUC;
    double CUP;
    double CVE;
    double CZK;
    double DJF;
    double DKK;
    double DOP;
    double DZD;
    double EGP;
    double ERN;
    double ETB;
    double EUR;
    double FJD;
    double FKP;
    double GBP;
    double GEL;
    double GGP;
    double GHS;
    double GIP;
    double GMD;
    double GNF;
    double GTQ;
    double GYD;
    double HKD;
    double HNL;
    double HRK;
    double HTG;
    double IDR;
    double ILS;
    double IMP;
    double INR;
    double IQD;
    double IRR;
    double ISK;
    double JEP;
    double JMD;
    double JOD;
    double JPY;
    double KES;
    double KGS;
    double KHR;
    double KMF;
    double KPW;
    double KRW;
    double KWD;
    double KYD;
    double KZT;
    double LAK;
    double LBP;
    double LKR;
    double LRD;
    double LSL;
    double LYD;
    double MAD;
    double MDL;
    double MGA;
    double MKD;
    double MMK;
    double MNT;
    double MOP;
    double MRU;
    double MUR;
    double MVR;
    double MWK;
    double MXN;
    double MYR;
    double MZN;
    double NAD;
    double NGN;
    double NIO;
    double NOK;
    double NPR;
    double NZD;
    double OMR;
    double PAB;
    double PEN;
    double PGK;
    double PHP;
    double PKR;
    double PLN;
    double PYG;
    double QAR;
    double RON;
    double RSD;
    double RUB;
    double RWF;
    double SAR;
    double SBD;
    double SCR;
    double SDG;
    double SEK;
    double SGD;
    double SHP;
    double SLL;
    double SOS;
    double SRD;
    double SSP;
    double STD;
    double STN;
    double SVC;
    double SYP;
    double SZL;
    double THB;
    double TJS;
    double TMT;
    double TND;
    double TOP;
    double TRY;
    double TTD;
    double TWD;
    double TZS;
    double UAH;
    double UGX;
    double USD;
    double UYU;
    double UZS;
    double VES;
    double VND;
    double VUV;
    double WST;
    double XAF;
    double XAG;
    double XAU;
    double XCD;
    double XDR;
    double XOF;
    double XPD;
    double XPF;
    double XPT;
    double YER;
    double ZAR;
    double ZMW;
    double ZWL;


}

