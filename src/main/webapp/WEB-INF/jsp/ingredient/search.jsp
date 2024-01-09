<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="../include/header.jsp"/>
<section>
    <div class="bg-light2 pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h2 class="m-0">Search ingredient</h2>
            </div>
        </div>
    </div>
</section>

<section class="pt-5 pb-5">
    <div class="container">

        <form method="GET" action="/ingredient/search" class="row ">
            <div>
                <label for="search" class="form-label">Search</label>
                <input type="text" class="form-control" id="search" name="search" placeholder="Search ingredient"
                       value="${search}">
                <select name="sorting" class="form-control">
                    <option>sort by </option>
                    <option value="asc">name asc</option>
                    <option value="desc">name desc</option>
                </select>
                <button type="submit" class="btn btn-secondary">Submit</button>
            </div>

        </form>


        <c:if test="${not empty ingredients}">
            <h6 class="pt-3" style="color: #49beb7">ingredients found ${ingredients.getTotalElements()} </h6>
            <table class='table  table-striped table-hover'>
                <c:forEach items="${ingredients.getContent()}" var="ingredient">
                    <tr>
                        <td><a href="/recipe/searchByIng?ingredientid=${ingredient.id}">${ingredient.name}</a></td>
                        <sec:authorize access="hasAnyAuthority('ADMIN')">
                            <td><a href="/ingredient/edit/${ingredient.id}">Edit</a></td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
            </table>
            <nav>
                <ul class="pagination justify-content-center">
                    <li class="page-item <c:if test="${!ingredients.hasPrevious()}">disabled</c:if> ">
                        <a class="page-link"  href="/ingredient/search?page=${ingredients.getNumber()-1}<c:if test="${param['sorting'] != null}">&sorting=${param['sorting']}</c:if>">Prev</a>

                    </li>
                    <li class="page-item <c:if test="${!ingredients.hasNext()}">disabled</c:if> "><a class="page-link"
                            href="/ingredient/search?page=${ingredients.getNumber()+1}<c:if test="${param['sorting'] != null}">&sorting=${param['sorting']}</c:if>">Next</a>
                    </li>
                </ul>
            </nav>
        </c:if>
    </div>
</section>
<jsp:include page="../include/footer.jsp"/>