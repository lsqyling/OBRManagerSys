<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>添加产品</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>

	<body>

		<div class="page_title">
			添加产品
		</div>
		
		<form:form action="${ctp }/product/create" method="post" modelAttribute="product"  >
		
			<div class="button_bar">
				<button class="common_button" onclick="javascript:history.back(-1);">
					返回
				</button>
				<button class="common_button" onclick="document.forms[0].submit();">
					保存
				</button>
			</div>
			
			<table class="query_form_table">
				<tr>
					<th>
						服务Id
					</th>
					<td>
						<input type="text" name="serviceId" />
					</td>
					<th>
						产品名
					</th>
					<td>
						<input type="text" name="name" />
					</td>
				</tr>
				<tr>
					<th>
						产品描述
					</th>
					<td>
						<input type="text" name="description">
					</td>
					
				</tr>
			</table>
		</form:form>
	</body>
</html>
