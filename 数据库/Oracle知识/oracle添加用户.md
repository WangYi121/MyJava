	create user fiberhome identified by fiberhome;//创建用户并设置密码

```shell
grant create session to fiberhome;
grant unlimited tablespace to fiberhome;
grant create table to fiberhome;
grant drop any table to fiberhome;
grant insert any table to fiberhome;
grant update any table to fiberhome;
grant dba to fiberhome;


alter user fiberhome account unlock;


shutdown abort;
startup;
```
