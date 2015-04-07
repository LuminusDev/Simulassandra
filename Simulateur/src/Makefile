PREFIX=..

INCDIR = $(PREFIX)/include/
LIBDIR = $(PREFIX)/lib
BINDIR = $(PREFIX)/bin

#le compilateur
CC=gcc

#les flags de compilation
CFLAGS= -std=c99 -g -Wall

#les flags du preprocesseur 
CPPFLAGS= -I $(INCDIR)

#les flags de l'editeur de liens 
LDFLAGS= -L $(LIBDIR) 

#les librairies utilisees 
LDLIBS=

#la bibliotheque a creer
LIBFILENAME=

#Les fichiers d'entete de la bibliotheque a creer
INSTHEADERS=

#Les fichiers objets Ã  inserer dans la bibliotheque
LIBOBJS=


#Le nom du fichier executable a generer
OUTFILE= main

#les fichiers objets a generer 
OBJS= $(LIBOBJS)

#les dependances 
all: $(OUTFILE)

$(OUTFILE): $(OBJS)
        # regle implicite utilisee par make pour l'edition de liens
        # $(CC) $(LDFLAGS) $(OBJS) $(LDLIBS) -o $@

#les dependances des fichiers objets
$(OUTFILE): main.o file.o noeud.o requete.o cluster.o donnee.o strategie_replication.o strategies/strategie_prochain_noeud.o strategies/strategie_prochain_noeud_augmentation.o
noeud.o : noeud.c
main.o : main.c
file.o : file.c
requete.o : requete.c
cluster.o : cluster.c
donnee.o : donnee.c
strategie_replication.o : strategie_replication.c
strategies/strategie_prochain_noeud.o : strategies/strategie_prochain_noeud.c
strategies/strategie_prochain_noeud_augmentation.o : strategies/strategie_prochain_noeud_augmentation.c

        # regle implicite utilisee par make pour la compilation 
        # $(CC) -c $(CPPFLAGS) $(CFLAGS) $< -o $@
clean:
	rm -rf *.o
	rm -rf strategies/*.o
	rm -rf $(OUTFILE)
