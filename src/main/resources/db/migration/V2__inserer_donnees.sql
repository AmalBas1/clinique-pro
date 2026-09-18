INSERT INTO users (id, email, password, nom, prenom, role) VALUES
(1, 'admin@cliniquepro.com', '$2a$10$X...dummyhash', 'Admin', 'System', 'ADMIN'),
(2, 'dr.house@cliniquepro.com', '$2a$10$X...dummyhash', 'House', 'Gregory', 'MEDECIN'),
(3, 'dr.grey@cliniquepro.com', '$2a$10$X...dummyhash', 'Grey', 'Meredith', 'MEDECIN'),
(4, 'patient1@gmail.com', '$2a$10$X...dummyhash', 'Dupont', 'Jean', 'PATIENT'),
(5, 'patient2@gmail.com', '$2a$10$X...dummyhash', 'Martin', 'Sophie', 'PATIENT');


INSERT INTO medecins (id, nom, prenom, telephone, specialite, user_id) VALUES
(1, 'House', 'Gregory', '0611223344', 'Cardiologie', 2),
(2, 'Grey', 'Meredith', '0699887766', 'Généraliste', 3);

INSERT INTO patients (id, nom, prenom, telephone, adresse, date_naissance, user_id) VALUES
(1, 'Dupont', 'Jean', '0600112233', '12 Rue de Paris, Casablanca', '1985-05-12', 4),
(2, 'Martin', 'Sophie', '0655443322', '45 Avenue Mohammed V, Rabat', '1992-09-28', 5);

INSERT INTO rendezvous (id, date_rendez_vous, statut, patient_id) VALUES
(1, '2026-10-10 10:00:00', 'CONFIRME', 1),
(2, '2026-10-11 14:30:00', 'EN_ATTENTE', 2),
(3, '2026-10-12 09:15:00', 'ANNULE', 1);

INSERT INTO notifications (id, contenu, date_creation, type, rendez_vous_id) VALUES
(1, 'Votre rendez-vous prévu le 2026-10-10 10:00:00 a été confirmé.', '2026-06-01 08:30:00', 'RAPPEL_RDV', 1),
(2, 'Nous vous informons que votre rendez-vous prévu le 2026-10-12 09:15:00 a été annulé.', '2026-06-02 11:00:00', 'ANNULATION_RDV', 3);

INSERT INTO messages (id, contenu, date_envoi, expediteur_role, rendez_vous_id) VALUES
(1, 'Bonjour docteur, est-ce que je dois être à jeun pour la consultation ?', '2026-06-01 09:00:00', 'PATIENT', 1),
(2, 'Bonjour, oui il est préférable d arriver à jeun.', '2026-06-01 09:30:00', 'MEDECIN', 1);