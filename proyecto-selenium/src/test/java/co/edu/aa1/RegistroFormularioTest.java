
package co.edu.aa1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistroFormularioTest {

    private WebDriver driver;
    private WebDriverWait wait;

    // Configuración inicial antes de cada prueba.
    @BeforeEach
    public void configurarNavegador() {

        // Localizar el ejecutable de Brave.
        Path rutaBrave = localizarBrave();

        // Configurar Brave mediante ChromeDriver.
        ChromeOptions options = new ChromeOptions();

        options.setBinary(rutaBrave.toString());
        options.addArguments("--start-maximized");

        // Selenium Manager administra el controlador.
        driver = new ChromeDriver(options);

        // Configurar espera explícita de 10 segundos.
        wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(10)
        );

        // Abrir el formulario HTML local.
        String rutaFormulario = Path.of(
            "src/test/resources/formulario.html"
        ).toAbsolutePath().toUri().toString();

        driver.get(rutaFormulario);

        System.out.println("Navegador utilizado: Brave");
        System.out.println("Formulario abierto correctamente.");
    }

    // Detectar Brave en las ubicaciones habituales de Windows.
    private Path localizarBrave() {

        List<Path> posiblesRutas = new ArrayList<>();

        String programFiles = System.getenv("ProgramFiles");
        String programFilesX86 = System.getenv("ProgramFiles(x86)");
        String localAppData = System.getenv("LOCALAPPDATA");

        if (programFiles != null) {
            posiblesRutas.add(
                Path.of(programFiles,
                    "BraveSoftware",
                    "Brave-Browser",
                    "Application",
                    "brave.exe")
            );
        }

        if (programFilesX86 != null) {
            posiblesRutas.add(
                Path.of(programFilesX86,
                    "BraveSoftware",
                    "Brave-Browser",
                    "Application",
                    "brave.exe")
            );
        }

        if (localAppData != null) {
            posiblesRutas.add(
                Path.of(localAppData,
                    "BraveSoftware",
                    "Brave-Browser",
                    "Application",
                    "brave.exe")
            );
        }

        for (Path ruta : posiblesRutas) {

            if (Files.isRegularFile(ruta)) {
                System.out.println("Brave localizado en: " + ruta);
                return ruta;
            }
        }

        throw new IllegalStateException(
            "No se encontró Brave en las ubicaciones habituales. "
            + "Verifica la ruta de instalación del navegador."
        );
    }

    // Esperar hasta que aparezca el mensaje esperado.
    private String esperarMensaje(String mensajeEsperado) {

        wait.until(
            ExpectedConditions.textToBe(
                By.id("mensaje"),
                mensajeEsperado
            )
        );

        return driver.findElement(By.id("mensaje")).getText();
    }

    // Guardar una captura de pantalla del escenario.
    private void guardarCaptura(String nombre) throws IOException {

        File captura = ((TakesScreenshot) driver)
            .getScreenshotAs(OutputType.FILE);

        Path carpetaEvidencias = Path.of(
            "..",
            "evidencias",
            "formulario"
        ).toAbsolutePath().normalize();

        Files.createDirectories(carpetaEvidencias);

        Path destino = carpetaEvidencias.resolve(nombre);

        Files.copy(
            captura.toPath(),
            destino,
            StandardCopyOption.REPLACE_EXISTING
        );

        System.out.println(
            "Captura guardada correctamente: " + destino
        );
    }

    // PRUEBA 1: Verificar el comportamiento con campos vacíos.
    @Test
    public void verificarCamposVacios() throws IOException {

        driver.findElement(By.id("registrar")).click();

        String mensaje = esperarMensaje(
            "Todos los campos son obligatorios."
        );

        assertEquals(
            "Todos los campos son obligatorios.",
            mensaje
        );

        guardarCaptura("selenium_01_campos_vacios.png");

        System.out.println("Prueba 1: campos vacíos aprobada.");
    }

    // PRUEBA 2: Verificar el rechazo de un correo inválido.
    @Test
    public void verificarCorreoInvalido() throws IOException {

        driver.findElement(By.id("nombre"))
            .sendKeys("Carlos Pérez");

        driver.findElement(By.id("correo"))
            .sendKeys("correo123");

        driver.findElement(By.id("password"))
            .sendKeys("Clave123");

        driver.findElement(By.id("registrar")).click();

        String mensaje = esperarMensaje(
            "El correo electrónico no es válido."
        );

        assertEquals(
            "El correo electrónico no es válido.",
            mensaje
        );

        guardarCaptura("selenium_02_correo_invalido.png");

        System.out.println("Prueba 2: correo inválido aprobada.");
    }

    // PRUEBA 3: Verificar el registro exitoso.
    @Test
    public void verificarRegistroExitoso() throws IOException {

        driver.findElement(By.id("nombre"))
            .sendKeys("Carlos Pérez");

        driver.findElement(By.id("correo"))
            .sendKeys("carlos@example.com");

        driver.findElement(By.id("password"))
            .sendKeys("Clave123");

        driver.findElement(By.id("registrar")).click();

        String mensaje = esperarMensaje(
            "Registro exitoso."
        );

        assertEquals(
            "Registro exitoso.",
            mensaje
        );

        // Comprobar que el formulario se haya limpiado.
        WebElement campoNombre = driver.findElement(
            By.id("nombre")
        );

        assertEquals(
            "",
            campoNombre.getAttribute("value")
        );

        guardarCaptura("selenium_03_registro_exitoso.png");

        System.out.println("Prueba 3: registro exitoso aprobado.");
    }

    // Cerrar Brave después de cada prueba.
    @AfterEach
    public void cerrarNavegador() {

        if (driver != null) {
            driver.quit();
            System.out.println("Navegador Brave cerrado.");
        }
    }
}
