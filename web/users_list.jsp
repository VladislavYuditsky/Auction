<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pageContent" var="pc"/>


<jsp:useBean id="users" class="java.util.ArrayList" scope="request"/>

<c:if test="${users.size()!=0}">
    <table class="table table-striped">
    <thead>
    <tr>
        <th scope="col"><fmt:message key="users_table.id" bundle="${pc}"/></th>
        <th scope="col"><fmt:message key="users_table.login" bundle="${pc}"/></th>
        <th scope="col"><fmt:message key="users_table.email" bundle="${pc}"/></th>
        <th scope="col"><fmt:message key="users_table.role" bundle="${pc}"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
        </tr>
    </c:forEach>
</c:if>