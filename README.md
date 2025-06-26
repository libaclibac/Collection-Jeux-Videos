# 🎮 Gestionnaire de Jeux Vidéo

Un projet Java console permettant aux utilisateurs de :

* Créer un compte et se connecter
* Ajouter, modifier, supprimer des jeux vidéo
* Critiquer et évaluer les jeux (**1 critique et 1 évaluation max par jeu**)
* Signaler des erreurs
* Gérer une liste de souhaits
* Consulter leurs statistiques et leurs notifications

## 🚀 Technologies

* Java (programmation objet, console)
* SQLite (base de données embarquée)
* JDBC (accès base de données)

## 🛠 Fonctionnalités principales

* **Authentification** sécurisée avec hachage de mot de passe (SHA-256)
* **CRUD** complet sur les jeux vidéo (ajout, modification, suppression)
* **Critiques** et **évaluations** individuelles (**1 critique et 1 évaluation par jeu maximum**, remplacement automatique si déjà ajouté)
* **Rapports d'erreur** avec suivi du statut (ouvert, en traitement, résolu)
* **Bot (pas implémenté)** pour automatiser la gestion des jeux et suppression des critiques inapropriées
* **Liste de souhaits** personnelle pour chaque utilisateur
* **Statistiques** sur les genres de jeux ajoutés

## 📁 Arborescence simplifiée

```
src/
 ├── controller           // Logiques de navigation, interactions utilisateur
 ├── database/            // Gestion BDD et initialisation
 ├── metier/              // Entités (Utilisateur, Jeu, Evaluation, ...)
 ├── service/             // Services métiers (JeuService, AuthentificationService, ...)
 ├── utils/               // Outils divers (hash mot de passe)
 └── Main.java            // Point d'entrée (menu console)
```

## 🧪 Lancer le projet

### 1. Prérequis

* JDK 17 ou plus
* SQLite JDBC driver (dans `lib/sqlite-jdbc-3.50.1.0.jar`)

### 2. Compilation (PowerShell / Windows)

```powershell
javac -cp "lib/sqlite-jdbc-3.50.1.0.jar" -d bin (Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { $_.FullName })
```

### 3. Exécution

```bash
java -cp "bin;lib/sqlite-jdbc-3.50.1.0.jar" src.Main
```

## 📦 Génération d’un .jar (optionnel)

### 1. Compile les sources

```powershell
javac -cp "lib/sqlite-jdbc-3.50.1.0.jar" -d bin (Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { $_.FullName })
```

### 2. Crée un fichier `manifest.txt` :

```
Main-Class: src.Main
Class-Path: lib/sqlite-jdbc-3.50.1.0.jar
```

📝 **N’oublie pas** : il doit y avoir une ligne vide à la fin du fichier.

### 3. Génére le `.jar` :

```bash
jar cfm JeuVideoApp.jar manifest.txt -C bin .
```

### 4. Exécute le projet :

```bash
java -jar JeuVideoApp.jar
```

## 🧑‍🎓 Projet étudiant

Ce projet a pour objectif de mettre en pratique :

* L'héritage, le polymorphisme, les classes abstraites
* Les interfaces, les énumérations, les méthodes `final`, `override`, `static`
* L'organisation en couches (modèle - service - console)

## 📬 Auteur

Projet réalisé par ... dans le cadre d'un devoir encadré de Java.
