FAILURE=false

if [[ -f ".env" ]]; then
    export apiKey=$(head -n 1 .env)
else
    echo -e "Please create .env file with OpenWeatherMap API key as line 1 and database password as line 2 \n"
    FAILURE=true
fi

# Download the dependencies
if [ ! -f "./json-simple-1.1.1.jar" ]; then
    echo "Downloading Simple JSON..."
    wget https://cdn.stevit.rs/java/json-simple-1.1.1.jar || (echo "Couldn't download Simple JSON ❌"; FAILURE=true; )
    echo -e "JSON Simple successfully downloaded ✔️ \n"
fi
if [ ! -f "./mysql-connector-5.1.48.jar" ]; then
    echo "Downloading MySQL Connector/J..."
    wget -O mysql-connector-5.1.48.jar https://cdn.stevit.rs/java/mysql-connector-java-5.1.48.jar || (echo "Couldn't download MySQL Connector/J ❌"; FAILURE=true; )
    echo -e "MySQL Connector/J successfully downloaded ✔️ \n"
fi

# Stop the script if there was problem with setting
# environmental variable or downloading dependencies
if [[ $FAILURE = true ]]; then
    exit 1
fi

# Build and run the program
javac -classpath .:json-simple-1.1.1.jar:mysql-connector-5.1.48.jar Main.java
java -classpath .:json-simple-1.1.1.jar:mysql-connector-5.1.48.jar Main
