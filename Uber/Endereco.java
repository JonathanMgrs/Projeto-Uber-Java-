public class Endereco {
    private final String endereco;

    public Endereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser nulo ou vazio.");
        }
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    @Override
    public String toString() {
        return endereco;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Endereco that = (Endereco) obj;
        return endereco.equals(that.endereco);
    }

    @Override
    public int hashCode() {
        return endereco.hashCode();
    }
}
