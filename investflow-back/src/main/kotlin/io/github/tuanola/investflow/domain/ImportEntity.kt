package io.github.tuanola.investflow.domain

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "imports")
class ImportEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "file_name", nullable = false)
    var fileName: String = "",

    @Column(name = "status", nullable = false)
    var status: String = "",

    @Column(name = "uploaded_at", nullable = false)
    var uploadedAt: Instant = Instant.now(),

    @Column(name = "record_count", nullable = false)
    var recordCount: Int = 0
)