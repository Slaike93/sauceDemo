package automation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        plugin={"pretty", "html:target/cucumber-reports.html"}
)
public class RunTests {
    @Test
    public void tests(){}
}
