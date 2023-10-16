insert into users(id, name, email, password, register_at)
values (1, 'Rustam', 'easton12345@gmail.com', 'pass', now()),
       (2, 'Andrey', 'andr@gmail.com', 'adnrpass', now()),
       (3, 'Anna', 'annaAnna@gmail.com', 'annapassword', now()),
       (4, 'Maxim', 'max@gmail.com', 'maxstrongpass', now()),
       (5, 'Alla', 'alalla@gmail.com', 'lalatrongpass', now()),
       (6, 'Petr', 'petya@gmail.com', 'qwerty', now());
select setval('users_id_seq', (select max(id) from users));

insert into sender_recipient(id, sender_id, recipient_id, request_status)
values (1, 1, 2, 'SUBSCRIBED'),
       (2, 1, 3, 'FRIENDS');
select setval('sender_recipient_id_seq', (select max(id) from sender_recipient));

insert into post(id, users_id, title, body, created_at)
values (2, 2, 'MyPost', 'body of the post', now());