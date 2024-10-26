package inflearn.unit2;

import java.util.ArrayList;
import java.util.Scanner;

public class InvertedPrime {
    public ArrayList<Integer> solution(int num, StringBuilder[] sb) {
        int[] input = new int[num];
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            input[i] = Integer.parseInt(sb[i].toString());
            if (isPrime(input[i]) &&
                    input[i] != 1) {
                arrayList.add(input[i]);
            }
        }

        return arrayList;
    }

    public static boolean isPrime(int num) {
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        StringBuilder[] sb = new StringBuilder[num];

        for (int i = 0; i < num; i++) {
            sb[i] = new StringBuilder(String.valueOf(sc.nextInt())).reverse();
        }

        InvertedPrime app = new InvertedPrime();
        for (int x : app.solution(num, sb)) {
            System.out.print(x + " ");
        }
    }
}
