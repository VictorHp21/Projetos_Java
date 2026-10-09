public class Main {

    static final Object lockA = new Object();
    static final Object lockB = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("T1 obteve lockA");

                synchronized (lockB) {
                    System.out.println("T1 obteve lockB");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("T2 obteve lockB");

                synchronized (lockA) {
                    System.out.println("T2 obteve lockA");
                }
            }
        });

        t1.start();
        t2.start();

        /*1. A thread t1 obtém lockA.
        2. A thread t2 obtém lockB.
        3. t1 tenta obter lockB, mas precisa esperar por t2.
        4. t2 tenta obter lockA, mas precisa esperar por t1.
        Agora, nenhuma consegue avançar.*/

    }
}