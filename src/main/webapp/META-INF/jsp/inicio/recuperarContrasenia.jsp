<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agrilog - recuperar contraseña</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/nav.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/mainRegistrarse.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}favicon.ico" />
</head>
<body>
    <nav class="navbar">
        <div class="logo-contenedor align-items-center justify-content-center">
            <img src="${pageContext.request.contextPath}/img/logo/Logo-sin-fondo.png" alt="Logo" class="logo" />
            <span class="logo-text">Agrilog</span>
        </div>
        <div class="buttons-container">
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-transparent btnNav">Inicio</a>
            <a href="${pageContext.request.contextPath}/inicio/iniciarSesion.jsp" class="btn btn-transparent btnNav">Iniciar Sesión</a>
            <a href="${pageContext.request.contextPath}/inicio/registrarse.jsp" class="btn btn-transparent btnNav">Registrarse</a>
        </div>
    </nav>
    <main>
        <p>Formulario para recuperar contraseña</p>
    </main>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/inicio/registrarse.js"></script>
</body>
</html>
