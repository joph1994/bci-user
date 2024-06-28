INSERT INTO USER_BCI (id_user_bci, name, email, password, created, modified, last_login, is_active, type)
VALUES ('eab79700-06e3-4e20-b5c7-5814f56b4c4a', 'Administrador', 'admin@dominio.cl', '$2a$12$4mLwwvFn6lLhwu.XJ8D30OaTbivUC9U.0NY/AfLYf/TWDw.Ehrzaa', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'ADMIN');

INSERT INTO USER_PHONE (number, citycode, contrycode, id_user_bci)
VALUES ('123456789', '1', '57', 'eab79700-06e3-4e20-b5c7-5814f56b4c4a');

INSERT INTO USER_BCI (id_user_bci, name, email, password, created, modified, last_login, is_active, type)
VALUES ('eab79700-06e3-4e20-b5c7-5814f56b4c4b', 'User', 'user@dominio.cl', '$2a$12$YKUZluEGLbjTn3bzufjAc.g1EpBEBn0dNDMQ.2JhVkIRpFjEeByMW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 'USER');

INSERT INTO USER_PHONE (number, citycode, contrycode, id_user_bci)
VALUES ('123456788', '1', '57', 'eab79700-06e3-4e20-b5c7-5814f56b4c4b');