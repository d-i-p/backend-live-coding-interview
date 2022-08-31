CREATE SCHEMA IF NOT EXISTS hiking
    AUTHORIZATION postgres;


-- Table: hiking.trail

CREATE TABLE IF NOT EXISTS hiking.trail
(
    trail_id   uuid NOT NULL,
    end_time   time without time zone,
    max_age    integer,
    min_age    integer,
    name       character varying(255) COLLATE pg_catalog."default",
    price      double precision,
    start_time time without time zone,
    CONSTRAINT trail_pkey PRIMARY KEY (trail_id)
) TABLESPACE pg_default;

ALTER TABLE hiking.trail
    OWNER to postgres;

-- Table: hiking.hiker

CREATE TABLE IF NOT EXISTS hiking.hiker
(
    hiker_id uuid NOT NULL,
    age      integer,
    mail     character varying(255) COLLATE pg_catalog."default",
    name     character varying(255) COLLATE pg_catalog."default",
    surname  character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT hiker_pkey PRIMARY KEY (hiker_id)
) TABLESPACE pg_default;

ALTER TABLE hiking.hiker
    OWNER to postgres;


-- Table: hiking.booking

CREATE TABLE IF NOT EXISTS hiking.booking
(
    booking_id           uuid NOT NULL,
    booking_date         date,
    reserved_by_hiker_id uuid,
    trail_id             uuid,
    CONSTRAINT booking_pkey PRIMARY KEY (booking_id),
    CONSTRAINT fkqc275ha8wkxqi4bc5ueekx8pu FOREIGN KEY (trail_id)
        REFERENCES hiking.trail (trail_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
) TABLESPACE pg_default;

ALTER TABLE hiking.booking
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS hiking.booking_hiker
(
    booking_id UUID NOT NULL,
    hiker_id   UUID NOT NULL,
    CONSTRAINT fkqc275ha8wkxqi4bc5uhhkx8pu FOREIGN KEY (hiker_id)
        REFERENCES hiking.hiker (hiker_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkqc275ha8mmxqi4bc5uhhkx8pu FOREIGN KEY (booking_id)
        REFERENCES hiking.booking (booking_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION

) TABLESPACE pg_default;

ALTER TABLE hiking.booking_hiker
    OWNER to postgres;