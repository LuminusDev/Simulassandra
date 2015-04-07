#include <stdio.h>
#include <stdlib.h>

#include <requete.h>

struct requete {
	time temps_traitement;
	type_requete type_requete;
	donnee donnee_requete;
};

requete creer_requete(type_requete typeReq, time temps, donnee d) {
	requete req = malloc(sizeof(*req));
	
	if (req == NULL)
		return NULL;

	req->type_requete = typeReq;
	req->temps_traitement = temps;
	req->donnee_requete = d;

	return req;
}

void detruire_requete(requete r) {
	free(r);
}

type_requete obtenir_type_requete(requete r) {
	return r->type_requete;
}

time obtenir_temps_requete(requete r) {
	return r->temps_traitement;
}

donnee obtenir_donnee_requete(requete r) {
	return r->donnee_requete;
}
