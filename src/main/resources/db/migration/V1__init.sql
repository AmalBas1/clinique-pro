
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255),
  nom VARCHAR(100),
  prenom VARCHAR(100),
  role VARCHAR(50)
) ;


CREATE TABLE medecins (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(100),
  prenom VARCHAR(100),
  telephone VARCHAR(50),
  specialite VARCHAR(100),
  user_id BIGINT UNIQUE,
  FOREIGN KEY (user_id) REFERENCES users(id)
) ;

CREATE TABLE patients (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(100),
  prenom VARCHAR(100),
  telephone VARCHAR(50),
  adresse VARCHAR(255),
  date_naissance DATE,
  user_id BIGINT UNIQUE,
  FOREIGN KEY (user_id) REFERENCES users(id)
) ;

CREATE TABLE rendezvous (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  date_rendez_vous DATETIME,
  statut VARCHAR(50),
  patient_id BIGINT,
  medecin_id BIGINT,
  FOREIGN KEY (patient_id) REFERENCES patients(id),
  FOREIGN KEY (medecin_id) REFERENCES medecins(id)
) ;

CREATE TABLE messages (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  contenu TEXT,
  date_envoi DATETIME,
  expediteur_role VARCHAR(100),
  rendez_vous_id BIGINT,
  FOREIGN KEY (rendez_vous_id) REFERENCES rendezvous(id)
) ;

CREATE TABLE notifications (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  contenu TEXT,
  date_creation DATETIME,
  type VARCHAR(100),
  rendez_vous_id BIGINT NOT NULL,
  FOREIGN KEY (rendez_vous_id) REFERENCES rendezvous(id)
) ;