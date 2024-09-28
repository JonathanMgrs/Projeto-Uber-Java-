import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CRUDMotorista crudMotorista = new CRUDMotorista();
        crudMotorista.carregarMotoristas("motoristas.txt");

        CRUDPassageiro crudPassageiro = new CRUDPassageiro();
        crudPassageiro.carregarPassageiros("passageiros.txt");

        CRUDEndereco crudEndereco = new CRUDEndereco();
        crudEndereco.carregarEnderecos("enderecos.txt");

        CRUDCorrida crudCorrida = new CRUDCorrida();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Olá, sejam bem-vindos ao Uber.");
        pause(3000);

        while (true) {
            System.out.println("1. Solicitar Corrida");
            System.out.println("2. Mostrar Histórico de Corridas");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    solicitarCorrida(crudMotorista, crudPassageiro, crudEndereco, crudCorrida, scanner);
                    break;
                case 2:
                    crudCorrida.listarHistorico();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void solicitarCorrida(CRUDMotorista crudMotorista, CRUDPassageiro crudPassageiro,
                                         CRUDEndereco crudEndereco, CRUDCorrida crudCorrida,
                                         Scanner scanner) {
        System.out.println("Qual é o seu nome? ");
        crudPassageiro.exibirListaPassageiros();
        String nomePassageiro = scanner.nextLine();
        Passageiro passageiro = crudPassageiro.buscarOuCriarPassageiro(nomePassageiro);
        System.out.printf("Bem-vindo(a), %s!%n", passageiro.getNome());

        System.out.println("Onde você está?");
        listarEnderecos(crudEndereco.listarEnderecos(), scanner, "Digite seu endereço atual: ");
        String enderecoAtual = scanner.nextLine();

        if (!crudEndereco.enderecoExiste(enderecoAtual)) {
            System.out.println("Endereço atual não encontrado. Aqui estão os endereços disponíveis:");
            crudEndereco.listarEnderecos();
            return;
        }

        System.out.println("Para onde desejaria ir?");
        listarEnderecos(crudEndereco.listarEnderecos(), scanner, "Digite seu destino: ", enderecoAtual);
        String enderecoDestino = scanner.nextLine();

        if (!crudEndereco.enderecoExiste(enderecoDestino)) {
            System.out.println("Endereço de destino não encontrado. Adicionando ao sistema.");
            crudEndereco.adicionarEndereco(new Endereco(enderecoDestino));
        }

        if (enderecoAtual.isEmpty() || enderecoDestino.isEmpty()) {
            System.out.println("Endereço atual ou de destino não pode estar vazio.");
            return;
        }

        double distancia;
        try {
            System.out.printf("Calculando distância de %s para %s%n", enderecoAtual, enderecoDestino);
            distancia = DistanceMatrix.getDistancia(enderecoAtual, enderecoDestino);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao calcular a distância: " + e.getMessage());
            return;
        }

        double tempoEstimado = calcularTempoEstimado(distancia);
        double valorCorrida = calcularValor(distancia, tempoEstimado);

        System.out.printf("Sua viagem deu R$%.2f (distância: %.2f km, tempo estimado: %.1f minutos)%n",
                valorCorrida, distancia, tempoEstimado);
        pause(4000);

        System.out.print("Deseja aceitar? (S/N): ");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("N")) {
            System.out.println("Corrida cancelada.");
            return;
        }

        System.out.println("Encontrando motoristas próximos...");
        pause(6000);

        int tempoMaximo = 4;
        Random random = new Random();
        int quantidade = random.nextInt(5) + 1;

        // Seleciona motoristas próximos
        List<Motorista> motoristasProximos = crudMotorista.selecionarMotoristasProximosComTempoLimite(enderecoAtual, 1, tempoMaximo, quantidade);

        if (motoristasProximos.isEmpty()) {
            System.out.println("Nenhum motorista disponível.");
            return;
        }

        System.out.println("Motoristas próximos:");
        Motorista motoristaMaisProximo = null;
        double menorTempoDeChegada = Double.MAX_VALUE;

        for (Motorista motorista : motoristasProximos) {

            double tempoDeChegada = calcularTempoEstimadoParaMotorista();
            System.out.printf("Motorista %s, Carro: %s, Nota: %.1f, Tempo de chegada: %.1f minutos%n",
                    motorista.getNome(), motorista.getVeiculo(), motorista.calcularMediaAvaliacoes(), tempoDeChegada);

            if (tempoDeChegada < menorTempoDeChegada) {
                menorTempoDeChegada = tempoDeChegada;
                motoristaMaisProximo = motorista;
            }
        }
        pause(3000);

        if (motoristaMaisProximo != null) {
            System.out.printf("Motorista %s aceitou a corrida, e está a caminho.%n", motoristaMaisProximo.getNome());
            pause(7000);

            System.out.printf("Motorista %s chegou ao local.%n", motoristaMaisProximo.getNome());
            pause(6000);

            System.out.println("Seguindo o trajeto...");
            pause(7000);

            System.out.println("Você chegou ao seu destino!");
            pause(4000);
            System.out.printf("O valor foi: R$%.2f%n", valorCorrida);
            pause(4000);

            System.out.print("Qual seria sua forma de pagamento? (Dinheiro/Cartao/Pix): ");
            String formaPagamento = scanner.nextLine();
            if (!isFormaPagamentoValida(formaPagamento)) {
                System.out.println("Forma de pagamento inválida. Encerrando o programa.");
                return;
            }

            System.out.println("Efetuando o pagamento...");
            pause(7000);
            System.out.println("Pagamento Concluído.");
            pause(4000);

            double avaliacao = solicitarAvaliacao(scanner);
            int avaliacaoAleatoria = new Random().nextInt(10) + 1;
            System.out.println("Motorista avaliou o passageiro com nota: " + avaliacaoAleatoria);

            Endereco enderecoOrigem = new Endereco(enderecoAtual);
            Endereco enderecoDestinoObj = new Endereco(enderecoDestino);
            Corrida corrida = new Corrida(passageiro, enderecoOrigem, enderecoDestinoObj, valorCorrida, motoristaMaisProximo, avaliacao, avaliacaoAleatoria, distancia);

            crudCorrida.adicionarCorrida(corrida);
            crudPassageiro.atualizarAvaliacaoPassageiro(passageiro.getId(), avaliacaoAleatoria);
            crudPassageiro.salvarPassageiros("passageiros.txt");

            motoristaMaisProximo.adicionarAvaliacao(avaliacao);
            crudMotorista.salvarMotoristas("motoristas.txt");

            System.out.println("A Uber agradece você!");
        }
    }

    public static double calcularValor(double distancia, double tempo) {
        double taxaBase = 3.0;
        double precoPorKm = 1.50;
        double precoPorMinuto = 0.50;

        double valorTotal = taxaBase + (precoPorKm * distancia) + (precoPorMinuto * tempo);
        
        return valorTotal;
    }

    private static double calcularTempoEstimado(double distancia) {
        double velocidadeMedia = 30.0;
        return (distancia / velocidadeMedia) * 60;
    }

    private static double calcularTempoEstimadoParaMotorista() {

        Random random = new Random();
        return 1 + (random.nextDouble() * 3);
    }

    private static void listarEnderecos(List<Endereco> enderecos, Scanner scanner, String mensagem, String... enderecoAtual) {
        if (enderecoAtual.length > 0) {
            String enderecoAtualStr = enderecoAtual[0];
            System.out.printf("Endereços disponíveis (exceto %s):%n", enderecoAtualStr);
            for (Endereco endereco : enderecos) {
                if (!endereco.getEndereco().equals(enderecoAtualStr)) {
                    System.out.println(endereco.getEndereco());
                }
            }
        } else {
            System.out.println("Endereços disponíveis:");
            for (Endereco endereco : enderecos) {
                System.out.println(endereco.getEndereco());
            }
        }
        System.out.print(mensagem);
    }

    private static boolean isFormaPagamentoValida(String formaPagamento) {
        return formaPagamento.equalsIgnoreCase("Dinheiro") ||
               formaPagamento.equalsIgnoreCase("Cartao") ||
               formaPagamento.equalsIgnoreCase("Pix");
    }

    private static double solicitarAvaliacao(Scanner scanner) {
        System.out.print("Como você avaliaria a corrida de 1 a 10? ");
        double avaliacao = scanner.nextDouble();
        return avaliacao;
    }

    private static void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
