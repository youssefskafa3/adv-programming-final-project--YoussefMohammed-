package thread;
import model.Student;
import java.util.concurrent.ConcurrentHashMap;
public class GradeCalculatorTask implements Runnable {
    private Student student;
    private ConcurrentHashMap<String, String> results; // shared collection

    public GradeCalculatorTask(Student student, ConcurrentHashMap<String, String> results) {
        this.student = student;
        this.results = results;
    }

    @Override
    public void run() {
        double avg = student.calculateAverage();
        String letterGrade = student.getLetterGrade();
        // تأكد من إضافة الرموز %s و %f و %.2f هنا
        String result = String.format("ID: %s | Name: %s | Avg: %.2f | Letter: %s",
                student.getStudentId(),
                student.getName(),
                avg,
                letterGrade);
        results.put(student.getStudentId(), result);
        System.out.println("Done: " + student.getName());
    }
}