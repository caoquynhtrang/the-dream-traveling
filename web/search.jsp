<%-- 
    Document   : admin
    Created on : Jun 10, 2020, 7:45:36 PM
    Author     : USER
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
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
                    <c:if test="${sessionScope.USER.role.name == 'User'}">
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
        <c:set var="search" value="${requestScope.SEARCH_RESULT}"/>
        <div class="container border mt-3 bg-light p-3" style="max-width: 700px"> 

            <form action="search" method="GET">
                <h3 class="text-center mb-4">Search form</h3>
                <div class="form-row mb-2">
                    <div class="col-md-6">
                        <input placeholder="Location" type="text" class="form-control" name="txtSearchLocation" value="${param.txtSearchLocation}" />
                    </div>
                </div>

                <div class="form-row">
                    <div class="col-md-6 mb-2">
                        <input placeholder="From Price" type="text" class="form-control" name="txtFromPrice" value="${param.txtFromPrice}" />
                    </div>
                    <div class="col-md-6">
                        <input placeholder="To Price" type="text" class="form-control" name="txtToPrice" value="${param.txtToPrice}" />
                    </div>
                </div>  
                <div class="form-row">
                    <div class="col-md-6">
                        <input placeholder="FromDate" type="date" class="form-control" name="txtFromDate" value="${param.txtFromDate}" />
                    </div>
                    <div class="col-md-6">
                        <input placeholder="To Date" type="date" class="form-control" name="txtToDate" value="${param.txtToDate}" />
                    </div>
                </div> 
                <input class="mt-3 btn btn-primary px-5" type="submit" value="Search" />
                <c:if test="${not empty search}">
                    <div class="form-row mt-3">
                        <div class="col-md-2">
                            <select class="custom-select" name="page">
                                <c:forEach varStatus="counter" items="${requestScope.TOTAL_PAGE}">
                                    <option
                                        <c:if test="${param.page eq counter.count}">
                                            selected="selected"
                                        </c:if>
                                        >
                                        ${counter.count}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <input class="btn btn-info" type="submit" value="Change Page" />
                        </div>

                    </div>
                </c:if>
            </form>
        </div>
        <div class="container mt-5" style="width: 1000px">

            <c:set var="cart" value="${sessionScope.CART}"/>
            <c:if test="${not empty search}">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>TourName</th>
                            <th>Price</th>
                            <th>FromDate</th>
                            <th>ToDate</th>
                            <th>ImageLink</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${search}" varStatus="counter">
                            <tr>
                                <td> 
                                    <c:if test="${not empty param.page}">
                                        ${(param.page - 1) * 5 + counter.count}
                                    </c:if>
                                    <c:if test="${empty param.page}">
                                        ${counter.count}
                                    </c:if>
                                </td>
                                <td>${dto.tourName}</td>
                                <td>${dto.getPriceDisplay(dto.tourId)}</td>
                                <td>${dto.fromDate}</td>
                                <td>${dto.toDate}</td>
                                <td>
                                    <img class="border rounded" src="${dto.imageLink}" width="150"/>
                                </td>
                                <td>
                                    <c:if test="${empty sessionScope.USER || sessionScope.USER.role.name == 'User'}">
                                        <form action="addItemToCart" action="POST">
                                            <input type="hidden" name="tourId" value="${dto.tourId}" />
                                            <input type="hidden" name="txtSearchLocation" value="${param.txtSearchLocation}" />
                                            <input type="hidden" name="txtFromPrice" value="${param.txtFromPrice}" />
                                            <input type="hidden" name="txtToPrice" value="${param.txtToPrice}" />
                                            <input type="hidden" name="txtFromDate" value="${param.txtFromDate}" />
                                            <input type="hidden" name="txtToDate" value="${param.txtToDate}" />
                                            <input type="hidden" name="page" value="${param.page}" />
                                            <input class="btn btn-success" type="submit" value="Add To Cart"/>
                                        </form>
                                    </c:if>

                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </c:if>
            <c:if test="${not empty param.txtSearchLocation && empty search}">
                <h5 class="alert alert-danger text-center">Not Found!!!!!</h5>
            </c:if>
        </div>








    </body>
</html>
