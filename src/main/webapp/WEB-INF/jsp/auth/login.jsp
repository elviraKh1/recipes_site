<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>
<section>
    <div class="bg-light2 pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h2 class="m-0">Login</h2>
            </div>
        </div>
    </div>
</section>
<section class="pt-15 pb-15">
    <div class="container">

        <c:if test="${param['error'] eq ''}">
            <div class="row justify-content-center">
                <div class="col-5 pb-15 alert alert-danger">
                    Invalid Username or Password
                </div>
            </div>
        </c:if>


        <div class="row justify-content-center">
            <div class="col-5 pb-15  " style="background-color: #f1f1f1;">
                <form method="post" action="/auth/loginSubmit">
                    <div class="col-lg-12 col-md-6">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" name="username"
                               placeholder="Your username">
                    </div>

                    <div class="col-lg-12 col-md-6">
                        <label for="password" class="form-label">Password</label>
                        <input type="text" class="form-control" id="password" name="password"
                               placeholder="Your password">>
                    </div>

                    <div class="text-center pb-3">
                        <button type="submit" class="btn btn-secondary mt-4  ">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<jsp:include page="../include/footer.jsp"/>