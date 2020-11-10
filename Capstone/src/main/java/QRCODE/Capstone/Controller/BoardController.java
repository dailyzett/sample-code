package QRCODE.Capstone.Controller;


import QRCODE.Capstone.domain.Board;
import QRCODE.Capstone.dto.BoardDto;
import QRCODE.Capstone.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/post")
    public String post(Model model){
        model.addAttribute("board", new BoardDto());
        return "board/boardWrite";
    }

    @PostMapping("/post")
    public String postWrite(BoardDto boardDto, BindingResult bindingResult){
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());

        boardService.postSave(board);
        return "redirect:/notice";
    }

    @GetMapping("/notice")
    public String boardList(Model model){
        List<Board> postAll = boardService.findPostAll();
        model.addAttribute("postAll", postAll);
        return "board/boardList";
    }

    @GetMapping("/post/{no}")
    public String showBoard(@PathVariable("no") Long id, Model model){
        BoardDto boardDto = boardService.getBoard(id);
        model.addAttribute("boardDto", boardDto);
        return "board/boardDetail";

    }

}
