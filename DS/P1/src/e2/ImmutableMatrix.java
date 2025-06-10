package e2;

public class ImmutableMatrix {

    private final int[][] matrix;
    public ImmutableMatrix(int[][] arr) {
        if(arr==null || arr.length==0){
            throw new IllegalArgumentException("Argumentos no validos");
        }

        for(int p=1;p<arr.length;p++){
            if(arr[p]==null || arr[p].length!=arr[p-1].length){
                throw new IllegalArgumentException("Matriz argumento irregular");
            }
        }

        matrix=new int[arr.length][];
        //final int[][] matrix = new int[arr.length][];

        for(int i=0;i<arr.length;i++){
            matrix[i]=new int[arr[i].length];
            System.arraycopy(arr[i], 0, matrix[i], 0, arr[i].length);
        }
    }

    public ImmutableMatrix(int filas, int columnas){
        if(filas<=0 || columnas<=0) throw new IllegalArgumentException("Argumentos no validos");

        int dato=1;

        matrix=new int[filas][];

        for(int i=0;i<matrix.length;i++){
            matrix[i]=new int[columnas];
            for(int j=0;j<matrix[i].length;j++){
                matrix[i][j]=dato;
                dato++;
            }
        }
    }
    @Override
    public String toString(){
        StringBuilder resultado=new StringBuilder();

        for (int[] i : matrix) {
            resultado.append("[");
            for (int j = 0; j < i.length; j++) {
                resultado.append(i[j]);
                if (j != (i.length) - 1) {
                    resultado.append(", ");
                }
            }
            resultado.append("]\n");
        }

        return resultado.toString();
    }

    public int at(int fila, int columna) {
        if(fila<0 || fila >=matrix.length || columna<0 || columna>=matrix[0].length){
            throw new IllegalArgumentException("Coordenadas fuera de la matriz");
        }
        return matrix[fila][columna];
    }

    public int rowCount(){
        return matrix.length;
    }

    public int columnCount(){return matrix[0].length;}

    public int[][] toArray2D(){
        int[][] result = new int[this.rowCount()][];
        for(int i=0;i<this.rowCount();i++){
            result[i]=new int[this.columnCount()];
            for(int j=0;j<this.columnCount();j++){
                result[i][j]=this.at(i,j);
            }
        }
        return result;
    }

    public ImmutableMatrix reverse(){
        if(this.rowCount()==0){
            throw new IllegalArgumentException("Matriz no valida");
        }
        else {
            int[][] reversa = new int[this.rowCount()][];
            for (int x = 0; x < this.rowCount(); x++) {
                reversa[x] = new int[this.columnCount()];
                for (int y = 0, b = this.columnCount()-1; y < this.columnCount(); y++, b--) {
                    reversa[x][b] = this.at(x, y);
                }
            }
            return new ImmutableMatrix(reversa);
        }
    }

    public ImmutableMatrix transpose(){
        if(this.rowCount()==0){
            throw new IllegalArgumentException("Matriz no valida");
        }
        else {
            int[][] traspuesta = new int[this.columnCount()][this.rowCount()];
            for (int x = 0; x < this.rowCount(); x++) {
                for (int y = 0; y < this.columnCount(); y++) {
                    traspuesta[y][x] = this.at(x, y);
                }
            }
            return new ImmutableMatrix(traspuesta);
        }
    }

    public ImmutableMatrix reshape(int newColumns) {
        if (this.rowCount() == 0) {
            throw new IllegalArgumentException("Matriz no valida");
        } else {
            int casillas = this.columnCount() * this.rowCount();
            if (casillas % newColumns != 0) {
                throw new IllegalArgumentException("Valores no validos");
            } else {
                int filas = casillas / newColumns;
                int[][] newMatriz = new int[filas][newColumns];
                int a = 0, b = 0;
                for (int x = 0; x < this.rowCount(); x++) {
                    for (int y = 0; y < this.columnCount(); y++) {
                        newMatriz[a][b] = this.at(x, y);
                        b++;
                        if (b == newColumns) {
                            b = 0;
                            a++;
                        }
                    }
                }
                return new ImmutableMatrix(newMatriz);
            }

        }
    }
}