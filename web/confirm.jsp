<%-- 
    Document   : confirm
    Created on : Jun 21, 2020, 10:56:55 AM
    Author     : USER
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Page</title>
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
                        <a class="btn btn-info" href="view">View Cart</a>

                        <a class="btn border btn-light" href="logout">Logout</a>
                    </c:if>
                    <c:if test="${empty sessionScope.USER}">
                        <a href="try">Login here!!!</a>
                    </c:if>
                    <c:if test="${sessionScope.USER.role.name == 'Admin'}">
                        <a href="createTour">Create New Tour</a>        
                    </c:if>
                </div>
            </div>
        </nav>
        <div class="container mt-3 border bg-light p-4" style="width: 500px" >
            <font color="red">Thank you for booking tour travel! Your confirm has completed</font>

            <a class="btn btn-success" href="search">Go shopping</a>
            <c:set var="cart" value="${requestScope.CART}"/>
            <c:set var="mapTravelTour" value="${cart.travelTour}"/>

        </div>
        <c:if test="${not empty cart}">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>TourName</th>
                        <th>Price</th>
                        <th>FromDate</th>
                        <th>ToDate</th>
                        <th>ImageLink</th>
                        <th>Amount</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cart.items}" varStatus="counter" >
                        <tr>
                            <c:set var="tourId" value="${item.key}"/>
                            <c:set var="amount" value="${item.value}"></c:set>
                            <c:set var="travelTourDTO" value="${mapTravelTour.get(tourId)}" ></c:set>
                            <td>${counter.count}</td>
                            <td>${travelTourDTO.tourName}</td>
                            <td>${cart.getPriceDisplay(tourId)}</td>
                            <td>${travelTourDTO.fromDate}</td>
                            <td>${travelTourDTO.toDate}</td>
                            <td>
                                <img src="${travelTourDTO.imageLink}" width="150"/>
                            </td> 
                            <td>
                                ${amount}
                            </td>
                            <td>
                                ${cart.getPriceOfEachItemDisplay(tourId)}
                            </td>
                        </c:forEach>
                    <tr>
                        <td>
                            Discount Percent: ${cart.discountPercent} (%)
                        </td>
                        <td>
                            - ${cart.discountValueDisplay}

                        </td>
                        <td>
                            Total Price: ${cart.totalPriceDisplay}

                        </td>
                    </tr>

                </tbody>
            </table>
        </c:if>


    </body>
</html>
