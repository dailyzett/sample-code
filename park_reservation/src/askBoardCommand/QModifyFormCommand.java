package askBoardCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDao;
import dao.MemberDao;
import dto.BoardDto;
import dto.MemberDto;

public class QModifyFormCommand implements QCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("sessionId");
		
		String qid = request.getParameter("qid");
		
		MemberDao dao = MemberDao.getInstance();
		BoardDao bDao = new BoardDao();
		BoardDto bDto = new BoardDto();
		
		
		
		bDto = bDao.findById(Integer.parseInt(qid));
		int status = bDao.getStatus(Integer.parseInt(qid));
		
		request.setAttribute("status", status);
		
		request.setAttribute("id", bDto.getId());
		request.setAttribute("title", bDto.getTitle());
		request.setAttribute("content", bDto.getContent());
		request.setAttribute("writerId", bDto.getWriterId());
		request.setAttribute("writerName", bDto.getWriterName());
		request.setAttribute("writerEmail", bDto.getWriterEmail());
		
		
		return 0;
	}

}
