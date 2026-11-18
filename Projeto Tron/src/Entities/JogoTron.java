package Entities;

import Config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;



public class JogoTron extends JPanel implements Runnable {

    private final Semaphore mutex = new Semaphore(1);

    private Thread threadDoJogo;
    private volatile boolean rodando = false;

    private static final int INTERVALO_PASSO  = 100; // ms entre passos das motos
    private static final int INTERVALO_FRAME  = 16;  // ms entre frames (~60 fps)

    private Moto motoAzul;

    private Image fundo;


    // matar no próprio rastro???
    private static final boolean MATAR_NO_PROPRIO_RASTRO = false;

    private final List<InimigoIA> inimigos= new ArrayList<>();
    private final List<Moto> todasasmotos = new ArrayList<>();

    private final GeradorExplosao explosoes = new GeradorExplosao();
    private InterrupcaoTeclado teclado;

    private boolean fimdeJogo = false;
    private String mensagem = null;

    private final Entities.Cronometro cronometro = new Entities.Cronometro();
    private final Pontuacao pontuacao = new Pontuacao();
    private boolean venceu = false;


    public JogoTron() {

        fundo = new ImageIcon(
                getClass().getResource("/Imagens e sons/fundo.png")
        ).getImage();

        setPreferredSize(new Dimension(Config.LARGURA, Config.ALTURA + HudTron.ALTURA_BARRA));
        //setBackground(Color.BLACK);


        setFocusable(true);

        // para que ao clicar o enter reinicie quando o jogo acaba

        addKeyListener(new InterrupcaoTeclado(this));

        iniciarPartida();

        // trocar por mutex com threads POSTERIORMENTE??


    }


    // controle THREAD

    public void iniciar(){
        if(rodando) return;
        rodando = true;
        cronometro.reiniciar();
        threadDoJogo = new Thread(this, "loop-tron");
        threadDoJogo.setDaemon(true);
        threadDoJogo.start();
    }

    public void parar(){
        rodando = false;
    }

    @Override
    public void run() {

        long ultimoPasso = System.currentTimeMillis();

        while (rodando){
            long agora = System.currentTimeMillis();

            // entra na região critica
            mutex.acquireUninterruptibly();

            try {
                explosoes.update();

                if(!fimdeJogo && agora - ultimoPasso >= INTERVALO_PASSO){
                    atualizarJogo();
                    ultimoPasso = agora;
                }
            } finally {
                mutex.release(); // sai da região critica
            }

            repaint(); // EDT redesenha

            try {
                Thread.sleep(INTERVALO_FRAME);
            } catch (InterruptedException e) {
                return;
            }
        }

    }


    // métodos chamados pelo teclado

    public void pedirDirecao(char direcao) {
        mutex.acquireUninterruptibly();
        try {
            if (motoAzul.estaViva()) {
                motoAzul.mudarDirecao(direcao);
            }
        } finally {
            mutex.release();
        }
    }

    public void reiniciarSeAcabou() {
        mutex.acquireUninterruptibly();
        try {
            if (fimdeJogo) {
                iniciarPartida();
            }
        } finally {
            mutex.release();
        }
    }


    private void iniciarPartida(){
        todasasmotos.clear();
        inimigos.clear();
        fimdeJogo = false;
        mensagem = null;

        int c = Config.CELULA;

        motoAzul = new Moto((Config.COLUNAS / 2) * c, (Config.LINHAS - 3) * c, "azul", 'W');

        Moto laranja1 = new Moto((Config.COLUNAS / 4) * c,                    3 * c, "laranja", 'S');
        Moto laranja2 = new Moto((Config.COLUNAS / 2) * c,                    3 * c, "laranja", 'S');
        Moto laranja3 = new Moto((Config.COLUNAS - Config.COLUNAS / 4) * c,   3 * c, "laranja", 'S');

        inimigos.add(new InimigoIA(laranja1, InimigoIA.Personalidade.PERSEGUIDOR));
        inimigos.add(new InimigoIA(laranja2, InimigoIA.Personalidade.EMBOSCADOR));
        inimigos.add(new InimigoIA(laranja3, InimigoIA.Personalidade.PINCA));

        todasasmotos.add(motoAzul);
        todasasmotos.add(laranja1);
        todasasmotos.add(laranja2);
        todasasmotos.add(laranja3);


        venceu = false;
        pontuacao.reiniciar();
        cronometro.reiniciar();
    }

