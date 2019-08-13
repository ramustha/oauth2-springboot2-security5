CREATE TABLE OAUTH_ACCESS_TOKEN
(
    TOKEN_ID          VARCHAR(256),
    TOKEN             TEXT,
    AUTHENTICATION_ID VARCHAR(256),
    USER_NAME         VARCHAR(256),
    CLIENT_ID         VARCHAR(256),
    AUTHENTICATION    TEXT,
    REFRESH_TOKEN     VARCHAR(256)
);

CREATE TABLE OAUTH_CLIENT_DETAILS
(
    CLIENT_ID               VARCHAR(255),
    CLIENT_SECRET           VARCHAR(255) NOT NULL,
    RESOURCE_IDS            VARCHAR(255),
    SCOPE                   VARCHAR(255),
    AUTHORIZED_GRANT_TYPES  VARCHAR(255),
    WEB_SERVER_REDIRECT_URI VARCHAR(255),
    AUTHORITIES             VARCHAR(255),
    ACCESS_TOKEN_VALIDITY   INT,
    REFRESH_TOKEN_VALIDITY  INT,
    ADDITIONAL_INFORMATION  TEXT,
    AUTOAPPROVE             BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (CLIENT_ID)
);

CREATE TABLE OAUTH_REFRESH_TOKEN
(
    TOKEN_ID       VARCHAR(256),
    TOKEN          TEXT,
    AUTHENTICATION TEXT
);


CREATE TABLE OAUTH_CLIENT_TOKEN
(
    TOKEN_ID          VARCHAR(256),
    TOKEN             TEXT,
    AUTHENTICATION_ID VARCHAR(256),
    USER_NAME         VARCHAR(256),
    CLIENT_ID         VARCHAR(256)
);