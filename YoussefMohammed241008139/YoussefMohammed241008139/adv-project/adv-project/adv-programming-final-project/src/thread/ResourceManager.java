package thread;
import java.util.concurrent.locks.ReentrantLock;
public class ResourceManager
{
    private final ReentrantLock gradeLock  = new ReentrantLock();
    private final ReentrantLock reportLock = new ReentrantLock();
    public void processAndReport(String threadName)
    {
        gradeLock.lock();
        try
        {
            System.out.println(threadName + " Got gradeLock");
            reportLock.lock();
            try
            {
                System.out.println( threadName + " Got reportLock ");
                Thread.sleep(100);
                System.out.println( threadName + "Done");
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
            finally
            {
                reportLock.unlock();
            }
        }
        finally
        {
            gradeLock.unlock();
        }
    }
    public void demonstratePrevention()
    {
        System.out.println(" Resource Position ");
        Thread t1 = new Thread(() -> processAndReport("thread1"));
        Thread t2 = new Thread(() -> processAndReport("thread2"));
        t1.start();
        t2.start();
        try
        {
            t1.join();
            t2.join();
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        System.out.println("No Deadlock Occurred!");
    }
}
