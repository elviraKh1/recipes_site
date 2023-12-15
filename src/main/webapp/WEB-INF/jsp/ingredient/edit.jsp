<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<h1>Create Ingredient</h1>

<section class="pt-5 pb-5">
    <div class="container">

        <c:if test="${not empty success}">
            <div class="row justify-content-center">
                <div class="col-6 text-center">
                    <div class="alert alert-success" role="alert">
                            ${success}
                    </div>
                </div>
            </div>
        </c:if>

        <form method="GET" action="/ingredient/submit">
            <input type="hidden" name="id" value="${form.id}">
            <div class="mt-3">
                <label for="name" class="form-label">name</label>
                <input type="text" class="form-control" id="name"  name="name" aria-describedby="firstNameHelp"  value="${form.name}">
                <div id="firstNameHelp" class="form-text">Enter ingredient name.</div>

                <c:if test="${errors.hasFieldErrors('name')}">
                    <div style="color:red">
                        <c:forEach items="${errors.getFieldErrors('name')}" var="error">
                            ${error.defaultMessage}<br>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <div class="mt-3">
                <label for="instructions" class="form-label">instructions</label>
                <input type="text" class="form-control" id="instructions" name="instructions" aria-describedby="lastNameHelp"  value="${form.instructions}">
                <div id="lastNameHelp" class="form-text">enter instructions.</div>
                <c:if test="${errors.hasFieldErrors('instructions')}">
                    <div style="color:red">
                        <c:forEach items="${errors.getFieldErrors('instructions')}" var="error">
                            ${error.defaultMessage}<br>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <div class="mt-3">
                <label for="image_url" class="form-label">image</label>
                <input type="text" class="form-control" id="image_url" name="image_url" aria-describedby="departmentNameHelp"  value="${form.imageUrl}">
                <div id="departmentNameHelp" class="form-text">image_url</div
                <c:if test="${errors.hasFieldErrors('image_url')}">
                    <div style="color:red">
                        <c:forEach items="${errors.getFieldErrors('image_url')}" var="error">
                            ${error.defaultMessage}<br>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <div class="mt-3">
                <button type="submit" class="btn btn-primary">Submit</button>
                <c:if test="${form.id != null}"> <a href="/recipe/show/?id=${form.id}"
                                                    class="btn btn-primary  ">View</a> </c:if>
            </div>

        </form>
    </div>
</section>

<jsp:include page="../include/footer.jsp"/>