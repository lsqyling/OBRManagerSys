<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>  
<body class="main">  
	<span class="page_title">上传bundle</span>
    <form name="userForm" action="${ctp }/bundle/replace" method="POST" enctype="multipart/form-data">
		<input type="file" name="file">
		<input type="submit" value="上传">
	</form>
</body>  
</html>  
