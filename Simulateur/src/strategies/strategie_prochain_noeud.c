#include <stdio.h>
#include <stdlib.h>

#include <strategies/strategie_prochain_noeud.h>

static int nombre_total_noeud;

void init_strat_prochain_noeud(int nombre_noeud) {
	nombre_total_noeud = nombre_noeud;
}

num_noeud * get_position_ensemble_strat_prochain_noeud(donnee d) {
	int nb_copie = obtenir_nombre_copie_strat_prochain_noeud(d);

	num_noeud * tab = malloc(nb_copie * sizeof(*tab));

	id_donnee id= obtenir_id_donnee(d);

	for (int i = 0; i < nb_copie; i++)
		tab[i] = (id + i) % nombre_total_noeud;

	return tab;
}

int obtenir_nombre_copie_strat_prochain_noeud(donnee d) {
	//Nombre arbitraire :)
	return 3;
}
