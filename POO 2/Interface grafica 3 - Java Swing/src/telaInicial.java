import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class telaInicial extends JFrame {

    private JLabel lbNome;
    private JTextField txtNome;
    private JLabel lbCpf;
    private JFormattedTextField txtCpf;
    private JLabel lbTipo;
    private JComboBox cbTipo;

    private final String[] tiposDeUsuarios = {"Administrador", "Geral"};

    private JButton btnOk;

    private Container ctn;

    public telaInicial(){
        setSize(400,300);
        setTitle("Tela Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ctn = getContentPane();
        lbNome =  new JLabel("Nome: ");
        txtNome = new JTextField();
        lbCpf = new JLabel("CPF: ");

        try{
            txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
        } catch (ParseException e){
            e.printStackTrace();
        }

        lbTipo = new JLabel("Tipo de usuário");
        cbTipo =  new JComboBox(tiposDeUsuarios);
        btnOk = new JButton("Enviar");

        ctn.setLayout(null);

        //tamanhos
        lbNome.setBounds(0,10,100,25);
        txtNome.setBounds(150,10,200,25);
        lbCpf.setBounds(0,50,100,25);
        txtCpf.setBounds(150,50,200,25);
        lbTipo.setBounds(0,100,200,25);
        cbTipo.setBounds(150,100,200,25);
        btnOk.setBounds(150,150,100,100);

        ctn.add(lbNome);
        ctn.add(txtNome);
        ctn.add(lbCpf);
        ctn.add(txtCpf);
        ctn.add(lbTipo);
        ctn.add(cbTipo);
        ctn.add(btnOk);

        setVisible(true);



    }


}
