-- $2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK is password
INSERT INTO S_USERS (ID,
                     USERNAME,
                     EMAIL,
                     ENABLED,
                     IS_ACCOUNT_NON_EXPIRED,
                     IS_CREDENTIALS_NON_EXPIRED,
                     IS_ACCOUNT_NON_LOCKED)
    VALUES ('u00001', 'admin', 'admin@example.com', TRUE, TRUE, TRUE, TRUE),
    ('u00002', 'user', 'user@example.com', TRUE, TRUE, TRUE, TRUE),
    ('u00003', 'userDisable', 'userDisable@example.com', FALSE, TRUE, TRUE, TRUE),
    ('u00004', 'accountExpired', 'accountExpired@example.com', TRUE, FALSE, TRUE, TRUE),
    ('u00005', 'credentialExpired', 'credentialExpired@example.com', TRUE, TRUE, FALSE, TRUE),
    ('u00006', 'accountLocked', 'accountLocked@example.com', TRUE, TRUE, TRUE, FALSE);

-- $2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK is password
INSERT INTO S_PASSWORDS (ID, CURRENT_PASSWORD, OLD_PASSWORD)
    VALUES ('pa00001', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK', null),
    ('pa00002', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK', null);

INSERT INTO S_USERS_PASSWORDS (ID_USER, ID_PASSWORD)
    VALUES ('u00001', 'pa00001'),
    ('u00002', 'pa00002'),
    ('u00003', 'pa00001'),
    ('u00004', 'pa00002'),
    ('u00005', 'pa00001');

INSERT INTO S_ROLES (ID, NAME)
    VALUES ('r00001', 'ROLE_ADMIN'),
    ('r00002', 'ROLE_USER');

INSERT INTO S_PERMISSIONS (ID, LABEL, VALUE)
    VALUES ('pe00001', 'can create source', 'SOURCE_CREATE'),
    ('pe00002', 'can update source', 'SOURCE_UPDATE'),
    ('pe00003', 'can read source', 'SOURCE_READ'),
    ('pe00004', 'can delete source', 'SOURCE_DELETE'),
    ('pe00005', 'can update synd', 'SYND_UPDATE'),
    ('pe00006', 'can read synd', 'SYND_READ'),
    ('pe00007', 'can delete synd', 'SYND_DELETE');

INSERT INTO S_PERMISSIONS_ROLES (ID_ROLE, ID_PERMISSION)
    VALUES ('r00001', 'pe00001'),
    ('r00001', 'pe00002'),
    ('r00001', 'pe00003'),
    ('r00001', 'pe00004'),
    ('r00001', 'pe00005'),
    ('r00001', 'pe00006'),
    ('r00001', 'pe00007'),
    ('r00002', 'pe00003'),
    ('r00002', 'pe00006');

INSERT INTO S_USERS_ROLES (ID_USER, ID_ROLE)
    VALUES ('u00001', 'r00001'),
    ('u00001', 'r00002'),
    ('u00002', 'r00002'),
    ('u00003', 'r00002'),
    ('u00004', 'r00002'),
    ('u00005', 'r00002'),
    ('u00006', 'r00002');
