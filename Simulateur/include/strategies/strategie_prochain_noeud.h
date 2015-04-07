/**
*	\file strategie_prochain_noeud.h
*	\brief Interface pour la stratégie de réplication qui choisit les N prochains noeuds pour stocker les données.
*	\author Corentin Salingue
*
*	Projet : Simulation d'algorithmes de charge dans un environnement distribué
*
*/

#ifndef STRATEGIE_PROCHAIN_NOEUD_H
#define STRATEGIE_PROCHAIN_NOEUD_H

#include <strategie_replication.h>

/**
*	\brief Fonction pour initialiser la strategie, appelée par creer_strategie_replication principalement
*	\param nombre_noeud nombre de noeuds qui se servira de la stratégie
*/
void init_strat_prochain_noeud(int nombre_noeud);

/**
*	\brief Fonction appelée par get_position_ensemble
*	\param d Donnée a traiter
*	\return les positions de la donnée
*/
num_noeud * get_position_ensemble_strat_prochain_noeud(donnee d);

/**
*	\brief Fonction appelée par obtenir_nombre_copie
*	\param d Donnée à traiter
*	\return le nombre de copies
*/
int obtenir_nombre_copie_strat_prochain_noeud(donnee d);

#endif

