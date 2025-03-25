Permets de traiter les manuels en mode batch de LTF

Cet UI permet de faire 6 actions distinct.
- Convertir un PDF en une serie d'image
- De renommer en lot des fichiers et de les replacer dans le bon ordre
- De crop une image
- De convertir une liste de fichier en pdf sous plusieur format
- De redimantionner des images
- Convetir en une autre extention par lot
- Et de faire de rotation par lot.

## Pre-requis

- une JRE v17 minumum
- imagemagick 7 (prenez la version portable) [liens ici](https://imagemagick.org/script/download.php)
- ghostscript [liens ici](https://ghostscript.com/releases/gsdnld.html)

Pour imagemagick installer le fichier portable dasn un répértoire quelqu'onque et recuprer le chemain que vous aller mettre dans config.json (fichier créé a la 1ere éxecution du programme)


## Démmarage

Pour commencer a utiliser l'outil il faut cliquer sur le bouton "Ouvrir" en haut a droite puis renseigner le répértoire de travail.


Attention, cet outil traite uniquement le repertoire choisi. il ne fait pas de récursivité.

De plus une fois une action choisi l'outil se figera jusqu'a la fin du traitement. c'est la ou le texte "nombre d'images" changera. une fois le label changer c'est que le traitement est fini. **Tous les traitement sont longs**


## Convertir un pdf en image

Le seul parametre c'est la densité.  
ca permet d'avoir le dpi.  
300 est un bon compromis  pour avoir plus de détails il faut cliquer sur Infos

une fois le traitement fini le pdf originale est glisser dans un repertoire qui se nomme "originals"

## Renommer 

Cet outil permet de renommer des fichiers certe mais la ou ca devient interessant c'est que l´ont peut mettre le fichier dans l'ordre ou on veut.
C'est pour ca qu'il y a les bouton up/down et pgup/pgdown.
si vous avez une liste du genre:
- 0.jpg
- 1.jpg
- 10.jpg
- 2.jpg
- 3.jpg
etc...  
ca permet de mettre le 10 a la bonne place.
le faites de cliquer sur convertir mettra cette liste au format :
- 00.jpg
- 01.jpg
- 02.jpg
...
- 10.jpg
...  
etc...  

On peux également supprimer de la la liste les fichiers 

Pour démarrer l'outil il faut cliquer sur le bouton "Reset",
placer les fichiers dans le bon ordre puis cliquez sur "Convertir"
Si vous vous trompez refaites un "Reset"

## Crop

le crop c'est rognage (en francais) ca permet de couper une image qui contient 2 feuilles de manuel pour en faire 1 image distinct.

si vous avez par exemple 02-03.jpg cela donnera:
- 02-03-1.jpg
- 02-03-2.jpg

la premiere chose a faire c'est de cliquez sur "Lister" 

les images que vous ne voulez pas crop mettez les dans la liste "A exclure"

Puis sur convertir. 

Cela créera un repertoire a la racine de celui choisi au tout debut nommer "crop" qui contiendra le resultat. Et les fichiers qui sont exclus se mettent quand meme dans le reprtoire de sortie crop mais entieres.

## Img 2 PDF

permet de convertir toutes les images du repertoire de debut dans le format que l'on souhaite. 

Cela crée un PDF au meme endroit que le repertoire de depart.
qui a pour nom, le meme nom que la 1ere image.
exemple: 00.pdf

## Resize

Permet de séléctionner les images que l'on veux redimentionner. en cliquant sur Infos1, on arrive sur la page de comment on peux ecrire un "size"
de base c'est 50% mais ca peux etre beaucoup d'autre choses.

Cliquez sur Lister. Maintenez "controle" de votre clavier pour sélectionnez plusieur fichier a convertir. 

Si vous voulez tous convertir cliquer sur "Tout séléctionner" 

De base cela crée un repertoire "resize" dans le repertoire d'origine

si on coche Ecraser les fichiers originaux, cela permet de crée le repertoire intermediaire resize pour mieux le vider et le supprimer derriere.

## Convertir

Permet de convertir toutes une floppée d'image de l'exention choisie vers du .jpg.

En effet les fichiers de sortie sont forcement en .jpg (bonne qualitée)

## Rotation
Comme sont nom l'indique cela permet de faire des rotations sur les fichiers choisis.

Comme l'onglet resize, on liste on choisis et on converetie.


