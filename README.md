# 🎮 Gestionnaire de Jeux Java (Projet POO)

Ce projet Java est une application console permettant de gérer une base de données de jeux vidéo. Il met en pratique les principes de la **programmation orientée objet**, la **gestion de base de données SQLite**, la **manipulation de fichiers**, et des **structures de données Java**.

---

## 🚀 Fonctionnalités du MVP

- ✅ Authentification (compte, connexion) avec mot de passe hashé (SHA-256)
- ✅ Création, modification et suppression de jeux
- ✅ Liste de souhaits par utilisateur
- ✅ Ajout et modification de critiques / évaluations
- ✅ Statistiques sur les genres de jeux (depuis la liste de souhaits)
- ✅ Signalement d’erreurs (création de rapports)
- ✅ Import automatique de jeux via fichier JSON

---

## 📁 Structure du projet

```

📦 src/
┣ 📂controller/         → Contrôleurs (menus et logique console)
┣ 📂database/           → Base SQLite + initialisation
┣ 📂metier/             → Modèles métiers (Jeu, Utilisateur, etc.)
┣ 📂metier/gestionnaires → Actions utilisateur/bot via interface
┣ 📂service/            → Accès BDD et logique fonctionnelle
┣ 📂utils/              → Utilitaires (ex : hachage SHA-256)
┗ Main.java             → Menu principal du programme

````

---

## ⚙️ Compilation & exécution

### ✅ Prérequis
- Java JDK installé
- Bibliothèques nécessaires dans `/lib` :
    - [SQLite JDBC driver (`sqlite-jdbc-3.50.1.0.jar`)](https://github.com/xerial/sqlite-jdbc/releases)
    - [org.json (`json-20231013.jar`)](https://repo1.maven.org/maven2/org/json/json/20231013/)

### 🔨 Compilation (PowerShell Windows)

```powershell
javac -cp "lib/sqlite-jdbc-3.50.1.0.jar;lib/json-20231013.jar" -d bin (Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { $_.FullName })
````

> Sur Linux/macOS : remplace `;` par `:` dans le `-cp`.

### ▶️ Lancement

```powershell
java -cp "bin;lib/sqlite-jdbc-3.50.1.0.jar;lib/json-20231013.jar" src.Main
```

---

## 📚 Concepts démontrés

| Thème Java                     | Exemple dans le code                     |
| ------------------------------ | ---------------------------------------- |
| **POO (objets, classes)**      | `Jeu`, `Utilisateur`, `Critique`, etc.   |
| **Encapsulation**              | `private` + `getters/setters`            |
| **Interfaces / polymorphisme** | `GestionnaireJeu` et ses implémentations |
| **Collections**                | `List`, `Map`, `ArrayList`, etc.         |
| **Fichiers (JSON)**            | Import de jeux via `BotGestionnaire`     |
| **Base de données**            | SQLite avec JDBC                         |
| **Enums**                      | `StatusRapport`                          |
| **Architecture**               | Packages organisés                       |
| **Sécurité**                   | Hachage des mots de passe (`SHA-256`)    |

---

## 📦 Données et BDD

* Les jeux sont importés automatiquement depuis `jeux.json`
* La base SQLite `database.db` est générée dans `src/database/` si elle n'existe pas

---

## 📜 Auteur

Projet réalisé pour valider les acquis du module **POO en Java** (B2 - ESGI).
