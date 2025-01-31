def call() {
    pipeline {
        agent any
    tools {
        maven 'maven'  // Make sure this name matches what you have configured in Jenkins
    }
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
        branches: [[name: '*/master']], // Replace 'main' with your branch name
        userRemoteConfigs: [[
            url: 'https://github.com/prakhar173/Devops-Assignment-2023mt93305.git', // Replace with your repo URL
            credentialsId: '5d9ba77a-0db4-4cb2-8708-272f5f035762' // Replace with Jenkins credentials ID
        ]]
    ])
}

def buildProject() {
    echo 'Building the project...'
    // Add build logic here
    def mvnHome = tool 'maven'
    // Run Maven clean and package commands
    sh 'ls -al'
    sh "mvn clean package" 
    // sh 'docker build -t my-java-app:latest .'

 withCredentials([usernamePassword(credentialsId: 'c99b9d7c-35a5-48be-9c8c-6a9f2ee38ee5', 
                                   usernameVariable: 'DOCKER_USER', 
                                   passwordVariable: 'DOCKER_PASS')]) {
                            sh '''
                                echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                                docker build -t my-java-app:latest .
                                docker tag  my-java-app:latest $DOCKER_USER/my-java-app:latest
                                docker push $DOCKER_USER/my-java-app:latest
                            '''
               }
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
