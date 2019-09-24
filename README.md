# weather Recorder

Final version of this project is supposed to represent a Java app made out of several classes that are responsible for:
 - **API comminication** -- With [OpenWeatherMap](https://openweathermap.org)
 - **Database manipulation** -- Class that will run every half-an-hour (CRON) so that there are 48 records each day
 - **Analysis & Prediction** -- Class that will calculate useful facts by operating with collected weather data and try to **very** roughly predict near future weather
 - **Front end** -- Class that will represent main interaction between user and this program through CLI

## Instalation

### Dependencies

This application depends on `json-simple-1.1.1.jar` ([download](http://www.java2s.com/Code/Jar/j/Downloadjsonsimple111jar.htm)) and `mysql-connector-java-5.1.48.jar` ([download](https://dev.mysql.com/downloads/connector/j/5.1.html)). Currently, `build.sh` script implies that those `.jar` files are in the same directory as `Main.java` (libraries will be moved to `lib/` folder in future changes);

### Database

Application connects to MySQL 5.7 (will be upgraded to 8.1 in future changes) database named `weather` as a user also called `weather` and it's corresponding password.

### .env file

You need to manually create `.env` file which will store your OWM API key (line 1) and database password (line 2). Program will automatically read those values and insert them in the program.

### Build

After you went through all these steps, just run `build.sh` script and the program will handle build by itself. Script also runs the program, which is for now stored in the same directory as `Main.java` (will be moved to `bin/` folder in future changes).

