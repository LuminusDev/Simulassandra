#include <stdio.h>
#include <stdlib.h>

#include <strategie_replication.h>
#include <strategies/strategie_prochain_noeud.h>
#include <strategies/strategie_prochain_noeud_augmentation.h>

struct strategie_replication {
	//Nombre de noeuds à gérer
	int nombre_noeuds;
	//Permet de choisir dans les différentes stratégies disponibles
	type_strategie type;	
};

strategie_replication creer_strategie_replication(int nombre, type_strategie type) {
	strategie_replication s = malloc(sizeof(*s));

	if (s == NULL)
		return NULL;

	s->nombre_noeuds = nombre;
	s->type = type;

	switch(s->type) {
		case TYPE_STRATEGIE_PROCHAIN_NOEUD :
			init_strat_prochain_noeud(nombre);
			break;
		case TYPE_STRATEGIE_PROCHAIN_NOEUD_AUGMENTATION :
			init_strat_prochain_noeud_augmentation(nombre);
			break;
		default :
			printf("Erreur, la stratégie n'existe pas ou la fonction d'initialisation n'a pas été définie !");
			exit(EXIT_FAILURE);
			break;	
	}

	return s;
}

void detruire_strategie_replication(strategie_replication s) {
	switch(s->type) {
		case TYPE_STRATEGIE_PROCHAIN_NOEUD :
			//Ne rien faire :)
			break;
		case TYPE_STRATEGIE_PROCHAIN_NOEUD_AUGMENTATION :
			detruire_strategie_replication_prochain_noeud_augmentation();	
			break;
		default :
			printf("Erreur, la stratégie n'existe pas ou la fonction de terminaison n'a pas été définie !");
			exit(EXIT_FAILURE);
			break;	
	}
	free(s);
}

num_noeud * get_position_ensemble(strategie_replication s, donnee d) {
	switch(s->type) {
		case TYPE_STRATEGIE_PROCHAIN_NOEUD :
			return get_position_ensemble_strat_prochain_noeud(d);
			break; 
		case TYPE_STRATEGIE_PROCHAIN_NOEUD_AUGMENTATION :
			return get_position_ensemble_strat_prochain_noeud_augmentation(d);
			break;
		default :
			printf("Erreur, la stratégie n'existe pas ou la fonction get_position_ensemble n'a pas été définie !");
			exit(EXIT_FAILURE);
			break;	
	}
}

int obtenir_nombre_copie(strategie_replication s, donnee d) {
	switch(s->type) {
		case TYPE_STRATEGIE_PROCHAIN_NOEUD :
			return obtenir_nombre_copie_strat_prochain_noeud(d);
			break;
		case TYPE_STRATEGIE_PROCHAIN_NOEUD_AUGMENTATION :
			return obtenir_nombre_copie_strat_prochain_noeud_augmentation(d);
			break;
		default :
			printf("Erreur, la stratégie n'existe pas ou la fonction obtenir_nombre_copie n'a pas été définie !");
			exit(EXIT_FAILURE);
			break;	
	}
}

void envoyer_popularite_strategie_replication(strategie_replication s, struct popularite * regroupement, int taille) {
	switch(s->type) {
		case TYPE_STRATEGIE_PROCHAIN_NOEUD :
			//Rien faire
			break;
		case TYPE_STRATEGIE_PROCHAIN_NOEUD_AUGMENTATION :
			envoyer_popularite_strategie_replication_prochain_noeud_augmentation(regroupement, taille);
			break;
		default :
			printf("Erreur, la stratégie n'existe pas ou la fonction envoyer_popularite_strategie_replication n'a pas été définie !");
			exit(EXIT_FAILURE);
			break;	
	}
	
}


bool remise_zero_popularite_strategie_replication(strategie_replication s) {
	switch(s->type) {
		case TYPE_STRATEGIE_PROCHAIN_NOEUD :
			//Rien faire
			return false;
			break;
		case TYPE_STRATEGIE_PROCHAIN_NOEUD_AUGMENTATION :
			return remise_zero_popularite_strategie_replication_prochain_noeud_augmentation();	
			break;
		default :
			printf("Erreur, la stratégie n'existe pas ou la fonction remise_zero_popularite_strategie_replication n'a pas été définie !");
			exit(EXIT_FAILURE);
			break;	
	}

}
