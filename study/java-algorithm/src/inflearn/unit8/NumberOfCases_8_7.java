package inflearn.unit8;

import java.util.Scanner;

/*조합의 경우수(메모이제이션)

▣ 입력설명
첫째 줄에 자연수 n(3<=n<=33)과 r(0<=r<=n)이 입력됩니다.
▣ 출력설명
첫째 줄에 조합수를 출력합니다.
▣ 입력예제 1
5 3
▣ 출력예제 1
10
▣ 입력예제 2
33 19
▣ 출력예제 2
818809200
* */

public class NumberOfCases_8_7 {
    static int n, r;
    static int answer = 0;
    static int[][] memo = new int[35][35];

    public static int DFS(int n, int r) {
        if(memo[n][r] > 0) return memo[n][r];
        if (r == 0 || r == n) return 1;
        else {
            return memo[n][r] = DFS(n - 1, r - 1) + DFS(n - 1, r);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        r = scanner.nextInt();

        answer = DFS(n, r);
        System.out.println(answer);
    }
}
