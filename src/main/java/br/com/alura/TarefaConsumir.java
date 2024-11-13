package br.com.alura;

import java.util.concurrent.BlockingQueue;

public class TarefaConsumir implements Runnable {
    private BlockingQueue<String> fila;

    public TarefaConsumir(BlockingQueue<String> fila) {
        this.fila = fila;
    }

    @Override
    public void run() {
        try {
            String comando;

            while((comando = fila.take()) != null) {
                System.out.println("Consumindo comando " + comando + " - " + Thread.currentThread().getName());

                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
