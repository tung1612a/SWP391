<%-- 
    Document   : Signup
    Created on : Sep 23, 2024, 5:27:00 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

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


            <!-- Reservation Start -->
            <div class="container-xxl py-5 px-0 wow fadeInUp" data-wow-delay="0.1s">
                <div class="row g-0">
                    <div class="col-md-6">
                        <div class="video">
                            <button type="button" class="btn-play" data-bs-toggle="modal" data-src="https://www.youtube.com/embed/DWRcNpR6Kdc" data-bs-target="#videoModal">
                                <span></span>
                            </button>
                        </div>
                    </div>
                    <div class="col-md-6 bg-dark d-flex align-items-center">
                        <div class="p-5 wow fadeInUp" data-wow-delay="0.2s">
                            <h5 class="section-title ff-secondary text-start text-primary fw-normal"><a href="Login.jsp">back</a></h5>
                            <h1 class="text-white mb-4">Sign Up</h1>
                            <form action="OTPRegister" method="get">
                                <div class="row g-3">
                                    <!-- Username -->
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="text" class="form-control" id="username" placeholder="Username" name="username" required>
                                            <label for="username">Username</label>
                                        </div>
                                    </div>

                                    <!-- Password -->
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="password" class="form-control" id="password" placeholder="Password" name="password" required>
                                            <label for="password">Password</label>
                                        </div>
                                    </div>

                                    <!-- Customer Name -->
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="text" class="form-control" id="customerName" placeholder="Name" name="customerName" required>
                                            <label for="customerName">Name</label>
                                        </div>
                                    </div>

                                    <!-- Phone Number -->
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="text" class="form-control" id="phone" placeholder="Phone Number" name="phoneNumber"
                                                   pattern="^0\d{9}$" 
                                                   title="Số điện thoại phải bắt đầu bằng số 0 và có 10 chữ số. Ví dụ: 0123456789" 
                                                   required>
                                            <label for="phone">Phone Number</label>
                                        </div>
                                    </div>

                                    <!-- Email -->
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="email" class="form-control" id="email" placeholder="Email" name="email" required>
                                            <label for="email">Email</label>
                                        </div>
                                    </div>
                                </div><br>

                                <!-- Register Button -->
                                <div class="col-12">
                                    <button class="btn btn-primary w-100 py-3" type="submit">Register</button>
                                </div>

                                <!-- Hiển thị thông báo lỗi -->
                                <div class="col-12">
                                    <p class="text-danger mt-2">
                                        <%
                                            // Không cần khai báo session, vì nó đã có sẵn
                                            String errorMessage = (String) session.getAttribute("errorMessage");
                                            if (errorMessage != null) { 
                                                out.print(errorMessage); 
                                                session.removeAttribute("errorMessage"); // Xóa thông báo sau khi hiển thị
                                            }
                                        %>
                                    </p>
                                </div>
                            </form>



                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="videoModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content rounded-0">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Youtube Video</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <!-- 16:9 aspect ratio -->
                            <div class="ratio ratio-16x9">
                                <iframe class="embed-responsive-item" src="" id="video" allowfullscreen allowscriptaccess="always"
                                        allow="autoplay"></iframe>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Reservation Start -->


             <%@ include file="footer.jsp" %>


            <!-- Back to Top -->
            <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
        </div>

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
