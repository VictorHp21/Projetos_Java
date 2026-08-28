import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class TelaInicial  extends JFrame {

    private JLabel lbNome;
    private JTextField txtNome;
    private JLabel lbCep;
    private JFormattedTextField txtCep;
    private JLabel lbCnpj;
    private JFormattedTextField txtCnpj;
    private JLabel lbCodFornecedor;
    private JFormattedTextField txtCodFornecedor;

    private JButton btnCadastrar;

    private JButton btnCancelar;

    private Container ctn;

    public TelaInicial(){
        setSize(500,400);
        setTitle("Cadastro Fornecedor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ctn = getContentPane();

        lbNome = new JLabel("Nome: ");
        txtNome = new JTextField();

        lbCep = new JLabel("CEP: ");

        lbCnpj = new JLabel("CNPJ: ");

        lbCodFornecedor = new JLabel("Código do fornecedor: ");



        try{
            txtCep = new JFormattedTextField(new MaskFormatter("#####-###"));

            txtCnpj = new JFormattedTextField(new MaskFormatter("##.###.###/####-##"));

            txtCodFornecedor = new JFormattedTextField(new MaskFormatter("UU-#####L"));

        } catch (ParseException e){
            e.printStackTrace();
        }

        btnCadastrar = new JButton("Cadastrar");
        btnCancelar = new JButton("Cancelar");

        ctn.setLayout(null);


        // sizes
        lbNome.setBounds(10, 10, 100, 25);
        txtNome.setBounds(150, 10, 200, 25);

        lbCep.setBounds(10, 50, 100, 25);
        txtCep.setBounds(150, 50, 200, 25);

        lbCnpj.setBounds(10, 90, 100, 25);
        txtCnpj.setBounds(150, 90, 200, 25);

        lbCodFornecedor.setBounds(10, 130, 100, 25);
        txtCodFornecedor.setBounds(150, 130, 200, 25);

        btnCadastrar.setBounds(10, 170, 235,25);
        btnCancelar.setBounds(250, 170, 235, 25);

        ctn.add(lbNome);
        ctn.add(txtNome);
        ctn.add(lbCep);
        ctn.add(txtCep);
        ctn.add(lbCnpj);
        ctn.add(txtCnpj);
        ctn.add(lbCodFornecedor);
        ctn.add(txtCodFornecedor);

        ctn.add(btnCadastrar);
        ctn.add(btnCancelar);

        setVisible(true);


    }
}
