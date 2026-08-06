package io.github.tuanola.investflow.controller

import io.github.tuanola.investflow.dto.ImportCreatedResponse
import io.github.tuanola.investflow.dto.ImportSummaryDto
import io.github.tuanola.investflow.service.ImportService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException

private const val MAX_FILE_NAME_LENGTH = 255

@Tag(name = "Imports", description = "Portfolio import operations")
@RestController
@RequestMapping("/api/v1")
class ImportController(
    private val importService: ImportService
) {

    @Operation(
        summary = "List imports",
        description = "Returns all imports ordered from newest to oldest"
    )
    @ApiResponse(responseCode = "200", description = "Imports returned successfully")
    @GetMapping("/imports")
    fun getImports(): List<ImportSummaryDto> =
        importService.listImports()

    @Operation(
        summary = "Upload an import",
        description = "Accepts a CSV file and creates an import in the uploaded state"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Import created successfully",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ImportCreatedResponse::class)
                    )
                ]
            ),
            ApiResponse(responseCode = "400", description = "The uploaded file is invalid")
        ]
    )
    @PostMapping(
        "/imports",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun uploadImport(
        @Parameter(
            description = "CSV file to import",
            required = true,
            schema = Schema(type = "string", format = "binary")
        )
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<ImportCreatedResponse> {
        if (file.isEmpty) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "CSV file is required")
        }

        val fileName = file.originalFilename
            ?.takeIf { it.isNotBlank() }
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "File name is required")

        if (fileName.length > MAX_FILE_NAME_LENGTH) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "File name must not exceed $MAX_FILE_NAME_LENGTH characters"
            )
        }

        if (!fileName.lowercase().endsWith(".csv")) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Only CSV files are allowed")
        }

        val importId = importService.createUploadedImport(fileName)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ImportCreatedResponse(importId))
    }
}
