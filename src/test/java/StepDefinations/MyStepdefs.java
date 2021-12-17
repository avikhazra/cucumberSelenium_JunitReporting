package StepDefinations;

import PageObjects.CommonPage;
import io.cucumber.java.en.Given;

public class MyStepdefs {

    @Given("Step from {string} in {string} feature file")
    public void step_from_in_feature_file(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
System.out.println(string+"  "+string2);
    }
}
