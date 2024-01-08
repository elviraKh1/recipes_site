<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="include/header.jsp"/>

<section id="recipes" class="recipes">
    <div class="container" data-aos="fade-up">

        <ul class="nav justify-content-center  nav-underline">
            <li class="nav-item">
                <a class="nav-link <c:if test="${category eq ''}" >active</c:if>" aria-current="page" href="/">All</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${category eq 'Breakfast'}" >active</c:if>" aria-current="page" href="/recipe/category?c=Breakfast">Breakfast</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${category eq 'Lunch'}" >active</c:if>" href="/recipe/category?c=Lunch">Lunch</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${category eq 'Dinner'}" >active</c:if>" href="/recipe/category?c=Dinner">Dinner</a>
            </li>
        </ul>

        <c:if test="${not empty recipes}">

        <h6 class="pt-3">recipes found ${recipes.getTotalElements()} </h6>

        <div class="tab-content" data-aos="fade-up" data-aos-delay="300" >
            <div class="row gy-4">
                <c:forEach items="${recipes.getContent()}" var="recipe">
                    <div class="col-lg-4 recipes-item  justify-content-center" >
                        <h4>${recipe.name}</h4>
                        <a href="/recipe/detail/?id=${recipe.id}" class="glightbox"><img
                                src="${recipe.imageUrl}" class="recipes-img img-recipes-item" alt=""></a>


                        <a href="/recipe/detail/?id=${recipe.id}"  ><button type="button" class="btn btn-outline-secondary">Detail</button></a>
                        <a href="/recipe/detail/?id=${recipe.id}"  ><button type="button" class="btn btn-outline-secondary">Bookmark</button></a>
                        <sec:authorize access="isAuthenticated()">
                            <c:if test="${user != null && recipe.authorId == user.id}">
                                <a href="/recipe/delete/?id=${recipe.id}"  > <button type="button" class="btn btn-outline-secondary">Delete</button></a>
                            </c:if>
                        </sec:authorize>
                    </div>
                </c:forEach>
            </div>
        </div>

        </c:if>
    </div>
</section>
        <nav >
            <ul class="pagination justify-content-center">
                <li class="page-item <c:if test="${!recipes.hasPrevious()}">disabled</c:if> "><a class="page-link" href="/?page=${recipes.getNumber()-1}">Prev</a></li>
                <li class="page-item <c:if test="${!recipes.hasNext()}">disabled</c:if> "><a class="page-link" href="/?page=${recipes.getNumber()+1}">Next</a></li>
            </ul>
        </nav>


<jsp:include page="include/footer.jsp"/>