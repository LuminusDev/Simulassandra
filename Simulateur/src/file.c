#include <stdio.h>
#include <stdlib.h>

#include <file.h>

typedef struct bloc {
    struct bloc * next;
    void * elem;
} * bloc;

struct file{
    bloc first;
    bloc last;
    int taille;
};

file creer_file(void){
    file liste = malloc(sizeof(*liste));

    if (liste == NULL)
        return NULL;

    liste->first = malloc(sizeof(struct bloc));

    if (liste->first == NULL) {
        free(liste);
        return NULL;
    }

    liste->last = liste->first;;
    liste->taille = 0;

    return liste;
}

void detruire_file(file s){
    free(s->first);
    free(s);
}

bool est_vide_file(file s){
    return s->first == s->last;
}

void ajouter_element_file(file s, void *objet){
    bloc b = malloc(sizeof(*b));
    s->last->elem=objet;

    s->last->next = b;
    s->last = b;

    s->taille++;
}


void * obtenir_element_file(file s){
    if(est_vide_file(s))
        return NULL;

    return s->first->elem;
}

void defiler_file(file s){
    bloc tmp = s->first;

    s->first=s->first->next;
    free(tmp);

    s->taille--;
}

int obtenir_taille_file(file s){
    return s->taille;
}
