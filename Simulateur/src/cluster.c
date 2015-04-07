#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#include <cluster.h>

struct cluster {
	//Contient les noeuds du cluster
	noeud * grappe;
	//Nombre de noeuds dans la grappe
	int nb_noeuds;
	//Strategie du cluster
	strategie_replication strat_rep;
};

cluster creer_cluster(int nombre, type_strategie s) {
	if(nombre < 1) {
		printf("Erreur, le nombre de noeuds doit être supérieur à 1 !\n");
		exit(EXIT_FAILURE);
	}		

	cluster c = malloc(sizeof(*c));

	if (c == NULL)
		return NULL;

	c->grappe = malloc(nombre * sizeof(*(c->grappe)));

	if (c->grappe == NULL) {
		free(c);
		return NULL;
	}

	c->strat_rep = creer_strategie_replication(nombre, s);

	if (c->strat_rep == NULL) {
		free(c->grappe);
		free(c);
		return NULL;
	}
	
	for (int i = 0; i < nombre; i++)
		c->grappe[i] = creer_noeud(i, c);
	c->nb_noeuds = nombre;

	return c;
}

void detruire_cluster(cluster c) {
	for (int i = 0; i < c->nb_noeuds; i++)
		detruire_noeud(c->grappe[i]);
	
	detruire_strategie_replication(c->strat_rep);

	free(c->grappe);
	free(c);
}

void envoyer_requete_cluster(cluster c, requete r, num_noeud num) {
	if (!(num >= 0 && num < c->nb_noeuds)) {
		printf("Erreur, le noeud %d est inconnu !\n", num);
		exit(EXIT_FAILURE);
	} 
	ajouter_requete_noeud(c->grappe[num], r);
}

//Ajoute entree dans res et retourne la taille finale de res
static int regroupement_popularite(struct popularite ** res, int taille_res, struct popularite * entree, int taille_entree) {
	for (int i = 0; i < taille_entree; i++) {
		bool trouve = false;
		for (int j = 0; j < taille_res; j++)
			if (entree[i].id == res[0][j].id) {
				res[0][j].nb += entree[i].nb;
				//prochaine entrée dans entree=
				trouve = true;
				break;
			}
		if (!trouve) {
			struct popularite * tmp = malloc((taille_res + 1) * sizeof(*tmp));
			
			for (int x = 0; x < taille_res; x++) {
				tmp[x].id = res[0][x].id;
				tmp[x].nb = res[0][x].nb;
			}
			
			tmp[taille_res].id = entree[i].id;
			tmp[taille_res].nb = entree[i].nb;

			if (res != NULL)
				free(*res);
			*res = tmp;
			taille_res++;
		}
	}
	return taille_res;
}

void pas_de_travail_cluster(cluster c, time pas) {
	struct popularite * regroupement = NULL;
	int taille = 0;
	for (int i = 0; i < c->nb_noeuds; i++) {
		pas_de_travail_noeud(c->grappe[i], pas);
		//on récupère la popularité de chaque noeud, on la regroupe sur le cluster et on l'envoie à la strategie
		taille = regroupement_popularite(&regroupement, taille, obtenir_popularite_noeud(c->grappe[i]), nb_donnee_noeud(c->grappe[i]));
	}
	
	envoyer_popularite_strategie_replication(c->strat_rep, regroupement, taille);
	
	if (remise_zero_popularite_strategie_replication(c->strat_rep)) {
		for (int i = 0; i < c->nb_noeuds; i++)
			remise_zero_popularite_noeud(c->grappe[i]);
		//La popularite est RAZ
		taille = 0;
	}
	
}

strategie_replication obtenir_strategie_cluster(cluster c) {
	return c->strat_rep;
}
