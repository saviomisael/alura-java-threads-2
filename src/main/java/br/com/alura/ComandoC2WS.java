package br.com.alura;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2WS implements Callable<String> {
    private final PrintStream saidaCliente;

    public ComandoC2WS(PrintStream saidaCliente) {
        this.saidaCliente = saidaCliente;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Servidor recebeu comando c2 - WS");

        saidaCliente.println("processando comando c2 - WS");

        Thread.sleep(15000);

        int numero = new Random().nextInt(100) + 1;

        System.out.println("Servidor finalizou o comando c2 - WS");

        return String.valueOf(numero);
    }
}
