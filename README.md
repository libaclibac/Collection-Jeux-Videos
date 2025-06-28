# 🎮 Gestionnaire de Jeux Vidéo

Un projet Java console permettant aux utilisateurs de :

- Créer un compte et se connecter
- Ajouter, modifier, supprimer des jeux vidéo
- Critiquer et évaluer les jeux (**1 critique et 1 évaluation max par jeu**)
- Signaler des erreurs
- Gérer une liste de souhaits
- Consulter leurs statistiques et leurs notifications

## 🚀 Technologies

- Java (programmation objet, console)
- SQLite (base de données embarquée)
- JDBC (accès base de données)
- JSON (lecture de fichiers JSON avec `org.json`)

## 🛠 Fonctionnalités principales

- **Authentification** sécurisée avec hachage de mot de passe (SHA-256)
- **CRUD** complet sur les jeux vidéo (ajout, modification, suppression)
- **Critiques** et **évaluations** individuelles (**1 critique et 1 évaluation par jeu maximum**, remplacement automatique si déjà ajouté)
- **Rapports d'erreur** avec suivi du statut (ouvert, en traitement, résolu)
- **Bot (partiellement implémenté)** pour automatiser la gestion des jeux et la suppression des critiques inappropriées
- **Liste de souhaits** personnelle pour chaque utilisateur
- **Statistiques** sur les genres de jeux ajoutés

## 📁 Arborescence simplifiée

```

src/
├── controller/        // Logique de navigation, menus, interface console
├── database/          // Connexion BDD, initialisation (SQLite + JDBC)
├── metier/            // Entités du modèle (Utilisateur, Jeu, Critique, etc.)
├── service/           // Logique métier (JeuService, EvaluationService, etc.)
├── utils/             // Fonctions utilitaires (hashage SHA-256, etc.)
└── Main.java          // Point d'entrée de l'application (menu principal)
README.md
.gitignore

````

⚠️ Les dossiers `bin/`, `lib/` et le fichier `.db` ne sont pas inclus dans le dépôt. Tu dois les créer comme indiqué ci-dessous.

## 🧰 Prérequis

- Java JDK 17 ou supérieur
- [SQLite JDBC driver (`sqlite-jdbc-3.50.1.0.jar`)](https://github.com/xerial/sqlite-jdbc/releases)
- [org.json (`json-20231013.jar`)](https://repo1.maven.org/maven2/org/json/json/20231013/)

## 🏗 Préparation de l'environnement

Avant de compiler :

```bash
mkdir bin
mkdir lib
````

Puis ajoute les fichiers suivants dans le dossier `lib/` :

* `sqlite-jdbc-3.50.1.0.jar`
* `json-20231013.jar`

## 🔧 Compilation

### Sous PowerShell (Windows)

```powershell
javac -cp "lib/sqlite-jdbc-3.50.1.0.jar;lib/json-20231013.jar" -d bin (Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { $_.FullName })
```

## ▶️ Exécution

```bash
java -cp "bin;lib/sqlite-jdbc-3.50.1.0.jar;lib/json-20231013.jar" src.Main
```

*(Sur Linux ou Mac, remplace `;` par `:` dans le classpath)*

## 📦 Génération d’un `.jar` (optionnel)

### 1. Compile les sources

```powershell
javac -cp "lib/sqlite-jdbc-3.50.1.0.jar;lib/json-20231013.jar" -d bin (Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { $_.FullName })
```

### 2. Crée un fichier `manifest.txt`

```
Main-Class: src.Main
Class-Path: lib/sqlite-jdbc-3.50.1.0.jar lib/json-20231013.jar

```

**📝 Important :** laisse une ligne vide à la fin du fichier.

### 3. Génére le `.jar`

```bash
jar cfm JeuVideoApp.jar manifest.txt -C bin .
```

### 4. Lance le `.jar`

```bash
java -jar JeuVideoApp.jar
```

## 🧑‍🎓 Projet étudiant

Ce projet a pour objectif de mettre en pratique :

* L’héritage, le polymorphisme, les classes abstraites
* Les interfaces, les énumérations, les méthodes `final`, `override`, `static`
* L’organisation en couches (modèle - service - console)

## 📬 Auteur

Projet réalisé par **BACHELIER Lili** et **BIDARD Erwan** dans le cadre d’un devoir encadré de Java.

