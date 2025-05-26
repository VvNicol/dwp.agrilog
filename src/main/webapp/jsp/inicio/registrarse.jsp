<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agrilog - Registrarse</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/nav.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/mainRegistrarse.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico" />
</head>
<body>
    <nav class="navbar">
        <div class="logo-contenedor align-items-center justify-content-center">
            <img src="<%=request.getContextPath()%>/img/logo/Logo-sin-fondo.png" alt="Logo" class="logo" />
            <span class="logo-text">Agrilog</span>
        </div>
        <div class="buttons-container">
            <a href="${pageContext.request.contextPath}/inicio/principal" class="btn btn-transparent btnNav">Inicio</a>
            <a href="${pageContext.request.contextPath}/inicio/iniciar-sesion" class="btn btn-transparent btnNav">Iniciar Sesión</a>
            <a href="${pageContext.request.contextPath}/regis/registrarse" class="btn btn-activo btnNav">Registrarse</a>
        </div>
    </nav>
    <main>
        <div class="container-fluid p-0 contenedor">
            <!-- Carrusel de fondo -->
            <div class="background-carousel">
                <div id="carouselExample" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="${pageContext.request.contextPath}/img/fotos/Agricultura.jpg" class="d-block w-100" alt="Imagen del carrusel 1">
                        </div>
                        <div class="carousel-item">
                            <img src="${pageContext.request.contextPath}/img/fotos/Agricultura2.jpg" class="d-block w-100" alt="Imagen del carrusel 2">
                        </div>
                        <div class="carousel-item">
                            <img src="${pageContext.request.contextPath}/img/fotos/Agricultura3.jpg" class="d-block w-100" alt="Imagen del carrusel 3">
                        </div>
                    </div>
                </div>
            </div>
            <!-- Formulario centrado sobre el fondo -->
            <div class="form-container d-flex justify-content-center align-items-center">
                <div class="col-11 col-sm-10 col-md-8 col-lg-6 col-xl-4 p-5 formulario bg-light rounded shadow-lg">
                    <h2 class="text-center mb-4">Registrarse</h2>
                    <form id="registroForm" action="https://agrilog.nicoldev.es/regis/registrarse" method="POST">
                        <div class="mb-4">
                            <label for="nombreCompleto" class="form-label">Nombre Completo</label>
                            <input type="text" id="nombreCompleto" name="nombreCompleto" class="form-control" required>
                            <div class="invalid-feedback">El nombre completo es obligatorio.</div>
                        </div>
                        <div class="mb-4">
                            <label for="correo" class="form-label">Correo Electrónico</label>
                            <input type="email" id="correo" name="correo" class="form-control" required>
                            <div class="invalid-feedback">El correo electrónico es obligatorio y debe tener un formato válido.</div>
                        </div>
                        <div class="mb-4">
                            <label for="telefono" class="form-label">Teléfono</label>
                            <input type="tel" id="telefono" name="telefono" class="form-control" inputmode="numeric" pattern="^[0-9]+$" required>
                            <div class="invalid-feedback">El teléfono es obligatorio y debe contener solo números.</div>
                        </div>
                        <div class="mb-4">
                            <label for="contrasenia" class="form-label">Contraseña</label>
                            <input type="password" id="contrasenia" name="contrasenia" class="form-control" required>
                            <div class="invalid-feedback">La contraseña es obligatoria y debe tener al menos 6 caracteres.</div>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 mt-4">Registrarse</button>
                        <!-- Contenedor para mensajes -->
                        <div id="alerta-contenedor"></div>
                    </form>
                </div>
            </div>
        </div>
    </main>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/inicio/registrarse.js"></script>
</body>
</html>
