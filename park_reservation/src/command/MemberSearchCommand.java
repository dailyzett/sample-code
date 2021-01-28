package command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import dto.MemberDto;

public class MemberSearchCommand implements MemberCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int pageSize = 8;
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		
		int startRow = (currentPage - 1) * pageSize + 1;
		int count = 0;
		
		ArrayList<MemberDto> dtos = new ArrayList<MemberDto>();
		String search = request.getParameter("search");
		
		MemberDao dao = MemberDao.getInstance();
		count = dao.getSearchCount(search);
		
		
		dtos = dao.searchMemberName(startRow, pageSize, search);
	
		request.setAttribute("dtos", dtos);
		request.setAttribute("search", search);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
		return 0;
	}

}
