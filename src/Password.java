import java.util.Scanner;

public class Password {

    public void solution(int count, String str){
        String answer = "";
        String[] s = new String[count];

        int beginIndex = 0;
        int endIndex = 7;
        for(int i = 0; i < count; i++){
            s[i] = str.substring(beginIndex, endIndex);
            s[i] = s[i].replace('#', '1');
            s[i] = s[i].replace('*', '0');

            System.out.print(Character.toChars(Integer.valueOf(s[i], 2)));
            beginIndex = endIndex;
            endIndex = endIndex + 7;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String stringCount = in.nextLine();
        int count = Integer.parseInt(stringCount);
        String input = in.nextLine();

        Password app = new Password();
        app.solution(count, input);
    }
}
