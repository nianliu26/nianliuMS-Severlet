<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2022/1/1
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
</head>
<body>

    <div style="text-align: center">
        <form action="/login" method="post" id="loginform">
            姓名：<input type="text" name="uname"><br>
            密码：<input type="password" name="upwd"><br>
            <span id="msg" style="font-size: 12px;color: bisque"></span><br>
            <button type="button">登录</button>
            <button type="button">注册</button>
        </form>
    </div>
</body>
</html>
