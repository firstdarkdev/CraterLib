pipeline {
    agent {
        label "master"
    }

    stages {
        stage("Build LTS Folders") {
            steps {
                script {
                    def subfolderNames = ["1.18.2", "1.19.2", "1.19.3", "1.20", "1.20.2", "1.20.4"]
                    boolean hasFailure = false

                    for (def subfolderName : subfolderNames) {
                        def subfolderPath = "${WORKSPACE}/${subfolderName}/.jenkins/Jenkinsfile.snapshot"

                        if (fileExists(subfolderPath)) {
                            try {
                                dir("${WORKSPACE}/${subfolderName}") {
                                    def subfolderJob = load ".jenkins/Jenkinsfile.snapshot"
                                    subfolderJob()
                                }
                            } catch (Exception e) {
                                echo "Error running pipeline for ${subfolderPath}: ${e.message}"
                                hasFailure = true
                            }
                        } else {
                            echo "Skipping non-existent Jenkinsfile: ${subfolderPath}"
                        }
                    }

                    if (hasFailure) {
                        error "One or more subfolder pipelines failed."
                    }
                }
            }
        }
    }
}
