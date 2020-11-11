package QRCODE.Capstone.Controller;


import QRCODE.Capstone.domain.Member;
import QRCODE.Capstone.dto.MemberDto;
import QRCODE.Capstone.repository.MemberRepository;
import QRCODE.Capstone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Qualifier("passwordValidator")
    private final Validator validator;

    @GetMapping("/")
    public String home(){
        return "Home";
    }

    @PostMapping("/")
    public String signIn(String username, String password){
        log.info("id : {}, pw : {}", username, password);
        Member member = memberService.findOne(username,password);
        if(member != null){
            return "menu";
        }
        return "Home";
    }

    @GetMapping("/members/new")
    public String register(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "members/Register";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberDto memberDto, BindingResult result){
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setAge(memberDto.getAge());
        member.setUsername(memberDto.getUsername());
        member.setPassword(memberDto.getPassword());
        member.setPhone(memberDto.getPhone());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @RequestMapping("/vision")
    @ResponseBody
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


}
