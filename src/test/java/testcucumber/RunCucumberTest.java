package testcucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import javax.security.auth.login.Configuration;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        features = {"classpath:features/"})

public class RunCucumberTest {
    @BeforeClass
    static public void setupTimeout() throws InterruptedException {
       Thread.sleep(10000);
    }
}