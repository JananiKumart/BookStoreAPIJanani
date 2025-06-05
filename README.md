# 📚 BookStore API Automation Project

This project is a comprehensive **API test automation framework** for a mock BookStore application using **Java**, **RestAssured**, **TestNG**, **Cucumber**, and **Extent Reports**. It validates user registration, login, book management, and environmental configurations across **DEV**, **UAT**, **STAGE**, and **PROD**.

---

## 🚀 Tech Stack

| Component        | Version       | Purpose                                  |
|------------------|---------------|------------------------------------------|
| Java             | 21            | Programming Language                     |
| Maven            | Latest        | Build and Dependency Management          |
| REST-assured     | 5.x           | API Testing Library                      |
| TestNG           | Latest        | Test Execution Engine                    |
| Cucumber         | 7.x           | BDD-style Testing                        |
| ExtentReports    | 5.x           | Test Reporting                           |
| Jenkins          | Optional      | CI/CD Automation                         |
| WireMock         | 2.x           | Mock API Server                          |

Project structure:

BookStoreAPI/
├── JenkinsFile                         ⬅️ CI pipeline
├── pom.xml                             ⬅️ Maven dependencies
├── README.md                           ⬅️ Project documentation
├── testng.xml                          ⬅️ Test suite configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── apiController/          ⬅️ API call logic
│   │   │   ├── constants/              ⬅️ API endpoints and reusable constants
│   │   │   ├── data/                   ⬅️ Test data classes & JSON
│   │   │   └── Server/                 ⬅️ Utilities, reports, env manager
│   └── test/
│       ├── java/
│       │   ├── Hooks/                  ⬅️ Cucumber hooks (Before/After)
│       │   ├── MockServer/            ⬅️ WireMock stub logic
│       │   ├── Runner/                ⬅️ TestNG+Cucumber runner
│       │   └── Steps/                 ⬅️ Step definitions
│       └── resources/
│           ├── config/                ⬅️ Environment configs
│           └── Features/             ⬅️ Gherkin scenarios

✅ Scenarios Covered
Feature Area	Scenario
🔐 Authentication	Login, Register, Invalid credentials
📚 Book Management	Get Book List, Add Book, Update Book, Delete
🔄 User Actions	View Profile, Modify Info
⚠ Unauthorized Test	Accessing APIs without valid token
🧪 Mock Server	Controlled testing with simulated responses


1) ## 🚀 How to Set Up and Run Tests

1. **Create or Clone Project**:
   - Create a new Maven automation project or clone the GitHub repository if already present.

2. **Fork the Dev Repository**:
   - Fork the Dev repo and set it up locally. Follow its `README.md` for detailed setup instructions.

3. **Endpoints Automated in This Project**:
   - `POST /register` – Sign up to the bookstore
   - `POST /login` – Log in after sign-up and generate a token
   - `POST /books` – Create a new book
   - `PUT /books/{id}` – Update an existing book
   - `GET /books/{id}` – Fetch a book by ID
   - `GET /books` – Fetch all books
   - `DELETE /books/{id}` – Delete a book

4. **Execute the Automation Suite**:
   - Run the `testng.xml` file. This will trigger all feature scenarios written in a human-readable format.

5. **View Reports**:
   - Once test execution completes, **ExtentReports** will be generated at:
     ```
     test-output/ExtentReport/Extent.html
     ```
   - Open the HTML report in your browser to view test execution results.

---
2)
## 🔄 CI/CD Integration

### ✅ Prerequisites

- Jenkins installed with the following plugins:
   - Git
   - GitHub
   - Pipeline
   - Maven Integration
   - Environment Injector (E Plugin)


---

### ⚙️ Steps for CI/CD (Development - Testing Environment)

1. **JenkinsFile in Dev Repo**:

```groovy
pipeline {
    agent any
    stages {
        stage('Build Dev') {
            steps {
                echo 'Build or test dev code here'
            }
        }
        stage('Trigger QA Automation') {
            steps {
                build job: 'QA-Repo'
            }
        }
    }
}
3) Start Jenkins and install the required plugins.

4) Create pipeline jobs in Jenkins for both Dev and QA repos.

5) Configure GitHub repositories and link them to Jenkins jobs.

6) Add a webhook to the Dev GitHub repository pointing to your public Jenkins URL.

7) Run WireMockServer to expose your local Jenkins

8) Commit a change in Dev repo to trigger the full CI/CD chain.

9) ✅ Now Dev and QA pipelines will run automatically on every commit.