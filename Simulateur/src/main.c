#include <stdio.h>
#include <stdlib.h>

#include <cluster.h>
#include <requete.h>

int main(int arc, char ** arv) {

	cluster c = creer_cluster(20, TYPE_STRATEGIE_PROCHAIN_NOEUD_AUGMENTATION);	

	donnee d = creer_donnee();

	for (int i = 0; i < 50; i++) {
	requete r = creer_requete(TYPE_EXTERNE, 1, d);
//	requete r1 = creer_requete(TYPE_INTERNE, 1);

	envoyer_requete_cluster(c, r, 1);
//	envoyer_requete_cluster(c, r1, 1);

}

	for (int i = 0; i < 11; i++)
		pas_de_travail_cluster(c, 2);

	detruire_donnee(d);

	detruire_cluster(c);

	return EXIT_SUCCESS;
}
