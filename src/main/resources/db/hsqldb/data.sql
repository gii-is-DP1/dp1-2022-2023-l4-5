-- Creación de usuarios.
INSERT INTO users(username, password, avatar, tier, description, authority, birth_date, enable)
VALUES ('alesanfe', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'I want a nap', 'DOKTOL', '2002-02-01', 1),
       ('antonio', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'I am a description', 'USER', '1999-02-01', 1),
       ('laurolmer', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'awanabumbambam', 'DOKTOL', '2002-08-21', 1),
       ('alvhidrod', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'drakorion', 'USER', '2002-02-23', 1),
       ('ismruijur', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'er jefe brrr', 'USER', '2002-10-27', 1),
       ('ivasansan1', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'bético encubierto', 'DOKTOL', '2002-11-12', 1),
       ('pedruiagu', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'sácame del bolsillo', 'USER', '2002-10-01', 1),
        ('lapaqui', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'vivo en una simulación', 'USER', '2003-05-11', 1),
        ('pepe', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'pepito de carne mechá', 'USER', '2004-07-14', 1);

-- Creación de mensajes.
INSERT INTO messages(content, time, receiver_id, sender_id)
VALUES ('Hola, soy Alesanfe', '2020-02-01 12:00', 1, 2),
       ('Hola, soy Antonio', '2020-02-01 12:00', 2, 1);

-- Creación de capacidades.
INSERT INTO capacities(state_capacity, less_damage)
VALUES ('MELEE', false),
       ('RANGE', false),
       ('MAGIC', false),
       ('EXPERTISE', false),
       ('MELEE', true),
       ('RANGE', true),
       ('MAGIC', true),
       ('EXPERTISE', true);

-- Creación de héroes.
INSERT INTO heroes(name, front_image, back_image, max_uses, health, role)
VALUES ('Valèrys', '/resources/images/heroes/valerys_hero_front.png',
        '/resources/images/heroes/lisavette_hero_back.png', -1, 3, 'KNIGHT'),
       ('Lisavette', '/resources/images/heroes/lisavette_hero_back.png',
        '/resources/images/heroes/valerys_hero_front.png', -1, 3, 'KNIGHT'),
       ('Beleth-Il', '/resources/images/heroes/belethil_hero_front.png',
        '/resources/images/heroes/idril_hero_back.png', -1, 3, 'EXPLORER'),
       ('Idril', '/resources/images/heroes/idril_hero_back.png',
        '/resources/images/heroes/belethil_hero_front.png', -1, 3, 'EXPLORER'),
       ('Neddia', '/resources/images/heroes/neddia_hero_front.png',
        '/resources/images/heroes/feldon_hero_front.png', -1, 2, 'THIEF'),
       ('Feldon', '/resources/images/heroes/feldon_hero_back.png',
        '/resources/images/heroes/neddia_hero_front.png', -1, 2, 'THIEF'),
       ('Aranel', '/resources/images/heroes/aranel_hero_front.png',
        '/resources/images/heroes/taheral_hero_back.png', -1, 2, 'WIZARD'),
       ('Taheral', '/resources/images/heroes/taheral_hero_back.png',
        '/resources/images/heroes/aranel_hero_back.png', -1, 2, 'WIZARD');

