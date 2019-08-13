-- $2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK is password
INSERT INTO OAUTH_CLIENT_DETAILS(CLIENT_ID,
                                 CLIENT_SECRET,
                                 RESOURCE_IDS,
                                 SCOPE,
                                 AUTHORIZED_GRANT_TYPES,
                                 WEB_SERVER_REDIRECT_URI,
                                 AUTHORITIES,
                                 ACCESS_TOKEN_VALIDITY,
                                 REFRESH_TOKEN_VALIDITY,
                                 ADDITIONAL_INFORMATION,
                                 AUTOAPPROVE)
    VALUES
    ('c00001', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK',
     'syndResource', 'SOURCE_READ,SYND_READ', 'authorization_code,implicit',
     'http://localhost:8080/login/oauth2/code/synd', null, 900, 3600, null, FALSE),
--     client-with-registered-redirect
    ('c00002', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK',
     'syndResource', 'SOURCE_READ,SYND_READ', 'authorization_code,client_credentials',
     'http://localhost:8080/login/oauth2/code/synd', null, 900, 3600, '{}', FALSE),
--     trusted-client
    ('c00003', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK',
     'syndResource', 'SOURCE_READ,SYND_READ', 'password,authorization_code,refresh_token,implicit',
     'http://localhost:8080/login/oauth2/code/synd', null, 900, 3600, '{}', FALSE),
--     trusted-client-with-secret
    ('c00004', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK',
     'syndResource', 'SYND_READ', 'password,authorization_code,refresh_token,implicit',
     'http://localhost:8080/login/oauth2/code/synd', null, 900, 3600, '{}', FALSE),
--     less-trusted-client
    ('c00005', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK',
     'syndResource', 'SOURCE_READ,SYND_READ', 'authorization_code,implicit',
     'http://localhost:8080/login/oauth2/code/synd', null, 900, 3600, '{}', FALSE),
--     lesstrusted-autoapprove-client
    ('c00006', '{bcrypt}$2a$10$kTAw0Z.BXg6UfH9b1UioE.cZl7ObfpT9RWGULtfOUjE5B3g8ZJhKK',
     'syndResource', 'SOURCE_READ,SYND_READ', 'implicit',
     'http://localhost:8080/login/oauth2/code/synd', null, 900, 3600, '{}', TRUE);