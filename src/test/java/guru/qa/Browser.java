package guru.qa;

import com.codeborne.selenide.SelenideConfig;

public enum Browser {

  CHROME(new SelenideConfig()
      .baseUrl("http://frontend.niffler.dc")
      .browser("chrome")
      .browserSize("1920x1080")
      .pageLoadStrategy("eager")
      .timeout(5000L)),
  FIREFOX(new SelenideConfig()
      .baseUrl("http://frontend.niffler.dc")
      .browser("firefox")
      .browserSize("1920x1080")
      .pageLoadStrategy("eager")
      .timeout(5000L));

  private final SelenideConfig config;

  Browser(SelenideConfig config) {
    this.config = config;
  }

  public SelenideConfig getConfig() {
    return config;
  }
}
