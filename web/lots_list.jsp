<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="lots" class="java.util.ArrayList" scope="request"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pageContent" var="pc"/>

<c:if test="${lots.size()!=0}">
    <c:forEach var="lot" items="${lots}">
        <div class="card mb-3" >
            <div class="card-body">
                <h5>${lot.name}</h5>
                <p class="card-text">
                        ${lot.description} <br>
                            <fmt:message key="lot.start_price" bundle="${pc}"/> ${lot.startPrice}
                </p>
                <p class="card-text"><small class="text-muted">${lot.location}</small></p>

                <form method="post" action="${pageContext.request.contextPath}/edit_lot?lotId=${lot.id}">
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="button.edit" bundle="${pc}"/>
                    </button>
                </form>
                <form method="post" action="${pageContext.request.contextPath}/delete_lot?lotId=${lot.id}">
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="button.delete" bundle="${pc}"/>
                    </button>
                </form>
                <form method="post" action="${pageContext.request.contextPath}/create_auction?lotId=${lot.id}">
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="button.put_up" bundle="${pc}"/>
                    </button>
                </form>
            </div>
        </div>
    </c:forEach>
</c:if>