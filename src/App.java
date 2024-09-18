import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

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

    public static double getIC(String conteudo) {
        int[] letras = new int[26];
        double IC = 0.0;

        for (int i = 0; i < conteudo.length(); i++) {
            char c = conteudo.charAt(i);
            if (c >= 'a' && c <= 'z') {
                letras[c - 'a']++;
            }
        }

        long N = conteudo.length();
        for (int f : letras) {
            IC += f * (f - 1);
        }
        IC /= N * (N - 1);

        return IC;
    }

    public static void main(String[] args) {
        String caminho = "ingles.txt";
        String conteudoOriginal = lerArquivo(caminho);

        Map<Integer, StringBuilder> mapa = dividirTexto(7, conteudoOriginal);

        char[] alfabetoOriginal = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
            'u', 'v', 'w', 'x', 'y', 'z'
        };
        
        char[] alfabetoFrequente = {
            'e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r', 'd', 
            'l', 'u', 'c', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 
            'v', 'k', 'j', 'x', 'q', 'z'
        };

        List<StringBuilder> fragmentosModificados = new ArrayList<>();

        for (int i = 0; i < mapa.size(); i++) {
            String fragmento = mapa.get(i).toString();
            String fragmentoOriginal = fragmento;

            int[] frequenciaLetras = new int[26];

            for (int j = 0; j < fragmento.length(); j++) {
                char c = fragmento.charAt(j);
                if (c >= 'a' && c <= 'z') {
                    frequenciaLetras[c - 'a']++;
                }
            }

            System.out.println("Frequência de letras no Fragmento " + i + ":");
            for (int k = 0; k < frequenciaLetras.length; k++) {
                System.out.println((char) (k + 'a') + ": " + frequenciaLetras[k]);
            }

            double IC = getIC(fragmento);
            System.out.println("Índice de Coincidência (IC) do Fragmento " + i + ": " + IC);

            Map<Character, Integer> frequenciaMap = new HashMap<>();
            for (int j = 0; j < frequenciaLetras.length; j++) {
                if (frequenciaLetras[j] > 0) {
                    frequenciaMap.put((char) (j + 'a'), frequenciaLetras[j]);
                }
            }

            List<Map.Entry<Character, Integer>> listaFrequencias = new ArrayList<>(frequenciaMap.entrySet());
            listaFrequencias.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
            
            // %%%%%%%%%%%%%%%%
            
            int aux1 = 0;
            int aux2 = 0;

            for (int val = 0; val < listaFrequencias.size(); val++) {
                if (listaFrequencias.get(0).getKey() == alfabetoOriginal[val]) {
                    aux1 = val;
                }
            }

            for (int val = 0; val < alfabetoOriginal.length; val++) {
                if (alfabetoFrequente[0] == alfabetoOriginal[val])
                aux2 = val;
            }

            int distancia = aux1 - aux2;

            System.out.println(distancia);

            // %%%%%%%%%%%%%%%

            // A PARTIR DAQUI TEM QUE AJEITAR/FAZER!

            // Substitui as letras conforme a lista de frequências
            StringBuilder resultado = new StringBuilder(fragmento);
            int indexAlfabeto = 0;
            for (Map.Entry<Character, Integer> entry : listaFrequencias) {
                if (indexAlfabeto < alfabetoFrequente.length) {
                    char letraOriginal = entry.getKey();
                    char letraSubstituta = alfabetoFrequente[indexAlfabeto];
                    for (int k = 0; k < resultado.length(); k++) {
                        if (resultado.charAt(k) == letraOriginal) {
                            resultado.setCharAt(k, letraSubstituta);
                        }
                    }
                    indexAlfabeto++;
                }
            }

            // System.out.println("Fragmento " + i + " após substituição: " + resultado.toString());

            // Adicionar fragmento modificado à lista
            fragmentosModificados.add(resultado);
        }

        // Reconstruir o texto original a partir dos fragmentos modificados
        StringBuilder textoReconstruido = new StringBuilder();
        int fragmentSize = conteudoOriginal.length() / mapa.size();
        for (int i = 0; i < mapa.size(); i++) {
            int start = i * fragmentSize;
            int end = Math.min(start + fragmentSize, conteudoOriginal.length());
            textoReconstruido.append(fragmentosModificados.get(i));
        }

        // System.out.println("Texto reconstruído após substituição: " + textoReconstruido.toString());
    }
}
