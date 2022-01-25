package inflearn;

import java.util.Scanner;

public class SieveOfEratosthenes{
    public int solution(int num){
        int answer = 0;

        int prime[] = new int[200002];
        for(int i = 2; i < num + 1; i++){
            prime[i] = i;
        }

        for(int i = 2; i < num + 1; i++){
            if(prime[i] == 0)
                continue;
            else{
                for(int j = i * 2; j < num + 1; j+=i){
                    prime[j] = 0;
                }
            }
        }

        for(int i = 2; i < num; i++){
            if(prime[i] != 0) answer++;
        }

        return answer;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        SieveOfEratosthenes app = new SieveOfEratosthenes();
        System.out.println(app.solution(num));
    }
}