package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.SelenideDriver;
import guru.qa.extension.BrowserExtension;
import guru.qa.page.AllPeoplePage;
import guru.qa.page.FriendsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.List;

public class FriendsTest {

  private SelenideDriver browser0;
  private SelenideDriver browser1;

  @RegisterExtension
  private BrowserExtension browserExtension = new BrowserExtension(new ArrayList<>());

  @BeforeEach
  void initDriver() {
    browser0 = new SelenideDriver(defaultConfig());
    browser1 = new SelenideDriver(defaultConfig());
    browserExtension.getBrowsers().addAll(List.of(browser0, browser1));
  }

  @Test
  void addFriendsTest() {
    loginWithUser(browser0, "duck", "12345");
    loginWithUser(browser1, "bee", "12345");

    browser0.open(AllPeoplePage.URL);
    new AllPeoplePage(browser0)
        .sendInviteToAndCheckSuccessInvitationMessage("bee");

    browser1.open(FriendsPage.URL);
    new FriendsPage(browser1)
        .checkRecievedInvite("duck");
  }

  @EnumSource(Browser.class)
  @ParameterizedTest
  void multiBrowserTest(Browser browser) {
    SelenideDriver driver = new SelenideDriver(browser.getConfig());
    browserExtension.getBrowsers().add(driver);
    // do smt
  }

  private void loginWithUser(SelenideDriver browser, String username, String password) {
    browser.open("/main");
    browser.$("a[href*='redirect']").click();
    browser.$("input[name='username']").setValue(username);
    browser.$("input[name='password']").setValue(password);
    browser.$("button[type='submit']").click();
    browser.$("section.main-content__section-stats").should(Condition.visible);
  }

  private SelenideConfig defaultConfig() {
    return new SelenideConfig()
        .baseUrl("http://frontend.niffler.dc")
        .browser("chrome")
        .browserSize("1920x1080")
        .pageLoadStrategy("eager")
        .timeout(5000L);
  }
}
