insert into user (uid, password, email, deleted, social, created_at, modified_at) values
("test1", "test1234", "test@test.com", false, false, now(), now());

insert into article (title, description, score, count, created_at, modified_at, uid) values
("testarticle", "testdescription", 5, 1, now(), now(), "test1");

insert into comment (description, score, created_at, modified_at, aid, uid) values
("testdescription", 5, now(), now(), 1, "test1")