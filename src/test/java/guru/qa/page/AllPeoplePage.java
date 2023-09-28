package guru.qa.page;

import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;

public class AllPeoplePage {

  public static final String URL = "/people";
  private final SelenideDriver browser;
  private final SelenideElement table;

  public AllPeoplePage(SelenideDriver browser) {
    this.browser = browser;
    this.table = this.browser.$("table tbody");
  }

  public AllPeoplePage sendInviteToAndCheckSuccessInvitationMessage(String username) {
    SelenideElement row = table.$$("tr").find(text(username));
    row.$("[data-tooltip-id='add-friend'] button").click();
    row.shouldHave(text("Pending invitation"));
    return this;
  }

  public AllPeoplePage checkRecievedInvite(String username) {
    SelenideElement row = table.$$("tr").find(text(username));
    row.shouldHave(text("Pending invitation"));
    return this;
  }
}
