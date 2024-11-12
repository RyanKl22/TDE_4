import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LeitorCSV {

    public static int[] lerDadosDoCSV(String caminhoArquivo) {
        BufferedReader br = null;
        String linha;
        int numeroLinha = 0;
        ArrayList<Integer> valores = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(caminhoArquivo));

            while ((linha = br.readLine()) != null) {
                if (numeroLinha == 0) {
                    numeroLinha++;
                    continue;
                }
                linha = linha.trim();

                try {
                    int valor = Integer.parseInt(linha);
                    valores.add(valor);
                } catch (NumberFormatException e) {
                    System.out.println("Valor inválido encontrado: " + linha);
                }
                numeroLinha++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int[] resultado = new int[valores.size()];
        for (int i = 0; i < valores.size(); i++) {
            resultado[i] = valores.get(i);
        }

        return resultado;
    }

    public static void lerTodosArquivosCSV(String pasta) {
        File pastaDir = new File(pasta);
        if (pastaDir.exists() && pastaDir.isDirectory()) {
            File[] arquivos = pastaDir.listFiles((dir, nome) -> nome.endsWith(".csv"));

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    System.out.println("Lendo o arquivo: " + arquivo.getName());
                    int[] dados = lerDadosDoCSV(arquivo.getAbsolutePath());

                    if (dados != null) {
                        for (int valor : dados) {
                            System.out.println(valor);
                        }
                    }
                }
            }
        } else {
            System.out.println("A pasta especificada não existe ou não é um diretório.");
        }
    }

    public static void main(String[] args) {
        String caminhoPasta = "C:\\Users\\Ryan Kloss\\IdeaProjects\\TDE4_RA4\\arquivoscsv";
        File pasta = new File(caminhoPasta);
        if (!pasta.exists()) {
            System.out.println("O diretório não foi encontrado: " + caminhoPasta);
            return;
        }

        lerTodosArquivosCSV(caminhoPasta);
    }
}
