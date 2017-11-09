pipeline {
    agent { docker 'maven:3.3.1' }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}