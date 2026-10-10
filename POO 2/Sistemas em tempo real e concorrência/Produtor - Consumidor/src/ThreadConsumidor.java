import javax.swing.*;

public class ThreadConsumidor extends Thread{

    ThreadProdutor ObjetoThreadProdutor; // obj para acessar thread produtor
    Thread ObjetoThreadConsumirdor;
    JTextArea ListaInterfaceGrafica;
    public boolean Encerrar = false;

    ThreadConsumidor(ThreadProdutor temp, JTextArea Lista){
        // inicializar os atributos
        ObjetoThreadProdutor = temp;
        ListaInterfaceGrafica = Lista;
        // cria a thread
        ObjetoThreadConsumirdor = new Thread(this);
        // iniciar thread (executa metodo run())
        ObjetoThreadConsumirdor.start();
    }

    public void run(){
        while (Encerrar == false){ // obs: Encerrar sera true quando o usuer clicar no botao (parar) na interface

            try { // REGIÃO CRÍTICA -- Verificar Mutex
                ObjetoThreadProdutor.Mutex.acquire();

                if(ObjetoThreadProdutor.buffercircular.size() > 0){ // verificar se há dados no buffer para serem lidos
                    int temp = ObjetoThreadProdutor.buffercircular.remove(0); // remove o primeiro elemento da lista
                    int processamento = temp * 100; // processar o dado
                    ListaInterfaceGrafica.append(Integer.toString(processamento) + "\n"); // mostrar o processamento na interface

                }

                // Liberar acesso a REGIÃO CRÍTICA
                ObjetoThreadProdutor.Mutex.release();
                Thread.sleep(10); // thread espera 10 milisegundos para continuar a execução

            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }


}
