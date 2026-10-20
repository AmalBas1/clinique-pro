ALTER TABLE messages DROP FOREIGN KEY messages_ibfk_1;
ALTER TABLE messages DROP COLUMN rendez_vous_id;
ALTER TABLE messages ADD COLUMN notification_id BIGINT NULL;
ALTER TABLE messages ADD CONSTRAINT uq_messages_notification UNIQUE (notification_id);
ALTER TABLE messages ADD CONSTRAINT fk_messages_notification FOREIGN KEY (notification_id) REFERENCES notifications(id);
