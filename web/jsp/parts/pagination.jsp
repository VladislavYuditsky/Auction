<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="/jsp/parts/common.jsp" %>
<jsp:useBean id="pages" class="java.util.ArrayList" scope="request"/>

<div class="mt-3">
    <ul class="pagination">
        <li class="page-item disabled">
            <a class="page-link" href="#" tabindex="-1">
                <fmt:message key="pages" bundle="${pc}"/>
            </a>
        </li>
        <c:set var="curPage" value="${requestScope.currentPage + 1}"/>

        <div style="display: none">
            <c:choose>
                <c:when test="${requestScope.pagesNumber > 7}">

                    <c:choose>
                        <c:when test="${curPage > 4}">
                            ${pages.add(1)}
                            ${pages.add(-1)}
                        </c:when>
                        <c:otherwise>
                            ${pages.add(1)}
                            ${pages.add(2)}
                            ${pages.add(3)}
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${curPage > 4 && curPage < requestScope.pagesNumber - 1}">
                        ${pages.add(curPage - 2)}
                        ${pages.add(curPage - 1)}
                    </c:if>

                    <c:if test="${curPage > 3 && curPage < requestScope.pagesNumber - 2}">
                        ${pages.add(curPage)}
                    </c:if>

                    <c:if test="${curPage > 2 && curPage < requestScope.pagesNumber - 3}">
                        ${pages.add(curPage + 1)}
                        ${pages.add(curPage + 2)}
                    </c:if>

                    <c:choose>
                        <c:when test="${curPage < requestScope.pagesNumber - 3}">
                            ${pages.add(-1)}
                            ${pages.add(requestScope.pagesNumber)}
                        </c:when>
                        <c:otherwise>
                            ${pages.add(requestScope.pagesNumber - 2)}
                            ${pages.add(requestScope.pagesNumber - 1)}
                            ${pages.add(requestScope.pagesNumber)}
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <c:otherwise>
                    <c:forEach var="i" begin="${1}" end="${requestScope.pagesNumber}">
                        ${pages.add(i)}
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <c:forEach items="${pages}" var="p">
            <c:choose>
                <c:when test="${p - 1 == requestScope.currentPage}">
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${p}</a>
                    </li>
                </c:when>

                <c:when test="${p == -1}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1">...</a>
                    </li>
                </c:when>

                <c:otherwise>

                    <c:url value="${servletPath}" var="url">
                        <c:param name="currentPage" value="${p - 1}"/>
                    </c:url>

                    <li class="page-item">
                        <a class="page-link"
                                href="${url}"
                           tabindex="-1">${p}</a>
                    </li>
                </c:otherwise>
            </c:choose>

        </c:forEach>

    </ul>
</div>