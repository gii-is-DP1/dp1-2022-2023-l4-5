-- Creación de usuarios.
INSERT INTO users(username, password, avatar, tier, description, authority, birth_date, enable)
VALUES ('alesanfe', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg',
        0, 'I am a description', 'DOKTOL', '2002-02-01', 1);

-- Creación de capacidades.
INSERT INTO capacities(id, state_capacity, less_damage)
VALUES (1, 0, false),
       (2, 1, false),
       (3, 2, false),
       (4, 3, false),
       (5, 0, true),
       (6, 1, true),
       (7, 2, true),
       (8, 3, true);

