package io.github.tuanola.investflow.controller

import io.github.tuanola.investflow.domain.ImportEntity
import io.github.tuanola.investflow.dto.ImportSummaryDto
import io.github.tuanola.investflow.repository.ImportRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Imports", description = "Portfolio import operations")
@RestController
class ImportController(
    private val importRepository: ImportRepository
) {
    @Operation(
        summary = "List imports",
        description = "Returns all imports ordered from newest to oldest"
    )
    @ApiResponse(responseCode = "200", description = "Imports returned successfully")
    @GetMapping("/imports")
    fun getImports(): List<ImportSummaryDto> =
        importRepository.findAllByOrderByUploadedAtDesc()
            .map { it.toDto() }

    private fun ImportEntity.toDto(): ImportSummaryDto =
        ImportSummaryDto(
            id = id!!,
            fileName = fileName,
            status = status,
            uploadedAt = uploadedAt,
            recordCount = recordCount
        )
}
