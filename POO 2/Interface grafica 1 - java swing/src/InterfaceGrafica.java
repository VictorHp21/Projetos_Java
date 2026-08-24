import javax.swing.*;

public class InterfaceGrafica extends JFrame {
    public InterfaceGrafica(){
        setSize(400,500);
        setTitle("Tela Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ao click do x na janela o programa também será encerrado.
        setVisible(true);
    }
}
