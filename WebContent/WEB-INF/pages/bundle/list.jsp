<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="baustem" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>库存查询</title>
	<script type="text/javascript">
		$(function(){
			$("img[id^='delete_']").click(function(){
				var bundleId = $(this).prev(":hidden").val();
				var flag = confirm("你确定要删除编号<"+bundleId+">Bundle吗?");
				if(!flag){
					return;
				}
				var url = $(this).attr("onclick");
				$("#_method").val("DELETE");
				$("#hiddenForm").attr("action",url).submit();
				return;
			});
			
			
		});
	
	
	</script>
</head>
<body>
	<div class="page_title">
		库存管理
	</div>
	<div class="button_bar">
		<button class="common_button"
			onclick="window.location.href='${ctp}/bundle/add'">
			添加bundle
		</button>
		<button class="common_button" onclick="document.forms[1].submit();">
			查询
		</button>
	</div>
	
	<form action="" id="hiddenForm" method="post">
		<input type="hidden" name="_method" id="_method">
	</form>
	
	<form action="${ctp }/bundle/list">
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>
					特征名
				</th>
				<td>
					<input type="text" name="search_LIKE_symbolicName" />
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />
		
		<c:if test="${page != null && page.totalCount > 0 }">	
		<table class="data_list_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>
					编号
				</th>
				<th>
					资源ID
				</th>
				<th>
					展示名
				</th>
				<th>
					特征名
				</th>
				<th>
					uri
				</th>
				<th>
					version
				</th>
				<th>
					size
				</th>
				<th>
					操作
			</tr>
			<c:forEach var="bundle" items="${page.content }">
				<tr>
					<td class="list_data_number">
						${bundle.id}
					</td>
					<td class="list_data_text">
						${bundle.resId}
					</td>
					<td class="list_data_text">
						${bundle.presentationName}
					</td>
					<td class="list_data_text">
						${bundle.symbolicName}
					</td>

					<td class="list_data_text">
						${bundle.uri}
					</td>
					<td class="list_data_text">
						${bundle.version}
					</td>
					<td class="list_data_text">
						${bundle.size}
					</td>
					<td class="list_data_op">
						<input type="hidden" value="${bundle.id}">
						<img 
							id="delete_${bundle.id }" onclick="${ctp}/bundle/delete/${bundle.id }" 
							title="删除" src="${ctp }/static/images/bt_del.gif" class="op_button" />
						<%-- <img
							onclick="window.location.href='${ctp}/bundle/update'"
							class="op_button" src="${ctp}/static/images/bt_edit.gif"
							title="编辑" /> --%>

					</td>
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