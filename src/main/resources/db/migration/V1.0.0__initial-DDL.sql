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

