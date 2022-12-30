-- drop table tickets;
-- drop table users;
-- drop table sessions;
-- drop table halls;

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE
);

COMMENT ON table users IS '������� �������������';
COMMENT ON column users.id IS '������������� ������������';
COMMENT ON column users.username IS '��� ������������';
COMMENT ON column users.password IS '������ ������������';
COMMENT ON column users.email IS 'Email ������������ (��� �����)';
COMMENT ON column users.phone IS '������� ������������ (��� �����)';



CREATE TABLE halls (
  id SERIAL PRIMARY KEY,
  name text NOT NULL UNIQUE ,
  rows int NOT NULL,
  cells int NOT NULL
);

COMMENT ON table halls IS '������� �����';
COMMENT ON column halls.name IS '�������� ����';
COMMENT ON column halls.rows IS '���������� ����� � ����';
COMMENT ON column halls.cells IS '���������� ���� � ����';



CREATE TABLE sessions (
  id SERIAL PRIMARY KEY,
  name text,
  foto BYTEA,
  halls_id int NOT NULL REFERENCES halls(id)
);

comment on table sessions is '������� �������';
comment on column sessions.id is '������������� ������';
comment on column sessions.name is '�������� ������';
comment on column sessions.foto is '������ ������';
comment on column sessions.halls is '������������� ����';



CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    pos_row INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id)
);

alter table tickets
add constraint unique_ticket_constrain
unique (session_id, cell, pos_row);

COMMENT ON table tickets IS '������� �������';
COMMENT ON column tickets.id IS '������������� ������';
COMMENT ON column tickets.session_id IS '������������� ������, ������� ����';
COMMENT ON column tickets.pos_row IS '����� ����';
COMMENT ON column tickets.cell IS '����� �����';
COMMENT ON column tickets.user_id IS '������������� ������������, ������� ����';
