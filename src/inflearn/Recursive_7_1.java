package inflearn;

import java.util.Scanner;

/*
자연수 N이 입력되면 재귀함수를 이용하여 1부터 N까지를 출력하는 프로그램을 작성하세요
*/
public class Recursive_7_1 {
    private void solution(int n) {
        if (n == 0) return;
        else {
            solution(n - 1);
            System.out.print(n + " ");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Recursive_7_1 app = new Recursive_7_1();
        app.solution(n);
    }
}
