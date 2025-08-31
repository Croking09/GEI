package es.udc.paproject.e2etests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppTest {

    WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testLogin() {
        // Hacer login:
        login("testuser1", "pa2425");

        // Localizar desplegable con opciones de usuario:
        WebElement userForm = driver.findElement(By.id("userForm"));

        // Comprobar que es el usuario correcto:
        WebElement userNameLogin = driver.findElement(By.id("userNameLogin"));
        assertEquals(" testuser1", userNameLogin.getText());
    }

    // TT-1: Ver información detallada de un producto
    @Test
    public void testSeeProductDetails() {
        // Hacer login:
        login("testuser1", "pa2425");

        // Rellenar campos de búsqueda:
        WebElement categorySelector = driver.findElement(By.id("categoryId"));
        Select catSel = new Select(categorySelector);
        catSel.selectByValue("3");

        WebElement textInput = driver.findElement(By.id("keywords"));
        textInput.sendKeys("Test Product 1");

        // Pulsar botón de búsqueda:
        WebElement searchButton = driver.findElement(By.id("searchProductsButton"));
        searchButton.click();

        // Comprobar resultado y localizar el producto:
        WebElement productCategoryCell = driver.findElement(By.cssSelector("table tbody tr:first-child td:first-child"));
        String productCategory = productCategoryCell.getText();
        assertEquals("Test", productCategory);

        WebElement productNameCell = driver.findElement(By.cssSelector("table tbody tr:first-child td:nth-child(2)"));
        String productName = productNameCell.getText();
        assertEquals("Test Product 1", productName);

        // Hacer click en el enlace:
        WebElement productLink = productNameCell.findElement(By.tagName("a"));
        productLink.click();

        // Comprobar nombre del producto:
        WebElement productDetailsName = driver.findElement(By.id("productDetailsName"));
        assertEquals("Test Product 1", productDetailsName.getText());

        // Comprobar el resto de campos del producto:
        WebElement productDetailsCategory = driver.findElement(By.id("productDetailsCategory"));
        WebElement productDetailsDescription = driver.findElement(By.id("productDetailsDescription"));
        WebElement productDetailsPublisher = driver.findElement(By.id("productDetailsPublisher"));
        WebElement productDetailsPublishingDate = driver.findElement(By.id("productDetailsPublishingDate"));
        WebElement productDetailsOutPrice = driver.findElement(By.id("productDetailsOutPrice"));
        WebElement productDetailsDeliveryInformation = driver.findElement(By.id("productDetailsDeliveryInformation"));
        WebElement productDetailsCurrentPrice = driver.findElement(By.id("productDetailsCurrentPrice"));
        WebElement productDetailsBidTime = driver.findElement(By.id("productDetailsBidTime"));

        // Comprobar formulario de puja:
        WebElement bidFormTitle = driver.findElement(By.id("bidFormTitle"));
        WebElement bidForm = driver.findElement(By.id("productDetailsBidTime"));
    }

    // TT-2: Anunciar (insertar) un producto
    @Test
    public void testInsertProduct() {
        login("testuser2", "pa2425");

        WebElement insertProductButton = driver.findElement(By.id("insertProductLink"));
        insertProductButton.click();

        WebElement nameField = driver.findElement(By.id("name"));
        nameField.sendKeys("Test product 2");

        WebElement descriptionField = driver.findElement(By.id("description"));
        descriptionField.sendKeys("Descripcion de prueba");

        WebElement durationField = driver.findElement(By.id("duration"));
        String minutes = String.valueOf(ChronoUnit.MINUTES.between(
                LocalDateTime.now(),
                LocalDateTime.of(2026, 12, 31, 23, 59))
        );
        durationField.sendKeys(minutes);

        WebElement outPriceField = driver.findElement(By.id("outPrice"));
        outPriceField.sendKeys("10");

        WebElement deliveryInfoField = driver.findElement(By.id("informacionEnvio"));
        deliveryInfoField.sendKeys("Informacion de envio de prueba");

        Select categoryField = new Select(driver.findElement(By.id("category")));
        categoryField.selectByVisibleText("Test");

        WebElement submitButton = driver.findElement(By.id("submitFormButton"));
        submitButton.click();

        WebElement insertedMessage = driver.findElement(By.id("insertionMsg"));
        assertTrue(insertedMessage.isDisplayed());

        WebElement myProductsLink = driver.findElement(By.id("myProductsLink"));
        myProductsLink.click();

        WebElement productNameCell = driver.findElement(By.cssSelector("table tbody tr:first-child td:first-child"));
        String productName = productNameCell.getText();
        assertEquals("Test product 2", productName);
    }

    private WebElement waitUntilVisible(By locator, int timeoutSecs) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // TT-3: Pujar por un producto
    @Test
    public void testBidByProduct() {
        // 1. Autenticar al usuario "testuser1"
        login("testuser1", "pa2425");

        // 1.a) Esperar hasta que el nombre de usuario sea visible (o cualquier otro indicador de que ya estamos logueados)
        WebElement userIndicator = waitUntilVisible(By.id("userNameLogin"), 10);

        // 2. Ahora que ya estamos seguros de que la sesión se ha iniciado,
        //    accedemos a la URL de detalles de "Test Product 1"
        driver.get("http://localhost:5173/catalog/product-details/5");

        // 3. Especificar una cantidad válida en el formulario de puja
        WebElement bidValueField = waitUntilVisible(By.id("bidValue"), 10);
        bidValueField.clear();
        bidValueField.sendKeys("45");

        // 4. Hacer clic en el botón de pujar
        WebElement bidButton = waitUntilVisible(By.id("bidButton"), 10);
        bidButton.click();

        // 5. Comprobar que aparece el mensaje de “¡Puja realizada con éxito!”
        WebElement bidSuccessContainer = waitUntilVisible(By.id("bidSuccess"), 10);
        String textoCompletoSuccess = bidSuccessContainer.getText();
        assertTrue(textoCompletoSuccess.contains("¡Puja realizada con éxito!"),
                "Debe mostrarse el mensaje ‘¡Puja realizada con éxito!’");

        // 5.1. Comprobar que la puja va ganando
        WebElement bidStateContainer = waitUntilVisible(By.id("bidState"), 10);
        String textoCompletoState = bidStateContainer.getText();
        assertTrue(textoCompletoState.contains("GANANDO"),
                "Debe mostrarse el mensaje GANANDO");


        // 6. Hacer clic en “Mis pujas”
        WebElement myBidsLink = waitUntilVisible(By.id("myBidsLink"), 10);
        myBidsLink.click();

        // 7. Verificar que el primer producto en “Mis pujas” es “Test Product 1”
        WebElement firstBidProductCell = waitUntilVisible(
                By.cssSelector("table tbody tr:first-child td:first-child"), 10
        );

        String firstBidProductName = firstBidProductCell.getText();
        assertEquals("Test Product 1", firstBidProductName,
                "El primer producto de la lista de pujas debe ser el que acabamos de pujar");
    }


    @AfterEach
    public void teardown() {
        driver.quit();
    }

    private void login(String userName, String password) {
        // Acceder a la URL base:
        driver.get("http://localhost:5173");

        // Localizar enlace al formulario de login:
        WebElement loginLink = driver.findElement(By.id("loginLink"));

        // Pulsar el enlace:
        loginLink.click();

        // Localizar campos y el botón del formulario:
        WebElement usernameField = driver.findElement(By.id("userName"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));

        // Rellenar los campos del formulario:
        usernameField.sendKeys(userName);
        passwordField.sendKeys(password);

        // Pulsar el botón:
        loginButton.click();
    }

}