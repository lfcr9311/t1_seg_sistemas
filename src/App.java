import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class App {

    // Método para ler o arquivo
    public static String lerArquivo(String caminhoArquivo) {
        StringBuilder conteudo = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return conteudo.toString();
    }

    // Método para dividir o texto em p fragmentos
    public static Map<Integer, StringBuilder> dividirTexto(int p, String conteudo) {
        Map<Integer, StringBuilder> fragmento = new HashMap<>();

        for (int i = 0; i < p; i++) {
            fragmento.put(i, new StringBuilder());
        }

        for (int j = 0; j < conteudo.length(); j++) {
            int index = j % p;
            fragmento.get(index).append(conteudo.charAt(j));
        }

        return fragmento;
    }

    public static char letraMaisFrequente(String conteudo) {
        int[] frequenciaLetras = new int[26];

        for (int i = 0; i < conteudo.length(); i++) {
            char c = Character.toLowerCase(conteudo.charAt(i));
            if (c >= 'a' && c <= 'z') {
                frequenciaLetras[c - 'a']++;
            }
        }

        int maxFreq = 0;
        char letraMaisFrequente = 'a';

        for (int i = 0; i < 26; i++) {
            if (frequenciaLetras[i] > maxFreq) {
                maxFreq = frequenciaLetras[i];
                letraMaisFrequente = (char) (i + 'a');
            }
        }

        return letraMaisFrequente;
    }

    public static int calcularShift(char letra1, char letra2) {
        return (letra1 - letra2 + 26) % 26;
    }

    public static String descriptografarVigenere(String textoCifrado, String chave) {
        StringBuilder textoOriginal = new StringBuilder();
        int chaveLength = chave.length();

        for (int i = 0; i < textoCifrado.length(); i++) {
            char letraCifrada = textoCifrado.charAt(i);
            char letraChave = chave.charAt(i % chaveLength);

            if (Character.isLetter(letraCifrada)) {
                char base = Character.isLowerCase(letraCifrada) ? 'a' : 'A';
                // Subtraindo o shift da chave para obter a letra original
                char letraOriginal = (char) ((letraCifrada - letraChave + 26) % 26 + base);
                textoOriginal.append(letraOriginal);
            } else {
                textoOriginal.append(letraCifrada);
            }
        }

        return textoOriginal.toString();
    }

    public static void main(String[] args) {

        String caminho = "portugues.txt";
        String conteudo = lerArquivo(caminho);

        // Fragmenta o texto em 7 partes
        Map<Integer, StringBuilder> mapa = dividirTexto(7, conteudo);

        StringBuilder chave = new StringBuilder();
        char letraMaisFrequenteIngles = 'e';
        char letraMaisFrequentePortugues = 'e';

        for (int i = 0; i < mapa.size(); i++) {
            String fragmento = mapa.get(i).toString();
            char letraMaisFrequente = letraMaisFrequente(fragmento);

            //int shift = calcularShift(letraMaisFrequente, letraMaisFrequenteIngles);
            int shift = calcularShift(letraMaisFrequente, letraMaisFrequentePortugues);

            char letraChave = (char) ('a' + shift);
            chave.append(letraChave);

            System.out.println("Fragmento " + i + ": Letra mais frequente = " + letraMaisFrequente 
                                + ", Shift = " + shift + ", Letra da chave = " + letraChave);
        }

        String chaveFinal = chave.toString();
        System.out.println("Chave de criptografia do Vigenère: " + chaveFinal);

        String textoOriginal = descriptografarVigenere(conteudo, chaveFinal);
        //System.out.println("Texto Original Descriptografado:\n" + textoOriginal);
    }
}
