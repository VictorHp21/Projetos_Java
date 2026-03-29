import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /*Ex 07: Solicitar a idade de uma pessoa em anos e informar na tela a idade em meses e dias..   */

        Scanner s = new Scanner(System.in);

        DateTimeFormatter dtm = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Digite sua data de nascimento: ");
        String nascimentoTexto = s.nextLine();
        LocalDate nascimento = LocalDate.parse(nascimentoTexto, dtm);

        /* Anotações para consulta:
        ChronoUnit.DAYS.between -- obs: use este para calcular dia exatos (considerandos anos bissextos)

        Valores que podem dar erro
        utileze Period para valores exatos:
         */


        // de forma n exata
        //  int idadeDias =  ((idade - 1) * 365) + diasAnoAtual ;

        LocalDate hoje = LocalDate.now();

        long idadeDias = ChronoUnit.DAYS.between(nascimento, hoje);


        System.out.println(idadeDias);

        /*
        int idademeses = (int) (idadeDias / 30);
        int idadeAnos = (int) (idadeDias / 365);


         */

        Period periodo = Period.between(nascimento, hoje);

        int anos = periodo.getYears();
        int meses = periodo.getMonths();
        int dias = periodo.getDays();

        System.out.println("Sua idade em anos: " + anos + "\nSua idade em meses : " + meses + "\nSua" +
                " idade em " +
                "dias :" +
                " " + dias);

        System.out.println("Idade em dias totais: " + idadeDias);

        System.out.println("Idade em meses totais (aproximadamente) " + (idadeDias / 30));


    }
}
