#!/bin/bash
BASEDIR=$(dirname $0)
DATABASE=employee_skills
psql -U postgres -f "$BASDIR/dropdb.sql" &&
createdb -U postgres $DATABASE &&
psql -U postgres -d $DATABASE -f "$BASEDIR/schema.sql" &&
psql -U postgres -d $DATABASE -f "$BASDIR/user.sql"