    private void atualizarJogo(){
        Moto perseguidor = inimigos.get(0).getMoto();
        for (InimigoIA ia : inimigos) {
            ia.decidir(motoAzul, perseguidor, todasasmotos);
        }

        for (Moto m : todasasmotos) {
            m.andar();
        }

        verificarColisoes();
        verificarFimDeJogo();
    }

    private void verificarColisoes() {
        List<Moto> condenadas = new ArrayList<>();

        for (Moto a : todasasmotos) {
            if (!a.estaViva()) continue;

            boolean morreu = Config.foraDaArena(a.getX(), a.getY())
                    || (MATAR_NO_PROPRIO_RASTRO && a.bateuNoProprioRastro());

            for (Moto b : todasasmotos) {
                if (morreu) break;
                if (a == b || !b.estaViva()) continue;

                if (!a.mesmaCor(b) && a.bateuNoRastroDe(b)) {
                    morreu = true;
                } else if (a.getX() == b.getX() && a.getY() == b.getY()) {
                    morreu = true;
                }
            }

            if (morreu) condenadas.add(a);
        }

        int laranjasMortas = 0;
        for (Moto m : condenadas) {
            m.matar();
            explosoes.explodir(m.getX(), m.getY(), m.getCorluz());
            if (m != motoAzul) laranjasMortas++;
        }
        pontuacao.registrarMortes(laranjasMortas);
    }



    private void verificarFimDeJogo() {
        if (!motoAzul.estaViva()) {
            fimdeJogo = true;
            mensagem = "VOCÊ PERDEU";
            finalizarPartida(false);
            return;
        }



        boolean algumaLaranjaViva = false;
        for (InimigoIA ia : inimigos) {
            if (ia.getMoto().estaViva()) algumaLaranjaViva = true;
        }

        if (!algumaLaranjaViva) {
            fimdeJogo = true;
            mensagem = "VOCÊ VENCEU!";
            finalizarPartida(true);
        }


    }

    private void finalizarPartida(boolean vitoria) {
        venceu = vitoria;
        if (vitoria) pontuacao.registrarVitoria();        // +500
        cronometro.parar();                               // congela o tempo
        pontuacao.finalizar(cronometro.getSegundos());    // fecha a pontuação e confere o recorde
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        mutex.acquireUninterruptibly();    // a thread do jogo não altera nada enquanto desenhamos
        try {
            Graphics2D g2 = (Graphics2D) g;

            HudTron.desenharBarra(g2, Config.LARGURA, cronometro, pontuacao);

            Graphics2D arena = (Graphics2D) g2.create();
            arena.translate(0, HudTron.ALTURA_BARRA);   // arena desenhada abaixo da barra

            arena.drawImage(fundo, 0, 0, Config.LARGURA, Config.ALTURA, this);

            for (Moto m : todasasmotos) {
                m.Desenhar(arena);
            }

            explosoes.render(arena);

            HudTron.desenharEvento(arena, Config.LARGURA, pontuacao);

            if (fimdeJogo) {
                HudTron.desenharFimDeJogo(arena, Config.LARGURA, Config.ALTURA, venceu, mensagem, cronometro, pontuacao);
            }

            arena.dispose();
        } finally {
            mutex.release();
        }
    }

    private void desenharMensagem(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // escurece a tela
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(Color.CYAN);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(mensagem, (getWidth() - fm.stringWidth(mensagem)) / 2, getHeight() / 2);

        String dica = "Pressione ENTER para jogar novamente";
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        fm = g2.getFontMetrics();
        g2.drawString(dica, (getWidth() - fm.stringWidth(dica)) / 2, getHeight() / 2 + 40);

        g2.dispose();
    }


}
