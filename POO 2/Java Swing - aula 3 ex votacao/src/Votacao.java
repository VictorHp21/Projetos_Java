public class Votacao {
    private int candidato1;
    private int candidato2;
    private int votoBranco;
    private int totalVotos;


    public Votacao(){
        candidato1 = 0;
        candidato2 = 0;
        votoBranco = 0;
        totalVotos = 0;
    }

    public int Votar(int voto){
        switch (voto){
            case 1:
                candidato1++;
                break;
            case 2:
                candidato2++;
                break;
            case 3:
                votoBranco++;
                break;

            default:
                return -1;
        }

        totalVotos++;

        return 1;
    }

    public int getCandidato1() {
        return candidato1;
    }

    public int getCandidato2() {
        return candidato2;
    }

    public int getVotoBranco() {
        return votoBranco;
    }

    public int getTotalVotos() {
        return totalVotos;
    }
}
