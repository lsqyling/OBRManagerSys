<%@ tag language="java" pageEncoding="UTF-8"%>
<!-- 定义当前标签的属性 -->

<%--  
name: 属性名
type: 属性的类型。通常情况下是一个全类名.
required: 该属性是否为必须
rtexprvalue: rt runtime, expr express, value, 是否可以寄售运行时表达式的值。即是否可以接受 EL.
注意：属性是当前的标签处理器的成员变量，所以可以像使用变量那样去使用。
使用示例: <atguigu:page page="${page }"></atguigu:page>
--%>

<%@ attribute name="page" type="com.baustem.obrmanager.orm.Page"  required="true" rtexprvalue="true"%>
<%@ attribute name="queryString" type="java.lang.String"  required="false" rtexprvalue="true"%>

<% 
	if(queryString == null) {
		queryString = "";
	}

	queryString = "&"+queryString;
%>


<div style="text-align:right; padding:6px 6px 0 0;">

	

	共 <%=page.getTotalCount() %> 条记录 
	&nbsp;&nbsp;
	
	当前第 <%=page.getPageNo() %> 页/共 <%=page.getTotalPages() %> 页
	&nbsp;&nbsp;
	
		<% 
			if(page.isHasPre()){
		%>
			<a href='?pageNo=1<%=queryString %>'>首页</a>
			&nbsp;&nbsp;
			<a href='?pageNo=<%=page.getPrePages() %><%=queryString %>'>上一页</a>
			&nbsp;&nbsp;
		<%		
			}
		%>
		
		<% 
			if(page.isHasNext()){
		%>
			<a href='?pageNo=<%=page.getNextPages() %><%=queryString %>'>下一页</a>
			&nbsp;&nbsp;
			<a href='?pageNo=<%=page.getTotalPages() %><%=queryString %>'>末页</a>
			&nbsp;&nbsp;
		<%		
			}
		%>	
	 
	
	
	转到 <input id="pageNo" size='1' value="<%=page.getPageNo() %>"/> 页
	&nbsp;&nbsp;

</div>


<script type="text/javascript" src="${ctp}/static/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript">

	$(function(){
		
		$("#pageNo").change(function(){
			var pageNo = $(this).val();
			var reg = /^\d+$/;
			if(!reg.test(pageNo)){
				$(this).val("");
				alert("输入的页码不合法");
				return;
			}
			
			var pageNo2 = parseInt(pageNo);
			if(pageNo2 < 1 || pageNo2 > parseInt("<%= page.getTotalPages() %>")){
				$(this).val("");
				alert("输入的页码不合法");
				return;
			}
			
			//查询条件需要放入到 class='condition' 的隐藏域中. 
			window.location.href = window.location.pathname + "?pageNo=" + pageNo2 + "<%= queryString %>";
		});
	})
</script>