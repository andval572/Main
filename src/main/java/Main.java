import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) { //CREAR HILOS

        for (int i = 1; i <= 20 ; i++) {
            Hilos hilo = new Hilos(0);
            hilo.setName("CAMION NUMERO "+ i );
            hilo.start();
        }

    }

}

class Hilos extends Thread {

    int llegada;
    public Hilos( int llegada) {
        this.llegada=llegada;
    }

    @Override
    public void run() {
        Descargar.solicitarAcceso(this);
    }
}

class Descargar {

    private static final int NUM_CAMIONES_EN_DARSENAS = 5;
    private static final int DESCARGA = 2;

    static Semaphore darsena = new Semaphore(NUM_CAMIONES_EN_DARSENAS);
    static Semaphore mozo = new Semaphore(DESCARGA,true);


    public static void solicitarAcceso(Hilos hilo) {

        try {
            darsena.acquire(1);
            solicitarDescarga(hilo);
            Thread.sleep(3000);
            darsena.release(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void solicitarDescarga(Hilos hilo) {

        System.out.println("EL " + hilo.getName() + " ENTRA EN SU DARSENA");

        try {
            mozo.acquire(1);
            descarga(hilo);
            Thread.sleep(3000);
            mozo.release(1);

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }

    public static void descarga(Hilos hilo) throws InterruptedException {

        System.out.println("EL CAMION " + hilo.getName() + " EMPIEZA DESCARGA ");

        Thread.sleep((new Random().nextInt(1) +5)  * 1000);

        System.out.println("EL CAMION " + hilo.getName() + " TERMINA DESCARGA Y LIBERA UN SITIO");




    }

}