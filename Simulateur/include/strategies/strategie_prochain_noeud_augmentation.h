/**
*	\file strategie_prochain_noeud_augmentation.h
*	\brief Interface pour la stratégie de réplication qui choisit les N prochains noeuds pour stocker les données. Le nombre N augmente ou diminue en fonction de la popularité des donnée stockées.
*	\author Corentin Salingue
*
*	Projet : Simulation d'algorithmes de charge dans un environnement distribué
*
*/

#ifndef STRATEGIE_PROCHAIN_NOEUD_AUGMENTATION_H
#define STRATEGIE_PROCHAIN_NOEUD_AUGMENTATION_H

#include <strategie_replication.h>

/**
*	\brief Fonction pour initialiser la strategie, appelée par creer_strategie_replication principalement
*	\param nombre_noeud nombre de noeuds qui se servira de la stratégie
*/
void init_strat_prochain_noeud_augmentation(int nombre_noeud);

/**
*	\brief Fonction pour détruire la strategie, appelée par détruire_strategie_replication principalement
*/
void detruire_strategie_replication_prochain_noeud_augmentation();

/**
*	\brief Fonction appelée par get_position_ensemble
*	\param d Donnée a traiter
*	\return les positions de la donnée
*/
num_noeud * get_position_ensemble_strat_prochain_noeud_augmentation(donnee d);

/**
*	\brief Fonction appelée par obtenir_nombre_copie
*	\param d Donnée à traiter
*	\return le nombre de copies
*/
int obtenir_nombre_copie_strat_prochain_noeud_augmentation(donnee d);

/**
*	\brief Fonction appelée par envoyer_popularite_strategie_replication
*	\param regroupement la popularite d'un cluster
*	\param taille le nombre d'élément de popularite a ajouter
*/
void envoyer_popularite_strategie_replication_prochain_noeud_augmentation(struct popularite * regroupement, int taille);

/**
*	\brief Fonction appelée par remise_zero_popularite_strategie_replication
*	\return Vrai si on doit reset les noeuds
*/
bool remise_zero_popularite_strategie_replication_prochain_noeud_augmentation();
#endif

