import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class TelaInicial extends JFrame {
    private JLabel lbNum1;
    private JLabel lbNum2;
    private JLabel lbPeso1;
    private JLabel lbPeso2;

    private JLabel lbResultado;

    private JTextField txtNum1;
    private JTextField txtNum2;

    private JTextField txtPeso1;
    private JTextField txtPeso2;

    private Container ctn;

    private JButton btnCalc;

    public TelaInicial(){
        setSize(600,300);
        setTitle("Média Ponderada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ctn = getContentPane();

        lbNum1 = new JLabel("Número 1 ");
        lbNum2 = new JLabel("Número 2 ");

        lbPeso1 = new JLabel("Peso 1 ");
        lbPeso2 = new JLabel("Peso 2 ");

        txtNum1 = new JTextField();
        txtNum2 = new JTextField();
        txtPeso1 = new JTextField();
        txtPeso2 = new JTextField();

        lbResultado = new JLabel("A média ponderada é: --");

        ctn.setLayout(null);

        btnCalc = new JButton("Calcular");

        //

        lbNum1.setBounds(10, 10, 100, 25);
        txtNum1.setBounds(90, 10, 150,25);

        lbNum2.setBounds(10, 50, 100, 25);
        txtNum2.setBounds(90, 50, 150,25);

        lbPeso1.setBounds(300, 10, 100, 25);
        txtPeso1.setBounds(380, 10, 150,25);

        lbPeso2.setBounds(300, 50, 100, 25);
        txtPeso2.setBounds(380, 50, 150,25);

        btnCalc.setBounds(205, 130, 180, 35);

        btnCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculaMedia();
            }
        });


        lbResultado.setBounds(10, 200, 150,25);


        ctn.add(lbNum1);
        ctn.add(txtNum1);
        ctn.add(lbNum2);
        ctn.add(txtNum2);

        ctn.add(lbPeso1);
        ctn.add(txtPeso1);

        ctn.add(lbPeso2);
        ctn.add(txtPeso2);

        ctn.add(btnCalc);

        ctn.add(lbResultado);

        setVisible(true);

    }

    public void calculaMedia(){

        Double valor1 = parseDouble(txtNum1.getText());
        Double valor2 = parseDouble(txtNum2.getText());

        Double peso1 = parseDouble(txtPeso1.getText());
        Double peso2 = parseDouble(txtPeso2.getText());

        Double media =
                ((valor1 * peso1) + (valor2 * peso2)) / (peso1 + peso2);


        lbResultado.setText("A média ponderada é: " + String.valueOf(media));

    }

}
