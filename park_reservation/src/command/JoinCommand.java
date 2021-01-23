package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import dto.MemberDto;

public class JoinCommand implements MemberCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		int r = 0;
		MemberDao dao = MemberDao.getInstance();
		MemberDto dto = new MemberDto
				.Builder()
				.username(request.getParameter("username"))
				.password(request.getParameter("pw"))
				.email(request.getParameter("email"))
				.phone1(request.getParameter("phone1"))
				.phone2(request.getParameter("phone2"))
				.phone3(request.getParameter("phone3"))
				.name(request.getParameter("name"))
				.regit_date()
				.build();
		
		dao.joinMember(dto);
		return r;
	}
}
