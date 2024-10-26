package inflearn.unit7;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ShortestDistance_7_14 {
    static int n, m, answer = 0;
    static ArrayList<ArrayList<Integer>> graph;
    static int[] ch, dis;

    public void BFS(int v) {
        Queue<Integer> Q = new LinkedList<>();

        ch[v] = 1;
        dis[v] = 0;
        Q.offer(v);

        while (!Q.isEmpty()) {
            int current = Q.poll();
            for (int next : graph.get(current)) {
                if(ch[next] == 0){
                    ch[next] = 1;
                    Q.offer(next);
                    dis[next] = dis[current] + 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        graph = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<Integer>());
        }
        ch = new int[n + 1];
        dis = new int[n + 1];
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph.get(a).add(b);
        }
        ShortestDistance_7_14 app = new ShortestDistance_7_14();
        app.BFS(1);
        for(int i = 2; i <= n; i++){
            System.out.println(i + " : " + dis[i]);
        }
    }
}
