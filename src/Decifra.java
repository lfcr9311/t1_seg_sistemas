import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Decifra {

    public static String lerArquivo(String caminhoArquivo) {
        StringBuilder conteudo = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                conteudo.append(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return conteudo.toString();
    }

    public static String decifrar(String textoCifrado, String chave) {
        StringBuilder textoDecifrado = new StringBuilder();
        int chaveTamanho = chave.length();
        
        for (int i = 0; i < textoCifrado.length(); i++) {
            char letraCifrada = textoCifrado.charAt(i);
            char letraChave = chave.charAt(i % chaveTamanho);
            
            if (letraCifrada >= 'a' && letraCifrada <= 'z') {
                int deslocamento = (letraCifrada - letraChave + 26) % 26;
                char letraDecifrada = (char) ('a' + deslocamento);
                textoDecifrado.append(letraDecifrada);
            } else {
                textoDecifrado.append(letraCifrada);
            }
        }
        return textoDecifrado.toString();
    }

    public static void main(String[] args) {
        String caminho = "ingles.txt";
        String conteudoCifrado = lerArquivo(caminho);
        String chave = "avelino";

        String textoDecifrado = decifrar(conteudoCifrado, chave);
        System.out.println("Texto decifrado: ");
        System.out.println(textoDecifrado);
    }
}
