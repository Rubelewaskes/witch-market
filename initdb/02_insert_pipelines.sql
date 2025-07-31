INSERT INTO pipeline_definitions (name, steps, specializations, requires_handoff) VALUES
('Фамильяр-наставник', 3, ARRAY['CHIMEROLOGIST', 'SCHOLAR', 'POTION_MASTER']::specialization[], true),
('Часы судьбы', 4, ARRAY['ARTIFACTOR', 'SCHOLAR', 'POTION_MASTER', 'CHIMEROLOGIST']::specialization[], true),
('Перевод древней надписи', 1, ARRAY['SCHOLAR']::specialization[], false),
('Уход за лунным зайцем', 1, ARRAY['CHIMEROLOGIST']::specialization[], false);