pipeline {
    agent { docker 'maven:3.3.1' }
    stages {
        stage('build') {
            steps {
                sh 'mvn clean install -DskipTests=true'
            }
            /* Restructure the Groovy Script to call function
                def pipeline
                node('slave') {
                    pipeline = load 'postBuild.groovy'
                    pipeline.postToSnow()
                }
                pipeline.functionB()
            */
            /* post { 
                always { 
                    echo 'Calling the Post BUILD Action !!!'
                }
            } */
        }
        stage('test') {
            steps {
                sh 'mvn clean test'
            }
            /* Restructure the Groovy Script to call function
                def pipeline
                node('slave') {
                    pipeline = load 'postTest.groovy'
                    pipeline.postToSnow()
                }
                pipeline.functionB()
            */
            /* post { 
                always { 
                    echo 'Calling the Post TEST Action !!!'
                }
            } */
        }
    }
}