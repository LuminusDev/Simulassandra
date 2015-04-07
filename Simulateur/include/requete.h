/**
*	\file requete.h
*	\brief Interface pour la gestion des requêtes
*	\author Corentin Salingue
*
*	Projet : Simulation d'algorithmes de charge dans un environnement distribué
*
*/

#ifndef REQUETE_H
#define REQUETE_h

#include <global.h>
#include <donnee.h>

//Encapsulation forte de la structure
struct requete;

/**
*	\brief Désigne l'objet Requete
*/
typedef struct requete * requete;

/**
*	\brief Permet de désigner si une requête est externe ou interne.
*/
typedef int type_requete;

/**
*	\def TYPE_EXTERNE
*	\brief Une requete externe provient d'un utilisateur extérieur au réseau
*/
#define TYPE_EXTERNE 1 
/**
*	\def TYPE_INTERNE
*	\brief Une requete interne provient de l'un des noeuds du réseau
*/
#define TYPE_INTERNE 2 

/**
*	\brief Constructeur de l'objet requete
*	\param typeReq désigne le type de la requête
*	\param temps désigne le temps que prendra le noeud à traiter la requête
*	\param d désigne la donnée sur laquelle la requete a lieu
*	\return Retourne l'objet requete
*/
requete creer_requete(type_requete typeReq, time temps, donnee d);

/**
*	\brief Destructeur de l'objet requete
*	\param c Objet requete à détruire
*/
void detruire_requete(requete r);

/**
*	\brief Retourne le type de la requete
*	\param r La requête demandée
*	\return le type de la requête
*/
type_requete obtenir_type_requete(requete r);

/**
*	\brief Permet d'obtenir le temps de traitement restant à la requête
*	\param r la requête demandée
*	\return le temps restant
*/
time obtenir_temps_requete(requete r);

/**
*	\brief Retourne la donnée sur laquelle porte la requête
*	\param r la requête visée
*	\return La donnée modifiée
*/
donnee obtenir_donnee_requete(requete r);
#endif
