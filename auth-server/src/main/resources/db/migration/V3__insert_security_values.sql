-- $2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK is password
INSERT INTO S_USER (ID,
                    USERNAME,
                    EMAIL,
                    ENABLED,
                    IS_ACCOUNT_NON_EXPIRED,
                    IS_CREDENTIALS_NON_EXPIRED,
                    IS_ACCOUNT_NON_LOCKED)
VALUES ('u00001', 'system', 'system@example.com', TRUE, TRUE, TRUE, TRUE),
       ('u00002', 'admin', 'admin@example.com', TRUE, TRUE, TRUE, TRUE),
       ('u00003', 'user', 'user@example.com', TRUE, TRUE, TRUE, TRUE),
       ('u00004', 'userDisable', 'userDisable@example.com', FALSE, TRUE, TRUE, TRUE),
       ('u00005', 'accountExpired', 'accountExpired@example.com', TRUE, FALSE, TRUE, TRUE),
       ('u00006', 'credentialExpired', 'credentialExpired@example.com', TRUE, TRUE, FALSE, TRUE),
       ('u00007', 'accountLocked', 'accountLocked@example.com', TRUE, TRUE, TRUE, FALSE);

-- $2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK is password
INSERT INTO S_PASSWORD (ID, CURRENT_PASSWORD, OLD_PASSWORD)
VALUES ('pa00001', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK', null),
       ('pa00002', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK', null),
       ('pa00003', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK', null),
       ('pa00004', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK', null),
       ('pa00005', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK', null),
       ('pa00006', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK', null),
       ('pa00007', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK', null);

INSERT INTO S_USERS_PASSWORDS (ID_USER, ID_PASSWORD)
VALUES ('u00001', 'pa00001'),
       ('u00002', 'pa00002'),
       ('u00003', 'pa00003'),
       ('u00004', 'pa00004'),
       ('u00005', 'pa00005'),
       ('u00006', 'pa00006'),
       ('u00007', 'pa00007');

INSERT INTO S_ROLES (ID, NAME)
VALUES ('r00001', 'ROLE_SYSTEM'),
       ('r00002', 'ROLE_ADMIN'),
       ('r00003', 'ROLE_USER');

INSERT INTO S_PERMISSIONS (ID, LABEL, VALUE)
VALUES ('pe00001', 'can create source', 'source_create'),
       ('pe00002', 'can update source', 'source_update'),
       ('pe00003', 'can read source', 'source_read'),
       ('pe00004', 'can delete source', 'source_delete'),
       ('pe00005', 'can update synd', 'synd_update'),
       ('pe00006', 'can read synd', 'synd_read'),
       ('pe00007', 'can delete synd', 'synd_delete');

INSERT INTO S_PERMISSIONS_ROLES (ID_ROLE, ID_PERMISSION)
VALUES ('r00001', 'pe00001'),
       ('r00001', 'pe00002'),
       ('r00001', 'pe00003'),
       ('r00001', 'pe00004'),
       ('r00001', 'pe00005'),
       ('r00001', 'pe00006'),
       ('r00001', 'pe00007'),
       ('r00002', 'pe00001'),
       ('r00002', 'pe00002'),
       ('r00002', 'pe00003'),
       ('r00002', 'pe00004'),
       ('r00002', 'pe00005'),
       ('r00002', 'pe00006'),
       ('r00002', 'pe00007'),
       ('r00003', 'pe00003'),
       ('r00003', 'pe00006');

INSERT INTO S_USERS_ROLES (ID_USER, ID_ROLE)
VALUES ('u00001', 'r00001'),
       ('u00001', 'r00002'),
       ('u00001', 'r00003'),
       ('u00002', 'r00002'),
       ('u00002', 'r00003'),
       ('u00003', 'r00003'),
       ('u00004', 'r00003'),
       ('u00005', 'r00003'),
       ('u00006', 'r00003'),
       ('u00007', 'r00003');
