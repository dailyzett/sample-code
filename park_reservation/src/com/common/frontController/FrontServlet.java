package com.common.frontController;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.MemberDao;
import com.common.etc.MemberIdCheckAction;
import com.common.etc.Token;
import com.member.command.AdminMemberModifyFormCommand;
import com.member.command.AdminMemberModifyProcessCommand;
import com.member.command.JoinCommand;
import com.member.command.ListOneCommand;
import com.member.command.LoginCommand;
import com.member.command.MemberCommand;
import com.member.command.MemberListCommand;
import com.member.command.MemberSearchCommand;
import com.member.command.ModifyMemberCommand;
import com.member.command.ModifyMemberFormCommand;
import com.member.command.signOutSuccessCommand;
import com.member.command.userPasswordCheckCommand;
import com.question.command.QBoardDetailCommand;
import com.question.command.QBoardListCommand;
import com.question.command.QCommand;
import com.question.command.QDeleteCommand;
import com.question.command.QModifyCommand;
import com.question.command.QModifyFormCommand;
import com.question.command.QReplyCommand;
import com.question.command.QReplyViewCommand;
import com.question.command.QSearchCommand;
import com.question.command.QWriteCommand;
import com.question.command.QWriteFormCommand;
import com.reservation.command.GetDateCommand;
import com.reservation.command.ReservationCommand;

/**
 * Servlet implementation class FrontServlet
 */
