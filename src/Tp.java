import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Tp {
    //attributes
    private long mins;
    private ExecutorService executor;

    //constructor
    public Tp(long mins, ExecutorService executor) {
        setMins(mins);
        setExecutor(executor);
    }


    //setters & getters
    public void setMins(long mins) {
        if (mins <= 0) {
            throw new IllegalArgumentException("Timeout < = 0");
        }

        this.mins = mins;
    }

    public void setExecutor(ExecutorService executor) {
        if (executor == null) {
            throw new IllegalArgumentException("Executor is required");
        }
        this.executor = executor;
    }

    public double getMins() {
        return mins;
    }


    //methods
    public void terminaTimeout() {
        executor.shutdown();
    }

    public void resTimeout() {
        try {
            if(!this.executor.awaitTermination(this.mins, TimeUnit.MINUTES)) {
                System.out.println("Timeout finished; Port ignored");
            }
        } catch (Exception e) {
            System.out.println("Scan interrupted");
        }
    }


}
