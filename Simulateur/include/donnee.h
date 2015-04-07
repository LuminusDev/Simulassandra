/**
*	\file donnee.h
*	\brief Interface pour la gestion des données
*	\author Corentin Salingue
*
*	Projet : Simulation d'algorithmes de charge dans un environnement distribué
*
*/

#ifndef DONNEE_H
#define DONNEE_H

//Encapsulation forte de la structure
struct donnee;

/**
*	\brief Désigne l'objet Donnee
*/
typedef struct donnee * donnee;

/**
*	\brief Désigne l'identifiant unique pour le cluster de la donnee
*/
typedef int id_donnee;

/**
*	\brief Constructeur de l'objet Donnee
*/
donnee creer_donnee();

/**
*	\brief Destructeur de l'objet donnee
*	\param d Objet donnee à détruire
*/
void detruire_donnee(donnee d);

/**
*	\brief Permet d'obtenir un identifiant unique d'un objet
*	\param d l'objet donnee à étudier
*	\return l'identifiant de la donnée
*/
id_donnee obtenir_id_donnee(donnee d);

#endif
