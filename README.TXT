In order to run the .jar file you need to have JRE or JDK 16 installed on your local machine

You then also need to download the JavaFX library manually. you need to choose a version that works with your OS

You can then run the program with a command like this:

Java -jar --module-path "Path to JavaFX lib folder on your local machine" --add-modules javafx.controls,javafx.fxml -jar PongChamp1.jar

If this command doesn't work try using your JDK (the same as the one Intellij uses)

Example:

/Users/peyman/Library/Java/JavaVirtualMachines/azul-16.0.2/Contents/Home/bin/java --module-path "/Users/peyman/Downloads/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml -jar PongChamp1.jar
