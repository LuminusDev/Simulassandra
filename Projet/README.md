Mise en place des sources
===========================

Cassandra étant un projet avec une taille d'environ 130Mo, nous nous concentrerons seulement sur les parties que nous allons modifier, c'est à dire les fichiers sources du coeur de Cassandra.
Nous utilisons la version 2.1.2 comme base de projet.

Prérequis
------------

- Java SDK (>=1.7)
- ANT (>=1.8)
- Git (>=1.7)
- CCM (optionnel)


Récupérer le dépôt de Cassandra et passer à la version 2.1.2 :

	git clone git://git.apache.org/cassandra.git cassandra
	git checkout cassandra-2.1.2

Pour compiler le projet, copier le dossier `cassandra` et remplacer le dans le dossier `<chemin_de_cassandra>\src\java\org\apache`.
Ensuite, à partir du dossier `<chemin_de_cassandra>`, lancer la commande

	ant

Le build est alors executé, et il est maintenant possible d'utiliser Cassandra. Pour le lancer, deux solutions:

- Sans CCM, se placer dans le dossier `<chemin_de_cassandra>`. il est préférable de regarder la configuration de Cassandra pour ne pas avoir d'ennui. Elle se situe dans le fichier `conf/cassandra.yaml`. Il est possible de lancer une instance de Cassandra avec la commande

		bin/cassandra

- Avec CCM, il est possible de lancer un cluster en local plus facilement, CCM s'occupant de la configuration. On peut donc créer un cluster

		ccm create mon_cluster --install-dir=<chemin_de_cassandra>

	Puis ajouter des noeuds à ce cluster, par exemple 3

		ccm populate -n 3
	
	On lance ensuite le cluster

		ccm start

	Pour plus de détails, voir la documentation de CCM.

Pour arrêter une instance de Cassandra, il faut arrêter le processus à la main. Avec CCM, il existe la commande

	ccm stop

