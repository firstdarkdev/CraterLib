pipeline {
    agent {
        label "master"
    }
    tools {
        jdk "JAVA17"
    }
    stages {
        stage("Notify Discord") {
            steps {
                discordSend webhookURL: env.FDD_WH_ADMIN,
                        title: "Deploy Started: CraterLib 1.20.2 Deploy #${BUILD_NUMBER}",
                        link: env.BUILD_URL,
                        result: 'SUCCESS',
                        description: "Build: [${BUILD_NUMBER}](${env.BUILD_URL})"
            }
        }
        stage("Prepare") {
            steps {
                sh "curl https://raw.githubusercontent.com/hypherionmc/changelogs/main/craterlib/changelog-forge.md --output changelog-forge.md"
                sh "curl https://raw.githubusercontent.com/hypherionmc/changelogs/main/craterlib/changelog-fabric.md --output changelog-fabric.md"
                sh "chmod +x ./gradlew"
                sh "./gradlew clean"
            }
        }
        stage("Publish to Modrinth/Curseforge") {
            steps {
                sh "./gradlew publishMod -Prelease=true"
            }
        }
        stage("Publish to Maven") {
            steps {
                sh "./gradlew publish -Prelease=true"
            }
        }
    }
    post {
        always {
            sh "./gradlew --stop"
            deleteDir()

            discordSend webhookURL: env.FDD_WH_ADMIN,
                    title: "CraterLib 1.20.2 Deploy #${BUILD_NUMBER}",
                    link: env.BUILD_URL,
                    result: currentBuild.currentResult,
                    description: "Build: [${BUILD_NUMBER}](${env.BUILD_URL})\nStatus: ${currentBuild.currentResult}"
        }
    }
}
