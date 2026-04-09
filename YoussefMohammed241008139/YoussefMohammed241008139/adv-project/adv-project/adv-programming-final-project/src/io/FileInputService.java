package io;
import exception.InvalidDataException;
import model.Student;
import thread.StudentFileReader;
import thread.ThreadSafeGradeRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class FileInputService
{
    public ArrayList<Student> readStudentFile(String filename)
    {
        ArrayList<Student> list = new ArrayList<>();
        File file = new File(filename);
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
                if (parts.length < 3) continue;
                try
                {
                    String id   = parts[0];
                    String name = parts[1]  ;
                    Student student = new Student(id, name);
                    for (int i = 2; i < parts.length; i++)
                    {
                        try
                        {
                            student.addGrade(Double.parseDouble(parts[i]));
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println(" Skipping bad grade: " + parts[i]);
                        }
                    }
                    list.add(student);
                }
                catch (InvalidDataException e)
                {
                    System.out.println("Skipping student: " + e.getMessage());
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println(" File not found: " + filename);
        }
        return list;
    }
    public ArrayList<Student> readMultipleFiles(String[] filenames)
    {
        ThreadSafeGradeRepository repository = new ThreadSafeGradeRepository();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < filenames.length; i++)
        {
            String filename = filenames[i];
            StudentFileReader reader = new StudentFileReader(filename, repository);
            threads.add(reader);
            reader.start();
        }
        for (int i = 0; i < threads.size(); i++)
        {
            Thread t = threads.get(i);
            try
            {
                t.join();
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted.");
            }
        }
        repository.setDataReady();
        return new ArrayList<>(repository.getAllStudents().values());
    }
}
