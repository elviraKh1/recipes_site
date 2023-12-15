<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>
<section>
    <div class="bg-light2 pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">User Registration</h1>
            </div>
        </div>
    </div>
</section>

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

        <form method="GET" action="/auth/registerSubmit">

            <div class="mt-3">
                <label for="email" class="form-label">Email</label>
                <input type="text" class="form-control" id="email" name="email" aria-describedby="emailNameHelp"
                       value="${form.email}">
                <c:if test="${errors.hasFieldErrors('email')}">
                    <div style="color:red">
                        <c:forEach items="${errors.getFieldErrors('email')}" var="error">
                            ${error.defaultMessage}<br>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <div class="mt-3">
                <label for="password" class="form-label">Password</label>
                <input type="text" class="form-control" id="password" name="password" aria-describedby="passwordHelp"
                       value="${form.password}">
                <c:if test="${errors.hasFieldErrors('password')}">
                    <div style="color:red">
                        <c:forEach items="${errors.getFieldErrors('password')}" var="error">
                            ${error.defaultMessage}<br>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <div class="mt-3">
                <label for="confirmPassword" class="form-label">Password</label>
                <input type="text" class="form-control" id="confirmPassword" name="confirmPassword" aria-describedby="confirmPassworddHelp"
                       value="${form.confirmPassword}">
                <c:if test="${errors.hasFieldErrors('confirmPassword')}">
                    <div style="color:red">
                        <c:forEach items="${errors.getFieldErrors('confirmPassword')}" var="error">
                            ${error.defaultMessage}<br>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
</section>
<jsp:include page="../include/footer.jsp"/>