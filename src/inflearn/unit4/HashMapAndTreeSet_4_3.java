package inflearn.unit4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HashMapAndTreeSet_4_3 {
    public ArrayList<Integer> solution(int n, int k, int[] inputArr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> answer = new ArrayList<>();
        for (int i = 0; i < k - 1; i++) {
            map.put(inputArr[i], map.getOrDefault(inputArr[i], 0) + 1);
        }
        int lt = 0;
        for (int rt = k - 1; rt < n; rt++) {
            map.put(inputArr[rt], map.getOrDefault(inputArr[rt], 0) + 1);
            answer.add(map.size());
            map.put(inputArr[lt], map.get(inputArr[lt]) - 1);
            if(map.get(inputArr[lt]) == 0){
                map.remove(inputArr[lt]);
            }
            lt++;
        }
        return answer;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] inputArr = new int[n];
        for (int i = 0; i < n; i++) {
            inputArr[i] = in.nextInt();
        }

        HashMapAndTreeSet_4_3 T = new HashMapAndTreeSet_4_3();
        for (int x : T.solution(n, k, inputArr)) {
            System.out.print(x + " ");
        }

    }
}
