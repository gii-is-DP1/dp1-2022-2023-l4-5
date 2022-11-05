-- Creación de usuarios.
INSERT INTO users(username, password, avatar, tier, description, authority, birth_date, enable)
VALUES ('alesanfe', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'I am a description', 'DOKTOL', '2002-02-01', 1),
       ('antonio', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'I am a description', 'DOKTOL', '2002-02-01', 1);

INSERT INTO message(id, content, time, receiver_id, sender_id)
VALUES (1, 'Hola, soy Alesanfe', '2020-02-01 12:00:00', 1, 2),
       (2, 'Hola, soy Antonio', '2020-02-01 12:00:00', 2, 1);
-- Creación de capacidades.
INSERT INTO capacities(id, state_capacity, less_damage)
VALUES (1, 0, false), (2, 1, false), (3, 2, false), (4, 3, false), (5, 0, true), (6, 1, true), (7, 2, true), (8, 3, true);
INSERT INTO abilities(id,name,backimage,frontimage,timesused,role,attack)
VALUES
(1,'Compañero Lobo','src/main/resources/static/resources/images/juego/wolf_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,2),
(2,'Disparo Certero','src/main/resources/static/resources/images/juego/sharp_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,3),
(3,'Disparo Certero','src/main/resources/static/resources/images/juego/sharp_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,3),
(4,'Disparo Rápido','src/main/resources/static/resources/images/juego/rapid_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,1),
(5,'Disparo Rápido','src/main/resources/static/resources/images/juego/rapid_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,1),
(6,'Disparo Rápido','src/main/resources/static/resources/images/juego/rapid_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,1),
(7,'Disparo Rápido','src/main/resources/static/resources/images/juego/rapid_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,1),
(8,'Disparo Rápido','src/main/resources/static/resources/images/juego/rapid_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,1),
(9,'Disparo Rápido','src/main/resources/static/resources/images/juego/rapid_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,1),
(10,'En la Diana','src/main/resources/static/resources/images/juego/diana_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,4),
(11,'Lluvia de flechas','src/main/resources/static/resources/images/juego/rain_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,2),
(12,'Lluvia de flechas','src/main/resources/static/resources/images/juego/rain_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,2),
(13,'Recoger flechas','src/main/resources/static/resources/images/juego/pick_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,0),
(14,'Recoger flechas','src/main/resources/static/resources/images/juego/pick_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,0),
(15,'Supervivencia','src/main/resources/static/resources/images/juego/survival_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,0),
(16,'Ataque Brutal','src/main/resources/static/resources/images/juego/brutal_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,3),
(17,'Ataque Brutal','src/main/resources/static/resources/images/juego/brutal_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,3),
(18,'Carga con Escudo','src/main/resources/static/resources/images/juego/charge_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,2),
(19,'Doble Espadazo','src/main/resources/static/resources/images/juego/doble_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,2),
(20,'Doble Espadazo','src/main/resources/static/resources/images/juego/doble_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,2),
(21,'Escudo','src/main/resources/static/resources/images/juego/shield_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,0),
(22,'Escudo','src/main/resources/static/resources/images/juego/shield_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,0),
(23,'Espadazo','src/main/resources/static/resources/images/juego/sword_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,1),
(24,'Espadazo','src/main/resources/static/resources/images/juego/sword_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,1),
(25,'Espadazo','src/main/resources/static/resources/images/juego/sword_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,1),
(26,'Espadazo','src/main/resources/static/resources/images/juego/sword_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,1),
(27,'Paso Atrás','src/main/resources/static/resources/images/juego/back_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,0),
(28,'Paso Atrás','src/main/resources/static/resources/images/juego/back_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,0),
(29,'Todo o Nada','src/main/resources/static/resources/images/juego/all_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,1),
(30,'Voz de Aliento','src/main/resources/static/resources/images/juego/AAAAAAAA_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,0);











INSERT INTO orc(id,name,backimage,frontimage,timesused,health,attack,heroes,glory,gold,hascure,lessdamagewizard)
INSERT INTO night_lords(id,name,backimage,frontimage,timesused,health,attack,heroes)

