node () {
        stage ('Code Checkout')
        {
            checkout changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/ritubgp/customer-openshift-service']]]
        }
        stage ('Build')
        {
            echo "Checkout completed. Starting the build"
            withMaven(maven: 'maven-latest') {
                sh 'mvn clean install package'
                stash name:"jar", includes:"target/customer-service-*.jar"
            }
        }
        stage('unit tests') {
            withMaven(maven: 'maven-latest') {
                sh 'mvn test'   
            }
        }
        stage('Create Image Builder') {
            if (
                expression {
                openshift.withCluster() {
                  openshift.withProject() {
                      return !openshift.selector("bc", "customer-service").exists()
                      }
                     }
                    }
                )
                script {
                    openshift.withCluster() {
                        openshift.withProject() {
                            openshift.newApp "registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift~/var/lib/jenkins/jobs/customer-service-pipeline-test/workspace", "--name=customer-service"
                        }
                    }
                }
            }
        stage('Build Image') {
              script {
                openshift.withCluster() {
                  openshift.withProject() {
                      def build = openshift.selector("bc", "customer-service");
                      def startedBuild = build.startBuild("--from-file=\"./target/customer-service-0.0.1-SNAPSHOT.jar\"");
                      startedBuild.logs('-f');
                      echo "Customer service build status: ${startedBuild.object().status}";
                  }
                }
              }
          }
        stage('Tag Image') {
            script {
                openshift.withCluster() {
                    openshift.withProject() {
                        openshift.tag("customer-service"+":latest", "customer-service"+":dev")
                    }
                }
            }
        }
        stage('Deploy STAGE') {
              script {
                openshift.withCluster() {
                  openshift.withProject() {
                    if (openshift.selector('dc', 'customer-service').exists()) {
                      openshift.selector('dc', 'customer-service').delete()
                      openshift.selector('svc', 'customer-service').delete()
                      //openshift.selector('route', 'customer-service').delete()
                    }

                    openshift.newApp("customer-service").narrow("svc").expose()
                    }
              }
            }
          }
}
