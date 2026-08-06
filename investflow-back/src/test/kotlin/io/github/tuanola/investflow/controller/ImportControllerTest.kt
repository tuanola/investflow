package io.github.tuanola.investflow.controller

import io.github.tuanola.investflow.domain.ImportEntity
import io.github.tuanola.investflow.repository.ImportRepository
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

@WebMvcTest(ImportController::class)
class ImportControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @MockitoBean
    private lateinit var importRepository: ImportRepository

    @Test
    fun getImports_returnsEmptyArrayWhenNoImportsExist() {
        given(importRepository.findAllByOrderByUploadedAtDesc()).willReturn(emptyList())

        mockMvc.perform(get("/imports"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"))
    }

    @Test
    fun getImports_returnsAllDtoFields() {
        val uploadedAt = Instant.parse("2026-08-06T09:00:00Z")
        val importEntity = ImportEntity(
            id = 1L,
            fileName = "portfolio.csv",
            status = "COMPLETED",
            uploadedAt = uploadedAt,
            recordCount = 42
        )
        given(importRepository.findAllByOrderByUploadedAtDesc()).willReturn(listOf(importEntity))

        mockMvc.perform(get("/imports"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].fileName").value("portfolio.csv"))
            .andExpect(jsonPath("$[0].status").value("COMPLETED"))
            .andExpect(jsonPath("$[0].uploadedAt").value("2026-08-06T09:00:00Z"))
            .andExpect(jsonPath("$[0].recordCount").value(42))
    }

    @Test
    fun getImports_returnsNewestImportsFirst() {
        val newestImport = ImportEntity(
            id = 2L,
            fileName = "newest.csv",
            status = "COMPLETED",
            uploadedAt = Instant.parse("2026-08-06T10:00:00Z"),
            recordCount = 20
        )
        val oldestImport = ImportEntity(
            id = 1L,
            fileName = "oldest.csv",
            status = "COMPLETED",
            uploadedAt = Instant.parse("2026-08-06T09:00:00Z"),
            recordCount = 10
        )
        given(importRepository.findAllByOrderByUploadedAtDesc())
            .willReturn(listOf(newestImport, oldestImport))

        mockMvc.perform(get("/imports"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].fileName").value("newest.csv"))
            .andExpect(jsonPath("$[0].uploadedAt").value("2026-08-06T10:00:00Z"))
            .andExpect(jsonPath("$[1].fileName").value("oldest.csv"))
            .andExpect(jsonPath("$[1].uploadedAt").value("2026-08-06T09:00:00Z"))
    }
}
