# Smart Candidate Management System

A Java command-line application for managing candidate records in a MySQL database using JDBC.

## Features

- Add, view, search, update, and delete candidates
- Search by candidate ID or name
- Filter and sort candidates by CGPA
- Search by one or more skills
- Display the total number of candidates
- Validate user input and report database errors

## Requirements

- Java JDK
- MySQL Server
- MySQL Connector/J JAR in the `lib` folder

## Database setup

The application connects to a database named `candidatedb` and uses a table named `candidates`.

For a new setup, run this SQL in MySQL Workbench:

```sql
CREATE DATABASE candidatedb;
USE candidatedb;

CREATE TABLE candidates (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    college VARCHAR(150),
    branch VARCHAR(100),
    graduation_year INT,
    cgpa DECIMAL(4,2),
    skills VARCHAR(500),
    experience_years INT,
    CONSTRAINT chk_candidates_cgpa
        CHECK (cgpa >= 0 AND cgpa <= 10),
    CONSTRAINT chk_candidates_experience
        CHECK (experience_years >= 0)
);
```

If you already created this database and table, do not run the SQL again.

## Database credentials

The application reads credentials from these environment variables:

- `DB_USER` — your MySQL username
- `DB_PASSWORD` — your MySQL password

Set both variables in Windows under **Edit environment variables for your account**. Restart PowerShell or VS Code after setting them. Do not put your password in the source code or commit it to the repository.

## Compile and run on Windows

Open PowerShell in the project folder, the folder that contains `src` and `lib`, then run:

```powershell
javac -cp "lib/mysql-connector-j-9.7.0.jar" -d bin src/Candidate.java src/CandidateDAO.java src/CandidateDAOImpl.java src/DBConnection.java src/Main.java
java -cp "bin;lib/mysql-connector-j-9.7.0.jar" Main
```

## Author

Deepika Pulagam