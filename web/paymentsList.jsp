<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="payments" class="java.util.ArrayList" scope="request"/>

<c:if test="${payments.size()!=0}">
    <c:forEach var="payment" items="${payments}">
        <div class="card mb-3">
            <div class="card-body">
                <h3>Payment number: ${payment.id}</h3>
                <p class="card-text">
                    Goods id: ${payment.lotId}<br>
                    Payer id: ${payment.payerId}<br>
                    Sum: ${payment.sum}<br>
                </p>
                <p class="card-text"><small class="text-muted">
                        ${payment.date}
                </small></p>
            </div>
        </div>
    </c:forEach>
</c:if>