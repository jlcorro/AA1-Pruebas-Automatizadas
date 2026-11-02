
# AA1 - Automatización de pruebas de software

**Estudiante:** Jairo Luis Corro Noriega

## Descripción

Proyecto académico de automatización de pruebas funcionales para un formulario web y un servicio REST.

Se utilizan Selenium WebDriver, Java, JUnit 5, Maven y Postman.

## Tecnologías utilizadas

- Java 25
- Maven 3.10.0
- Selenium WebDriver
- JUnit 5
- Brave Browser
- Postman
- JSONPlaceholder

## 1. Pruebas del formulario web

Se desarrolló un formulario HTML de registro de usuarios y se automatizaron tres escenarios:

1. Validación de campos vacíos.
2. Validación de correo electrónico incorrecto.
3. Registro exitoso.

### Ejecución

Requisitos:
- JDK 17 o superior.
- Maven instalado.
- Brave Browser instalado.
- Conexión a Internet para gestionar el controlador del navegador.

Abrir PowerShell dentro de la carpeta `proyecto-selenium` y ejecutar:

```powershell
mvn clean test
```

### Resultado obtenido

- Pruebas ejecutadas: 3.
- Aprobadas: 3.
- Fallidas: 0.
- Errores: 0.

## 2. Pruebas de API REST

Se utilizó JSONPlaceholder para realizar pruebas de servicios REST.

Solicitudes implementadas:

- GET: consulta de una publicación.
- POST: creación simulada de una publicación.

### Ejecución

1. Abrir Postman.
2. Importar el archivo de la carpeta `pruebas-api`.
3. Ejecutar la colección completa.

### Validaciones realizadas

- Código HTTP 200 para GET.
- Código HTTP 201 para POST.
- Validación del formato JSON.
- Validación de los campos y datos de respuesta.

### Resultado obtenido

- Validaciones ejecutadas: 6.
- Aprobadas: 6.
- Fallidas: 0.

**Nota:** JSONPlaceholder simula la creación mediante POST y no almacena permanentemente los registros.

## 3. Evidencias

Las capturas de las ejecuciones se encuentran en la carpeta `evidencias`.

## 4. Documentación

El informe técnico de la actividad se encuentra en la carpeta `documentacion`.

Este proyecto corresponde a la actividad AA1 de la Unidad 2: Automatización de pruebas.
