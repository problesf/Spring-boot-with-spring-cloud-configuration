# 🔒 SPRING-BOOT-WITH-SPRING-CLOUD-CONFIGURATION

SPRING-BOOT-WITH-SPRING-CLOUD-CONFIGURATION es un proyecto que implementa **Spring Cloud Config 4.2** y una API con autenticación basada en **Keycloak OAuth2** y configuración centralizada con **Spring Cloud Config 4.2**.

---

## 🚀 Características

✅ **Autenticación con Keycloak** utilizando `client_credentials`.
✅ **Integración con Spring Cloud Config** para configuración centralizada.
✅ **Microservicios seguros** con JWT y OAuth2.
✅ **WebClient para consumo de API segura** con tokens renovables.
✅ **Servidor de Configuración basado en Git** para administración flexible.

---

## 📌 Requisitos

Antes de ejecutar el proyecto, asegúrate de tener instalados:

- **Java 17** o superior.
- **Maven 3.8+**.
- **Docker y Docker Compose** (opcional para Keycloak).
- **Git** (para la configuración de Spring Cloud Config).

---

## 📂 Estructura del Proyecto

```
|-- spring-boot/       # Microservicio principal
|   |-- src/main/java/com/securityChat/boot/  # Código fuente
|   |-- src/main/resources/        # Configuraciones de la aplicación
|   |-- pom.xml                     # Dependencias de Maven
|-- spring-cloud-config/            # Servidor de configuración central
|   |-- src/main/java/com/secureChat/springCloudConfig/
|   |-- src/main/resources/application.yml  # Configuración del Config Server
|   |-- pom.xml                     # Dependencias de Maven
|-- config-repo/                    # Repositorio de configuraciones externas
```

---

## 🚀 Instalación y Configuración

### 1️⃣ Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/spring-boot-secure-chat.git
cd spring-boot-secure-chat
```

### 2️⃣ Configurar el Servidor de Configuración

Edita el archivo `bootstrap.yml` en **Spring Boot** para apuntar al servidor de configuración:

```yaml
spring:
  cloud:
    config:
      uri: http://localhost:8888
      name: secure-chat
```

Ejecuta el servidor de configuración:

```bash
cd spring-cloud-config
mvn spring-boot:run
```

### 3️⃣ Configurar Keycloak

Levanta Keycloak con Docker:

```bash
docker run -p 8080:8080 --name keycloak -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev
```

Crea un **cliente** en Keycloak con los siguientes datos:

- `client_id: secure-chat-client`
- `client_secret: tu-secret`
- `grant_type: client_credentials`

Asegúrate de modificar `application.yml` con los valores correctos.

### 4️⃣ Ejecutar el Microservicio

```bash
cd spring-boot-secure-chat
mvn spring-boot:run
```

---

## 📡 Endpoints Principales

### 🔑 Autenticación (Obtener Token)

```http
POST /realms/MiRealm/protocol/openid-connect/token
```

**Headers:**
```yaml
Content-Type: application/x-www-form-urlencoded
```

**Body:**
```yaml
client_id: secure-chat-client
client_secret: tu-secret
grant_type: client_credentials
```

### 📬 Enviar Mensaje Seguro
```http
POST /api/messages
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "to": "user@example.com",
  "message": "Hello, this is a secure message!"
}
```

### 📥 Obtener Mensajes
```http
GET /api/messages
Authorization: Bearer {TOKEN}
```

---

## 🛠️ Tecnologías Utilizadas

- **Spring Boot 3.4.3**
- **Spring Security OAuth2**
- **Spring Cloud Config 4.2**
- **Keycloak**
- **WebClient (Spring WebFlux)**
- **Docker**
- **Maven**

---

## 📜 Licencia

Este proyecto está bajo la licencia **MIT**. Puedes usarlo, modificarlo y distribuirlo libremente. 🎉

---

## ✨ Contribuir

Si deseas contribuir, haz un **fork** del repositorio, crea una rama y envía un **pull request** con tus mejoras. ¡Toda ayuda es bienvenida! 🚀
