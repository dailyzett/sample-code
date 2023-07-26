package inflearn.unit8;

// https://cote.inflearn.com/contest/10/problem/08-12

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Tomato_8_12 {
    static int M, N;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int[][] box, day;
    static Queue<Point> Q = new LinkedList<>();

    public void BFS(){
        while (!Q.isEmpty()) {
            Point temp = Q.poll();
            for (int i = 0; i < 4; i++) {
                int nx = dx[i] + temp.x;
                int ny = dy[i] + temp.y;
                if (nx >= 0 && nx < N && ny >= 0 && ny < M && box[nx][ny] == 0) {
                    box[nx][ny] = 1;
                    Q.offer(new Point(nx, ny));
                    day[nx][ny] = day[temp.x][temp.y] + 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        M = sc.nextInt();
        N = sc.nextInt();

        box = new int[N][M];
        day = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                box[i][j] = sc.nextInt();
                if(box[i][j] == 1) Q.offer(new Point(i, j));
            }
        }

        Tomato_8_12 app = new Tomato_8_12();
        app.BFS();
        boolean flag = true;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(box[i][j] == 0) flag = false;
            }
        }

        int answer = Integer.MIN_VALUE;

        if (flag) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    answer = Math.max(answer, day[i][j]);
                }
            }
        } else {
            answer = -1;
        }

        System.out.println(answer);
    }
}
