package thread;
import model.Student;
import java.util.concurrent.ConcurrentHashMap;
public class ThreadSafeGradeRepository
{
    private ConcurrentHashMap<String, Student> studentMap = new ConcurrentHashMap<>();
    private boolean dataReady = false;
    public synchronized void addStudent(Student student)
    {
        studentMap.put(student.getStudentId(), student);
        System.out.println("Name: " + student.getName());
        notifyAll();
    }
    public synchronized Student waitForStudent(String id) throws InterruptedException
    {
        while (!studentMap.containsKey(id) && !dataReady)
        {
            wait();
        }
        return studentMap.get(id);
    }
    public synchronized void setDataReady()
    {
        dataReady = true;
        notifyAll();
    }
    public ConcurrentHashMap<String, Student> getAllStudents()
    {

        return studentMap;
    }
    public int size()
    {

        return studentMap.size();
    }
}
