DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM menus;
DELETE
FROM dishes;
DELETE
FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@gmail.com', 'user'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_USER', 100001),
       ('ROLE_ADMIN', 100001);

INSERT INTO restaurants (name)
VALUES ('Burger King'),
       ('The Restaurant at the End of the Universe');

INSERT INTO dishes (restaurant_id, name)
VALUES (100002, 'Burger'),
       (100002, 'Cola'),
       (100002, 'Snack'),
       (100002, 'Chicken Toast'),
       (100002, 'Latte'),
       (100002, 'Ice Cream'),
       (100003, 'Meet The Meat'),
       (100003, 'The Pan Galactic Gargle Blaster'),
       (100003, 'Janx Spirit'),
       (100003, 'Hagro biscuit'),
       (100003, 'Peanuts'),
       (100003, 'Jynnan tonnyx');

INSERT INTO menus (dish_id, date, price)
VALUES (100004, '2019-03-20', 1000),
       (100005, '2019-03-20', 200),
       (100006, '2019-03-20', 500),
       (100010, '2019-03-20', 9900),
       (100011, '2019-03-20', 4560),
       (100012, '2019-03-20', 1000),
       (100007, '2019-03-21', 1000),
       (100008, '2019-03-21', 320),
       (100009, '2019-03-21', 450),
       (100013, '2019-03-21', 9900),
       (100014, '2019-03-21', 4560),
       (100015, '2019-03-21', 1000);
