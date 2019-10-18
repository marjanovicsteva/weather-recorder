import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.*;

class Main {
  public static void main(String[] args) throws MalformedURLException, IOException, ParseException, SQLException {
    String api = "https://api.openweathermap.org/data/2.5/weather?lat=44.28&lon=19.9&units=metric&mode=json&APPID=";

    /*
     * CITATI .env FAJL ZA TAJNI API kljuc https://openweathermap.org/api
     */
    String apiKey = System.getenv("apiKey");

    api += apiKey;

    /*
     * Kod za pravljenje jednostavnog GET zahteva
     * https://stackoverflow.com/a/11901997
     */
    URL url = new URL(api);
    Scanner skener = new Scanner(url.openStream());
    String odgovor = skener.useDelimiter("\\Z").next();
    skener.close();

    // Konvertovanje JSON stringa u Java objekat

    JSONParser parser = new JSONParser();

    JSONObject jsonObject = (JSONObject) parser.parse(odgovor);
    double temperatura = (Double) ((JSONObject) jsonObject.get("main")).get("temp");

    /**
     * Upisivanje u bazu podataka
     */

    BufferedReader citac = new BufferedReader(new FileReader("./.env"));
    citac.readLine(); // Preskoci prvi red koji predstavlja
    String lozinka = citac.readLine();

    try {
    	Class.forName("com.mysql.jdbc.Driver");
	Connection konekcija = DriverManager.getConnection("jdbc:mysql://localhost:3306/weather?autoReconnect=true&useSSL=false", "weather", lozinka);

	Calendar kalendar = Calendar.getInstance();
	java.sql.Date datum = new java.sql.Date(kalendar.getTime().getTime());

	String izjava = "INSERT INTO `weather` (`temperatura`, `datum`) VALUES (?, ?)";
	PreparedStatement pripremaIzjave = konekcija.prepareStatement(izjava);
	pripremaIzjave.setDouble(1, (Double)temperatura);
	pripremaIzjave.setDate(2, datum);

	pripremaIzjave.execute();

	konekcija.close();
    } catch (ClassNotFoundException e) {
    	System.err.println("ClassNotFoundException: " + e.getMessage());
    }
  }
}
