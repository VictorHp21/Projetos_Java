package Config;

public final class Config {

    private Config(){};

    // configurando para tamanho de tela 720*720

    public static final int CELULA = 24; // o resto das dimensões são calculadas a partir do tamanhi de celula
    public static final int COLUNAS = 30;
    public static final int LINHAS = 30;
    // horizontal e vertical


    public static final int LARGURA = COLUNAS * CELULA;
    public static final int ALTURA  = LINHAS * CELULA; // 30 * 24 = 720

    public static final int LIMITE_MIN   = CELULA;
    public static final int LIMITE_MAX_X = LARGURA - CELULA;
    public static final int LIMITE_MAX_Y = ALTURA - CELULA;

    public static final double ESCALA = CELULA / 16.0; // em relação ao tamanho feito de 16px

    public static final int   TAMANHO_MOTO     = CELULA * 3 / 2;
    public static final float ESPESSURA_BRILHO = (float) (10 * ESCALA);
    public static final float ESPESSURA_RAIO   = (float) (4 * ESCALA);

    public static boolean foraDaArena(int x, int y) {
        return x < LIMITE_MIN || x > LIMITE_MAX_X
                || y < LIMITE_MIN || y > LIMITE_MAX_Y;
    }

}
