Executable:

Résumé : 
Ce dépôt contient une implémentation du jeu de société Catan en Java. L'objectif est de recréer l'expérience de jeu de Catan en utilisant le langage de programmation Java. Le projet est développé par Radouane Aouini, Claire Chambaz,Marc Robin, Gabriel Treca et Zhula XXX

Comment lancer un jeu ?
- Tout d'abbord il faut cliquer sur le bouton "paramètres" du menu pour initialiser les joueurs et pouvoir lancer une partie. Il faut en initialiser 4 pour jouer entre 4 joueurs, si vous en initialisez moins, le reste des joueurs seront des bots (pour l'instant il n'y a pas de bots fonctionnels). 
- Ensuite vuos pouvez lancer une partie en cliquant sur le bouton "jouer". 
- Chaque joueur doit placer 1 village et 1 route tour à tour, deux fois. 
- Maintenant le jeu est lancé et c'est aux joueurs de jouer

Comment jouer ?
1- phase de lancer de dés (pioche ressources, éventuellement défausse sur un 7)
2- phase de commerce (échanger des ressources avec des joueurs ou des ports)
3- phase principale (construction/achat…)
    - vous pouvez décider de construire une route (argile+bois), une colonie (argile+bois+mouton+blé) 
      ou une ville (2blés+3pierres)
    - vous pouvez acheter une carte développement au hasard (blé+mouton+pierre)
4- vous pouvez jouer une carte développement

Comment gagner ?
Le premier joueur à ateindre 10 points de victoie gagne la partie

Cartes développement:
- 5 cartes point de victoire
- 14 cartes chevaliers (permet de déplacer le voleur et voler un joueur)
- 6 cartes progrès (appliquer l'effet de la carte)

Extension météo:
- Soleil: donne la possibilité au joueur de relancer les dés s’il le souhaite.
- Nuage: empêche le voleur d’être déplacé. 
- Pluie: permet de gagner une ressource supplémentaire de bois ou de blé (si les dés activent une tuile forêt ou champ)
- Vent: permet de gagner une ressource supplémentaire de pierre ou d’argile (si les dés activent une tuile montagne ou collines)
- Neige: empêche le joueur de construire une route.