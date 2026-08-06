package io.github.tuanola.investflow.controller

import io.github.tuanola.investflow.dto.ImportSummaryDto
import io.github.tuanola.investflow.service.ImportService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

@WebMvcTest(ImportController::class)
class ImportControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @MockitoBean
    private lateinit var importService: ImportService

    @Test
    fun getImports_returnsEmptyArrayWhenNoImportsExist() {
        given(importService.listImports()).willReturn(emptyList())

        mockMvc.perform(get("/api/v1/imports"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"))
    }

    @Test
    fun getImports_returnsAllDtoFields() {
        val uploadedAt = Instant.parse("2026-08-06T09:00:00Z")
        val portfolioImport = ImportSummaryDto(
            id = 1L,
            fileName = "portfolio.csv",
            status = "COMPLETED",
            uploadedAt = uploadedAt,
            recordCount = 42
        )
        given(importService.listImports()).willReturn(listOf(portfolioImport))

        mockMvc.perform(get("/api/v1/imports"))
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
        val newestImport = ImportSummaryDto(
            id = 2L,
            fileName = "newest.csv",
            status = "COMPLETED",
            uploadedAt = Instant.parse("2026-08-06T10:00:00Z"),
            recordCount = 20
        )
        val oldestImport = ImportSummaryDto(
            id = 1L,
            fileName = "oldest.csv",
            status = "COMPLETED",
            uploadedAt = Instant.parse("2026-08-06T09:00:00Z"),
            recordCount = 10
        )
        given(importService.listImports())
            .willReturn(listOf(newestImport, oldestImport))

        mockMvc.perform(get("/api/v1/imports"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].fileName").value("newest.csv"))
            .andExpect(jsonPath("$[0].uploadedAt").value("2026-08-06T10:00:00Z"))
            .andExpect(jsonPath("$[1].fileName").value("oldest.csv"))
            .andExpect(jsonPath("$[1].uploadedAt").value("2026-08-06T09:00:00Z"))
    }

    @Test
    fun uploadImport_returnsCreatedResponseForCsvFile() {
        val file = csvFile(fileName = "portfolio.csv")
        given(importService.createUploadedImport("portfolio.csv")).willReturn(42L)

        mockMvc.perform(multipart("/api/v1/imports").file(file))
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.importId").value(42))

        verify(importService).createUploadedImport("portfolio.csv")
    }

    @Test
    fun uploadImport_returnsBadRequestForEmptyFile() {
        val file = MockMultipartFile("file", "portfolio.csv", "text/csv", byteArrayOf())

        mockMvc.perform(multipart("/api/v1/imports").file(file))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(importService)
    }

    @Test
    fun uploadImport_returnsBadRequestForMissingFileName() {
        val file = MockMultipartFile("file", null, "text/csv", "Date,Ticker\n".toByteArray())

        mockMvc.perform(multipart("/api/v1/imports").file(file))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(importService)
    }

    @Test
    fun uploadImport_returnsBadRequestForNonCsvFile() {
        val file = csvFile(fileName = "portfolio.txt")

        mockMvc.perform(multipart("/api/v1/imports").file(file))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(importService)
    }

    @Test
    fun uploadImport_returnsBadRequestWhenFileNameExceedsDatabaseLimit() {
        val fileName = "a".repeat(252) + ".csv"
        val file = csvFile(fileName)

        mockMvc.perform(multipart("/api/v1/imports").file(file))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(importService)
    }

    private fun csvFile(fileName: String): MockMultipartFile =
        MockMultipartFile("file", fileName, "text/csv", "Date,Ticker\n".toByteArray())
}
