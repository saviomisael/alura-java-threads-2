package br.com.alura;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
    private final ServerSocket servidor;
    private final ExecutorService threadPool;
    private AtomicBoolean estaRodando;
    private BlockingQueue<String> filaComandos;

    public ServidorTarefas() throws IOException {
        System.out.println("--- Iniciando Servidor ---");
        this.servidor = new ServerSocket(12345);
//                for (Thread thread : Thread.getAllStackTraces().keySet())
//            System.out.println(thread.getName());

//        System.out.println("Quantidade de processadores: " + Runtime.getRuntime().availableProcessors()
//                + " Quantidade de memória: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB");


//        ExecutorService threadPool = Executors.newFixedThreadPool(2);

//        ScheduledExecutorService pool = Executors.newScheduledThreadPool(4);
//        pool.scheduleAtFixedRate(new DistribuirTarefas(servidor.accept()), 0, 60, TimeUnit.MINUTES); // ira rodar de 60 em 60 minutos
        this.threadPool = Executors.newCachedThreadPool(new FabricaDeThreads()); // cresce e diminui dinamicamente

        estaRodando = new AtomicBoolean(true);
        filaComandos = new ArrayBlockingQueue<>(2);

        iniciarConsumidores();
    }

    private void iniciarConsumidores() {
        int quantidadeDeConsumidores = 2;

        for (int i = 0; i < quantidadeDeConsumidores; i++) {
            TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
            this.threadPool.execute(tarefa);
        }
    }

    public void rodar() throws IOException {
        while (this.estaRodando.get()) {
            try {
                Socket socket = servidor.accept();
                System.out.println("Aceitando novo cliente na porta " + socket.getPort());

                threadPool.execute(new DistribuirTarefas(socket, this, threadPool, filaComandos));
            } catch (SocketException e) {
                System.out.println("SocketException, está rodando? " + this.estaRodando);
            }
        }
    }

    public void parar() throws IOException {
        this.estaRodando.set(false);
        servidor.close();
        threadPool.shutdown(); // espera os clientes desconectarem
//        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        ServidorTarefas servidor = new ServidorTarefas();
        servidor.rodar();
        servidor.parar();
    }
}
