package inflearn;

import java.util.ArrayList;
import java.util.Scanner;

public class RouteDFSArrayList_7_13 {
    static int n, m, answer = 0;
    static ArrayList<ArrayList<Integer>> graph;
    static int[] ch;
    public void DFS(int v){
        if(v == n) answer++;
        else{
            for(int next : graph.get(v)){
                if(ch[next] == 0){
                    ch[next] = 1;
                    DFS(next);
                    ch[next] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        graph = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i <= n; i++){
            graph.add(new ArrayList<Integer>());
        }
        ch = new int[n + 1];
        for(int i = 0; i < m; i++){
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph.get(a).add(b);
        }

        ch[1] = 1;
        RouteDFSArrayList_7_13 app = new RouteDFSArrayList_7_13();
        app.DFS(1);
        System.out.println(answer);
    }
}
