package br.com.alura;

import java.io.PrintStream;

public class ComandoC1 implements Runnable {
    private final PrintStream saidaCliente;

    public ComandoC1(PrintStream saidaCliente) {
        this.saidaCliente = saidaCliente;
    }

    @Override
    public void run() {
        System.out.println("Executando comando c1");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            saidaCliente.println("Houve um erro ao executar o comando c1");

            throw new RuntimeException(e);
        }

//        throw new RuntimeException("exception no comando c2");

        saidaCliente.println("Comando c1 executado com sucesso!");
    }
}
