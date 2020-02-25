<jsp:useBean id="lot" scope="request" type="com.yuditsky.auction.entity.Lot"/>

<%@include file="/jsp/parts/common.jsp" %>

<div>
    <label>${lot.name}</label>
</div>
<div>
    <label>${lot.description}</label>
</div>
<div>
    <label>
        <fmt:message key="lot.location" bundle="${pc}"/> ${lot.location}
    </label>
</div>
