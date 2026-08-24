import javax.swing.*;

public class PrimeiraTela extends JFrame {

    // atributos
    private JLabel Nome;
    private JTextField txtNome;

    public PrimeiraTela(){
        Nome = new JLabel("Nome: ");
        txtNome = new JTextField();

        setSize(400,200);
        setTitle("Tela inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null); // limpar o layout para configurar os componentes nos locais corretos
        // "Não quero que o Layout Manager organize os componentes. Eu mesmo vou definir a posição deles."

        // configurar área do label e caixa de texto
        Nome.setBounds(10,10,100,25);
        txtNome.setBounds(50,10,200,25);

        // adiciona label e a caixa de texto na tela
        getContentPane().add(Nome);
        getContentPane().add(txtNome);

    }

}
