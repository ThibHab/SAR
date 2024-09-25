package task1;

import java.util.concurrent.Semaphore;

public class RendezVous {
    Semaphore Servsem = new Semaphore(0);
    Semaphore Clisem = new Semaphore(0);
    Broker Cli;

    public void accept() throws InterruptedException{
        Servsem.release();
        Clisem.acquire();
    }

    public void connect() throws InterruptedException{
        Clisem.release();
        Servsem.acquire();
    }

}
