<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="baustem" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>OBR管理系统</title>
</head>
<body>

	<div class="page_title">日志信息详情</div>
	
	<form action="${ctp }/log/list" method="POST">
		
		<!-- 列表数据 -->
		<br />
		
		<c:if test="${page != null && page.totalCount > 0 }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>日志编号</th>
					<th>操作者</th>
					<th>方法名</th>
					<th>类名</th>
					<th>方法入参</th>
					<th>操作结果</th>
					<th>返回值</th>
					<th>操作时间</th>
					<th>错误消息</th>
				</tr>
				
				<c:forEach var="log" items="${page.content }">
					<tr>
						<td class="list_data_text">${log.id }&nbsp;</td>
						<td class="list_data_ltext">${log.operator }&nbsp;</td>
						<td class="list_data_text">${log.methodName }&nbsp;</td>
						<td class="list_data_text">${log.className }&nbsp;</td>
						<td class="list_data_text">${log.args }&nbsp;</td>
						<td class="list_data_text">${log.operateResult}&nbsp;</td>
						<td class="list_data_text">${log.returnValue}&nbsp;</td>
						<td class="list_data_text">${log.operateTime}&nbsp;</td>
						<td class="list_data_text">${log.errorMessage}&nbsp;</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${page == null || page.totalCount == 0 }">
			没有任何数据
		</c:if>
		
	</form>
	<baustem:page page="${page }" queryString="${queryString }"></baustem:page>
</body>
</html>