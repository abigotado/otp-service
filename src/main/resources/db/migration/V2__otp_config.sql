CREATE TABLE otp_config (
                            id UUID PRIMARY KEY,
                            code_length INTEGER NOT NULL,
                            ttl_seconds INTEGER NOT NULL
);