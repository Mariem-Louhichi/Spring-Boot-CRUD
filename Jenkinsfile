pipeline {
    agent any

    tools {
        maven 'maven-3'
        jdk 'jdk17'
    }

    stages {

       stage('Checkout') {
    steps {
        checkout([$class: 'GitSCM',
            branches: [[name: "*/${env.BRANCH_NAME}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions: [[$class: 'LocalBranch', localBranch: "${env.BRANCH_NAME}"]],
            userRemoteConfigs: [[
                url: 'https://github.com/Mariem-Louhichi/Spring-Boot-CRUD.git',
                credentialsId: 'JenkinsGithubCredientials'
            ]]
        ])

        script {
            // Get the last commit message
            def commitMessage = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim()
            echo "Last commit message: ${commitMessage}"

            // Skip the build if it contains [maven-release-plugin]
            if (commitMessage.contains("[maven-release-plugin]")) {
                echo "⏭️ Skipping build because last commit was made by Maven Release Plugin"
                currentBuild.result = 'NOT_BUILT'
                return
            }
        }
    }
}


        stage('Clean old tags') {
            when {
                expression { currentBuild.result != 'NOT_BUILT' }
            }
            steps {
                sh '''
                    git fetch --prune
                    for tag in $(git tag -l "pl-*"); do
                        git tag -d "$tag" || true
                        git push origin ":refs/tags/$tag" || true
                    done
                '''
            }
        }

        stage('Release') {
            when {
                expression { currentBuild.result != 'NOT_BUILT' }
            }
            steps {
                withCredentials([usernamePassword(credentialsId: 'JenkinsGithubCredientials', usernameVariable: 'USER', passwordVariable: 'TOKEN')]) {
                    configFileProvider([configFile(fileId: 'f004d16f-f267-4765-9ab3-27d71dd560c0', variable: 'MyGlobalSettings')]) {
                        script {
                            sh '''
                                # Create temporary Git askpass script
                                echo '#!/bin/sh' > askpass.sh
                                echo 'echo $TOKEN' >> askpass.sh
                                chmod +x askpass.sh
                                export GIT_ASKPASS=$(pwd)/askpass.sh

                                # Run Maven release
                                mvn -B -e --settings $MyGlobalSettings \
                                    -Darguments="-Dmaven.test.skip=true -Dmaven.javadoc.skip=true" \
                                    release:prepare release:perform

                                # Cleanup
                                rm askpass.sh
                            '''
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Release deployed successfully on branch: ${env.BRANCH_NAME}"
        }
        failure {
            echo "❌ Release failed on branch: ${env.BRANCH_NAME}"
        }
        notBuilt {
            echo "⏭️ Build skipped on branch: ${env.BRANCH_NAME} (Maven Release Plugin commit)"
        }
    }
}
