package inflearn.unit7;


import java.util.Scanner;

/*
    1) 피보나치 수열을 출력한다. 피보나치 수열이란 앞의 2개의 수를 합하여 다음 숫자가 되는
    수열이다.
    2) 입력은 피보나치 수열의 총 항의 수 이다. 만약 7이 입력되면 1 1 2 3 5 8 13을 출력하면
    된다
*/
public class DfsFibonacci_7_4 {
    static int[] fibo;
    public int dfs(int n) {
        if(fibo[n] > 0) return fibo[n];
        if (n == 1) return fibo[n] = 1;
        else if (n == 2) return fibo[n] = 1;
        else return fibo[n] = dfs(n - 2) + dfs(n - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DfsFibonacci_7_4 app = new DfsFibonacci_7_4();
        int n = sc.nextInt();
        fibo = new int[n + 1];
        app.dfs(n);
        for(int i = 1; i <= n; i++){
            System.out.print(fibo[i] + " ");
        }
    }
}
