--Destroys the database and associated users
select pg_terminate_backend(pid)
from pg_stat_activity
where datnam = 'employee_skills';

drop database employee_skills;

drop user employee_skills_owner;
drop user employee_skills_appuser;