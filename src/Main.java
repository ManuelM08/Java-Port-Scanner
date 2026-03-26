import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Main {
    public static void main(String[] args) {
        System.out.println("---==== Scanner Porte ====---");

        String ip = "127.0.0.1";

        final int timeout = 200;
        final int N = 70000;
        final int thread = 100;


        ExecutorService executor = Executors.newFixedThreadPool(thread);
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

        final AtomicInteger completati = new AtomicInteger(0);

        for (int port = 1; port <= N; port++)  {

            final int p = port;     // La lambda executor.submit per i thread lavora solo con variabili costanti

            executor.submit(() -> {
                try {
                    Socket socket = new Socket();       // Apre la connessione tcp con il socket
                    socket.connect(new InetSocketAddress(ip, p), timeout);      // Si connette all'innet socket formato da ip address e porta con un timeout [200ms]
                    socket.close();     // Chiude la connessione tcp con il socket

                    queue.add(p);


                } catch (Exception e) {
                } finally {
                    int attuali = completati.incrementAndGet();     // Incremento atomico sul thread

                    if (attuali % 100 == 0 || attuali == N){
                        System.out.print("\rProgresso : " + (attuali * 100 / N) + "%");
                    }
                }
            });
        }

        executor.shutdown();


        try {
        if(!executor.awaitTermination(2,TimeUnit.MINUTES)) {
            System.out.println("Timeout scaduto");
        }
        } catch (Exception e) {
            System.out.println("Scan interrotto");
        }

        System.out.println("\n");


        System.out.println("Scansione completata. \n" + queue);
        new java.util.Scanner(System.in).nextLine();

    }
}