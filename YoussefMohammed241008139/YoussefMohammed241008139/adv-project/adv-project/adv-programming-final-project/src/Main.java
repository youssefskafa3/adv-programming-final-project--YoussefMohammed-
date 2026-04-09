import exception.InvalidDataException;
import exception.InvalidGradeException;
import io.FileInputService;
import io.GradeReportGenerator;
import io.ReportGenerator;
import model.Course;
import model.Student;
import processor.GradeAnalyzer;
import thread.GradeCalculatorTask;
import thread.ResourceManager;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Student Grade Processing System");
        createSampleFiles();
        System.out.println("Reading student files");
        FileInputService inputService = new FileInputService();
        String[] files = {
            "data/students_1.txt",
            "data/students_2.txt",
            "data/students_3.txt"
        };
        ArrayList<Student> allStudents = inputService.readMultipleFiles(files);
        System.out.println("Total students loaded: " + allStudents.size() );

        Course course = null;
        try
        {
            course = new Course("CS001", "Advanced Programming");
            for (int i = 0; i < allStudents.size(); i++)
            {
                Student s = allStudents.get(i);
                course.addStudent(s);
            }
        }
        catch (InvalidDataException e)
        {
            System.out.println("Course error: " + e.getMessage());
            return;
        }
        System.out.println(" Validating and Analyzing Grades ");
        GradeAnalyzer analyzer = new GradeAnalyzer();
        for (int i = 0; i < course.getStudents().size(); i++)
        {
            Student s = course.getStudents().get(i);
            try
            {
                analyzer.validateGrades(s.getGrades());
                String stats = analyzer.processGrades(s.getGrades());
                System.out.println(s.getName() + " = " + stats);
            }
            catch (InvalidGradeException e)
            {
                System.out.println("Invalid grade for" + s.getName() + ": " + e.getMessage());
            }
        }
        System.out.println("Running Grade Calculator Threads");
        ConcurrentHashMap<String, String> results = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < course.getStudents().size(); i++)
        {
            Student s = course.getStudents().get(i);
            executor.submit(new GradeCalculatorTask(s, results));
        }
        executor.shutdown();
        try
        {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        System.out.println("Calculation Results");
        ArrayList<String> values = new ArrayList<>(results.values());
        for (int i = 0; i < values.size(); i++)
        {
            String r = values.get(i);
            System.out.println(r);
        }
        System.out.println("Generating Reports");
        ReportGenerator reporter = new ReportGenerator();
        reporter.generateSummaryReport(course, "reports/summary_report.txt");
        reporter.generateGradeDistribution(course, "reports/grade_distribution.txt");
        reporter.generateErrorLog("System started successfully.", "reports/error_log.txt");
        GradeReportGenerator gradeReport = new GradeReportGenerator("reports/grade_report.txt");
        gradeReport.writeReport(course);
        gradeReport.close();
        System.out.println( course.generateGradeReport());
        ResourceManager manager = new ResourceManager();
        manager.demonstratePrevention();
        System.out.println(" Done reports");
    }
    private static void createSampleFiles()
    {
        try
        {
            new java.io.File("data").mkdirs();
            new java.io.File("reports").mkdirs();
            java.io.PrintWriter w1 = new java.io.PrintWriter("data/students_1.txt");
            w1.println("1111,Asaad Rami,85,90,78,92");
            w1.println("2222,Mostafa Ahmed,88,76,95,89");
            w1.println("3333,Belal Hasaan,72,68,74,70");
            w1.close();
            java.io.PrintWriter w2 = new java.io.PrintWriter("data/students_2.txt");
            w2.println("4444,Ziad Tarek,55,60,58,50");
            w2.println("5555,Abdelrahman Ali,91,95,88,97");
            w2.println("6666,Yassin Hassan,63,67,70,65");
            w2.close();
            java.io.PrintWriter w3 = new java.io.PrintWriter("data/students_3.txt");
            w3.println("7777,Youssef Mohammed,45,50,40,48");
            w3.println("8888,Farouk osama,80,85,82,88");
            w3.println("9999,Abdallah Hossam ,93,97,91,95");
            w3.close();
            System.out.println("Sample files created in data");
        }
        catch (java.io.IOException e)
        {
            System.out.println("Could not create sample files: " + e.getMessage());
        }
    }
}
