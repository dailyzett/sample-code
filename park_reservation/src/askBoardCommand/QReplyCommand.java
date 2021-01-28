package askBoardCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import dto.BoardDto;
import dto.ReplyBoardDto;

public class QReplyCommand implements QCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {

		String sId = request.getParameter("qid");
		String reContent = request.getParameter("reContent");
		
		int id = Integer.parseInt(sId);
	
		BoardDao dao = new BoardDao();
		dao.reply(id, reContent);
		
		
		
		return 0;
		
	}

}
