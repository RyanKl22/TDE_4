import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ComparacaoAlgoritmosOrdenacao extends JFrame {
    private JButton botaoBubbleSort;
    private JButton botaoInsertionSort;
    private JButton botaoQuickSort;
    private JTable tabelaResultados;
    private String[] arquivos = {"arquivoscsv/aleatorio_100.csv", "arquivoscsv/aleatorio_1000.csv", "arquivoscsv/aleatorio_10000.csv",
            "arquivoscsv/crescente_100.csv", "arquivoscsv/crescente_1000.csv", "arquivoscsv/crescente_10000.csv",
            "arquivoscsv/decrescente_100.csv", "arquivoscsv/decrescente_1000.csv", "arquivoscsv/decrescente_10000.csv"};
    private DefaultTableModel modeloTabela;
    private Map<String, Long[]> temposExecucao;

    public ComparacaoAlgoritmosOrdenacao() {
        setTitle("Comparação de Algoritmos de Ordenação");
        setLayout(new BorderLayout());
        setSize(800, 400);

        temposExecucao = new HashMap<>();

        JPanel painelBotoes = new JPanel();
        botaoBubbleSort = new JButton("BubbleSort");
        botaoInsertionSort = new JButton("InsertionSort");
        botaoQuickSort = new JButton("QuickSort");

        painelBotoes.add(botaoBubbleSort);
        painelBotoes.add(botaoInsertionSort);
        painelBotoes.add(botaoQuickSort);

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Tipo de Conjunto de Dados");
        modeloTabela.addColumn("BubbleSort (ms)");
        modeloTabela.addColumn("InsertionSort (ms)");
        modeloTabela.addColumn("QuickSort (ms)");

        for (String nomeArquivo : arquivos) {
            String tipoConjunto = nomeArquivo.replace(".csv", "")
                    .replace("arquivoscsv/", "")
                    .replace("_", " ")
                    .replace("aleatorio", "aleatório")
                    .replace("crescente", "crescente")
                    .replace("decrescente", "decrescente")
                    .concat(" Registros");

            tipoConjunto = tipoConjunto.substring(0, 1).toUpperCase() + tipoConjunto.substring(1);

            modeloTabela.addRow(new Object[]{tipoConjunto, null, null, null});
        }

        tabelaResultados = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaResultados);

        add(painelBotoes, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        botaoBubbleSort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executarAlgoritmoOrdenacao("BubbleSort");
            }
        });

        botaoInsertionSort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executarAlgoritmoOrdenacao("InsertionSort");
            }
        });

        botaoQuickSort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executarAlgoritmoOrdenacao("QuickSort");
            }
        });
    }

    public void executarAlgoritmoOrdenacao(String algoritmo) {
        for (int i = 0; i < arquivos.length; i++) {
            String nomeArquivo = arquivos[i];

            try {
                int[] dados = LeitorCSV.lerDadosDoCSV(nomeArquivo);
                long tempoAlgoritmo = -1;

                if (algoritmo.equals("BubbleSort")) {
                    tempoAlgoritmo = medirTempoExecucao(dados.clone(), AlgoritmosOrdenacao::bubbleSort);
                } else if (algoritmo.equals("InsertionSort")) {
                    tempoAlgoritmo = medirTempoExecucao(dados.clone(), AlgoritmosOrdenacao::insertionSort);
                } else if (algoritmo.equals("QuickSort")) {
                    tempoAlgoritmo = medirTempoExecucao(dados.clone(), (d) -> AlgoritmosOrdenacao.quickSort(d, 0, d.length - 1));
                }

                atualizarTabela(i, algoritmo, tempoAlgoritmo);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private long medirTempoExecucao(int[] dados, Algoritmo ordenacao) {
        long tempoInicial = System.nanoTime();
        ordenacao.ordenar(dados);
        long tempoFinal = System.nanoTime();
        return tempoFinal - tempoInicial;
    }

    private void atualizarTabela(int indiceArquivo, String algoritmo, long tempoAlgoritmo) {
        int colunaAlgoritmo = -1;
        switch (algoritmo) {
            case "BubbleSort":
                colunaAlgoritmo = 1;
                break;
            case "InsertionSort":
                colunaAlgoritmo = 2;
                break;
            case "QuickSort":
                colunaAlgoritmo = 3;
                break;
        }

        double tempoEmMilissegundos = tempoAlgoritmo / 1_000_000.0;

        modeloTabela.setValueAt(tempoAlgoritmo == -1 ? "N/A" : String.format("%.3f", tempoEmMilissegundos), indiceArquivo, colunaAlgoritmo);
    }

    public static void main(String[] args) {
        ComparacaoAlgoritmosOrdenacao app = new ComparacaoAlgoritmosOrdenacao();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    @FunctionalInterface
    interface Algoritmo {
        void ordenar(int[] dados);
    }
}
