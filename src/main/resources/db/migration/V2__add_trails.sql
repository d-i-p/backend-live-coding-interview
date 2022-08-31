INSERT INTO hiking.trail
    (trail_id, name, price, min_age, max_age, start_time, end_time)
VALUES ('ac43d61a-9a62-4151-9448-f4b09dba6b54',
        'Shire',
        29.90,
        5,
        100,
        time '07:00',
        time '09:00') ON CONFLICT (trail_id) DO NOTHING;


INSERT INTO hiking.trail
    (trail_id, name, price, min_age, max_age, start_time, end_time)
VALUES ('c820b2b3-f10a-4ab9-86c0-e32362e2cc1d',
        'Gondor',
        59.90,
        11,
        50,
        time '10:00',
        time '13:00') ON CONFLICT (trail_id) DO NOTHING;

INSERT INTO hiking.trail
    (trail_id, name, price, min_age, max_age, start_time, end_time)
VALUES ('e231de08-d21b-41e3-8ba9-6195beb8676f',
        'Mordor',
        99.90,
        18,
        40,
        time '14:00',
        time '19:00') ON CONFLICT (trail_id) DO NOTHING;
