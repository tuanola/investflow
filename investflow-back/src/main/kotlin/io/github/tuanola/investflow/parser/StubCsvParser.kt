package io.github.tuanola.investflow.parser

import io.github.tuanola.investflow.service.CsvParser
import io.github.tuanola.investflow.service.ParsedCsv
import io.github.tuanola.investflow.service.UploadedCsv
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

/**
 * Temporary in-process parser for local development and end-to-end tests.
 *
 * It keeps imports in UPLOADED until the FastAPI adapter is ready.
 */
@Component
@ConditionalOnProperty(
    prefix = "investflow.parser",
    name = ["mode"],
    havingValue = "stub",
    matchIfMissing = true
)
class StubCsvParser : CsvParser {

    override val isAvailableForProcessing = false

    override fun parse(csv: UploadedCsv): ParsedCsv =
        error("Stub CSV parser does not process uploads")
}
