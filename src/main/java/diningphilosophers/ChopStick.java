package diningphilosophers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChopStick {

    private static int stickCount = 0;
    private boolean iAmFree = true;
    private final int myNumber;
    private final Lock lock = new ReentrantLock();


    public ChopStick() {
        myNumber = ++stickCount;
    }

    public boolean tryTake(int delay) throws InterruptedException {
        if (lock.tryLock()) {
            try {
                return true; // Succès
            } finally {
                // Pas de libération ici, car le verrou doit rester pris jusqu'à `release`
            }
        }

        // Si le verrou n'est pas disponible, on attend pendant le délai spécifié
        if (lock.tryLock(delay, java.util.concurrent.TimeUnit.MILLISECONDS)) {
            try {
                return true; // Succès
            } finally {
                // Pas de libération ici non plus
            }
        }

        return false; // Échec
    }

    public void release() {
        lock.unlock(); // Libération du verrou
        System.out.println("Stick " + myNumber + " Released");
    }

    @Override
    public String toString() {
        return "Stick#" + myNumber;
    }
}
