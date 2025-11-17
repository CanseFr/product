INSERT INTO role (id, name)
VALUES (1, 'ADMIN')
ON DUPLICATE KEY UPDATE name = 'ADMIN';

INSERT INTO role (id, name)
VALUES (2, 'USER')
ON DUPLICATE KEY UPDATE name = 'USER';

INSERT INTO user (id, username, password, enabled)
VALUES (1, 'admin', 'admin', true)
ON DUPLICATE KEY UPDATE username = 'admin';

INSERT INTO user (id, username, password, enabled)
VALUES (2, 'user', 'user', true)
ON DUPLICATE KEY UPDATE username = 'user';

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1)
ON DUPLICATE KEY UPDATE user_id = 1;

INSERT INTO user_role (user_id, role_id)
VALUES (2, 2)
ON DUPLICATE KEY UPDATE user_id = 2;
