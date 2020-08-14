<%-- 
    Document   : createNewTour
    Created on : Jun 21, 2020, 12:45:48 PM
    Author     : USER
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Tour</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">VietTravel</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse d-flex justify-content-between" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a class="nav-link btn btn-primary text-white" href="searchPage">Search <span class="sr-only">(current)</span></a>
                    </li>
                </ul>
                <div>
                    <c:if test="${not empty sessionScope.USER}">
                        <font color="red">Welcome, ${sessionScope.USER.name}</font>


                        <a class="btn border btn-light" href="logout">Logout</a>
                    </c:if>
                    <c:if test="${sessionScope.USER.role.name == 'user'}">
                        <a class="btn btn-info" href="view">View Cart</a>
                    </c:if>
                    <c:if test="${sessionScope.USER.role.name == 'Admin'}">
                        <a class="btn-primary btn" href="createTour">Create New Tour</a>        
                    </c:if>
                    <c:if test="${empty sessionScope.USER}">
                        <a class="btn btn-success" href="try">Login here!!!</a>
                    </c:if>

                </div>
            </div>
        </nav>
        <div class="container mt-3 border bg-light p-4" style="width: 500px" >
            <h1>Create New Tour Travel</h1>
            <form action="createNewTour" method="POST" enctype="multipart/form-data">

                <c:set var="errors" value="${requestScope.CREATEERROR}"></c:set>

                    Tour Name*: <input class="form-control" type="text" name="txtTourName" value="${param.txtTourName}" />  (2 - 70 characters)</br>
                <c:if test="${not empty errors.tourNameErr}">
                    <font color="red">${errors.tourNameErr}</font></br>     
                </c:if>
                Price*: <input class="form-control" type="text" name="txtPrice" value="${param.txtPrice}" /></br>
                <c:if test="${not empty errors.priceErr}">
                    <font color="red">${errors.priceErr}</font></br>     
                </c:if>
                Place*: <input class="form-control" type="text" name="txtPlace" value="${param.txtPlace}" /> (2 - 70 characters)</br>
                <c:if test="${not empty errors.placeErr}">
                    <font color="red">${errors.placeErr}</font></br>     
                </c:if>
                From Date*: <input class="form-control" type="date" name="txtFromDate" value="${param.txtFromDate}" /></br>
                <c:if test="${not empty errors.fromDateErr}">
                    <font color="red">${errors.fromDateErr}</font></br>     
                </c:if>
                To Date*: <input class="form-control" type="date" name="txtToDate" value="${param.txtToDate}" /></br>
                <c:if test="${not empty errors.toDateErr}">
                    <font color="red">${errors.toDateErr}</font></br>     
                </c:if>
                <c:if test="${not empty errors.toDateGreaterErr}">
                    <font color="red">${errors.toDateGreaterErr}</font></br> 
                </c:if>
                Quota*: <input class="form-control" type="text" name="txtQuota" value="${param.txtQuota}" /> </br>
                <c:if test="${not empty errors.quotaErr}">
                    <font color="red">${errors.quotaErr}</font></br>     
                </c:if>
                Image*: <input type="file" name="fileImage" value="${param.txtImage}" /></br>
                <c:if test="${not empty errors.imageErr}">
                    <font color="red">${errors.imageErr}</font></br>     
                </c:if>
                <input class="btn btn-success mt-2" type="submit" value="Add New Tour" />
                <input class="btn-light btn border mt-2" type="reset" value="Reset" />
            </form>
        </div>


    </body>
</html>
