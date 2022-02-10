package inflearn.unit7;

import java.util.Scanner;
/*
[문제] 방향그래프가 주어지면 1번 정점에서 N번 정점으로 가는 모든 경로의 가지 수를 출력하는 프로그램을 작성하세요.

[입력]첫째 줄에는 정점의 수 N(1<=N<=20)와 간선의 수 M가 주어진다. 그 다음부터 M줄에 걸쳐 연
결정보가 주어진다.

[출력]총 가지수를 출력한다.
* */
public class RouteDFSMatrix_7_12 {
    static int n, m, answer = 0;
    static int[][] graph;
    static int[] check;

    private void DFS(int v) {
        if(v == n){
            answer++;
        }else{
            for(int i = 1; i <= n; i++){
                if(graph[v][i] == 1 && check[i] == 0){
                    check[i] = 1;
                    DFS(i);
                    check[i] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();

        graph = new int[n + 1][n + 1];
        check = new int[n + 1];

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph[a][b] = 1;
        }
        RouteDFSMatrix_7_12 app = new RouteDFSMatrix_7_12();
        check[1] = 1;
        app.DFS(1);
        System.out.println(answer);
    }
}
