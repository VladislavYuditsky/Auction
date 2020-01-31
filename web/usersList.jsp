<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="users" class="java.util.ArrayList" scope="request"/>

<c:if test="${users.size()!=0}">
    <table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">Id</th>
        <th scope="col">Login</th>
        <th scope="col">Email</th>
        <th scope="col">Role</th>
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