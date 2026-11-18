package Entities;

import java.awt.*;
import java.io.InputStream;

public class HudTron {
    public static final int ALTURA_BARRA = 56;

    private static final Color CIANO       = new Color(0, 234, 255);
    private static final Color LARANJA     = new Color(255, 140, 0);
    private static final Color FUNDO       = new Color(4, 8, 24);
    private static final Color TEXTO_SUAVE = new Color(170, 200, 215);

    private static final Font FONTE_PADRAO = new Font(Font.MONOSPACED, Font.BOLD, 12);
    private static Font fonteBase = FONTE_PADRAO;


    static {
        try (InputStream in = HudTron.class.getResourceAsStream("/Imagens e sons/fonte.ttf")) {
            if (in != null) {
                fonteBase = Font.createFont(Font.TRUETYPE_FONT, in);
            }
        } catch (Exception e) {
            System.out.println("Fonte personalizada não carregada: usando a fonte padrão.");
        }
    }

    private HudTron() {}   // classe só de métodos estáticos


    public static void desenharBarra(Graphics2D g2, int largura, Cronometro cronometro, Pontuacao pontuacao) {
        Graphics2D g = (Graphics2D) g2.create();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        g.setColor(FUNDO);
        g.fillRect(0, 0, largura, ALTURA_BARRA);


        g.setColor(new Color(0, 160, 255, 22));
        for (int x = 0; x < largura; x += 16) g.drawLine(x, 0, x, ALTURA_BARRA);
        for (int y = 0; y < ALTURA_BARRA; y += 16) g.drawLine(0, y, largura, y);


        int yLinha = ALTURA_BARRA - 2;
        g.setStroke(new BasicStroke(6f));
        g.setColor(comAlfa(CIANO, 60));
        g.drawLine(0, yLinha, largura, yLinha);
        g.setStroke(new BasicStroke(2f));
        g.setColor(CIANO);
        g.drawLine(0, yLinha, largura, yLinha);
        g.setStroke(new BasicStroke(1f));

        int segundos = cronometro.getSegundos();
        int total    = pontuacao.getTotal(segundos);
        int recorde  = pontuacao.getRecordeExibido(segundos);

        int lb = largura / 3;   // largura de cada bloco
        bloco(g, "TEMPO",   cronometro.formatar(),          lb / 2,              lb, CIANO);
        bloco(g, "PONTOS",  String.format("%06d", total),   lb + lb / 2,         lb, LARANJA);
        bloco(g, "RECORDE", String.format("%06d", recorde), 2 * lb + lb / 2,     lb, CIANO);

        // separadores verticais
        g.setColor(comAlfa(CIANO, 70));
        g.drawLine(lb, 10, lb, ALTURA_BARRA - 12);
        g.drawLine(2 * lb, 10, 2 * lb, ALTURA_BARRA - 12);

        g.dispose();
    }

    private static void bloco(Graphics2D g, String rotulo, String valor, int centroX, int larguraBloco, Color cor) {

        Font fr = fonte(rotulo, 11f);
        g.setFont(fr);
        g.setColor(TEXTO_SUAVE);
        int xr = centroX - g.getFontMetrics().stringWidth(rotulo) / 2;
        g.drawString(rotulo, xr, 18);


        Font fv = fonteQueCabe(g, valor, 22f, larguraBloco - 20);
        textoNeon(g, valor, centroX, 44, fv, cor, 1f);
    }



    public static void desenharEvento(Graphics2D g2, int largura, Pontuacao pontuacao) {
        String evento = pontuacao.getEventoAtivo();
        if (evento == null) return;

        Graphics2D g = (Graphics2D) g2.create();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font f = fonteQueCabe(g, evento, 22f, largura - 40);
        textoNeon(g, evento, largura / 2, 44, f, LARANJA, pontuacao.getOpacidadeEvento());

        g.dispose();
    }


