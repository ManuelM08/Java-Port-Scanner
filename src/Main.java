import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);    //input dati

        System.out.println("---==== Port Scanner ====---");

        String ip = "127.0.0.1";

        int numP = 0;
        while (numP <= 0) {
            System.out.println("Enter maximum port number to scan (1-65535): ");
            if (sc.hasNextInt()) {
                numP = sc.nextInt();
            } else {
                System.out.println("Error: Enter a valid number.");
                sc.next();
            }
        }

        final int timeout = 200;
        final int N = numP;
        final int threads = 100;


        ExecutorService executor = Executors.newFixedThreadPool(threads);
        Tp t = new Tp(2,executor);

        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

        final AtomicInteger completati = new AtomicInteger(0);

        for (int port = 1; port <= N; port++)  {

            final int p = port;     //la lambda executor.submit per i thread lavora solo con variabili costanti

            executor.submit(() -> {
                try {
                    Socket socket = new Socket();       //apre la connessione tcp con il socket
                    socket.connect(new InetSocketAddress(ip, p), timeout);      //si connette all'innet socket formato da ip address e porta con un timeout [200ms]
                    socket.close();     //chiude la connessione tcp con il socket

                    queue.add(p);


                } catch (Exception e) {
                } finally {
                    int attuali = completati.incrementAndGet();     //incremento atomico sul thread

                    if (attuali % 100 == 0 || attuali == N){
                        System.out.print("\rProgress : " + (attuali * 100 / N) + "%");
                    }
                }
            });
        }

        t.terminaTimeout();


        t.resTimeout();

        System.out.println("\n");

        ArrayList<Integer> openPorts = new ArrayList(queue);
        openPorts.sort(null);


        System.out.println("Scan completed. \n" + openPorts);


    }
}