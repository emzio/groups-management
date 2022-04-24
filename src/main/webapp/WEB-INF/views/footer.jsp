<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<span>footer</span>
</div>
</div>

<div class="col-md-2 d-sm-none d-md-block">
    <p>sidebar</p>
    <div class="card">
        <div class="card-body, text-black-50">
            <h4 class="text-dark">WELCOME
                <sec:authorize access="isAuthenticated()">
                    <p> <sec:authentication property="principal.username"/></p>
                    <p>Uprawnienia: <sec:authentication property="authorities"/></p>
                </sec:authorize>
            </h4>
        </div>
    </div>
    <hr class="my-3">
    <p><a href="http://localhost:8080" type="button" class="btn btn-success" >main</a></p>
    <p><sec:authorize access="isAnonymous()"><a href="/login" class="btn btn-success">Login</a></sec:authorize></p>
    <p><sec:authorize access="isAnonymous()"><a href="/registry" class="btn btn-success">Registry</a></sec:authorize></p>
    <sec:authorize access="isAuthenticated()">
        <form action="<c:url value="/logout"/>" method="post">
            <input type="submit" value="Wyloguj"class="btn btn-info">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </sec:authorize>
</div>
</div>
</div>



<script src="https://unpkg.com/@popperjs/core@2.4.0/dist/umd/popper.min.js"></script>
<script src="bootstrap.js"></script>
</body>
</html>