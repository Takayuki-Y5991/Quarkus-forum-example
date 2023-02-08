CREATE TABLE "account_role" (
  "role_id" bigserial PRIMARY KEY,
  "role_name" varchar(20) NOT NULL
);

ALTER TABLE "account" ADD COLUMN "roles" bigint;
ALTER TABLE "account" ADD FOREIGN KEY ("roles") REFERENCES "account_role" ("role_id");