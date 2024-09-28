import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CRUDMotorista {
    private final List<Motorista> motoristas = new ArrayList<>();

    public CRUDMotorista() {
        carregarMotoristas("motoristas.txt");
    }

    public List<Motorista> selecionarMotoristasProximos(int quantidade) {
        List<Motorista> motoristasAleatorios = new ArrayList<>(motoristas);
        Collections.shuffle(motoristasAleatorios);
        return motoristasAleatorios.subList(0, Math.min(quantidade, motoristasAleatorios.size()));
    }

    public List<Motorista> selecionarMotoristasProximosComTempoLimite(String enderecoAtual, int tempoMinimo, int tempoMaximo, int quantidade) {
        List<Motorista> motoristasProximos = new ArrayList<>();
        
        for (Motorista motorista : motoristas) {
            double tempoDeChegada = calcularTempoEstimadoParaMotorista(enderecoAtual, motorista.getLocalizacao());
            if (tempoDeChegada >= tempoMinimo && tempoDeChegada <= tempoMaximo) {
                motoristasProximos.add(motorista);
            }
        }
        
        return motoristasProximos.stream().limit(quantidade).collect(Collectors.toList());
    }

    private double calcularTempoEstimadoParaMotorista(String localizacaoPassageiro, String localizacaoMotorista) {
        double distancia = calcularDistancia(localizacaoPassageiro, localizacaoMotorista);
        double velocidadeMedia = 0.5;
        return distancia / velocidadeMedia;
    }

    private double calcularDistancia(String localizacaoPassageiro, String localizacaoMotorista) {
        return 2.0;
    }

    public void carregarMotoristas(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");

                if (dados.length < 5) {
                    System.err.println("Erro ao criar motorista a partir da string: " + linha + " - Número de partes insuficiente.");
                    continue;
                }

                try {
                    int id = Integer.parseInt(dados[0].trim());
                    String nome = dados[1].trim();
                    String veiculo = dados[2].trim();
                    double avaliacao = Double.parseDouble(dados[3].trim().replace(",", "."));
                    String localizacao = dados[4].trim();

                    Motorista motorista = new Motorista(id, nome, veiculo, localizacao);
                    motorista.adicionarAvaliacao(avaliacao);
                    atualizarMotorista(motorista);
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao formatar número: " + e.getMessage() + " - linha: " + linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar motoristas: " + e.getMessage());
        }
    }

    public void salvarMotoristas(String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo, false))) {
            motoristas.forEach(motorista -> writer.println(motorista.toString()));
        } catch (IOException e) {
            System.out.println("Erro ao salvar motoristas: " + e.getMessage());
        }
    }

    public void adicionarAvaliacao(Motorista motorista, double novaAvaliacao) {
        motorista.adicionarAvaliacao(novaAvaliacao);
        atualizarMotorista(motorista);
        salvarMotoristas("motoristas.txt");
    }

    public void atualizarMotorista(Motorista motorista) {
        motoristas.removeIf(m -> m.getId() == motorista.getId());
        motoristas.add(motorista);
    }

    public Motorista buscarOuCriarMotorista(String nome, String localizacao) {
        return motoristas.stream()
                .filter(motorista -> motorista.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElseGet(() -> criarNovoMotorista(nome, localizacao));
    }

    private Motorista criarNovoMotorista(String nome, String localizacao) {
        Motorista novoMotorista = new Motorista(motoristas.size() + 1, nome, "Veículo Desconhecido", localizacao);
        motoristas.add(novoMotorista);
        return novoMotorista;
    }
}
