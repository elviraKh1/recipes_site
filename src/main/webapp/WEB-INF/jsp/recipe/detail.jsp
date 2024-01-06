<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-light2 pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Recipe</h1>
            </div>
        </div>
    </div>
</section>

<section style="background-color: #eee;">
    <div class="container py-5">

        <div class="row">
            <div class="col-lg-3">
                <div class="card mb-4">
                    <div class="card-body text-center">
                        <img src="${recipe.imageUrl}" alt="avatar"
                             class="rounded-circle img-fluid" style="width: 180px;">
                        <h5 class="my-3">${recipe.name}</h5>
                    </div>
                </div>
            </div>


            <div class="col-lg-8">
                <div class="card mb-4">
                    <div class="card-body">

                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">Name</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0">${recipe.name}</p>
                            </div>
                        </div>

                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">instructions</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0">${recipe.instructions}</p>
                            </div>
                        </div>
                        <hr>

                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0">Author</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0">${recipe.authorId}</p>
                            </div>
                        </div>
                        <hr>

                        <div class="row">
                            <div class="col-sm-3"></div>
                            <div class="col-sm-9">
                                <sec:authorize access="isAuthenticated()">
                                    <c:if test="${user != null && recipe.authorId == user.id}">
                                        <a href='/recipe/edit/${recipe.id}' class='btn btn-primary '>Edit</a>
                                    </c:if>
                                </sec:authorize>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</section>
<table class='table'>
    <c:forEach items="${ingredients}" var="ingredient">
        <tr>
            <td>${ingredient.ingredient.name}</td>
            <td>${ingredient.measure}</td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="../include/footer.jsp"/>
