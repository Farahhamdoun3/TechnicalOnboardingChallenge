# TechnicalOnboardingChallenge
Here is an overview of the implemented features in the project:

Endpoint to list credit card transactions, with the ability to filter by amount, merchant, and status.
Ability to sort transactions by amount.
Implementation of pagination using query parameters page and size.
Use of a mock repository layer with fictional JSON data for transactions.
Transaction model including properties like id, amount, date, merchant, and status.
Transaction filter model with properties like amount, merchant, and status.
Mock repository interface with methods to retrieve transactions and apply filters.
Service that interacts with the mock repository and applies filters.
Controller with an endpoint to list transactions with filtering and pagination, and error handling for invalid filter values.
