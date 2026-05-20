# language: fr
Fonctionnalité: Gestion du cycle de vie du catalogue
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

  Scénario: Échec de modification sur un catalogue soumis ou validé
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