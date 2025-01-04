def call() {
    pipeline {
        agent any

        stages {
            stage('Example Stage') {
                steps {
                    echo 'Hello from the shared library pipeline!'
                }
            }
        }
    }
}
