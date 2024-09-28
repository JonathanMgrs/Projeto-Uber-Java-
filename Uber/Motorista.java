public class Motorista extends Usuario {
    private String veiculo;
    private String localizacao;

    public Motorista(int id, String nome, String veiculo, String localizacao) {
        super(id, nome);
        this.veiculo = veiculo;
        this.localizacao = localizacao;
    }

    @Override
    public void exibirDados() {
        System.out.printf("Motorista: %s, Veículo: %s, Avaliação média: %.1f, Localização: %s%n", 
                          getNome(), veiculo, calcularMediaAvaliacoes(), localizacao);
    }

    public String getVeiculo() {
        return veiculo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public static Motorista fromString(String linha) {
        String[] partes = linha.split(";");
        if (partes.length < 5) {
            throw new IllegalArgumentException("Número de partes insuficiente: " + linha);
        }
        
        try {
            Motorista motorista = new Motorista(
                Integer.parseInt(partes[0]), 
                partes[1],                  
                partes[2],                  
                partes[4]                   
            );
            motorista.adicionarAvaliacao(Double.parseDouble(partes[3].replace(',', '.')));
            return motorista;
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter dados do motorista: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public String toString() {
        return String.format("%d;%s;%s;%.1f;%s", getId(), getNome(), veiculo, calcularMediaAvaliacoes(), localizacao).replace('.', ',');
    }

}
