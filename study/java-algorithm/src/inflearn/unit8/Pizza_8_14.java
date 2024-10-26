package inflearn.unit8;

import java.util.ArrayList;
import java.util.Scanner;

// https://cote.inflearn.com/contest/10/problem/08-14

public class Pizza_8_14 {
    static int n,m, answer=Integer.MAX_VALUE, length;
    static int[] comb;
    static ArrayList<Point> pizza, house;
    static class Point{
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void DFS(int level, int start){
        if(level == m){
            int sum = 0;
            for (Point h : house) {
                int dis = Integer.MAX_VALUE;
                for (int i : comb) {
                    dis = Math.min(dis, Math.abs(h.x - pizza.get(i).x) + Math.abs(h.y - pizza.get(i).y));
                }
                sum += dis;
            }
            answer = Math.min(sum, answer);
        }else{
            for (int i = start; i < length; i++) {
                comb[level] = i;
                DFS(level + 1, i + 1);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();

        pizza = new ArrayList<>();
        house = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tmp = sc.nextInt();
                if(tmp == 1) house.add(new Point(i, j));
                if(tmp == 2) pizza.add(new Point(i, j));
            }
        }
        length = pizza.size();
        comb = new int[m];
        Pizza_8_14 T = new Pizza_8_14();
        T.DFS(0, 0);
        System.out.println(answer);
    }
}
