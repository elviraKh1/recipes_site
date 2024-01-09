<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../include/header.jsp"/>
<section>
    <div class="bg-light2 pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h2 class="m-0">Users</h2>
            </div>
        </div>
    </div>
</section>

<section class="pt-5 pb-5">
    <div class="container" style="color: #49beb7">
        <c:if test="${param['success'] != null}">
            <div class="row justify-content-center">
                <div class="col-6 text-center">
                    <div class="alert alert-success" role="alert">
                            ${param['success']}
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty users}">
            <h6 class="pt-3">users found ${users.getTotalElements()} </h6>
            <table class='table'>
                <c:forEach items="${users.getContent()}" var="user">
                    <tr>
                        <td>${user.name}</td>
                        <td><a href="/admin/delete/${user.id}">delete</a></td>
                    </tr>
                </c:forEach>
            </table>
            <nav>
                <ul class="pagination justify-content-center">
                    <li class="page-item <c:if test="${!users.hasPrevious()}">disabled</c:if> "><a class="page-link"
                                                                                                         href="/admin/index?page=${users.getNumber()-1}">Prev</a>
                    </li>
                    <li class="page-item <c:if test="${!users.hasNext()}">disabled</c:if> "><a class="page-link"
                                                                                                     href="/admin/index?page=${users.getNumber()+1}">Next</a>
                    </li>
                </ul>
            </nav>
        </c:if>
    </div>
</section>


<jsp:include page="../include/footer.jsp"/>