-- Creación de usuarios.
INSERT INTO users(username, password, avatar, tier, description, authority, birth_date, enable)
VALUES ('alesanfe', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'I am a description', 'DOKTOL', '2002-02-01', 1),
       ('antonio', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'I am a description', 'DOKTOL', '2002-02-01', 1),
       ('laurolmer', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'awanabumbambam', 'DOKTOL', '2002-08-21', 1),
       ('alvhidrod', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'drakorion', 'DOKTOL', '2002-02-23', 1),
       ('ismruijur', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'er jefe brrr', 'DOKTOL', '2002-10-27', 1),
       ('ivasansan1', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'bético encubierto', 'DOKTOL', '2002-11-12', 1),
       ('pedruiagu', 'patata', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'sácame del bolsillo', 'DOKTOL', '2002-10-01', 1);

INSERT INTO message(id, content, time, receiver_id, sender_id)
VALUES (1, 'Hola, soy Alesanfe', '2020-02-01 12:00:00', 1, 2),
       (2, 'Hola, soy Antonio', '2020-02-01 12:00:00', 2, 1);

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

-- Héroes
INSERT INTO heroes(id, name, frontImage, backImage, timesUsed, aux, health, role, deck, hand, discard, enemies, capacities)
VALUES (1, 'Valèrys', 'src/main/resources/static/resources/images/heroes/valerys_hero_front.png', 'src/main/resources/static/resources/images/heroes/lisavette_hero_back.png', 0, 0, 3, KNIGHT, ?, ?, ?, ?, MELEE),
       (2, 'Lisavette', 'src/main/resources/static/resources/images/heroes/lisavette_hero_back.png', 'src/main/resources/static/resources/images/heroes/valerys_hero_front.png', 0, 0, 3, KNIGHT, ?, ?, ?, ?, MELEE),
       (3, 'Beleth-Il', 'src/main/resources/static/resources/images/heroes/belethil_hero_front.png', 'src/main/resources/static/resources/images/heroes/idril_hero_back.png', 0, 0, 3, EXPLORER, ?, ?, ?, ?, RANGED, MELEE),
       (4, 'Idril', 'src/main/resources/static/resources/images/heroes/idril_hero_back.png', 'src/main/resources/static/resources/images/heroes/belethil_hero_front.png', 0, 0, 3, EXPLORER, ?, ?, ?, ?, [RANGED, MELEE]),
       (5, 'Neddia', 'src/main/resources/static/resources/images/heroes/neddia_hero_back.png', 'src/main/resources/static/resources/images/heroes/feldon_hero_front.png', 0, 0, 2, THIEF, ?, ?, ?, ?, [EXPERTISE, RANGED]),
       (4, 'Feldon', 'src/main/resources/static/resources/images/heroes/feldon_hero_back.png', 'src/main/resources/static/resources/images/heroes/neddia_hero_front.png', 0, 0, 2, THIEF, ?, ?, ?, ?, [EXPERTISE, RANGED]),
       (4, 'Aranel', 'src/main/resources/static/resources/images/heroes/aranel_hero_back.png', 'src/main/resources/static/resources/images/heroes/taheral_hero_front.png', 0, 0, 2, WIZARD, ?, ?, ?, ?, [MAGIC]),
       (4, 'Taheral', 'src/main/resources/static/resources/images/heroes/taheral_hero_back.png', 'src/main/resources/static/resources/images/heroes/aranel_hero_front.png', 0, 0, 2, WIZARD, ?, ?, ?, ?, [MAGIC]);

