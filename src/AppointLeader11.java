import java.util.Scanner;

public class AppointLeader11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] st = new int[n+1][6];

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= 5; j++){
                st[i][j] = sc.nextInt();
            }
        }

        AppointLeader11 app = new AppointLeader11();
        System.out.println(app.solution(n, st));
    }

    public int solution(int n, int[][] student){
        int answer = 0;
        int max = Integer.MIN_VALUE;
        for(int i = 1; i <= n; i++){
            int count = 0;
            for(int j = 1; j <= n; j++){
                for(int k = 1; k <= 5; k++){
                    if(student[i][k] == student[j][k]){
                        count++;
                        break;
                    }
                }
            }
            if(count > max){
                max = count;
                answer = i;
            }
        }
        return answer;
    }
}
