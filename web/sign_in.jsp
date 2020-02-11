<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pageContent" var="pc"/>

<html>
<head>
    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
    <form id="form" method="post" action="${pageContext.request.contextPath}/sign_in">
        <label>
            <fmt:message key="user.login" bundle="${pc}"/>
            <input type="text" name="login" id="login" required>
        </label>
        <br>
        <label>
            <fmt:message key="user.password" bundle="${pc}"/>
            <input type="password" name="password" id="password" required>
        </label>
        <br>
        <button type="submit" onclick="valid()">
            <fmt:message key="button.sign_in" bundle="${pc}"/>
        </button>
    </form>
    <jsp:include page="bootstrap_footer.jsp"/>
</body>

<script>
    function valid() {
        let flag = true;
        const loginPattern = /^[a-zA-Z\u0430-\u044F\u0410-\u042F]{1}[a-zA-Z\u0430-\u044F\u0410-\u042F\d\u002E\u005F]{3,10}$/;
        const passwordPattern = /^[a-zA-Z\u0430-\u044F\u0410-\u042F]{1}[a-zA-Z1-9\u0430-\u044F\u0410-\u042F]{5,20}$/;
        let login = document.getElementById("login").value;
        let password = document.getElementById("password").value;
        if (!loginPattern.exec(login)) {
            flag = false;
        }
        if (!passwordPattern.exec(password)) {
            flag = false;
        }
        if (flag) {
            document.getElementById("form").submit();
        } else {
            alert("<fmt:message key="signIn/reg.message" bundle="${pc}"/>");
        }
    }
</script>

</html>
