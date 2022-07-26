// ====================== PIPELINE ========================

pipeline {
  agent any
  options {
    // This is required if you want to clean before build
    skipDefaultCheckout(true)
  }
  stages {
    stage('Build') {
      steps {
        withMaven {
          sh "mvn clean verify -DskipTests"
        }
      }
    }

    stage('Unit Tests') {
        steps {
        withMaven {
          sh "mvn test"
        }          
    }
}
