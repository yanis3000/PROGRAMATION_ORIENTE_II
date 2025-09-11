# Yanis Boumazouzi

# Faire en sorte que les pouvoirs du docteur fonctionnent -- importer un switch
# Faire bouger les daleks
# Si les daleks se trouvent dans la meme case, il seront mort


import random
import readchar

class Daleks():
    def __init__(self, liste_pos, docteur):
        self.docteur = docteur
        self.pos_x = liste_pos[0]
        self.pos_y = liste_pos[1]

class Ferraille():                  #Est-ce utile ?
    def __init__(self, liste_pos):
        self.pos_x = liste_pos[0]
        self.pos_y = liste_pos[1]


class Docteur():
    def __init__(self, partie):
        self.partie  = partie
        self.pos_x = random.randrange(self.partie.taille_x)
        self.pos_y = random.randrange(self.partie.taille_y)


class Partie():
    def __init__(self, x, y):
        self.taille_x = x
        self.taille_y = y
        self.docteur = Docteur(self)
        self.daleks = Daleks(self)
        self.ferraile = Ferraille(self) # Est-ce utile ?
        self.daleks = []
        self.nivo = 0
        self.nb_daleks_par_nivo = 5
        self.init = 0
        self.creer_nivo()


    def creer_nivo (self) :
        self.nivo += 1
        nb_daleks = self.nivo * self.nb_daleks_par_nivo
        pos_legale = [[self.docteur.pos_x, self.docteur.pos_y]]
        while nb_daleks:
            x = random.randrange(self.taille_x)
            y = random.randrange(self.taille_y)
            if [x,y] not in pos_legale :
                pos_legale.append([x,y])
                nb_daleks -= 1
        pos_legale.pop(0)
        for i in pos_legale:
            dalek = Daleks(i, self.docteur)
            self.daleks.append(dalek)


class Vue():
    def __init__(self, partie):
        self.partie = partie

    def afficher_info(self, info):
        print(info)

    def afficher_partie(self) :
        print("\n\n\n\n")
        grille = self.creer_grille()
        self.peupler_grille(grille)
        self.afficher_grille(grille)
        print("""          
DOCTOR WHO : JEU DES DALEKS
`t` : Permet de vous téléporter dans une case aléatoire
`z` : Permet de zapper tous les daleks se trouvant à deux cases à côté de vous
Déplacer vous à l'aide des touches fléchées
        """)
        self.move_dalek()
        self.move_doc()


    def creer_grille(self):
        haut = self.partie.taille_y
        large = self.partie.taille_x
        tableau = []
        for i in range(haut):
            ligne = []            
            for j in range(large):
                ligne.append(" ")
            tableau.append(ligne)
        return tableau
    
    def peupler_grille(self, grille) :
        grille[self.partie.docteur.pos_y][self.partie.docteur.pos_x] = "D"
        for i in self.partie.daleks : 
            if (len(grille[i.pos_y][i.pos_x]) > 1) :
                self.partie.daleks.pop(i)
                grille[i.pos_y][i.pos_x] = "X"
            grille[i.pos_y][i.pos_x] = "*"

    def teleportation(self)

    def move_doc (self) : 
        x = self.partie.docteur.pos_x
        y = self.partie.docteur.pos_y
    
        dict_mouv = {
            "1": [x - 1, y + 1],
            "2": [x,     y + 1],
            "3": [x + 1, y + 1],
            "4": [x - 1, y],
            "5": [x,     y],
            "6": [x + 1, y],
            "7": [x - 1, y - 1],
            "8": [x,     y - 1],
            "9": [x + 1, y - 1]
            "t": 
        }
        print('Appuyez sur une touche :')
        char = readchar.readchar()
        if char in dict_mouv:
            x, y = dict_mouv[char]
            self.partie.docteur.pos_x = x
            self.partie.docteur.pos_y = y
        else:
            print("Touche invalide")
        
    def move_dalek(self) :
        x = self.partie.daleks.pos_x
        y = self.partie.daleks.pos_y

        if ((x == self.partie.docteur.pos_x) && (y == self.partie.docteur.pos_y)) :
            print("Game Over")
            # Peut etre essayer de mettre une autre valeur ici pour que ca sorte du jeu
        
        x = x + 1 if self.partie.docteur.pos_x > x else x - 1
        y = y + 1 if self.partie.docteur.pos_y > x else y - 1


    def afficher_grille(self, grille) :
        for i in grille :
            print(i)


class Controleur():
    def __init__(self):
        self.partie = Partie(12,8)
        for _ in range(10) :
            self.vue = Vue(self.partie)
            self.vue.afficher_partie()


if __name__ == "__main__":
    c = Controleur()


