package thread;
import exception.FileProcessingException;
import exception.InvalidDataException;
import model.Student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
public class StudentFileReader extends Thread
{
    private String filename;
    private ThreadSafeGradeRepository repository;
    public StudentFileReader(String filename, ThreadSafeGradeRepository repository)
    {
        this.filename = filename;
        this.repository = repository;
    }
    @Override
    public void run()
    {
        System.out.println("Starting to read: " + filename);
        File file = new File(filename);
        if (!file.exists())
        {
            System.out.println("File not found: " + filename);
            return;
        }
        try (Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (line == null)
                {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length < 3)
                {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                try {
                    String id = parts[0];
                    String name = parts[1];
                    Student student = new Student(id, name);
                    for (int i = 2; i < parts.length; i++) {
                        try {
                            double grade = Double.parseDouble(parts[i]);
                            student.addGrade(grade);
                        } catch (NumberFormatException e) {
                            System.out.println(" Invalid grade value: " + parts[i]);
                        }
                    }
                    repository.addStudent(student);
                }
                catch (InvalidDataException e)
                {
                    System.out.println(" Invalid student data: " + e.getMessage());
                }
            }
        }
        catch (FileNotFoundException e)
        {
            throw new FileProcessingException("File not found: " + filename, e);
        }
        catch (IOException e)
        {
            throw new FileProcessingException("Error reading file: " + filename, e);
        }
        System.out.println("Finished reading: " + filename);
    }
}
