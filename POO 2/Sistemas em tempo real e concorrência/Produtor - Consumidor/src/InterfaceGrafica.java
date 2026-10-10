import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceGrafica extends JFrame implements ActionListener {

    // atributos
    private JButton botaoINICIAR;
    private JButton botaoPARAR;
    private JLabel LabelNumerosGerados;
    private JLabel LabelNumerosProcessados;
    private JLabel LabelRelogio;
    static private JTextArea ListaNumerosGerados;
    static private JTextArea ListaNumerosProcessados;
    private JScrollPane scrollListaNumerosGerados;
    private JScrollPane scrollListaNumerosProcessados;
    private Container janelaPrincipal;

    // atributos para criar as threads
    ThreadProdutor objetoThreadProdutor; // thread que vai gerar os numeros
    ThreadConsumidor objetoThreadConsumidor; // vai processar os numeros


    // construtor

    public InterfaceGrafica(){

        setSize(300, 640);
        setTitle("Threads");
        janelaPrincipal = getContentPane();
        janelaPrincipal.setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // componentes
        botaoINICIAR = new JButton("Iniciar");
        botaoPARAR = new JButton("Parar");
        LabelRelogio = new JLabel("Relogio");
        LabelNumerosGerados = new JLabel("Gerados");
        LabelNumerosProcessados = new JLabel("Processados");
        ListaNumerosGerados = new JTextArea();
        ListaNumerosProcessados = new JTextArea();
        scrollListaNumerosGerados = new JScrollPane(ListaNumerosGerados);
        scrollListaNumerosProcessados = new JScrollPane(ListaNumerosProcessados);


        // posicionamento na tela
        botaoINICIAR.setBounds(70, 550, 80, 40); //deslocamentos na tela: coluna linha comprimento altura
        botaoPARAR.setBounds(160, 550, 80, 40);
        LabelNumerosGerados.setBounds(50, 3, 100, 20);
        LabelNumerosProcessados.setBounds(180, 3, 100, 20);
        scrollListaNumerosGerados.setBounds(30, 20, 100, 500);
        scrollListaNumerosProcessados.setBounds(160, 20, 100, 500);
        LabelRelogio.setBounds(120, 520, 80, 40);

        //adicionar os componentes na tela
        janelaPrincipal.add(botaoINICIAR);
        janelaPrincipal.add(botaoPARAR);
        janelaPrincipal.add(LabelNumerosGerados);
        janelaPrincipal.add(LabelNumerosProcessados);
        janelaPrincipal.add(scrollListaNumerosGerados);
        janelaPrincipal.add(scrollListaNumerosProcessados);
        janelaPrincipal.add(LabelRelogio);

        setVisible(true);

        botaoINICIAR.addActionListener(this);
        botaoPARAR.addActionListener(this);

        // inserir relogio na tela
        Temporizador relogio = new Temporizador(LabelRelogio);

    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getActionCommand().equals("Iniciar")){
            ListaNumerosGerados.setText("");
            ListaNumerosProcessados.setText("");

            // criando as threads
            objetoThreadProdutor = new ThreadProdutor(ListaNumerosGerados);
            objetoThreadConsumidor = new ThreadConsumidor(objetoThreadProdutor, ListaNumerosProcessados);
        }

        if(e.getActionCommand().equals("Parar")){
            // setar o atriuto (Encerrar) para true --> as threads encerrarem o loop infinito
            objetoThreadProdutor.Encerrar = true;
            objetoThreadConsumidor.Encerrar = true;
        }

    }



}
