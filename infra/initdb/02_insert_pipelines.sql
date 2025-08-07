INSERT INTO pipeline_definitions (id, name, requires_handoff) VALUES
('11111111-1111-1111-1111-111111111111', 'Фамильяр-наставник',true),
('22222222-2222-2222-2222-222222222222', 'Часы судьбы', true),
('33333333-3333-3333-3333-333333333333', 'Перевод древней надписи', false),
('44444444-4444-4444-4444-444444444444', 'Уход за лунным зайцем', false);

INSERT INTO pipeline_step_definitions (pipeline_id, step_id, specialization, task_type, ingredients, requirements) VALUES
('11111111-1111-1111-1111-111111111111', 1, 'CHIMEROLOGIST', 'GATHER_FAMILIAR_ESSENCE', '[{"item": "Духовная пыль", "quantity": 3}]', '{"needs_night": true}'),
('11111111-1111-1111-1111-111111111111', 2, 'SCHOLAR', 'WRITE_FAMILIAR_TOME', '[{"item": "Пергамент", "quantity": 5}]', '{"language": "Древний"}'),
('11111111-1111-1111-1111-111111111111', 3, 'POTION_MASTER', 'BREW_LINK_POTION', '[{"item": "Слизь маны", "quantity": 2}, {"item": "Серебряный лист", "quantity": 1}]', '{}');

INSERT INTO pipeline_step_definitions (pipeline_id, step_id, specialization, task_type, ingredients, requirements) VALUES
('22222222-2222-2222-2222-222222222222', 1, 'ARTIFACTOR', 'FORGE_CLOCK_CASING', '[{"item": "Эфирный металл", "quantity": 4}]', '{}'),
('22222222-2222-2222-2222-222222222222', 2, 'SCHOLAR', 'INSCRIBE_RUNES', '[{"item": "Руническое перо", "quantity": 1}]', '{"alignment": "Хаос"}'),
('22222222-2222-2222-2222-222222222222', 3, 'POTION_MASTER', 'IMBUE_WITH_TIME', '[{"item": "Пыль времени", "quantity": 2}]', '{"phase": "Полнолуние"}'),
('22222222-2222-2222-2222-222222222222', 4, 'CHIMEROLOGIST', 'BIND_FATE_SPIRIT', '[{"item": "Кристалл судьбы", "quantity": 1}]', '{"ritual_circle": true}');

INSERT INTO pipeline_step_definitions (pipeline_id, step_id, specialization, task_type, ingredients, requirements) VALUES
('33333333-3333-3333-3333-333333333333', 1, 'SCHOLAR', 'TRANSLATE_TEXT', '[{"item": "Свиток с надписью", "quantity": 1}]', '{"language": "Неизвестный"}');

INSERT INTO pipeline_step_definitions (pipeline_id, step_id, specialization, task_type, ingredients, requirements) VALUES
('44444444-4444-4444-4444-444444444444', 1, 'CHIMEROLOGIST', 'FEED_LUNAR_BUNNY', '[{"item": "Лунная морковь", "quantity": 2}]', '{"time": "Ночь"}');
