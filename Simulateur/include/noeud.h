/**
*	\file noeud.h
*	\brief Interface pour la gestion des noeuds
*	\author Corentin Salingue
*
*	Projet : Simulation d'algorithmes de charge dans un environnement distribué
*
*/

#ifndef NOEUD_H
#define NOEUD_H

//Encapsulation forte de la structure
struct noeud;

/**
*	\brief Désigne l'objet Noeud
*/
typedef struct noeud * noeud;

/**
*	\brief Désigne l'identifiant unique au système pour un noeud
*/
typedef int num_noeud;

struct popularite;

//Besoin du type num_noeud pour le cluster
#include <requete.h>
#include <cluster.h>
#include <strategie_replication.h>

/**
*	\brief Désigne le lien entre la popularité et la donnée la concernant
*/
struct popularite {
	id_donnee id;
	int nb;	
};

/**
*	\brief Constructeur de l'objet noeud
*	\param numero désigne le numéro que doit porter le noeud
*	\param c Le cluster auquel appartient le noeud
*	\return Retourne l'objet Noeud
*/
noeud creer_noeud(num_noeud numero, cluster c);

/**
*	\brief Destructeur de l'objet noeud
*	\param n Objet noeud à détruire
*/
void detruire_noeud(noeud n);

/**
*	\brief Permet d'obtenir l'identifiant unique d'un noeud
*	\param n Objet noeud à étudier
*	\return l'identifiant unique au système pour un noeud
*/
num_noeud obtenir_numero_noeud(noeud n);

/**
*	\brief Ajoute une requete à la file d'attente pour le noeud
*	\param n Objet noeud traitant
*	\param r Requête a ajouter
*/
void ajouter_requete_noeud(noeud n, requete r);

/**
*	\brief Effectue un pas de travail.
*	\param n Objet noeud traitant
*	\param pas Temps de traitement maximal que peut travailler le noeud
*/
void pas_de_travail_noeud(noeud n, time pas);

/**
*	\brief Permet d'obtenir les objets populaires d'un noeud donné
*	\param n Objet noeud traitant
*	\return les objets populaires
*/
struct popularite * obtenir_popularite_noeud(noeud n);

/**
*	\brief Permet d'obtenir le nombre de données différents que le noeud a traité durant un laps de temps
*	\param n Objet noeud traitant
*	\return le nombre de données traitées durant un laps de temps
*/
int nb_donnee_noeud(noeud n);

/**
*	\brief Permet de réinitialiser les valeurs de la popularite
*	\param n Objet noeud a traiter
*/
void remise_zero_popularite_noeud(noeud n);
#endif
