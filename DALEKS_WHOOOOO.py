# Yanis Boumazouzi

# Faire en sorte que les pouvoirs du docteur fonctionnent -- importer un switch
# Faire bouger les daleks
# Si les daleks se trouvent dans la meme case, il seront mort


import random
import readchar
import os

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
        # self.dalek = Daleks(self)
        # self.ferraile = Ferraille(self) # Est-ce utile ?
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

    def move_doc (self) : 
        x = self.docteur.pos_x
        y = self.docteur.pos_y
    
        dict_mouv = {
            "1": [x - 1, y + 1],
            "2": [x,     y + 1],
            "3": [x + 1, y + 1],
            "4": [x - 1, y],
            "5": [x,     y],
            "6": [x + 1, y],
            "7": [x - 1, y - 1],
            "8": [x,     y - 1],
            "9": [x + 1, y - 1],
            #"t": self.teleport(x, y),
            #"z": self.zapper(self.partie.daleks)
        }
        print('Appuyez sur une touche :')
        char = readchar.readchar()
        if char in dict_mouv:
            x, y = dict_mouv[char]
            x, y = self.collision(x,y)
            self.docteur.pos_x = x
            self.docteur.pos_y = y
        else:
            print("Touche invalide")

    def move_dalek(self, daleks) :

        mort = []

        for i in daleks:
            for j in daleks:
                if (i != j) and (i.pos_x == j.pos_x) and (i.pos_y == j.pos_y) :
                    if i not in mort :
                        mort.append(i)
                        mort.append(j)
        
        for d in mort :
            mort.remove(d)



        for dalek in daleks:
            if dalek.pos_x < self.docteur.pos_x:
                dalek.pos_x += 1
            elif dalek.pos_x > self.docteur.pos_x:
                dalek.pos_x -= 1

            if dalek.pos_y < self.docteur.pos_y:
                dalek.pos_y += 1
            elif dalek.pos_y > self.docteur.pos_y:
                dalek.pos_y -= 1
    
    def teleport(self, x, y) :
        x = random.randint(x - 3, x + 3)
        y = random.randint(y - 3, y + 3)

        x, y = self.collision(x, y)

        return x, y
    
    def collision(self, x, y) :
        x = min(self.taille_x - 1, x)
        x = max(0, x)

        y = min(self.taille_y - 1, y)
        y = max(0, y)
        
        
        return x, y

    def zapper(self, daleks) :
        for dalek in daleks :
            if abs(dalek.pos_x - self.docteur.pos_x) and abs(dalek.pos_y - self.docteur.pos_y)  :
                daleks.remove(dalek)
    


class Vue():
    def __init__(self, partie):
        self.partie = partie

    def afficher_info(self, info):
        print(info)

    def afficher_partie(self) :
        grille = self.creer_grille()
        self.peupler_grille(grille)
        self.afficher_grille(grille)
        print("""          
DOCTOR WHO : JEU DES DALEKS
`t` : Permet de vous téléporter dans une case aléatoire
`z` : Permet de zapper tous les daleks se trouvant à deux cases à côté de vous
Déplacer vous à l'aide des touches fléchées
        """)
        # self.move_dalek(self.partie.daleks)
        # self.move_doc()


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
            # Collision de daleks
            # if (len(grille[i.pos_y][i.pos_x]) > 1) :
            #     self.partie.daleks.pop(i)
            #     grille[i.pos_y][i.pos_x] = "X"
            grille[i.pos_y][i.pos_x] = "*"

    # def teleportation(self)

    # def move_doc (self) : 
    #     x = self.partie.docteur.pos_x
    #     y = self.partie.docteur.pos_y
    
    #     dict_mouv = {
    #         "1": [x - 1, y + 1],
    #         "2": [x,     y + 1],
    #         "3": [x + 1, y + 1],
    #         "4": [x - 1, y],
    #         "5": [x,     y],
    #         "6": [x + 1, y],
    #         "7": [x - 1, y - 1],
    #         "8": [x,     y - 1],
    #         "9": [x + 1, y - 1],
    #         "t": self.teleport(x, y),
    #         #"z": self.zapper(self.partie.daleks)
    #     }
    #     print('Appuyez sur une touche :')
    #     char = readchar.readchar()
    #     if char in dict_mouv:
    #         x, y = dict_mouv[char]
    #         self.partie.docteur.pos_x = x
    #         self.partie.docteur.pos_y = y
    #     else:
    #         print("Touche invalide")
        
    # def move_dalek(self, daleks) :
    #     for dalek in daleks:
    #         if dalek.pos_x < self.partie.docteur.pos_x:
    #             dalek.pos_x += 1
    #         elif dalek.pos_x > self.partie.docteur.pos_x:
    #             dalek.pos_x -= 1

    #         if dalek.pos_y < self.partie.docteur.pos_y:
    #             dalek.pos_y += 1
    #         elif dalek.pos_y > self.partie.docteur.pos_y:
    #             dalek.pos_y -= 1

    def afficher_grille(self, grille) :
        for i in grille :
            print(i)

    # def teleport(self, x, y) :
    #     x = random.randint(x - 3, x + 3)
    #     y = random.randint(y - 3, y + 3)

    #     return x, y
    
    # def collision(self, x, y) :

    #     x = min(self.partie.taille_x, x)
    #     y = min(self.partie.taille_y, y)
    
    # def zapper(self, daleks) :
    #     for dalek in daleks :
    #         if abs(dalek.pos_x - self.partie.docteur.pos_x) and abs(dalek.pos_y - self.partie.docteur.pos_y)  :
    #             daleks.remove(dalek)
    
class Controleur():
    def __init__(self):
        self.partie = Partie(5,5)
        self.vue = Vue(self.partie) 
        for _ in range(10):
            os.system('cls')
            self.vue.afficher_partie()
            self.partie.move_doc()
            self.partie.move_dalek(self.partie.daleks)
            # self.partie.move_doc(self.daleks)


if __name__ == "__main__":
    c = Controleur()


