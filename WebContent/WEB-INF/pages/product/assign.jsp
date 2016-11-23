<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>角色管理 - 分配权限</title>
	<script type="text/javascript">
		$(function(){
			var listId = "";
			$(":hidden[class^=parent]").each(function(){
				var pre = $(this).next(":hidden").val();
				listId +=pre+"-";
			});
			
			$(":checkbox[class^=sub]").each(function(){
				var id = $(this).val();
				var flag = false;
				for(var i = 0;i<listId.split("-").length-1;i++){
					if(Number(id)==Number(listId.split("-")[i])){
						flag = true;
					}
				}				
				$(this).prop("checked", flag);
			});
		})
	</script>
</head>

<body class="main">
 	
 	
 	<form:form action="${ctp }/product/uprelated/${product.id}" method="post" modelAttribute="product">
		
		<div class="page_title">
			产品管理 &gt; 关联Bundle
		</div>
		
		<div class="button_bar">
			<button class="common_button" onclick="javascript:history.back(-1);">
				返回
			</button>
			<button class="common_button" onclick="document.forms[0].submit();">
				保存
			</button>
		</div>

		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title" width="10%">
					产品名
				</th>
				<td class="input_content" width="20%">
					${product.name}
				</td>
				<th class="input_title" width="10%">
					产品描述
				</th>
				<td class="input_content" width="20%">
					${product.description}
				</td>
				<th class="input_title" width="10%">
					服务ID
				</th>
				<td class="input_content" width="20%">
					${product.serviceId}
				</td>
			</tr>
			<tr>
				<th class="input_title">
					Bundle
				</th>
				<td class="input_content" colspan="5" valign="top">
				
				
				&nbsp;&nbsp;&nbsp;
				<form:checkboxes items="${bundles }" path="bundles2" 
					itemLabel="symbolicName" itemValue="id" 
					delimiter="<br>&nbsp;&nbsp;&nbsp;&nbsp;"
					cssClass="sub-${symbolicName }" />
				<br><br>
				</td>
			</tr>
		</table>
	</form:form>
	<form:form>
		<c:forEach var="bnd" items="${product.bundles }" varStatus="status">
					<input type="hidden" class="parent"/>
					<input type="hidden" value="${bnd.id}" />
		</c:forEach>
	</form:form>
	
</body>
</html>
