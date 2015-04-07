#include <stdio.h>
#include <stdlib.h>

#include <noeud.h>
#include <file.h>

struct noeud {
	//Equivaut à son nom
	num_noeud numero;
	//Stocke les requêtes 
	file requetes;
	//A l'instant T, si une requête est en cours, elle est là. NULL si rien.
	requete req_en_cours;
	//Le temps qui lui reste à monopoliser le processeur
	time temps_req_en_cours;
	//Cluster du noeud
	cluster cluster_noeud;
	//Popularité des objets
	struct popularite * popularite;
	//taille du tableau de popularité
	int size_popularite;
};

noeud creer_noeud(num_noeud numero, cluster c) {
	noeud n = malloc(sizeof(*n));

	if (n == NULL)
		return NULL;

	n->requetes = creer_file();
	n->numero = numero;
	n->req_en_cours = NULL;
	n->temps_req_en_cours = 0;
	n->cluster_noeud = c;

	if (n->requetes == NULL) {
		free(n);
		return NULL;
	}
	
	//Aucun objet initialisé :)
	n->popularite = NULL;
	n->size_popularite = 0;
	
	return n;
}

void detruire_noeud(noeud n) {
	detruire_file(n->requetes);

	if (n->size_popularite != 0)
		free(n->popularite);

	free(n);
}

num_noeud obtenir_numero_noeud(noeud n) {
	return n->numero;
}

void ajouter_requete_noeud(noeud n, requete r) {
	ajouter_element_file(n->requetes, (void *) r);
}

static void augmenter_priorite(noeud n, donnee d) {
	for (int i = 0; i < n->size_popularite; i++) {
		if (n->popularite[i].id == obtenir_id_donnee(d)) {
			n->popularite[i].nb += 1;
			return;
		}
	}
	//on n'a pas trouvé la donnée, on la rajoute
	struct popularite * tmp = malloc((n->size_popularite + 1) * sizeof(*tmp));

	for (int i = 0; i < n->size_popularite; i++) {
		tmp[i].id = n->popularite[i].id;
		tmp[i].nb = n->popularite[i].nb;
	}

	if (n->size_popularite != 0)
		free(n->popularite);

	tmp[n->size_popularite].id = obtenir_id_donnee(d);
	tmp[n->size_popularite].nb = 1;

	n->size_popularite += 1;
	n->popularite = tmp;

}

void pas_de_travail_noeud(noeud n, time pas) {

	while(pas > 0) {
		//Y-a-t-il une requête en cours ?
		if (n->req_en_cours != NULL) {
			//La requête va se terminer dans ce pas de travail
			if (pas >= n->temps_req_en_cours) {
				pas -= n->temps_req_en_cours;
				
				detruire_requete(n->req_en_cours);
				n->temps_req_en_cours = 0;
				n->req_en_cours = NULL;
			} else {
				n->temps_req_en_cours -= pas;
				pas = 0;
				continue;
			}

		}

		//S'il y a du travail...
		if (est_vide_file(n->requetes))
			return;

		requete r = obtenir_element_file(n->requetes);
		defiler_file(n->requetes);

		switch(obtenir_type_requete(r)) {
			case TYPE_INTERNE :
			{
				//Il traite la requête
				n->req_en_cours = r;
				n->temps_req_en_cours = obtenir_temps_requete(r);
				augmenter_priorite(n, obtenir_donnee_requete(r));
				break;
			}
			case TYPE_EXTERNE :
			{
				//Si la requête vient de l'extérieur, il récupère les noeuds qui s'en charge et leur transmet.
				strategie_replication s = obtenir_strategie_cluster(n->cluster_noeud);
				donnee d = obtenir_donnee_requete(r);
				num_noeud * position = get_position_ensemble(s, d);
				int nombre = obtenir_nombre_copie(s, d);

				for (int i = 0; i < nombre; i++) {
					requete tmp = creer_requete(TYPE_INTERNE, obtenir_temps_requete(r), d);
					//Celui là est pour nous
					if (position[i] == n->numero) {
						if (n->req_en_cours == NULL) {
							n->req_en_cours = tmp;
							n->temps_req_en_cours = obtenir_temps_requete(tmp);
							augmenter_priorite(n, d);
						} else {
							ajouter_requete_noeud(n, tmp);		
						}
					//Sinon on envoie au copain :) mais c'est le cluster qui le fait pour nous
					} else {
						envoyer_requete_cluster(n->cluster_noeud, tmp, position[i]);
					}
				}
				
				free(position);
				detruire_requete(r);
			}
				break;
			default :
				printf("Erreur, le type %d de la requête est inconnu pour le noeud %d ! \n", obtenir_type_requete(r), n->numero);
				exit(EXIT_FAILURE);
				break;
		}
	}
}

struct popularite * obtenir_popularite_noeud(noeud n) {
	return n->popularite;
}

int nb_donnee_noeud(noeud n) {
	return n->size_popularite;
}

void remise_zero_popularite_noeud(noeud n) {
	free(n->popularite);
	n->popularite = NULL;
	n->size_popularite = 0;
}
