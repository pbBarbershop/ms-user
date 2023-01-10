use ms_user;
INSERT INTO user(name, phone, email, description, password) values("Jônatas", 71992938992,
"jonata25@hotmail.com", "teste", "$2a$12$/Jd99pSYJpWGnL1Vcmw0K.dQ.DHyWW7hvSSMr2JdzNYoBbA//GR9y");
INSERT INTO profile(name)values("ROLE_MANAGER");
INSERT INTO profile(name)values("ROLE_CUSTOMER");
INSERT INTO profile(name)values("ROLE_EMPLOYEE");
insert into user_profile(user_id, profile_id) values(1,1);




