<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, games, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="<spring:url value="/" htmlEscape="true"/>"><span></span></a>
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#main-navbar">
                <span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
        </div>
        <div class="navbar-collapse collapse" id="main-navbar">
            <ul class="nav navbar-nav">
                <sec:authorize access="isAuthenticated()">
                    <nt4h:menuItem active="${name eq 'home'}" url="/"
                                   title="home page">
                        <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                        <span>Home</span>
                    </nt4h:menuItem>
                    <nt4h:menuItem active="${name eq 'Users'}" url="/users"
                                   title="User">
                        <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>
                        <span>Users</span>
                    </nt4h:menuItem>
                    <nt4h:menuItem active="${name eq 'friends'}" url="/friends"
                                   title="friends">
                        <span class="glyphicon glyphicon-menu-hamburger" aria-hidden="true"></span>
                        <span>Friends</span>
                    </nt4h:menuItem>
                    <nt4h:menuItem active="${name eq 'Card'}" url="/cards/buttonsCards"
                                   title="Card">
                        <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                        <span>Cards</span>
                    </nt4h:menuItem>
                </sec:authorize>
            </ul>





			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">

                    <li class="dropdown"><a href="#" class="dropdown-toggle"
                                            data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
                        <strong><sec:authentication property="name"/></strong> <span
                                class="glyphicon glyphicon-chevron-down"></span>
                    </a>
                        <ul class="dropdown-menu">
                            <li>
                                <div class="navbar-login">
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <p class="text-center">
                                                <span class="glyphicon glyphicon-user icon-size"></span>
                                            </p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
												   class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
											<p class="text-left">
												<a href="<c:url value="/users/details" />"
												   class="btn btn-primary btn-block btn-sm">My Profile</a>
											</p>
											<p class="text-left">
												<a href="<c:url value="/users/delete" />"
												   class="btn btn-primary btn-block btn-sm">Delete</a>
											</p>
										</div>
									</div>
								</div>
                            </li>
                        </ul>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
