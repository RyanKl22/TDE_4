    public class AlgoritmosOrdenacao {

        public static void bubbleSort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
        }

        public static void insertionSort(int[] arr) {
            int n = arr.length;
            for (int i = 1; i < n; i++) {
                int chave = arr[i];
                int j = i - 1;
                while (j >= 0 && arr[j] > chave) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                }
                arr[j + 1] = chave;
            }
        }

        public static void quickSort(int[] arr, int baixo, int alto) {
            if (baixo < alto) {
                int indicePivo = particionar(arr, baixo, alto);
                quickSort(arr, baixo, indicePivo - 1);
                quickSort(arr, indicePivo + 1, alto);
            }
        }

        private static int particionar(int[] arr, int baixo, int alto) {
            int pivo = arr[alto];
            int i = (baixo - 1);

            for (int j = baixo; j < alto; j++) {
                if (arr[j] <= pivo) {
                    i++;
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }

            int temp = arr[i + 1];
            arr[i + 1] = arr[alto];
            arr[alto] = temp;

            return i + 1;
        }
    }
