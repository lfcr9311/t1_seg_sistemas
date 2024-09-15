import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public static void main(String[] args) {

        int letras[] = new int[26];

        String caminho = "20201-teste1.txt";
        String conteudo = lerArquivo(caminho);
        int n = conteudo.length();

        for (int i = 0; i < conteudo.length(); i++) {
            char c = conteudo.charAt(i);
            if (c >= 'a' && c <= 'z') {
                letras[c - 'a']++;
            } else if (c >= 'A' && c <= 'Z') {
                letras[c - 'A']++;
            }
        }

        System.out.println("Letras encontradas no arquivo:");
        for (int i = 0; i < letras.length; i++) {
            if (letras[i] > 0) {
                System.out.println((char) (i + 'a') + ": " + letras[i]);
            }
        }
        System.out.println(conteudo.length());

        double ic = 0;

        for (int i = 0; i < 25; i++) {
            double freqIndividual = conteudo.charAt(i);
            double result = (freqIndividual * (freqIndividual - 1) / (n * (n - 1)));
            ic = ic + result;
        }

        System.out.println(ic);

        Linguas lingua;

        if (ic == 1.73) {
            lingua = Linguas.INGLES;
        } else {
            lingua = Linguas.PORTUGUES;
        }

        System.out.println("Língua: " + lingua);
    }
}
