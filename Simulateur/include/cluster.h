/**
*	\file cluster.h
*	\brief Interface pour la gestion des clusters de noeuds
*	\author Corentin Salingue
*
*	Projet : Simulation d'algorithmes de charge dans un environnement distribué
*
*/

#ifndef CLUSTER_H
#define CLUSTER_H

//Encapsulation forte de la structure
struct cluster;

/**
*	\brief Désigne l'objet Cluster
*/
typedef struct cluster * cluster;

#include <requete.h>
#include <strategie_replication.h>
#include <noeud.h>

/**
*	\brief Constructeur de l'objet cluster
*	\param nombre désigne le nombre de noeuds souhaité dans le cluster
*	\param s désigne la stratégie à adopter dans le cluster
*	\return Retourne l'objet Cluster
*/
cluster creer_cluster(int nombre, type_strategie s);

/**
*	\brief Destructeur de l'objet cluster
*	\param c Objet cluster à détruire
*/
void detruire_cluster(cluster c);

/**
*	\brief Envoie une requête au cluster
*	\param c Le cluster visé
*	\param r La requête à envoyer
*	\param num Le numéro du noeud qui reçoit la requête, compris entre [0, nombre_max_noeud-1]
*/
void envoyer_requete_cluster(cluster c, requete r, num_noeud num);

/**
*	\brief Effectue un travail de temps "pas"
*	\param c Le cluster visé
*	\param pas Le temps de traval du cluster
*/
void pas_de_travail_cluster(cluster c, time pas);

/**
*	\brief Retourne la stratégie en cours pour le cluster
*	\param Le cluster visé
*	\return La stratégie du cluster
*/
strategie_replication obtenir_strategie_cluster(cluster c);

#endif
