INSERT INTO oauth_client_details
    (client_id, client_secret, scope, authorized_grant_types,
    web_server_redirect_uri, authorities, access_token_validity,
    refresh_token_validity, additional_information, autoapprove)
VALUES
    ('client_android', '$2a$04$rRIQoDbmYiQf9QlKHJ3BXu30vgFv1QcLVJoKq9335mA7BKwiFplv6', 'foo,read,write','password,client_credentials,refresh_token', null, null, 36000, 36000, null, true),
    ('client_ios', '$2a$04$AnYUYnPT66TLY5fRh29FaO6Bj7bZf9g5HTlHdMF0cODpYgV7eliie', 'foo,read,write','password,client_credentials,refresh_token', null, null, 36000, 36000, null, true);

INSERT INTO user (user_id, username, password, enabled)
VALUES ('1', 'admin@example.com', '$2a$04$uBjpP8uyZ9I1SFhuT8L2ousq8V8OVla0AoooWDOXh589A7zfNG6QS', true),
       ('2', 'user@example.com', '$2a$04$uBjpP8uyZ9I1SFhuT8L2ousq8V8OVla0AoooWDOXh589A7zfNG6QS', true);

INSERT INTO role (role_id, role_name)
VALUES ('1', 'ADMIN'),
       ('2', 'USER');

INSERT INTO role_user (role_id, user_id)
VALUES ('1', '1'),
       ('2', '2');

INSERT INTO user_info (user_info_id, user_id, email, profile_image)
VALUES ('1', '1', 'admin@example.com', 'https://randomuser.me/api/portraits/women/4.jpg'),
       ('2', '2', 'user@example.com', 'https://randomuser.me/api/portraits/men/40.jpg');