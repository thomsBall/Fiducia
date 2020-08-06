pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo "Build on Branch ${BRANCH_NAME}"
                echo JOB_NAME
            }
        }
        stage('Test') {
            steps {
                junit "C:/Jobintervie/Fiducia/src/test/java"

            }
        }
    }
}
