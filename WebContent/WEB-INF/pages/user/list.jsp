<%@page import="com.baustem.obrmanager.orm.Page"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="baustem"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/commons/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
	$(function() {
		$("#new").click(function() {
			window.location.href = "${ctp}" + "/user/";
			return false;
		});

		$("img[id^='delete-']").click(function() {
			var id = this.id;
			id = id.split("-")[1];

			var href = "${ctp}/user/" + id;
			$("#hiddenForm").attr("action", href);
			$("#_method").val("DELETE");
			$("#hiddenForm").submit();

			return false;

		});
	})
</script>
</head>

<body class="main">


	<form action="" method="post" id="hiddenForm">
		<input type="hidden" name="_method" id="_method">
	</form>
	<form action="${ctp}/user/list">
		<div class="page_title">用户管理</div>
		<div class="button_bar">
			<button class="common_button" id="new">新建</button>
			<button class="common_button" onclick="document.forms[0].submit();">
				查询</button>
		</div>
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title">用户名</th>
				<td class="input_content"><input type="text"
					name="search_LIKE_name" /></td>
				<th class="input_title">状态</th>
				<td class="input_content"><select name="search_EQ_enabled">
						<option value="">全部</option>
						<option value="1">正常</option>
						<option value="0">已删除</option>
				</select></td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />

		<c:if test="${!empty page.content}">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th class="data_title" style="width: 40px;">编号</th>
					<th class="data_title" style="width: 50%;">用户名</th>
					<th class="data_title" style="width: 20%;">状态</th>
					<th class="data_title">操作</th>
				</tr>
				<c:forEach var="user" items="${page.content }">
					<tr>
						<td class="data_cell" style="text-align: right; padding: 0 10px;">
							${user.id}</td>
						<td class="data_cell" style="text-align: center;">
							${user.name}</td>
						<td class="data_cell">${user.enabled == true ? "有效" : "无效"}</td>
						<td class="data_cell"><img title="删除"
							src="${ctp}/static/images/bt_del.gif" class="op_button"
							id="delete-${user.id }" /> <img
							onclick="window.location.href='${ctp}/user/input/${user.id}'"
							class="op_button" src="${ctp}/static/images/bt_edit.gif"
							title="编辑" /></td>
					</tr>
				</c:forEach>
			</table>
			<baustem:page page="${page }" queryString="${queryString }"></baustem:page>
		</c:if>
		<c:if test="${empty page.content}">
			没有任何数据
		</c:if>
	</form>
</body>
</html>
