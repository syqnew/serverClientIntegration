DROP TABLE MarketState, MarketOrder, Client, Quote, News, Sale, Metadata;

SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED;
CREATE TABLE MarketState (
  year INT(11) PRIMARY KEY,
  year1Event VARCHAR(4),
  year2Event VARCHAR(4),
  duration INT(11)
)ENGINE=InnoDB;

CREATE TABLE MarketOrder (
  id INT(11) AUTO_INCREMENT PRIMARY KEY,
  orderType INT(11),
  status INT(11),
  client INT(11),
  amount INT(11),
  price INT(11),
  unfulfilled INT(11),
  time BIGINT(11),
  version INT(11)
)ENGINE=InnoDB;

CREATE TABLE Client (
  id INT(11) AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(20),
  cash INT(11),
  shares INT(11)
)ENGINE=InnoDB;

CREATE TABLE Quote (
  id INT(11) AUTO_INCREMENT PRIMARY KEY,
  bid INT(11),
  bidSize INT(11),
  ask INT(11),
  askSize INT(11),
  time BIGINT(11)
)ENGINE=InnoDB;

CREATE TABLE News (
  id INT(11) AUTO_INCREMENT PRIMARY KEY,
  message VARCHAR(50)
)ENGINE=InnoDB;

CREATE TABLE Sale (
  id INT(11) AUTO_INCREMENT PRIMARY KEY,
  buyerId INT(11),
  sellerId INT(11),
  amount INT(11),
  price INT(11),
  time BIGINT(11),
  buyOrderId INT(11),
  sellOrderId INT(11)
)ENGINE=InnoDB;

CREATE TABLE Metadata (
  id INT(11) PRIMARY KEY,
  high INT(11),
  low INT(11),
  last INT(11),
  volume INT(11),
  bid INT(11),
  bidSize INT(11),
  ask INT(11),
  askSize INT(11), 
  version INT(11)
)ENGINE=InnoDB;

INSERT INTO Metadata VALUE (1, 0,100000000,0,0,0,0,0,0,0);


-- INSERT INTO Metadata VALUE (1, 45,15,20,0,0,0,0,0,0);
