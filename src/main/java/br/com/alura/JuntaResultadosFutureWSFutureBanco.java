package br.com.alura;

import java.io.PrintStream;
import java.util.concurrent.*;

public class JuntaResultadosFutureWSFutureBanco implements Callable<Void> {
    private final Future<String> futureWS;
    private final Future<String> futureAcessaBanco;
    private final PrintStream saidaCliente;

    public JuntaResultadosFutureWSFutureBanco(Future<String> futureWS, Future<String> futureAcessaBanco, PrintStream saidaCliente) {
        this.futureWS = futureWS;
        this.futureAcessaBanco = futureAcessaBanco;
        this.saidaCliente = saidaCliente;
    }

    @Override
    public Void call() throws Exception {
        System.out.println("Aguardando resultados do future WS e Banco");

        try {
            String wsResultado = futureWS.get(20, TimeUnit.SECONDS);
            String bancoResultado = futureAcessaBanco.get(20, TimeUnit.SECONDS);

            saidaCliente.println("Resultado comando c2 : " + wsResultado + ", " + bancoResultado);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Timeout : Cancelando execução do comando");
            this.saidaCliente.println("Execucao do comando c2 - Timeout");

            this.futureWS.cancel(true);
            this.futureAcessaBanco.cancel(true);
        }

        System.out.println("Finalizou " + getClass().getName());

        return null;
    }
}
