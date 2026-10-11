## Captures des résultats

### 1. Création des tables

Hibernate crée les tables des catégories, produits, commandes, utilisateurs et sous-classes. La table `commande_produit` reçoit une clé primaire composée de `commande_id` et `produit_id`.

<img width="1812" height="967" alt="1" src="https://github.com/user-attachments/assets/2049c5bc-060a-42d1-b582-54a514bff433" />

### 2. Création des contraintes et des relations

Hibernate ajoute la contrainte d’unicité sur le nom de la catégorie ainsi que les clés étrangères pour l’héritage `JOINED` et les associations entre produits, commandes et utilisateurs.

<img width="1702" height="1010" alt="2" src="https://github.com/user-attachments/assets/f648c1bd-31fc-486b-a617-25908442da85" />

### 3. Insertion des données

Le programme insère les catégories et les produits de démonstration dans la base.

<img width="1667" height="1002" alt="3" src="https://github.com/user-attachments/assets/1a144262-c8ad-4d30-8f25-0f07151dbd2d" />

### 4. Création d’une commande

Hibernate récupère les produits nécessaires et enregistre une commande.

<img width="1632" height="1005" alt="4" src="https://github.com/user-attachments/assets/13f1e889-ee11-4dcb-a16b-3bb0de8bc71f" />

### 5. Fin de l’exécution

La console confirme la création de la commande. Hibernate ferme ensuite la connexion et supprime les tables du schéma de test.

<img width="1516" height="982" alt="5" src="https://github.com/user-attachments/assets/e4d1c760-681a-4d3b-aa09-7c6aec9c73ef" />
