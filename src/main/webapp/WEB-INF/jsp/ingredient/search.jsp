<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<form method="GET" action="/ingredient/search">
    <div class="mb-3">
        <label for="search" class="form-label">Search</label>
        <input type="text" class="form-control" id="search" name="search" placeholder="Search by name"
               value="${search}">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>


<c:if test="${not empty ingredients}">
    <h6 class="pt-3">recipes found ${ingredients.getTotalElements()} </h6>
    <table class='table'>
        <c:forEach items="${ingredients.getContent()}" var="ingredient">
            <tr>
                <td>${ingredient.name}</td>
                <td><a href="/ingredient/edit/${ingredient.id}">Edit</a></td>
            </tr>
        </c:forEach>
    </table>
    <nav>
        <ul class="pagination justify-content-center">
            <li class="page-item <c:if test="${!ingredients.hasPrevious()}">disabled</c:if> "><a class="page-link"
                                                                                             href="/ingredient/search?page=${ingredients.getNumber()-1}">Prev</a>
            </li>
            <li class="page-item <c:if test="${!ingredients.hasNext()}">disabled</c:if> "><a class="page-link"
                                                                                         href="/ingredient/search?page=${ingredients.getNumber()+1}">Next</a>
            </li>
        </ul>
    </nav>
</c:if>
    </div>
</section>
<jsp:include page="../include/footer.jsp"/>