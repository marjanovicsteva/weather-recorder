import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.Arrays;

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
    var apiKey = System.getenv("apiKey");

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
    System.out.println(((JSONObject) jsonObject.get("main")).get("temp"));
	
    /**
     * Upisivanje u bazu podataka
     */

    BufferedReader citac = new BufferedReader(new FileReader("./.env"));
    citac.readLine(); // Preskoci prvi red koji predstavlja 
    String lozinka = citac.readLine();

    try {
    	Class.forName("com.mysql.jdbc.Driver");
	Connection konekcija = DriverManager.getConnection("jdbc:mysql://localhost:3306/weather?autoReconnect=true&useSSL=false", "weather", lozinka);
	Statement izjava = konekcija.createStatement();
	ResultSet rs = izjava.executeQuery("SELECT * FROM weather");
	/**
	 * Ispis SQL tabele naucen sa sledeceg linka
	 * https://stackoverflow.com/a/28165814
	 */
	ResultSetMetaData rsmd = rs.getMetaData();
	int brojKolona = rsmd.getColumnCount();
	while (rs.next()) {
	    for (int i = 1; i <= brojKolona; i++) {
	        if (i > 1) System.out.print(",  ");
	        String columnValue = rs.getString(i);
	        System.out.print(columnValue + " " + rsmd.getColumnName(i));
	   }

	   System.out.println("");
	}
	konekcija.close();
    } catch (ClassNotFoundException e) {
    	System.err.println("ClassNotFoundException: " + e.getMessage());
    }
  }
}
