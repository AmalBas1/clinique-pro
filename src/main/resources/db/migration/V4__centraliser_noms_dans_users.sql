UPDATE users u
JOIN patients p ON p.user_id = u.id
SET u.nom = p.nom
WHERE u.nom IS NULL OR u.nom = '';

UPDATE users u
JOIN patients p ON p.user_id = u.id
SET u.prenom = p.prenom
WHERE u.prenom IS NULL OR u.prenom = '';

UPDATE users u
JOIN medecins m ON m.user_id = u.id
SET u.nom = m.nom
WHERE u.nom IS NULL OR u.nom = '';

UPDATE users u
JOIN medecins m ON m.user_id = u.id
SET u.prenom = m.prenom
WHERE u.prenom IS NULL OR u.prenom = '';

ALTER TABLE patients
DROP COLUMN nom,
DROP COLUMN prenom;

ALTER TABLE medecins
DROP COLUMN nom,
DROP COLUMN prenom;
