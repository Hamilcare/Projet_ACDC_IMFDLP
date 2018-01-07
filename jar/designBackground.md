# Cédric GARCIA - Projet ACDC - designBackground.md - 15/12/2017


## 1 - Utilisation du .jar

### 1.1 - Execution du .jar

- Extraire le contenu de l'archive ACDC_IMFDLP.zip
- Se placer dans le répertoire ACDC_IMFDLP. Vérifier la présence du dossier "cache"; il doit être vide.
- Ouvrir une console.
- Executer le .jar en utilisant la commande "java -jar ACDC_IMFDLP.jar".

Cependant, le .jar ne produira aucune action si l'on ne spécifie aucun paramètre derrière la commande d'éxécution. En mode console, seules deux options sont disponibles. Elles sont détaillées dans le point 1.2.

### 1.2 - Options disponibles

- java -jar ACDC_IMFDLP.jar -a "Répertoire à partir duquel parcourir l'arborescence"
(Remplacer " " par le chemin voulu)

Cette première option d'utilisation parcours récursivement l'arborescence de l'ordinateur à partir d'un chemin donné. Une fois l'arborescence parcourue, elle est affichée dans la console.

- java -jar ACDC_IMFDLP.jar -d "Répertoire à partir duquel parcourir l'arborescence"
(Remplacer " " par le chemin voulu)

Cette seconde option parcours l'arborescence de la même manière que la première option afin de rechercher les doublons. Elle cache tous les fichiers à partir du chemin donné et affiche les doublons trouvés à partir de ce  point de départ. Une fois les fichiers cachés, l'utilisation ultérieure de cette option sera bien plus rapide car le hash md5 des fichiers aura déjà été généré une première fois.

- D'autres options ne sont pas disponibles en mode console, comme la possibilité de choisir les types de fichiers à filtrer. Cette option fonctionne mais le fait que j'utilise un tableau de filtres permettant de combiner différentes options de filtrage, cela était trop compliqué à mettre en place dans une console.


## 2 - Conception et architecture du logiciel

Le logiciel repose sur une architecture parcourant récursivement les répertoires d'une arborescence à partir d'un point d'entrée donné.

### 2.1 - La classe Node

Cette classe implémente les les fonctionnalités de l'API que nous avions déterminées. Egalement, elle implémente "Runnable" de Java afin de pouvoir fonctionner dans un Thread.

Elle est caractérisée par trois méthodes principales :

- La méthode createNode, qui créé une arborescence à partir d'un chemin donné, de façon récursive. Cette méthode rempli un "DefaultMutableTreeNode" qui porte à chaque noeud un objet de la classe "FileNode".

- La méthode doublons, qui retourne une HashMap liant un hash md5 à un (si pas de doublons) ou plusieurs fichiers s'ils sont identiques.

- La méthode hash qui génère le hash md5 des fichiers. Elle s'appuie sur la librairie org.apache.commons.codec.digest.DigestUtils.

Le parcours de l'arborescence peut être fait en appliquant un ou plusieurs filtres (extension des fichiers, répertories ou non, etc...). Ils peuvent être combinés et manipulés de manière très poussée. Ces filtres sont ensuite appliqués à la méthode listFiles du type File de Java, afin de lister les fichiers selon nos critères de filtrage. Tous ces filtres et les possibilités combinatoires reposent sur la librairie org.apache.commons.io.filefilter.

### 2.2 - La classe FileNode

Cette classe porte un attribut de type File, une méthode permettant de le retourner ainsi qu'une redéfinition de la méthode toString.

### 2.3 - La classe CacheFile

Cette classe utilisée par la méthode doublons génère des fichiers de cache de l'arborescence. pour chaque dossier, un fichier de cache est généré. Il comporte pour tous les fichiers qu'il contient le nom du fichier, son hash md5 ainsi que la dernière date de modification. La première génération du cache prend du temps. Cependant, il sera ensuite utilisé comme élément de comparaion et permettra de gagner du temps afin d'éviter de hasher à nouveau des fichiers qui n'ont pas été modifiés.


## 3 - Diagramme de classes

Lien vers le diagramme : [Diagramme de classes](diagramme.png).


## 4 - Javadoc

Lien vers la Javadoc : [Javadoc](./javadoc/index.html)


### 4.1 - Difficultées rencontrées

La plus grande difficulté du projet était de trouver une manière de parcourir l'arborescence en un temps optimal. J'ai trouvé qu'utiliser la méthode listFiles de manière récursive était un bon compromis en terme de performances. L'avantage est que de remplir récursivement un objet de type "DefaultMutableTreeNode" permet de le réutiliser ensuite pour générer un Jtree en Swing, un aspect important pour la seconde partie du projet. Pour des raisons d'éfficacité, j'ai décidé d'utiliser des librairies externes pour la génération du hash md5 et les possibilités combinatoires de filtrage.
