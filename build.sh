export apiKey=$(head -n 1 .env)
javac -classpath .:json-simple-1.1.1.jar:mysql-connector-5.1.48.jar Main.java
java -classpath .:json-simple-1.1.1.jar:mysql-connector-5.1.48.jar Main

