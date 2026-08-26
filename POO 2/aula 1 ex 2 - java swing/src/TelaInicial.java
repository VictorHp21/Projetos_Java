import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class TelaInicial extends JFrame {

    private JLabel lbNome;
    private JTextField txtNome;

    private JLabel lbEndereco;
    private JTextField txtEndereco;

    private JLabel lbTelefone;
    private JFormattedTextField txtTelefone;

    private JLabel lbCpf;
    private JFormattedTextField txtCpf;

    private JLabel tipoSanguineo;

    private JComboBox cbTipoS;

    private final String[] tiposSanguineos = {"A", "B", "O", "AB"};

    private JLabel lbFatorRH;
    private JComboBox cbFatorRh;

    private final String[] fatorRh = {"+", "-"};


    private JLabel lbCurso;
    private JComboBox cbCurso;

    private final String[] cursos = {"TI", "ADMINISTRAÇÃO", "GEOGRAFIA", "EDUCAÇÃO FISÍCA"};


    private JLabel lbContatoEmergencia;
    private JTextField txtContatoEmergencia;

    private JLabel lbtelefoneEmergencia;
    private JFormattedTextField txtTelefoneEmergencia;

    private JButton btnInserir;
    private JButton btnCancelar;



    private Container ctn;


    public TelaInicial(){
        setSize(400,500);
        setTitle("Sistema de cadastro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ctn = getContentPane();
        lbNome = new JLabel("Nome ");
        txtNome = new JTextField();
        lbEndereco = new JLabel("Endereço ");
        txtEndereco = new JTextField();
        lbTelefone = new JLabel("Telefone ");
        lbCpf = new JLabel("CPF ");

        try{
            txtTelefone = new JFormattedTextField(new MaskFormatter("(##) #####-####"));

            txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));

            txtTelefoneEmergencia = new JFormattedTextField(new MaskFormatter("(##) #####-####"));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        tipoSanguineo = new JLabel("Tipo sanguíneo ");
        cbTipoS = new JComboBox(tiposSanguineos);

        lbFatorRH = new JLabel("Fator RH ");
        cbFatorRh = new JComboBox(fatorRh);

        lbCurso = new JLabel("Curso ");
        cbCurso = new JComboBox(cursos);

        lbContatoEmergencia = new JLabel("Contato de emergência");
        txtContatoEmergencia = new JTextField();

        lbtelefoneEmergencia = new JLabel("Telefone emergencial ");

        btnInserir = new JButton("Inserir");
        btnCancelar = new JButton("Cancelar");

        ctn.setLayout(null);

        // encaixando elementos na tela
        lbNome.setBounds(10, 10, 100, 25);
        txtNome.setBounds(130, 10, 200, 25);

        lbEndereco.setBounds(10, 50, 100, 25);
        txtEndereco.setBounds(130, 50, 200, 25);

        lbTelefone.setBounds(10, 90, 100, 25);
        txtTelefone.setBounds(130, 90, 200, 25);

        lbCpf.setBounds(10, 130, 100, 25);
        txtCpf.setBounds(130, 130, 200, 25);

        tipoSanguineo.setBounds(10, 160, 100, 25);
        cbTipoS.setBounds(130, 160, 60, 25);

        lbFatorRH.setBounds(198, 160, 68, 25);
        cbFatorRh.setBounds(280, 160, 49, 25);

        lbCurso.setBounds(10, 200, 100, 25);
        cbCurso.setBounds(130, 200, 200, 25);

        lbContatoEmergencia.setBounds(10, 240, 100, 25);
        txtContatoEmergencia.setBounds(130, 240, 200, 25);

        lbtelefoneEmergencia.setBounds(10, 280, 100, 25);
        txtTelefoneEmergencia.setBounds(130, 280, 200, 25);

        btnInserir.setBounds(125, 320, 125, 30);
        btnCancelar.setBounds(250, 320,125,30);

        // adicionando ao container
        ctn.add(lbNome);
        ctn.add(txtNome);
        ctn.add(lbEndereco);
        ctn.add(txtEndereco);
        ctn.add(lbTelefone);
        ctn.add(txtTelefone);
        ctn.add(lbCpf);
        ctn.add(txtCpf);
        ctn.add(tipoSanguineo);
        ctn.add(cbTipoS);
        ctn.add(lbFatorRH);
        ctn.add(cbFatorRh);
        ctn.add(lbCurso);
        ctn.add(cbCurso);
        ctn.add(lbContatoEmergencia);
        ctn.add(txtContatoEmergencia);
        ctn.add(lbtelefoneEmergencia);
        ctn.add(txtTelefoneEmergencia);

        ctn.add(btnInserir);
        ctn.add(btnCancelar);

        setVisible(true);



    }

}
