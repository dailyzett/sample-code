package askBoardCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import dto.BoardDto;
import dto.MemberDto;

public class QWriteFormCommand implements QCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("sessionId");
		
		MemberDao dao = MemberDao.getInstance();
		MemberDto dto = new MemberDto.Builder().build();
		BoardDto bDto = new BoardDto();
		
		dto = dao.listOneMember(id);
		
		bDto.setWriterId(dto.getUsername());
		bDto.setWriterEmail(dto.getEmail());
		bDto.setWriterName(dto.getName());
		
		
		request.setAttribute("writerId", bDto.getWriterId());
		request.setAttribute("writerName", bDto.getWriterName());
		request.setAttribute("writerEmail", bDto.getWriterEmail());
		
		return 0;
	}

}
