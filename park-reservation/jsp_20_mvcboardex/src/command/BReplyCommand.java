package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;

public class BReplyCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bTitle");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		String bStep = request.getParameter("bStep");
		String bIndent = request.getParameter("bIndent");
		String bGroup = request.getParameter("bGroup");
		
		
		BoardDao dao = new BoardDao();
		dao.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
	}

}
