# language: fr
Fonctionnalité: Gestion du cycle de vie du catalogue nosm-catalog
  En tant que gestionnaire de catalogue
  Je veux piloter le statut d'un catalogue (Création, Modification, Soumission, Validation, Clôture)
  Afin de garantir la conformité des règles de tarification avant leur activation

  Contexte:
    Étant donné que l'utilisateur connecté est un gestionnaire avec le code "UTXXXX"

  Scénario: Création réussie d'un nouveau catalogue en mode Draft
    Quand je crée un nouveau catalogue avec les informations suivantes :
      | Type | Nom                          | Devise par défaut |
      | Cash | Threshold Based volume/amount | EUR               |
    Alors un nouveau catalogue est généré avec le numéro de version "9.1"
    Et le statut du catalogue doit être "DRAFT"
    Et la date de création doit être la date du jour
    Et l'identifiant unique du catalogue "Catalog Unique Id" doit être généré

  Scénario: Modification autorisée d'un catalogue au statut DRAFT
    Étant donné un catalogue existant au statut "DRAFT"
    Quand je mets à jour la date d'activation avec "19/05/2026"
    Et que je saisis la description "Mise à jour des seuils de volume"
    Alors les modifications sont enregistrées avec succès
    Et la "Last Modification Date" est mise à jour avec la date du jour
    Et l'auteur de la modification est enregistré comme "UTXXXX"

  Scénario Échec de modification sur un catalogue soumis ou validé
    Étant donné un catalogue existant au statut "SUBMITTED"
    Quand je tente de modifier la description du catalogue
    Alors le système refuse la modification
    Et un message d'erreur s'affiche : "Le catalogue ne peut être modifié qu'au statut DRAFT."

  Scénario: Soumission d'un catalogue pour validation
    Étant donné un catalogue au statut "DRAFT" contenant au moins 1 produit configuré
    Quand je clique sur le bouton "Submit Catalog"
    Alors le statut du catalogue passe à "SUBMITTED"
    Et la "Submission Date" est alimentée avec la date du jour

  Scénario: Validation finale du catalogue soumis
    Étant donné un catalogue au statut "SUBMITTED"
    Quand un valideur approuve le catalogue
    Alors le statut du catalogue passe à "VALIDATED"
    Et la "Validation Date" est enregistrée

  Scénario: Clôture d'un catalogue actif
    Étant donné un catalogue au statut "VALIDATED"
    Quand je demande la clôture du catalogue
    Alors le statut du catalogue passe à "CLOSED"
    Et la "Closure Date" est enregistrée

  Scénario: Suppression d'un catalogue au statut DRAFT
    Étant donné un catalogue au statut "DRAFT"
    Quand je clique sur "Delete current draft version"
    Alors le catalogue est définitivement supprimé du système
	

# language: fr
Fonctionnalité: Gestion de la portée (Scope) du catalogue
  En tant que gestionnaire de catalogue
  Je veux associer des entités juridiques et des segments de clientèle à mon catalogue
  Afin de restreindre l'application des tarifs

  Scénario: Ajout d'entités au périmètre du catalogue
    Étant donné un catalogue au statut "DRAFT"
    Quand j'ajoute les entités suivantes au catalogue :
      | Entity Code | Entity Name      |
      | 111111      | XX-XXX (LONDON)  |
      | 22222       | XX-XXX (INDIA)   |
    Alors l'onglet "ENTITIES" affiche un compteur égal à 2

  Scénario: Configuration d'un segment avec liste d'exception
    Étant donné un catalogue au statut "DRAFT"
    Quand j'accède à l'onglet "SEGMENTS"
    Et que j'ajoute le segment "External Financial Institution"
    Et que je sélectionne "No" à l'option "Exceptional List"
    Alors le segment est enregistré sans exemption de la commission XXX/YYY
    Et l'onglet "SEGMENTS" affiche un compteur égal à 1


# language: fr
Fonctionnalité: Configuration des Produits & Services et de leur tarification
  En tant que gestionnaire de catalogue
  Je veux ajouter et paramétrer des produits financiers (codes applicatifs, options de facturation, flags de commission)
  Afin de définir précisément le comportement du moteur de facturation

  Scénario: Ajout d'un produit avec tarification par palier de volume (Volume Range)
    Étant donné un catalogue au statut "DRAFT"
    Quand j'ajoute un produit au catalogue avec les caractéristiques d'identification suivantes :
      | Unique Product ID | Product Version ID | Family              | Sub Family          | Product                         | Billing Code |
      | 11111             | 22222              | family_1            | sub_family_1        | product_type_1                  | 000000001    |
    Et que je configure la portée (Scope) sur :
      | Entities            | Segments |
      | Selection (XX-XXX)  | All      |
    Et que je définis les options de facturation (Billing Options) :
      | Kind of fees  | Billing Mode | Calculation Mode       | Price Unit       | Price Type   | Price | Currency |
      | Recurring fees| Deferred     | Number of transactions | Per transaction  | Volume Range | 1     | EUR      |
    Alors le produit est ajouté à la liste "PRODUCTS & SERVICES"
    Et le compteur global des produits passe à 14

  Scénario: Configuration des codes applicatifs et comptables d'un produit
    Étant donné un produit en cours de configuration
    Quand je saisis les codes applicatifs requis :
      | Type de Code    | Valeur  |
      | Accounting code | NOTAPP  |
      | AFP code        | NOTAPP  |
      | CRT code        | NOTAPP  |
      | GPP             | 1       |
    Alors ces codes d'imputation technique sont rattachés au produit

  Scénario: Configuration des indicateurs de type de commission (Commission Type)
    Étant donné un produit en cours de configuration
    Quand je configure les indicateurs de type de commission (Commission Type) :
      | Repair Corporate Flag | Repair FI Commercial Payment | Repair FI Financial Payment | Correspondent Flag | Change Flag |
      | No                    | No                          | No                          | No                 | No          |
    Alors le moteur applique la configuration standard de non-réparation pour ce produit

  Scénario: Configuration des preuves de facturation (Billing Proof)
    Étant donné un produit en cours de configuration
    Quand je configure la section "Billing Proof" :
      | Repair Fees | OUR Commission | Claims |
      | No          | No             | No     |
    Alors les preuves de facturation associées sont désactivées


	
