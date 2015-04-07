/**
*	\file file.h
*	\brief Interface pour la gestion des files
*	\author Corentin Salingue
*
*	Projet : Simulation d'algorithmes de charge dans un environnement distribué
*
*/

#ifndef FILE_H
#define FILE_H

#include <stdbool.h>

//Encapsulation forte de la structure
struct file;

/**
*	\brief Désigne l'objet File
*/
typedef struct file * file;

/**
*	\brief Constructeur de l'objet file
*	\return Retourne l'objet file
*/
file creer_file(void);

/**
*	\brief Destructeur de l'objet file
*	\param s Objet file à détruire
*/
void detruire_file(file s);

/**
*	\brief Permet de savoir si une file est vide
*	\param s La file à tester
*	\return TRUE si la vide est vide, False sinon
*/
bool est_vide_file(file s);

/**
*	\brief Ajoute un élement à la fin de la file
*	\brief Attention l'élément ajouté n'est pas copié !
*	\param s La file visée
*	\param object L'objet à ajouter 
*/
void ajouter_element_file(file s, void *objet);

/**
*	\brief Donne l'élement en tête de la file SANS le supprimer
*	\param s La file visée
*	\return L'objet stocké
*/
void * obtenir_element_file(file s);

/**
*	\brief Défile la file d'un élément
*	\param s La file visée
*/
void defiler_file(file s);

/**
*	\brief Retourne la taille de la file
*	\param s La file visée
*	\return La taille de la file
*/
int obtenir_taille_file(file s);

#endif

