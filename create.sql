CREATE TABLE person (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_at datetime DEFAULT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE bank_card (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_at datetime DEFAULT NULL,
  card_number bigint(20) NOT NULL,
  person_id bigint(20) NOT NULL,
  UNIQUE (card_number),
  PRIMARY KEY (id),
  KEY fk_person_id (person_id),
  CONSTRAINT fk_person_id FOREIGN KEY (person_id) REFERENCES person (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DELIMITER //

CREATE PROCEDURE find_person_with_cards_by_id(IN input BIGINT(20))
BEGIN
	SELECT * 
    FROM person as p
    LEFT JOIN bank_card as bc ON bc.person_id = p.id
    WHERE p.id = input;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE find_all_person_with_cards()
BEGIN
	SELECT 
		p.*,
		group_concat(bc.card_number) as cards    
	FROM person as p
	LEFT JOIN bank_card as bc ON bc.person_id = p.id
	group by (p.id);
    
END //

DELIMITER ;
