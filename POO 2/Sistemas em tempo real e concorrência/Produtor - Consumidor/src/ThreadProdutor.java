import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ThreadProdutor extends Thread {

    ArrayList<Integer> buffercircular = new ArrayList<>(100);
    int valor = 0;
    public Semaphore Mutex = new Semaphore(1); //Mutual Exclusion
    public boolean Encerrar = false;
    JTextArea ListaInterfaceGrafica;
    Thread ObjetoThreadProdutor;

    ThreadProdutor(JTextArea lista){
        //Inicializar atributos
        ListaInterfaceGrafica = lista;
        // criar a thread
        ObjetoThreadProdutor = new Thread(this);
        //Iniciar thread
        ObjetoThreadProdutor.start(); // o método Run será executado

    }

    public void run(){
        while(Encerrar == false){ // obs: "Encerrar será true quando o usuário clicar no botão (parar) na interface grafica"
            try {
                //***** Região crítica ---- Verificar o Mutex
                Mutex.acquire();

                if(buffercircular.size() < 100){
                    buffercircular.add(valor); // adc o valor aquisicionando no buffer circular
                    ListaInterfaceGrafica.append(Integer.toString(valor) + "\n"); // mostrar valor na interface
                    valor++; // gerar novo valor
                }

                // **** Liberar acesso região critica
                Mutex.release();

                Thread.sleep(10); // fazer a Thread esperar 10 milisegundos para continuar a execução


            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
