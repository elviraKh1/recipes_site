<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="include/header.jsp"/>
<h1 class="pb-3">search by ingredient</h1>

<form method="GET" action="/recipe/search">
    <div class="mb-3">
        <label for="search" class="form-label">Search</label>
        <input type="text" class="form-control" id="search" name="search" placeholder="Search by name"
               value="${search}">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>


<c:if test="${not empty recipes}">
    <h6 class="pt-3">recipes found ${recipes.getTotalElements()} </h6>
    <table class='table'>
        <c:forEach items="${recipes.getContent()}" var="recipe">
            <tr>
                <td>${recipe.name}</td>
                <td>${recipe.id}</td>
                <td><img src="${recipe.imageUrl}" style="max-width: 100px"></td>
                    <%--                <td><a href="/customer/edit/${customer.id}">Edit</a></td>--%>
                <td><a href="/recipe/detail/?id=${recipe.id}">Detail</a></td>
                <sec:authorize access="isAuthenticated()">
                    <c:if test="${user != null && recipe.authorId == user.id}">
                        <td><a href="/recipe/delete/?id=${recipe.id}">delete</a></td>
                    </c:if>
                </sec:authorize>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${recipes.hasPrevious()}"><a href="/?page=${recipes.getNumber()-1}">&lt;</a></c:if>
    <c:if test="${recipes.hasNext()}"><a href="/?page=${recipes.getNumber()+1}">&gt;</a></c:if>

</c:if>
<jsp:include page="include/footer.jsp"/>