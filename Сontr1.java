package dm;
import java.util.Scanner;

    //Исходные данные
    //Кф-ты а
    //2.18 2.44 2.49
    //2.17 2.31 2.49
    //3.15 3.22 3.17
    //Кф-ты b
    //-4.34 -3.91 -5.27

public class Сontr1 {
    ///////////////////////////////////////////////////////////////////
    //Массив для хранения матрицы кф-тов А
    static double[][] a;
    //Переменная для хранения размерности матрицы А
    static int n;
    //Объявление массивов для хранения матриц кф-тов В и Х
    static double[] b;
    static double[] x;
    //Вспомогательный массив для хранения номеров переставленных столбцов
    static int[] indexOfColumns={0,1,2,3,4,5,6,7};
    ///////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        ///////////////////////////////////////////////////////////////////
        //Часть программы, отвечающая за обработку пользовательского ввода
        //Ввод размерности матрицы А
        System.out.print("Введите размерность матрицы A (после этого нажмите Enter): ");
        Scanner sc0=new Scanner(System.in);
        n= sc0.nextInt();
        a=new double[n][n];
        b=new double[n];
        x=new double[n];
        //Объявление массива для применения метода Холецкого
        double[][] aHol=new double[n][n];
        double[] bHol=new double[n];
        //Ввод кф-тов а
        for (int i = 0; i < n; i++) {
            System.out.printf("Введите через пробел коэфициенты матрицы A для %d-ой строки (после этого нажмите Enter): ", i);
            Scanner sc1=new Scanner(System.in);
            String line=sc1.nextLine();
            String[] res=line.split(" ");
            for (int j = 0; j < res.length; j++) {
                a[i][j] =Double.parseDouble(res[j]);
                aHol[i][j]=a[i][j];
            }
        }
        //Ввод кф-тов b
        Scanner sc2=new Scanner(System.in);
        System.out.print("Введите коэфициенты матрицы B (после этого нажмите Enter): ");
        String line2=sc2.nextLine();
        String[] res2=line2.split(" ");
        for (int i = 0; i < b.length; i++) {
            b[i]=Double.parseDouble(res2[i]);
            bHol[i]=b[i];
        }
        System.out.println();
        //Конец обработки пользовательского ввода
        ///////////////////////////////////////////////////////////////////
        //Вызов метода Гаусса
       // gaus(a,b,n);
        //Вызов метода Холецкого
        holecki(aHol,bHol);
    }
    ///////////////////////////////////////////////////////////////////
    //Метод Гаусса
    public static double[][] gaus(double[][] a, double[] b, int n){
        double[] x = new double[n];
        //Прямой ход метода Гаусса
        for (int k = 0; k < n; k++) {
            //Вызов метода для выбора главного элемента
            makeMaxMatrix(a, k);
            double memA=a[k][k];
            b[k]=b[k]/a[k][k];
            for (int j = k; j < n; j++) {
                a[k][j]=a[k][j]/memA;
                 System.out.printf("A[%-1d][%-1d]=%-10.5f ",k,j,a[k][j]);
            }
             System.out.printf("B[%-1d]=%-10.5f \n",k,b[k]);
            for (int i = k+1; i < n; i++) {
                double memAi=a[i][k];
                b[i]=b[i]-b[k]*a[i][k];
                for (int j = k; j < n; j++) {
                    a[i][j]=a[i][j]-a[k][j]*memAi;
                     System.out.printf("A[%-1d][%-1d]=%-10.5f ",i,j,a[i][j]);
                }
                 System.out.printf("B[%-1d]=%-10.5f \n",i,b[i]);
            }
              System.out.println();
        }
        //Вывод на экран матриц А и В
        System.out.println("----------------//Решение СЛАУ методом Гаусса//----------------");
        System.out.println("Итоговая верхняя треугольная матрица и столбец свободных членов:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("A[%-1d][%-1d]=%-10.5f ",i,j,a[i][j]);
            }
            System.out.printf("B[%-1d]=%-10.5f \n",i,b[i]);
        }
        //Обратный ход метода Гаусса
        for (int i = n-1; i >=0; i--) {
            double sum=0;
            for (int j = i+1; j < n; j++) {
                sum=sum+a[i][j]*x[j];
            }
            x[i]=b[i]-sum;
        }
        //Вывод на экран матрицы Х
        System.out.println("Решение системы уравнений:");
        for (int i = 0; i < n; i++) {
            int indexFind=findColumns(indexOfColumns,i);
            System.out.printf("X[%-1d]=%-10.5f \n",i,x[indexFind]);
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            indexOfColumns[i]=i;
        }
        return a;
    }
    ///////////////////////////////////////////////////////////////////
    //Метод Холецкого
    public static double[][] holecki(double[][] a,double[] b){
        double[][] hol = new double[a.length][a.length];
        //объявление массивов для хранения треугольных матриц U и L
        double[][] u = new double[a.length][a.length];
        double[][] l = new double[a.length][a.length];
        //объявление массивов для хранения матриц Y и X
        double[] y=new double[a.length];
        double[] x=new double[a.length];
        //цикл для вычисления элементов треугольных матриц U и L
        for (int i = 0; i < a.length; i++) {
            double temp1;
            double temp2;
            for (int j = 0; j < a.length; j++) {
                if (i<=j) {
                    //временная вспомагательная переменная
                    temp1 = 0;
                    for (int k = 0; k <= a.length-1; k++) {
                        temp1 = temp1 + l[i][k] * u[k][j];
                    }
                    u[i][j] = a[i][j] - temp1;
                }
                else {
                    //временная вспомагательная переменная
                    temp2 = 0;
                    for (int k = 0; k <= a.length-1; k++) {
                        temp2 = temp2 + l[i][k] * u[k][j];
                    }
                    l[i][j] = (a[i][j] - temp2)/u[j][j];
                }
            }
            l[i][i]=1;
        }
        System.out.println("----------------//Решение СЛАУ методом Холецкого//----------------");
        System.out.println("Треугольная матрица U(верхняя):");
        //Вывод на экран верхней треугольной матрицы U
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                System.out.printf("U[%-1d][%-1d]=%-10.5f ",i,j,u[i][j]);
            }
            System.out.println();
        }
        //Вывод на экран нижней треугольной матрицы L
        System.out.println("Треугольная матрица L(нижняя):");
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                System.out.printf("L[%-1d][%-1d]=%-10.5f ",i,j,l[i][j]);
            }
            System.out.println();
        }
        System.out.println("Матрица Y:");
        //Вычисление и вывод на экран  матрицы Y
        for (int i = 0; i < a.length; i++) {
            double temp3=0;
            for (int k = 0; k <=i-1 ; k++) {
                temp3=temp3+l[i][k]*y[k];
            }
            y[i]=b[i]-temp3;
            System.out.printf("Y[%-1d]=%-10.5f \n",i,y[i]);
        }
        //Вычисление и вывод на экран  матрицы X
        System.out.println("Решение системы уравнений:");
        for (int i = a.length-1; i >= 0; i--) {
            double temp4=0;
            for (int k = i+1; k < a.length; k++) {
                temp4=temp4+u[i][k]*x[k];
            }
            x[i]=(y[i]-temp4)/u[i][i];
        }
        for (int i = 0; i < a.length; i++) {
            System.out.printf("X[%-1d]=%-10.5f \n",i,x[i]);
        }
        return hol;
    }
    ///////////////////////////////////////////////////////////////////
    //Вспомогательный метод для поиска
    //номера столбца наибольшего по модулю элемента строки
    public static int findIndexMax(double[] arr){
        double max=arr[0];
        int index=0;
        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i])>Math.abs(max)){
                max=arr[i];
                index=i;
            }
        }
        return index;
    }
    //Вспомогательный метод для поиска
    //номера переставленного столбца
    public static int findColumns(int[] arr, int ar){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]==ar) return i;
        }
        return -1;
    }
    //Вспомогательный метод для перестановки столбца
    //с наибольшим по модулю элементом
    public static double[][] makeMaxMatrix(double[][] matr, int str){
        int index=findIndexMax(matr[str]);
        for (int i = 0; i < matr.length; i++) {
            double memA=matr[i][index];
            matr[i][index]=matr[i][str];
            matr[i][str]=memA;
            //Здесь запоминаются номера переставленных столбцов
            int memColumns=indexOfColumns[index];
            indexOfColumns[index]=indexOfColumns[str];
            indexOfColumns[str]=memColumns;
        }
        return a;
    }
    ///////////////////////////////////////////////////////////////////
    //Метод квадратных корней
    public static double[][] holecky(double[][] a)
    {
        double[][] hol = new double[a.length][];
        double[] y = new double[a.length];
        double[] x = new double[a.length];
        for (int i = 0; i < a.length; i++)
        {//hol - треугольная матрица, поэтому в i-ой строке i+1 элементов
            hol[i] = new double[i + 1];
            double temp;
            //Сначала вычисляем значения элементов слева от диагонального элемента,
            //так как эти значения используются при вычислении диагонального элемента.
            for (int j = 0; j < i; j++){
                temp = 0;
                for (int k = 0; k < j; k++){
                    temp += hol[i][k] * hol[j][k];
                }
                hol[i][j] = (a[i][j] - temp) / hol[j][j];
                System.out.print(hol[i][j]+" ");
            }
            //Находим значение диагонального элемента
            temp = a[i][i];
            for (int k = 0; k < i; k++){
                temp -= hol[i][k] * hol[i][k];
            }
            hol[i][i] = Math.sqrt(temp);
            System.out.println(hol[i][i]);
        }
        return hol;
    }
}

