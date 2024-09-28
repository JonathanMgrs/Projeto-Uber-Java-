public class Passageiro extends Usuario {

    public Passageiro(int id, String nome) {
        super(id, nome);
    }

    @Override
    public void exibirDados() {
        System.out.println("Passageiro: " + nome + ", Avaliação média: " + getAvaliacaoFormatada());
    }

    public static Passageiro fromString(String linha) {
        String[] partes = linha.split(";");
        Passageiro passageiro = new Passageiro(Integer.parseInt(partes[0]), partes[1]);
        passageiro.adicionarAvaliacao(Double.parseDouble(partes[2].replace(',', '.')));
        return passageiro;
    }

    public String getAvaliacaoFormatada() {
        return String.format("%.1f", calcularMediaAvaliacoes()).replace('.', ',');
    }
}
