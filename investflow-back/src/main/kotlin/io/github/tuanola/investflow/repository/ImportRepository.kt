package io.github.tuanola.investflow.repository

import io.github.tuanola.investflow.domain.ImportEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImportRepository : JpaRepository<ImportEntity, Long> {
    fun findAllByOrderByUploadedAtDesc(): List<ImportEntity>
}
