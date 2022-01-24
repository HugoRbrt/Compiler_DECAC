# Projet Génie Logiciel, Ensimag.
gl49, 01/01/2022.

## Mise en place de l'environnement

Pour la compilation à destination de IMA, le PATH doit être configuré
comme montré dans le poly-projet-GL.pdf à la section 3.1 du chapitre
[Séance Machine].

Pour la compilation à destination de ARM, il faudra préalablement lancer
l'installation du compilateur croisé grâce à la ligne de commande :
```sudo apt-get arm-linux-gnueabihf``` et ```sudo apt-get qemu-user```.

## Utilisation du compilateur

- Pour lancer le compilateur decac à destination de IMA, sur un ou plusieurs fichiers .deca,
on utilise la commande :
```decac <fichier1.deca> <fichier2.deca> ...```.

Les options sont décrites dans le manuel utilisateur situés dans le
répertoire ```docs```.

Les fichiers ```.ass``` générés par défaut peuvent être exécutés par
```ima <file.ass>```.

- Pour la compilation vers une cible ARM, on utilise l'option ```-a```
de decac. (i.e. ```decac -a <file1.deca> <file2.deca> ...```).

Les fichiers ```.s``` générés par défaut peuvent être exécutés par
```ARM-exec.sh <filename-without-.s>``` (à supposer que l'environnement ARM ait bien été installé).

En revanche, nous avons choisi de laisser l'exécutable généré.
