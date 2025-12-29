DELETE FROM crypto_watchlist_component;
DELETE FROM crypto_wallets_component;
DELETE FROM crypto_prices_component;
DELETE FROM crypto_btc_component;
DELETE FROM schedule_component;
DELETE FROM task_distribution_component;
DELETE FROM github_metrics_component;
DELETE FROM metrics;
DELETE FROM account_session_activity;
DELETE FROM account_session;
DELETE FROM navigation;
DELETE FROM account;

INSERT INTO account (account_sid, email, password_hash, name, avatar, status) OVERRIDING SYSTEM VALUE
VALUES
    (1, 'evan.g.coyle@gmail.com', '21988c8818ec26d9686f08a05117e471b9be6ac7f514ec6123d06bab59f6e6ed', 'Evan Coyle', 'images/avatars/Evan - IMG_0188.jpg', 'online');
    
INSERT INTO account (account_sid, email, password_hash, name, avatar, status) OVERRIDING SYSTEM VALUE
VALUES
    (2, 'ecoyle@medistreams.com', '21988c8818ec26d9686f08a05117e471b9be6ac7f514ec6123d06bab59f6e6ed', 'Demo Acct', 'images/avatars/Evan - IMG_0188.jpg', 'online');

INSERT INTO metrics (metrics_sid, account_sid, metric_key, value, label, secondary_label, secondary_value) OVERRIDING SYSTEM VALUE
VALUES
    (1, 1, 'summary', 21, 'Due Tasks', 'Completed:', 13),
    (2, 1, 'overdue', 17, 'Tasks', 'From yesterday:', 9),
    (3, 1, 'issues', 24, 'Open', 'Closed today:', 19),
    (4, 1, 'features', 38, 'Proposals', 'Implemented:', 16);

INSERT INTO github_metrics_component (
    github_metrics_component_sid,
    account_sid,
    overview_this_week_new_issues,
    overview_this_week_closed_issues,
    overview_this_week_fixed,
    overview_this_week_wont_fix,
    overview_this_week_re_opened,
    overview_this_week_needs_triage,
    overview_last_week_new_issues,
    overview_last_week_closed_issues,
    overview_last_week_fixed,
    overview_last_week_wont_fix,
    overview_last_week_re_opened,
    overview_last_week_needs_triage,
    labels,
    this_week_new_issues_name,
    this_week_new_issues_type,
    this_week_new_issues_data,
    this_week_closed_issues_name,
    this_week_closed_issues_type,
    this_week_closed_issues_data,
    last_week_new_issues_name,
    last_week_new_issues_type,
    last_week_new_issues_data,
    last_week_closed_issues_name,
    last_week_closed_issues_type,
    last_week_closed_issues_data
) OVERRIDING SYSTEM VALUE
VALUES
    (
        1,
        1,
        214,
        75,
        3,
        4,
        8,
        6,
        197,
        72,
        6,
        11,
        6,
        5,
        ARRAY['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
        'New issues',
        'line',
        ARRAY[42, 28, 43, 34, 20, 25, 22],
        'Closed issues',
        'column',
        ARRAY[11, 10, 8, 11, 8, 10, 17],
        'New issues',
        'line',
        ARRAY[37, 32, 39, 27, 18, 24, 20],
        'Closed issues',
        'column',
        ARRAY[9, 8, 10, 12, 7, 11, 15]
    );

INSERT INTO task_distribution_component (
    task_distribution_component_sid,
    account_sid,
    overview_this_week_new,
    overview_this_week_completed,
    overview_last_week_new,
    overview_last_week_completed,
    labels,
    this_week_series,
    last_week_series
) OVERRIDING SYSTEM VALUE
VALUES
    (
        1,
        1,
        594,
        287,
        526,
        260,
        ARRAY['API', 'Backend', 'Frontend', 'Issues'],
        ARRAY[15, 20, 38, 27],
        ARRAY[19, 16, 42, 23]
    );

INSERT INTO schedule_component (schedule_component_sid, account_sid, today, tomorrow) OVERRIDING SYSTEM VALUE
VALUES
    (
        1,
        1,
        '[
            {"title":"Group Meeting","time":"in 32 minutes","location":"Conference room 1B"},
            {"title":"Coffee Break","time":"10:30 AM","location":null},
            {"title":"Public Beta Release","time":"11:00 AM","location":null},
            {"title":"Lunch","time":"12:10 PM","location":null},
            {"title":"Dinner with David","time":"05:30 PM","location":"Magnolia"},
            {"title":"Jane''s Birthday Party","time":"07:30 PM","location":"Home"},
            {"title":"Overseer''s Retirement Party","time":"09:30 PM","location":"Overseer''s room"}
        ]'::jsonb,
        '[
            {"title":"Marketing Meeting","time":"09:00 AM","location":"Conference room 1A"},
            {"title":"Public Announcement","time":"11:00 AM","location":null},
            {"title":"Lunch","time":"12:10 PM","location":null},
            {"title":"Meeting with Beta Testers","time":"03:00 PM","location":"Conference room 2C"},
            {"title":"Live Stream","time":"05:30 PM","location":null},
            {"title":"Release Party","time":"07:30 PM","location":"CEO''s house"},
            {"title":"CEO''s Private Party","time":"09:30 PM","location":"CEO''s Penthouse"}
        ]'::jsonb
    );

