# Projet Génie Logiciel, Ensimag.
gl49, 01/01/2022.

## Mise en place de l'environnement

Le PATH doit être bien positionné sur le répertoire ```src/main/bin/```
et sur ```src/test/script/```.

On pourra installer l'émulateur de ARM choisi avec la commande suivante:
```sudo apt-get arm-linux-gnueabihf```.

## Utilisation du compilateur

- Pour lancer le compilateur decac, sur un ou plusieurs fichiers .deca,
on utilise la commande :
```decac <file1.deca> <file2.deca> ...```.

Les fichiers ```.ass``` générés par défaut peuvent être exécutés par
```ima <file.ass>```.

- Pour la compilation vers une cible ARM, on utilise l'option ```-a```
de decac. (i.e. ```decac -a <file1.deca> <file2.deca> ...```).

Les fichiers ```.s``` générés par défaut peuvent être exécutés par
```arm <file.s>```.




