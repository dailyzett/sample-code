package inflearn;

import java.util.Scanner;


/*
자연수 N이 입력되면 N!를 구하는 프로그램을 작성하세요.
예를 들어 5! = 5*4*3*2*1=120입니다..
*/

public class Factorial_7_3 {
    private int solution(int n) {
         if(n==1) return 1;
         else return n * solution(n - 1);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Factorial_7_3 app = new Factorial_7_3();
        System.out.println(app.solution(n));
    }

}
