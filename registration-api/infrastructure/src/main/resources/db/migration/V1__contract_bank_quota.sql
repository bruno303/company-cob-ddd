CREATE TABLE BANK (
    ID UUID NOT NULL PRIMARY KEY,
    NAME VARCHAR(300) NOT NULL,
    INTEREST_RATE DECIMAL(12,2)
);

CREATE TABLE CONTRACT (
    ID UUID NOT NULL PRIMARY KEY,
    NUMBER VARCHAR(100) NOT NULL UNIQUE,
    DATE DATE NOT NULL,
    BANK_ID UUID NOT NULL,
    CALC_TYPE SMALLINT NOT NULL
);

ALTER TABLE CONTRACT
ADD CONSTRAINT FK_CONTRACT_BANK FOREIGN KEY (BANK_ID) REFERENCES BANK (ID);

CREATE TABLE QUOTA (
    ID UUID NOT NULL PRIMARY KEY,
    NUMBER INT NOT NULL,
    AMOUNT DECIMAL(12,2) NOT NULL,
    UPDATED_AMOUNT DECIMAL(12,2) NOT NULL,
    DATE DATE NOT NULL,
    STATUS SMALLINT NOT NULL,
    DATE_UPDATED DATE NULL,
    CONTRACT_ID UUID NOT NULL
);

ALTER TABLE QUOTA
ADD CONSTRAINT FK_QUOTA_CONTRACT FOREIGN KEY (CONTRACT_ID) REFERENCES CONTRACT (ID);
