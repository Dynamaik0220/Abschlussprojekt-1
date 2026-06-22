package abschlussprojekt;

import java.io.*;
import java.util.Collection;

public class FileHandler {
    public void saveStudents(Collection<Student> students) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.csv"))) {

            for (Student s : students) {
                String line = s.getID() + "," + s.getName();

                writer.write(line);
                writer.newLine();
            }
            System.out.println("Students saved successfully!");

        } catch (IOException e) {
            System.out.println("Critical error during saving of students: " + e.getMessage());
        }
    }

    public void loadStudents(Manager manager) {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.csv"))) {

            String line;

            while ((line = reader.readLine()) != null) {


                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];

                manager.loadStudentFromDatabase(id, name);
            }
            System.out.println("Students successfully loaded!");

        } catch (IOException e) {
            System.out.println("No saved students found.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Critical error during loading of students: " + e.getMessage());
        }
    }

    public void saveModules(Collection<Module> modules) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("modules.csv"))) {

            for (Module m : modules) {
                String line = m.getID() + "," + m.getName();

                writer.write(line);
                writer.newLine();
            }
            System.out.println("Modules saved successfully!");

        } catch (IOException e) {
            System.out.println("Critical error during saving of modules: " + e.getMessage());
        }
    }

    public void loadModules(Manager manager) {
        try (BufferedReader reader = new BufferedReader(new FileReader("modules.csv"))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];

                manager.loadModuleFromDatabase(id, name);
            }
            System.out.println("Modules successfully loaded!");

        } catch (IOException e) {
            System.out.println("No saved modules found.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Critical error during loading of models: " + e.getMessage());
        }
    }

    public void saveEnrollments(Collection<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("enrollments.csv"))) {
            for (Student s : students) {
                for (Enrollment e : s.getEnrollments()) {
                    int studentID = s.getID();
                    int moduleID = e.getModule().getID();
                    Double grade = e.getGrade();
                    String line;

                    if (grade == null) {
                        line = studentID + "," + moduleID + ",null";
                    } else {
                        line = studentID + "," + moduleID + "," + grade;
                    }

                    writer.write(line);
                    writer.newLine();
                }
            }
            System.out.println("Enrollments saved successfully!");

        } catch (IOException e) {
            System.out.println("Critical error during saving of enrollments: " + e.getMessage());
        }
    }

    public void loadEnrollments(Manager manager){
        try (BufferedReader reader = new BufferedReader(new FileReader("enrollments.csv"))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                int studentID = Integer.parseInt(parts[0]);
                int moduleID = Integer.parseInt(parts[1]);
                String gradeString = parts[2];
                Double grade = null;
                if (!gradeString.equals("null")) {
                    grade = Double.parseDouble(gradeString);
                }

                manager.loadEnrollmentFromDatabase(studentID, moduleID, grade);
            }
            System.out.println("Enrollments successfully loaded!");
        } catch (IOException e) {
            System.out.println("No saved enrollments found.");
        } catch (StudentNotFoundException | ModuleNotFoundException | ArrayIndexOutOfBoundsException e){
            System.out.println("Critical error during loading of enrollments: " + e.getMessage());
        }
    }
}
