package askBoardCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import dto.BoardDto;
import dto.ReplyBoardDto;

public class QReplyViewCommand implements QCommand {

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {

		String sId = request.getParameter("qid");

		ReplyBoardDto rdto = new ReplyBoardDto();
		BoardDto dto = new BoardDto();

		int id = Integer.parseInt(sId);

		BoardDao dao = new BoardDao();

		rdto = dao.findByReplyBoardId(id);
		dto = dao.findById(id);

		request.setAttribute("rdto", rdto);
		request.setAttribute("qdto", dto);
		return 0;
	}

}
