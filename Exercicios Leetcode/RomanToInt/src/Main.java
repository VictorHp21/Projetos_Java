import model.entities.Symbols;

import java.util.*;

public class Main {
    public static void main (String[] args){
        Scanner s = new Scanner(System.in);

        List<Symbols> RomanSymbols = new ArrayList<>(Arrays.asList(
                new Symbols("I", 1),
                new Symbols("V", 5),
                new Symbols("X", 10),
                new Symbols("L", 50),
                new Symbols("C", 100),
                new Symbols("D", 500),
                new Symbols("M", 1000)
        ));

        System.out.println("Digite um valor em algarismo romano: ");
        String rom = s.next();

        int valor = RomanToint(rom);

        System.out.println("O valor deste algarismo é: " + valor);


    }

    public static int RomanToint(String rom){
       rom = rom.toUpperCase();

        int result = 0;



        List<Symbols> RomanSymbols = new ArrayList<>(Arrays.asList(
                new Symbols("I", 1),
                new Symbols("V", 5),
                new Symbols("X", 10),
                new Symbols("L", 50),
                new Symbols("C", 100),
                new Symbols("D", 500),
                new Symbols("M", 1000)
        ));

        Map<String, Integer> valores = new HashMap<>();
        for (Symbols s : RomanSymbols) {
            valores.put(s.getSymbol(), s.getValor());
        }

        for(Symbols r: RomanSymbols){
            if(rom.equals(r.getSymbol())){
                result = r.getValor();
                break;
            }
        }

        for (int i = 0; i < rom.length(); i++){
            int valorAtual = valores.get(String.valueOf(rom.charAt(i)));

            if(i + 1 < rom.length()) {
                int proxValor = valores.get(String.valueOf(rom.charAt(i + 1)));

                if (valorAtual < proxValor) {
                    result -= valorAtual;
                } else if (valorAtual > proxValor) {
                    result += valorAtual;
                } else  {
                    result += valorAtual;
                }
            } else {
                result += valorAtual;
            }




        }

        return result;

        }



    }

