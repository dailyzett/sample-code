package QRCODE.Capstone.Controller;


import QRCODE.Capstone.domain.Member;
import QRCODE.Capstone.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RestMemberController {
    private final MemberService memberService;

    @RequestMapping("/vision")
    public Map<String, String> androidTestWithRequestAndResponse(HttpServletRequest request){
        System.out.println("request = " + request.getParameter("id"));
        System.out.println("request = " + request.getParameter("pw"));
        HashMap<String, String> result = new HashMap<>();

        String username = request.getParameter("id");
        String password = request.getParameter("pw");

        List<Member> members = memberService.findMembers();

        for (Member member : members) {
            if (username.equals(member.getUsername()) && password.equals(member.getPassword())) {
                result.put("username", username);
                result.put("password", password);
            }
        }
        return result;
    }

    @RequestMapping("/join")
    public void androidJoin(HttpServletRequest request){
        HashMap<String, String> result = new HashMap<>();

        String username = request.getParameter("id");
        String password = request.getParameter("pw");
        int age = Integer.parseInt(request.getParameter("age"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");

        Member member = new Member();
        member.setUsername(username);
        member.setName(name);
        member.setPassword(password);
        member.setPhone(phone);
        member.setAge(age);

        memberService.join(member);
    }
}
