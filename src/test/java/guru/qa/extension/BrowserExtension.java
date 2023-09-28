package guru.qa.extension;

import com.codeborne.selenide.SelenideDriver;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.util.List;

public class BrowserExtension implements
    TestExecutionExceptionHandler,
    AfterEachCallback {

  private final List<SelenideDriver> browsers;

  public BrowserExtension(List<SelenideDriver> browsers) {
    this.browsers = browsers;
  }

  public List<SelenideDriver> getBrowsers() {
    return browsers;
  }

  @Override
  public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
    for (SelenideDriver browser : browsers) {
      if (browser.hasWebDriverStarted()) {
        Allure.addAttachment("Screen on fail",
            new ByteArrayInputStream(
                ((TakesScreenshot) browser.getWebDriver())
                    .getScreenshotAs(OutputType.BYTES))
        );
      }
    }
    throw throwable;
  }

  @Override
  public void afterEach(ExtensionContext extensionContext) throws Exception {
    for (SelenideDriver browser : browsers) {
      if (browser.hasWebDriverStarted()) {
        browser.getWebDriver().quit();
      }
    }
  }
}
