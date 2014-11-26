package com..;

import javax.servlet.http.HttpServletRequest;

import sun.rmi.runtime.Log;

public class Pager {

	private int pg;

	private int totalCnt;

	public static String makeTag(HttpServletRequest request, int pageSize,
			int total) {
		// 파라미터
		// pagesize - 한페이지에서 보여줄
		// 데이타 개수
		// total - 전체 데이타 건수- 총 몇페이지
		// 인지 페이지 개수 세려고
		String Tag = "";

		int cpage;

		int pageTotal;
		int pageGroupSize = 5;
		int pageGroupStart;
		int pageGroupEnd;

		String path = "";
		// System.out.println(path);
		String beginLabel = "<img src='/MVC/jsp/images/board/def/pg_first.gif' alt='첫 페이지' border=0 align='absmiddle'>";
		String prevLabel = "<img src='/MVC/jsp/images/board/def/pg_prev.gif'  alt='이전 페이지' border='0'  align='absmiddle'>\n";
		String nextLabel = "<img src='/MVC/jsp/images/board/def/pg_next.gif'  alt='다음 페이지' order='0' align='absmiddle'>";
		String endLabel = "<img src='/MVC/jsp/images/board/def/pg_last.gif'  alt='마지막 페이지' border=0 align='absmiddle'>\n";

		String classname;

		try {

			StringBuffer sb = new StringBuffer();

			String page = StringUtil.convNull(request.getParameter("pg"), "0");
			page = (page == null) ? "0" : page;

			cpage = Integer.parseInt(page);

			pageTotal = (total - 1) / pageSize;

			pageGroupStart = (int) (cpage / pageGroupSize) * pageGroupSize;
			pageGroupEnd = pageGroupStart + pageGroupSize;
			if (pageGroupEnd > pageTotal) {
				pageGroupEnd = pageTotal + 1;
			}

			boolean hasPreviousPage = cpage - pageGroupSize >= 0;
			boolean hasNextPage = pageGroupStart + pageGroupSize < pageTotal;

			sb.append((cpage > 0) ? makeLink(0, beginLabel, "f") : beginLabel);

			sb.append(hasPreviousPage ? makeLink(pageGroupStart - 1, prevLabel,
					"p") : prevLabel);

			sb.append("<ul>");
			for (int i = pageGroupStart; i < pageGroupEnd; i++) {
				classname = "";
				if (i == pageGroupEnd - 1)
					classname = "last";
				if (i == cpage)
					classname = "on";

				sb.append(makeLink2(i, i + 1+"", classname));

			}
			sb.append("</ul>");

			sb.append(hasNextPage ? makeLink(pageGroupEnd, nextLabel, "n")
					: nextLabel);

			sb.append((cpage < pageTotal) ? makeLink(pageTotal, endLabel, "l")
					: endLabel);

			Tag = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Tag;
	}

	// 첫페이지, 이전, 이후, 마지막
	public static String makeLink(int page, String label, String classname) {
		StringBuffer tmp = new StringBuffer();
		tmp.append(
				"<a class='" + classname + "' href=\"javascript:goPage('"
						+ page + "')\">").append(label).append("</a>");
		return tmp.toString();
	}

	// 숫자
	public static String makeLink2(int page, String label, String classname) {
		StringBuffer tmp = new StringBuffer();
		tmp.append("<li class='" + classname + "' >");
		tmp.append("<a  href=\"javascript:goPage('" + page + "')\">")
				.append(label).append("</a>");
		tmp.append("</li>");
		return tmp.toString();
	}

	public void setPg(int pg) {
		this.pg = pg;
	}

	public int getPg() {
		return pg;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getTotalCnt() {
		return totalCnt;
	}
}
