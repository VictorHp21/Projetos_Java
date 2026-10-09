public class Contador {
    private int valor = 0;

    // A palavra-chave synchronized faz com que apenas uma thread por vez possa executar um método sincronizado sobre o mesmo objeto, respeitando o monitor desse objeto

    public synchronized void incrementar(){
        valor++;
    }

    public synchronized int getValor(){
        return valor;
    }
}
