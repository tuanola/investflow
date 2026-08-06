package io.github.tuanola.investflow.service

import io.github.tuanola.investflow.domain.ImportEntity
import io.github.tuanola.investflow.dto.ImportSummaryDto
import io.github.tuanola.investflow.repository.ImportRepository
import org.springframework.stereotype.Service

@Service
class ImportServiceImpl(
    private val importRepository: ImportRepository
) : ImportService {

    override fun listImports(): List<ImportSummaryDto> =
        importRepository.findAllByOrderByUploadedAtDesc()
            .map { it.toDto() }

    override fun createUploadedImport(fileName: String): Long {
        val saved = importRepository.save(
            ImportEntity(
                fileName = fileName,
                status = "UPLOADED",
                recordCount = 0
            )
        )

        return saved.id ?: error("Saved import did not return an id")
    }

    private fun ImportEntity.toDto(): ImportSummaryDto =
        ImportSummaryDto(
            id = id ?: error("Import entity id is null"),
            fileName = fileName,
            status = status,
            uploadedAt = uploadedAt,
            recordCount = recordCount
        )
}