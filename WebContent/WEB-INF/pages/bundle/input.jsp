<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">  
    i = 1;  
    $(document).ready(function(){  
          
        $("#btn_add").click(function(){  
            document.getElementById("newUpload").innerHTML+='<div id="div_'+i+'"><input  name="file_'+i+'" type="file"  /><input type="button" value="删除"  onclick="del_('+i+')"/></div>';  
              i = i + 1;  
        });  
    });  
  
    function del_(o){  
         document.getElementById("newUpload").removeChild(document.getElementById("div_"+o));  
    }  
  
</script>  
</head>  
<body class="main">  
	<span class="page_title">上传bundle</span>
    <form name="userForm" action="${ctp }/bundle/upload" method="POST" enctype="multipart/form-data">
    	<div id="newUpload">
			<input type="file" name="file">
    	</div>
		<input type="button" id="btn_add" value="增加选择">
		<input type="submit" value="上传">
	</form>
</body>  
</html>  
