DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE meals IF EXISTS;
DROP TABLE votes IF EXISTS;
DROP TABLE restaurants IF EXISTS;

CREATE TABLE restaurants
(
  id INT PRIMARY KEY NOT NULL IDENTITY,
  name VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx ON restaurants (name);

CREATE TABLE votes
(
  id INT PRIMARY KEY NOT NULL IDENTITY,
  user_id INT NOT NULL,
  rest_id INT NOT NULL,
  date DATE DEFAULT now() NOT NULL,
  CONSTRAINT votes_rest_fk FOREIGN KEY (rest_id) REFERENCES RESTAURANTS (ID) ON DELETE CASCADE
);
CREATE UNIQUE INDEX votes_unique_user_date_idx ON votes (user_id, date);

CREATE TABLE meals
(
  id INT PRIMARY KEY NOT NULL IDENTITY,
  rest_id INT NOT NULL,
  title VARCHAR(255) NOT NULL,
  price INT NOT NULL,
  date DATE DEFAULT now() NOT NULL,
  CONSTRAINT meals_rest_fk FOREIGN KEY (rest_id) REFERENCES RESTAURANTS (ID) ON DELETE CASCADE
);

CREATE TABLE users
(
  id INT PRIMARY KEY NOT NULL IDENTITY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);







