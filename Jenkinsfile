pipeline {
    agent any

    stages {
        stage('build') {
            steps {
                echo "Build"
            }
        }
          stage('test') {
                    steps {
                        sh 'mvn --version'
                    }
                }
            stage('checkout') {
                      steps {
                          sh 'mvn --version'
                      }
                  }
    }
}
