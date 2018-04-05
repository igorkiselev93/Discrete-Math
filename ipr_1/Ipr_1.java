package dm.ipr_1;

public class Ipr_1 {
    public static void main(String[] args) {
        //Исходные данные
        double[] x= {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0,1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9,2.0};
        double[] y={-0.16,0.01,0.1,0.16,0.05,0.35,0.19,0.5,0.74,1.03,1.06,1.49,1.79,2.03,2.22,2.5,2.88,3.21,3.63,3.90};
        //Точка, в которой вычисляются значения функции интерполяции
        double q=0.1;
        //Переменная для хранения значения полинома Лагранжа
        double l;
        //Переменная для хранения значения полинома влияния i-ого узла
        double s;
        //Число узлов интерполяции
        int n=20;
        //Шаг интерполяции
        double h=0.1;
        /////////////////////////////////////////////////////////////////////////////////////////
        //Апроксимация с помощью многочлена Лагранжа
        System.out.println("-------------------------//Апроксимация с помощью многочлена Лагранжа//-------------------------");
        //Цикл для вычисления многочлена Лагранжа во всех промежуточных точках
        while (q<=x[n-1]+0.01){//Пока не достигнута последняя точка
            l=0.0;//Первоначальное значение многочлена Лагранжа
            for (int i = 0; i < n; i++) {
                s=1.0;
                for (int j = 0; j < n; j++) {
                    if (j!=i){//Вычисление полинома влияния i-ого узла
                        s=s*(q-x[j])/(x[i]-x[j]);
                    }
                }
                l=l+y[i]*s;
            }
            System.out.printf("Значение функции в точке х= %.2f: y= %.4f\n",q,l);
            //Увеличиваем значение точки интерполяции
            q=q+0.05;
        }
        System.out.println();
        /////////////////////////////////////////////////////////////////////////////////////////
        //Апроксимация с помощью полиномов Ньютона
        System.out.println("-------------------------//Апроксимация с помощью полинома Ньютона//-------------------------");
        q=0.1;
        while (q<=x[n-1]+0.01) {
            //Массив для хранения кф-тов а полинома Ньютона
            double[] a = new double[n];
            //Вычисление кф-тов а полинома Ньютона
            for (int i = 0; i < n; i++) {
                if (i == 0) a[i] = y[i];
                else {
                    a[i] = dif(y, i, 0) / (factorial(i) * Math.pow(h, i));
                }
            }
            //Вспомогательная переменная
            double mul = 1;
            //Переменная для хранения значения полинома Ньютона
            double ll = 0;
            for (int i = 0; i < n; i++) {
                if (i == 0) ll = a[0];
                else {
                    mul = mul * (q - x[i - 1]);
                    ll = ll + a[i] * mul;
                }
            }
            System.out.printf("Значение функции в точке х= %.2f: y= %.4f\n",q,ll);
            //Увеличиваем значение точки интерполяции
            q=q+0.05;
        }
        System.out.println();
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    //Вспомогательный метод для вычисления конечной разности n-ого порядка
    public static double dif (double[] y, int n, int i){
        double dif;
        if (n==1)
            dif=y[i+1]-y[i];
        else
            dif = dif(y,n-1,i+1)-dif(y,n-1,i);
        return dif;
    }
    //Вспомогательный метод для вычисления факториала
    public static long factorial (long n){
        if (n==0) n=1;
        else
            for (long i = n-1; i > 0; i--) {
                n=i*n;
        }
        return n;
    }
}