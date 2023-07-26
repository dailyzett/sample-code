package com.common.etc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.MemberDao;
import com.member.command.MemberCommand;

public class MemberIdCheckAction implements MemberCommand {

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("id");
		MemberDao dao = MemberDao.getInstance();
		
		int result = dao.confirmUsername(username);
		
		try {
			PrintWriter writer = response.getWriter();
			if(result == MemberDao.MEMBER_EXISTENT) {
				writer.println("0");
			}else {
				writer.println("1");
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
		return 0;

	}
}
