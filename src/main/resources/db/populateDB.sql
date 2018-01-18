INSERT INTO users (name, email, password) VALUES
  ('user1', 'user1@mail.ru', '{noop}qwerty'),
  ('user2', 'user2@mail.ru', '{noop}qwerty'),
  ('admin', 'admin@mail.ru', '{noop}qwerty');

INSERT INTO user_roles (user_id, role) VALUES
  (1, 'ROLE_USER'),
  (2, 'ROLE_USER'),
  (3, 'ROLE_ADMIN');

INSERT INTO restaurants (name) VALUES
  ('Restaurant1'),
  ('Restaurant2'),
  ('Restaurant3');

INSERT INTO meals (rest_id, title, price, date) VALUES
  (1, 'meat', 10000, '2000-01-01'),
  (1, 'fish', 15000, now()),
  (1, 'chicken', 5000, now()),
  (2, 'meat', 15000, '2000-01-01'),
  (2, 'fish', 20000, now()),
  (2, 'chicken', 10000, now());

INSERT INTO votes (user_id, rest_id) VALUES
  (2, 1);

