CREATE TABLE IF NOT EXISTS account (
    account_sid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    name TEXT NOT NULL,
    avatar TEXT,
    status TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS account_session (
    account_session_sid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_sid BIGINT NOT NULL REFERENCES account(account_sid) ON DELETE CASCADE,
    session_id TEXT NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    last_activity_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    ended_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS account_session_activity (
    account_session_activity_sid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_session_sid BIGINT NOT NULL REFERENCES account_session(account_session_sid) ON DELETE CASCADE,
    request_method TEXT NOT NULL,
    request_path TEXT NOT NULL,
    response_status INT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS navigation (
    navigation_sid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_sid BIGINT NOT NULL REFERENCES account(account_sid) ON DELETE CASCADE,
    parent_navigation_sid BIGINT REFERENCES navigation(navigation_sid) ON DELETE CASCADE,
    navigation_key TEXT NOT NULL,
    item_id TEXT NOT NULL,
    title TEXT,
    subtitle TEXT,
    type TEXT NOT NULL,
    icon TEXT,
    link TEXT,
    tooltip TEXT,
    badge_title TEXT,
    badge_classes TEXT,
    active BOOLEAN,
    disabled BOOLEAN,
    exact_match BOOLEAN,
    sort_order INT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS metrics (
    metrics_sid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_sid BIGINT NOT NULL REFERENCES account(account_sid) ON DELETE CASCADE,
    metric_key TEXT NOT NULL,
    value INT NOT NULL,
    label TEXT NOT NULL,
    secondary_label TEXT NOT NULL,
    secondary_value INT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS github_metrics_component (
    github_metrics_component_sid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_sid BIGINT NOT NULL REFERENCES account(account_sid) ON DELETE CASCADE,
    overview_this_week_new_issues INT NOT NULL,
    overview_this_week_closed_issues INT NOT NULL,
    overview_this_week_fixed INT NOT NULL,
    overview_this_week_wont_fix INT NOT NULL,
    overview_this_week_re_opened INT NOT NULL,
    overview_this_week_needs_triage INT NOT NULL,
    overview_last_week_new_issues INT NOT NULL,
    overview_last_week_closed_issues INT NOT NULL,
    overview_last_week_fixed INT NOT NULL,
    overview_last_week_wont_fix INT NOT NULL,
    overview_last_week_re_opened INT NOT NULL,
    overview_last_week_needs_triage INT NOT NULL,
    labels TEXT[] NOT NULL,
    this_week_new_issues_name TEXT NOT NULL,
    this_week_new_issues_type TEXT NOT NULL,
    this_week_new_issues_data INT[] NOT NULL,
    this_week_closed_issues_name TEXT NOT NULL,
    this_week_closed_issues_type TEXT NOT NULL,
    this_week_closed_issues_data INT[] NOT NULL,
    last_week_new_issues_name TEXT NOT NULL,
    last_week_new_issues_type TEXT NOT NULL,
    last_week_new_issues_data INT[] NOT NULL,
    last_week_closed_issues_name TEXT NOT NULL,
    last_week_closed_issues_type TEXT NOT NULL,
    last_week_closed_issues_data INT[] NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS task_distribution_component (
    task_distribution_component_sid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_sid BIGINT NOT NULL REFERENCES account(account_sid) ON DELETE CASCADE,
    overview_this_week_new INT NOT NULL,
    overview_this_week_completed INT NOT NULL,
    overview_last_week_new INT NOT NULL,
    overview_last_week_completed INT NOT NULL,
    labels TEXT[] NOT NULL,
    this_week_series INT[] NOT NULL,
    last_week_series INT[] NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS schedule_component (
    schedule_component_sid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_sid BIGINT NOT NULL REFERENCES account(account_sid) ON DELETE CASCADE,
    today JSONB NOT NULL,
    tomorrow JSONB NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_account_session_account_sid ON account_session(account_sid);
CREATE INDEX IF NOT EXISTS idx_account_session_activity_session_sid ON account_session_activity(account_session_sid);
CREATE INDEX IF NOT EXISTS idx_account_session_activity_created_at ON account_session_activity(created_at);
CREATE INDEX IF NOT EXISTS idx_navigation_account_sid ON navigation(account_sid);
CREATE INDEX IF NOT EXISTS idx_navigation_parent_sid ON navigation(parent_navigation_sid);
CREATE INDEX IF NOT EXISTS idx_navigation_account_key ON navigation(account_sid, navigation_key);
