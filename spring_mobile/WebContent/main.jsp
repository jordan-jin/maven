<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="ko" style="height:100%;">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Normal</title>
	<meta name="keywords" content="" />
    <meta name="description" content="" />
	<script type="text/javascript" src="/inc/js/jquery-1.8.1.min.js"></script>
</head>
<!-- html body -->
<script type="text/javascript">
	function goJsonTest() {
		$.ajax({
			url : "/json/test.json"
			,data:{'cate_id':'cate_id'
				,'cate_name':'cate_name'
				,'description':'description'
				,'use_yn':'use_yn'}
			,contentType: "application/json;charset=UTF-8"
			,success : function(){
				alert('xxxxxxxxxxx');
			}
			,error : function(){
				alert('error');
			}
		});
	}
</script>
<body>
	Normal Page
	
	
	<input type="button" value="json" onclick="goJsonTest();"/>
</body>
<!-- //html body -->
</html>