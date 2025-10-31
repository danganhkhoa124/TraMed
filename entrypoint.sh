#!/bin/bash
# entrypoint.sh

set -e

# Resolve the effective Postgres configuration, honoring both DB_* and POSTGRES_* overrides.
POSTGRES_USER="${POSTGRES_USER:-${DB_USER:-testUser}}"
POSTGRES_PASSWORD="${POSTGRES_PASSWORD:-${DB_PASSWORD:-abcd@1234}}"
POSTGRES_DB="${POSTGRES_DB:-${DB_NAME:-testDb}}"

export POSTGRES_USER POSTGRES_PASSWORD POSTGRES_DB
export PGPASSWORD="${POSTGRES_PASSWORD}"

# Propagate resolved values back to DB_* variables so downstream scripts can rely on them.
if [ -z "${DB_USER:-}" ] || { [ "${DB_USER}" = "testUser" ] && [ "${POSTGRES_USER}" != "testUser" ]; }; then
  DB_USER="${POSTGRES_USER}"
fi

if [ -z "${DB_PASSWORD:-}" ] || { [ "${DB_PASSWORD}" = "abcd@1234" ] && [ "${POSTGRES_PASSWORD}" != "abcd@1234" ]; }; then
  DB_PASSWORD="${POSTGRES_PASSWORD}"
fi

if [ -z "${DB_NAME:-}" ] || { [ "${DB_NAME}" = "testDb" ] && [ "${POSTGRES_DB}" != "testDb" ]; }; then
  DB_NAME="${POSTGRES_DB}"
fi

DB_HOST="${DB_HOST:-postgres}"
DB_PORT="${DB_PORT:-5432}"

export DB_USER DB_PASSWORD DB_NAME DB_HOST DB_PORT
export PGUSER="${POSTGRES_USER}" PGHOST="${DB_HOST}" PGPORT="${DB_PORT}"

# Wait for PostgreSQL to start using docker-entrypoint.sh
/usr/local/bin/docker-entrypoint.sh postgres &
POSTGRES_PID=$!

until pg_isready -h "${DB_HOST}" -p "${DB_PORT}" -U "${POSTGRES_USER}" > /dev/null 2>&1; do
  echo "Waiting for PostgreSQL to start..."
  sleep 2
done

echo "PostgreSQL is ready."

# Resolve target database name from environment variables
TARGET_DB="${DB_NAME:-${POSTGRES_DB:-testDb}}"

# Check if the database already exists
DB_EXISTS=$(psql -h "${DB_HOST}" -p "${DB_PORT}" -U "${POSTGRES_USER}" -d postgres -tAc "SELECT 1 FROM pg_database WHERE datname='${TARGET_DB}'" || true)
if [ "${DB_EXISTS}" != "1" ]; then
  echo "Creating ${TARGET_DB} database..."
  createdb -h "${DB_HOST}" -p "${DB_PORT}" -U "${POSTGRES_USER}" "${TARGET_DB}"
fi

# Run init.sql
echo "Executing init.sql to create table structure..."
psql -h "${DB_HOST}" -p "${DB_PORT}" -U "${POSTGRES_USER}" -d "${TARGET_DB}" -f /docker-entrypoint-initdb.d/init.sql

# Run seed.sql after creating the table structure
echo "Executing seed.sql to create sample data..."
psql -h "${DB_HOST}" -p "${DB_PORT}" -U "${POSTGRES_USER}" -d "${TARGET_DB}" -f /docker-entrypoint-initdb.d/seed.sql

echo "Database initialization completed."

# Wait for PostgreSQL process to finish
wait $POSTGRES_PID
