pipeline {
    agent any

    tools {
        maven 'maven-3'
        jdk 'jdk17'
    }

    stages {

        stage('Checkout') {
            steps {
                // Automatically checks out the branch that triggered this build
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Clean & Update Dependencies') {
            steps {
                // Clean local dependencies and update
                sh 'mvn dependency:purge-local-repository -DmanualInclude="org.mockito.*,net.bytebuddy.*" -DreResolve=false'
                sh 'mvn install -U'
            }
        }

        stage('Build and Deploy Snapshot') {
            steps {
                configFileProvider([configFile(fileId: 'f004d16f-f267-4765-9ab3-27d71dd560c0', variable: 'MyGlobalSettings')]) {
                    // Deploy to snapshot repository defined in pom.xml
                    sh 'mvn clean deploy -U --settings $MyGlobalSettings'
                }
            }
        }
    }

    post {
        success {
            echo "✅ Snapshot deployed successfully on branch: ${env.BRANCH_NAME}"
        }
        failure {
            echo "❌ Snapshot build or deployment failed on branch: ${env.BRANCH_NAME}"
        }
    }
}
