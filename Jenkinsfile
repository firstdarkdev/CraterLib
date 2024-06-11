pipeline {
    stages {
        stage("Build LTS Folders") {
            steps {
            script {
              def subfolderNames = ["1.18.2", "1.19.2", "1.19.3", "1.20", "1.20.2", "1.20.4"]
              boolean hasFailure = false

              for (def subfolderName in subfolderNames) {
                def subfolderPath = "${WORKSPACE}/${subfolderName}"

                if (dir(subfolderPath).exists()) {
                  try {
                    def subfolderJob = load "${subfolderPath}/.jenkins/Jenkinsfile.snapshot"
                    subfolderJob()
                  } catch (Exception e) {
                    echo "Error running pipeline for ${subfolderPath}: ${e.message}"
                    hasFailure = true
                  }
                } else {
                  echo "Skipping non-existent folder: ${subfolderPath}"
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