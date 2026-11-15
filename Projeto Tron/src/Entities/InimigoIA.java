package Entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InimigoIA {

    public enum Personalidade {PERSEGUIDOR, EMBOSCADOR, PINCA}

    private static final int passo = 16;
    private static final  double CHANCE_ERRO = 0.10; // 10% de chance de escolher uma direção qualquer (para q n fique tão dificil)
    private static final char[] DIRECOES = {'W', 'A', 'S', 'D'};

    private final Moto moto;
    private final Personalidade personalidade;
    private final Random random = new Random();

    public InimigoIA(Moto moto, Personalidade personalidade){
        this.moto = moto;
        this.personalidade = personalidade;
    }

    public Moto getMoto(){
        return moto;
    }

    // parte da tomada de decisão
    public void decidir(Moto jogador, Moto perseguidor, List<Moto> todas){
        if(!moto.estaViva()) return;


        Point alvo = calcularAlvo(jogador, perseguidor);

        List<Character> seguras = new ArrayList<>();
        char melhor = moto.getDirecao();
        double menorDistancia = Double.MAX_VALUE;


        for (char d : DIRECOES){
            if(ehOposta(d, moto.getDirecao())) continue; // não vai dar ré

            Point proxima = proximaCasa(d);

            if(ehPerigosa(proxima, todas)) continue; // descarta direções que podem matar a mesma


            seguras.add(d);

            double distancia = proxima.distanceSq(alvo);

            if(distancia < menorDistancia){
                menorDistancia = distancia;
                melhor = d;
            }

        }

        if(seguras.isEmpty()) return; // sem saida: so aceita e bate

        // errar de próposito as vezes

        if(random.nextDouble() < CHANCE_ERRO){
            melhor = seguras.get(random.nextInt(seguras.size()));
        }

        moto.mudarDirecao(melhor);

    }

    private Point calcularAlvo(Moto jogador, Moto perseguidor){
        if(personalidade == Personalidade.EMBOSCADOR){
            return pontoNaFrente(jogador, 4);
        }

        if(personalidade == Personalidade.PINCA){
            // pivô: fica 2 casas a frente do jogador
            Point pivo = pontoNaFrente(jogador, 2);

            // alvo: o pivô reflete a partir da posição da moto perseguidora

            return new Point(
                    pivo.x + (pivo.x - perseguidor.getX()),
                    pivo.y + (pivo.y - perseguidor.getY())
            );

        }

        //PERSEGUIDOR - irá direto no jogador
        return new Point(jogador.getX(), jogador.getY());
    }

    private Point pontoNaFrente(Moto m, int casas) {
        int d = casas * passo;
        return switch (m.getDirecao()) {
            case 'W' -> new Point(m.getX(), m.getY() - d);
            case 'S' -> new Point(m.getX(), m.getY() + d);
            case 'A' -> new Point(m.getX() - d, m.getY());
            default  -> new Point(m.getX() + d, m.getY());
        };
    }

    private Point proximaCasa(char d) {
        return switch (d) {
            case 'W' -> new Point(moto.getX(), moto.getY() - passo);
            case 'S' -> new Point(moto.getX(), moto.getY() + passo);
            case 'A' -> new Point(moto.getX() - passo, moto.getY());
            default  -> new Point(moto.getX() + passo, moto.getY());
        };
    }

    private boolean ehOposta(char a, char b) {
        return (a == 'W' && b == 'S') || (a == 'S' && b == 'W')
                || (a == 'A' && b == 'D') || (a == 'D' && b == 'A');
    }

    // uma casa é perigosa se for parede ou se tiver qualquer rastro de moto viva
    private boolean ehPerigosa(Point p, List<Moto> todas) {
        if (p.x < JogoTron.LIMITE_MIN || p.x > JogoTron.LIMITE_MAX
                || p.y < JogoTron.LIMITE_MIN || p.y > JogoTron.LIMITE_MAX) {
            return true;
        }
        for (Moto m : todas) {
            if (m.estaViva() && m.getRastro().contains(p)) return true;
        }
        return false;
    }

}
