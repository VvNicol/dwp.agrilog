<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Panel de Usuario</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/estilos/nav.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/estilos/panel.css">
  <link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/favicon.ico" />
</head>
<body>
  <nav class="navbar">
    <div class="logo-contenedor align-items-center justify-content-center">
      <img src="<%=request.getContextPath()%>/img/logo/Logo-sin-fondo.png" alt="Logo" class="logo" />
      <span class="logo-text">Agrilog</span>
    </div>
    <div class="buttons-container">
      <a href="#" class="btn btn-activo btnNav">Panel</a>
      <a href="${pageContext.request.contextPath}/cerrar-sesion" class="btn btn-danger btn-sm ms-3">Cerrar Sesión</a>
    </div>
  </nav>

  <div class="container mt-5">
    <div class="row">
      <!-- Columna izquierda -->
      <div class="col-md-4 d-flex flex-column gap-4">
        <div class="card shadow text-center" style="height: 220px;">
          <div class="card-body d-flex flex-column justify-content-center">
            <h5 class="card-title">Total de plantas cultivadas:</h5>
            <p class="card-text fs-4">${totalPlantas}</p>
            <i class="bi bi-flower3" style="font-size: 2rem;"></i>
          </div>
        </div>
 <!-- Tarjeta parcelas -->
        <div class="card shadow text-center" style="height: 220px;">
          <div class="card-body d-flex flex-column justify-content-center">
            <h5 class="card-title">Parcelas registradas:</h5>
            <c:choose>
            <c:when test="${empty parcelas}">
                <p class="text-muted">Ninguna aún</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="parcela" items="${parcelas}" varStatus="status">
                    <c:if test="${status.index < 3}">
                        <div class="mb-1 fs-5">${parcela.nombre}</div>
                    </c:if>
                </c:forEach>

                <!-- Mostrar solo si hay una o más -->
                <c:if test="${not empty parcelas}">
                    <button class="btn btn-sm mt-3"
                            style="background-color: #A9746E; color: white; border: none; width: 50%; margin: 0 auto;">
                        Ver todas
                    </button>
                </c:if>
            </c:otherwise>
        </c:choose>
          </div>
        </div>
      </div>

      <!-- Columna derecha -->
      <div class="col-md-8">
        <c:choose>
          <c:when test="${empty cultivos}">
            <div class="card shadow text-center" style="height: 460px;">
              <div class="card-body d-flex flex-column justify-content-center">
                <h4 class="card-title mb-4">Registra tu primer cultivo</h4>
                <a href="${pageContext.request.contextPath}/cultivo/formulario"
                   class="btn btn-success px-4 py-2 fs-5">
                  <i class="bi bi-plus-circle"></i> Crear
                </a>
              </div>
            </div>
          </c:when>
          <c:otherwise>
            <div class="card shadow">
              <div class="card-header d-flex justify-content-between align-items-center">
                <h5 class="mb-0">Tus Cultivos</h5>
                <a href="${pageContext.request.contextPath}/cultivo/formulario"
                   class="btn fs-4 bi bi-plus-circle icono-agregar" title="Agregar cultivo"></a>
              </div>
              <div class="card-body">
                <div class="accordion" id="cultivosAccordion">
                  <c:forEach var="cultivo" items="${cultivos}">
                    <div class="accordion-item mb-3">
                      <h2 class="accordion-header" id="heading${cultivo.cultivoId}">
                        <button class="accordion-button collapsed cultivo-header"
                                type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapse${cultivo.cultivoId}"
                                aria-expanded="false" aria-controls="collapse${cultivo.cultivoId}">
                          ${cultivo.nombre}
                        </button>
                      </h2>
                      <div id="collapse${cultivo.cultivoId}" class="accordion-collapse collapse"
                           aria-labelledby="heading${cultivo.cultivoId}" data-bs-parent="#cultivosAccordion">
                        <div class="accordion-body">
                          <p><strong>Parcela:</strong> ${cultivo.parcelaId.nombre}</p>
                          <p><strong>Cantidad:</strong> ${cultivo.cantidad}</p>
                          <p><strong>Fecha de siembra:</strong> ${cultivo.fechaSiembra}</p>
                          <p><strong>Descripción:</strong>
                            <c:choose>
                              <c:when test="${fn:length(cultivo.descripcion) > 100}">
                                ${fn:substring(cultivo.descripcion, 0, 100)}...
                                <a href="#" class="text-primary" data-bs-toggle="tooltip" title="${cultivo.descripcion}">ver más</a>
                              </c:when>
                              <c:otherwise>${cultivo.descripcion}</c:otherwise>
                            </c:choose>
                          </p>
                          <div class="d-flex justify-content-end gap-2">
<%--                             <a href="${pageContext.request.contextPath}/cultivo/editar/${cultivo.cultivoId}" --%>
							<a href="#"
                               class="btn btn-outline-primary btn-sm">
                              <i class="bi bi-pencil-fill icono-editar"></i>
                            </a>
                            <button class="btn btn-outline-danger btn-sm" data-bs-toggle="modal"
                                    data-bs-target="#modalEliminar${cultivo.cultivoId}">
                              <i class="bi bi-trash3-fill icono-eliminar"></i>
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>

                    <!-- Modal eliminar -->
                    <div class="modal fade" id="modalEliminar${cultivo.cultivoId}" tabindex="-1"
                         aria-labelledby="modalLabel${cultivo.cultivoId}" aria-hidden="true">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <form action="${pageContext.request.contextPath}/cultivo/eliminar/${cultivo.cultivoId}"
                                method="post" 
                                onsubmit="return limpiarEspacios(this);" >                                                                                         
                            <div class="modal-header">
                              <h5 class="modal-title" id="modalLabel${cultivo.cultivoId}">Confirmar eliminación</h5>
                              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                            </div>
                            <div class="modal-body">
                              <p>Para confirmar, escribe el nombre de la planta: <strong>${cultivo.nombre}</strong></p>
                              <input type="text" class="form-control" name="confirmacionNombre"
                                     placeholder="Nombre de la planta" required>
                            </div>
                            <div class="modal-footer">
                              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                              <button type="submit" class="btn btn-danger">Eliminar</button>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                  </c:forEach>
                </div>
              </div>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/usuario/usuarioPanel.js"></script>
</body>
</html>
