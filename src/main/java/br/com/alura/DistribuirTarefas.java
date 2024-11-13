package br.com.alura;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefas implements Runnable {
    private final Socket socket;
    private final ServidorTarefas servidor;
    private final ExecutorService threadPool;
    private final BlockingQueue<String> filaComandos;

    public DistribuirTarefas(Socket socket, ServidorTarefas servidor, ExecutorService threadPool, BlockingQueue<String> filaComandos) {
        this.socket = socket;
        this.servidor = servidor;
        this.threadPool = threadPool;
        this.filaComandos = filaComandos;
    }

    @Override
    public void run() {
        System.out.println("Distribuindo tarefas para " + socket);


        try {
            Scanner entradaCliente = new Scanner(socket.getInputStream());
            PrintStream saidaCliente = new PrintStream(socket.getOutputStream());

            while (entradaCliente.hasNextLine()) {
                String comando = entradaCliente.nextLine();

                System.out.println("Comando recebido " + comando);

                switch (comando) {
                    case "c1" -> {
                        saidaCliente.println("Confirmação comando c1");
                        ComandoC1 c1 = new ComandoC1(saidaCliente);
                        threadPool.execute(c1);
                    }
                    case "c2" -> {
                        saidaCliente.println("Confirmação comando c2");
//                        ComandoC2 c2 = new ComandoC2(saidaCliente);
//                        threadPool.execute(c2);
                        ComandoC2WS ws = new ComandoC2WS(saidaCliente);
                        ComandoC2AcessaBanco acessaBanco = new ComandoC2AcessaBanco(saidaCliente);
                        Future<String> futureWS = threadPool.submit(ws);
                        Future<String> futureAcessaBanco = threadPool.submit(acessaBanco);

                        this.threadPool.submit(new JuntaResultadosFutureWSFutureBanco(futureWS, futureAcessaBanco, saidaCliente));

                    }
                    case "c3" -> {
                        this.filaComandos.put(comando); // bloqueia
                        saidaCliente.println("Confirmação comando c3");
                    }
                    case "fim" -> {
                        saidaCliente.println("Servidor desligando");
                        servidor.parar();
                    }
                    default -> saidaCliente.println("Comando não encontrado");
                }

                System.out.println(comando);
            }

            entradaCliente.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
