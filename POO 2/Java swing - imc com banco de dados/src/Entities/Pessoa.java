package Entities;

public class Pessoa {
    private int Id;
    private String nomeCompleto;
    private String endereço;
    private String telefone;
    private String CPF;
    private String tipoSanguineo;
    private String curso;
    private String contatoDeEmergencia;
    private String telefoneEmergencia;
    private Double altura;
    private Double peso;
    private Double imc;


    public Pessoa(String nomeCompleto, String endereço, String telefone, String CPF, String tipoSanguineo, String curso, String contatoDeEmergencia, String telefoneEmergencia, Double altura, Double peso) {
        this.nomeCompleto = nomeCompleto;
        this.endereço = endereço;
        this.telefone = telefone;
        this.CPF = CPF;
        this.tipoSanguineo = tipoSanguineo;
        this.curso = curso;
        this.contatoDeEmergencia = contatoDeEmergencia;
        this.telefoneEmergencia = telefoneEmergencia;
        this.altura = altura;
        this.peso = peso;

        this.imc = calculaImc(peso, altura);
    }

    // contrutor para pegar dado bd:
    public Pessoa(
            String nomeCompleto,
            String endereço,
            String telefone,
            String CPF,
            String tipoSanguineo,
            String curso,
            String contatoDeEmergencia,
            String telefoneEmergencia,
            Double altura,
            Double peso,
            Double imc
    ) {
        this.nomeCompleto = nomeCompleto;
        this.endereço = endereço;
        this.telefone = telefone;
        this.CPF = CPF;
        this.tipoSanguineo = tipoSanguineo;
        this.curso = curso;
        this.contatoDeEmergencia = contatoDeEmergencia;
        this.telefoneEmergencia = telefoneEmergencia;
        this.altura = altura;
        this.peso = peso;
        this.imc = imc;
    }




    public Double calculaImc(Double altura, Double peso){
        Double imc = peso / (altura * altura);
        return imc;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getContatoDeEmergencia() {
        return contatoDeEmergencia;
    }

    public void setContatoDeEmergencia(String contatoDeEmergencia) {
        this.contatoDeEmergencia = contatoDeEmergencia;
    }

    public String getTelefoneEmergencia() {
        return telefoneEmergencia;
    }

    public void setTelefoneEmergencia(String telefoneEmergencia) {
        this.telefoneEmergencia = telefoneEmergencia;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }
}
