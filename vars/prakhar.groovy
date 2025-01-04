def call() {
                    echo 'Hello from the shared library pipeline!'    
   parameters {
        string(name: 'BRANCH', defaultValue: 'master', description: 'Branch to build')
    }
    environment {
        GITHUB_REPO = 'https://github.com/prakhar173/Devops-Assignment-2023mt93305.git'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: "${params.BRANCH}"]],
                          userRemoteConfigs: [[url: "https://github.com/${env.GITHUB_REPO}.git"]]])
            }
        }
      stage('git') {
                git credentialsId: 'd6c43a83-7a80-4fea-a6e4-ba1732a8c771', url: 'https://github.com/prakhar173/Devops-Assignment-2023mt93305.git'
        }
      
      stage('Build') {
        // Define the Maven executable path
        def mvnHome = tool 'maven'
        // Run Maven clean and package commands
        bat "mvn clean package"        
        }
        stage('Test') {
            steps {
                echo "Running tests for ${params.APP_NAME}"
                // Add test steps here
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying ${params.APP_NAME}"
                // Add deploy steps here
            }
        }
    }
    post {
        always {
            echo "Pipeline completed for ${params.APP_NAME}"
        }
    }
}
