package processor;
import java.util.ArrayList;
public class GradeAnalyzer extends GradeProcessor
{
    @Override
    public String processGrades(ArrayList<Double> grades)
    {
        if (grades == null)
        {
            return "No grades available.";
        }
        double highest = grades.get(0);
        double lowest  = grades.get(0);
        double sum     = 0;
        for (int i = 0; i < grades.size(); i++)
        {
            double grade = grades.get(i);
            if (grade > highest) highest = grade;
            if (grade < lowest)  lowest  = grade;
            sum += grade;
        }
        double average = sum / grades.size();
        double stdDev  = calculateStdDev(grades, average);
        return String.format(
                "Highest: %.2f && Lowest: %.2f && Average: %.2f && Dev: %.2f",
                highest, lowest, average, stdDev
        );
    }
    private double calculateStdDev(ArrayList<Double> grades, double average)
    {
        double sumSquares = 0;
        for (int i = 0; i < grades.size(); i++)
        {
            double grade = grades.get(i);
            sumSquares += Math.pow(grade - average, 2);
        }
        return Math.sqrt(sumSquares / grades.size());
    }
}
