

```sql
查看表空间信息
select tablespace_name, file_id, file_name,
round(bytes/(1024*1024),0) total_space
from dba_data_files
order by tablespace_name

创建表空间
create tablespace DATA_SPACE
logging 
datafile 'C:/temp/DATA_SPACE.dbf' 
size 1500m 
autoextend on 
next 100m maxsize 10000m 
extent management local;

创建用户指定表空间
create user ZW_TEST identified by ZW_TEST DEFAULT TABLESPACE DATA_SPACE;

给用户授权
GRANT 
　　CREATE SESSION, CREATE ANY TABLE, CREATE ANY VIEW ,CREATE ANY INDEX, CREATE ANY sequence,CREATE ANY PROCEDURE,
　　ALTER ANY TABLE, ALTER ANY sequence,ALTER ANY PROCEDURE,
　　DROP ANY TABLE, DROP ANY sequence,DROP ANY VIEW, DROP ANY INDEX, DROP ANY PROCEDURE,
　　SELECT ANY TABLE, INSERT ANY TABLE, UPDATE ANY TABLE, DELETE ANY TABLE
TO ZW_TEST;

grant create session to ZW_TEST;
grant unlimited tablespace to ZW_TEST;
grant create table to ZW_TEST;
grant drop any table to ZW_TEST;
grant insert any table to ZW_TEST;
grant update any table to ZW_TEST;
grant dba to ZW_TEST;

解锁用户
alter user ZW_TEST account unlock;

登录sysdba
cmd下 sqlplus sys as sysdba
关闭服务
shutdown abort;
重启服务
startup;


```

