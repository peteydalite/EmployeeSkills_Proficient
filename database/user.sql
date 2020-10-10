-- Creates the database users and grants permission

create user employee_skills_owner
with password 'sybc5932';

grant all
on all tables in schema public
to employee_skills_owner;

grant all
on all sequences in schema public
to employee_skills_owner;

create user employee_skills_appuser
with password 'sybc5932'

grant select, insert, update, delete
on all tables in schema public
to employee_skills_appuser;

grant usage, select
on all sequences in schema public
to employee_skills_appuser;