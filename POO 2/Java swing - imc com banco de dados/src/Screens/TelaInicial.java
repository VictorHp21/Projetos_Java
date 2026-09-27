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
import java.util.Comparator;
import java.util.List;

import static java.lang.Double.max;
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

    private final String[] cursos = {"Direito" , "Ciência da Computação", "Sistemas De Informação", "Medicina",
            "Psicologia", "Nutrição"};


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



        lbResultado.setBounds(10, 500, 350, 25);


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
        btnListagem.addActionListener(this);
        btnRelatorio.addActionListener(this);
        btnAlterar.addActionListener(this);
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

            String mensagem;

            if (imc >= 18.5 && imc <= 25) {
                mensagem = "Peso ideal";
            } else if (imc > 25) {
                mensagem = "Você está acima do peso ideal!";
            } else {
                mensagem = "Você está abaixo do peso ideal!";
            }

            lbResultado.setText(
                    "O valor do IMC é: " +
                            String.format("%.2f", imc) +
                            " - " +
                            mensagem
            );
        }

        // BTN cadastrar

        if(e.getActionCommand().equals("Cadastrar")){

            // verificar se todos os campos estão preenchidos:

            if (txtNome.getText().trim().isEmpty() ||
                    txtEndereco.getText().trim().isEmpty() ||
                    txtTelefone.getText().trim().isEmpty() ||
                    txtCpf.getText().trim().isEmpty() ||
                    txtContatoEmergencia.getText().trim().isEmpty() ||
                    txtTelefoneEmergencia.getText().trim().isEmpty() ||
                    txtPeso.getText().trim().isEmpty() ||
                    txtAltura.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos antes de cadastrar.",
                        "Campos obrigatórios",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

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

                JOptionPane.showMessageDialog(
                        this,
                        mensagem,
                        "Cadastro",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Limpar os campos após o cadastro
                txtNome.setText("");
                txtEndereco.setText("");
                txtTelefone.setText("");
                txtCpf.setText("");
                txtContatoEmergencia.setText("");
                txtTelefoneEmergencia.setText("");
                txtPeso.setText("");
                txtAltura.setText("");

                cbTipoS.setSelectedIndex(0);
                cbFatorRh.setSelectedIndex(0);
                cbCurso.setSelectedIndex(0);


            } catch (SQLException e1){

                e1.printStackTrace();

                // OUTRO SE DER ERRADO
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao cadastrar a pessoa no banco de dados.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(
                        this,
                        "Digite valores válidos para peso e altura.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
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

                    JOptionPane.showMessageDialog(
                            this,
                            "Operação de remoção cancelada.",
                            "Cancelado",
                            JOptionPane.INFORMATION_MESSAGE
                    );

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



        // Listagem


        if(e.getActionCommand().equals("Listar")){

            // Limpa JTextArea
            listaPesquisaBancoDeDados.setText("");

            try {

                objBd = new ConexaoBancoDeDados();

                List<Pessoa> pessoas = objBd.cadastrados();

                StringBuilder lista = new StringBuilder();

                lista.append("PESSOAS CADASTRADAS\n\n");

                for (Pessoa pessoa : pessoas) {

                    lista.append("ID: ")
                            .append(pessoa.getId())
                            .append("\n");

                    lista.append("Nome completo: ")
                            .append(pessoa.getNomeCompleto())
                            .append("\n");

                    lista.append("Endereço: ")
                            .append(pessoa.getEndereço())
                            .append("\n");

                    lista.append("Telefone: ")
                            .append(pessoa.getTelefone())
                            .append("\n");

                    lista.append("CPF: ")
                            .append(pessoa.getCPF())
                            .append("\n");

                    lista.append("Tipo sanguíneo: ")
                            .append(pessoa.getTipoSanguineo())
                            .append("\n");

                    lista.append("Curso: ")
                            .append(pessoa.getCurso())
                            .append("\n");

                    lista.append("Contato de emergência: ")
                            .append(pessoa.getContatoDeEmergencia())
                            .append("\n");

                    lista.append("Telefone de emergência: ")
                            .append(pessoa.getTelefoneEmergencia())
                            .append("\n");

                    lista.append("Altura: ")
                            .append(pessoa.getAltura())
                            .append(" m\n");

                    lista.append("Peso: ")
                            .append(pessoa.getPeso())
                            .append(" kg\n");

                    lista.append("IMC: ")
                            .append(String.format("%.2f", pessoa.getImc()))
                            .append("\n");

                    lista.append("----------------------------------------\n\n");
                }

                // Coloca a lista
                listaPesquisaBancoDeDados.setText(lista.toString());

                // Volta o cursor para o início
                listaPesquisaBancoDeDados.setCaretPosition(0);

            } catch (SQLException e2){
                e2.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao buscar os dados no banco de dados.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }



        }


        // Relatório

        if(e.getActionCommand().equals("Relatório")){

            listaPesquisaBancoDeDados.setText("");

            try {

                objBd = new ConexaoBancoDeDados();

                List<Pessoa> pessoas = objBd.cadastrados();

                StringBuilder lista = new StringBuilder();

                // MAIOR PESO

                lista.append("PESSOA COM MAIOR PESO \n\n");

                Pessoa pessoaMaiorPeso = pessoas.stream()
                        .max(Comparator.comparing(Pessoa::getPeso))
                        .orElse(null);

                if (pessoaMaiorPeso != null) {
                    lista.append("Nome: ")
                            .append(pessoaMaiorPeso.getNomeCompleto())
                            .append("\n");

                    lista.append("Tipo sanguíneo: ")
                            .append(pessoaMaiorPeso.getTipoSanguineo())
                            .append("\n");

                    lista.append("Peso: ")
                            .append(pessoaMaiorPeso.getPeso())
                            .append(" kg\n");
                }

                // MENOR PESO

                Pessoa pessoaMenorPeso = pessoas.stream()
                                .min(Comparator.comparing(Pessoa::getPeso))
                                        .orElse(null);



                lista.append("\nPESSOA COM MENOR PESO \n\n");

                if (pessoaMenorPeso != null) {
                    lista.append("Nome: ")
                            .append(pessoaMenorPeso.getNomeCompleto())
                            .append("\n");

                    lista.append("Tipo sanguíneo: ")
                            .append(pessoaMenorPeso.getTipoSanguineo())
                            .append("\n");

                    lista.append("Peso: ")
                            .append(pessoaMenorPeso.getPeso())
                            .append(" kg\n\n");
                }


                // MÉDIA PESO

                double mediaPesos = pessoas.stream()
                        .mapToDouble(Pessoa::getPeso)
                        .average()
                        .orElse(0.0);

                lista.append("MÉDIA DOS PESOS: ")
                        .append(String.format("%.2f", mediaPesos))
                        .append(" kg\n");

                // MAIOR ALTURA

                Pessoa pessoaMaiorAltura = pessoas.stream()
                        .max(Comparator.comparing(Pessoa::getAltura))
                        .orElse(null);

                if (pessoaMaiorAltura != null) {

                    lista.append("\nMAIOR ALTURA: ")
                            .append(pessoaMaiorAltura.getAltura())
                            .append(" m\n");

                    lista.append("Nome: ")
                            .append(pessoaMaiorAltura.getNomeCompleto())
                            .append("\n");

                    lista.append("Curso: ")
                            .append(pessoaMaiorAltura.getCurso())
                            .append("\n");

                }

                // MENOR ALTURA




                Pessoa pessoaMenorAltura = pessoas.stream()
                        .min(Comparator.comparing(Pessoa::getAltura))
                        .orElse(null);

                lista.append("\nMENOR ALTURA: ")
                        .append(pessoaMenorAltura.getAltura())
                        .append(" m\n");

                if (pessoaMenorAltura != null) {
                    lista.append("Nome: ")
                            .append(pessoaMenorAltura.getNomeCompleto())
                            .append("\n");

                    lista.append("Curso: ")
                            .append(pessoaMenorAltura.getCurso())
                            .append("\n");

                }



                // MÉDIA ALTURAS

                double mediaAlturas = pessoas.stream()
                        .mapToDouble(Pessoa::getAltura)
                        .average()
                        .orElse(0.0);

                lista.append("\nMÉDIA DAS ALTURAS: ")
                        .append(String.format("%.2f", mediaAlturas))
                        .append(" m\n");


                // MÉDIA IMC

                double mediaIMC = pessoas.stream()
                                .mapToDouble(Pessoa::getImc)
                                        .average()
                                                .orElse(0.0);


                lista.append("\nMÉDIA IMC: ")
                        .append(String.format("%.2f", mediaIMC))
                        .append(" kg\n");


                // MAIOR IMC

                Pessoa pessoaMaiorIMC = pessoas.stream()
                        .max(Comparator.comparing(Pessoa::getImc))
                        .orElse(null);



                lista.append("\nMAIOR IMC: ")
                        .append(String.format("%.2f", pessoaMaiorIMC.getImc()))
                        .append(" kg\n");


                if (pessoaMaiorIMC != null) {
                    lista.append("Nome: ")
                            .append(pessoaMaiorIMC.getNomeCompleto())
                            .append("\n");

                }


                // MENOR IMC

                Pessoa pessoaMenorIMC = pessoas.stream()
                        .min(Comparator.comparing(Pessoa::getImc))
                        .orElse(null);


                lista.append("\nMENOR IMC: ")
                        .append(String.format("%.2f", pessoaMenorIMC.getImc()))
                        .append(" kg\n");

                if (pessoaMenorIMC != null) {
                    lista.append("Nome: ")
                            .append(pessoaMenorIMC.getNomeCompleto())
                            .append("\n");

                }




                listaPesquisaBancoDeDados.setText(lista.toString());


                listaPesquisaBancoDeDados.setText(lista.toString());


                listaPesquisaBancoDeDados.setCaretPosition(0);

            } catch (SQLException e2){
                e2.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao buscar os dados no banco de dados.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        }


        // BTN ALTERAR

        if (e.getActionCommand().equals("Alterar")) {

            try {

                objBd = new ConexaoBancoDeDados();

                // Busca todas as pessoas
                List<Pessoa> pessoas = objBd.cadastrados();

                // Verifica se existem pessoas
                if (pessoas.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Não existem pessoas cadastradas.",
                            "Alterar",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    return;
                }

                // Monta a lista
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

                // Mostra a lista
                JOptionPane.showMessageDialog(
                        this,
                        lista.toString(),
                        "Pessoas cadastradas",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Pede o ID
                String idTexto = JOptionPane.showInputDialog(
                        this,
                        "Digite o ID da pessoa que deseja alterar:"
                );

                // Cancelar
                if (idTexto == null) {
                    return;
                }

                int id = Integer.parseInt(idTexto);

                // Verifica se o ID existe
                boolean idExiste = false;

                for (Pessoa pessoa : pessoas) {

                    if (pessoa.getId() == id) {
                        idExiste = true;
                        break;
                    }
                }

                if (!idExiste) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Não existe uma pessoa cadastrada com o ID " + id + ".",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }


                JCheckBox nome = new JCheckBox("Nome");
                JCheckBox endereco = new JCheckBox("Endereço");
                JCheckBox telefone = new JCheckBox("Telefone");
                JCheckBox cpf = new JCheckBox("CPF");
                JCheckBox tipoSanguineo = new JCheckBox("Tipo sanguíneo");
                JCheckBox curso = new JCheckBox("Curso");
                JCheckBox contatoEmergencia = new JCheckBox("Contato de emergência");
                JCheckBox telefoneEmergencia = new JCheckBox("Telefone de emergência");
                JCheckBox altura = new JCheckBox("Altura");
                JCheckBox peso = new JCheckBox("Peso");

                JPanel painel = new JPanel();

                painel.setLayout(new BoxLayout(
                        painel,
                        BoxLayout.Y_AXIS
                ));

                painel.add(new JLabel("Selecione os campos que deseja alterar:"));
                painel.add(nome);
                painel.add(endereco);
                painel.add(telefone);
                painel.add(cpf);
                painel.add(tipoSanguineo);
                painel.add(curso);
                painel.add(contatoEmergencia);
                painel.add(telefoneEmergencia);
                painel.add(altura);
                painel.add(peso);

                int resultado = JOptionPane.showConfirmDialog(
                        this,
                        painel,
                        "Campos para alteração",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                // Usuário cancelou
                if (resultado != JOptionPane.OK_OPTION) {
                    return;
                }

                // Verifica se selecionou algum campo
                if (!nome.isSelected() &&
                        !endereco.isSelected() &&
                        !telefone.isSelected() &&
                        !cpf.isSelected() &&
                        !tipoSanguineo.isSelected() &&
                        !curso.isSelected() &&
                        !contatoEmergencia.isSelected() &&
                        !telefoneEmergencia.isSelected() &&
                        !altura.isSelected() &&
                        !peso.isSelected()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Selecione pelo menos um campo para alterar.",
                            "Alterar",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                if (nome.isSelected()) {

                    String valor = JOptionPane.showInputDialog(
                            this,
                            "Digite o novo nome:"
                    );

                    if (valor != null && !valor.trim().isEmpty()) {

                        objBd.AlterarPessoa(
                                id,
                                "nome_completo",
                                valor
                        );
                    }
                }

                if (endereco.isSelected()) {

                    String valor = JOptionPane.showInputDialog(
                            this,
                            "Digite o novo endereço:"
                    );

                    if (valor != null && !valor.trim().isEmpty()) {

                        objBd.AlterarPessoa(
                                id,
                                "endereco",
                                valor
                        );
                    }
                }

                if (telefone.isSelected()) {

                    String valor = JOptionPane.showInputDialog(
                            this,
                            "Digite o novo telefone:"
                    );

                    if (valor != null && !valor.trim().isEmpty()) {

                        objBd.AlterarPessoa(
                                id,
                                "telefone",
                                valor
                        );
                    }
                }

                if (cpf.isSelected()) {

                    String valor = JOptionPane.showInputDialog(
                            this,
                            "Digite o novo CPF:"
                    );

                    if (valor != null && !valor.trim().isEmpty()) {

                        objBd.AlterarPessoa(
                                id,
                                "cpf",
                                valor
                        );
                    }
                }

                if (tipoSanguineo.isSelected()) {

                    String[] tipos = {
                            "A+",
                            "A-",
                            "B+",
                            "B-",
                            "O+",
                            "O-",
                            "AB+",
                            "AB-"
                    };

                    String valor = (String) JOptionPane.showInputDialog(
                            this,
                            "Selecione o novo tipo sanguíneo:",
                            "Tipo sanguíneo",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            tipos,
                            tipos[0]
                    );

                    if (valor != null) {

                        objBd.AlterarPessoa(
                                id,
                                "tipo_sanguineo",
                                valor
                        );
                    }
                }

                if (curso.isSelected()) {

                    String[] cursosAlteracao = {
                            "Direito",
                            "Ciência da Computação",
                            "Sistemas De Informação",
                            "Medicina",
                            "Psicologia",
                            "Nutrição"
                    };

                    String valor = (String) JOptionPane.showInputDialog(
                            this,
                            "Selecione o novo curso:",
                            "Curso",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            cursosAlteracao,
                            cursosAlteracao[0]
                    );

                    if (valor != null) {

                        objBd.AlterarPessoa(
                                id,
                                "curso",
                                valor
                        );
                    }
                }

                if (contatoEmergencia.isSelected()) {

                    String valor = JOptionPane.showInputDialog(
                            this,
                            "Digite o novo contato de emergência:"
                    );

                    if (valor != null && !valor.trim().isEmpty()) {

                        objBd.AlterarPessoa(
                                id,
                                "contato_emergencia",
                                valor
                        );
                    }
                }

                if (telefoneEmergencia.isSelected()) {

                    String valor = JOptionPane.showInputDialog(
                            this,
                            "Digite o novo telefone de emergência:"
                    );

                    if (valor != null && !valor.trim().isEmpty()) {

                        objBd.AlterarPessoa(
                                id,
                                "telefone_emergencia",
                                valor
                        );
                    }
                }

                if (altura.isSelected()) {

                    String valor = JOptionPane.showInputDialog(
                            this,
                            "Digite a nova altura em metros:"
                    );

                    if (valor != null && !valor.trim().isEmpty()) {

                        Double novaAltura = Double.parseDouble(valor);

                        objBd.AlterarPessoa(
                                id,
                                "altura",
                                novaAltura.toString()
                        );
                    }
                }

                if (peso.isSelected()) {

                    String valor = JOptionPane.showInputDialog(
                            this,
                            "Digite o novo peso em kg:"
                    );

                    if (valor != null && !valor.trim().isEmpty()) {

                        Double novoPeso = Double.parseDouble(valor);

                        objBd.AlterarPessoa(
                                id,
                                "peso",
                                novoPeso.toString()
                        );
                    }
                }



                JOptionPane.showMessageDialog(
                        this,
                        "Dados alterados com sucesso!",
                        "Alteração",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException e1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Digite um ID, peso ou altura válido.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (SQLException e1) {

                e1.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao alterar os dados no banco de dados.",
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
