ALTER TABLE files ADD COLUMN ftp_file_name VARCHAR(255);
ALTER TABLE files ADD COLUMN original_file_name VARCHAR(255);

UPDATE files SET ftp_file_name = name;

ALTER TABLE files DROP COLUMN name;