INSERT INTO crypto_btc_component (
    crypto_btc_component_sid,
    account_sid,
    amount,
    trend_dir,
    trend_amount,
    market_cap,
    volume,
    supply,
    all_time_high,
    price_series
) OVERRIDING SYSTEM VALUE
VALUES
    (
        1,
        1,
        8878.48,
        'up',
        0.17,
        148752956966,
        22903438381,
        18168448,
        19891.0,
        '[
            {
                "name": "Price",
                "data": [
                    { "x": -145, "y": 6554.36 },
                    { "x": -144, "y": 6554.36 },
                    { "x": -143, "y": 6546.94 },
                    { "x": -142, "y": 6546.96 },
                    { "x": -141, "y": 6546.11 },
                    { "x": -140, "y": 6550.26 },
                    { "x": -139, "y": 6546.11 },
                    { "x": -138, "y": 6550.79 },
                    { "x": -137, "y": 6545.36 },
                    { "x": -136, "y": 6541.06 },
                    { "x": -135, "y": 6540.1 },
                    { "x": -134, "y": 6538.31 },
                    { "x": -133, "y": 6538.42 },
                    { "x": -132, "y": 6538.48 },
                    { "x": -131, "y": 6538.71 },
                    { "x": -130, "y": 6548.42 },
                    { "x": -129, "y": 6546.87 },
                    { "x": -128, "y": 6547.07 },
                    { "x": -127, "y": 6535.07 },
                    { "x": -126, "y": 6535.01 },
                    { "x": -125, "y": 6539.02 },
                    { "x": -124, "y": 6547.96 },
                    { "x": -123, "y": 6547.92 },
                    { "x": -122, "y": 6546.56 },
                    { "x": -121, "y": 6546.56 },
                    { "x": -120, "y": 6564.16 },
                    { "x": -119, "y": 6560.83 },
                    { "x": -118, "y": 6559.08 },
                    { "x": -117, "y": 6553.02 },
                    { "x": -116, "y": 6564.99 }
                ]
            }
        ]'::jsonb
    );

INSERT INTO crypto_prices_component (
    crypto_prices_component_sid,
    account_sid,
    btc,
    eth,
    bch,
    xrp
) OVERRIDING SYSTEM VALUE
VALUES
    (1, 1, 8878.48, 170.46, 359.93, 0.23512);

INSERT INTO crypto_wallets_component (
    crypto_wallets_component_sid,
    account_sid,
    btc,
    eth,
    bch,
    xrp
) OVERRIDING SYSTEM VALUE
VALUES
    (1, 1, 24.97311243, 126.3212, 78.454412, 11278.771123);

INSERT INTO crypto_watchlist_component (
    crypto_watchlist_component_sid,
    account_sid,
    items
) OVERRIDING SYSTEM VALUE
VALUES
    (
        1,
        1,
        '[
            {
                "title": "Ethereum",
                "iso": "ETH",
                "amount": 170.46,
                "trend": { "dir": "up", "amount": 2.35 },
                "series": [
                    {
                        "name": "Price",
                        "data": [
                            { "x": "09:00", "y": 154.36 },
                            { "x": "09:01", "y": 154.36 },
                            { "x": "09:02", "y": 146.94 },
                            { "x": "09:03", "y": 146.96 },
                            { "x": "09:04", "y": 146.11 },
                            { "x": "09:05", "y": 150.26 },
                            { "x": "09:06", "y": 146.11 },
                            { "x": "09:07", "y": 150.79 },
                            { "x": "09:08", "y": 145.36 },
                            { "x": "09:09", "y": 141.06 },
                            { "x": "09:10", "y": 140.1 },
                            { "x": "09:11", "y": 138.31 },
                            { "x": "09:12", "y": 138.42 },
                            { "x": "09:13", "y": 138.48 },
                            { "x": "09:14", "y": 138.71 },
                            { "x": "09:15", "y": 148.42 },
                            { "x": "09:16", "y": 146.87 },
                            { "x": "09:17", "y": 147.07 },
                            { "x": "09:18", "y": 135.07 },
                            { "x": "09:19", "y": 135.01 }
                        ]
                    }
                ]
            },
            {
                "title": "Bitcoin Cash",
                "iso": "BCH",
                "amount": 359.93,
                "trend": { "dir": "up", "amount": 9.94 },
                "series": [
                    {
                        "name": "Price",
                        "data": [
                            { "x": "09:00", "y": 374.77 },
                            { "x": "09:01", "y": 374.41 },
                            { "x": "09:02", "y": 375.08 },
                            { "x": "09:03", "y": 375.08 },
                            { "x": "09:04", "y": 374.09 },
                            { "x": "09:05", "y": 368.84 },
                            { "x": "09:06", "y": 367.49 },
                            { "x": "09:07", "y": 359.75 },
                            { "x": "09:08", "y": 366.65 },
                            { "x": "09:09", "y": 367.52 },
                            { "x": "09:10", "y": 367.59 },
                            { "x": "09:11", "y": 364.18 },
                            { "x": "09:12", "y": 370.11 },
                            { "x": "09:13", "y": 362.7 },
                            { "x": "09:14", "y": 362.7 },
                            { "x": "09:15", "y": 362.77 },
                            { "x": "09:16", "y": 369.46 },
                            { "x": "09:17", "y": 371.04 },
                            { "x": "09:18", "y": 371.48 },
                            { "x": "09:19", "y": 371.3 }
                        ]
                    }
                ]
            },
            {
                "title": "XRP",
                "iso": "XRP",
                "amount": 0.23512,
                "trend": { "dir": "down", "amount": 0.35 },
                "series": [
                    {
                        "name": "Price",
                        "data": [
                            { "x": "09:00", "y": 0.258 },
                            { "x": "09:01", "y": 0.256 },
                            { "x": "09:02", "y": 0.255 },
                            { "x": "09:03", "y": 0.255 },
                            { "x": "09:04", "y": 0.254 },
                            { "x": "09:05", "y": 0.248 },
                            { "x": "09:06", "y": 0.247 },
                            { "x": "09:07", "y": 0.249 },
                            { "x": "09:08", "y": 0.246 },
                            { "x": "09:09", "y": 0.247 },
                            { "x": "09:10", "y": 0.247 },
                            { "x": "09:11", "y": 0.244 },
                            { "x": "09:12", "y": 0.25 },
                            { "x": "09:13", "y": 0.242 },
                            { "x": "09:14", "y": 0.251 },
                            { "x": "09:15", "y": 0.251 },
                            { "x": "09:16", "y": 0.251 },
                            { "x": "09:17", "y": 0.249 },
                            { "x": "09:18", "y": 0.242 },
                            { "x": "09:19", "y": 0.24 }
                        ]
                    }
                ]
            },
            {
                "title": "Litecoin",
                "iso": "LTC",
                "amount": 60.15,
                "trend": { "dir": "up", "amount": 0.99 },
                "series": [
                    {
                        "name": "Price",
                        "data": [
                            { "x": "09:00", "y": 62.54 },
                            { "x": "09:01", "y": 61.54 },
                            { "x": "09:02", "y": 62.55 },
                            { "x": "09:03", "y": 60.55 },
                            { "x": "09:04", "y": 59.54 },
                            { "x": "09:05", "y": 58.48 },
                            { "x": "09:06", "y": 54.47 },
                            { "x": "09:07", "y": 51.49 },
                            { "x": "09:08", "y": 51.46 },
                            { "x": "09:09", "y": 53.47 },
                            { "x": "09:10", "y": 52.47 },
                            { "x": "09:11", "y": 54.44 },
                            { "x": "09:12", "y": 59.5 },
                            { "x": "09:13", "y": 62.42 },
                            { "x": "09:14", "y": 61.42 },
                            { "x": "09:15", "y": 60.42 },
                            { "x": "09:16", "y": 58.49 },
                            { "x": "09:17", "y": 57.51 },
                            { "x": "09:18", "y": 54.51 },
                            { "x": "09:19", "y": 51.25 }
                        ]
                    }
                ]
            },
            {
                "title": "Zcash",
                "iso": "ZEC",
                "amount": 58.41,
                "trend": { "dir": "down", "amount": 8.79 },
                "series": [
                    {
                        "name": "Price",
                        "data": [
                            { "x": "09:00", "y": 53.54 },
                            { "x": "09:01", "y": 52.54 },
                            { "x": "09:02", "y": 52.55 },
                            { "x": "09:03", "y": 46.44 },
                            { "x": "09:04", "y": 49.5 },
                            { "x": "09:05", "y": 55.42 },
                            { "x": "09:06", "y": 54.42 },
                            { "x": "09:07", "y": 43.49 },
                            { "x": "09:08", "y": 43.46 },
                            { "x": "09:09", "y": 41.47 },
                            { "x": "09:10", "y": 41.47 },
                            { "x": "09:11", "y": 51.55 },
                            { "x": "09:12", "y": 48.54 },
                            { "x": "09:13", "y": 49.48 },
                            { "x": "09:14", "y": 45.47 },
                            { "x": "09:15", "y": 51.42 },
                            { "x": "09:16", "y": 49.49 },
                            { "x": "09:17", "y": 46.51 },
                            { "x": "09:18", "y": 41.51 },
                            { "x": "09:19", "y": 44.25 }
                        ]
                    }
                ]
            },
            {
                "title": "Bitcoin Gold",
                "iso": "BTG",
                "amount": 12.23,
                "trend": { "dir": "down", "amount": 4.42 },
                "series": [
                    {
                        "name": "Price",
                        "data": [
                            { "x": "09:00", "y": 14.77 },
                            { "x": "09:01", "y": 14.41 },
                            { "x": "09:02", "y": 15.08 },
                            { "x": "09:03", "y": 15.08 },
                            { "x": "09:04", "y": 14.09 },
                            { "x": "09:05", "y": 18.84 },
                            { "x": "09:06", "y": 17.49 },
                            { "x": "09:07", "y": 19.75 },
                            { "x": "09:08", "y": 16.65 },
                            { "x": "09:09", "y": 17.52 },
                            { "x": "09:10", "y": 17.59 },
                            { "x": "09:11", "y": 14.18 },
                            { "x": "09:12", "y": 10.11 },
                            { "x": "09:13", "y": 12.7 },
                            { "x": "09:14", "y": 12.7 },
                            { "x": "09:15", "y": 12.77 },
                            { "x": "09:16", "y": 19.46 },
                            { "x": "09:17", "y": 11.04 },
                            { "x": "09:18", "y": 11.48 },
                            { "x": "09:19", "y": 11.3 }
                        ]
                    }
                ]
            }
        ]'::jsonb
    );



