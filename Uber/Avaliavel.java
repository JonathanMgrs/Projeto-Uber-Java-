public interface Avaliavel {
    void adicionarAvaliacao(double nota);
    double calcularMediaAvaliacoes();

    default void mostrarMensagem() {
        System.out.println("Avaliação realizada.");
    }
}
