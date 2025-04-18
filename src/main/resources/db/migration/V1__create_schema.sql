CREATE TABLE sectors (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_id BIGINT,
    CONSTRAINT fk_sector_parent FOREIGN KEY (parent_id) REFERENCES sectors(id)
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE user_consents (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    consent_type VARCHAR(100) NOT NULL,
    terms_version VARCHAR(50) NOT NULL,
    agreed_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_user_consent_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uk_user_consent_type_version UNIQUE (user_id, consent_type, terms_version)
);

CREATE TABLE user_sectors (
    user_id UUID NOT NULL,
    sector_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, sector_id),
    CONSTRAINT fk_user_sector_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_sector_sector FOREIGN KEY (sector_id) REFERENCES sectors(id)
);

CREATE INDEX idx_sectors_parent_id ON sectors(parent_id);
CREATE INDEX idx_user_consents_user_id ON user_consents(user_id);
CREATE INDEX idx_user_consents_type_version ON user_consents(consent_type, terms_version);