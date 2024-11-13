package br.com.alura.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);

        System.out.println("--- Conexao estabelecida ---");

        Thread threadEnviaComando = new Thread(() -> {
            try {
                System.out.println("Enviando comandos");
                PrintStream saida = new PrintStream(socket.getOutputStream());

                Scanner teclado = new Scanner(System.in);

                while (teclado.hasNextLine()) {
                    String linha = teclado.nextLine();

                    if (linha.trim().equals("")) break;

                    saida.println(linha);
                }

                saida.close();
                teclado.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Thread threadRecebeRespostaDoServidor = new Thread(() -> {
            try {
                System.out.println("Recebendo dados do servidor");
                Scanner respostaServidor = new Scanner(socket.getInputStream());

                while (respostaServidor.hasNextLine()) {
                    String linha = respostaServidor.nextLine();
                    System.out.println(linha);
                }

                respostaServidor.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        threadRecebeRespostaDoServidor.start();
        threadEnviaComando.start();

        try {
            threadEnviaComando.join(); // thread main se junta e espera a threadEnviaComando
//            threadEnviaComando.join(30000); // espera 30 segundos para a threadEnviaComando terminar, do contrário continua a execução, chamando socket.close()
//            threadRecebeRespostaDoServidor.join(); // não gera erro, pode ser usada tbm
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Fechando socket do cliente");
//        if(!socket.isConnected()) socket.close();
        socket.close();
    }
}
