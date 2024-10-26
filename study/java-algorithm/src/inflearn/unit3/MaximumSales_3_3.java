package inflearn.unit3;

import java.util.Scanner;

public class MaximumSales_3_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();

        int[] nArray = new int[n];
        for (int i = 0; i < n; i++) {
            nArray[i] = sc.nextInt();
        }

        MaximumSales_3_3 app = new MaximumSales_3_3();
        System.out.println(app.solution(n, k, nArray));
    }

    public int solution(int n, int k, int[] nArray) {
        int answer = 0, sum = 0;

        for (int i = 0; i < k; i++) {
            sum += nArray[i];
        }
        answer = sum;
        for (int i = k; i < n; i++) {
            sum += (nArray[i] - nArray[i - k]);
            answer = Math.max(answer, sum);
        }
        return answer;
    }
}