    public static void desenharFimDeJogo(Graphics2D g2, int largura, int altura, boolean venceu,
                                         String mensagem, Cronometro cronometro, Pontuacao p) {
        Graphics2D g = (Graphics2D) g2.create();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // escurece a arena
        g.setColor(new Color(0, 0, 10, 200));
        g.fillRect(0, 0, largura, altura);

        // vitória = ciano (cor do jogador); derrota = laranja (cor dos inimigos)
        Color cor = venceu ? CIANO : LARANJA;

        // painel com borda de neon
        int px = 30, py = 50, pw = largura - 60, ph = altura - 100;
        g.setColor(new Color(4, 8, 24, 235));
        g.fillRoundRect(px, py, pw, ph, 18, 18);
        g.setStroke(new BasicStroke(7f));
        g.setColor(comAlfa(cor, 50));
        g.drawRoundRect(px, py, pw, ph, 18, 18);
        g.setStroke(new BasicStroke(2f));
        g.setColor(cor);
        g.drawRoundRect(px, py, pw, ph, 18, 18);

        int cx = largura / 2;
        int segundos = cronometro.getSegundos();

        // mensagem de vitória / derrota
        textoNeon(g, mensagem, cx, py + 62, fonteQueCabe(g, mensagem, 38f, pw - 40), cor, 1f);

        // pontuação final
        String rotuloFinal = "PONTUAÇÃO FINAL";
        g.setFont(fonte(rotuloFinal, 13f));
        g.setColor(TEXTO_SUAVE);
        g.drawString(rotuloFinal, cx - g.getFontMetrics().stringWidth(rotuloFinal) / 2, py + 110);

        String total = String.format("%06d", p.getTotal(segundos));
        textoNeon(g, total, cx, py + 160, fonteQueCabe(g, total, 44f, pw - 60), LARANJA, 1f);

        // detalhamento pontos
        int xe = px + 28;
        int xd = px + pw - 28;
        int y = py + 208;
        linha(g, "MOTOS DESTRUÍDAS (" + p.getMotosDestruidas() + ")", "+" + p.getPontosMortes(), xe, xd, y);
        y += 28;
        linha(g, "TEMPO SOBREVIVIDO (" + segundos + "s)", "+" + p.getPontosTempo(segundos), xe, xd, y);
        y += 28;
        if (venceu) {
            linha(g, "BÔNUS DE VITÓRIA", "+" + p.getBonusVitoria(), xe, xd, y);
            y += 28;
        }

        // recorde
        y += 12;
        boolean aceso = (System.currentTimeMillis() / 400) % 2 == 0;
        if (p.isNovoRecorde()) {
            String r = "NOVO RECORDE!";
            textoNeon(g, r, cx, y, fonte(r, 18f), CIANO, aceso ? 1f : 0.45f);
        } else {
            String r = "RECORDE  " + String.format("%06d", p.getRecordeExibido(segundos));
            g.setFont(fonte(r, 14f));
            g.setColor(TEXTO_SUAVE);
            g.drawString(r, cx - g.getFontMetrics().stringWidth(r) / 2, y);
        }

        // dica piscando
        String dica = "PRESSIONE ENTER PARA JOGAR NOVAMENTE";
        textoNeon(g, dica, cx, py + ph - 24, fonteQueCabe(g, dica, 13f, pw - 30), CIANO, aceso ? 1f : 0.35f);

        g.dispose();
    }

    private static void linha(Graphics2D g, String esquerda, String direita, int xe, int xd, int y) {
        g.setFont(fonte(direita, 15f));
        int largDireita = g.getFontMetrics().stringWidth(direita);
        g.setColor(CIANO);
        g.drawString(direita, xd - largDireita, y);

        g.setFont(fonteQueCabe(g, esquerda, 13f, xd - xe - largDireita - 16));
        g.setColor(TEXTO_SUAVE);
        g.drawString(esquerda, xe, y);
    }



    // texto centralizado em centroX,
    private static void textoNeon(Graphics2D g, String texto, int centroX, int baseY,
                                  Font fonte, Color cor, float opacidade) {
        g.setFont(fonte);
        int x = centroX - g.getFontMetrics().stringWidth(texto) / 2;

        // halo: o mesmo texto deslocado ao redor (com transparência)
        g.setColor(comAlfa(cor, (int) (38 * opacidade)));
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                if (dx != 0 || dy != 0) g.drawString(texto, x + dx, baseY + dy);
            }
        }


        g.setColor(comAlfa(clarear(cor), (int) (255 * opacidade)));
        g.drawString(texto, x, baseY);
    }

    // fonte do arcade;
    private static Font fonte(String texto, float tamanho) {
        Font f = fonteBase.deriveFont(Font.BOLD, tamanho);
        if (f.canDisplayUpTo(texto) != -1) {
            f = FONTE_PADRAO.deriveFont(Font.BOLD, tamanho);
        }
        return f;
    }


    private static Font fonteQueCabe(Graphics2D g, String texto, float tamanhoMax, int larguraMax) {
        Font f = fonte(texto, tamanhoMax);
        for (float t = tamanhoMax; t > 8f; t -= 1f) {
            f = fonte(texto, t);
            if (g.getFontMetrics(f).stringWidth(texto) <= larguraMax) break;
        }
        return f;
    }

    private static Color comAlfa(Color c, int alfa) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), Math.max(0, Math.min(255, alfa)));
    }

    private static Color clarear(Color c) {
        return new Color(
                c.getRed()   + (255 - c.getRed())   / 2,
                c.getGreen() + (255 - c.getGreen()) / 2,
                c.getBlue()  + (255 - c.getBlue())  / 2
        );
    }
}
