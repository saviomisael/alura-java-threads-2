package br.com.alura.experimento;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class TesteFila {
    public static void main(String[] args) throws InterruptedException {
        Queue<String> fila = new LinkedList<>();

        fila.offer("c1");
        fila.offer("c2");
        fila.offer("c3");

        System.out.println(fila.peek());
        System.out.println(fila.poll());
        System.out.println(fila.poll());
        System.out.println(fila.poll());
        System.out.println(fila.poll());

        System.out.println(fila.size());

        BlockingQueue<String> fila2 = new ArrayBlockingQueue<>(3);
        fila2.put("c1");
        fila2.put("c2");
        fila2.put("c3");

        System.out.println(fila2.take());
        System.out.println(fila2.take());
        System.out.println(fila2.take());
//        System.out.println(fila2.take()); // bloqueia o thread

        System.out.println(fila2.size());
    }
}
