Project Name:
Description
GESCOBK est une API RESTful développée avec Spring Boot. 
Elle permet la Gestion des clients, des comptes et opérations d'une Banque (Basic).

Technologies utilisées
Java - Version 21 ou supérieure
Spring Boot - Version 3.3.3
Maven - Pour la gestion des dépendances
MySQL - Pour la gestion de la base de donnée

Prérequis
Java JDK 21 ou supérieur
Maven 3.3.3 ou supérieur
[Base de données] (ex: MySQL)

Installation
Clonez le projet :

bash
Copier le code

  git clone https://github.com/adama455/gescptbank.git

cd nom-du-repo

Configurez la base de données :

Modifiez le fichier application.properties ou application.yml dans src/main/resources pour configurer les informations de votre base de données :

properties

Copier le code: 

  spring.datasource.url=jdbc:mysql://localhost:3306/votre_base_de_donnees
  
  spring.datasource.username=votre_utilisateur
  
  spring.datasource.password=votre_mot_de_passe

  spring.jpa.hibernate.ddl-auto=update
  
  spring.jpa.show-sql=true
  
Compilez et exécutez l'application :


Avec Maven :

bash
  Copier le code
  
  mvn clean install
  
  mvn spring-boot:run


Auteurs:

Adama NDIAYE - Développeur full stack - adama455](https://github.com/adama455)

Collaborateur - Contributions supplémentaires - Collaborateur GitHub
