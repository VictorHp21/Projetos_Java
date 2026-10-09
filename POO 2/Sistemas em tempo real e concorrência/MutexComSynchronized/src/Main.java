public class Main {

    // então se pode usar o contador em 2 threads

    public static void main(String[] args)
            throws InterruptedException {

        Contador contador = new Contador();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                contador.incrementar();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                contador.incrementar();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(contador.getValor());


        // anotação:
        /*synchronized é um mecanismo de sincronização do Java que utiliza monitores;
        não é uma implementação explícita de um objeto Mutex separado. Para trabalhar com um bloqueio explícito, Java também oferece ReentrantLock.
        * */

    }
}