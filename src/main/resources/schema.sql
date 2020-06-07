DROP TABLE IF EXISTS role_user;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS oauth_client_details;

create table oauth_client_details
(
    client_id               VARCHAR(256) PRIMARY KEY,
    resource_ids            VARCHAR(256),
    client_secret           VARCHAR(256),
    scope                   VARCHAR(256),
    authorized_grant_types  VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(256)
);

CREATE TABLE user
(
    user_id  BIGINT PRIMARY KEY auto_increment,
    username VARCHAR(128) UNIQUE,
    password VARCHAR(256),
    enabled  BOOL,
    role     BIGINT
);

CREATE TABLE role
(
    role_id   BIGINT PRIMARY KEY auto_increment,
    role_name VARCHAR(50) UNIQUE
);

CREATE TABLE role_user
(
    id      BIGINT PRIMARY KEY auto_increment,
    user_id BIGINT,
    role_id BIGINT
);

CREATE TABLE user_info
(
    user_info_id  BIGINT PRIMARY KEY auto_increment,
    user_id       BIGINT UNIQUE REFERENCES user (user_id),
    email         VARCHAR(128) UNIQUE,
    profile_image VARCHAR(256)
);