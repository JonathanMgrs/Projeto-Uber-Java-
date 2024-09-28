public class Corrida {
    private Passageiro passageiro;
    private Endereco enderecoOrigem;
    private Endereco enderecoDestino;
    private double valor;
    private Motorista motorista;
    private double avaliacaoMotorista;
    private double avaliacaoPassageiro;
    private double distancia;

    public Corrida(Passageiro passageiro, Endereco enderecoOrigem, Endereco enderecoDestino,
                   double valor, Motorista motorista, double avaliacaoMotorista,
                   double avaliacaoPassageiro, double distancia) {
        this.passageiro = passageiro;
        this.enderecoOrigem = enderecoOrigem;
        this.enderecoDestino = enderecoDestino;
        this.valor = valor;
        this.motorista = motorista;
        this.avaliacaoMotorista = avaliacaoMotorista;
        this.avaliacaoPassageiro = avaliacaoPassageiro;
        this.distancia = distancia;
    }

    public Passageiro getPassageiro() { return passageiro; }
    public Motorista getMotorista() { return motorista; }
    public Endereco getEnderecoOrigem() { return enderecoOrigem; }
    public Endereco getEnderecoDestino() { return enderecoDestino; }
    public double getValor() { return valor; }
    public double getAvaliacaoMotorista() { return avaliacaoMotorista; }
    public double getAvaliacaoPassageiro() { return avaliacaoPassageiro; }
    public double getDistancia() { return distancia; }

    public static Corrida fromString(String linha) {
        String[] partes = linha.split(";");
        if (partes.length < 10) {
            throw new IllegalArgumentException("Número de partes insuficiente: " + linha);
        }
        return new Corrida(
            new Passageiro(Integer.parseInt(partes[0]), partes[1]),
            new Endereco(partes[4]),
            new Endereco(partes[5]),
            Double.parseDouble(partes[6].replace(',', '.')),
            new Motorista(Integer.parseInt(partes[2]), partes[3], "", ""),
            Double.parseDouble(partes[7].replace(',', '.')),
            Double.parseDouble(partes[8].replace(',', '.')),
            Double.parseDouble(partes[9].replace(',', '.'))
        );
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%s;%.2f;%.1f;%.1f;%.2f",
            passageiro.getId(),
            passageiro.getNome(),
            motorista.getId(),
            motorista.getNome(),
            enderecoOrigem.getEndereco(),
            enderecoDestino.getEndereco(),
            valor,
            avaliacaoMotorista,
            avaliacaoPassageiro,
            distancia).replace('.', ',');
    }
}
