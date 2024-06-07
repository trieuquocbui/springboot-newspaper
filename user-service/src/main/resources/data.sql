INSERT IGNORE  INTO role (role_id,name) values('ADMIN','ADMIN');
INSERT IGNORE  INTO role (role_id,name) values('USER','USER');

INSERT IGNORE  INTO user (username,password,full_Name,thumbnail,role_role_id)
values('admin','$2a$12$7esC5sTvrvcqpZ4zyl6SjupC//ZNsn3W109u3dbKFD9EyKTuD.mt2','admin','peaa3cc2f6-51c8-47bb-a1ad-21d8f3a939b1','ADMIN');