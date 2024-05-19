rm *.jar
javac -d bin src/java/**/*.java
jar cfm K-catan.jar manifest.txt -C bin . -C src/ressources .
java -jar K-catan.jar