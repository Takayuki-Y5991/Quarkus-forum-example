-- V1.0.0
CREATE TABLE "topic" (
  "topic_id" bigserial PRIMARY KEY,
  "title" varchar UNIQUE NOT NULL,
  "subtitle" varchar,
  "content" text NOT NULL,
  "tags" bigint NOT NULL,
  "contributor" bigint NOT NULL,
  "created_at" timestamptz NOT NULL DEFAULT (now()),
  "last_modified_date" timestamptz NOT NULL DEFAULT (now())
);

CREATE TABLE "tag" (
  "tag_id" bigserial PRIMARY KEY,
  "tag_name" varchar NOT NULL
);

CREATE TABLE "comment" (
  "comment_id" bigserial PRIMARY KEY,
  "topic_id" bigint NOT NULL,
  "contributor" bigint NOT NULL,
  "comment" text NOT NULL,
  "created_at" timestamptz NOT NULL DEFAULT (now())
);

CREATE TABLE "account" (
  "account_id" bigserial PRIMARY KEY,
  "account_name" varchar(20) UNIQUE NOT NULL,
  "first_name" varchar(20) NOT NULL,
  "middle_name" varchar(20),
  "last_name" varchar(20) NOT NULL,
  "birthday" date NOT NULL,
  "email" varchar NOT NULL,
  "phone_number" varchar,
  "password" varchar NOT NULL
);

CREATE INDEX ON "topic" ("topic_id");

CREATE INDEX ON "topic" ("title");

CREATE INDEX ON "tag" ("tag_id");

CREATE INDEX ON "comment" ("comment_id");

CREATE INDEX ON "comment" ("topic_id");

CREATE INDEX ON "comment" ("comment_id", "topic_id");

CREATE INDEX ON "account" ("account_id");

COMMENT ON COLUMN "topic"."subtitle" IS 'In one word';

COMMENT ON COLUMN "account"."account_name" IS 'display name';

ALTER TABLE "topic" ADD FOREIGN KEY ("tags") REFERENCES "tag" ("tag_id");

ALTER TABLE "topic" ADD FOREIGN KEY ("contributor") REFERENCES "account" ("account_id");

ALTER TABLE "comment" ADD FOREIGN KEY ("topic_id") REFERENCES "topic" ("topic_id");

ALTER TABLE "comment" ADD FOREIGN KEY ("contributor") REFERENCES "account" ("account_id");

-- V1.0.1
ALTER TABLE "comment" ADD COLUMN "delete_flg" integer NOT NULL default(0);

-- V1.0.2
CREATE TABLE "account_role" (
  "role_id" bigserial PRIMARY KEY,
  "role_name" varchar(20) NOT NULL UNIQUE
);

ALTER TABLE "account" ADD COLUMN "roles" bigint;
ALTER TABLE "account" ADD FOREIGN KEY ("roles") REFERENCES "account_role" ("role_id");

-- 初期データ登録
INSERT INTO public.account_role(role_name)VALUES('Normal') ON CONFLICT DO NOTHING;
INSERT INTO public.account_role(role_name)VALUES('Admin') ON CONFLICT DO NOTHING;

-- テスト実行ユーザー
INSERT INTO public.account
(account_name, first_name, middle_name, last_name, birthday, email, phone_number, "password", roles)
VALUES('NormalAccount', 'Normal', 'F', 'Account', '1990-01-01', 'normal-account@example.com', '000-0000-0000', '$2a$10$GDdsRPU1mhauoyRfd0kZZ.f/ypUFxISEP8wPVSaWUTIx90VPWb0xy', 1);
INSERT INTO public.account
(account_name, first_name, middle_name, last_name, birthday, email, phone_number, "password", roles)
VALUES('AdminAccount', 'Admin', 'A', 'Account', '1980-01-01', 'admin-account@example.com', '001-0000-0000', '$2a$10$383cJA3P8f4V32oqFgoCDuxlD0LNSAIAKL5XIMf32Eb81.lJMhizu', 2);
