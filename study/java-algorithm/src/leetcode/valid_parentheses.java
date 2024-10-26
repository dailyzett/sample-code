package leetcode;

import java.util.Deque;
import java.util.LinkedList;

public class valid_parentheses {
    public boolean isValid(String s) {
        Deque<Character> stack = new LinkedList<>();

        for(char c : s.toCharArray()){
            switch(c){
                case '(':
                    stack.add(')');
                    break;
                case '[':
                    stack.add(']');
                    break;
                case '{':
                    stack.add('}');
                    break;
                default:
                    if(stack.isEmpty() || stack.pollLast() != c){
                        return false;
                    }
            }
        }
        return stack.isEmpty();
    }
}
