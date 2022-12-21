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
sessions (name, halls_id)
values
('Годзилла', 1),
('KingCong', 2),
('NeverLand', 3),
('Terminator', 4);

INSERT INTO
tickets (session_id, pos_row, cell, user_id)
VALUES
(1, 1, 1, 1),
(2, 1, 2, 2),
(3, 1, 3, 3),
(1, 2, 1, 1);