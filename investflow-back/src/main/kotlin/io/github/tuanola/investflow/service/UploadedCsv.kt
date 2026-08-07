package io.github.tuanola.investflow.service

data class UploadedCsv(
    val fileName: String,
    val content: ByteArray
)
