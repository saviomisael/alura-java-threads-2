package br.com.alura;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class FabricaDeThreads implements ThreadFactory {
    static int numero = 1;

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setName("Thread Servidor Tarefas " + numero++);
        thread.setUncaughtExceptionHandler(new TratadorDeExcecao());

        return thread;
    }
}
