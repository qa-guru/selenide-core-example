package guru.qa.page;

import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class FriendsPage {

  public static final String URL = "/friends";

  private final SelenideDriver browser;
  private final SelenideElement table;

  public FriendsPage(SelenideDriver browser) {
    this.browser = browser;
    this.table = this.browser.$("table tbody");
  }

  public FriendsPage checkTableContains(String username) {
    table.$$("tr").find(text(username))
        .should(text("You are friends"));
    return this;
  }

  public FriendsPage checkRecievedInvite(String username) {
    SelenideElement row = table.$$("tr").find(text(username));
    row.$("[data-tooltip-id='submit-invitation'] button").should(visible);
    row.$("[data-tooltip-id='decline-invitation']").should(visible);
    return this;
  }
}
