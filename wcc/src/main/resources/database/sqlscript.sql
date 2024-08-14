DROP TABLE IF EXISTS `postcode_distance_logs`;
DROP TABLE IF EXISTS `postcodelatlng`;
DROP TABLE IF EXISTS `customer`;

------------------------------------------------------------------------------------------------------------------------------------
-- Create Statements
------------------------------------------------------------------------------------------------------------------------------------
-- Customer table
CREATE TABLE IF NOT EXISTS CUSTOMER (
    user_id VARCHAR(20) NOT NULL,
    password VARCHAR(64) NOT NULL,
    nationality VARCHAR(64) NOT NULL,
    ic_type VARCHAR(40),
    ic_no VARCHAR(20) UNIQUE,
    passport VARCHAR(25) UNIQUE,
    issuing_country VARCHAR(40),
    expiry_date DATETIME,
    salutation VARCHAR(10) NOT NULL,
    full_name VARCHAR(40) NOT NULL,
    dialing_code VARCHAR(40) NOT NULL,
    phone_no VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    ic_front longblob,
    ic_back longblob,
    status VARCHAR(20) NOT NULL,
    registered_date DATETIME NOT NULL,
    PRIMARY KEY (user_id)
);

-- Postcodelatlng table
CREATE TABLE IF NOT EXISTS POSTCODELATLNG (
  id int(11) NOT NULL AUTO_INCREMENT,
  postcode varchar(8) NOT NULL,
  latitude decimal(10,7) NOT NULL,
  longitude decimal(10,7) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1;

-- POSTCODE_DISTANCE_LOGS table
CREATE TABLE IF NOT EXISTS POSTCODE_DISTANCE_LOGS (
  id int(11) NOT NULL AUTO_INCREMENT,
  first_location_id int(11) NOT NULL,
  second_location_id int(11) NOT NULL,
  user_id VARCHAR(20) NOT NULL,
  distance varchar(50) NOT NULL,
  update_date DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (first_location_id) REFERENCES POSTCODELATLNG(id),
  FOREIGN KEY (second_location_id) REFERENCES POSTCODELATLNG(id),
  FOREIGN KEY (user_id) REFERENCES CUSTOMER(user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1;