-- Creación de habilidades.
INSERT INTO abilities(name, front_image, back_image, max_uses, role, attack, quantity, ability_effect_enum)
VALUES ('Compañero Lobo', '/resources/images/abilities/wolf_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'EXPLORER', 2, 1, 'COMPANERO_LOBO'),
       ('Disparo Certero', '/resources/images/abilities/sharp_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'EXPLORER', 3, 1, 'DISPARO_CERTERO'),
       ('Disparo Rápido', '/resources/images/abilities/rapid_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'EXPLORER', 1, 1, 'DISPARO_RAPIDO'),
       ('En la Diana', '/resources/images/abilities/diana_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'EXPLORER', 4, 1, 'EN_LA_DIANA'),
       ('Lluvia de flechas', '/resources/images/abilities/rain_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'EXPLORER', 2, 1, 'LLUVIA_DE_FLECHAS'),
       ('Recoger flechas', '/resources/images/abilities/pick_abilites_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'EXPLORER', 0, 1, 'RECOGER_FLECHAS'),
       ('Supervivencia', '/resources/images/abilities/survival_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'EXPLORER', 0, 1, 'SUPERVIVENCIA'),
       ('Ataque Brutal', '/resources/images/abilities/brutal_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'KNIGHT', 3, 1, 'ATAQUE_BRUTAL'),
       ('Carga con Escudo', '/resources/images/abilities/charge_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'KNIGHT', 2, 1, 'CARGA_CON_ESCUDO'),
       ('Doble Espadazo', '/resources/images/abilities/doble_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'KNIGHT', 2, 1, 'DOBLE_ESPADAZO'),
       ('Escudo', '/resources/images/abilities/shield_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'KNIGHT', 0, 1, 'ESCUDO'),
       ('Espadazo', '/resources/images/abilities/sword_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'KNIGHT', 1, 1, 'ESPADAZO'),
       ('Paso Atrás', '/resources/images/abilities/back_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'KNIGHT', 0, 1, 'PASO_ATRAS'),
       ('Todo o Nada', '/resources/images/abilities/all_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'KNIGHT', 1, 1, 'TODO_O_NADA'),
       ('Voz de Aliento', '/resources/images/abilities/AAAAAAAA_abilities_front.png',
        '/resources/images/abilities/parte_atras_general.png', -1, 'KNIGHT', 0, 1, 'VOZ_DE_ALIENTO'),
       ('Aura protectora', '/resources/images/abilities/auraprotectora_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'WIZARD', 0, 1, 'AURA_PROTECTORA'),
       ('Bola de fuego', '/resources/images/abilities/boladefuego_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'WIZARD', 2, 1, 'BOLA_DE_FUEGO'),
       ('Disparo gélido', '/resources/images/abilities/disparogelido_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'WIZARD', 1, 2, 'DISPARO_GELIDO'),
       ('Flecha corrosiva', '/resources/images/abilities/flechacorrosiva_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'WIZARD', 1, 1, 'FLECHA_CORROSIVA'),
       ('Golpe de bastón', '/resources/images/abilities/golpebaston_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'WIZARD', 1, 3, 'GOLPE_DE_BASTON'),
       ('Orbe curativo', '/resources/images/abilities/orbecurativo_ability_front.png',
        '/resources/images/abilities/ability_back.png', 1, 'WIZARD', 0, 1, 'ORBE_CURATIVO'),
       ('Proyectil ígneo', '/resources/images/abilities/proyectiligneo_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'WIZARD', 2, 1, 'PROYECTIL_IGNEO'),
       ('Reconstitución', '/resources/images/abilities/reconstitucion_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'WIZARD', 0, 1, 'RECONSTITUCION'),
       ('Torrente de luz', '/resources/images/abilities/torrentedeluz_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'WIZARD', 2, 1, 'TORRENTE_DE_LUZ'),
       ('Al corazón', '/resources/images/abilities/alcorazon_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'THIEF', 4, 1, 'AL_CORAZON'),
       ('Ataque furtivo', '/resources/images/abilities/ataquefurtivo_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'THIEF', 2, 1, 'ATAQUE_FURTIVO'),
       ('Ballesta precisa', '/resources/images/abilities/ballestaprecisa_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'THIEF', 2, 2, 'BALLESTA_PRECISA'),
       ('En las sombras', '/resources/images/abilities/enlassombras_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'THIEF', 1, 1, 'EN_LAS_SOMBRAS'),
       ('Engañar', '/resources/images/abilities/enganar_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'THIEF', 0, 1, 'ENGANAR'),
       ('Robar bolsillo', '/resources/images/abilities/robarbolsillos_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'THIEF', 0, 1, 'ROBAR_BOLSILLOS'),
       ('Saqueo', '/resources/images/abilities/saqueo_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'THIEF', 0, 1, 'SAQUEO_ORO'),
       ('Saqueo2', '/resources/images/abilities/saqueo2_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'THIEF', 0, 1, 'SAQUEO_ORO_GLORIA'),
       ('Trampa', '/resources/images/abilities/trampa_ability_front.png',
        '/resources/images/abilities/ability_back.png', -1, 'THIEF', 0, 1, 'TRAMPA');

-- Creacion de orcos.
INSERT INTO enemies(name, back_image, front_image, max_uses, health, glory, gold, has_cure, less_damage_wizard, is_night_lord)
VALUES ('Honda', '/resources/images/orcs/0-0_orc_back.png',
        '/resources/images/orcs/honda_orc_front.png', -1, 2, 1, 0, FALSE, FALSE, FALSE),
       ('Honda', '/resources/images/orcs/1-0_orc_back.png',
        '/resources/images/orcs/honda_orc_front.png', -1, 2, 1, 1, FALSE, FALSE, FALSE),
       ('Honda', '/resources/images/orcs/1-0_orc_back.png',
        '/resources/images/orcs/honda_orc_front.png', -1, 2, 1, 1, FALSE, FALSE, FALSE),
       ('Honda', '/resources/images/orcs/0-0_orc_back.png',
        '/resources/images/orcs/honda_orc_front.png', -1, 2, 1, 0, FALSE, FALSE, FALSE),
       ('Honda', '/resources/images/orcs/1-0_orc_back.png',
        '/resources/images/orcs/honda_orc_front.png', -1, 2, 1, 1, FALSE, FALSE, FALSE),
       ('Lanza', '/resources/images/orcs/0-0_orc_back.png',
        '/resources/images/orcs/lanza_orc_front.png', -1, 3, 2, 0, TRUE, FALSE, FALSE),
       ('Lanza', '/resources/images/orcs/0-0_orc_back.png',
        '/resources/images/orcs/lanza_orc_front.png', -1, 3, 2, 0, TRUE, FALSE, FALSE),
       ('Lanza', '/resources/images/orcs/0-0_orc_back.png',
        '/resources/images/orcs/lanza_orc_front.png', -1, 3, 2, 0, TRUE, FALSE, FALSE),
       ('Lanza', '/resources/images/orcs/1-0_orc_back.png',
        '/resources/images/orcs/lanza_orc_front.png', -1, 3, 2, 1, TRUE, FALSE, FALSE),
       ('Lanza', '/resources/images/orcs/1-1_orc_back.png',
        '/resources/images/orcs/chaman_orc_front.png', -1, 3, 3, 1, TRUE, FALSE, FALSE),
       ('Chaman', '/resources/images/orcs/2-0_orc_back.png',
        '/resources/images/orcs/chaman_orc_front.png', -1, 3, 1, 2, FALSE, TRUE, FALSE),
       ('Chaman', '/resources/images/orcs/0-0_orc_back.png',
        '/resources/images/orcs/chaman_orc_front.png', -1, 3, 1, 0, FALSE, TRUE, FALSE),
       ('Sword', '/resources/images/orcs/0-0_orc_back.png',
        '/resources/images/orcs/sword_orc_front.png', -1, 4, 2, 0, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/0-0_orc_back.png',
        '/resources/images/orcs/sword_orc_front.png', -1, 4, 2, 0, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/1-0_orc_back.png',
        '/resources/images/orcs/sword_orc_front.png', -1, 4, 2, 1, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/1-0_orc_back.png',
        '/resources/images/orcs/bounty_orc_front.png', -1, 4, 2, 1, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/1-0_orc_back.png',
        '/resources/images/orcs/bounty_orc_front.png', -1, 4, 2, 1, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/2-0_orc_back.png',
        '/resources/images/orcs/bounty_orc_front.png', -1, 4, 2, 2, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/2-1_orc_back.png',
        '/resources/images/orcs/bounty_orc_front.png', -1, 4, 3, 2, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/2-1_orc_back.png',
        '/resources/images/orcs/bounty_orc_front.png', -1, 4, 3, 2, FALSE, FALSE, FALSE),
       ('Gran Chaman', '/resources/images/orcs/0-0_orc_back.png',
        '/resources/images/orcs/greatchaman_orc_front.png', -1, 5, 3, 0, FALSE, TRUE, FALSE),
       ('Gran Chaman', '/resources/images/orcs/2-0_orc_back.png',
        '/resources/images/orcs/greatchaman_orc_front.png', -1, 5, 3, 2, FALSE, TRUE, FALSE),
       ('Gran Chaman', '/resources/images/orcs/2-0_orc_back.png',
        '/resources/images/orcs/greatchaman_orc_front.png', -1, 5, 3, 2, FALSE, TRUE, FALSE),
       ('Gran Chaman', '/resources/images/orcs/2-1_orc_back.png',
        '/resources/images/orcs/greatchaman_orc_front.png', -1, 5, 4, 2, FALSE, TRUE, FALSE),
       ('Terminator', '/resources/images/orcs/1-0_orc_back.png',
        '/resources/images/orcs/axe_orc_front.png', -1, 6, 4, 0, FALSE, FALSE, FALSE),
       ('Terminator', '/resources/images/orcs/1-0_orc_back.png',
        '/resources/images/orcs/axe_orc_front.png', -1, 6, 4, 0, FALSE, FALSE, FALSE),
       ('Terminator', '/resources/images/orcs/0-0_orc_back.png',
        '/resources/images/orcs/axe_orc_front.png', -1, 6, 4, 1, FALSE, FALSE, FALSE),
       ('Gurdrug', '/resources/images/orcs/nightlord_back.png',
        '/resources/images/orcs/gur_nightlord_front.png',-1, 8, null, null, FALSE, FALSE, TRUE),
       ('Roghkiller', '/resources/images/orcs/nightlord_back.png',
        '/resources/images/orcs/rogh_nightlord_front.png', -1, 9, null, null, FALSE, FALSE, TRUE),
       ('Shriekknifer', '/resources/images/orcs/nightlord_back.png',
        '/resources/images/orcs/shriek_nightlord_front.png', -1, 10, null, null, FALSE, FALSE, TRUE);

-- Productos
INSERT INTO products(name, front_image, back_image, max_uses, price, attack, quantity, ability_effect_enum)
VALUES ('Daga élfica', '/resources/images/products/dagaelfica_product_front.png',
        '/resources/images/products/product_back.png', -1, 3, 2, 2, 'DAGA_ELFICA'),
       ('Poción curativa', '/resources/images/products/pocioncurativa_product_front.png',
        '/resources/images/products/product_back.png', 1, 8, 0, 3, 'POCION_CURATIVA'),
       ('Piedra de amolar', '/resources/images/products/piedraamolar_product_front.png',
        '/resources/images/products/product_back.png', -1, 4, 0, 1, 'PIEDRA_DE_AMOLAR'),
       ('Vial de conjuración',
        '/resources/images/products/vialconjuracion_product_front.png',
        '/resources/images/products/product_back.png', -1, 5, 0, 2, 'VIAL_DE_CONJURACION'),
       ('Elixir de concentración',
        '/resources/images/products/pocioncurativa_product_front.png',
        '/resources/images/products/product_back.png', -1, 3, 0, 2, 'ELIXIR_DE_CONCENTRACION'),
       ('Capa élfica', '/resources/images/products/capaelfica_product_front.png',
        '/resources/images/products/product_back.png', -1, 3, 0, 1, 'CAPA_ELFICA'),
       ('Armadura de placas',
        '/resources/images/products/armaduraplacas_product_front.png',
        '/resources/images/products/product_back.png', -1, 4, 0, 1, 'ARMADURA_DE_PLACAS'),
       ('Alabarda orca', '/resources/images/products/alabardaorca_product_front.png',
        '/resources/images/products/product_back.png', -1, 5, 4, 1, 'ALABARDA_ORCA'),
       ('Arco compuesto', '/resources/images/products/arcocompuesto_product_front.png',
        '/resources/images/products/product_back.png', -1, 5, 4, 1, 'ARCO_COMPUESTO');


-- Nuevo

-- Creación de la relación entre capacidades y productos.
INSERT INTO products_capacity(product_id, capacity_id)
VALUES (3, 1),
       (3, 2),
       (3, 4),
       (6, 3),
       (6, 4),
       (7, 2),
       (8, 2),
       (9, 4);

-- Creación de logros.
INSERT INTO achievements(name, description, image, threshold, type)
VALUES ('Primera partida', 'Se le otorga un logro al usuario cuando completa su primera partida. ', 'awanakimkum', '1','numPlayedGames'),
       ('Primera victoria', 'Se le otorga un logro al usuario cuando gana su primera partida. ', 'awanakimkum', '1','numWonGames'),
       ('Última Sangre', 'Se le otorga un logro al usuario cuando gana su primera partida.', 'awanakimkum', '1','numWonGames'),
       ('Aficionado', 'Se le otorga un logro al usuario cuando mata a 10 orcos. ', 'awanakimkum', '10','numOrcsKilled'),
       ('Veterano', 'Se le otorga un logro al usuario cuando mata a 50 orcos. ', 'awanakimkum', '50','numOrcsKilled'),
       ('Maestro', 'Se le otorga un logro al usuario cuando mata a 100 orcos. ', 'awanakimkum', '100','numOrcsKilled'),
       ('Exterminador', 'Se le otorga un logro al usuario cuando mata a 200 orcos. ', 'awanakimkum', '200','numOrcsKilled'),
       ('Asesino', 'Se le otorga un logro al usuario cuando mata a 500 orcos. ', 'awanakimkum', '500','numOrcsKilled'),
       ('Mataorcos', 'Se le otorga un logro al usuario cuando mata a 1000 orcos. ', 'awanakimkum', '1000','numOrcsKilled'),
       ('Primeros pasos', 'Se le otorga un logro al usuario cuando compra su primer producto. ', 'awanakimkum', '1','purchasedProduct'),
       ('Comprador', 'Se le otorga un logro al usuario cuando compra 10 productos. ', 'awanakimkum', '10','purchasedProduct'),
       ('Comprador experto', 'Se le otorga un logro al usuario cuando compra 50 productos. ', 'awanakimkum', '50','purchasedProduct'),
       ('Comprador maestro', 'Se le otorga un logro al usuario cuando compra 100 productos. ', 'awanakimkum', '100','purchasedProduct'),
       ('Touch Some Grass', 'Se le otorga un logro al usuario cuando juega 20 partidas.', 'awanakimkum', '20','numPlayedGames'),
       ('Un poco de todo', 'Se le otorga un logro al usuario cuando completa su primera partida en modo multiclase.',
        'awanakimkum', '1',);

-- Habilidades de los heroes
INSERT INTO heroes_abilities(hero_id, abilities_id)
VALUES (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (2, 8),
       (2, 9),
       (2, 10),
       (2, 11),
       (2, 12),
       (2, 13),
       (2, 14),
       (2, 15),
       (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (3, 5),
       (3, 6),
       (3, 7),
       (4, 1),
       (4, 2),
       (4, 3),
       (4, 4),
       (4, 5),
       (4, 6),
       (4, 7),
       (5, 16),
       (5, 17),
       (5, 18),
       (5, 19),
       (5, 20),
       (5, 21),
       (5, 22),
       (5, 23),
       (5, 24),
       (6, 16),
       (6, 17),
       (6, 18),
       (6, 19),
       (6, 20),
       (6, 21),
       (6, 22),
       (6, 23),
       (6, 24),
       (7, 25),
       (7, 26),
       (7, 27),
       (7, 28),
       (7, 29),
       (7, 30),
       (7, 31),
       (7, 32),
       (8, 25),
       (8, 26),
       (8, 27),
       (8, 28),
       (8, 29),
       (8, 30),
       (8, 31),
       (8, 32);

-- Capacidades de los heroes
INSERT INTO heroes_capacities(hero_id, capacities_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (3, 5),
       (4, 2),
       (4, 5),
       (5, 4),
       (5, 6),
       (6, 4),
       (6, 6),
       (7, 3),
       (8, 3);

INSERT INTO users_friends(user_id, friends_id)
VALUES (1, 2),
       (2, 1);

