# Doojons and Dragons 🎲  
[English version below](#english-version)

Projet pédagogique en Java (POO / jeu) : mini RPG textuel mettant en oeuvre la programmation orientée objet, l'héritage, les interfaces et la gestion basique d'une boucle de jeu, d'inventaire et de sauvegarde.

---

## 👥 Contributeurs
| Nom | GitHub |
|-----|--------|
| Arthur ALEXANDRE | [@ArthurALEXANDRE-29](https://github.com/ArthurALEXANDRE-29) |
| (Autres contributeurs) |  |

---

## 🖼️ Aperçu

| Attaquer | Tour du joueur | Map du jeu |
|---------:|:--------------:|:----------:|
| ![Attaquer](assets/Attaquer.png) | ![Tour du joueur](assets/Tour_joueur.png) | ![Map du jeu](assets/Map.png) |


---

## ✨ Fonctionnalités (résumé)
- Création et gestion de personnages (classes, statistiques, inventaire)
- Système de combat tour par tour basique
- Objets consommables et équipements
- Ennemis et rencontres aléatoires
- Gestion de l'expérience et montée de niveau
- Sauvegarde / chargement simple (fichiers)
- Architecture orientée objet (héritage, interfaces, encapsulation)

---

## 🚀 Installation & Lancement (Version Simplifiée)

Prérequis :
- JDK 11+ installé
- Un terminal ou un IDE Java (IntelliJ IDEA, Eclipse, VS Code)

1. Cloner le projet  
   ```bash
   git clone https://github.com/ArthurALEXANDRE-29/doojons-and-dragons.git
   ```
2. Si le projet n'utilise pas de build tool (fichiers .java dans src/) :
   - Compiler :
     ```bash
     javac -d out $(find src -name "*.java")
     ```
   - Lancer (adapter le nom du package / de la classe principale) :
     ```bash
     java -cp out com.monnom.paquetage.Main
     ```
3. Si Maven est utilisé :
   ```bash
   mvn package
   java -jar target/nom-du-jar.jar
   ```
4. Si Gradle est utilisé :
   ```bash
   ./gradlew build
   java -jar build/libs/nom-du-jar.jar
   ```

---

## 📁 Structure (suggestion / simplifiée)
```
.
├── README.md
├── .gitignore
├── src/                # sources Java
│   └── main/ (ou package root)
├── assets/              # captures d'écran
│   ├── Attaquer.png
│   ├── Tour_joueur.png
│   └── Map.png
├── data/               # fichiers de sauvegarde exemple
└── build/ target/ out/ # sorties de build (générées)
```

---

## 📋 Cahier des charges (avancement)
| Élément | Statut |
|---------|--------|
| Création personnage | ✔️ |
| Système de combat | ✔️ |
| Inventaire / objets | ✔️ |
| Ennemis / rencontres | ✔️ |
| Sauvegarde / chargement | ✔️ |
| Ajouts UI (console améliorée) | ⚪ |
| Tests unitaires | ⚪ |

---

## 📝 Licence
Projet académique – usage pédagogique.

---

# English Version

## Overview
Educational Java project (OOP / game): a small text-based RPG showcasing object-oriented design (classes, inheritance, interfaces), a turn-based combat loop, inventory management and basic save/load.

---

## 👥 Contributors
| Name | GitHub |
|------|--------|
| Arthur ALEXANDRE | [@ArthurALEXANDRE-29](https://github.com/ArthurALEXANDRE-29) |
| (Other contributors) |  |

---

## 🖼️ Screenshots

| Attack | Player turn | Game map |
|------:|:------------:|:--------:|
| ![Attack](assets/Attaquer.png) | ![Player turn](assets/Tour_joueur.png) | ![Map](assets/Map.png) |


---

## ✨ Features (summary)
- Character creation and management (classes, stats, inventory)
- Turn-based combat system
- Consumables and equipment
- Enemies and random encounters
- Experience and leveling
- Simple save/load to files
- OOP-based architecture (inheritance, interfaces, encapsulation)

---

## 🚀 Quick Setup
Requirements:
- JDK 11+ installed
- Terminal or Java IDE (IntelliJ IDEA, Eclipse, VS Code)

1. Clone repository  
   ```bash
   git clone https://github.com/ArthurALEXANDRE-29/doojons-and-dragons.git
   ```
2. If no build tool:
   ```bash
   javac -d out $(find src -name "*.java")
   java -cp out com.my.package.Main
   ```
3. With Maven:
   ```bash
   mvn package
   java -jar target/name-of-jar.jar
   ```
4. With Gradle:
   ```bash
   ./gradlew build
   java -jar build/libs/name-of-jar.jar
   ```

Adjust commands to match the actual package and main class.

---

## 📁 Structure (simplified)
```
Repository root
├── README.md
├── .gitignore
├── src/
├── assets/
│   ├── Attaquer.png
│   ├── Tour_joueur.png
│   └── Map.png
├── data/
└── build/ target/ out/
```

---

## 📋 Requirements Progress
| Item | Status |
|------|--------|
| Character system | ✔️ |
| Combat system | ✔️ |
| Inventory / items | ✔️ |
| Enemies / encounters | ✔️ |
| Save / load | ✔️ |
| Console UI improvements | ⚪ |
| Unit tests | ⚪ |

---

## 📝 License
Academic project – educational use.
