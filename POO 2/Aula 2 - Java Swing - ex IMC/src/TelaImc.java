import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Double.parseDouble;

public class TelaImc extends JFrame implements ActionListener {

    private JLabel lbPeso;
    private JLabel lbAltura;

    private JTextField txtPeso;
    private JTextField txtAltura;

    private JLabel lbResultado;

    private JButton btnCalcular;

    private JLabel lbMensagem;

    private Container ctn;

    public TelaImc(){
        setSize(400,300);
        setTitle("Calcular IMC ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ctn = getContentPane();

        lbPeso = new JLabel("Digite seu peso: ");
        lbAltura = new JLabel("Digite sua altura: ");

        txtPeso = new JTextField();
        txtAltura = new JTextField();

        lbResultado = new JLabel("O valor do IMC é: --");

        lbMensagem = new JLabel("");

        btnCalcular = new JButton("Calcular IMC");

        btnCalcular.addActionListener(this);

        ctn.setLayout(null);

        //

        lbPeso.setBounds(10, 30, 100 ,25);
        txtPeso.setBounds(150, 30, 150, 25);

        lbAltura.setBounds(10, 70, 100, 25);
        txtAltura.setBounds(150, 70, 150, 25);

        btnCalcular.setBounds(90, 110, 140, 35);

        lbResultado.setBounds(10, 180, 150, 25);

        lbMensagem.setBounds(10, 210, 150, 25);

        ctn.add(lbPeso);
        ctn.add(txtPeso);
        ctn.add(lbAltura);
        ctn.add(txtAltura);
        ctn.add(btnCalcular);
        ctn.add(lbResultado);

        ctn.add(lbMensagem);

        setVisible(true);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
            Double peso = parseDouble(txtPeso.getText());
            Double altura = parseDouble(txtAltura.getText());

            Double imc = peso / (altura * altura);

            lbResultado.setText("O valor do IMC é: " + String.valueOf(imc));

            if(imc >= 18.5 && imc <= 25){
                lbMensagem.setText("Peso ideal");
            } else if(imc > 25){
                lbMensagem.setText("Você está acima do peso ideal!");
            } else {
                lbMensagem.setText("Você está abaixo do peso ideal!");
            }

            ctn.add(lbMensagem);
    }
}
