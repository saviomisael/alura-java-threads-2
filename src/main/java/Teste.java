import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Teste {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayList<String> a = new ArrayList<>();
        a.add("a");
        a.add("e");
        a.add("f");

        ArrayList<String> b = new ArrayList<>();
        b.add("a");
        b.add("b");
        b.add("c");
        b.add("f");
        b.add("g");
        b.add("h");

        comparar(a, b);
        continueMetodo(a);

        Callable<String> tarefa = () -> "Teste";

        FutureTask<String> futureTask = new FutureTask<>(tarefa);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }

    public static boolean comparar(List a, List b) {
        Comparator<Object> comparador = Comparator.comparing(String::valueOf);

        a.sort(comparador);

        b.sort(comparador);

        boolean acheiIgualGeral = true;
        for (int i = 0; i < a.size(); i++) {
            boolean acheiIgual = false;
            for (int j = 0; j < b.size(); j++) {
                System.out.println("A = " + a.get(i) + " B = " + b.get(j));
                if(a.get(i).toString().trim().equals(b.get(j).toString().trim())) {
                    acheiIgual = true;
                    System.out.println("Break for");
                    break; // quebra somente o for interno
                }
                System.out.println("Saindo do if");
            }

            acheiIgualGeral = acheiIgualGeral && acheiIgual;

            if(!acheiIgualGeral) return true;

            System.out.println("Saindo do for interno");
        }

        System.out.println("Saindo do for externo");
        return false;
    }

    public static <T> void continueMetodo(List<T> a) {
        for (int i = 0; i < a.size(); i++) {
            if(a.get(i) != null && !a.get(i).toString().isEmpty()) {
                System.out.println("Comecei processamento A");

                System.out.println("Realizei processamento A");
                continue; // Intellij avisa que é desnecessário mas o maven no empacotamento não
            }
        }

        for (int i = 0; i < a.size(); i++) {
            if(a.get(i) != null && !a.get(i).toString().isEmpty()) {
                continue;
            }

            System.out.println("Realizando processamento B");
        }
    }
}
