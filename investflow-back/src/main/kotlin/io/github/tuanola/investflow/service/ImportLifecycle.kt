package io.github.tuanola.investflow.service

import io.github.tuanola.investflow.domain.ImportEntity
import io.github.tuanola.investflow.repository.ImportRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ImportLifecycle(
    private val importRepository: ImportRepository
) {

    @Transactional
    fun createUploaded(fileName: String): Long {
        val saved = importRepository.save(
            ImportEntity(
                fileName = fileName,
                status = "UPLOADED",
                recordCount = 0
            )
        )

        return saved.id ?: error("Saved import did not return an id")
    }

    @Transactional
    fun markProcessing(importId: Long) {
        update(importId) { status = "PROCESSING" }
    }

    @Transactional
    fun markCompleted(importId: Long, recordCount: Int) {
        require(recordCount >= 0) { "Record count must not be negative" }
        update(importId) {
            status = "COMPLETED"
            this.recordCount = recordCount
        }
    }

    @Transactional
    fun markFailed(importId: Long) {
        update(importId) { status = "FAILED" }
    }

    private fun update(importId: Long, changes: ImportEntity.() -> Unit) {
        val portfolioImport = importRepository.findById(importId)
            .orElseThrow { IllegalStateException("Import $importId was not found") }

        portfolioImport.changes()
    }
}
