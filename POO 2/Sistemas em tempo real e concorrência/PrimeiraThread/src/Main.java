public class Main {
    public static void main (String[] args){

        Thread tarefa = new Thread(
                ()-> {
                    for(int i = 1; i <= 5; i++){
                        System.out.println("Thread: " + i);
                    }
                }
        );

        tarefa.start();

        // exemplo 2 threads

        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("A: " + i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("B: " + i);
            }
        });

        t1.start();
        t2.start();

        System.out.println("Metodo main continua");

    }
}
