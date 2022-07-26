// ====================== PIPELINE ========================

pipeline {
  agent any

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
}
