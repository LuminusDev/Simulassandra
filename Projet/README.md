Mise en place des sources
===========================

Cassandra étant un projet avec une taille d'environ 130Mo, nous nous concentrerons seulement sur les parties que nous allons modifier, c'est à dire les fichiers sources du coeur de Cassandra.

Nous utilisons la version 2.1.2 comme base de projet.

Pour compiler le projet, copier le dossier `cassandra` et remplacer le dans le dossier `<chemin_de_cassandra>\src\java\org\apache`.
Ensuite, à partir du dossier `<chemin_de_cassandra>`, lancer la commande :

	ant

Le build est alors executé, et il est maintenant possible d'utiliser ce projet avec ccm en utilisant la commande :

	ccm create mon_cluster --install-dir=<chemin_de_cassandra>