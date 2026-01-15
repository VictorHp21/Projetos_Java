package Model.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessaoKey {
    private Filme filme;
    private LocalDateTime horario;

    public SessaoKey(Filme filme, LocalDateTime horario) {
        this.filme = filme;
        this.horario = horario;
    }

    public Filme getFilme() {
        return filme;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SessaoKey sessaoKey = (SessaoKey) o;
        return Objects.equals(filme, sessaoKey.filme) && Objects.equals(horario, sessaoKey.horario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filme, horario);
    }
}
