package Model.entities;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class Tarefa {
    private String nome;
    private String descricao;
    private Date data;
    private String prioridade;
    private String status;

    public Tarefa(String nome, String descricao, Date data, String prioridade, String status) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.prioridade = prioridade;
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return Objects.equals(nome, tarefa.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nome);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return "Tarefa: \n" +
                "Nome='" + nome + '\'' +
                ", Descricao='" + descricao + '\'' +
                ", Data=" + formatter.format(data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) +
                ", Prioridade='" + prioridade + '\'' +
                ", Status='" + status;
    }
}
