<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section id="hero" class="hero d-flex align-items-center section-bg pt-5">
    <div class="container">
        <div class="row justify-content-between gy-5 " >
            <div class="col-lg-5 order-2 order-lg-1 d-flex flex-column justify-content-start align-items-start align-items-lg-start "  >
                <h2 >${recipe.name}</h2>
                <div class="d-flex" data-aos="fade-up" data-aos-delay="200">
                    <a href="/recipe/category?c=${recipe.category}"
                       class="btn-book-a-table">Catefory: ${recipe.category} </a>
                </div>
                <div class="d-flex" data-aos="fade-up" data-aos-delay="200">
                    <a href="#book-a-table" class="btn-book-a-table">Author: ${recipe.authorId} </a>
                </div>
                <br>
                <div class="formatted-text-instructions">${recipe.instructions} </div>
                <h3 style="color: #085f63"> Ingredients:</h3>
                <table class="table  table-striped table-hover">
                    <thead>
                    <tr>
                        <th>ingredient</th>
                        <th>Measure</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ingredients}" var="ingredient">
                        <tr>
                            <td>${ingredient.ingredient.name}</td>
                            <td>${ingredient.measure}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
            <div class="col-lg-5 order-1 order-lg-2 text-center text-lg-start">
                <img src="${recipe.imageUrl}" class="img-fluid" alt="" data-aos="zoom-out" data-aos-delay="300">
            </div>
        </div>
        <div class="col-lg-15 pb-6 pt-6  ">
            <sec:authorize access="isAuthenticated()">
                <c:if test="${user != null && recipe.authorId == user.id}">
                    <a href='/recipe/edit/${recipe.id}' class='btn btn-secondary'>Edit</a>
                </c:if>
            </sec:authorize>
            <sec:authorize access="hasAnyAuthority('ADMIN')">
                <a href="/recipe/delete/?id=${recipe.id}">
                    <button type="button" class="btn btn-outline-secondary" onclick="return confirm('Are you sure you want to delete this recipe?')">Delete</button>
                </a>
            </sec:authorize>
        </div>
    </div>
</section>
<jsp:include page="../include/footer.jsp"/>
