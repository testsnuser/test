pipeline {
    agent { docker 'maven:3.3.1' }
    stages {
        stage('build') {
            steps {
                sh 'mvn clean install -DskipTests=true'
            }
        }
        post { 
            always { 
                echo 'Calling the Post BUILD Action !!!'
            }
        }
        stage('test') {
            steps {
                sh 'mvn clean test'
            }
        }
        post { 
            always { 
                echo 'Calling the Post TEST Action !!!'
            }
        }
    }
    
}