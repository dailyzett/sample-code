package askBoardCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import dto.BoardDto;

public class QBoardDetailCommand implements QCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		String qId = request.getParameter("qid");
		int id = Integer.parseInt(qId);
		int status = 0;
		
		BoardDao dao = new BoardDao();
		BoardDto dto = new BoardDto();
		
		dao.addHit(id);
		dto = dao.findById(id);
		status = dao.getStatus(id);
		
		request.setAttribute("qdto", dto);
		request.setAttribute("status", status);
		
		// 답변글이 없을 때
		if(status == 0) {
			return 0;
		// 답변글이 있을 때
		}else {
			return 1;
		}
		
		
		
	}

}
