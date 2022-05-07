INSERT INTO canceled_classes (local_date) VALUES ('2022-03-15');
INSERT INTO canceled_classes (local_date) VALUES ('2022-04-15');
INSERT INTO canceled_classes (local_date) VALUES ('2022-04-29');
INSERT INTO canceled_classes (local_date) VALUES ('2022-02-15');


INSERT INTO group_model (day_of_week, local_time, name, payment_rate, size) VALUES (3, '20:00:00', 'środa 19:30', 110.00, 6);
INSERT INTO group_model (day_of_week, local_time, name, payment_rate, size) VALUES (2, '19:30:00', 'wtorek 19:30', 110.00, 6);

INSERT INTO `role` (`id`, `name`) VALUES (NULL, 'ROLE_ADMIN');
INSERT INTO `role` (`id`, `name`) VALUES (NULL, 'ROLE_USER');