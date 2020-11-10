package QRCODE.Capstone.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BoardController {

    @GetMapping("/notice")
    public String notice(){
        return "board/boardList";
    }

    @GetMapping("/post")
    public String post(Model model){
        return "board/boardWrite";
    }
}
