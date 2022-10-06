CREATE TABLE IF NOT EXISTS "user" (
    "id" SERIAL PRIMARY KEY,
    "username" VARCHAR(100) NOT NULL UNIQUE,
    "password" VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS "currency" (
    "cbr_id" VARCHAR(8) PRIMARY KEY,
    "num_code" SMALLINT NOT NULL,
    "char_code" VARCHAR(3) NOT NULL,
    "name" VARCHAR(100),
	UNIQUE ("num_code", "char_code")
);
CREATE TABLE IF NOT EXISTS "rate" (
    "id" SERIAL PRIMARY KEY,
    "currency_id" VARCHAR(8) REFERENCES "currency"("cbr_id"),
    "nominal" INT,
    "value" NUMERIC(10, 4),
    "date" DATE DEFAULT NOW()
);
INSERT INTO "currency"("cbr_id", "num_code", "char_code", "name")
	VALUES ('na~rub', 643, 'RUB', 'Российский рубль'); --isn't a foreign currency
INSERT INTO "rate"("currency_id", "nominal", "value", "date")
    VALUES ('na~rub', 1, 1, NULL); --ruble is equal to itself and it's always valid

CREATE TABLE IF NOT EXISTS "history" (
    "id" SERIAL PRIMARY KEY,
    "user_id" INT REFERENCES "user"(id),
    "initial_currency_id" VARCHAR(8) REFERENCES "currency"("cbr_id"),
    "target_currency_id" VARCHAR(8) REFERENCES "currency"("cbr_id"),
    "initial_value" NUMERIC(16, 4),
    "target_value" NUMERIC(16, 4),
    "date" TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_history_by_user ON "history" USING HASH ("user_id");
CREATE INDEX idx_history_by_date ON "history"("date");