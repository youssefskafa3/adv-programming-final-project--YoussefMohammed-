package io;
import model.Course;
import model.Student;

import java.io.FileWriter;
import java.io.IOException;
public class GradeReportGenerator
{
    private FileWriter writer;
    public GradeReportGenerator(String outputPath)
    {
        try
        {
            this.writer = new FileWriter(outputPath);
        }
        catch (IOException e)
        {
            System.out.println("Could not open file: " + outputPath);
        }
    }
    public void writeReport(Course course)
    {
        if (writer == null)
        {
            System.out.println(" Writer is not initialized.");
            return;
        }
        try
        {
            writer.write("GRADE SUMMARY REPORT");
            writer.write("Course: " + course.getCourseCode() + course.getCourseName() );
            writer.write("Total Students: " + course.getStudents().size());
            writer.write(String.format("Class Average:", course.getClassAverage()));
            writer.write("Student Details:");
            for (Student s : course.getStudents())
            {
                writer.write("  " + s.toString() );
            }
            Student top = course.getTopStudent();
            if (top != null)
            {
                writer.write(String.format("Top Student: ",
                        top.getStudentId(), top.getName(), top.calculateAverage()));
            }
            writer.flush();
            System.out.println("Report written successfully.");
        }
        catch (IOException e)
        {
            System.out.println("Error writing report: " + e.getMessage());
        }
    }
    public void close()
    {
        if (writer != null)
        {
            try
            {
                writer.close();
            }
            catch (IOException e)
            {
                System.out.println("Error closing writer.");
            }
        }
    }
}
