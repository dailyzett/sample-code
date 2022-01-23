import java.util.ArrayList;
import java.util.Scanner;

public class StudentWhoSees {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int num = sc.nextInt();
        int[] studentTall = new int[num];
        for(int i = 0; i < studentTall.length; i++){
            studentTall[i] = sc.nextInt();
        }

        StudentWhoSees app = new StudentWhoSees();
        System.out.println(app.solution(num, studentTall));
    }

    private int solution(int num, int[] studentTall) {
        ArrayList<Integer> list = new ArrayList<>();

        list.add(studentTall[0]);
        int tempHeight = studentTall[0];
        for(int i = 1; i < studentTall.length; i++){
            if(studentTall[i] > tempHeight){
                list.add(studentTall[i]);
                tempHeight = studentTall[i];
            }
        }
        return list.size();
    }
}
