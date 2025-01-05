def call() {
    pipeline {
        agent any

        stages {

           stage('Checkout') {
                steps {
                    script {
                        checkoutSource()
                    }
                }
            }
            stage('Build') {
                steps {
                    script {
                        buildProject()
                    }
                }
            }
            stage('Test') {
                steps {
                    script {
                        runTests()
                    }
                }
            }
            stage('Deploy') {
                steps {
                    script {
                        deployApplication()
                    }
                }
            }
        }

        post {
            always {
                cleanup()
            }
            success {
                notifySuccess()
            }
            failure {
                notifyFailure()
            }
        }
    }
}

// Function to checkout source code
def checkoutSource() {
    echo 'Checking out the source code...'
    checkout([
        $class: 'GitSCM',
        branches: [[name: '*/main']], // Replace 'main' with your branch name
        userRemoteConfigs: [[
            url: 'https://github.com/prakhar173/Devops-Assignment-2023mt93305.git', // Replace with your repo URL
            credentialsId: 'fcf40809-39c9-4bdc-9d11-597c70edda29' // Replace with Jenkins credentials ID
        ]]
    ])
}

def buildProject() {
    echo 'Building the project...'
    // Add build logic here
    def mvnHome = tool 'maven'
    // Run Maven clean and package commands
    bat "mvn clean package"  
}

def runTests() {
    echo 'Running tests...'
    // Add test logic here
}

def deployApplication() {
    echo 'Deploying the application...'
    // Add deployment logic here
}

def cleanup() {
    echo 'Cleaning up workspace...'
}

def notifySuccess() {
    echo 'Pipeline completed successfully!'
}

def notifyFailure() {
    echo 'Pipeline failed!'
}
