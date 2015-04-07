/**
*	\file strategie_replication.h
*	\brief Interface pour la gestion unifiée des stratégies de réplication
*	\brief Il suffira de modifier ces fonctions pour rajouter de nouvelles stratégies
*	\author Corentin Salingue
*
*	Projet : Simulation d'algorithmes de charge dans un environnement distribué
*
*/

#ifndef STRATEGIE_REPLICATION_H
#define STRATEGIE_REPLICATION_H

#include <requete.h>
#include <stdbool.h>

//Encapsulation forte de la structure
struct strategie_replication;

/**
*	\brief Désigne l'objet Cluster
*/
typedef struct strategie_replication * strategie_replication;

/**
*	\brief Désigne le type d'une stratégie
*/
typedef int type_strategie;

//Besoin des types pour le noeud.h
#include <noeud.h>

/**
*	\def TYPE_STRATEGIE_PROCHAIN_NOEUD
*	\brief Désigne la stratégie qui prend l'identifiant modulo le nombre de noeud et les X suivant pour les copies
*/
#define TYPE_STRATEGIE_PROCHAIN_NOEUD 1

/**
*	\def TYPE_STRATEGIE_PROCHAIN_NOEUD_AUGMENTATION
*	\brief Désigne la stratégie qui prend l'identifiant modulo le nombre de noeud et les X suivant pour les copies et dont le nombre X (le nombre de copies) augmente en fonction de la popularité
*/
#define TYPE_STRATEGIE_PROCHAIN_NOEUD_AUGMENTATION 2


/**
*	\brief Constructeur de l'objet strategie_replication
*	\param nombre désigne le nombre de noeuds à intégrer dans la strategie
*	\param type désigne le type de la stratégie
*	\return Retourne l'objet strategie_replication
*/
strategie_replication creer_strategie_replication(int nombre, type_strategie type);

/**
*	\brief Destructeur de l'objet strategie_replication
*	\param s Objet strategie_replication à détruire
*/
void detruire_strategie_replication(strategie_replication s);

/**
*	\brief Permet de connaitre l'ensemble des positions sur le cluster, d'une donnée.
*	\brief Attention, c'est à l'appelant de détruire le tableau de retour
*	\param s la strategie de réplication utilisée
*	\param d la donnée demandée
*	\return un tableau contenant les numéros des noeuds où la donnée est placée
*/
num_noeud * get_position_ensemble(strategie_replication s, donnee d);

/**
*	\brief Permet d'obtenir le nombre de copies d'une donnée
*	\param s la strategie de réplication utilisée
*	\param d la donnée demandée
*	\return le nombre de copies
*/
int obtenir_nombre_copie(strategie_replication s, donnee d);

/**
*	\brief Fonction appelée dans le pas de calcul du cluster qui permet de savoir si on doit reset la popularite dans les noeuds
*	\param s la strategie de réplication utilisée
*	\return Vrai si on remet à zéro, faux sinon
*/
bool remise_zero_popularite_strategie_replication(strategie_replication s);

/**
*	\brief Permet d'envoyer la popularite à la strategie
*	\param s la strategie de réplication utilisée
*	\param regroupement la popularite des objets
*	\param taille le nombre d'objet dans regroupement
*/
void envoyer_popularite_strategie_replication(strategie_replication s, struct popularite * regroupement, int taille);

#endif
