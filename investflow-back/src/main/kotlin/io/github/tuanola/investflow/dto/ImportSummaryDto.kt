package io.github.tuanola.investflow.dto

import java.time.Instant

data class ImportSummaryDto(
    val id: Long,
    val fileName: String,
    val status: String,
    val uploadedAt: Instant,
    val recordCount: Int
)