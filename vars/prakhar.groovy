def call() {
                    echo 'Hello from the shared library pipeline!'    
   parameters {
        string(name: 'BRANCH', defaultValue: 'master', description: 'Branch to build')
    }
    environment {
        GITHUB_REPO = 'https://github.com/prakhar173/Devops-Assignment-2023mt93305.git'
    }
    stages {
        stage('Example') {
            steps {
                echo 'Hello World'
            }
        }
    }
}
