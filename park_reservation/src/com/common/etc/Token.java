package com.common.etc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Token {
	private static final String TOKEN_KEY = "TOKEN_KEY";

	public static void set(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		long systemTime = System.currentTimeMillis();
		byte[] time = new Long(systemTime).toString().getBytes();
		byte[] id = session.getId().getBytes();

		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(id);
			md5.update(time);

			String token = toHex(md5.digest());
			request.setAttribute(TOKEN_KEY, token);
			session.setAttribute(TOKEN_KEY, token);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static boolean isValid(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String requestToken = request.getParameter(TOKEN_KEY);
		String sessionToken = (String) session.getAttribute(TOKEN_KEY);
		if (requestToken == null || sessionToken == null) {
			return false;
		} else {
			return requestToken.equals(sessionToken);
		}
	}

	private static String toHex(byte[] digest) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			buf.append(Integer.toHexString((int) digest[i] & 0x00ff));
		}
		return buf.toString();
	}

}
