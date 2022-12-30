INSERT INTO
users (username, password, email, phone)
VALUES
('Petr', '123', '1@mail.ru', '123'),
('Ivan', '234', '2@mail.ru', '234'),
('Tom', '345', '3@mail.ru', '345');

INSERT INTO
halls (name, rows, cells)
values
('Paris', 7, 10),
('KingHall', 10, 14),
('Emerald', 3, 6),
('Lux3d', 1, 2);

INSERT INTO
sessions (name, foto, halls_id)
values
('Godzilla', pg_read_binary_file('img\godzilla.jpg'), 1),
('KingCong', pg_read_binary_file('img\kingcong.jpg'), 2),
('NeverLand', pg_read_binary_file('img\neverland.jpg'), 3),
('Terminator', pg_read_binary_file('img\terminator.jpg'), 4);

INSERT INTO
tickets (session_id, pos_row, cell, user_id)
VALUES
(1, 1, 1, 1),
(2, 1, 2, 2),
(3, 1, 3, 3),
(1, 2, 1, 1);