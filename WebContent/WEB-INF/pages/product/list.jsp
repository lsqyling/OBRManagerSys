<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="baustem" tagdir="/WEB-INF/tags" %>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>产品管理</title>
</head>

<body class="main">

	
	<form action="${ctp}/product/list">
		<div class="page_title">产品管理</div>
		<div class="button_bar">
			<button class="common_button" onclick="document.forms[0].submit();">
				查询
			</button>
		</div>
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title">产品名</th>
				<td class="input_content"><input type="text"
					name="search_LIKE_name" /></td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />
		<!-- 列表数据 -->
		<br />
		<c:if test="${!empty page.content }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th class="data_title" >
						编号
					</th>
					<th class="data_title" >
						服务id
					</th>
					<th class="data_title" >
						产品名
					</th>
					<th class="data_title" >
						产品描述
					</th>
					<th class="data_title">
						操作
					</th>
				</tr>
				
				<c:forEach var="product" items="${page.content }">
					<tr>
						<td class="data_cell" style="text-align:left;padding:0 10px;">${product.id}</td>
						<td class="data_cell" style="text-align:left;padding:0 10px;">${product.serviceId}</td>
						<td class="data_cell" style="text-align:left;">${product.name}</td>
						<td class="data_cell" style="text-align:left;">${product.description}</td>
						<td class="data_cell">
							<img onclick="window.location.href='${ctp }/product/${product.name}'" title="获取库URL" src="${ctp }/static/images/bt_acti.gif" class="op_button" />
							<img onclick="window.location.href='${ctp }/product/repo/${product.id}'" title="获取库文件" src="${ctp }/static/images/bt_edit.gif" class="op_button" />
							<img onclick="window.location.href='${ctp }/product/assign/${product.id}'" title="关联Bundle" src="${ctp }/static/images/bt_linkman.gif" class="op_button" />
							<img onclick="window.location.href='${ctp }/product/delete/${product.id}'" title="删除" src="${ctp }/static/images/bt_del.gif" class="op_button" />
						</td>
					</tr>
				</c:forEach>
				
			</table>
			<baustem:page page="${page }" queryString="${queryString }"></baustem:page>
		</c:if>
		
		<c:if test="${empty page.content }">
			没有任何数据
		</c:if>
		
	</form>
</body>
</html>

