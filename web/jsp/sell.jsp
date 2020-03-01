<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
    <form method="post" id="formWithNumber" action="${pageContext.request.contextPath}/create_lot">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="lot.name" bundle="${pc}"/>
            </label>
            <div class="col-sm-6">
                <input type="text" name="name" id="name" class="form-control" required/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="lot.description" bundle="${pc}"/>
            </label>
            <label class="col-sm-6">
                <input type="text" name="description" id="description" class="form-control" required/>
            </label>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="lot.location" bundle="${pc}"/>
            </label>
            <label class="col-sm-6">
                <input type="text" name="location" id="location" class="form-control" required/>
            </label>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="lot.start_price" bundle="${pc}"/>
            </label>
            <label class="col-sm-6">
                <input type="text" name="startPrice" id="number" class="form-control" required
                       onchange="document.getElementById('numberError').style.display='none'"/>
                <div id="numberError" style="display: none" class="invalid-feedback"><fmt:message
                        key="message.invalid_value" bundle="${pc}"/></div>
            </label>
        </div>
        <button type="submit" class="btn btn-dark">
            <fmt:message key="button.save" bundle="${pc}"/>
        </button>
    </form>
</div>

<jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>

<script>
    <jsp:directive.include file="/js/numberValidator.js"/>
</script>
