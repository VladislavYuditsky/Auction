<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="users" class="java.util.ArrayList" scope="request"/>

<%@include file="/jsp/parts/common.jsp" %>

<c:if test="${users.size()!=0}">
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="users_table.id" bundle="${pc}"/></th>
            <th scope="col"><fmt:message key="users_table.login" bundle="${pc}"/></th>
            <th scope="col"><fmt:message key="users_table.email" bundle="${pc}"/></th>
            <th scope="col"><fmt:message key="users_table.role" bundle="${pc}"/></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/change_block_status?userId=${user.id}">
                    <button type="submit" class="btn btn-dark">
                        <c:choose>
                            <c:when test="${user.blocked == true}">
                                <fmt:message key="button.unblock" bundle="${pc}"/>
                            </c:when>

                            <c:otherwise>
                                <fmt:message key="button.block" bundle="${pc}"/>
                            </c:otherwise>
                        </c:choose>
                    </button>
                </form>
            </td>
        </tr>
        </c:forEach>
</c:if>