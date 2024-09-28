import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDPassageiro {
    private final List<Passageiro> passageiros = new ArrayList<>();

    public void carregarPassageiros(String arquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Passageiro passageiro = criarPassageiroDeLinha(linha);
                if (passageiro != null) passageiros.add(passageiro);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar passageiros: " + e.getMessage());
        }
    }

    private Passageiro criarPassageiroDeLinha(String linha) {
        try {
            String[] partes = linha.split(",");
            if (partes.length < 3) return null;
            Passageiro passageiro = new Passageiro(Integer.parseInt(partes[0]), partes[1]);
            passageiro.adicionarAvaliacao(Double.parseDouble(partes[2].replace(',', '.')));
            return passageiro;
        } catch (NumberFormatException e) {
            System.out.println("Erro ao processar linha: " + linha + ". " + e.getMessage());
            return null;
        }
    }

    public void salvarPassageiros(String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo, false))) {
            passageiros.forEach(p -> writer.println(String.format("%d,%s,%s", p.getId(), p.getNome(), p.getAvaliacaoFormatada())));
        } catch (IOException e) {
            System.out.println("Erro ao salvar passageiros: " + e.getMessage());
        }
    }

    public Passageiro buscarOuCriarPassageiro(String nome) {
        return passageiros.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElseGet(() -> criarNovoPassageiro(nome));
    }

    private Passageiro criarNovoPassageiro(String nome) {
        Passageiro novoPassageiro = new Passageiro(passageiros.size() + 1, nome);
        passageiros.add(novoPassageiro);
        return novoPassageiro;
    }

    public void atualizarAvaliacaoPassageiro(int id, double avaliacao) {
        passageiros.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .ifPresent(p -> p.adicionarAvaliacao(avaliacao));
    }

    public void exibirListaPassageiros() {
        passageiros.forEach(p -> System.out.printf("%s (%s)%n", p.getNome(), p.getAvaliacaoFormatada()));
    }
}
