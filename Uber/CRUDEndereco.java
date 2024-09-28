import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CRUDEndereco {
    private List<Endereco> enderecos;

    public CRUDEndereco() {
        enderecos = new ArrayList<>();
    }

    public void carregarEnderecos(String nomeArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    enderecos.add(new Endereco(linha.trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar endereços: " + e.getMessage());
        }
    }

    public boolean enderecoExiste(String endereco) {
        return enderecos.stream().anyMatch(e -> e.getEndereco().equalsIgnoreCase(endereco));
    }

    public List<Endereco> listarEnderecos() {
        return new ArrayList<>(enderecos);
    }

    public void adicionarEndereco(Endereco endereco) {
        if (!enderecoExiste(endereco.getEndereco())) {
            enderecos.add(endereco);
        }
    }

    
}
