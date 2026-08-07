package io.github.tuanola.investflow.parser

import io.github.tuanola.investflow.service.UploadedCsv
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFalse

class StubCsvParserTest {

    private val parser = StubCsvParser()

    @Test
    fun parser_reportsThatProcessingIsUnavailable() {
        assertFalse(parser.isAvailableForProcessing)
    }

    @Test
    fun parse_failsIfCalledDirectly() {
        val csv = UploadedCsv("portfolio.csv", "Date,Ticker\n".toByteArray())

        assertThrows<IllegalStateException> { parser.parse(csv) }
    }
}
