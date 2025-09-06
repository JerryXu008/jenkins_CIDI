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
                echo '拉取git仓库代码 - SUCCESS'
            }
        }

        stage('通过maven构建项目') {
            steps {
                echo '通过maven构建项目 - SUCCESS'
            }
        }

        stage('通过SonarQube做代码质量检测') {
            steps {
                echo '通过SonarQube做代码质量检测 - SUCCESS'
            }
        }

        stage('通过Docker制作自定义镜像') {
            steps {
                echo '通过Docker制作自定义镜像 - SUCCESS'
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
