package packagename.cucumber

import io.cucumber.junit.platform.engine.Constants.*
import org.junit.platform.suite.api.*

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameters(
    ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "packagename.cucumber"), // this is the part where the stepdef file is
    ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@Example"),
    ConfigurationParameter(key = JUNIT_PLATFORM_NAMING_STRATEGY_PROPERTY_NAME, value = "long"),
    ConfigurationParameter(key = PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true"),
    ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "json:target/cucumber/cucumber.json")
)
class RunCucumberExampleTest
