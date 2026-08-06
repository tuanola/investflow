CREATE TABLE imports (
    id BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    uploaded_at TIMESTAMP NOT NULL DEFAULT NOW(),
    record_count INT NOT NULL DEFAULT 0,
    CONSTRAINT imports_status_check
        CHECK (status IN ('UPLOADED', 'PROCESSING', 'COMPLETED', 'FAILED'))
);