-- Productos
INSERT INTO products(id, name, frontImage, backImage, timesUsed, aux, price, attack, ability, capacity)
VALUES (1, 'Daga élfica', 'src/main/resources/static/resources/images/products/dagaelfica_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 3, 2, ?, [RANGED, MELEE, EXPERTISE, MAGIC]),
       (2, 'Daga élfica', 'src/main/resources/static/resources/images/products/dagaelfica_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 3, 2, ?, [RANGED, MELEE, EXPERTISE, MAGIC]),
       (3, 'Poción curativa', 'src/main/resources/static/resources/images/products/pocioncurativa_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 8, 0, ?, [RANGED, MELEE, EXPERTISE, MAGIC]),
       (4, 'Poción curativa', 'src/main/resources/static/resources/images/products/pocioncurativa_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 8, 0, ?, [RANGED, MELEE, EXPERTISE, MAGIC]),
       (5, 'Poción curativa', 'src/main/resources/static/resources/images/products/pocioncurativa_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 8, 0, ?, [RANGED, MELEE, EXPERTISE, MAGIC]),
       (6, 'Piedra de amolar', 'src/main/resources/static/resources/images/products/piedraamolar_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 4, 0, ?, [RANGED, MELEE, EXPERTISE]),
       (7, 'Vial de conjuración', 'src/main/resources/static/resources/images/products/vialconjuracion_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 5, 0, ?, [RANGED, MELEE, EXPERTISE, MAGIC]),
       (8, 'Vial de conjuración', 'src/main/resources/static/resources/images/products/vialconjuracion_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 5, 0, ?, [RANGED, MELEE, EXPERTISE, MAGIC]),
       (9, 'Elixir de concentración', 'src/main/resources/static/resources/images/products/pocioncurativa_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 3, 0, ?, [RANGED, MELEE, EXPERTISE, MAGIC]),
       (10, 'Elixir de concentración', 'src/main/resources/static/resources/images/products/pocioncurativa_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 3, 0, ?, [RANGED, MELEE, EXPERTISE, MAGIC]),
       (11, 'Capa élfica', 'src/main/resources/static/resources/images/products/capaelfica_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 3, 0, ?, [RANGED, MAGIC]),
       (12, 'Armadura de placas', 'src/main/resources/static/resources/images/products/armaduraplacas_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 4, 0, ?, [MELEE]),
       (13, 'Alabarda orca', 'src/main/resources/static/resources/images/products/alabardaorca_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 5, 4, ?, [MELEE]),
       (14, 'Arco compuesto', 'src/main/resources/static/resources/images/products/arcocompuesto_product_front.png', 'src/main/resources/static/resources/images/products/product_back.png', 0, 0, 5, 4, ?, [RANGED]);

-- Ability
INSERT INTO abilities(id, name, frontImage, backImage, timesUsed, aux, role, attack)
VALUES (1, 'Aura protectora', 'src/main/resources/static/resources/images/abilities/auraprotectora_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 0),
       (2, 'Bola de fuego', 'src/main/resources/static/resources/images/abilities/boladefuego_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 2),
       (3, 'Disparo gélido', 'src/main/resources/static/resources/images/abilities/disparogelido_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 1),
       (4, 'Disparo gélido', 'src/main/resources/static/resources/images/abilities/disparogelido_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 1),
       (5, 'Flecha corrosiva', 'src/main/resources/static/resources/images/abilities/flechacorrosiva_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 1),
       (6, 'Golpe de bastón', 'src/main/resources/static/resources/images/abilities/golpebaston_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 1),
       (7, 'Golpe de bastón', 'src/main/resources/static/resources/images/abilities/golpebaston_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 1),
       (8, 'Golpe de bastón', 'src/main/resources/static/resources/images/abilities/golpebaston_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 1),
       (9, 'Golpe de bastón', 'src/main/resources/static/resources/images/abilities/golpebaston_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 1),
       (10, 'Orbe curativo', 'src/main/resources/static/resources/images/abilities/orbecurativo_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 0),
       (11, 'Proyectil ígneo', 'src/main/resources/static/resources/images/abilities/proyectiligneo_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 2),
       (12, 'Proyectil ígneo', 'src/main/resources/static/resources/images/abilities/proyectiligneo_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 2),
       (13, 'Proyectil ígneo', 'src/main/resources/static/resources/images/abilities/proyectiligneo_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 2),
       (14, 'Reconstitución', 'src/main/resources/static/resources/images/abilities/reconstitucion_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 0),
       (15, 'Torrente de luz', 'src/main/resources/static/resources/images/abilities/torrentedeluz_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, WIZARD, 2),
       (16, 'Al corazón', 'src/main/resources/static/resources/images/abilities/alcorazon_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 4),
       (17, 'Al corazón', 'src/main/resources/static/resources/images/abilities/alcorazon_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 4),
       (18, 'Ataque furtivo', 'src/main/resources/static/resources/images/abilities/ataquefurtivo_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 2),
       (19, 'Ataque furtivo', 'src/main/resources/static/resources/images/abilities/ataquefurtivo_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 2),
       (20, 'Ataque furtivo', 'src/main/resources/static/resources/images/abilities/ataquefurtivo_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 2),
       (21, 'Ballesta precisa', 'src/main/resources/static/resources/images/abilities/ballestaprecisa_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 2),
       (22, 'Ballesta precisa', 'src/main/resources/static/resources/images/abilities/ballestaprecisa_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 2),
       (23, 'Ballesta precisa', 'src/main/resources/static/resources/images/abilities/ballestaprecisa_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 2),
       (24, 'En las sombras', 'src/main/resources/static/resources/images/abilities/enlassombras_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 1),
       (25, 'En las sombras', 'src/main/resources/static/resources/images/abilities/enlassombras_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 1),
       (26, 'Engañar', 'src/main/resources/static/resources/images/abilities/engañar_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 0),
       (27, 'Robar bolsillo', 'src/main/resources/static/resources/images/abilities/robarbolsillos_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 0),
       (28, 'Saqueo', 'src/main/resources/static/resources/images/abilities/saqueo_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 0),
       (29, 'Saqueo', 'src/main/resources/static/resources/images/abilities/saqueo_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 0),
       (30, 'Trampa', 'src/main/resources/static/resources/images/abilities/trampa_ability_front.png', 'src/main/resources/static/resources/images/abilities/ability_back.png', 0, 1, THIEF, 0);

