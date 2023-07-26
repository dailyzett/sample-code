package QRCODE.Capstone.service;

import QRCODE.Capstone.domain.Board;
import QRCODE.Capstone.dto.BoardDto;
import QRCODE.Capstone.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public Long postSave(Board board){
        boardRepository.save(board);
        return board.getId();
    }

    public List<Board> findPostAll(){
        return boardRepository.findAll();
    }
    public BoardDto getBoard(Long id){
        Optional<Board> findBoard = boardRepository.findById(id);
        Board board = findBoard.get();

        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());

        return boardDto;
    }
}
