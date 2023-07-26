package inflearn.unit8;

import java.util.Scanner;

/*
조합 구하기

1부터 N까지 번호가 적힌 구슬이 있습니다. 이 중 M개를 뽑는 방법의 수를 출력하는 프로그
램을 작성하세요.
▣ 입력설명
첫 번째 줄에 자연수 N(3<=N<=10)과 M(2<=M<=N) 이 주어집니다.

▣ 출력설명
첫 번째 줄에 결과를 출력합니다.
출력순서는 사전순으로 오름차순으로 출력합니다.

▣ 입력예제 1
4 2

▣ 출력예제 1
1 2
1 3
1 4
2 3
2 4
3 4
* */

public class FindCombination_8_9 {
    static int n, m;
    static int[] permutation;

    public static void DFS(int level, int startNumber){
        if(level == m){
            for(int x : permutation)
                System.out.print(x + " ");
            System.out.println();
        }else{
            for(int i = startNumber; i <= n; i++){
                permutation[level] = i;
                DFS(level + 1, i + 1);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        permutation = new int[m];

        DFS(0, 1);
    }
}
