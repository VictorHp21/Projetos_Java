import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface extends JFrame implements ActionListener {
    private JLabel lbCandidato;
    private JLabel lbTotal;
    private JLabel lbTotalBranco;
    private JLabel lbCandidato1;
    private JLabel lbCandidato2;

    private JTextField txtVoto;
    private JLabel lbTotalVotoBranco;
    private JLabel lbTotalVotoCandidato1;
    private JLabel lbTotalVotoCandidato2;
    private JLabel lbTotalVotacao;

    private JLabel lbNome;
    private JLabel lbNome1;
    private JLabel lbNome2;
    private JLabel lbNome3;
    private JLabel lbMsgVotacao;

    private JButton btnApurar;
    private JButton btnVotar;

    private Container ctn;

    public Votacao objVotacao;

    Interface(){
        setSize(300,430);
        setTitle("Urna eletrônica");
        ctn = getContentPane();
        ctn.setLayout(null);
        //

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        lbCandidato = new JLabel("Candidatos");
        lbCandidato.setBounds(10,10,100,25);

        lbNome1 = new JLabel("1 - Jones Alves");
        lbNome1.setBounds(150,10,150,25);

        lbNome2 = new JLabel("2 - Ignacio Varga");
        lbNome2.setBounds(150,30,150,25);

        lbNome3 = new JLabel("3 - Voto em  branco");
        lbNome3.setBounds(150,50,150,25);

        lbNome = new JLabel("Voto:");
        lbNome.setBounds(10,80,100,25);

        lbMsgVotacao = new JLabel("Digite o seu voto!!!");
        lbMsgVotacao.setBounds(60,180,200,25);

        txtVoto = new JTextField();
        txtVoto.setBounds(40,80,25,25);

        btnVotar = new JButton("Votar");
        btnVotar.setBounds(170,80,75,25);

        btnApurar = new JButton("Apuração dos votos");
        btnApurar.setBounds(10,260,250,25);

        lbTotal = new JLabel("Totoal de votos");
        lbTotal.setBounds(10,290,250,25);

        lbTotalVotacao = new JLabel("---");
        lbTotalVotacao.setBounds(170,290,30,25);

        lbTotalBranco = new JLabel("Total em branco");
        lbTotalBranco.setBounds(10,310,350,25);

        lbTotalVotoBranco = new JLabel("---");
        lbTotalVotoBranco.setBounds(170,310,30,25);

        lbCandidato1 = new JLabel("Votos candidato 1");
        lbCandidato1.setBounds(10,330,250,25);

        lbTotalVotoCandidato1 = new JLabel("---");
        lbTotalVotoCandidato1.setBounds(170,330,30,25);


        lbCandidato2 = new JLabel("Votos candidato 2");
        lbCandidato2.setBounds(10,350,250,25);

        lbTotalVotoCandidato2 = new JLabel("---");
        lbTotalVotoCandidato2.setBounds(170,350,30,25);

        ctn.add(btnVotar);
        ctn.add(btnApurar);
        ctn.add(lbCandidato);
        ctn.add(txtVoto);
        ctn.add(lbNome);
        ctn.add(lbNome1);
        ctn.add(lbNome2);
        ctn.add(lbNome3);
        ctn.add(lbTotal);
        ctn.add(lbTotalBranco);
        ctn.add(lbCandidato1);
        ctn.add(lbCandidato2);
        ctn.add(lbTotalVotacao);
        ctn.add(lbTotalVotoCandidato1);
        ctn.add(lbTotalVotoCandidato2);
        ctn.add(lbTotalVotoBranco);
        ctn.add(lbMsgVotacao);

        btnVotar.addActionListener(this);
        btnApurar.addActionListener(this);

        objVotacao = new Votacao();

        setVisible(true);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Votar")){
            int numero = Integer.parseInt(txtVoto.getText());

            int resultado = objVotacao.Votar(numero);

            if(resultado == 1){
                lbMsgVotacao.setText("Voto computado com sucesso !!!");
            } else {
                lbMsgVotacao.setText("Erro! candidato inválido");
            }

            txtVoto.setText("");

        }


        if(e.getActionCommand().equals("Apuração dos votos")){
            lbTotalVotacao.setText(Integer.toString(objVotacao.getTotalVotos()));

            lbTotalVotoBranco.setText(Integer.toString(objVotacao.getVotoBranco()));

            lbTotalVotoCandidato1.setText(Integer.toString(objVotacao.getCandidato1()));

            lbTotalVotoCandidato2.setText(Integer.toString(objVotacao.getCandidato2()));
        }
    }


}
