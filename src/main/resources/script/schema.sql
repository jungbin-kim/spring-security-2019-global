CREATE TABLE IF NOT EXISTS MEMBERS (
  MNAME    VARCHAR(50)  NOT NULL,
	PWD      VARCHAR(100) NOT NULL,

	PRIMARY KEY(MNAME)
);

CREATE TABLE IF NOT EXISTS AUTHORITIES (
    MNAME     VARCHAR(50)  NOT NULL,
    AUTHORITY VARCHAR(50)  NOT NULL,

    PRIMARY KEY(MNAME)
);

MERGE INTO MEMBERS VALUES ( 'admin' , '{noop}admin' );
MERGE INTO MEMBERS VALUES ( 'member', '{noop}member' );
MERGE INTO MEMBERS VALUES ( 'guest' , '{noop}guest' );

MERGE INTO AUTHORITIES VALUES ( 'admin' , 'ROLE_ADMIN'  );
MERGE INTO AUTHORITIES VALUES ( 'member', 'ROLE_MEMBER' );
MERGE INTO AUTHORITIES VALUES ( 'guest' , 'ROLE_GUEST'  );
