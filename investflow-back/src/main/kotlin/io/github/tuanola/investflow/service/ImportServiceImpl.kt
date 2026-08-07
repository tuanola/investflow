package io.github.tuanola.investflow.service

import io.github.tuanola.investflow.domain.ImportEntity
import io.github.tuanola.investflow.dto.ImportSummaryDto
import io.github.tuanola.investflow.repository.ImportRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Service

@Service
class ImportServiceImpl(
    private val importRepository: ImportRepository,
    private val importLifecycle: ImportLifecycle,
    private val csvParserProvider: ObjectProvider<CsvParser>
) : ImportService {

    override fun listImports(): List<ImportSummaryDto> =
        importRepository.findAllByOrderByUploadedAtDesc()
            .map { it.toDto() }

    override fun createImport(csv: UploadedCsv): Long {
        val importId = importLifecycle.createUploaded(csv.fileName)
        val csvParser = csvParserProvider.ifAvailable ?: return importId

        importLifecycle.markProcessing(importId)

        try {
            val parsedCsv = csvParser.parse(csv)
            importLifecycle.markCompleted(importId, parsedCsv.recordCount)
        } catch (error: Exception) {
            logger.warn("CSV parsing failed for import {}", importId, error)
            importLifecycle.markFailed(importId)
        }

        return importId
    }

    private fun ImportEntity.toDto(): ImportSummaryDto =
        ImportSummaryDto(
            id = id ?: error("Import entity id is null"),
            fileName = fileName,
            status = status,
            uploadedAt = uploadedAt,
            recordCount = recordCount
        )

    private companion object {
        val logger = LoggerFactory.getLogger(ImportServiceImpl::class.java)
    }
}
