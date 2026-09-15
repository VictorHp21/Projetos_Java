import java.awt.Container;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class InterfaceGrafica extends JFrame implements ActionListener {

    ConexaoBancoDeDados objBancoDeDados;

    // Atributos para a interface gráfica

    private JButton botaoINSERIR;
    private JButton botaoREMOVER;
    private JButton botaoALTERAR;
    private JButton botaoPESQUISAR;

    private JLabel LabelNome;
    private JLabel LabelEndereco;
    private JLabel LabelTelefone;
    private JLabel LabelResultadoPesquisa;
    private JLabel LabelMensagem;
    private JLabel LabelId;

    private JTextField TextNome;
    private JTextField TextEndereco;
    private JTextField TextTelefone;
    private JTextField TextId;

    private JTextArea listaPesquisaBancoDeDados;
    private JScrollPane scrollPesquisaBancoDeDados;

    private Container janelaprincipal;

    public InterfaceGrafica() {

        setSize(900, 370);

        setTitle("Conexao com Banco de Dados");

        janelaprincipal = getContentPane();

        janelaprincipal.setLayout(null);

        TextNome = new JTextField();
        TextEndereco = new JTextField();
        TextTelefone = new JTextField();
        TextId = new JTextField();

        // criar os componentes da interface gráfica

        botaoINSERIR = new JButton("Inserir");
        botaoREMOVER = new JButton("Remover");
        botaoALTERAR = new JButton("Alterar");
        botaoPESQUISAR = new JButton("Pesquisar");

        LabelMensagem = new JLabel("------");

        LabelNome = new JLabel("Nome");

        LabelId = new JLabel("ID");

        LabelEndereco = new JLabel("Endereço");

        LabelTelefone = new JLabel("Telefone");

        LabelResultadoPesquisa =
                new JLabel("Resultado da pesquisa no Banco de Dados");

        listaPesquisaBancoDeDados = new JTextArea();

        scrollPesquisaBancoDeDados =
                new JScrollPane(listaPesquisaBancoDeDados);

        // configurar o posicionamento dos componentes na tela

        botaoINSERIR.setBounds(10, 215, 90, 40);

        botaoREMOVER.setBounds(102, 280, 90, 40);

        botaoALTERAR.setBounds(10, 280, 90, 40);

        botaoPESQUISAR.setBounds(102, 215, 95, 40);

        LabelMensagem.setBounds(50, 170, 250, 20);

        LabelNome.setBounds(10, 40, 80, 20);

        LabelEndereco.setBounds(10, 85, 100, 20);

        LabelTelefone.setBounds(10, 130, 100, 20);

        LabelResultadoPesquisa.setBounds(480, 5, 300, 20);

        LabelId.setBounds(220, 285, 30, 30);

        scrollPesquisaBancoDeDados.setBounds(420, 25, 450, 235);

        TextNome.setBounds(100, 40, 200, 25);

        TextEndereco.setBounds(100, 85, 200, 25);

        TextTelefone.setBounds(100, 130, 200, 25);

        TextId.setBounds(240, 285, 30, 30);

        // adicionar os componentes na tela

        janelaprincipal.add(botaoINSERIR);
        janelaprincipal.add(botaoREMOVER);
        janelaprincipal.add(botaoALTERAR);
        janelaprincipal.add(botaoPESQUISAR);

        janelaprincipal.add(LabelEndereco);
        janelaprincipal.add(LabelNome);
        janelaprincipal.add(LabelTelefone);
        janelaprincipal.add(LabelResultadoPesquisa);

        janelaprincipal.add(scrollPesquisaBancoDeDados);

        janelaprincipal.add(TextNome);
        janelaprincipal.add(TextEndereco);
        janelaprincipal.add(TextTelefone);

        janelaprincipal.add(LabelMensagem);
        janelaprincipal.add(TextId);
        janelaprincipal.add(LabelId);

        // fazer com que todos os componentes fiquem visíveis na tela

        setVisible(true);

        // inserir tratamento dos eventos para os botões

        botaoINSERIR.addActionListener(this);
        botaoREMOVER.addActionListener(this);
        botaoALTERAR.addActionListener(this);
        botaoPESQUISAR.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Verificar se o usuário clicou no botão "Inserir"

        if (e.getActionCommand().equals("Inserir")) {

            Pessoa objeto =
                    new Pessoa(
                            TextNome.getText(),
                            TextEndereco.getText(),
                            TextTelefone.getText()
                    );

            try {

                objBancoDeDados = new ConexaoBancoDeDados();

                String mensagem =
                        objBancoDeDados.InserirDados(objeto);

                LabelMensagem.setText(mensagem);

            } catch (SQLException e1) {

                e1.printStackTrace();

            }

        }

        // Verificar se o usuário clicou no botão "Remover"

        if (e.getActionCommand().equals("Remover")) {

            Pessoa objeto =
                    new Pessoa(
                            TextNome.getText(),
                            TextEndereco.getText(),
                            TextTelefone.getText()
                    );

            try {

                objBancoDeDados = new ConexaoBancoDeDados();

                int id =
                        Integer.parseInt(TextId.getText());

                String mensagem =
                        objBancoDeDados.RemoverDados(id);

                LabelMensagem.setText(mensagem);

            } catch (SQLException e1) {

                e1.printStackTrace();

            }

        }

        // Verificar se o usuário clicou no botão "Alterar"

        if (e.getActionCommand().equals("Alterar")) {

            Pessoa objeto =
                    new Pessoa(
                            TextNome.getText(),
                            TextEndereco.getText(),
                            TextTelefone.getText()
                    );

            try {

                objBancoDeDados = new ConexaoBancoDeDados();

                int id =
                        Integer.parseInt(TextId.getText());

                String mensagem =
                        objBancoDeDados.AlterarDados(objeto, id);

                LabelMensagem.setText(mensagem);

            } catch (SQLException e1) {

                e1.printStackTrace();

            }

        }

        // Verificar se o usuário clicou no botão "Pesquisar"

        if (e.getActionCommand().equals("Pesquisar")) {

            try {

                objBancoDeDados = new ConexaoBancoDeDados();

                ArrayList<String> relatorioBancoDeDados =
                        objBancoDeDados.Relatorio();

                listaPesquisaBancoDeDados.setText("");

                for (String texto : relatorioBancoDeDados) {

                    listaPesquisaBancoDeDados.append(
                            texto + "\n\n"
                    );

                }

            } catch (SQLException e1) {

                e1.printStackTrace();

            }

        }

    }

}