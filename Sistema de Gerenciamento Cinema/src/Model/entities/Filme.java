package Model.entities;

import java.util.Objects;

public class Filme {
    private String titulo;
    private String genero;
    private int duracaoMin;

    public Filme(){}

    public Filme(String titulo, String genero, int duracaoMin) {
        this.titulo = titulo;
        this.genero = genero;
        this.duracaoMin = duracaoMin;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracaoMin() {
        return duracaoMin;
    }

    public void setDuracaoMin(int duracaoMin) {
        this.duracaoMin = duracaoMin;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Filme filme = (Filme) o;
        return Objects.equals(titulo, filme.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(titulo);
    }

    @Override
    public String toString() {
        return "Filme{" +
                "titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", duracaoMin=" + duracaoMin +
                '}';
    }
}
