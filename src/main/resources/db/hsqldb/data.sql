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

INSERT INTO abilities(id,name,frontimage,backimage,timesused,role,attack)
VALUES
(31,'Compañero Lobo','src/main/resources/static/resources/images/juego/wolf_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,2),
(32,'Disparo Certero','src/main/resources/static/resources/images/juego/sharp_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,3),
(33,'Disparo Certero','src/main/resources/static/resources/images/juego/sharp_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,3),
(34,'Disparo Rápido','src/main/resources/static/resources/images/juego/rapid_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,1),
(35,'Disparo Rápido','src/main/resources/static/resources/images/juego/rapid_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,1),
(36,'Disparo Rápido','src/main/resources/static/resources/images/juego/rapid_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,1),
(37,'Disparo Rápido','src/main/resources/static/resources/images/juego/rapid_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,1),
(38,'Disparo Rápido','src/main/resources/static/resources/images/juego/rapid_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,1),
(39,'Disparo Rápido','src/main/resources/static/resources/images/juego/rapid_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,1),
(40,'En la Diana','src/main/resources/static/resources/images/juego/diana_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,4),
(41,'Lluvia de flechas','src/main/resources/static/resources/images/juego/rain_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,2),
(42,'Lluvia de flechas','src/main/resources/static/resources/images/juego/rain_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,2),
(43,'Recoger flechas','src/main/resources/static/resources/images/juego/pick_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,0),
(44,'Recoger flechas','src/main/resources/static/resources/images/juego/pick_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,0),
(45,'Supervivencia','src/main/resources/static/resources/images/juego/survival_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, EXPLORER,0),
(46,'Ataque Brutal','src/main/resources/static/resources/images/juego/brutal_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,3),
(47,'Ataque Brutal','src/main/resources/static/resources/images/juego/brutal_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,3),
(48,'Carga con Escudo','src/main/resources/static/resources/images/juego/charge_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,2),
(49,'Doble Espadazo','src/main/resources/static/resources/images/juego/doble_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,2),
(50,'Doble Espadazo','src/main/resources/static/resources/images/juego/doble_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,2),
(51,'Escudo','src/main/resources/static/resources/images/juego/shield_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,0),
(52,'Escudo','src/main/resources/static/resources/images/juego/shield_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,0),
(53,'Espadazo','src/main/resources/static/resources/images/juego/sword_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,1),
(54,'Espadazo','src/main/resources/static/resources/images/juego/sword_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,1),
(55,'Espadazo','src/main/resources/static/resources/images/juego/sword_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,1),
(56,'Espadazo','src/main/resources/static/resources/images/juego/sword_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,1),
(57,'Paso Atrás','src/main/resources/static/resources/images/juego/back_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,0),
(58,'Paso Atrás','src/main/resources/static/resources/images/juego/back_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,0),
(59,'Todo o Nada','src/main/resources/static/resources/images/juego/all_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,1),
(60,'Voz de Aliento','src/main/resources/static/resources/images/juego/AAAAAAAA_abilities_front.png','src/main/resources/static/resources/images/juego/parte_atras_general.png',0, KNIGHT,0);

INSERT INTO orc(id,name,backimage,frontimage,timesused,health,heroes,glory,gold,hascure,lessdamagewizard) VALUES
(1,'Honda','src/main/resources/static/resources/images/juego/0-0_orc_back.png','src/main/resources/static/resources/images/juego/honda_orc_front.png',0,2,,1,0,FALSE,FALSE),
(2,'Honda','src/main/resources/static/resources/images/juego/1-0_orc_back.png','src/main/resources/static/resources/images/juego/honda_orc_front.png',0,2,,1,1,FALSE,FALSE),
(3,'Honda','src/main/resources/static/resources/images/juego/1-0_orc_back.png','src/main/resources/static/resources/images/juego/honda_orc_front.png',0,2,,1,1,FALSE,FALSE),
(4,'Honda','src/main/resources/static/resources/images/juego/0-0_orc_back.png','src/main/resources/static/resources/images/juego/honda_orc_front.png',0,2,,1,0,FALSE,FALSE),
(5,'Honda','src/main/resources/static/resources/images/juego/1-0_orc_back.png','src/main/resources/static/resources/images/juego/honda_orc_front.png',0,2,,1,1,FALSE,FALSE),
(6,'Lanza','src/main/resources/static/resources/images/juego/0-0_orc_back.png','src/main/resources/static/resources/images/juego/lanza_orc_front.png',0,3,,2,0,TRUE,FALSE),
(7,'Lanza','src/main/resources/static/resources/images/juego/0-0_orc_back.png','src/main/resources/static/resources/images/juego/lanza_orc_front.png',0,3,,2,0,TRUE,FALSE),
(8,'Lanza','src/main/resources/static/resources/images/juego/0-0_orc_back.png','src/main/resources/static/resources/images/juego/lanza_orc_front.png',0,3,,2,0,TRUE,FALSE),
(9,'Lanza','src/main/resources/static/resources/images/juego/1-0_orc_back.png','src/main/resources/static/resources/images/juego/lanza_orc_front.png',0,3,,2,1,TRUE,FALSE),
(10,'Lanza','src/main/resources/static/resources/images/juego/1-1_orc_back.png','src/main/resources/static/resources/images/juego/chaman_orc_front.png',0,3,,3,1,TRUE,FALSE),
(11,'Chaman','src/main/resources/static/resources/images/juego/2-0_orc_back.png','src/main/resources/static/resources/images/juego/chaman_orc_front.png',0,3,,1,2,FALSE,TRUE),
(12,'Chaman','src/main/resources/static/resources/images/juego/0-0_orc_back.png','src/main/resources/static/resources/images/juego/chaman_orc_front.png',0,3,,1,0,FALSE,TRUE),
(13,'Sword','src/main/resources/static/resources/images/juego/0-0_orc_back.png','src/main/resources/static/resources/images/juego/sword_orc_front.png',0,4,,2,0,FALSE,FALSE),
(14,'Sword','src/main/resources/static/resources/images/juego/0-0_orc_back.png','src/main/resources/static/resources/images/juego/sword_orc_front.png',0,4,,2,0,FALSE,FALSE),
(15,'Sword','src/main/resources/static/resources/images/juego/1-0_orc_back.png','src/main/resources/static/resources/images/juego/sword_orc_front.png',0,4,,2,1,FALSE,FALSE),
(16,'Sword','src/main/resources/static/resources/images/juego/1-0_orc_back.png','src/main/resources/static/resources/images/juego/bounty_orc_front.png',0,4,,2,1,FALSE,FALSE),
(17,'Sword','src/main/resources/static/resources/images/juego/1-0_orc_back.png','src/main/resources/static/resources/images/juego/bounty_orc_front.png',0,4,,2,1,FALSE,FALSE),
(18,'Sword','src/main/resources/static/resources/images/juego/2-0_orc_back.png','src/main/resources/static/resources/images/juego/bounty_orc_front.png',0,4,,2,2,FALSE,FALSE),
(19,'Sword','src/main/resources/static/resources/images/juego/2-1_orc_back.png','src/main/resources/static/resources/images/juego/bounty_orc_front.png',0,4,,3,2,FALSE,FALSE),
(20,'Sword','src/main/resources/static/resources/images/juego/2-1_orc_back.png','src/main/resources/static/resources/images/juego/bounty_orc_front.png',0,4,,3,2,FALSE,FALSE),
(21,'Gran Chaman','src/main/resources/static/resources/images/juego/0-0_orc_back.png','src/main/resources/static/resources/images/juego/greatchaman_orc_front.png',0,5,,3,0,FALSE,TRUE),
(22,'Gran Chaman','src/main/resources/static/resources/images/juego/2-0_orc_back.png','src/main/resources/static/resources/images/juego/greatchaman_orc_front.png',0,5,,3,2,FALSE,TRUE),
(23,'Gran Chaman','src/main/resources/static/resources/images/juego/2-0_orc_back.png','src/main/resources/static/resources/images/juego/greatchaman_orc_front.png',0,5,,3,2,FALSE,TRUE),
(24,'Gran Chaman','src/main/resources/static/resources/images/juego/2-1_orc_back.png','src/main/resources/static/resources/images/juego/greatchaman_orc_front.png',0,5,,4,2,FALSE,TRUE),
(25,'Terminator','src/main/resources/static/resources/images/juego/1-0_orc_back.png','src/main/resources/static/resources/images/juego/axe_orc_front.png',0,6,,4,0,FALSE,FALSE),
(26,'Terminator','src/main/resources/static/resources/images/juego/1-0_orc_back.png','src/main/resources/static/resources/images/juego/axe_orc_front.png',0,6,,4,0,FALSE,FALSE),
(27,'Terminator','src/main/resources/static/resources/images/juego/0-0_orc_back.png','src/main/resources/static/resources/images/juego/axe_orc_front.png',0,6,,4,1,FALSE,FALSE),

INSERT INTO night_lord(id,name,backimage,frontimage,timesused,health,heroes)
VALUES
(1,'Gurdrug','src/main/resources/static/resources/images/juego/nightlord_back.png','src/main/resources/static/resources/images/juego/gur_nightlord_front.png',0,8,),
(2,'Roghkiller','src/main/resources/static/resources/images/juego/nightlord_back.png','src/main/resources/static/resources/images/juego/rogh_nightlord_front.png',0,9,),
(3,'Shriekknifer','src/main/resources/static/resources/images/juego/nightlord_back.png','src/main/resources/static/resources/images/juego/shriek_nightlord_front.png',0,10,);



