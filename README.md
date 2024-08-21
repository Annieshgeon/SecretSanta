# Secret Santa Project

This is a Java application that automates the Secret Santa gift exchange process for employees at Acme. The application assigns each employee a "secret child" to whom they will anonymously give a gift during the event. It ensures that no one is assigned to themselves and avoids repeat assignments from previous years.

## Features

- Read employee information from a CSV file.
- Assign secret children while following specific rules:
  - An employee cannot choose themselves as their secret child.
  - An employee cannot be assigned the same secret child as in the previous year.
  - Each employee must have exactly one secret child.
  - Each secret child is assigned to only one employee.
- Output the assignments to a new CSV file.

## Prerequisites

- Java 1.8 or higher
- Apache Maven
- An IDE such as Eclipse

