package Principal;

import JogoCobrinha.JogoCobrinha;
import JogoExplosao.JogoExplosao;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class telaInicial extends JFrame implements ActionListener {

    public static final String NOME_FONTE = "Ink Free";
    private JLabel lbMensagem;

    private JButton btnJogoExplosao;
    private JButton btnJogoCobrinha;

    private Image imgDoFundo;
    private AudioInputStream audiofundo;

    private Clip clipFundo;

    private Container ctn;

    public telaInicial() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        setSize(JogoCobrinha.LARGURA_TELA, JogoCobrinha.ALTURA_TELA);
        setTitle("Tela de jogos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        File imagemFundo = new File("C:\\Users\\victo\\OneDrive\\Desktop\\PROJETOS\\Projetos Java\\POO 2\\Sistemas em tempo real e concorrência\\Jogo explosao e cobrinha\\imagem e sons\\gameimage.jpeg");

        imgDoFundo = ImageIO.read(imagemFundo);

        setContentPane(new PainelFundo());
        ctn = getContentPane();

        lbMensagem = new JLabel("Escolha um dos jogos abaixo para jogar!");

        lbMensagem.setFont(new Font(NOME_FONTE, Font.BOLD, 40));
        lbMensagem.setForeground(Color.WHITE);


        File arquivoMusicaAudioFundo = new File("C:\\Users\\victo\\OneDrive\\Desktop\\PROJETOS\\Projetos Java\\POO 2\\Sistemas em tempo real e concorrência\\Jogo explosao e cobrinha\\imagem e sons\\gameaudio.wav");

        audiofundo = AudioSystem.getAudioInputStream(arquivoMusicaAudioFundo);

        clipFundo = AudioSystem.getClip();
        clipFundo.open(audiofundo);                    // agora o clip recebe o áudio
        clipFundo.loop(Clip.LOOP_CONTINUOUSLY);        // repete a música sem parar



        btnJogoCobrinha = new JButton("Jogo da cobrinha");
        btnJogoExplosao = new JButton("Jogo da explosão");
        btnJogoCobrinha.setFont(new Font(NOME_FONTE, Font.BOLD, 25));
        btnJogoExplosao.setFont(new Font(NOME_FONTE, Font.BOLD, 25));

        ctn.setLayout(null);

        // encaixar elementos na tela

        int larguraTela = JogoCobrinha.LARGURA_TELA;
        int alturaTela = JogoCobrinha.ALTURA_TELA;

        // centralizar a mensagem:
        lbMensagem.setHorizontalAlignment(SwingConstants.CENTER);
        lbMensagem.setBounds(0, alturaTela / 2 - 100, larguraTela, 60);
        ctn.add(lbMensagem);


        // posicionamento btns

        int larguraBotao = 300;
        int alturaBotao = 70;
        int espaco = 40;

        int xPrimeiro = (larguraTela - (2 * larguraBotao + espaco)) / 2;
        int yBotoes = alturaTela / 2;

        btnJogoCobrinha.setBounds(xPrimeiro, yBotoes, larguraBotao, alturaBotao);
        btnJogoExplosao.setBounds(xPrimeiro + larguraBotao + espaco, yBotoes, larguraBotao, alturaBotao);

        ctn.add(btnJogoCobrinha);
        ctn.add(btnJogoExplosao);

        btnJogoCobrinha.addActionListener(this);
        btnJogoExplosao.addActionListener(this);


        setLocationRelativeTo(null); // centraliza janela no monitor
        setVisible(true);


    }

    private class PainelFundo extends JPanel{
       @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            if(imgDoFundo != null){
                g.drawImage(imgDoFundo, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private void voltarParaInicio(JFrame janelaDoJogo) {
        janelaDoJogo.dispose();
        setVisible(true);
        clipFundo.loop(Clip.LOOP_CONTINUOUSLY);
    }

    private void abrirJogo(String titulo, Component jogo, Runnable tarefaDoJogo) {
        setVisible(false);   // esconde a tela inicial
        clipFundo.stop();    // pausa a música

        JFrame janela = new JFrame(titulo);
        janela.setLayout(new BorderLayout());

        // botão voltar na parte de baixo
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font(NOME_FONTE, Font.BOLD, 20));
        btnVoltar.setFocusable(false);   // não rouba o teclado do jogo
        btnVoltar.addActionListener(ev -> voltarParaInicio(janela));

        janela.add(jogo, BorderLayout.CENTER);
        janela.add(btnVoltar, BorderLayout.SOUTH);

        // o X da janela também volta para o início, em vez de fechar o programa
        janela.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        janela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                voltarParaInicio(janela);
            }
        });

        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setResizable(false);
        janela.setVisible(true);

        jogo.requestFocusInWindow();       // o teclado da cobrinha precisa de foco
        new Thread(tarefaDoJogo).start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Jogo da cobrinha")) {
            JogoCobrinha gameCobra = new JogoCobrinha();
            abrirJogo("Cobrinha", gameCobra, gameCobra);
        }

        if (e.getActionCommand().equals("Jogo da explosão")) {
            JogoExplosao gameExplosao = new JogoExplosao();
            abrirJogo("Explosão", gameExplosao, gameExplosao);
        }

    }
}
