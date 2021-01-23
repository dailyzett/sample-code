package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;

public class userPasswordCheckCommand implements MemberCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			int r = 0;
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("sessionId");
			String pw = request.getParameter("pw");
			
			MemberDao dao = MemberDao.getInstance();
			int res = dao.loginMember(id, pw);
			if(res == MemberDao.MEMBER_LOGIN_SUCCESS) {
				request.setAttribute("pwCheck", true);
				r = 1;
			}else if(res == MemberDao.MEMBER_LOGIN_PW_NO_GOOD) {
				request.setAttribute("pwCheck", false);
				r = 2;
			}
			
			return r;
	}

}
