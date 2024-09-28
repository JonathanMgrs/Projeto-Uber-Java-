import java.util.ArrayList;
import java.util.List;

public abstract class Usuario implements Avaliavel {
    protected int id;
    protected String nome;
    protected List<Double> avaliacoes;

    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.avaliacoes = new ArrayList<>();
    }

    public void adicionarAvaliacao(double avaliacao) {
        avaliacoes.add(avaliacao);
    }

    public double calcularMediaAvaliacoes() {
        return avaliacoes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public abstract void exibirDados();
}
