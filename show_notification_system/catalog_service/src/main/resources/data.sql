INSERT INTO category(name) VALUES ('concert');

INSERT INTO category(name) VALUES ('play');

INSERT INTO category(name) VALUES ('exhibition');

INSERT INTO category(name) VALUES ('fashionShow');

INSERT INTO category(name) VALUES ('sportingEvent');

INSERT INTO show(name, price, duration, capacity, sale_date, status, description, number_rates, score)
VALUES ('U2', 75, 130, 560, '2021-01-23', 'PENDING', 'U2 concert', 0, 0);

INSERT INTO show(name, price, duration, capacity, sale_date, status, description, number_rates, score)
VALUES ('mutua_open', 90, 110, 600, '2021-03-28', 'PENDING', 'Tennis tourney', 0, 0);

INSERT INTO show_categories(id_show, id_category) VALUES(1, 1);

INSERT INTO show_categories(id_show, id_category) VALUES(2, 5);

INSERT INTO performance(show_id, date, remaining_seats, streaming_url, time)
VALUES (1, '2021-01-25', 320, 'u2.com', '02:03:04');

INSERT INTO performance(show_id, date, remaining_seats, streaming_url, time)
VALUES (2, '2022-10-3', 210, 'mutua.com', '18:35:04');
