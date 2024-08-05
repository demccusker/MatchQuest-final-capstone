#!/bin/bash
export PGPASSWORD='postgres1'
BASEDIR=$(dirname $0)
DATABASE=final_capstone

[ "$1" = "release" ] && DATADIR="$BASEDIR/data_release.sql" || DATADIR="$BASEDIR/data_debug.sql"

psql -U postgres -f "$BASEDIR/dropdb.sql" &&
createdb -U postgres $DATABASE &&
psql -U postgres -d $DATABASE -f "$BASEDIR/schema.sql" &&
psql -U postgres -d $DATABASE -f "$DATADIR" &&
psql -U postgres -d $DATABASE -f "$BASEDIR/user.sql"
