package Entities;



import javax.swing.Timer;

public class Cronometro {

    private static final int INTERVALO_MS = 100;

    private final Timer timer;

    private long inicio = 0;
    private long milissegundos = 0;

    public Cronometro() {
        timer = new Timer(INTERVALO_MS, e -> atualizar());
    }

    // zera e começa a contar
    public void reiniciar() {
        timer.stop();
        milissegundos = 0;
        inicio = System.currentTimeMillis();
        timer.start();
    }

    // congela o tempo (fim de jogo)
    public void parar() {
        if (!timer.isRunning()) return;
        timer.stop();
        atualizar();   // captura o valor final exato
    }

    private void atualizar() {
        milissegundos = System.currentTimeMillis() - inicio;
    }

    public int getSegundos() {
        return (int) (milissegundos / 1000);
    }

    // formato MM:SS.d (minutos, segundos e décimos)
    public String formatar() {
        long decimosTotais = milissegundos / 100;
        long decimos = decimosTotais % 10;
        long segundos = (decimosTotais / 10) % 60;
        long minutos = decimosTotais / 600;
        return String.format("%02d:%02d.%d", minutos, segundos, decimos);
    }

}
