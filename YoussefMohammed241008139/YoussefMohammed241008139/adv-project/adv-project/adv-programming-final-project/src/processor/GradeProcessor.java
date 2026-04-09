package processor;
import exception.InvalidGradeException;
import java.util.ArrayList;
public abstract class GradeProcessor
{
    public abstract String processGrades(ArrayList<Double> grades);
    public void validateGrades(ArrayList<Double> grades) throws InvalidGradeException
    {
        for (int i = 0; i < grades.size(); i++)
        {
            double grade = grades.get(i);
            if (grade < 0 || grade > 100)
            {
                throw new InvalidGradeException("Grade " + grade + " is :");
            }
        }
    }
}
