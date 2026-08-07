package io.github.tuanola.investflow.service

/**
 * Application port for the separate Python CSV parsing service.
 *
 * A Python HTTP adapter can implement this interface without exposing transport
 * details to the import orchestration service.
 */
interface CsvParser {
    fun parse(csv: UploadedCsv): ParsedCsv
}

data class ParsedCsv(
    val recordCount: Int
)
