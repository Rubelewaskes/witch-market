INSERT INTO pipeline_definitions (id, name, requires_handoff) VALUES
(0, 'Фамильяр-наставник',true),
(1, 'Часы судьбы', true),
(2, 'Перевод древней надписи', false),
(3, 'Уход за лунным зайцем', false),
(4, 'Магическая заточка', false);

INSERT INTO pipeline_step_definitions (pipeline_id, step_number, specialization, task_type, ingredients, requirements) VALUES
(0, 0, 'CHIMEROLOGIST', 'GATHER_FAMILIAR_ESSENCE', '[{"item": "Духовная пыль", "quantity": 3}]', '[{"needs_night": true}]'),
(0, 1, 'SCHOLAR', 'WRITE_FAMILIAR_TOME', '[{"item": "Пергамент", "quantity": 5}]', '[{"language": "Древний"}]'),
(0, 2, 'POTION', 'BREW_LINK_POTION', '[{"item": "Слизь маны", "quantity": 2}, {"item": "Серебряный лист", "quantity": 1}]', '[]');

INSERT INTO pipeline_step_definitions (pipeline_id, step_number, specialization, task_type, ingredients, requirements) VALUES
(1, 0, 'ARTIFACT', 'FORGE_CLOCK_CASING', '[{"item": "Эфирный металл", "quantity": 4}]', '[]'),
(1, 1, 'SCHOLAR', 'INSCRIBE_RUNES', '[{"item": "Руническое перо", "quantity": 1}]', '[{"alignment": "Хаос"}]'),
(1, 2, 'POTION', 'IMBUE_WITH_TIME', '[{"item": "Пыль времени", "quantity": 2}]', '[{"phase": "Полнолуние"}]'),
(1, 3, 'CHIMEROLOGIST', 'BIND_FATE_SPIRIT', '[{"item": "Кристалл судьбы", "quantity": 1}]', '[{"ritual_circle": true}]');

INSERT INTO pipeline_step_definitions (pipeline_id, step_number, specialization, task_type, ingredients, requirements) VALUES
(2, 0, 'SCHOLAR', 'TRANSLATE_TEXT', '[{"item": "Свиток с надписью", "quantity": 1}]', '[{"language": "Неизвестный"}]');

INSERT INTO pipeline_step_definitions (pipeline_id, step_number, specialization, task_type, ingredients, requirements) VALUES
(3, 0, 'CHIMEROLOGIST', 'FEED_LUNAR_BUNNY', '[{"item": "Лунная морковь", "quantity": 2}]', '[{"time": "Ночь"}]');

INSERT INTO pipeline_step_definitions (pipeline_id, step_number, specialization, task_type, ingredients, requirements) VALUES
(4, 0, 'ARTIFACT', 'SHARPE_TOOL', '[{"item": "Инструмент", "quantity": 1}]', '[]');