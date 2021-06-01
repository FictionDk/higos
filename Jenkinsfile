pipeline {
    environment {
        ACCESS_KEY = credentials('PASS_KEY')
        ALI_KEY = credentials('ALI_SECRET_ACCESS_KEY')
        PRO_IP = '***.***.***.***'
        DEV_IP = '192.168.110.13'
    }
    agent {
        docker {
            image 'maven-scp:3.6-alpine'
            args '-v /home/hek/data/jenkins/jenkins_home/.m2:/root/.m2'
        }
    }
    options {
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deliver For dev') {
            when {
                expression {
                    currentBuild.result == null || currentBuild.result == 'SUCCESS'
                }
                branch 'dev'
            }
            steps {
                sh 'chmod u+x ci/deliver.sh'
                sh 'expect ci/deliver.sh target/*.jar higos/target $ACCESS_KEY hek $DEV_IP'
                sh 'expect ci/deliver.sh ci/Dockerfile higos $ACCESS_KEY hek $DEV_IP'
                sh 'expect ci/deliver.sh ci/deploy.sh higos $ACCESS_KEY hek $DEV_IP'
                sh 'expect ci/remote_deploy.sh higos $ACCESS_KEY hek $DEV_IP dev'
            }
        }
        stage('Deliver For master') {
            when {
                expression {
                    currentBuild.result == null || currentBuild.result == 'SUCCESS'
                }
                branch 'master'
            }
            steps {
                sh 'chmod u+x ci/deliver.sh'
                sh 'expect ci/deliver.sh target/*.jar common-server/higos/target $ALI_KEY root $PRO_IP'
                sh 'expect ci/deliver.sh ci/Dockerfile common-server/higos $ALI_KEY root $PRO_IP'
                sh 'expect ci/deliver.sh ci/deploy.sh common-server/higos $ALI_KEY root $PRO_IP'
                sh 'expect ci/remote_deploy.sh common-server/higos $ALI_KEY root $PRO_IP pro'
            }
        }
    }
}
