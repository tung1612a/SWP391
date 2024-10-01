<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Staff Management</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Libraries Stylesheet -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>

        <%@ include file="header.jsp" %>

        <!-- Staff List Container -->
        <div class="container-fluid py-5 bg-secondary">
            <div class="row justify-content-center">
                <div class="col-12 bg-dark d-flex align-items-center">
                    <div class="p-5 w-100">
                        <h5 class="section-title ff-secondary text-start text-primary fw-normal">Staff List</h5>
                        <h1 class="text-white mb-4">Browse Our Staff</h1>

                        <!-- Search, Filter, Sort Form -->
                        <form action="StaffManage" method="get" class="mb-4">
                            <div class="row g-3">
                                <!-- Search Field -->
                                <div class="col-md-3">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="search" name="search" placeholder="Search by staff name" value="${param.search}">
                                        <label for="search">Search by Staff Name</label>
                                    </div>
                                </div>

                                <!-- Sort Options -->
                                <div class="col-md-3">
                                    <div class="form-floating">
                                        <select class="form-control" id="sortColumn" name="sortColumn">
                                            <option value="StaffName" ${param.sortColumn == 'StaffName' ? 'selected' : ''}>Staff Name</option>
                                            <option value="PhoneNumber" ${param.sortColumn == 'PhoneNumber' ? 'selected' : ''}>Phone Number</option>
                                            <option value="Email" ${param.sortColumn == 'Email' ? 'selected' : ''}>Email</option>
                                            <option value="Salary" ${param.sortColumn == 'Salary' ? 'selected' : ''}>Salary</option>
                                        </select>
                                        <label for="sortColumn">Sort by</label>
                                    </div>
                                </div>

                                <!-- Sort Order -->
                                <div class="col-md-3">
                                    <div class="form-floating">
                                        <select class="form-control" id="sortOrder" name="sortOrder">
                                            <option value="asc" ${param.sortOrder == 'asc' ? 'selected' : ''}>Ascending</option>
                                            <option value="desc" ${param.sortOrder == 'desc' ? 'selected' : ''}>Descending</option>
                                        </select>
                                        <label for="sortOrder">Sort Order</label>
                                    </div>
                                    <!-- Search Button -->
                                </div>

                                <div class="col-md-3">
                                    <button class="btn btn-primary w-100 py-3" type="submit">Search & Filter</button>
                                </div>
                            </div>
                        </form>

                        <div class="mb-4 text-end">
                            <a href="AddStaff" class="btn btn-success">Add New Staff</a>
                        </div>

                        <!-- Staff List Table -->
                        <c:choose>
                            <c:when test="${empty staffList}">
                                <div class="alert alert-warning text-center">No Staff Found</div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-dark table-striped table-bordered">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Staff Name</th>
                                                <th>Phone Number</th>
                                                <th>Email</th>
                                                <th>Salary</th>
                                                <th>New Account</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="staff" items="${staffList}">
                                                <tr>
                                                    <td>${staff.staffID}</td>
                                                    <td>${staff.staffName}</td>
                                                    <td>${staff.phoneNumber}</td>
                                                    <td>${staff.email}</td>
                                                    <td>${staff.salary}</td>
                                                    <td>${staff.newAccount ? 'Yes' : 'No'}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${staff.accountID == 0}">
                                                                Staff Fired
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a href="FireStaff?staffID=${staff.staffID}&page=${currentPage}&accountID=${staff.accountID}" class="btn btn-danger btn-sm">Fire</a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>

                        <!-- Pagination -->
                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-center">
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="StaffManage?page=${currentPage - 1}&search=${param.search}&sortColumn=${param.sortColumn}&sortOrder=${param.sortOrder}" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                </c:if>
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                                        <a class="page-link" href="StaffManage?page=${i}&search=${param.search}&sortColumn=${param.sortColumn}&sortOrder=${param.sortOrder}">${i}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="StaffManage?page=${currentPage + 1}&search=${param.search}&sortColumn=${param.sortColumn}&sortOrder=${param.sortOrder}" aria-label="Next">
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
