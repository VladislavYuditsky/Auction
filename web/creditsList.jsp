<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="credits" class="java.util.ArrayList" scope="request"/>

<c:if test="${credits.size()!=0}">
    <c:forEach var="credit" items="${credits}">
        <div class="card mb-3">
            <div class="card-body">
                <h3>Credit id: ${credit.id}</h3>
                <p class="card-text">
                    Sum: ${credit.sum}<br>
                    Balance: ${credit.balance}<br>
                    Borrower id: ${credit.borrowerId}
                <form method="post" action="${pageContext.request.contextPath}/user_credits?creditId=${credit.id}">
                    <input type="text" name="repaidSum"/>
                    <button type="submit">Repay</button>
                </form>
                </p>
                <p class="card-text"><small class="text-muted">
                    Repaid before ${credit.endDate}
                </small></p>
            </div>
        </div>
    </c:forEach>
</c:if>