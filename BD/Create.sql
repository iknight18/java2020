CREATE TABLE Personne(
   cin VARCHAR(8),
   nom VARCHAR(50) NOT NULL,
   prenom VARCHAR(50) NOT NULL,
   sexe VARCHAR(50) NOT NULL,
   dateNaiss DATE NOT NULL,
   numTel VARCHAR(8) NOT NULL,
   ville VARCHAR(50) NOT NULL,
   adresse VARCHAR(50) NOT NULL,
   code_postal VARCHAR(50) NOT NULL,
   gouvernerat VARCHAR(50) NOT NULL,
   statut_social VARCHAR(50) NOT NULL,
   email VARCHAR(50) NOT NULL,
   PRIMARY KEY(cin),
   UNIQUE(numTel)
);

CREATE TABLE Compte(
   cin VARCHAR(8),
   idCompte VARCHAR(50),
   RIB VARCHAR(50) NOT NULL,
   mdp VARCHAR(50) NOT NULL,
   typeCompte VARCHAR(50) NOT NULL,
   solde float,
   PRIMARY KEY(cin, idCompte),
   UNIQUE(RIB),
   FOREIGN KEY(cin) REFERENCES Personne(cin)
);

CREATE TABLE Transaction(
   cin VARCHAR(8),
   idCompte VARCHAR(50),
   idTransaction VARCHAR(50),
   type VARCHAR(50),
   dateT DATE NOT NULL,
   PRIMARY KEY(cin, idCompte, idTransaction),
   FOREIGN KEY(cin, idCompte) REFERENCES Compte(cin, idCompte)
);

CREATE TABLE Virement(
   idVirement VARCHAR(50),
   type VARCHAR(50) NOT NULL,
   dateV DATE NOT NULL,
   cinEmm VARCHAR(8) NOT NULL,
   idCompteEmm VARCHAR(50) NOT NULL,
   cinAcc VARCHAR(8) NOT NULL,
   idCompteAcc VARCHAR(50) NOT NULL,
   PRIMARY KEY(idVirement),
   FOREIGN KEY(cinEmm, idCompteEmm) REFERENCES Compte(cin, idCompte),
   FOREIGN KEY(cinAcc, idCompteAcc) REFERENCES Compte(cin, idCompte)
);
CREATE TABLE Authentification(
    username VARCHAR(50),
   password VARCHAR(50),
   cin VARCHAR(8) NOT NULL,
   PRIMARY KEY(username, password),
   UNIQUE(cin),
   FOREIGN KEY(cin) REFERENCES Personne(cin)

);