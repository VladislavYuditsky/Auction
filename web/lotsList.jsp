<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="lots" class="java.util.ArrayList" scope="request"/>

<c:if test="${lots.size()!=0}">
    <c:forEach var="lot" items="${lots}">
        <div class="card mb-3" >
            <div class="card-body">
                <h5>${lot.name}</h5>
                <p class="card-text">${lot.description}</p>
                <p class="card-text"><small class="text-muted">${lot.location}</small></p>
            </div>
        </div>
    </c:forEach>
</c:if>