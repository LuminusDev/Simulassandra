#include <stdio.h>
#include <stdlib.h>

#include <strategies/strategie_prochain_noeud.h>

//Nombre max d'appels avant d'augmenter les objets les plus populaires
#define MAX_APPELS 10
//Combien de données on regarde pour les augmenter
#define NB_DONNEE_AUGMENTATION 2

static int nombre_total_noeud;

struct nb_copie {
	id_donnee id;
	int nb;
};

//taille du tableau popularite
static int nb_popularite;
//Etat à l'instant T de la popularite communiquée à la strategie
static struct popularite * popularite;
//Nombre d'appels de fonction envoyer popularite
static int nb_appel_popularite;

//taille de nb_copie_replicas
static int nb_replicas_copie;

//contient l'ensemble des nombres de copies selon la donnée
static struct nb_copie * nb_copie_replicas;	

void init_strat_prochain_noeud_augmentation(int nombre_noeud) {
	nombre_total_noeud = nombre_noeud;
	nb_copie_replicas = NULL;
	nb_replicas_copie = 0;
	popularite = NULL;
	nb_popularite = 0;
	nb_appel_popularite = 0;
}

void detruire_strategie_replication_prochain_noeud_augmentation() {
	if(nb_copie_replicas != NULL)
		free(nb_copie_replicas);
	if(popularite != NULL)
		free(popularite);
}

int obtenir_nombre_copie_strat_prochain_noeud_augmentation(donnee d) {
	for (int i = 0; i < nb_replicas_copie; i++)
		if (nb_copie_replicas[i].id == obtenir_id_donnee(d))
			return nb_copie_replicas[i].nb;
	
	//pas trouvé, donc une nouvelle donnée à ajouter
	struct nb_copie * tmp = malloc((nb_replicas_copie + 1) * sizeof(*tmp));
	
	for (int i = 0; i < nb_replicas_copie; i++) {
		tmp[i].id = nb_copie_replicas[i].id;
		tmp[i].nb = nb_copie_replicas[i].nb;
	}

	tmp[nb_replicas_copie].id = obtenir_id_donnee(d);
	//Par défaut, on met 3 copies	
	tmp[nb_replicas_copie].nb = 3;

	if (nb_copie_replicas != NULL)
		free(nb_copie_replicas);

	nb_copie_replicas = tmp;
	nb_replicas_copie++;

	return nb_copie_replicas[nb_replicas_copie-1].nb;
}

num_noeud * get_position_ensemble_strat_prochain_noeud_augmentation(donnee d) {
	int nb_copie = obtenir_nombre_copie_strat_prochain_noeud_augmentation(d);

	num_noeud * tab = malloc(nb_copie * sizeof(*tab));

	id_donnee id= obtenir_id_donnee(d);

	for (int i = 0; i < nb_copie; i++)
		tab[i] = (id + i) % nombre_total_noeud;

	return tab;
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
	free(entree);
	return taille_res;
}

void envoyer_popularite_strategie_replication_prochain_noeud_augmentation(struct popularite * regroupement, int taille) {
	nb_appel_popularite++;

	nb_popularite = regroupement_popularite(&popularite, nb_popularite, regroupement, taille);
	
	if (nb_appel_popularite == MAX_APPELS) {
		int nombre_donnee_augmentee = 0;
		while (nombre_donnee_augmentee < NB_DONNEE_AUGMENTATION) {
			//On cherche quelle est la donnée la plus populaire
			int donnee_max = 0;
			for (int i = 0; i < nb_popularite; i++)
				if(popularite[donnee_max].nb < popularite[i].nb)
					donnee_max = i;
			//On augmente son nombre
			for (int i = 0; i < nb_replicas_copie; i++)
				if (nb_copie_replicas[i].id == popularite[donnee_max].id) {
					nb_copie_replicas[i].nb += 1; 
					break;
				}
			
			//On diminue sa popularité pour éviter de l'avoir encore au prochain tour de boucle
			popularite[donnee_max].nb = 0;
			nombre_donnee_augmentee++;	
			
		}
		free(popularite);
		nb_popularite = 0;
		nb_appel_popularite = 0;
	}
}

bool remise_zero_popularite_strategie_replication_prochain_noeud_augmentation() {
	return nb_appel_popularite == 0;
}
