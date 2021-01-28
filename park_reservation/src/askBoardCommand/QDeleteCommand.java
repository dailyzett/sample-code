package askBoardCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDao;
import dto.BoardDto;

public class QDeleteCommand implements QCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String sessionId = (String) session.getAttribute("sessionId");
		String sid = request.getParameter("qid");
		int id = Integer.parseInt(sid);
		
		BoardDao dao = new BoardDao();
		BoardDto dto = new BoardDto();
		
		dto = dao.findById(id);
		
		
		if(dto.getWriterId().equals(sessionId) || sessionId.equals("admin")) {
			dao.deleteQBoard(id);
			return 1;
		}else {
			return 0;
		}
	}

}
