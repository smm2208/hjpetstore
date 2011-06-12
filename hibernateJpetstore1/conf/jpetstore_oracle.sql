SET SCAN ON

PROMPT Enter the password and TNS name.
PROMPT Enter the oracle SID for TNS name if you are running a local database.

CONNECT system/&systempassword@&&tnsname

-- Drop the user hjpetstore and all other related objects.
DROP USER hjpetstore CASCADE;

-- Creating the study schema
CREATE USER hjpetstore IDENTIFIED BY hjpetstore;

-- Grant the permissions to the user.
GRANT RESOURCE, CONNECT TO hjpetstore;


ALTER USER hjpetstore DEFAULT TABLESPACE users
              QUOTA UNLIMITED ON users;

ALTER USER hjpetstore TEMPORARY TABLESPACE temp;
