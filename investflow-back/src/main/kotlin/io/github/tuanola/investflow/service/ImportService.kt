package io.github.tuanola.investflow.service

import io.github.tuanola.investflow.dto.ImportSummaryDto

interface ImportService {
    fun listImports(): List<ImportSummaryDto>
    fun createImport(csv: UploadedCsv): Long
}
