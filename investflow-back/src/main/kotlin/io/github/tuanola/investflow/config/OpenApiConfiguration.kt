package io.github.tuanola.investflow.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {

    @Bean
    fun investFlowOpenApi(): OpenAPI =
        OpenAPI().info(
            Info()
                .title("InvestFlow API")
                .description("API for importing and managing portfolio data")
                .version("v1")
        )
}
