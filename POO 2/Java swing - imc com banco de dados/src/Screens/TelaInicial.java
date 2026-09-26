package Screens;

import Connection.ConexaoBancoDeDados;
import Entities.Pessoa;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static java.lang.Double.parseDouble;

public class TelaInicial extends JFrame implements ActionListener {


    ConexaoBancoDeDados objBd;

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

    private JLabel lbPeso;
    private JLabel lbAltura;

    private JTextField txtPeso;
    private JTextField txtAltura;

    private JLabel lbResultado;


    // botões atributo início

    private JButton btnCalcularIMC;
    private JButton btnCadastrar;
    private JButton btnRemover;
    private JButton btnAlterar;
    private JButton btnListagem;
    private JButton btnRelatorio;

    // botões final

    // atributo textArea

    private JLabel LabelResultadoPesquisa;
    private JTextArea listaPesquisaBancoDeDados;
    private JScrollPane scrollPesquisaBancoDeDados;


    private JLabel lbMensagem;

    private JLabel LabelMensagem;





    private Container ctn;


    public TelaInicial() {
        setSize(800, 700);
        setTitle("Sistema de cadastro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ctn = getContentPane();
        lbNome = new JLabel("Nome ");
        txtNome = new JTextField();
        lbEndereco = new JLabel("Endereço ");
        txtEndereco = new JTextField();
        lbTelefone = new JLabel("Telefone ");
        lbCpf = new JLabel("CPF ");

        try {
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

        lbPeso = new JLabel("Digite seu peso: ");
        lbAltura = new JLabel("Digite sua altura: ");

        txtPeso = new JTextField();
        txtAltura = new JTextField();

        lbResultado = new JLabel("O valor do IMC é: --");

        lbMensagem = new JLabel("");

        // botôes texto início
        btnCalcularIMC = new JButton("Calcular IMC");
        btnCadastrar = new JButton("Cadastrar");
        btnRemover = new JButton("Remover");
        btnAlterar = new JButton("Alterar");
        btnListagem = new JButton("Listar");
        btnRelatorio = new JButton("Relatório");
        // final botôes texto




        // text area
        LabelResultadoPesquisa =
                new JLabel("Resultado da pesquisa no Banco de Dados");
        listaPesquisaBancoDeDados = new JTextArea();
        scrollPesquisaBancoDeDados =
                new JScrollPane(listaPesquisaBancoDeDados);




        ctn.setLayout(null);

        // encaixando elementos na tela
        lbNome.setBounds(10, 10, 100, 25);
        txtNome.setBounds(150, 10, 200, 25);

        lbEndereco.setBounds(10, 50, 100, 25);
        txtEndereco.setBounds(150, 50, 200, 25);

        lbTelefone.setBounds(10, 90, 100, 25);
        txtTelefone.setBounds(150, 90, 200, 25);

        lbCpf.setBounds(10, 130, 100, 25);
        txtCpf.setBounds(150, 130, 200, 25);

        // Tipo sanguineo e fator RH bounds

        tipoSanguineo.setBounds(10, 160, 100, 25);
        cbTipoS.setBounds(150, 160, 60, 25);

        lbFatorRH.setBounds(230, 160, 68, 25);
        cbFatorRh.setBounds(300, 160, 49, 25);

        // ------------------------------------------------------------

        lbCurso.setBounds(10, 200, 100, 25);
        cbCurso.setBounds(150, 200, 200, 25);

        lbContatoEmergencia.setBounds(10, 240, 100, 25);
        txtContatoEmergencia.setBounds(150, 240, 200, 25);

        lbtelefoneEmergencia.setBounds(10, 280, 100, 25);
        txtTelefoneEmergencia.setBounds(150, 280, 200, 25);

        btnCadastrar.setBounds(125, 320, 125, 30);
        btnRemover.setBounds(250, 320, 125, 30);

        lbPeso.setBounds(10, 370, 120, 25);
        txtPeso.setBounds(150, 370, 200, 25);

        lbAltura.setBounds(10, 410, 120, 25);
        txtAltura.setBounds(150, 410, 200, 25);



        lbResultado.setBounds(10, 500, 250, 25);


        // btn setBounds ini

        btnCadastrar.setBounds(65, 550, 200, 25);
        btnRemover.setBounds(285, 550, 200, 25);

        btnCalcularIMC.setBounds(65, 600, 200, 25);
        btnAlterar.setBounds(285, 600, 200, 25);

        btnListagem.setBounds(505, 550, 200, 25);

        btnRelatorio.setBounds(505, 600, 200, 25);

        // btn setBounds fim


        // textarea bounds
        LabelResultadoPesquisa.setBounds(480, 5, 300, 20);
        scrollPesquisaBancoDeDados.setBounds(460, 25, 285, 410);




        //lbMensagem.setBounds(10, 540, 300, 25); mensagem aparecera na tela ao lado

        ctn.add(lbPeso);
        ctn.add(txtPeso);
        ctn.add(lbAltura);
        ctn.add(txtAltura);

        ctn.add(lbResultado);

        ctn.add(lbMensagem);

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

        // btns
        ctn.add(btnCadastrar);
        ctn.add(btnRemover);
        ctn.add(btnCalcularIMC);
        ctn.add(btnAlterar);
        ctn.add(btnRelatorio);
        ctn.add(btnListagem);

        ctn.add(scrollPesquisaBancoDeDados);
        ctn.add(LabelResultadoPesquisa);



        // btn chamadas funções inicio
        btnCalcularIMC.addActionListener(this);
        btnCadastrar.addActionListener(this);
        btnRemover.addActionListener(this);

        // final btn chamadas func



        setVisible(true);

    }



    @Override
    public void actionPerformed(ActionEvent e) {




        if(e.getActionCommand().equals("Calcular IMC"))
        {

            // pegando dados da tela inicial

            Double peso = Double.parseDouble(txtPeso.getText());
            Double altura = Double.parseDouble(txtAltura.getText());



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

        // BTN cadastrar

        if(e.getActionCommand().equals("Cadastrar")){

            // pegando dados da tela inicial

            Double peso = Double.parseDouble(txtPeso.getText());
            Double altura = Double.parseDouble(txtAltura.getText());

            String tipoSanguineo =
                    cbTipoS.getSelectedItem().toString()
                            + cbFatorRh.getSelectedItem().toString();

            String curso = cbCurso.getSelectedItem().toString();



            Pessoa objeto =
                    new Pessoa(
                            txtNome.getText(),
                            txtEndereco.getText(),
                            txtTelefone.getText(),
                            txtCpf.getText(),
                            tipoSanguineo,
                            curso,
                            txtContatoEmergencia.getText(),
                            txtTelefoneEmergencia.getText(),
                            altura,
                            peso

                    );

            try {
                objBd = new ConexaoBancoDeDados();

                String mensagem =
                        objBd.InserirDados(objeto);

                // AQUI DEVE ESTAR 2 ALERTS UM PARA DIZER SE DEU CERTO A INSERÇÃO

                // EX; JOptionPane.showMessageDialog(this, "Dados alterados com sucesso!");

               // LabelMensagem.setText(mensagem);


            } catch (SQLException e1){

                e1.printStackTrace();

                // OUTRO SE DER ERRADO
            }

        }

        // BTN Remover

        if (e.getActionCommand().equals("Remover")) {

            try {

                objBd = new ConexaoBancoDeDados();

                // Busca todas as pessoas 
                List<Pessoa> pessoas = objBd.cadastrados();

                // Monta o texto que será mostrado
                StringBuilder lista = new StringBuilder();

                lista.append("PESSOAS CADASTRADAS\n\n");

                for (Pessoa pessoa : pessoas) {

                    lista.append("ID: ")
                            .append(pessoa.getId())
                            .append(" | Nome: ")
                            .append(pessoa.getNomeCompleto())
                            .append(" | CPF: ")
                            .append(pessoa.getCPF())
                            .append("\n");
                }

                // Mostra as pessoas cadastradas
                JOptionPane.showMessageDialog(
                        this,
                        lista.toString(),
                        "Pessoas cadastradas",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Campo para digitar o ID
                String idTexto = JOptionPane.showInputDialog(
                        this,
                        "Digite o ID da pessoa que deseja remover:"
                );

                // Se o usuário clicou em cancelar
                if (idTexto == null) {
                    return;
                }

                int id = Integer.parseInt(idTexto);

                // Remove pelo ID
                String mensagem = objBd.RemoverPessoa(id);

                JOptionPane.showMessageDialog(
                        this,
                        mensagem,
                        "Remoção",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException e1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Digite um ID válido.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (SQLException e1) {

                e1.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao acessar o banco de dados.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }


    }

    // exemplos base




    /*
    btnEditar.addActionListener(e -> {
            System.out.println("Editando...");
        });

    btnExcluir.addActionListener(e -> {
            System.out.println("Excluindo...");
        });

     */
}
