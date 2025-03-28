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

- une JRE v8 minumum 
- imagemagick 7 (prenez la version portable) [lien ici](https://imagemagick.org/script/download.php)
- ghostscript [lien ici](https://ghostscript.com/releases/gsdnld.html)

Pour imagemagick installer le fichier portable dasn un répértoire quelqu'onque et recuprer le chemain que vous aller mettre dans config.json (fichier créé a la 1ere éxecution du programme)

Il faut lancer le programme via la ligne de comamnde : java -jar manuels-X.X.X.jar

X = numero de version
exemple: 1.2.0 => manuels-1.2.0.jar

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

Cet outil permet de renommer des fichiers, certes, mais la ou ca devient interessant c'est que l´ont peut mettre le fichier dans l'ordre ou on veut.
C'est pour ca qu'il y a les bouton up/down.
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

On peux également supprimer des fichiers de la liste 

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

Cela créera un repertoire a la racine de celui choisi au tout debut nommer "originals" qui contiendra les images qui sont a rogner, autrement dit les images d'origines vont dans le repertoire originals.  
Les exclus ne bougent pas, et les nouvelles images sont dans le repertoire d'origine.

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

De base cela crée un repertoire "resize" dans le repertoire d'origine avec le résiltat.

si on coche Ecraser les fichiers originaux, le repertoire resize se crée mais se supprime a la fin du traitement.

## Convertir

Permet de convertir toutes une floppée d'image de l'exention choisie vers du .jpg.

En effet les fichiers de sortie sont forcement en .jpg (bonne qualitée)

les fichiers originaux sont dans un repertoire crée qui se nomme "originals"

On peux choisir les fichiers a inclure ou exclure de la convertion car on peut directement mettre les images en couleur de gris.
Cela reduit le poids, entre autre, puis c'est plus facile pour appliquer des filtres lighter/darker.

On exclue par exemple une image en couleur, et on passe toutes les autres en gris.
et on clique sur "Lister" pour ensuite convertir en couleur celles qui restent.


## Rotation
Comme sont nom l'indique cela permet de faire des rotations sur les fichiers choisis.

Comme l'onglet resize, on liste on choisis et on converetie.


