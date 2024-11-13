package br.com.alura.experimento;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorDeTeste {
    //    private volatile boolean estaRodando = false; // O volatile faz com que não haja diferentes atributos em caches diferentes da CPU mas apenas o da memoria principal
    private AtomicBoolean estaRodando = new AtomicBoolean(false); // um wrapper em cima do volatile

    public static void main(String[] args) throws InterruptedException {
        ServidorDeTeste servidor = new ServidorDeTeste();
        servidor.rodar();
        servidor.alterandoAtributo();
    }

    private void rodar() {
//        try {
//            new Thread(() -> {
//                System.out.println("Servidor comecando, estaRodando=" + estaRodando);
//
//                while (!estaRodando.get()) {}
//
//                if(estaRodando.get()) throw new RuntimeException("Deu erro na thread...");
//
//                System.out.println("Servidor rodando, estaRodando=" + estaRodando);
//
//                while (estaRodando.get()) {}
//
//                System.out.println("Servidor terminando, estaRodando=" + estaRodando);
//            }).start();
//        } catch (RuntimeException e) {
//            System.out.println("catch na thread " + Thread.currentThread().getName() + " " + e.getMessage());
//        }

//        new Thread(() -> {
//            try {
//                System.out.println("Servidor comecando, estaRodando=" + estaRodando);
//
//                while (!estaRodando.get()) {
//                }
//
//                if (estaRodando.get()) throw new RuntimeException("Deu erro na thread...");
//
//                System.out.println("Servidor rodando, estaRodando=" + estaRodando);
//
//                while (estaRodando.get()) {
//                }
//
//                System.out.println("Servidor terminando, estaRodando=" + estaRodando);
//            } catch (RuntimeException e) {
//                System.out.println("catch na thread " + Thread.currentThread().getName() + " " + e.getMessage());
//            }
//        }).start();

        Thread thread = new Thread(() -> {
            System.out.println("Servidor comecando, estaRodando=" + estaRodando);

            while (!estaRodando.get()) {
            }

            if (estaRodando.get()) throw new RuntimeException("Deu erro na thread...");

            System.out.println("Servidor rodando, estaRodando=" + estaRodando);

            while (estaRodando.get()) {
            }

            System.out.println("Servidor terminando, estaRodando=" + estaRodando);
        });

        thread.setUncaughtExceptionHandler(new TratadorDeExcecao());

        thread.start();
    }

    private void alterandoAtributo() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Main alterando estaRodando=true");
        estaRodando.set(true);

        Thread.sleep(5000);
        System.out.println("Main alterando estaRodando=false");
        estaRodando.set(false);
    }
}