@WebServlet("*.do")
public class FrontServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			actionDo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			actionDo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionDo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");

		MemberCommand command = null;
		QCommand qCommand = null;
		ReservationCommand rCommand = null;

		String viewPage = null;

		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length());

		if (com.equals("/login.do")) {
			command = new LoginCommand();
			int res = command.execute(request, response);
			if (res == 1) {
				viewPage = "home.do";
			} else {
				viewPage = "member/login.jsp";
			}
		}

		else if (com.equals("/logout.do")) {
			viewPage = "member/logout.jsp";
		}

		else if (com.equals("/join.do")) {
			command = new JoinCommand();
			command.execute(request, response);
			viewPage = "home.do";
		}

		else if (com.equals("/home.do")) {
			viewPage = "home.jsp";
		}

		else if (com.equals("/reservation.do")) {
			rCommand = new GetDateCommand();
			rCommand.execute(request, response);
			
			viewPage = "reserv/reserveKaya.jsp";
		}

		else if (com.equals("/MemberIdCheckAction.do")) {
			command = new MemberIdCheckAction();
			command.execute(request, response);
			viewPage = null;
		}

		else if (com.equals("/joinSuccess.do")) {
			command = new JoinCommand();
			command.execute(request, response);
			viewPage = "member/joinSuccess.jsp";
		}

		else if (com.equals("/listOne.do")) {
			command = new ListOneCommand();
			command.execute(request, response);
			viewPage = "member/listOne.jsp";
		}

		else if (com.equals("/modifyMember.do")) {
			command = new ModifyMemberFormCommand();
			command.execute(request, response);
			viewPage = "member/modifyMember.jsp";
		}

		else if (com.equals("/userPasswordCheck.do")) {
			command = new userPasswordCheckCommand();
			int res = command.execute(request, response);
			if (res == 1) {
				viewPage = "modifyMember.do";
			} else {
				viewPage = "member/userPasswordCheck.jsp";
			}
		}

		else if (com.equals("/modifyProcess.do")) {
			command = new ModifyMemberCommand();
			int res = command.execute(request, response);
			if (res == 1) {
				viewPage = "listOne.do";
			} else {
				viewPage = "member/modifyMember.jsp";
			}
		}

		else if (com.equals("/memberlist.do")) {
			command = new MemberListCommand();
			command.execute(request, response);
			viewPage = "member/memberList.jsp";
		}

		else if (com.equals("/memberListSearch.do")) {
			command = new MemberSearchCommand();
			command.execute(request, response);
			viewPage = "member/memberListSearch.jsp";
		}

		else if (com.equals("/signOut.do")) {
			viewPage = "member/signOut.jsp";
		}

		else if (com.equals("/signOutSucess.do")) {
			command = new signOutSuccessCommand();
			command.execute(request, response);
			viewPage = "member/signOutSuccess.jsp";
		}

		else if (com.equals("/userSignOutCheck.do")) {
			command = new userPasswordCheckCommand();
			int res = command.execute(request, response);
			if (res == 1) {
				viewPage = "signOutSucess.do";
			} else {
				viewPage = "signOut.do";
			}
		}

		else if (com.equals("/adminMemberModify.do")) {
			command = new AdminMemberModifyFormCommand();
			command.execute(request, response);
			viewPage = "member/adminMemberModify.jsp";
		}

		else if (com.equals("/adminMemberModifySuccess.do")) {
			int res = 0;
			command = new AdminMemberModifyProcessCommand();
			res = command.execute(request, response);

			if (res == MemberDao.MEMBER_EXISTENT) {
				viewPage = "adminMemberModify.do";
			} else {
				viewPage = "memberlist.do";
			}
		}

		else if (com.equals("/question.do")) {
			qCommand = new QBoardListCommand();
			qCommand.execute(request, response);
			viewPage = "questionBoard/askBoardList.jsp";
		}

		else if (com.equals("/writeQBoard.do")) {
			qCommand = new QWriteFormCommand();
			qCommand.execute(request, response);
			viewPage = "questionBoard/writeQBoard.jsp";
		}

		else if (com.equals("/writeProcess.do")) {
			if (Token.isValid(request)) {
				Token.set(request);
				request.setAttribute("TOKEN_SAVE_CHECK", "TRUE");
			} else {
				request.setAttribute("TOKEN_SAVE_CHECK", "FALSE");
			}
			qCommand = new QWriteCommand();
			qCommand.execute(request, response);
			viewPage = "question.do";
		}
		
		else if (com.equals("/questionDetail.do")) {
			int res = 0;
			qCommand = new QBoardDetailCommand();
			res = qCommand.execute(request, response);
			if(res == 0) {
				viewPage = "questionBoard/askBoardDetail.jsp";
			}else if(res == 1) {
				viewPage = "replyView.do";
			}
			
			
		}

		else if (com.equals("/reply.do")) {
			qCommand = new QReplyCommand();
			qCommand.execute(request, response);
			viewPage = "replyView.do";
		}
		
		else if (com.equals("/replyView.do")) {
			qCommand = new QReplyViewCommand();
			qCommand.execute(request, response);
			viewPage = "questionBoard/replyBoardDetail.jsp";
		}
		
		else if (com.equals("/modifyQuestion.do")) {
			qCommand = new QModifyFormCommand();
			qCommand.execute(request, response);
			viewPage = "questionBoard/modifyQBoard.jsp";
		}
		
		else if (com.equals("/modifyBoardProcess.do")) {
			int alertStatus = 0;
			qCommand = new QModifyCommand();
			alertStatus = qCommand.execute(request, response);
			request.setAttribute("modifyAlert", alertStatus);
			viewPage = "questionDetail.do";
		}
		
		else if (com.equals("/deleteQuestion.do")) {
			int alertStatus = 0;
			qCommand = new QDeleteCommand();
			alertStatus = qCommand.execute(request, response);
			if(alertStatus == 1) {
				request.setAttribute("deleteAlert", alertStatus);
				request.setAttribute("deleteCheck", true);
				viewPage = "question.do";
			}else {
				request.setAttribute("deleteCheck", false);
				viewPage = "questionDetail.do";
			}	
		}
		
		else if (com.equals("/qBoardListSearch.do")) {
			int res = 0;
			qCommand = new QSearchCommand();
			res = qCommand.execute(request, response);
			if(res == 1) {
				viewPage = "questionBoard/askBoardTitleSearch.jsp";
			}else {
				viewPage = "questionBoard/askBoardSubSearch.jsp";
			}
		}
		
		if(viewPage != null) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPage);
			requestDispatcher.forward(request, response);
		}
	}

}
