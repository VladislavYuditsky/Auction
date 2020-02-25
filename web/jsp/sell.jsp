<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
    <form method="post" action="${pageContext.request.contextPath}/create_lot">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="lot.name" bundle="${pc}"/>
            </label>
            <div class="col-sm-6">
                <input type="text" name="name" class="form-control" required/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="lot.description" bundle="${pc}"/>
            </label>
            <label class="col-sm-6">
                <input type="text" name="description" class="form-control" required/>
            </label>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="lot.location" bundle="${pc}"/>
            </label>
            <label class="col-sm-6">
                <input type="text" name="location" class="form-control" required/>
            </label>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="lot.start_price" bundle="${pc}"/>
            </label>
            <label class="col-sm-6">
                <input type="text" name="startPrice" class="form-control" required/>
            </label>
        </div>
        <button type="submit" class="btn btn-dark">
            <fmt:message key="button.save" bundle="${pc}"/>
        </button>
    </form>
</div>

</body>
</html>
