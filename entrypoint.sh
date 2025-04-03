#!/bin/bash
# entrypoint.sh

set -e

# Ensure this script has execution permission
chmod +x /entrypoint.sh

# Wait for PostgreSQL to start using docker-entrypoint.sh
/usr/local/bin/docker-entrypoint.sh postgres &
POSTGRES_PID=$!

until su - postgres -c "pg_isready" > /dev/null 2>&1; do
  echo "Waiting for PostgreSQL to start..."
  sleep 2
done

echo "PostgreSQL is ready."

# Check if the database already exists
if ! su - postgres -c "psql -lqt" | grep -qw tramed; then
  echo "Creating tramed database..."
  su - postgres -c "createdb tramed"
fi

# Run init.sql
echo "Executing init.sql to create table structure..."
su - postgres -c "psql -d tramed -f /docker-entrypoint-initdb.d/init.sql"

# Run seed.sql after creating the table structure
echo "Executing seed.sql to create sample data..."
su - postgres -c "psql -d tramed -f /docker-entrypoint-initdb.d/seed.sql"

echo "Database initialization completed."

# Wait for PostgreSQL process to finish
wait $POSTGRES_PID