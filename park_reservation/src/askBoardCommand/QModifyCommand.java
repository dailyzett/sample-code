package askBoardCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;

public class QModifyCommand implements QCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String parkName = request.getParameter("park");
		String id = request.getParameter("qid");
		
	
		BoardDao dao = new BoardDao();
		
		dao.updateQBoard(title, content, parkName, Integer.parseInt(id));
		return 1;
	}

}
