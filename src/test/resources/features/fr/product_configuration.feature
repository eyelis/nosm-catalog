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