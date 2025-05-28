pipeline {
    agent any
    
    tools {
        jdk 'JDK-17'
    }
    
    environment {
        DEPLOY_USER = 'ec2-user'
        DEPLOY_SERVER = 'localhost'
        APP_NAME = 'tribe-backend'
        APP_PORT = '9999'
        DEPLOY_PATH = '/home/ec2-user/deploy'
        PUBLIC_IP = '15.165.161.2'
        JAR_NAME = 'tribe-0.0.1-SNAPSHOT.jar'
    }
    
    stages {
        stage('환경 확인') {
            steps {
                echo "=== 환경 정보 확인 ==="
                echo "DEPLOY PATH: ${DEPLOY_PATH}"
                echo "APPLICATION PORT: ${APP_PORT}"
                echo "PUBLIC IP: ${PUBLIC_IP}"
                sh '''
                    echo "Java Version:"
                    java -version
                    echo "Gradle Version:"
                    ./gradlew --version
                    echo "Available Memory:"
                    free -h
                    echo "Disk Space:"
                    df -h ${DEPLOY_PATH}
                '''
            }
        }
        
        stage('코드 체크아웃') {
            steps {
                echo '=== 소스 코드 체크아웃 ==='
                checkout scm
                sh 'ls -la'
            }
        }
        
        stage('설정 파일 배포') {
            steps {
                echo '=== 설정 파일 배포 ==='
                script {
                    withCredentials([file(credentialsId: 'application-config', variable: 'CONFIG_FILE')]) {
                        sh '''
                            echo "CONFIG_FILE PATH: ${CONFIG_FILE}"
                            if [ ! -f "${CONFIG_FILE}" ]; then
                                echo "ERROR: Config file not found!"
                                exit 1
                            fi
                            
                            ls -l ${CONFIG_FILE}
                            mkdir -p src/main/resources
                            cp ${CONFIG_FILE} src/main/resources/application.yml
                            echo "Configuration file deployed successfully"
                        '''
                    }
                }
            }
        }
        
        // stage('테스트') {
        //     steps {
        //         echo '=== 단위 테스트 실행 ==='
        //         sh '''
        //             ./gradlew test --no-daemon
        //         '''
        //     }
        //     post {
        //         always {
        //             // 테스트 결과 보고서 수집
        //             publishTestResults testResultsPattern: 'build/test-results/test/*.xml'
        //             // 테스트 커버리지 보고서 (JaCoCo 사용시)
        //             // publishHTML([
        //             //     allowMissing: false,
        //             //     alwaysLinkToLastBuild: true,
        //             //     keepAll: true,
        //             //     reportDir: 'build/reports/jacoco/test/html',
        //             //     reportFiles: 'index.html',
        //             //     reportName: 'JaCoCo Coverage Report'
        //             // ])
        //         }
        //     }
        // }
        
        stage('빌드') {
            steps {
                echo '=== JAR 파일 빌드 ==='
                sh '''
                    ./gradlew clean build -x test --no-daemon --refresh-dependencies
                    
                    echo "=== 빌드된 JAR 파일 확인 ==="
                    ls -la build/libs/
                    if [ ! -f "build/libs/${JAR_NAME}" ]; then
                        echo "ERROR: JAR 파일이 생성되지 않았습니다: ${JAR_NAME}"
                        exit 1
                    fi
                    echo "SUCCESS: JAR 파일 생성 완료: ${JAR_NAME}"
                    
                    # JAR 파일 크기 확인
                    echo "JAR 파일 크기:"
                    du -h "build/libs/${JAR_NAME}"
                '''
            }
            post {
                always {
                    // 빌드 아티팩트 보관
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }
        
        stage('배포 준비') {
            steps {
                echo '=== 배포 환경 준비 ==='
                sh '''
                    # 디렉토리 생성
                    mkdir -p ${DEPLOY_PATH}
                    mkdir -p ${DEPLOY_PATH}/backup
                    mkdir -p ${DEPLOY_PATH}/logs
                    
                    # 권한 설정
                    chmod 755 ${DEPLOY_PATH}
                    chmod 755 ${DEPLOY_PATH}/backup
                    chmod 755 ${DEPLOY_PATH}/logs
                    
                    # PM2 설치 확인
                    if ! command -v pm2 &> /dev/null; then
                        echo "WARNING: PM2가 설치되어 있지 않습니다."
                        echo "PM2 설치: npm install -g pm2"
                    else
                        echo "PM2 버전: $(pm2 --version)"
                    fi
                    
                    echo "배포 디렉토리 준비 완료"
                '''
            }
        }
        
        stage('기존 애플리케이션 중지') {
            steps {
                echo '=== 기존 애플리케이션 중지 ==='
                script {
                    sh '''
                        # 기존 JAR 파일 백업
                        if [ -f ${DEPLOY_PATH}/app.jar ]; then
                            BACKUP_NAME="app-$(date +%Y%m%d_%H%M%S).jar"
                            cp ${DEPLOY_PATH}/app.jar ${DEPLOY_PATH}/backup/${BACKUP_NAME}
                            echo "기존 JAR 파일 백업 완료: ${BACKUP_NAME}"
                            
                            # 오래된 백업 파일 정리 (7개 이상 유지하지 않음)
                            cd ${DEPLOY_PATH}/backup
                            ls -t app-*.jar | tail -n +8 | xargs -r rm -f
                            echo "오래된 백업 파일 정리 완료"
                        fi
                        
                        # PM2로 관리되는 프로세스 종료
                        if pm2 describe tribe-backend > /dev/null 2>&1; then
                            echo "PM2 프로세스 종료 중..."
                            pm2 stop tribe-backend || true
                            pm2 delete tribe-backend || true
                            echo "PM2 프로세스 종료 완료"
                        else
                            echo "실행 중인 PM2 프로세스가 없습니다"
                        fi
                        
                        # 혹시 남아있는 프로세스 확인 및 종료
                        PID=$(pgrep -f "java.*${DEPLOY_PATH}/app.jar" || echo "")
                        if [ ! -z "$PID" ]; then
                            echo "남아있는 프로세스($PID) 종료 중..."
                            kill -TERM $PID || true
                            sleep 5
                            
                            if kill -0 $PID 2>/dev/null; then
                                echo "강제 종료 실행"
                                kill -KILL $PID || true
                                sleep 2
                            fi
                            echo "프로세스 종료 완료"
                        else
                            echo "실행 중인 애플리케이션이 없습니다"
                        fi
                        
                        # PID 파일 정리
                        rm -f ${DEPLOY_PATH}/app.pid
                    '''
                }
            }
        }
        
        stage('JAR 파일 배포') {
            steps {
                echo '=== JAR 파일 배포 ==='
                sh '''
                    # JAR 파일 존재 확인
                    if [ ! -f "build/libs/${JAR_NAME}" ]; then
                        echo "ERROR: JAR 파일을 찾을 수 없습니다: build/libs/${JAR_NAME}"
                        exit 1
                    fi
                    
                    # JAR 파일 복사
                    cp "build/libs/${JAR_NAME}" "${DEPLOY_PATH}/app.jar"
                    chmod +x "${DEPLOY_PATH}/app.jar"
                    
                    echo "JAR 파일 배포 완료"
                    ls -la "${DEPLOY_PATH}/app.jar"
                    
                    # JAR 파일 무결성 확인
                    if java -jar "${DEPLOY_PATH}/app.jar" --help > /dev/null 2>&1; then
                        echo "JAR 파일 무결성 확인 완료"
                    else
                        echo "WARNING: JAR 파일 무결성 확인 실패 (Spring Boot JAR의 경우 정상일 수 있음)"
                    fi
                '''
            }
        }
        
        stage('애플리케이션 시작') {
            steps {
                echo '=== 새 애플리케이션 시작 ==='
                script {
                    withCredentials([
                        string(credentialsId: 'rds-host', variable: 'DB_HOST'),
                        string(credentialsId: 'rds-database', variable: 'DB_NAME'),
                        string(credentialsId: 'rds-username', variable: 'DB_USERNAME'),
                        string(credentialsId: 'rds-password', variable: 'DB_PASSWORD')
                    ]) {
                        sh '''
                            cd ${DEPLOY_PATH}

                            echo "RDS 연결 정보 로드 완료"
                            echo "데이터베이스: MariaDB (${DB_HOST}:3306/${DB_NAME})"

                            # ecosystem.config.js 파일 생성 (PM2 설정)
                            cat > ecosystem.config.js <<CONFIG_EOF
module.exports = {
  apps: [{
    name: 'tribe-backend',
    script: 'app.jar',
    interpreter: 'none',
    cwd: '${DEPLOY_PATH}',
    instances: 1,
    autorestart: true,
    watch: false,
    max_memory_restart: '512M',
    env: {
      NODE_ENV: 'production',
      SPRING_PROFILES_ACTIVE: 'prod',
      SERVER_PORT: '${APP_PORT}',
      DB_HOST: '${DB_HOST}',
      DB_PORT: '3306',
      DB_NAME: '${DB_NAME}',
      DB_USERNAME: '${DB_USERNAME}',
      DB_PASSWORD: '${DB_PASSWORD}'
    },
    args: [
      '-Dspring.profiles.active=prod',
      '-Dserver.port=${APP_PORT}',
      '-Dlogging.file.path=${DEPLOY_PATH}/logs',
      '-Djava.awt.headless=true',
      '-Dfile.encoding=UTF-8',
      '-Duser.timezone=Asia/Seoul'
    ],
    log_file: '${DEPLOY_PATH}/logs/combined.log',
    out_file: '${DEPLOY_PATH}/logs/out.log',
    error_file: '${DEPLOY_PATH}/logs/err.log',
    time: true,
    log_date_format: 'YYYY-MM-DD HH:mm:ss Z'
  }]
};
CONFIG_EOF

                            echo "PM2 설정 파일 생성 완료"
                            
                            # PM2로 애플리케이션 시작
                            pm2 start ecosystem.config.js
                            pm2 save
                            
                            echo "애플리케이션 시작 완료"
                            pm2 status
                        '''
                    }
                }
            }
        }
        
        stage('배포 확인') {
            steps {
                echo '=== 배포 상태 확인 ==='
                script {
                    echo "애플리케이션 초기화 대기 중..."
                    sh "sleep 15"
                    
                    def maxAttempts = 12
                    def attempt = 0
                    def success = false
                    
                    while (attempt < maxAttempts && !success) {
                        attempt++
                        echo "헬스 체크: ${attempt}/${maxAttempts}"
                        
                        def healthCheck = sh(
                            script: """
                                # 프로세스 상태 확인
                                if ! pm2 describe tribe-backend > /dev/null 2>&1; then
                                    echo "ERROR: PM2 프로세스가 실행되지 않고 있습니다"
                                    exit 1
                                fi
                                
                                # 프로세스가 실제로 실행 중인지 확인
                                PM2_STATUS=\$(pm2 jlist | jq -r '.[] | select(.name=="tribe-backend") | .pm2_env.status')
                                if [ "\$PM2_STATUS" != "online" ]; then
                                    echo "ERROR: 애플리케이션 상태가 online이 아닙니다: \$PM2_STATUS"
                                    exit 1
                                fi
                                
                                echo "프로세스 상태: 정상 실행 중"
                                
                                # HTTP 헬스 체크
                                echo "HTTP 헬스 체크 진행 중..."
                                
                                # Spring Boot Actuator 헬스 체크
                                if curl -f -s -m 10 http://localhost:${APP_PORT}/api/actuator/health | grep -q '"status":"UP"'; then
                                    echo "SUCCESS: Actuator 헬스 체크 통과"
                                    exit 0
                                fi
                                
                                # 기본 API 엔드포인트 확인
                                HTTP_CODE=\$(curl -s -o /dev/null -w "%{http_code}" -m 10 http://localhost:${APP_PORT}/api)
                                if [ "\$HTTP_CODE" = "200" ] || [ "\$HTTP_CODE" = "404" ]; then
                                    echo "SUCCESS: API 엔드포인트 응답 확인 (HTTP \$HTTP_CODE)"
                                    exit 0
                                fi
                                
                                # 포트 응답 확인
                                if curl -f -s -m 5 http://localhost:${APP_PORT} > /dev/null; then
                                    echo "SUCCESS: 기본 포트 응답 확인"
                                    exit 0
                                fi
                                
                                echo "WARNING: HTTP 헬스 체크 실패, 재시도 중..."
                                exit 1
                            """,
                            returnStatus: true
                        )
                        
                        if (healthCheck == 0) {
                            success = true
                            echo """
=== 배포 성공 ===
API 주소: http://${PUBLIC_IP}:${APP_PORT}/api
헬스 체크: http://${PUBLIC_IP}:${APP_PORT}/api/actuator/health
Swagger UI: http://${PUBLIC_IP}:${APP_PORT}/api/swagger-ui.html
API 문서: http://${PUBLIC_IP}:${APP_PORT}/api/api-docs
                            """
                        } else {
                            if (attempt < maxAttempts) {
                                echo "재시도 대기 중 (${attempt}/${maxAttempts})..."
                                sh "sleep 10"
                            }
                        }
                    }
                    
                    if (!success) {
                        echo "ERROR: 헬스 체크 실패, 상세 진단 실행 중"
                        sh '''
                            echo "==================== 배포 실패 진단 ===================="
                            
                            echo "=== PM2 프로세스 상태 ==="
                            pm2 describe tribe-backend || echo "PM2 프로세스를 찾을 수 없습니다"
                            pm2 status
                            
                            echo "=== 최근 애플리케이션 로그 (마지막 50줄) ==="
                            if [ -f ${DEPLOY_PATH}/logs/out.log ]; then
                                tail -50 ${DEPLOY_PATH}/logs/out.log
                            else
                                echo "출력 로그 파일을 찾을 수 없습니다"
                            fi
                            
                            echo "=== 에러 로그 (마지막 50줄) ==="
                            if [ -f ${DEPLOY_PATH}/logs/err.log ]; then
                                tail -50 ${DEPLOY_PATH}/logs/err.log
                            else
                                echo "에러 로그 파일을 찾을 수 없습니다"
                            fi
                            
                            echo "=== 포트 사용 상태 ==="
                            ss -tlnp | grep ${APP_PORT} || echo "포트 ${APP_PORT}가 사용되지 않고 있습니다"
                            
                            echo "=== 네트워크 연결 테스트 ==="
                            curl -v http://localhost:${APP_PORT} || echo "로컬 연결 실패"
                            
                            echo "=== 시스템 리소스 ==="
                            echo "디스크 사용량:"
                            df -h ${DEPLOY_PATH}
                            echo "메모리 사용량:"
                            free -h
                            echo "CPU 로드:"
                            uptime
                            
                            echo "=== Java 프로세스 ==="
                            ps aux | grep java || echo "Java 프로세스가 실행되지 않고 있습니다"
                            
                            echo "=========================================================="
                        '''
                        error "배포 실패 - HTTP 헬스 체크 실패"
                    }
                }
            }
        }
    }
    
    post {
        always {
            // 워크스페이스 정리
            cleanWs()
        }
        success {
            echo """
✅ 배포 성공

배포 정보:
  애플리케이션: ${APP_NAME}
  포트: ${APP_PORT}
  프로파일: production (MariaDB)
  컨텍스트 패스: /api
  배포 시간: ${new Date()}

접속 URL:
  메인 API: http://${PUBLIC_IP}:${APP_PORT}/api
  헬스 체크: http://${PUBLIC_IP}:${APP_PORT}/api/actuator/health
  API 문서: http://${PUBLIC_IP}:${APP_PORT}/api/swagger-ui.html
  API 스펙: http://${PUBLIC_IP}:${APP_PORT}/api/api-docs

관리 명령어:
  로그 확인: pm2 logs tribe-backend
  재시작: pm2 restart tribe-backend
  중지: pm2 stop tribe-backend
  상태 확인: pm2 status
  백업 확인: ls -la ${DEPLOY_PATH}/backup/

================================================
            """
            // 이메일 알림 발송
            script {
                try {
                    mail to: 'jaeuu.dev@gmail.com',
                         subject: "[Jenkins] ${APP_NAME} 배포 성공 알림",
                         body: """
${APP_NAME} 애플리케이션이 성공적으로 배포되었습니다.

배포 정보:
  애플리케이션: ${APP_NAME}
  포트: ${APP_PORT}
  프로파일: production (MariaDB)
  컨텍스트 패스: /api
  배포 시간: ${new Date()}

접속 URL:
  메인 API: http://${PUBLIC_IP}:${APP_PORT}/api
  헬스 체크: http://${PUBLIC_IP}:${APP_PORT}/api/actuator/health
  API 문서: http://${PUBLIC_IP}:${APP_PORT}/api/swagger-ui.html
  API 스펙: http://${PUBLIC_IP}:${APP_PORT}/api/api-docs

Jenkins
                         """
                    echo "이메일 알림이 jaeuu.dev@gmail.com으로 발송되었습니다."
                } catch (Exception e) {
                    echo "이메일 발송 실패: ${e.message}"
                }
            }
        }
        failure {
            echo """
❌ 배포 실패

빌드 번호: ${env.BUILD_NUMBER}
빌드 URL: ${env.BUILD_URL}
            """
            // 이메일 알림 발송
            script {
                try {
                    mail to: 'jaeuu.dev@gmail.com',
                         subject: "[Jenkins] ${APP_NAME} 배포 실패 알림",
                         body: """
${APP_NAME} 애플리케이션 배포가 실패했습니다.

실패 정보:
  애플리케이션: ${APP_NAME}
  빌드 번호: ${env.BUILD_NUMBER}
  빌드 URL: ${env.BUILD_URL}
  실패 시간: ${new Date()}

오류를 확인하고 조치해주세요.

Jenkins
                         """
                    echo "실패 이메일 알림이 jaeuu.dev@gmail.com으로 발송되었습니다."
                } catch (Exception e) {
                    echo "실패 이메일 발송 실패: ${e.message}"
                }
            }
        }
    }
}