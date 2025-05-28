pipeline {
    agent any
    
    tools {
        jdk 'JDK-17'
    }
    
    environment {
        DEPLOY_USER = 'ec2-user'
        DEPLOY_SERVER = 'localhost'
        APP_NAME = 'tribe-backend'
        APP_PORT = '8081'
        DEPLOY_PATH = '/home/ec2-user/deploy'
        PUBLIC_IP = '15.165.161.2'
    }
    
    stages {
        stage('환경 확인') {
            steps {
                echo "배포 경로: ${DEPLOY_PATH}"
                sh 'java -version'
                sh './gradlew --version'
            }
        }
        
        stage('코드 체크아웃') {
            steps {
                echo '소스 코드 체크아웃 중'
                checkout scm
            }
        }
        
        stage('테스트') {
            steps {
                echo '테스트 실행 중'
                sh './gradlew clean test --no-daemon -Dorg.gradle.jvmargs="-Xmx512m"'
            }
            post {
                always {
                    junit 'build/test-results/test/*.xml'
                }
            }
        }
        
        stage('빌드') {
            steps {
                echo 'JAR 파일 빌드 중'
                sh './gradlew build -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx512m"'
            }
        }
        
        stage('배포 준비') {
            steps {
                echo '배포 환경 준비 중'
                sh '''
                    mkdir -p ${DEPLOY_PATH}
                    mkdir -p ${DEPLOY_PATH}/backup
                    mkdir -p ${DEPLOY_PATH}/logs
                    chmod 755 ${DEPLOY_PATH}/logs
                '''
            }
        }
        
        stage('기존 애플리케이션 중지') {
            steps {
                echo '기존 애플리케이션 중지 중'
                script {
                    sh '''
                        # 기존 JAR 파일 백업
                        if [ -f ${DEPLOY_PATH}/app.jar ]; then
                            cp ${DEPLOY_PATH}/app.jar ${DEPLOY_PATH}/backup/app-$(date +%Y%m%d_%H%M%S).jar
                            echo "기존 JAR 파일 백업 완료"
                        fi
                        
                        # 기존 프로세스 종료
                        PID=$(pgrep -f "java.*${DEPLOY_PATH}/app.jar" || echo "")
                        if [ ! -z "$PID" ]; then
                            echo "기존 프로세스($PID) 종료 중"
                            kill -TERM $PID || true
                            sleep 5
                            
                            if kill -0 $PID 2>/dev/null; then
                                echo "강제 종료 실행"
                                kill -KILL $PID || true
                            fi
                        else
                            echo "실행 중인 애플리케이션이 없습니다"
                        fi
                    '''
                }
            }
        }
        
        stage('JAR 파일 배포') {
            steps {
                echo 'JAR 파일 배포 중'
                sh '''
                    cp build/libs/*.jar ${DEPLOY_PATH}/app.jar
                    echo "JAR 파일 배포 완료"
                '''
            }
        }
        
        stage('애플리케이션 시작') {
            steps {
                echo '새 애플리케이션 시작 중'
                script {
                    sh '''
                        cd ${DEPLOY_PATH}
                        
                        # 시작 스크립트 생성
                        cat > start.sh << "SCRIPT_EOF"
#!/bin/bash
export JAVA_OPTS="-Xms128m -Xmx256m -XX:+UseG1GC"
export SERVER_PORT=8081
export SPRING_PROFILES_ACTIVE=prod

nohup java $JAVA_OPTS -jar app.jar > logs/app.log 2>&1 &
echo $! > app.pid

echo "애플리케이션 시작됨 (PID: $(cat app.pid))"
SCRIPT_EOF
                        
                        chmod +x start.sh
                        ./start.sh
                        
                        echo "PID: $(cat app.pid)"
                    '''
                }
            }
        }
        
        stage('배포 확인') {
            steps {
                echo '배포 상태 확인 중'
                script {
                    sh "sleep 20"
                    
                    def maxAttempts = 6
                    def attempt = 0
                    def success = false
                    
                    while (attempt < maxAttempts && !success) {
                        attempt++
                        echo "Health Check: ${attempt}/${maxAttempts}"
                        
                        def healthCheck = sh(
                            script: """
                                curl -f -m 5 http://localhost:${APP_PORT}/actuator/health || 
                                curl -f -m 5 http://localhost:${APP_PORT} || 
                                echo "FAILED"
                            """,
                            returnStatus: true
                        )
                        
                        if (healthCheck == 0) {
                            success = true
                            echo "✅ 배포 성공"
                            echo "🌐 접속 주소: http://${PUBLIC_IP}:${APP_PORT}"
                        } else {
                            if (attempt < maxAttempts) {
                                echo "대기 중 (${attempt}/${maxAttempts})"
                                sh "sleep 10"
                            }
                        }
                    }
                    
                    if (!success) {
                        echo "❌ 헬스체크 실패, 로그 확인 중"
                        sh '''
                            echo "=== 프로세스 상태 ==="
                            ps aux | grep java | grep ${DEPLOY_PATH} || true
                            
                            echo "=== 애플리케이션 로그 ==="
                            tail -20 ${DEPLOY_PATH}/logs/app.log || true
                            
                            echo "=== 포트 확인 ==="
                            ss -tlnp | grep ${APP_PORT} || true
                        '''
                        error "배포 실패"
                    }
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo """
✅ 배포가 성공적으로 완료되었습니다
✅ 애플리케이션 주소: http://${PUBLIC_IP}:${APP_PORT}
✅ 상태 확인: http://${PUBLIC_IP}:${APP_PORT}/actuator/health
✅ 로그 확인: tail -f ${DEPLOY_PATH}/logs/app.log
✅ 중지 명령: kill \$(cat ${DEPLOY_PATH}/app.pid)
            """
        }
        failure {
            echo '''
❌ 배포에 실패했습니다
로그를 확인하여 문제를 해결해주세요
            '''
        }
    }
}