package packagename.cucumber

import io.cucumber.core.options.Constants.FILTER_TAGS_PROPERTY_NAME
import io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME
import org.junit.platform.suite.api.*

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/example.feature")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "packagename.cucumber")
@ConfigurationParameters(ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "packagename.cucumber"), ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@Example"))
class RunCucumberExampleTest
