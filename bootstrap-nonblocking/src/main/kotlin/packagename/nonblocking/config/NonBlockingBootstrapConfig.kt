package packagename.nonblocking.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import packagename.domain.ExampleDomain
import packagename.domain.port.ObtainExample
import packagename.domain.port.RequestExample
import packagename.repository.config.R2dbcAdapterConfig

@Configuration
@Import(R2dbcAdapterConfig::class)
class NonBlockingBootstrapConfig {

  @Bean("non-blocking-request-example")
  fun getRequestExample(obtainExample: ObtainExample): RequestExample {
    return ExampleDomain(obtainExample)
  }
}
