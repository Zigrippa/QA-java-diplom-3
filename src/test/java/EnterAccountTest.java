import api_models.User;
import api_models.UserClient;
import config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.RegistrationPage;
import tools.WebDriverFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnterAccountTest {

    private WebDriver driver;
    public UserClient userClient;
    public User user;
    public String accessToken;


    @Before
    public void setUp() {
        user = new User().generateUser();
        userClient = new UserClient();
        RestAssured.baseURI = ApiConfig.BASE_URL;

    }

    @After
    public void tearDown() {
        driver.quit();
        if (accessToken != null) userClient.delete(accessTokenExtraction(user));
    }

    public String accessTokenExtraction(User user) {
        ValidatableResponse response = userClient.login(user);
        return response.extract().path("accessToken");
    }

    @Test
    public void enterThroughEnterToAccountButton() {
        driver = WebDriverFactory.get("chrome", "main");
        userClient.create(user);
        boolean isProfileHeaderVisible = new MainPage(driver)
                .clickToAccountButton()
                .inputEmail(user.getEmail())
                .inputPassword(user.getPassword())
                .clickEnterButton()
                .clickPersonalAreaButtonWhileAlreadyLogin()
                .isProfileHeaderVisible();
        assertTrue(isProfileHeaderVisible);
    }

    @Test
    public void enterThroughPersonalAreaButton() {
        driver = WebDriverFactory.get("chrome", "main");
        userClient.create(user);
        boolean isProfileHeaderVisible = new MainPage(driver)
                .clickPersonalAreaButtonWhileIsNotLogin()
                .inputEmail(user.getEmail())
                .inputPassword(user.getPassword())
                .clickEnterButton()
                .clickStellarisBurgerLogo()
                .clickPersonalAreaButtonWhileAlreadyLogin()
                .isProfileHeaderVisible();
        assertTrue(isProfileHeaderVisible);
    }

    //через регистрацию надо отдельно?
    @Test
    public void enterThroughToRegistrationButton() {
        driver = WebDriverFactory.get("chrome", "login");
        userClient.create(user);
        boolean isProfileHeaderVisible = new MainPage(driver)
                .clickPersonalAreaButtonWhileIsNotLogin()
                .inputEmail(user.getEmail())
                .inputPassword(user.getPassword())
                .clickEnterButton()
                .clickStellarisBurgerLogo()
                .clickPersonalAreaButtonWhileAlreadyLogin()
                .isProfileHeaderVisible();
        assertTrue(isProfileHeaderVisible);
    }


    @Test
    public void enterThroughPasswordRecoverButton() {
        driver = WebDriverFactory.get("chrome", "login");
        userClient.create(user);
        boolean isProfileHeaderVisible = new MainPage(driver)
                .clickPersonalAreaButtonWhileIsNotLogin()
                .inputEmail(user.getEmail())
                .inputPassword(user.getPassword())
                .clickEnterButton()
                .clickStellarisBurgerLogo()
                .clickPersonalAreaButtonWhileAlreadyLogin()
                .isProfileHeaderVisible();
        assertTrue(isProfileHeaderVisible);
    }


}
