<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>test</title>
</head>
<script type="text/javascript">
    function selectUser() {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                document.getElementById("test").innerHTML = xmlhttp.responseText;
            }
        }
        xmlhttp.open("POST", "<%=request.getContextPath()%>/storm/user/showUser.do", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("id=1DD2021081FE67BAC04E53A82193AFAB");
    }
</script>
<body>
<p id="test">Hello World!</p>
<button type="button" onclick="selectUser()">请求数据</button>
</body>

</html>