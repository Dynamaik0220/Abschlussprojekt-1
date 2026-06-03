import java.io.*;
import java.util.Collection;

public class FileHandler {
    // Speichert alle Studenten in einer Datei namens "students.csv"
    public void saveStudents(Collection<Student> students) {

        // Das try(...) öffnet die Datei und schließt sie am Ende automatisch
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.csv"))) {

            for (Student s : students) {
                // Wir bauen den String: "ID,Name" (z.B. "1,Maik")
                String line = s.getId() + "," + s.getName();

                writer.write(line);   // Schreibt die Zeile in die Datei
                writer.newLine();     // Macht einen Zeilenumbruch (Enter)
            }
            System.out.println("Daten erfolgreich gespeichert!");

        } catch (IOException e) {
            System.out.println("Kritischer Fehler beim Speichern: " + e.getMessage());
        }
    }

    public void loadStudents(Manager manager) {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.csv"))) {

            String line;
            // Wir lesen so lange, bis die Datei zu Ende ist (null)
            while ((line = reader.readLine()) != null) {

                // Wir zerschneiden die Zeile "1,Maik" am Komma
                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];

                // Wir übergeben ID und Name an die Uni, um den Studenten zu rekonstruieren
                manager.loadStudentFromDatabase(id, name);
            }
            System.out.println("Daten erfolgreich geladen!");

        } catch (IOException e) {
            System.out.println("Keine alte Speicherdatei gefunden. Starte mit leerem System.");
        }
    }
}
