package io.github.tuanola.investflow.controller

import io.github.tuanola.investflow.domain.ImportEntity
import io.github.tuanola.investflow.dto.ImportSummaryDto
import io.github.tuanola.investflow.repository.ImportRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ImportController(
    private val importRepository: ImportRepository
) {
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
