INSERT INTO users (name, email, password) VALUES
  ('user1', 'user1@mail.ru', 'qwerty'),
  ('user2', 'user2@mail.ru', 'qwerty'),
  ('admin1', 'admin1@mail.ru', 'qwerty'),
  ('admin2', 'admin2@mail.ru', 'qwerty');

INSERT INTO user_roles (user_id, role) VALUES
  (1, 'USER'),
  (2, 'USER'),
  (3, 'ADMIN'),
  (4, 'ADMIN');

INSERT INTO restaurants (name) VALUES
  ('Restaurant1'),
  ('Restaurant2');

INSERT INTO meals (rest_id, title, price, date) VALUES
  (1, 'meat', 10000, '2000-01-01'),
  (1, 'fish', 15000, now()),
  (1, 'chicken', 5000, now()),
  (2, 'meat', 15000, now()),
  (2, 'fish', 20000, now()),
  (2, 'chicken', 10000, now());

INSERT INTO votes (user_id, rest_id) VALUES
  (1, 1),
  (2, 1),
  (3, 1),
  (4, 2);

