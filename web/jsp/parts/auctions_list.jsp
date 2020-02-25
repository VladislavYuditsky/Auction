<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="lots" class="java.util.ArrayList" scope="request"/>

<%@include file="/jsp/parts/common.jsp" %>

<c:if test="${lots.size()!=0}">
    <c:forEach var="lot" items="${lots}">
        <div class="card mb-3">
            <div class="card-body">
                <a href="${pageContext.request.contextPath}/auction?lotId=${lot.id}"><h5>${lot.name}</h5></a>
                <p class="card-text">${lot.description}</p>
                <p class="card-text"><small class="text-muted">${lot.location}</small></p>
            </div>
        </div>
    </c:forEach>
</c:if>