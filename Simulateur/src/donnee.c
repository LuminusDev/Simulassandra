#include <stdio.h>
#include <stdlib.h>

#include <donnee.h>

struct donnee {
	id_donnee id;
};

//Permet de donner un id unique à chaque donnée
id_donnee prochain_id = 0;

donnee creer_donnee() {
	donnee d = malloc(sizeof(*d));
	
	if (d == NULL)
		return NULL;

	d->id = prochain_id++;

	return d;
}

void detruire_donnee(donnee d) {
	free(d);
}

id_donnee obtenir_id_donnee(donnee d) {
	return d->id;
}
