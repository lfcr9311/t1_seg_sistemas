import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class App {

    static String alfabeto = "abcdefghijklmnopqrstuvwxyz";

    public static boolean isLetter(char letra) {
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

    public static double[] getIC(String conteudo) {
        int letras[] = new int[26];
        double frequencia[] = new double[26];

        for (int i = 0; i < conteudo.length(); i++) {
            char c = conteudo.charAt(i);
            if (c >= 'a' && c <= 'z') {
                letras[c - 'a']++;
            } else if (c >= 'A' && c <= 'Z') {
                letras[c - 'A']++;
            }
        }

        for (int i = 0; i < 26; i++) {
            frequencia[i] = (double) letras[i] / conteudo.length();
        }

        return frequencia;
    }

    public static void main(String[] args) {

        int letras[] = new int[26];
        double frequencia[] = new double[26];

        String caminho = "20201-teste2.txt";
        String conteudo = lerArquivo(caminho);

        Map<Integer, StringBuilder> mapa = new HashMap<>();

        mapa = dividirTexto(7, conteudo);

        for (int i = 0; i < mapa.size(); i++) {
            getIC(mapa.get(i).toString());

            int[] frequenciaLetras = new int[26];

            conteudo = mapa.get(i).toString();

            for (int j = 0; j < conteudo.length(); j++) {
                char c = conteudo.charAt(j);
                if (c >= 'a' && c <= 'z') {
                    frequenciaLetras[c - 'a']++;
                }
            }

            // System.out.println("Frequência de letras no Fragmento " + i + ":");
            // for (int k = 0; k < frequenciaLetras.length; k++) {
            //     System.out.println((char) (k + 'a') + ": " + frequenciaLetras[k]);
            // }

            long N = conteudo.length();
            double IC = 0.0;
            for (int f : frequenciaLetras) {
                IC += f * (f - 1);
            }
            IC /= N * (N - 1);
            System.out.println("Índice de Coincidência (IC) do Fragmento " + i + ": " + IC);
        }
    }
}