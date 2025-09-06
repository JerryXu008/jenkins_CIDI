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
                echo '将自定义镜像推送到Harbor - SUCCESS'
            }
        }

        stage('通过Publish Over SSH通知目标服务器') {
            steps {
                echo '通过Publish Over SSH通知目标服务器 - SUCCESS'
            }
        }
    }
}