INSERT INTO navigation (navigation_sid, account_sid, parent_navigation_sid, navigation_key, item_id, title, subtitle, type, icon, link, tooltip, badge_title, badge_classes, active, disabled, exact_match, sort_order) OVERRIDING SYSTEM VALUE
VALUES
    (1, 1, NULL, 'default', 'dashboards', 'Dashboards', 'Unique dashboard designs', 'group', 'heroicons_outline:home', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (2, 1, 1, 'default', 'dashboards.project', 'Project', NULL, 'basic', 'heroicons_outline:clipboard-document-check', '/dashboards/project', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (3, 1, 1, 'default', 'dashboards.analytics', 'Analytics', NULL, 'basic', 'heroicons_outline:chart-pie', '/dashboards/analytics', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (4, 1, 1, 'default', 'dashboards.finance', 'Finance', NULL, 'basic', 'heroicons_outline:banknotes', '/dashboards/finance', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (5, 1, 1, 'default', 'dashboards.crypto', 'Crypto', NULL, 'basic', 'heroicons_outline:currency-dollar', '/dashboards/crypto', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (6, 1, NULL, 'default', 'apps', 'Applications', 'Custom made application designs', 'group', 'heroicons_outline:home', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (7, 1, 6, 'default', 'apps.academy', 'Academy', NULL, 'basic', 'heroicons_outline:academic-cap', '/apps/academy', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (8, 1, 6, 'default', 'apps.chat', 'Chat', NULL, 'basic', 'heroicons_outline:chat-bubble-bottom-center-text', '/apps/chat', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (9, 1, 6, 'default', 'apps.contacts', 'Contacts', NULL, 'basic', 'heroicons_outline:user-group', '/apps/contacts', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (10, 1, 6, 'default', 'apps.ecommerce', 'ECommerce', NULL, 'collapsable', 'heroicons_outline:shopping-cart', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (11, 1, 10, 'default', 'apps.ecommerce.inventory', 'Inventory', NULL, 'basic', NULL, '/apps/ecommerce/inventory', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (12, 1, 6, 'default', 'apps.file-manager', 'File Manager', NULL, 'basic', 'heroicons_outline:cloud', '/apps/file-manager', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (13, 1, 6, 'default', 'apps.help-center', 'Help Center', NULL, 'collapsable', 'heroicons_outline:information-circle', '/apps/help-center', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (14, 1, 13, 'default', 'apps.help-center.home', 'Home', NULL, 'basic', NULL, '/apps/help-center', NULL, NULL, NULL, NULL, NULL, TRUE, 1),
    (15, 1, 13, 'default', 'apps.help-center.faqs', 'FAQs', NULL, 'basic', NULL, '/apps/help-center/faqs', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (16, 1, 13, 'default', 'apps.help-center.guides', 'Guides', NULL, 'basic', NULL, '/apps/help-center/guides', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (17, 1, 13, 'default', 'apps.help-center.support', 'Support', NULL, 'basic', NULL, '/apps/help-center/support', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (18, 1, 6, 'default', 'apps.mailbox', 'Mailbox', NULL, 'basic', 'heroicons_outline:envelope', '/apps/mailbox', NULL, '27', 'px-2 bg-pink-600 text-white rounded-full', NULL, NULL, NULL, 7),
    (19, 1, 6, 'default', 'apps.notes', 'Notes', NULL, 'basic', 'heroicons_outline:pencil-square', '/apps/notes', NULL, NULL, NULL, NULL, NULL, NULL, 8),
    (20, 1, 6, 'default', 'apps.scrumboard', 'Scrumboard', NULL, 'basic', 'heroicons_outline:view-columns', '/apps/scrumboard', NULL, NULL, NULL, NULL, NULL, NULL, 9),
    (21, 1, 6, 'default', 'apps.tasks', 'Tasks', NULL, 'basic', 'heroicons_outline:check-circle', '/apps/tasks', NULL, NULL, NULL, NULL, NULL, NULL, 10),
    (22, 1, NULL, 'default', 'pages', 'Pages', 'Custom made page designs', 'group', 'heroicons_outline:document', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (23, 1, 22, 'default', 'pages.activities', 'Activities', NULL, 'basic', 'heroicons_outline:bars-3-bottom-left', '/pages/activities', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (24, 1, 22, 'default', 'pages.authentication', 'Authentication', NULL, 'collapsable', 'heroicons_outline:lock-closed', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (25, 1, 24, 'default', 'pages.authentication.sign-in', 'Sign in', NULL, 'collapsable', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (26, 1, 25, 'default', 'pages.authentication.sign-in.classic', 'Classic', NULL, 'basic', NULL, '/pages/authentication/sign-in/classic', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (27, 1, 25, 'default', 'pages.authentication.sign-in.modern', 'Modern', NULL, 'basic', NULL, '/pages/authentication/sign-in/modern', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (28, 1, 25, 'default', 'pages.authentication.sign-in.modern-reversed', 'Modern Reversed', NULL, 'basic', NULL, '/pages/authentication/sign-in/modern-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (29, 1, 25, 'default', 'pages.authentication.sign-in.split-screen', 'Split Screen', NULL, 'basic', NULL, '/pages/authentication/sign-in/split-screen', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (30, 1, 25, 'default', 'pages.authentication.sign-in.split-screen-reversed', 'Split Screen Reversed', NULL, 'basic', NULL, '/pages/authentication/sign-in/split-screen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (31, 1, 25, 'default', 'pages.authentication.sign-in.fullscreen', 'Fullscreen', NULL, 'basic', NULL, '/pages/authentication/sign-in/fullscreen', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (32, 1, 25, 'default', 'pages.authentication.sign-in.fullscreen-reversed', 'Fullscreen Reversed', NULL, 'basic', NULL, '/pages/authentication/sign-in/fullscreen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (33, 1, 24, 'default', 'pages.authentication.sign-up', 'Sign up', NULL, 'collapsable', NULL, '/pages/authentication/sign-up', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (34, 1, 33, 'default', 'pages.authentication.sign-up.classic', 'Classic', NULL, 'basic', NULL, '/pages/authentication/sign-up/classic', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (35, 1, 33, 'default', 'pages.authentication.sign-up.modern', 'Modern', NULL, 'basic', NULL, '/pages/authentication/sign-up/modern', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (36, 1, 33, 'default', 'pages.authentication.sign-up.modern-reversed', 'Modern Reversed', NULL, 'basic', NULL, '/pages/authentication/sign-up/modern-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (37, 1, 33, 'default', 'pages.authentication.sign-up.split-screen', 'Split Screen', NULL, 'basic', NULL, '/pages/authentication/sign-up/split-screen', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (38, 1, 33, 'default', 'pages.authentication.sign-up.split-screen-reversed', 'Split Screen Reversed', NULL, 'basic', NULL, '/pages/authentication/sign-up/split-screen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (39, 1, 33, 'default', 'pages.authentication.sign-up.fullscreen', 'Fullscreen', NULL, 'basic', NULL, '/pages/authentication/sign-up/fullscreen', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (40, 1, 33, 'default', 'pages.authentication.sign-up.fullscreen-reversed', 'Fullscreen Reversed', NULL, 'basic', NULL, '/pages/authentication/sign-up/fullscreen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (41, 1, 24, 'default', 'pages.authentication.sign-out', 'Sign out', NULL, 'collapsable', NULL, '/pages/authentication/sign-out', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (42, 1, 41, 'default', 'pages.authentication.sign-out.classic', 'Classic', NULL, 'basic', NULL, '/pages/authentication/sign-out/classic', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (43, 1, 41, 'default', 'pages.authentication.sign-out.modern', 'Modern', NULL, 'basic', NULL, '/pages/authentication/sign-out/modern', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (44, 1, 41, 'default', 'pages.authentication.sign-out.modern-reversed', 'Modern Reversed', NULL, 'basic', NULL, '/pages/authentication/sign-out/modern-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (45, 1, 41, 'default', 'pages.authentication.sign-out.split-screen', 'Split Screen', NULL, 'basic', NULL, '/pages/authentication/sign-out/split-screen', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (46, 1, 41, 'default', 'pages.authentication.sign-out.split-screen-reversed', 'Split Screen Reversed', NULL, 'basic', NULL, '/pages/authentication/sign-out/split-screen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (47, 1, 41, 'default', 'pages.authentication.sign-out.fullscreen', 'Fullscreen', NULL, 'basic', NULL, '/pages/authentication/sign-out/fullscreen', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (48, 1, 41, 'default', 'pages.authentication.sign-out.fullscreen-reversed', 'Fullscreen Reversed', NULL, 'basic', NULL, '/pages/authentication/sign-out/fullscreen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (49, 1, 24, 'default', 'pages.authentication.forgot-password', 'Forgot password', NULL, 'collapsable', NULL, '/pages/authentication/forgot-password', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (50, 1, 49, 'default', 'pages.authentication.forgot-password.classic', 'Classic', NULL, 'basic', NULL, '/pages/authentication/forgot-password/classic', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (51, 1, 49, 'default', 'pages.authentication.forgot-password.modern', 'Modern', NULL, 'basic', NULL, '/pages/authentication/forgot-password/modern', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (52, 1, 49, 'default', 'pages.authentication.forgot-password.modern-reversed', 'Modern Reversed', NULL, 'basic', NULL, '/pages/authentication/forgot-password/modern-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (53, 1, 49, 'default', 'pages.authentication.forgot-password.split-screen', 'Split Screen', NULL, 'basic', NULL, '/pages/authentication/forgot-password/split-screen', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (54, 1, 49, 'default', 'pages.authentication.forgot-password.split-screen-reversed', 'Split Screen Reversed', NULL, 'basic', NULL, '/pages/authentication/forgot-password/split-screen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (55, 1, 49, 'default', 'pages.authentication.forgot-password.fullscreen', 'Fullscreen', NULL, 'basic', NULL, '/pages/authentication/forgot-password/fullscreen', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (56, 1, 49, 'default', 'pages.authentication.forgot-password.fullscreen-reversed', 'Fullscreen Reversed', NULL, 'basic', NULL, '/pages/authentication/forgot-password/fullscreen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (57, 1, 24, 'default', 'pages.authentication.reset-password', 'Reset password', NULL, 'collapsable', NULL, '/pages/authentication/reset-password', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (58, 1, 57, 'default', 'pages.authentication.reset-password.classic', 'Classic', NULL, 'basic', NULL, '/pages/authentication/reset-password/classic', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (59, 1, 57, 'default', 'pages.authentication.reset-password.modern', 'Modern', NULL, 'basic', NULL, '/pages/authentication/reset-password/modern', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (60, 1, 57, 'default', 'pages.authentication.reset-password.modern-reversed', 'Modern Reversed', NULL, 'basic', NULL, '/pages/authentication/reset-password/modern-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (61, 1, 57, 'default', 'pages.authentication.reset-password.split-screen', 'Split Screen', NULL, 'basic', NULL, '/pages/authentication/reset-password/split-screen', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (62, 1, 57, 'default', 'pages.authentication.reset-password.split-screen-reversed', 'Split Screen Reversed', NULL, 'basic', NULL, '/pages/authentication/reset-password/split-screen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (63, 1, 57, 'default', 'pages.authentication.reset-password.fullscreen', 'Fullscreen', NULL, 'basic', NULL, '/pages/authentication/reset-password/fullscreen', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (64, 1, 57, 'default', 'pages.authentication.reset-password.fullscreen-reversed', 'Fullscreen Reversed', NULL, 'basic', NULL, '/pages/authentication/reset-password/fullscreen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (65, 1, 24, 'default', 'pages.authentication.unlock-session', 'Unlock session', NULL, 'collapsable', NULL, '/pages/authentication/unlock-session', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (66, 1, 65, 'default', 'pages.authentication.unlock-session.classic', 'Classic', NULL, 'basic', NULL, '/pages/authentication/unlock-session/classic', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (67, 1, 65, 'default', 'pages.authentication.unlock-session.modern', 'Modern', NULL, 'basic', NULL, '/pages/authentication/unlock-session/modern', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (68, 1, 65, 'default', 'pages.authentication.unlock-session.modern-reversed', 'Modern Reversed', NULL, 'basic', NULL, '/pages/authentication/unlock-session/modern-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (69, 1, 65, 'default', 'pages.authentication.unlock-session.split-screen', 'Split Screen', NULL, 'basic', NULL, '/pages/authentication/unlock-session/split-screen', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (70, 1, 65, 'default', 'pages.authentication.unlock-session.split-screen-reversed', 'Split Screen Reversed', NULL, 'basic', NULL, '/pages/authentication/unlock-session/split-screen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (71, 1, 65, 'default', 'pages.authentication.unlock-session.fullscreen', 'Fullscreen', NULL, 'basic', NULL, '/pages/authentication/unlock-session/fullscreen', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (72, 1, 65, 'default', 'pages.authentication.unlock-session.fullscreen-reversed', 'Fullscreen Reversed', NULL, 'basic', NULL, '/pages/authentication/unlock-session/fullscreen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (73, 1, 24, 'default', 'pages.authentication.confirmation-required', 'Confirmation required', NULL, 'collapsable', NULL, '/pages/authentication/confirmation-required', NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (74, 1, 73, 'default', 'pages.authentication.confirmation-required.classic', 'Classic', NULL, 'basic', NULL, '/pages/authentication/confirmation-required/classic', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (75, 1, 73, 'default', 'pages.authentication.confirmation-required.modern', 'Modern', NULL, 'basic', NULL, '/pages/authentication/confirmation-required/modern', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (76, 1, 73, 'default', 'pages.authentication.confirmation-required.modern-reversed', 'Modern Reversed', NULL, 'basic', NULL, '/pages/authentication/confirmation-required/modern-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (77, 1, 73, 'default', 'pages.authentication.confirmation-required.split-screen', 'Split Screen', NULL, 'basic', NULL, '/pages/authentication/confirmation-required/split-screen', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (78, 1, 73, 'default', 'pages.authentication.confirmation-required.split-screen-reversed', 'Split Screen Reversed', NULL, 'basic', NULL, '/pages/authentication/confirmation-required/split-screen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (79, 1, 73, 'default', 'pages.authentication.confirmation-required.fullscreen', 'Fullscreen', NULL, 'basic', NULL, '/pages/authentication/confirmation-required/fullscreen', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (80, 1, 73, 'default', 'pages.authentication.confirmation-required.fullscreen-reversed', 'Fullscreen Reversed', NULL, 'basic', NULL, '/pages/authentication/confirmation-required/fullscreen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (81, 1, 22, 'default', 'pages.coming-soon', 'Coming Soon', NULL, 'collapsable', 'heroicons_outline:clock', '/pages/coming-soon', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (82, 1, 81, 'default', 'pages.coming-soon.classic', 'Classic', NULL, 'basic', NULL, '/pages/coming-soon/classic', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (83, 1, 81, 'default', 'pages.coming-soon.modern', 'Modern', NULL, 'basic', NULL, '/pages/coming-soon/modern', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (84, 1, 81, 'default', 'pages.coming-soon.modern-reversed', 'Modern Reversed', NULL, 'basic', NULL, '/pages/coming-soon/modern-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (85, 1, 81, 'default', 'pages.coming-soon.split-screen', 'Split Screen', NULL, 'basic', NULL, '/pages/coming-soon/split-screen', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (86, 1, 81, 'default', 'pages.coming-soon.split-screen-reversed', 'Split Screen Reversed', NULL, 'basic', NULL, '/pages/coming-soon/split-screen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (87, 1, 81, 'default', 'pages.coming-soon.fullscreen', 'Fullscreen', NULL, 'basic', NULL, '/pages/coming-soon/fullscreen', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (88, 1, 81, 'default', 'pages.coming-soon.fullscreen-reversed', 'Fullscreen Reversed', NULL, 'basic', NULL, '/pages/coming-soon/fullscreen-reversed', NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (89, 1, 22, 'default', 'pages.error', 'Error', NULL, 'collapsable', 'heroicons_outline:exclamation-circle', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (90, 1, 89, 'default', 'pages.error.404', '404', NULL, 'basic', NULL, '/pages/error/404', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (91, 1, 89, 'default', 'pages.error.500', '500', NULL, 'basic', NULL, '/pages/error/500', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (92, 1, 22, 'default', 'pages.invoice', 'Invoice', NULL, 'collapsable', 'heroicons_outline:calculator', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (93, 1, 92, 'default', 'pages.invoice.printable', 'Printable', NULL, 'collapsable', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (94, 1, 93, 'default', 'pages.invoice.printable.compact', 'Compact', NULL, 'basic', NULL, '/pages/invoice/printable/compact', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (95, 1, 93, 'default', 'pages.invoice.printable.modern', 'Modern', NULL, 'basic', NULL, '/pages/invoice/printable/modern', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (96, 1, 22, 'default', 'pages.maintenance', 'Maintenance', NULL, 'basic', 'heroicons_outline:exclamation-triangle', '/pages/maintenance', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (97, 1, 22, 'default', 'pages.pricing', 'Pricing', NULL, 'collapsable', 'heroicons_outline:banknotes', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (98, 1, 97, 'default', 'pages.pricing.modern', 'Modern', NULL, 'basic', NULL, '/pages/pricing/modern', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (99, 1, 97, 'default', 'pages.pricing.simple', 'Simple', NULL, 'basic', NULL, '/pages/pricing/simple', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (100, 1, 97, 'default', 'pages.pricing.single', 'Single', NULL, 'basic', NULL, '/pages/pricing/single', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (101, 1, 97, 'default', 'pages.pricing.table', 'Table', NULL, 'basic', NULL, '/pages/pricing/table', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (102, 1, 22, 'default', 'pages.profile', 'Profile', NULL, 'basic', 'heroicons_outline:user-circle', '/pages/profile', NULL, NULL, NULL, NULL, NULL, NULL, 8),
    (103, 1, 22, 'default', 'pages.settings', 'Settings', NULL, 'basic', 'heroicons_outline:cog-8-tooth', '/pages/settings', NULL, NULL, NULL, NULL, NULL, NULL, 9),
    (104, 1, NULL, 'default', 'user-interface', 'User Interface', 'Building blocks of the UI & UX', 'group', 'heroicons_outline:rectangle-stack', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (105, 1, 104, 'default', 'user-interface.material-components', 'Material Components', NULL, 'basic', 'heroicons_outline:square-3-stack-3d', '/ui/material-components', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (106, 1, 104, 'default', 'user-interface.fuse-components', 'Fuse Components', NULL, 'basic', 'heroicons_outline:square-3-stack-3d', '/ui/fuse-components', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (107, 1, 104, 'default', 'user-interface.other-components', 'Other Components', NULL, 'basic', 'heroicons_outline:square-3-stack-3d', '/ui/other-components', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (108, 1, 104, 'default', 'user-interface.tailwindcss', 'TailwindCSS', NULL, 'basic', 'heroicons_outline:sparkles', '/ui/tailwindcss', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (109, 1, 104, 'default', 'user-interface.advanced-search', 'Advanced Search', NULL, 'basic', 'heroicons_outline:magnifying-glass-circle', '/ui/advanced-search', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (110, 1, 104, 'default', 'user-interface.animations', 'Animations', NULL, 'basic', 'heroicons_outline:play', '/ui/animations', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (111, 1, 104, 'default', 'user-interface.cards', 'Cards', NULL, 'basic', 'heroicons_outline:square-2-stack', '/ui/cards', NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (112, 1, 104, 'default', 'user-interface.colors', 'Colors', NULL, 'basic', 'heroicons_outline:swatch', '/ui/colors', NULL, NULL, NULL, NULL, NULL, NULL, 8),
    (113, 1, 104, 'default', 'user-interface.confirmation-dialog', 'Confirmation Dialog', NULL, 'basic', 'heroicons_outline:question-mark-circle', '/ui/confirmation-dialog', NULL, NULL, NULL, NULL, NULL, NULL, 9),
    (114, 1, 104, 'default', 'user-interface.datatable', 'Datatable', NULL, 'basic', 'heroicons_outline:table-cells', '/ui/datatable', NULL, NULL, NULL, NULL, NULL, NULL, 10),
    (115, 1, 104, 'default', 'user-interface.forms', 'Forms', NULL, 'collapsable', 'heroicons_outline:pencil-square', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 11),
    (116, 1, 115, 'default', 'user-interface.forms.fields', 'Fields', NULL, 'basic', NULL, '/ui/forms/fields', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (117, 1, 115, 'default', 'user-interface.forms.layouts', 'Layouts', NULL, 'basic', NULL, '/ui/forms/layouts', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (118, 1, 115, 'default', 'user-interface.forms.wizards', 'Wizards', NULL, 'basic', NULL, '/ui/forms/wizards', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (119, 1, 104, 'default', 'user-interface.icons', 'Icons', NULL, 'collapsable', 'heroicons_outline:bolt', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 12),
    (120, 1, 119, 'default', 'user-interface.icons.heroicons-outline', 'Heroicons Outline', NULL, 'basic', NULL, '/ui/icons/heroicons-outline', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (121, 1, 119, 'default', 'user-interface.icons.heroicons-solid', 'Heroicons Solid', NULL, 'basic', NULL, '/ui/icons/heroicons-solid', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (122, 1, 119, 'default', 'user-interface.icons.heroicons-mini', 'Heroicons Mini', NULL, 'basic', NULL, '/ui/icons/heroicons-mini', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (123, 1, 119, 'default', 'user-interface.icons.material-twotone', 'Material Twotone', NULL, 'basic', NULL, '/ui/icons/material-twotone', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (124, 1, 119, 'default', 'user-interface.icons.material-outline', 'Material Outline', NULL, 'basic', NULL, '/ui/icons/material-outline', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (125, 1, 119, 'default', 'user-interface.icons.material-solid', 'Material Solid', NULL, 'basic', NULL, '/ui/icons/material-solid', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (126, 1, 119, 'default', 'user-interface.icons.feather', 'Feather', NULL, 'basic', NULL, '/ui/icons/feather', NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (127, 1, 104, 'default', 'user-interface.page-layouts', 'Page Layouts', NULL, 'collapsable', 'heroicons_outline:rectangle-group', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 13),
    (128, 1, 127, 'default', 'user-interface.page-layouts.overview', 'Overview', NULL, 'basic', NULL, '/ui/page-layouts/overview', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (129, 1, 127, 'default', 'user-interface.page-layouts.empty', 'Empty', NULL, 'basic', NULL, '/ui/page-layouts/empty', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (130, 1, 127, 'default', 'user-interface.page-layouts.carded', 'Carded', NULL, 'collapsable', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (131, 1, 130, 'default', 'user-interface.page-layouts.carded.fullwidth', 'Fullwidth', NULL, 'basic', NULL, '/ui/page-layouts/carded/fullwidth', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (132, 1, 130, 'default', 'user-interface.page-layouts.carded.left-sidebar-1', 'Left Sidebar #1', NULL, 'basic', NULL, '/ui/page-layouts/carded/left-sidebar-1', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (133, 1, 130, 'default', 'user-interface.page-layouts.carded.left-sidebar-2', 'Left Sidebar #2', NULL, 'basic', NULL, '/ui/page-layouts/carded/left-sidebar-2', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (134, 1, 130, 'default', 'user-interface.page-layouts.carded.right-sidebar-1', 'Right Sidebar #1', NULL, 'basic', NULL, '/ui/page-layouts/carded/right-sidebar-1', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (135, 1, 130, 'default', 'user-interface.page-layouts.carded.right-sidebar-2', 'Right Sidebar #2', NULL, 'basic', NULL, '/ui/page-layouts/carded/right-sidebar-2', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (136, 1, 127, 'default', 'user-interface.page-layouts.simple', 'Simple', NULL, 'collapsable', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (137, 1, 136, 'default', 'user-interface.page-layouts.simple.fullwidth-1', 'Fullwidth #1', NULL, 'basic', NULL, '/ui/page-layouts/simple/fullwidth-1', NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (138, 1, 136, 'default', 'user-interface.page-layouts.simple.fullwidth-2', 'Fullwidth #2', NULL, 'basic', NULL, '/ui/page-layouts/simple/fullwidth-2', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (139, 1, 136, 'default', 'user-interface.page-layouts.simple.left-sidebar-1', 'Left Sidebar #1', NULL, 'basic', NULL, '/ui/page-layouts/simple/left-sidebar-1', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (140, 1, 136, 'default', 'user-interface.page-layouts.simple.left-sidebar-2', 'Left Sidebar #2', NULL, 'basic', NULL, '/ui/page-layouts/simple/left-sidebar-2', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (141, 1, 136, 'default', 'user-interface.page-layouts.simple.left-sidebar-3', 'Left Sidebar #3', NULL, 'basic', NULL, '/ui/page-layouts/simple/left-sidebar-3', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (142, 1, 136, 'default', 'user-interface.page-layouts.simple.right-sidebar-1', 'Right Sidebar #1', NULL, 'basic', NULL, '/ui/page-layouts/simple/right-sidebar-1', NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (143, 1, 136, 'default', 'user-interface.page-layouts.simple.right-sidebar-2', 'Right Sidebar #2', NULL, 'basic', NULL, '/ui/page-layouts/simple/right-sidebar-2', NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (144, 1, 136, 'default', 'user-interface.page-layouts.simple.right-sidebar-3', 'Right Sidebar #3', NULL, 'basic', NULL, '/ui/page-layouts/simple/right-sidebar-3', NULL, NULL, NULL, NULL, NULL, NULL, 8),
    (145, 1, 104, 'default', 'user-interface.typography', 'Typography', NULL, 'basic', 'heroicons_outline:pencil', '/ui/typography', NULL, NULL, NULL, NULL, NULL, NULL, 14),
    (146, 1, NULL, 'default', 'divider-1', NULL, NULL, 'divider', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (147, 1, NULL, 'default', 'documentation', 'Documentation', 'Everything you need to know about Fuse', 'group', 'heroicons_outline:information-circle', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (148, 1, 147, 'default', 'documentation.changelog', 'Changelog', NULL, 'basic', 'heroicons_outline:megaphone', '/docs/changelog', NULL, '21.0.0', 'px-2 bg-yellow-300 text-black rounded-full', NULL, NULL, NULL, 1),
    (149, 1, 147, 'default', 'documentation.guides', 'Guides', NULL, 'basic', 'heroicons_outline:book-open', '/docs/guides', NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (150, 1, 147, 'default', 'user-interface.material-components', 'Material Components', NULL, 'basic', 'heroicons_outline:square-3-stack-3d', '/ui/material-components', NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (151, 1, 147, 'default', 'user-interface.fuse-components', 'Fuse Components', NULL, 'basic', 'heroicons_outline:square-3-stack-3d', '/ui/fuse-components', NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (152, 1, 147, 'default', 'user-interface.other-components', 'Other Components', NULL, 'basic', 'heroicons_outline:square-3-stack-3d', '/ui/other-components', NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (153, 1, NULL, 'default', 'divider-2', NULL, NULL, 'divider', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 7),
    (154, 1, NULL, 'default', 'navigation-features', 'Navigation features', 'Collapsable levels & badge styles', 'group', 'heroicons_outline:bars-3', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 8),
    (155, 1, 154, 'default', 'navigation-features.level.0', 'Level 0', NULL, 'collapsable', 'heroicons_outline:check-circle', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (156, 1, 155, 'default', 'navigation-features.level.0.1', 'Level 1', NULL, 'collapsable', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (157, 1, 156, 'default', 'navigation-features.level.0.1.2', 'Level 2', NULL, 'collapsable', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (158, 1, 157, 'default', 'navigation-features.level.0.1.2.3', 'Level 3', NULL, 'collapsable', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (159, 1, 158, 'default', 'navigation-features.level.0.1.2.3.4', 'Level 4', NULL, 'collapsable', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (160, 1, 159, 'default', 'navigation-features.level.0.1.2.3.4.5', 'Level 5', NULL, 'collapsable', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (161, 1, 160, 'default', 'navigation-features.level.0.1.2.3.4.5.6', 'Level 6', NULL, 'basic', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (162, 1, 154, 'default', 'navigation-features.level.0-with-subtitle', 'Level 0', 'With subtitle', 'collapsable', 'heroicons_outline:check-circle', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (163, 1, 162, 'default', 'navigation-features.level.0.1-1-with-subtitle', 'Level 1.1', NULL, 'basic', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (164, 1, 162, 'default', 'navigation-features.level.0.1-2-with-subtitle', 'Level 1.2', NULL, 'basic', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (165, 1, 154, 'default', 'navigation-features.active', 'Active item', 'Manually marked as active', 'basic', 'heroicons_outline:check-circle', NULL, NULL, NULL, NULL, TRUE, NULL, NULL, 3),
    (166, 1, 154, 'default', 'navigation-features.disabled-collapsable', 'Disabled collapsable', 'Some subtitle', 'collapsable', 'heroicons_outline:check-circle', NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 4),
    (167, 1, 166, 'default', 'navigation-features.disabled-collapsable.child', 'You shouldn''t be able to see this child', NULL, 'basic', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (168, 1, 154, 'default', 'navigation-features.disabled-basic', 'Disabled basic', 'Some subtitle', 'basic', 'heroicons_outline:check-circle', NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 5),
    (169, 1, 154, 'default', 'navigation-features.badge-style-oval', 'Oval badge', NULL, 'basic', 'heroicons_outline:tag', NULL, NULL, '8', 'w-5 h-5 bg-teal-400 text-black rounded-full', NULL, NULL, NULL, 6),
    (170, 1, 154, 'default', 'navigation-features.badge-style-rectangle', 'Rectangle badge', NULL, 'basic', 'heroicons_outline:tag', NULL, NULL, 'Updated!', 'px-2 bg-teal-400 text-black rounded', NULL, NULL, NULL, 7),
    (171, 1, 154, 'default', 'navigation-features.badge-style-rounded', 'Rounded badge', NULL, 'basic', 'heroicons_outline:tag', NULL, NULL, 'NEW', 'px-2.5 bg-teal-400 text-black rounded-full', NULL, NULL, NULL, 8),
    (172, 1, 154, 'default', 'navigation-features.badge-style-simple', 'Simple badge', NULL, 'basic', 'heroicons_outline:tag', NULL, NULL, '87 Unread', 'text-teal-500', NULL, NULL, NULL, 9),
    (173, 1, 154, 'default', 'navigation-features.multi-line', 'A multi line navigation item title example which works just fine', NULL, 'basic', 'heroicons_outline:check-circle', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 10),
    (174, 1, NULL, 'compact', 'dashboards', 'Dashboards', NULL, 'aside', 'heroicons_outline:home', NULL, 'Dashboards', NULL, NULL, NULL, NULL, NULL, 1),
    (175, 1, NULL, 'compact', 'apps', 'Apps', NULL, 'aside', 'heroicons_outline:squares-2x2', NULL, 'Apps', NULL, NULL, NULL, NULL, NULL, 2),
    (176, 1, NULL, 'compact', 'pages', 'Pages', NULL, 'aside', 'heroicons_outline:document-duplicate', NULL, 'Pages', NULL, NULL, NULL, NULL, NULL, 3),
    (177, 1, NULL, 'compact', 'user-interface', 'UI', NULL, 'aside', 'heroicons_outline:rectangle-stack', NULL, 'UI', NULL, NULL, NULL, NULL, NULL, 4),
    (178, 1, NULL, 'compact', 'navigation-features', 'Navigation', NULL, 'aside', 'heroicons_outline:bars-3', NULL, 'Navigation', NULL, NULL, NULL, NULL, NULL, 5),
    (179, 1, NULL, 'futuristic', 'dashboards', 'DASHBOARDS', NULL, 'group', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (180, 1, NULL, 'futuristic', 'apps', 'APPS', NULL, 'group', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (181, 1, NULL, 'futuristic', 'others', 'OTHERS', NULL, 'group', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (182, 1, NULL, 'futuristic', 'pages', 'Pages', NULL, 'aside', 'heroicons_outline:document-duplicate', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (183, 1, NULL, 'futuristic', 'user-interface', 'User Interface', NULL, 'aside', 'heroicons_outline:rectangle-stack', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5),
    (184, 1, NULL, 'futuristic', 'navigation-features', 'Navigation Features', NULL, 'aside', 'heroicons_outline:bars-3', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 6),
    (185, 1, NULL, 'horizontal', 'dashboards', 'Dashboards', NULL, 'group', 'heroicons_outline:home', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
    (186, 1, NULL, 'horizontal', 'apps', 'Apps', NULL, 'group', 'heroicons_outline:squares-2x2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2),
    (187, 1, NULL, 'horizontal', 'pages', 'Pages', NULL, 'group', 'heroicons_outline:document-duplicate', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 3),
    (188, 1, NULL, 'horizontal', 'user-interface', 'UI', NULL, 'group', 'heroicons_outline:rectangle-stack', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 4),
    (189, 1, NULL, 'horizontal', 'navigation-features', 'Misc', NULL, 'group', 'heroicons_outline:bars-3', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 5);
