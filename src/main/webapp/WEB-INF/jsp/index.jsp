<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="include/header.jsp"/>

<section id="recipes" class="recipes">
    <div class="container"  >

        <ul class="nav justify-content-center  nav-underline">
            <li class="nav-item">
                <a class="nav-link <c:if test="${category eq ''}" >active</c:if>" aria-current="page" href="/"><h4>All</h4></a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${category eq 'Breakfast'}" >active</c:if>" aria-current="page"
                   href="/recipe/category?c=Breakfast"><h4>Breakfast</h4></a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${category eq 'Lunch'}" >active</c:if>" href="/recipe/category?c=Lunch"><h4>Lunch</h4></a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${category eq 'Dinner'}" >active</c:if>"
                   href="/recipe/category?c=Dinner"><h4>Dinner</h4></a>
            </li>
        </ul>

        <c:if test="${not empty recipes}">

            <h6 class="pt-3" style="color: #49beb7">Recipes found ${recipes.getTotalElements()} <c:if test="${param['search'] != null}">. Search word is (${param['search']})</c:if></h6>

        <div class="row"  >
            <div class="tab-content" style="  width: 100%" >
                <div class="row gy-4  text-center" >

                    <c:forEach items="${recipes.getContent()}" var="recipe" >
                        <div class="col-lg-4 recipes-item  justify-content-center pt-3  " style="background-color: #eeeeee ; border-radius: 10px;">
                            <h4 style="height: 70px;  ">${recipe.name}</h4>
                            <a href="/recipe/detail/?id=${recipe.id}" class="glightbox" >
                                <img src="<c:if test="${recipe.imageUrl== null || recipe.imageUrl eq ''}">../../pub/images/empyImage.jpg</c:if>${recipe.imageUrl}" class="recipes-img img-recipes-item" alt=""></a>

                            <a href="/recipe/detail/?id=${recipe.id}">
                                <button type="button" class="btn btn-outline-secondary">Detail</button>
                            </a>
                            <a href="/recipe/detail/?id=${recipe.id}">
                                <button type="button" class="btn btn-outline-secondary ">Bookmark</button>
                            </a>
                            <sec:authorize access="isAuthenticated()">
                                <c:if test="${user != null && recipe.authorId == user.id}">
                                    <a href="/recipe/delete/?id=${recipe.id}">
                                        <button type="button" class="btn btn-outline-secondary">Delete</button>
                                    </a>
                                </c:if>
                            </sec:authorize>
                        </div>
                    </c:forEach>

                </div>
            </div>
        </div>
        </c:if>

    </div>
</section>
<nav>
    <ul class="pagination justify-content-center">
        <li class="page-item <c:if test="${!recipes.hasPrevious()}">disabled</c:if> "><a class="page-link"
                                                                                         href="/?page=${recipes.getNumber()-1}">Prev</a>
        </li>
        <li class="page-item <c:if test="${!recipes.hasNext()}">disabled</c:if> "><a class="page-link"
                                                                                     href="/?page=${recipes.getNumber()+1}">Next</a>
        </li>
    </ul>
</nav>


<jsp:include page="include/footer.jsp"/>