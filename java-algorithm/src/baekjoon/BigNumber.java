package baekjoon;

import java.util.Scanner;

/*
백준 10757
https://www.acmicpc.net/problem/10757
*/

public class BigNumber {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        String a = sc.next();
        String b = sc.next();

        int digit = 0;
        for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; i--, j--) {
            int temp = digit;
            if (i >= 0) {
                temp += a.charAt(i) - '0';
            }

            if (j >= 0) {
                temp += b.charAt(j) - '0';
            }

            if (temp < 10) digit = 0;
            else{
                digit = 1;
                temp %= 10;
            }

            builder.append(temp);
        }
        if(digit == 1) builder.append(1);
        System.out.println(builder.reverse());
    }
}
