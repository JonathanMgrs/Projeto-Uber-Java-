public class DistanceMatrix {
    private static final String[] ENDERECOS = {
        "Casa", "Escola", "Shopping", "Parque", "Centro", "Hospital", "Lan House", "Casa do amigo"
    };

    private static final double[][] DISTANCIAS = {
        //   Casa   Escola   Shopping  Parque  Centro  Hospital  Lan House  Casa do amigo
            { 0.0,   1.2,     2.5,     3.0,    4.1,     2.8,      1.5,      5.0 }, // Casa
            { 1.2,   0.0,     3.3,     2.8,    1.5,     3.0,      2.1,      4.0 }, // Escola
            { 2.5,   3.3,     0.0,     2.1,    2.8,     1.9,      3.2,      4.5 }, // Shopping
            { 3.0,   2.8,     2.1,     0.0,    3.5,     2.3,      2.0,      3.0 }, // Parque
            { 4.1,   1.5,     2.8,     3.5,    0.0,     3.2,      3.0,      5.0 }, // Centro
            { 2.8,   3.0,     1.9,     2.3,    3.2,     0.0,      1.5,      2.2 }, // Hospital
            { 1.5,   2.1,     3.2,     2.0,    3.0,     1.5,      0.0,      2.5 }, // Lan House
            { 5.0,   4.0,     4.5,     3.0,    5.0,     2.2,      2.5,      0.0 }   // Casa do amigo
    };

    public static double getDistancia(String enderecoOrigem, String enderecoDestino) {
        int indexOrigem = encontrarIndiceEndereco(enderecoOrigem);
        int indexDestino = encontrarIndiceEndereco(enderecoDestino);

        if (indexOrigem == -1 || indexDestino == -1) {
            throw new IllegalArgumentException("Endereço não encontrado.");
        }

        return DISTANCIAS[indexOrigem][indexDestino];
    }

    private static int encontrarIndiceEndereco(String endereco) {
        for (int i = 0; i < ENDERECOS.length; i++) {
            if (ENDERECOS[i].equalsIgnoreCase(endereco)) {
                return i;
            }
        }
        return -1;
    }
}
