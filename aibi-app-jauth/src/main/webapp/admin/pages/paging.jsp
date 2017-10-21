<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<style type="text/css">
			.turn_page{ width:auto; height:11px; float: right;line-height:18px; border:1px solid #fff; margin:10px auto; position: relative;right: -100px; top: 0px;}
			.turn_page b{ display:block; float:left; padding:0 7px; margin-right:3px; background:lightblue; color:#fff;border: 1px solid #76C3FB;}
			.turn_page a{ display:block; float:left; padding:0 7px; margin-right:3px; background:#fff; border:1px solid #76C3FB;}
			.turn_page a:hover{ display:block; float:left; padding:0 7px; margin-right:3px; background:#76C3FB; border: 1px solid #76C3FB; text-decoration:none;}
			.turn_page span{ display:block; float:left; margin-right:3px; background:#fff; border:1px solid #76C3FB;}
		</style>
		<title>分页</title>
	</head>
	<body>
	<%
		String groupId = request.getParameter("groupId");
		String path = "";
		if(!(groupId == null)) {
			//查询组内
			path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI() + "?groupId=" + groupId + "&pageNo=";
		}else {
			//查询全部
			path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI() + "?pageNo=";
		}
		
		String pageNo = request.getParameter("pageNo");
		Object pc = request.getAttribute("pagecount");
		int pagecount;
		if(pc == null) {
			pagecount = 1;
		}else {
			pagecount = (Integer)request.getAttribute("pagecount");
		}
		 
		if(pageNo == null) {
			pageNo = "1";
		}
		int pageNoInt = Integer.parseInt(pageNo);
		
		//上一页
		int pageup = pageNoInt - 1;
		
		//下一页
		int pagedown = pageNoInt + 1;
		//显示8个页码,start为开始页
		int start = 0;
		if(pageNoInt <= 4) {
			start = 1;
		}else if(pagecount - pageNoInt <= 3) {
			start = pagecount - 6;
		}else {
			start = pageNoInt - 3;
		}
	%>
		<div class="turn_page" align="center">
			<%
				if(pageup > 0 ) {
			%>
				<a href="<%=path %>1">首页</a>
				<a href="<%=path %><%=pageup %>">上一页</a>
			<%
				}else {
			%>
				<b>首页</b>
				<b>上一页</b>
			<%
				}
				if(start > 1) {
			%>
				<span>...</span>
			<%			
				}
				for(int i = start; i <= start + 6 && i <= pagecount; i++) {
					if(i == pageNoInt){
			%>
				<b><%=i %></b>
			<%
					}else {
			%>
				<a href="<%=path %><%=i %>"><%=i %></a>
			<%
					}
				}
				if(!((pagecount - start) <= 6)) {
			%>
				<span>...</span>
			<%		
				}
				if(pagedown > pagecount) {
			%>
				<b>下一页</b>
				<b>末页</b>
			<%
				}else {
			%>
				<a href="<%=path %><%=pagedown %>">下一页</a>
				<a href="<%=path %><%=pagecount %>">末页</a>
			<%
				}
			%>
		</div>
	</body>
</html>