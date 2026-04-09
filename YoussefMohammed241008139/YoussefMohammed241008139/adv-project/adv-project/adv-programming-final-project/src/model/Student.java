package model;
import exception.InvalidDataException;
import java.util.ArrayList;
public class Student
{
    private String studentId;
    private String name;
    private ArrayList<Double> grades;
    public Student(String studentId, String name) throws InvalidDataException
    {
        if (studentId == null)
        {
            throw new InvalidDataException("Student ID cannot be null or empty");
        }
        if (name == null)
        {
            throw new InvalidDataException("Student name cannot be null or empty");
        }
        this.studentId = studentId;
        this.name = name;
        this.grades = new ArrayList<>();
    }
    public String getStudentId()
    {

        return studentId;
    }
    public String getName()
    {

        return name;
    }
    public ArrayList<Double> getGrades()
    {

        return grades;
    }
    public void setStudentId(String studentId) throws InvalidDataException
    {
        if (studentId == null)
        {
            throw new InvalidDataException("Student ID cannot be empty");
        }
        this.studentId = studentId;
    }
    public void setName(String name) throws InvalidDataException
    {
        if (name == null)
        {
            throw new InvalidDataException("Student name cannot be null or empty");
        }
        this.name = name;
    }
    public void setGrades(ArrayList<Double> grades)
    {

        this.grades = grades;
    }
    public void addGrade(double grade)
    {

        grades.add(grade);
    }
    public double calculateAverage()
    {
        if (grades.isEmpty())
        {
            return 0.0;
        }
        double sum = 0;
        for (int i = 0; i < grades.size(); i++)
        {
            double grade = grades.get(i);
            sum += grade;
        }
        return sum / grades.size();
    }
    public String getLetterGrade()
    {
        double avg = calculateAverage();
        if (avg >= 90) return "A";
        else if (avg >= 80) return "B";
        else if (avg >= 70) return "C";
        else if (avg >= 60) return "D";
        else return "F";
    }
    @Override
    public String toString()
    {
        return "Student{" +
               "ID='" + studentId  +
               ", Name='" + name  +
               ", Average=" + String.format("Avg", calculateAverage()) +
               ", Grade=" + getLetterGrade() +
               "}";
    }
}
