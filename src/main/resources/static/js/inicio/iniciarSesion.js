document.getElementById("iniciarSesionForm").addEventListener("submit", function (event) {
        event.preventDefault(); // Evita el envío normal del formulario

        let formData = new FormData(this);
        fetch(this.action, {
            method: "POST",
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if (data.token) {
                localStorage.setItem("token", data.token); // 🔥 Guarda el token en localStorage
                localStorage.setItem("usuario", data.usuario);
                localStorage.setItem("rol", data.rol);

                console.log("✅ Token guardado en localStorage:", data.token);
                console.log("✅ Usuario:", data.usuario);
                console.log("✅ Rol:", data.rol);

                // Redirigir según el rol
                if (data.rol === "ADMIN") {
                    window.location.href = "/proyectoAgricola/admin/panel";
                } else {
                    window.location.href = "/proyectoAgricola/usuario/panel";
                }
            } else {
                console.error("❌ Error en inicio de sesión:", data.error);
                document.getElementById("mensajeError").innerText = data.error;
            }
        })
        .catch(error => console.error("❌ Error en la solicitud:", error));
    });