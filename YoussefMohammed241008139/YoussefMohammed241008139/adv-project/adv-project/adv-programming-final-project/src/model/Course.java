package model;
import exception.InvalidDataException;
import java.util.ArrayList;
public class Course
{
    private String courseCode;
    private String courseName;
    private ArrayList<Student> students;
    public Course(String courseCode, String courseName) throws InvalidDataException
    {
        if (courseCode == null)
        {
            throw new InvalidDataException("Course code cannot be empty");
        }
        if (courseName == null)
        {
            throw new InvalidDataException("Course name cannot be empty");
        }
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.students = new ArrayList<>();
    }
    public String getCourseCode()
    {

        return courseCode;
    }
    public String getCourseName()
    {

        return courseName;
    }
    public ArrayList<Student> getStudents()
    {

        return students;
    }
    public void addStudent(Student s)
    {

        students.add(s);
    }
    public double getClassAverage()
    {
        if (students.isEmpty())
        {
            return 0.0;
        }
        double total = 0;
        for (int i = 0; i < students.size(); i++)
        {
            Student s = students.get(i);
            total += s.calculateAverage();
        }
        return total / students.size();
    }
    public Student getTopStudent()
    {
        if (students.isEmpty())
        {
            return null;
        }
        Student top = students.get(0);
        for (int i = 0; i < students.size(); i++)
        {
            Student s = students.get(i);
            if (s.calculateAverage() > top.calculateAverage())
            {
                top = s;
            }
        }
        return top;
    }
    public String generateGradeReport()
    {
        StringBuilder report = new StringBuilder();
        report.append("GRADE SUMMARY REPORT");
        report.append("Course: ").append(courseCode).append(" ").append(courseName).append("\n");
        report.append("Total Students: ").append(students.size()).append("");
        report.append(String.format("Class Average: ", getClassAverage()));
        report.append("");
        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;
        for (int i = 0; i < students.size(); i++)
        {
            Student s = students.get(i);
            String letter = s.getLetterGrade();
            if (letter.equals("A")) countA++;
            else if (letter.equals("B")) countB++;
            else if (letter.equals("C")) countC++;
            else if (letter.equals("D")) countD++;
            else countF++;
        }
        int total = students.size();
        report.append("Grade Distribution:");
        report.append(String.format("A: %d students", countA, total > 0 ? countA * 100 / total : 0));
        report.append(String.format("B: %d students ", countB, total > 0 ? countB * 100 / total : 0));
        report.append(String.format("C: %d students ", countC, total > 0 ? countC * 100 / total : 0));
        report.append(String.format("D: %d students ", countD, total > 0 ? countD * 100 / total : 0));
        report.append(String.format("F: %d students ", countF, total > 0 ? countF * 100 / total : 0));
        Student top = getTopStudent();
        if (top != null)
        {
            report.append(String.format("Top Student: ",
                    top.getStudentId(), top.getName(), top.calculateAverage()));
        }
        return report.toString();
    }
}
