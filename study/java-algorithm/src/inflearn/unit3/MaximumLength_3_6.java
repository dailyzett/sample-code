package inflearn.unit3;

import java.util.Scanner;

// https://cote.inflearn.com/contest/10/problem/03-06

public class MaximumLength_3_6 {
    static int n, k;
    static int[] arr;

    public int solution() {
        int answer = 0, count = 0, lt = 0;
        for (int rt = 0; rt < n; rt++) {
            if(arr[rt] == 0) count++;
            while(count > k){
                if(arr[lt] == 0) count--;
                lt++;
            }
            answer = Math.max(answer, rt - lt + 1);
        }
        return answer;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        k = sc.nextInt();
        arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        MaximumLength_3_6 T = new MaximumLength_3_6();
        System.out.println(T.solution());
    }
}
