import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class App {

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

    public static double calcularIndiceCoincidencia(String conteudo) {
        int[] frequenciaLetras = new int[26];
        long totalLetras = 0;

        for (int i = 0; i < conteudo.length(); i++) {
            char c = Character.toLowerCase(conteudo.charAt(i));
            if (c >= 'a' && c <= 'z') {
                frequenciaLetras[c - 'a']++;
                totalLetras++;
            }
        }

        double ic = 0.0;
        for (int i = 0; i < frequenciaLetras.length; i++) {
            ic += frequenciaLetras[i] * (frequenciaLetras[i] - 1);
        }
        

        if (totalLetras > 1) {
            ic /= (double) (totalLetras * (totalLetras - 1));
        }

        return ic;
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
                char letraOriginal = (char) (calcularShift(letraCifrada, letraChave) + base);
                textoOriginal.append(letraOriginal);
            } else {
                textoOriginal.append(letraCifrada);
            }
        }

        return textoOriginal.toString();
    }

    public static void salvarTextoEmArquivo(String texto, String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write(texto);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        String caminho = "ingles.txt";
        String conteudo = lerArquivo(caminho);

        Map<Integer, StringBuilder> mapa = dividirTexto(7, conteudo);
        StringBuilder chave = new StringBuilder();
        char letraMaisFrequenteIngles = 'e';

        for (int i = 0; i < mapa.size(); i++) {
            String substring = mapa.get(i).toString();
            char letraMaisFrequente = letraMaisFrequente(substring);

            int shift = calcularShift(letraMaisFrequente, letraMaisFrequenteIngles);
            char letraChave = (char) ('a' + shift);
            chave.append(letraChave);

            double indiceCoincidencia = calcularIndiceCoincidencia(substring);
            System.out.println("Substring " + i + ": Letra mais frequente = " + letraMaisFrequente
                    + ", Shift = " + shift + ", Letra da chave = " + letraChave
                    + ", Índice de Coincidência = " + indiceCoincidencia);
        }

        String chaveFinal = chave.toString();
        System.out.println("Chave de criptografia do Vigenère: " + chaveFinal);

        String textoOriginal = descriptografarVigenere(conteudo, chaveFinal);

        String nomeArquivoSaida = "textoDescriptografado.txt";
        salvarTextoEmArquivo(textoOriginal, nomeArquivoSaida);
    }
}