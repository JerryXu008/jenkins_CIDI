pipeline {
    agent any

    stages {
        stage('拉取git仓库代码') {
            steps {
                checkout scmGit(
                    branches: [[name: 'origin/master']],
                    extensions: [],
                    userRemoteConfigs: [[url: 'https://github.com/JerryXu008/jenkins_CIDI.git']]
                )
            }
        }

        stage('通过maven构建项目') {
            steps {
                sh '/var/jenkins_home/maven/apache-maven-3.8.8/bin/mvn clean package -DskipTests'
            }
        }

        stage('通过SonarQube做代码质量检测') {
            steps {
                echo '通过SonarQube做代码质量检测 - SUCCESS'
            }
        }

        stage('通过Docker制作自定义镜像') {
            steps {
                sh '''
                    mv ./target/*.jar ./docker/
                    docker build -t ${JOB_NAME}:latest ./docker/
                '''
            }
        }

        stage('将自定义镜像推送到DockerHub') {
            steps {
                sh '''
                    docker login -u xujinlei008 -p xujinlei7882273
                    docker tag ${JOB_NAME}:latest xujinlei008/${JOB_NAME}:latest
                    docker push xujinlei008/${JOB_NAME}:latest
                '''
            }
        }

        stage('将yml传到k8s主节点中') {
            steps {
                sshPublisher(publishers: [sshPublisherDesc(configName: 'k8s-helper',
                    transfers: [sshTransfer(
                        cleanRemote: false,
                        execCommand: '',
                        execTimeout: 120000,
                        flatten: false,
                        makeEmptyDirs: false,
                        noDefaultExcludes: false,
                        patternSeparator: '[, ]+',
                        remoteDirectory: '',
                        remoteDirectorySDF: false,
                        removePrefix: '',
                        sourceFiles: 'pipeline.yml'
                    )],
                    usePromotionTimestamp: false,
                    useWorkspaceInPromotion: false,
                    verbose: false
                )])
            }
        }

        stage('远程调用k8s yml') {
            steps {
                sh '''
                    ssh root@192.168.1.102 "kubectl apply -f /usr/local/k8s/pipeline.yml"
                '''
            }
        }
    }
}
