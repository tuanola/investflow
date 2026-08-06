package io.github.tuanola.investflow

import io.github.tuanola.investflow.domain.ImportEntity
import io.github.tuanola.investflow.repository.ImportRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import java.time.Instant
import kotlin.test.assertEquals

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class ImportIntegrationTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val importRepository: ImportRepository,
    @Autowired private val jdbcTemplate: JdbcTemplate
) {

    @Test
    fun applicationRunsMigrationAndServesImportsFromPostgres() {
        val appliedMigrationCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM flyway_schema_history WHERE version = '1' AND success",
            Int::class.java
        )
        assertEquals(1, appliedMigrationCount)

        importRepository.saveAndFlush(
            ImportEntity(
                fileName = "portfolio.csv",
                status = "COMPLETED",
                uploadedAt = Instant.parse("2026-08-06T09:00:00Z"),
                recordCount = 42
            )
        )

        mockMvc.perform(get("/imports"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].fileName").value("portfolio.csv"))
            .andExpect(jsonPath("$[0].status").value("COMPLETED"))
            .andExpect(jsonPath("$[0].uploadedAt").value("2026-08-06T09:00:00Z"))
            .andExpect(jsonPath("$[0].recordCount").value(42))
    }

    @Test
    fun openApiDocumentationDescribesImportsEndpoint() {
        mockMvc.perform(get("/v3/api-docs"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.info.title").value("InvestFlow API"))
            .andExpect(jsonPath("$.info.version").value("v1"))
            .andExpect(jsonPath("$.paths['/imports'].get").exists())
            .andExpect(jsonPath("$.components.schemas.ImportSummaryDto").exists())
    }

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer("postgres:16")
    }
}
