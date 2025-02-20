<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
    crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/estilos/nav.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/estilos/mainIniciarSesion.css">
<link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/favicon.ico" />

<meta charset="UTF-8">
<title>Agrilog - Iniciar Sesión</title>
</head>
<body>
    <nav class="navbar">
        <div class="logo-contenedor align-items-center justify-content-center">
            <img src="<%=request.getContextPath()%>/img/logo/Logo-sin-fondo.png"
                alt="Logo" class="logo" />
            <span class="logo-text">Agrilog</span>
        </div>

        <div class="buttons-container">
            <a href="<%=request.getContextPath()%>/index.jsp" class="btn btn-transparent btnNav">Inicio</a>
            <a href="<%=request.getContextPath()%>/html/inicio/iniciarSesion.jsp" class="btn btn-transparent btnNav">Iniciar Sesión</a>
            <a href="<%=request.getContextPath()%>/html/inicio/registrarse.jsp" class="btn btn-transparent btnNav">Registrarse</a>
        </div>
    </nav>

    <main>
        <div class="row contenedor">
            <img src="<%=request.getContextPath()%>/img/fotos/Agricultura7.jpg" alt="Fondo" class="imagen-fondo">

            <div class="col-12 col-md-10 d-flex align-items-center justify-content-center p-5">
                <div class="col-lg-8 col-md-10 col-12 p-4 formulario bg-light rounded shadow-lg">
                    <h2 class="text-center mb-4">Iniciar Sesión</h2>

                    <!-- Formulario de inicio de sesión -->
                    <form id="iniciarSesionForm" action="${pageContext.request.contextPath}/inicio/iniciar-sesion" method="POST" novalidate>
                        <div class="mb-4">
                            <label for="correo" class="form-label">Correo Electrónico</label>
                            <input type="email" id="correo" name="correo" class="form-control" required>
                            <div class="invalid-feedback">El correo electrónico es obligatorio.</div>
                        </div>

                        <div class="mb-4">
                            <label for="contrasenia" class="form-label">Contraseña</label>
                            <input type="password" id="contrasenia" name="contrasenia" class="form-control" required>
                            <div class="invalid-feedback">La contraseña es obligatoria.</div>
                        </div>

                        <button type="submit" class="btn btn-primary w-100 mt-4">Iniciar Sesión</button>
                    </form>

                    <!-- Mensajes de error -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger mt-3">${error}</div>
                    </c:if>

                    <div class="mt-3 text-center enlaces">
                        <a href="<%=request.getContextPath()%>/html/inicio/recuperarContrasenia.jsp" class="text-decoration-none d-block mb-2">¿Olvidaste tu contraseña?</a>
                        <a href="<%=request.getContextPath()%>/html/inicio/registrarse.jsp" class="text-decoration-none">¿No tienes cuenta? Regístrate</a>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
