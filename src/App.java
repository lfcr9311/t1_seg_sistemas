import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {

    static String alfabeto = "abcdefghijklmnopqrstuvwxyz";

    public static boolean isLetter(char letra){
         return alfabeto.indexOf(letra) != -1;
    }

    public static int letrasContagem(String conteudo) {
        int cont = 0;
        for (int i = 0; i < conteudo.length(); i++) {
            if (isLetter(conteudo.charAt(i))) {
                cont++;
            }
        }
        return cont;
    }

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

    public static double getIC(String conteudo){
        int[] contLetras = new int[26];

        for (int i = 0; i < alfabeto.length(); i++) {
            int cont = 0;
            for (int j = 0; j < conteudo.length(); j++) {
                if(conteudo.charAt(j) == alfabeto.charAt(i)){
                    cont ++;
                }
            }
            contLetras[i] = cont;
        }

        double result = 0;
        for (int i = 0; i < contLetras.length; i++) {
            int ni = contLetras[i];
            result += ni * (ni -1);
        }

        int N = letrasContagem(conteudo);
        result = result/ (N * (N - 1));
        return result;
    }

    public static void main(String[] args) {

        int letras[] = new int[26];
        double frequencia[] = new double[26];

        String caminho = "20201-teste1.txt";
        String conteudo = lerArquivo(caminho);

        double total = getIC(conteudo);
        System.out.println("IC: " + total);

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
        System.out.println("Letras totais: " + conteudo.length());

        double somaMedia = 0;

        for (int i = 0; i < letras.length; i++) {
            double frequenciaLetra = (double) letras[i] / conteudo.length();
            frequencia[i] = frequenciaLetra;
            System.out.println((char) (i + 'a') + ": " + frequenciaLetra);
            somaMedia = somaMedia + frequenciaLetra;
        }

        double n = conteudo.length();
        double result1 = 0;
        double ic = 0;

        for (int i = 0; i < letras.length; i++) {
            result1 = Math.abs((letras[i] * (letras[i] - 1))/ (n * (n - 1)));
            ic = ic + result1;
        }

        System.out.println(result1);
        System.out.println(ic);

    }
}