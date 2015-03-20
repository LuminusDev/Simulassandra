CREATE TABLE Trains (
  key bigint PRIMARY KEY,
  name varchar,
  speed int,
  birth_year bigint
);

CREATE TABLE Gares (
  name varchar PRIMARY KEY,
  latitude float,
  longitude float
);

CREATE TABLE Agents (
  user_name varchar PRIMARY KEY,
  gender varchar,
  birth_year bigint
);

CREATE TABLE Abonnements (
  name varchar PRIMARY KEY,
  price int,
  validity int
);

CREATE TABLE Frets (
  key varchar PRIMARY KEY,
  speed int,
  birth_year bigint
);


INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (834564, 'Tchoutchou', 80, 1931);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (654842, 'TER Limousin', 210, 2004);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (864527, 'Ter Aquitaine', 210 , 2005);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (134583, 'TGV', 300, 1999);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (648654, 'Intercité', 190, 1993);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (543842, 'Intercité', 190, 2001);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (562652, 'Lunea', 195, 2002);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (564521, 'TGV', 290, 2008);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (695421, 'Thalys', 320 , 2012);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (332152, 'Ter Poitou-Charentes', 185, 2007);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (912215, 'TGV Sud', 250, 2014);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (515153, 'TGV Atlantique', 240, 2013);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (688766, 'Corail', 180, 1999);
INSERT INTO Trains (key, name, speed, birth_year)
  VALUES (666334, 'Deneigeuse', 75, 1995);
  
INSERT INTO Gares (name, latitude, longitude)
  VALUES ( 'Bordeaux', 24.5, 0.5);
INSERT INTO Gares (name, latitude, longitude)
  VALUES ( 'Arcachon', 24.7, -0.5);
INSERT INTO Gares (name, latitude, longitude)
  VALUES ( 'Paris', 23.2, 1.5);
INSERT INTO Gares (name, latitude, longitude)
  VALUES ( 'Bayonne', 25.1, -0.7);
INSERT INTO Gares (name, latitude, longitude)
  VALUES ( 'Poitiers', 23.9, 0.8);
INSERT INTO Gares (name, latitude, longitude)
  VALUES ( 'Brest', 23.2, -1.5);
INSERT INTO Gares (name, latitude, longitude)
  VALUES ( 'Niort', 24.0, -0.4);
INSERT INTO Gares (name, latitude, longitude)
  VALUES ( 'Nantes', 23.4, -0.6);
INSERT INTO Gares (name, latitude, longitude)
  VALUES ( 'Pau', 25.1, 0.6);


INSERT INTO Agents (user_name, gender, birth_year)
  VALUES ( 'Marie', 'F', 1984);
INSERT INTO Agents (user_name, gender, birth_year)
  VALUES ( 'Paul', 'M', 1975);
INSERT INTO Agents (user_name, gender, birth_year)
  VALUES ( 'Jean', 'M', 1990);
INSERT INTO Agents (user_name, gender, birth_year)
  VALUES ( 'Kevin', 'F', 1993);
INSERT INTO Agents (user_name, gender, birth_year)
  VALUES ( 'Benjamin', 'M', 1992);
INSERT INTO Agents (user_name, gender, birth_year)
  VALUES ( 'Rachid', 'M', 1989);
INSERT INTO Agents (user_name, gender, birth_year)
  VALUES ( 'Laurie', 'F', 1991);
INSERT INTO Agents (user_name, gender, birth_year)
  VALUES ( 'Margot', 'F', 1995);
INSERT INTO Agents (user_name, gender, birth_year)
  VALUES ( 'Corentin', 'M', 1993);
INSERT INTO Agents (user_name, gender, birth_year)
  VALUES ( 'Guillaume', 'M', 1993);
  
  
INSERT INTO Abonnements (name, price, validity)
  VALUES ( 'PassLoisir', 80, 10);
INSERT INTO Abonnements (name, price, validity)
  VALUES ( 'Etudiant', 70, 12);
INSERT INTO Abonnements (name, price, validity)
  VALUES ( 'Voyageur', 85, 10);
INSERT INTO Abonnements (name, price, validity)
  VALUES ( 'Militaire', 50, 8);
INSERT INTO Abonnements (name, price, validity)
  VALUES ( 'Vieux', 70, 12); 
  

INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'fr-08524', 125, 1995);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'fr-84552', 120, 1995);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'es-65242', 125, 1997);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'fr-55142', 125, 2005);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'pt-45115', 100, 1990);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'fr-55122', 150, 2002);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'fr-34223', 150, 2008);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'ch-51222', 125, 2003);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'se-65452', 150, 2010);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'se-32141', 160, 2014);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'uk-54444', 145, 2008);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'fr-65411', 130, 2006);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'fr-45114', 130, 2006);
INSERT INTO Frets (key, speed, birth_year)
  VALUES ( 'fr-44443', 135, 2007);