<%-- 
    Document   : FoodManage
    Created on : Sep 27, 2024, 8:15:19 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Restoran - Bootstrap Restaurant Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&family=Pacifico&display=swap" rel="stylesheet">

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/animate/animate.min.css" rel="stylesheet">
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>


        <%@ include file="header.jsp" %>
        <%@ include file="loader.jsp" %>

        <div class="container-fluid py-5 wow fadeInUp bg-secondary" data-wow-delay="0.1s">
            <div class="row justify-content-center">
                <div class="col-12 bg-dark d-flex align-items-center">
                    <div class="p-5 wow fadeInUp w-100" data-wow-delay="0.2s">
                        <h5 class="section-title ff-secondary text-start text-primary fw-normal">Food List</h5>
                        <h1 class="text-white mb-4">Browse Our Food Menu</h1>

                        <!-- Search, Filter, Sort Form -->
                        <form action="FoodManage" method="get" class="mb-4">
                            <div class="row g-3">
                                <!-- Search Field -->
                                <div class="col-md-3">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="search" name="search" placeholder="Search by food name" value="${param.search}">
                                        <label for="search">Search by Food Name</label>
                                    </div>
                                </div>

                                <!-- Category Filter Dropdown -->
                                <div class="col-md-3">
                                    <div class="form-floating">
                                        <select class="form-control" id="categoryID" name="categoryID">
                                            <option value="" ${param.categoryID == '' ? 'selected' : ''}>All Categories</option>
                                            <c:forEach var="category" items="${categoryList}">
                                                <option value="${category.categoryID}" ${param.categoryID == category.categoryID ? 'selected' : ''}>${category.categoryName}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="categoryID">Filter by Category</label>
                                    </div>
                                </div>

                                <!-- Sort Options -->
                                <div class="col-md-3">
                                    <div class="form-floating">
                                        <select class="form-control" id="sortColumn" name="sortColumn">
                                            <option value="foodName" ${param.sortColumn == 'foodName' ? 'selected' : ''}>Food Name</option>
                                            <option value="categoryID" ${param.sortColumn == 'categoryID' ? 'selected' : ''}>Category</option>
                                            <option value="status" ${param.sortColumn == 'status' ? 'selected' : ''}>Status</option>
                                        </select>
                                        <label for="sortColumn">Sort by</label>
                                    </div>
                                </div>

                                <div class="col-md-3">
                                    <div class="form-floating">
                                        <select class="form-control" id="sortOrder" name="sortOrder">
                                            <option value="asc" ${param.sortOrder == 'asc' ? 'selected' : ''}>Ascending</option>
                                            <option value="desc" ${param.sortOrder == 'desc' ? 'selected' : ''}>Descending</option>
                                        </select>
                                        <label for="sortOrder">Sort Order</label>
                                    </div>
                                </div>

                                <!-- Add Button -->
                                <div class="col-md-12">
                                    <button class="btn btn-primary w-100 py-3" type="submit">Search & Filter</button>
                                </div>
                            </div>
                        </form>

                        <!-- Add Food Button -->
                        <div class="mb-4 text-end">
                            <a href="AddFood" class="btn btn-success">Add New Food</a>
                        </div>

                        <!-- Food List Table -->
                        <c:choose>
                            <c:when test="${empty foodList}">
                                <div class="alert alert-warning text-center">No Food Added</div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-dark table-striped table-bordered">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Food Name</th>
                                                <th>Status</th>
                                                <th>Category</th>
                                                <th>Image</th>
                                                <th>Actions</th> <!-- Column for Delete and Update -->
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="food" items="${foodList}">
                                                <tr>
                                                    <td>${food.foodID}</td>
                                                    <td>${food.foodName}</td>
                                                    <td>${food.status}</td>
                                                    <td>${food.categoryID}</td>
                                                    <td><img src="${food.image}" alt="Food Image" class="img-thumbnail" style="max-width: 50px;"></td>
                                                    <td>
                                                        <a href="UpdateFoodServlet?foodID=${food.foodID}" class="btn btn-warning btn-sm">Update</a>
                                                        <a href="DeleteFoodServlet?foodID=${food.foodID}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this food item?')">Delete</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>

                        <!-- Pagination -->
                        <nav aria-label="Page navigation example">
                            <ul class="pagination justify-content-center">
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="FoodManage?page=${currentPage - 1}&search=${param.search}&sortColumn=${param.sortColumn}&sortOrder=${param.sortOrder}&categoryID=${param.categoryID}" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                </c:if>
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                                        <a class="page-link" href="FoodManage?page=${i}&search=${param.search}&sortColumn=${param.sortColumn}&sortOrder=${param.sortOrder}&categoryID=${param.categoryID}">${i}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="FoodManage?page=${currentPage + 1}&search=${param.search}&sortColumn=${param.sortColumn}&sortOrder=${param.sortOrder}&categoryID=${param.categoryID}" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>

        <%@ include file="footer.jsp" %>



        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="lib/wow/wow.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/waypoints/waypoints.min.js"></script>
        <script src="lib/counterup/counterup.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="lib/tempusdominus/js/moment.min.js"></script>
        <script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
    </body>
</html>
