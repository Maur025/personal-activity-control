#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-INIT_SCRIPT
  CREATE USER dbusr WITH PASSWORD 'WNKQlKMMziswOR3F';

  CREATE DATABASE activity_control_db;
  GRANT ALL PRIVILEGES ON DATABASE activity_control_db TO dbusr;

  \c activity_control_db;
  CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
INIT_SCRIPT