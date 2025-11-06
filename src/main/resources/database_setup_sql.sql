-- Create database if not exists
-- Run this script with postgres user before starting the application

-- Connect to postgres database first
\c postgres

-- Drop database if exists (optional, for clean start)
-- DROP DATABASE IF EXISTS warehouse_db;

-- Create new database
CREATE DATABASE warehouse_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Connect to the new database
\c warehouse_db

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE warehouse_db TO postgres;

-- Confirm database creation
SELECT datname
FROM pg_database
WHERE datname = 'warehouse_db';

-- The tables will be created automatically by Spring Boot JPA