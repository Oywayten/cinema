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
('Godzilla', 1),
('KingCong', 2),
('NeverLand', 3),
('Terminator', 4);

/*INSERT INTO
sessions (name, foto, halls_id)
values
('Godzilla', file_read('C:\projects\cinema\src\main\resources\img\godzilla.jpg'), 1),
('KingCong', file_read('C:\projects\cinema\src/main/resources/img/kingcong.jpg'), 2),
('NeverLand', file_read('C:\projects\cinema\src/main/resources/img/neverland.jpg'), 3),
('Terminator', file_read('C:\projects\cinema\src/main/resources/img/terminator.jpg'), 4);*/

INSERT INTO
tickets (session_id, pos_row, cell, user_id)
VALUES
(1, 1, 1, 1),
(2, 1, 2, 2),
(3, 1, 3, 3),
(1, 2, 1, 1);