#!/bin/sh
set -e

export POSTGRES_USER=$(cat /run/secrets/DB_USER)
export POSTGRES_PASSWORD=$(cat /run/secrets/DB_PASS)
export POSTGRES_DB=education_system
exec docker-entrypoint.sh postgres
if [ ! -s "$PGDATA/PG_VERSION" ]; then
  echo "Initializing database..."

  initdb --username="$POSTGRES_USER" --pwfile=<(echo "$POSTGRES_PASSWORD")
fi