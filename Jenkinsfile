pipeline {
    // 指定任务再哪个集群节点中执行
    agent any

    // 声明全局变量，方便后面使用
    environment {
        key = 'value'
    }

    stages {
        stage('拉取git仓库代码') {
            steps {
               checkout scmGit(branches: [[name: '${tag}']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/JerryXu008/jenkins_CIDI.git']])
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
                sh '''mv ./target/*.jar ./docker/
                docker build -t ${JOB_NAME}:${tag} ./docker/'''
            }
        }

        stage('将自定义镜像推送到Harbor') {
            steps {
                
    sh '''
        docker login -u xujinlei008 -p xujinlei7882273
        docker tag ${JOB_NAME}:${tag} xujinlei008/${JOB_NAME}:${tag}
        docker push xujinlei008/${JOB_NAME}:${tag}
    '''
 
            }
        }

        //stage('通过Publish Over SSH通知目标服务器') {
        stage('将yml传到k8s主节点中'){  
        steps {
                 //这是docker 构建方式
                // sshPublisher(publishers: [sshPublisherDesc(configName: 'ssh-helper', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: "deploy.sh xujinlei008 ${JOB_NAME} $tag 8090", execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
                //这是k8s构建方式
                sshPublisher(publishers: [sshPublisherDesc(configName: 'k8s-helper', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: 'pipeline.yml')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
        }
    }
}
