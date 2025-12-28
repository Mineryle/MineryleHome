CREATE TABLE IF NOT EXISTS "user" (
    user_sid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    name TEXT NOT NULL,
    avatar TEXT,
    status TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS user_session (
    user_session_sid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_sid BIGINT NOT NULL REFERENCES "user"(user_sid) ON DELETE CASCADE,
    session_id TEXT NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    last_activity_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    ended_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS user_session_activity (
    user_session_activity_sid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_session_sid BIGINT NOT NULL REFERENCES user_session(user_session_sid) ON DELETE CASCADE,
    request_method TEXT NOT NULL,
    request_path TEXT NOT NULL,
    response_status INT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_user_session_user_sid ON user_session(user_sid);
CREATE INDEX IF NOT EXISTS idx_user_session_activity_session_sid ON user_session_activity(user_session_sid);
CREATE INDEX IF NOT EXISTS idx_user_session_activity_created_at ON user_session_activity(created_at);
