CREATE TABLE resume
(
    uuid VARCHAR(36) PRIMARY KEY,
    full_name TEXT NOT NULL
);

CREATE TABLE contact
(
    id SERIAL PRIMARY KEY,
    resume_id VARCHAR(36) REFERENCES resume ON DELETE CASCADE,
    type TEXT NOT NULL,
    value TEXT NOT NULL,
    UNIQUE (resume_id, type)
);

CREATE TABLE text_section
(
    id SERIAL PRIMARY KEY,
    resume_id VARCHAR(36) REFERENCES resume ON DELETE CASCADE,
    type TEXT NOT NULL,
    value TEXT NOT NULL,
    UNIQUE(resume_id, type)
);

CREATE TABLE list_section
(
    id SERIAL PRIMARY KEY,
    resume_id VARCHAR(36) REFERENCES resume ON DELETE CASCADE,
    type TEXT NOT NULL,
    value TEXT NOT NULL,
    UNIQUE(resume_id, type)
);
