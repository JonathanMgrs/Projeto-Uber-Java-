import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDCorrida {
    private List<Corrida> historicoCorridas = new ArrayList<>();

    public void adicionarCorrida(Corrida corrida) {
        historicoCorridas.add(corrida);
    }

    public void listarHistorico() {
        if (historicoCorridas.isEmpty()) {
            System.out.println("Nenhuma corrida registrada.");
            return;
        }

        historicoCorridas.forEach(this::imprimirCorrida);
    }

    private void imprimirCorrida(Corrida corrida) {
        System.out.printf("Corrida de %s (%.1f); %s para %s (Distancia: %.2f km); Motorista %s (%.1f); Carro: %s; Valor: R$%.2f; Av. do motorista: %.1f; Av. do passageiro: %.1f%n",
            corrida.getPassageiro().getNome(),
            corrida.getPassageiro().calcularMediaAvaliacoes(),
            corrida.getEnderecoOrigem().getEndereco(),
            corrida.getEnderecoDestino().getEndereco(),
            corrida.getDistancia(),
            corrida.getMotorista().getNome(),
            corrida.getMotorista().calcularMediaAvaliacoes(),
            corrida.getMotorista().getVeiculo(),
            corrida.getValor(),
            corrida.getAvaliacaoMotorista(),
            corrida.getAvaliacaoPassageiro());
    }

    public void carregarCorridas(String arquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Corrida corrida = Corrida.fromString(linha);
                adicionarCorrida(corrida);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar corridas: " + e.getMessage());
        }
    }

    public void salvarCorridas(String arquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo, false))) {
            for (Corrida corrida : historicoCorridas) {
                writer.println(corrida);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar corridas: " + e.getMessage());
        }
    }
}
