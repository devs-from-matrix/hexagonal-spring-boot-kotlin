package packagename.cucumber

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.test.context.ContextConfiguration
import packagename.ExampleE2EApplication

@RunWith(Cucumber::class)
@CucumberOptions(features = ["classpath:features/example.feature"],
    strict = true,
    plugin = ["json:target/cucumber/example.json", "json:target/cucumber/example.xml"],
    tags = "@Example",
    glue = ["classpath:packagename.cucumber"])
class RunCucumberExampleTest
