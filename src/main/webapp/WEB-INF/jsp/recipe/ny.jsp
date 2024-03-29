<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="../include/header.jsp"/>
<section>
    <div class="bg-light2 pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h2 class="m-0">My <c:if test="${bookmark!=null}">saved</c:if>  recipes</h2>
            </div>
        </div>
    </div>
</section>

<section class="pt-5 pb-5">
    <div class="container">
        <c:if test="${not empty recipes}">
            <h6 class="pt-3" style="color: #49beb7">recipes found ${recipes.getTotalElements()} </h6>
            <form action="/recipe/ny/delete/" method="post">
            <table class='table  table-striped table-hover'>

                <c:forEach items="${recipes.getContent()}" var="recipe">
                    <tr>
                        <td>${recipe.name}</td>
                        <td><a href="/recipe/detail/?id=${recipe.id}">View</a></td>
                        <c:if test="${bookmark == null}">
                            <td><a href="/recipe/edit/${recipe.id}">Edit</a></td>
                            <td><input type="checkbox" value="${recipe.id}" name="id"></td>
                        </c:if>
                    </tr>
                </c:forEach>

            </table>
            <c:if test="${bookmark == null}">
                <button type="submit"  class="btn btn-secondary">Delete</button>
            </c:if>
            </form>
            <nav>
                <ul class="pagination justify-content-center">
                    <li class="page-item <c:if test="${!recipes.hasPrevious()}">disabled</c:if> ">
                        <a class="page-link"  href="/recipe/ny?page=${recipes.getNumber()-1}">Prev</a>

                    </li>
                    <li class="page-item <c:if test="${!recipes.hasNext()}">disabled</c:if>  "><a class="page-link"
                                                                                                     href="/recipe/ny?page=${recipes.getNumber()+1}">Next</a>
                    </li>
                </ul>
            </nav>
        </c:if>
    </div>
</section>
<jsp:include page="../include/footer.jsp"/>