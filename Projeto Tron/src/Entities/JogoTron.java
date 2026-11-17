package Entities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class JogoTron extends JPanel {
    private Moto motoAzul;

    private Image fundo;

    public static final int LARGURA = 480;
    public static final int ALTURA = 480;

    // limites arena
    public static final int LIMITE_MIN = 16;
    public static final int LIMITE_MAX = 464;

    // matar no próprio rastro???
    private static final boolean MATAR_NO_PROPRIO_RASTRO = false;

    private final List<InimigoIA> inimigos= new ArrayList<>();
    private final List<Moto> todasasmotos = new ArrayList<>();

    private final GeradorExplosao explosoes = new GeradorExplosao();
    private InterrupcaoTeclado teclado;

    private boolean fimdeJogo = false;
    private String mensagem = null;


    public JogoTron() {

        fundo = new ImageIcon(
                getClass().getResource("/Imagens e sons/fundo.png")
        ).getImage();

        setPreferredSize(new Dimension(LARGURA, ALTURA));
        //setBackground(Color.BLACK);


        setFocusable(true);

        // para que ao clicar o enter reinicie quando o jogo acaba

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && fimdeJogo){
                    iniciarPartida();
                }
            }
        });

        iniciarPartida();

        // trocar por mutex com threads POSTERIORMENTE??
        new Timer(100, e -> atualizarJogo()).start();

        new Timer(16, e -> {
            explosoes.update();
            repaint();
        }).start();

    }

    public void iniciarPartida(){
        todasasmotos.clear();
        inimigos.clear();
        fimdeJogo = false;
        mensagem = null;

        motoAzul = new Moto(240, 432, "azul", 'W');

        Moto laranja1 = new Moto(112, 48, "laranja", 'S');
        Moto laranja2 = new Moto(240, 48, "laranja", 'S');
        Moto laranja3 = new Moto(368, 48, "laranja", 'S');

        // dando uma personalidade para cada moto inimiga
        inimigos.add(new InimigoIA(laranja1, InimigoIA.Personalidade.PERSEGUIDOR));
        inimigos.add(new InimigoIA(laranja2, InimigoIA.Personalidade.EMBOSCADOR));
        inimigos.add(new InimigoIA(laranja3, InimigoIA.Personalidade.PINCA));

        todasasmotos.add(motoAzul);
        todasasmotos.add(laranja1);
        todasasmotos.add(laranja2);
        todasasmotos.add(laranja3);

        // teclado aponta para a moto do jogador

        if(teclado != null) removeKeyListener(teclado);
        teclado = new InterrupcaoTeclado(motoAzul);
        addKeyListener(teclado);


    }

    public void atualizarJogo(){
        if(fimdeJogo) return;

        // ias decidem a direção
        Moto perseguidor = inimigos.get(0).getMoto();
        for(InimigoIA ia: inimigos){
            ia.decidir(motoAzul, perseguidor, todasasmotos);
        }

        // todas as motos vivas se movimentam
        for(Moto m: todasasmotos){
            m.andar();
        }

        // verificar se alguem morreu
        verificarColisoes();
        verificarFimDeJogo();


    }

    private void verificarColisoes() {
        List<Moto> condenadas = new ArrayList<>();

        for (Moto a : todasasmotos) {
            if (!a.estaViva()) continue;

            boolean morreu = foraDaArena(a)
                    || (MATAR_NO_PROPRIO_RASTRO && a.bateuNoProprioRastro());

            for (Moto b : todasasmotos) {
                if (morreu) break;
                if (a == b || !b.estaViva()) continue;

                if (!a.mesmaCor(b) && a.bateuNoRastroDe(b)) {
                    morreu = true;                    // bateu no rastro de outra cor
                } else if (a.getX() == b.getX() && a.getY() == b.getY()) {
                    morreu = true;                    // duas motos na mesma casa
                }
            }

            if (morreu) condenadas.add(a);
        }

        // matar apenas depois que olhar todas, assim batida frontas mata as duas
        for (Moto m: condenadas){
            m.matar();
            explosoes.explodir(m.getX(), m.getY(), m.getCorluz());
        }
    }

    private boolean foraDaArena(Moto m) {
        return m.getX() < LIMITE_MIN || m.getX() > LIMITE_MAX
                || m.getY() < LIMITE_MIN || m.getY() > LIMITE_MAX;
    }

    private void verificarFimDeJogo() {
        if (!motoAzul.estaViva()) {
            fimdeJogo = true;
            mensagem = "VOCÊ PERDEU";
            return;
        }

        boolean algumaLaranjaViva = false;
        for (InimigoIA ia : inimigos) {
            if (ia.getMoto().estaViva()) algumaLaranjaViva = true;
        }

        if (!algumaLaranjaViva) {
            fimdeJogo = true;
            mensagem = "VOCÊ VENCEU!";
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);

        for (Moto m : todasasmotos) {
            m.Desenhar(g);
        }

        explosoes.render(g);

        if (mensagem != null) {
            desenharMensagem(g);
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
