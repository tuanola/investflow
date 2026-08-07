package io.github.tuanola.investflow.service

import io.github.tuanola.investflow.repository.ImportRepository
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

class ImportServiceImplTest {

    private val importRepository = mock(ImportRepository::class.java)
    private val importLifecycle = mock(ImportLifecycle::class.java)
    private val csvParser = mock(CsvParser::class.java)
    private val importService = ImportServiceImpl(
        importRepository,
        importLifecycle,
        csvParser
    )
    private val csv = UploadedCsv("portfolio.csv", "Date,Ticker\n".toByteArray())

    @Test
    fun createImport_keepsUploadedStatusWhenParserIsUnavailable() {
        given(importLifecycle.createUploaded(csv.fileName)).willReturn(42L)
        given(csvParser.isAvailableForProcessing).willReturn(false)

        val importId = importService.createImport(csv)

        kotlin.test.assertEquals(42L, importId)
        verify(importLifecycle).createUploaded(csv.fileName)
        verify(importLifecycle, never()).markProcessing(42L)
        verify(csvParser, never()).parse(csv)
    }

    @Test
    fun createImport_completesImportWhenParserSucceeds() {
        given(importLifecycle.createUploaded(csv.fileName)).willReturn(42L)
        given(csvParser.isAvailableForProcessing).willReturn(true)
        given(csvParser.parse(csv)).willReturn(ParsedCsv(recordCount = 12))

        val importId = importService.createImport(csv)

        kotlin.test.assertEquals(42L, importId)
        inOrder(importLifecycle, csvParser).apply {
            verify(importLifecycle).createUploaded(csv.fileName)
            verify(importLifecycle).markProcessing(42L)
            verify(csvParser).parse(csv)
            verify(importLifecycle).markCompleted(42L, 12)
        }
        verify(importLifecycle, never()).markFailed(42L)
    }

    @Test
    fun createImport_failsImportWhenParserThrowsException() {
        given(importLifecycle.createUploaded(csv.fileName)).willReturn(42L)
        given(csvParser.isAvailableForProcessing).willReturn(true)
        given(csvParser.parse(csv)).willThrow(IllegalStateException("Parser unavailable"))

        val importId = importService.createImport(csv)

        kotlin.test.assertEquals(42L, importId)
        verify(importLifecycle).markProcessing(42L)
        verify(importLifecycle).markFailed(42L)
        verify(importLifecycle, never()).markCompleted(eq(42L), anyInt())
    }
}
