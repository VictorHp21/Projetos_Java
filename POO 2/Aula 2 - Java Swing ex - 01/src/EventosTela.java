import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class EventosTela extends JFrame {

    private JLabel lbNome;
    private JTextField txtNome;

    private JButton btnEnviar;

    private Container ctn;

    public EventosTela(){
        setSize(300, 200);
        setTitle("Eventos em Java Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ctn = getContentPane();
        ctn.setLayout(null);

        lbNome = new JLabel("Nome: ");
        txtNome = new JTextField();

        btnEnviar = new JButton("Enviar");

        lbNome.setBounds(10,10,100,25);
        txtNome.setBounds(70,10,200,25);
        btnEnviar.setBounds(90,50,80,40);

        ctn.add(lbNome);
        ctn.add(txtNome);
        ctn.add(btnEnviar);

        // inserindo tratamento de eventos criado classe anônima

        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                somaValorBtn();
            }
        });

        setVisible(true);
    }

    public void trataBotaoEnviar(){
        txtNome.setText("Botão clicado");
    }

    // metodo para ex 2

    public void somaValorBtn(){
       int valor = parseInt(txtNome.getText()) ;
       valor = valor + 10;
       txtNome.setText(String.valueOf(valor));
    }


}
