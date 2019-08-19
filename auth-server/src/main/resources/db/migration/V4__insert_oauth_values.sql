INSERT INTO OAUTH_CLIENT_DETAILS(ID,
                                 CLIENT_ID,
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
VALUES ('cid00001', 'c00001', '{noop}password','syndResource', 'profile,source_read,synd_read', 'authorization_code,implicit',
        'http://localhost:8080/login/oauth2/code/synd', null, 900, 3600, null, FALSE),
--     client-with-registered-redirect
       ('cid00002', 'c00002', '{noop}password', 'syndResource', 'profile,source_read,synd_read', 'authorization_code,client_credentials',
        'http://localhost:8080/login/oauth2/code/synd', null, 900, 3600, null, FALSE),
--     trusted-client
       ('cid00003', 'c00003', '{noop}password', 'syndResource', 'profile,source_read,synd_read', 'password,authorization_code,refresh_token,implicit',
        'http://localhost:8080/login/oauth2/code/synd', null, 900, 3600, null, FALSE),
--     trusted-client-with-secret
       ('cid00004', 'c00004', '{noop}password', 'syndResource', 'profile,synd_read', 'password,authorization_code,refresh_token,implicit',
        'http://localhost:8080/login/oauth2/code/synd', null, 900, 3600, null, FALSE),
--     less-trusted-client
       ('cid00005', 'c00005', '{noop}password', 'syndResource', 'profile,source_read,synd_read', 'authorization_code,implicit',
        'http://localhost:8080/login/oauth2/code/synd', null, 900, 3600, null, FALSE),
--     lesstrusted-autoapprove-client
       ('cid00006', 'c00006', '{noop}password', 'syndResource', 'profile,source_read,synd_read', 'implicit',
        'http://localhost:8080/login/oauth2/code/synd', null, 900, 3600, null, TRUE);

INSERT INTO S_USERS_OAUTH_CLIENT_DETAILS (ID_USER, CLIENT_ID)
VALUES ('u00001', 'cid00001'),
       ('u00001', 'cid00002'),
       ('u00001', 'cid00003'),
       ('u00001', 'cid00005'),
       ('u00001', 'cid00006'),
       ('u00002', 'cid00004');