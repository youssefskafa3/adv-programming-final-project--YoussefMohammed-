package io;
import model.Course;
import model.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
public class ReportGenerator
{
    public void generateSummaryReport(Course course, String outputFile)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile)))
        {
            writer.write("GRADE SUMMARY REPORT\n");
            writer.write("Course: " + course.getCourseCode() + "\n");
            writer.printf("Total Students:", course.getStudents().size());
            writer.printf("Class Average: ", course.getClassAverage());
            Map<String, Integer> dist = getDistribution(course);
            int total = course.getStudents().size();
            writer.println("Grade Distribution:");
            String[] letters = {"A", "B", "C", "D", "F"};
            for (int i = 0; i < letters.length; i++)
            {
                String letter = letters[i];
                int count   = dist.getOrDefault(letter, 0);
                int percent = (total > 0) ? (count * 100 / total) : 0;
                writer.printf(" students ", letter, count, percent);
            }
            Student top = course.getTopStudent();
            if (top != null)
            {
                writer.printf("%nTop Student: ",
                        top.getStudentId(), top.getName(), top.calculateAverage());
            }
            System.out.println(" Summary saved to: " + outputFile);
        }
        catch (IOException e)
        {
            System.out.println(" Could not write summary: " + e.getMessage());
        }
    }
    public void generateGradeDistribution(Course course, String outputFile)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile)))
        {
            writer.println("Grade Distribution Histogram");
            Map<String, Integer> dist = getDistribution(course);
            String[] letters = {"A", "B", "C", "D", "F"};
            for (int i = 0; i < letters.length; i++)
            {
                String letter = letters[i];
                int count = dist.getOrDefault(letter, 0);
                String bar = "".repeat(count);
                writer.printf(" students", letter, bar, count);
            }
            System.out.println(" Distribution saved to: " + outputFile);
        }
        catch (IOException e)
        {
            System.out.println(" Could not write distribution: " + e.getMessage());
        }
    }
    public void generateErrorLog(String errorMessage, String logFile)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true)))
        {
            writer.println( LocalDateTime.now() +  errorMessage);
            System.out.println("Error logged to: " + logFile);
        }
        catch (IOException e)
        {
            System.out.println(" Could not write to log: " + e.getMessage());
        }
    }
    private Map<String, Integer> getDistribution(Course course)
    {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < course.getStudents().size(); i++)
        {
            Student s = course.getStudents().get(i);
            String letter = s.getLetterGrade();
            map.put(letter, map.getOrDefault(letter, 0) + 1);
        }
        return map;
    }
}
