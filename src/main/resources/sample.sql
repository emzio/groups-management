INSERT INTO GroupManagement.canceled_classes (local_date) VALUES ('2022-03-15');
INSERT INTO GroupManagement.canceled_classes (local_date) VALUES ('2022-04-15');
INSERT INTO GroupManagement.canceled_classes (local_date) VALUES ('2022-04-29');
INSERT INTO GroupManagement.canceled_classes (local_date) VALUES ('2022-02-15');

INSERT INTO GroupManagement.customer (email, last_name, name) VALUES ('email@domain.pl', 'Kowalski', 'Jan');
INSERT INTO GroupManagement.customer (email, last_name, name) VALUES ('another@domain.pl', 'Iksińska', 'Żaneta');

INSERT INTO GroupManagement.group_model (day_of_week, local_time, name) VALUES (4, '18:00:00', 'nameTest');

INSERT INTO `role` (`id`, `name`) VALUES (NULL, 'ROLE_USER');