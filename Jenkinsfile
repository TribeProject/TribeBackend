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
        JAVA_OPTS = '-Xms256m -Xmx512m -XX:+UseG1GC -Dserver.port=9999'
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
                    echo "JAVA_HOME: $JAVA_HOME"
                    echo "Gradle Version:"
                    ./gradlew --version
                    echo "Available Memory:"
                    free -h
                    echo "Disk Space:"
                    df -h ${DEPLOY_PATH}
                    echo "PM2 Version:"
                    pm2 --version || echo "PM2 not installed"
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
                            
                            # 설정 파일 내용 확인 (민감한 정보 제외)
                            echo "=== Configuration validation ==="
                            if grep -q "spring:" src/main/resources/application.yml; then
                                echo "✓ Spring configuration found"
                            else
                                echo "⚠ Spring configuration not found in application.yml"
                            fi
                        '''
                    }
                }
            }
        }
        
        stage('빌드') {
            steps {
                echo '=== JAR 파일 빌드 ==='
                sh '''
                    # Gradle 캐시 정리
                    ./gradlew clean
                    
                    # 빌드 실행
                    ./gradlew build -x test --no-daemon --refresh-dependencies
                    
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
                        echo "ERROR: PM2가 설치되어 있지 않습니다."
                        echo "PM2 설치: npm install -g pm2"
                        exit 1
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
                            ls -t app-*.jar 2>/dev/null | tail -n +8 | xargs -r rm -f
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
                        
                        # 포트 사용 중인 프로세스 종료
                        PIDS=$(lsof -ti:${APP_PORT} 2>/dev/null || echo "")
                        if [ ! -z "$PIDS" ]; then
                            echo "포트 ${APP_PORT}를 사용 중인 프로세스 종료: $PIDS"
                            echo "$PIDS" | xargs -r kill -TERM
                            sleep 3
                            # 강제 종료가 필요한 경우
                            REMAINING_PIDS=$(lsof -ti:${APP_PORT} 2>/dev/null || echo "")
                            if [ ! -z "$REMAINING_PIDS" ]; then
                                echo "강제 종료: $REMAINING_PIDS"
                                echo "$REMAINING_PIDS" | xargs -r kill -KILL
                            fi
                        else
                            echo "포트 ${APP_PORT}를 사용 중인 프로세스가 없습니다"
                        fi
                        
                        # PID 파일 정리
                        rm -f ${DEPLOY_PATH}/app.pid
                        
                        # 잠시 대기
                        sleep 2
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

                            # PM2 ecosystem 설정 파일 생성
                            cat > ecosystem.config.js <<'CONFIG_EOF'
module.exports = {
  apps: [{
    name: 'tribe-backend',
    script: 'java',
    args: [
      '-jar',
      'app.jar'
    ],
    cwd: process.env.DEPLOY_PATH || '/home/ec2-user/deploy',
    instances: 1,
    autorestart: true,
    watch: false,
    max_memory_restart: '512M',
    restart_delay: 4000,
    env: {
      NODE_ENV: 'production',
      SPRING_PROFILES_ACTIVE: 'prod',
      SERVER_PORT: process.env.APP_PORT || '9999',
      DB_HOST: process.env.DB_HOST,
      DB_PORT: '3306',
      DB_NAME: process.env.DB_NAME,
      DB_USERNAME: process.env.DB_USERNAME,
      DB_PASSWORD: process.env.DB_PASSWORD,
      JAVA_OPTS: '-Xms256m -Xmx512m -XX:+UseG1GC -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Seoul'
    },
    log_file: './logs/combined.log',
    out_file: './logs/out.log',
    error_file: './logs/err.log',
    time: true,
    log_date_format: 'YYYY-MM-DD HH:mm:ss Z',
    merge_logs: true
  }]
};
CONFIG_EOF

                            echo "PM2 설정 파일 생성 완료"
                            
                            # 환경변수 설정하여 PM2 시작
                            export DEPLOY_PATH="${DEPLOY_PATH}"
                            export APP_PORT="${APP_PORT}"
                            export DB_HOST="${DB_HOST}"
                            export DB_NAME="${DB_NAME}"
                            export DB_USERNAME="${DB_USERNAME}"
                            export DB_PASSWORD="${DB_PASSWORD}"
                            
                            # PM2로 애플리케이션 시작
                            pm2 start ecosystem.config.js
                            pm2 save
                            
                            echo "애플리케이션 시작 명령 완료"
                            sleep 5
                            pm2 status tribe-backend
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
                    sh "sleep 20"
                    
                    def maxAttempts = 15
                    def attempt = 0
                    def success = false
                    
                    while (attempt < maxAttempts && !success) {
                        attempt++
                        echo "헬스 체크: ${attempt}/${maxAttempts}"
                        
                        def healthCheck = sh(
                            script: """
                                # PM2 프로세스 존재 확인
                                if ! pm2 describe tribe-backend > /dev/null 2>&1; then
                                    echo "ERROR: PM2 프로세스가 존재하지 않습니다"
                                    exit 1
                                fi
                                
                                # PM2 상태 확인 (jq 없이)
                                PM2_STATUS=\$(pm2 status tribe-backend --no-color | grep 'tribe-backend' | awk '{print \$10}' || echo "unknown")
                                echo "PM2 상태: \$PM2_STATUS"
                                
                                if echo "\$PM2_STATUS" | grep -q "online"; then
                                    echo "✓ PM2 프로세스가 정상 실행 중입니다"
                                else
                                    echo "⚠ PM2 프로세스 상태: \$PM2_STATUS"
                                    # 로그 확인
                                    echo "=== 최근 에러 로그 확인 ==="
                                    pm2 logs tribe-backend --lines 10 --raw || true
                                    exit 1
                                fi
                                
                                # 포트 리스닝 확인
                                if ss -tln | grep -q ":${APP_PORT} "; then
                                    echo "✓ 포트 ${APP_PORT}가 리스닝 중입니다"
                                else
                                    echo "⚠ 포트 ${APP_PORT}가 리스닝되지 않고 있습니다"
                                    exit 1
                                fi
                                
                                # HTTP 헬스 체크
                                echo "HTTP 헬스 체크 진행 중..."
                                
                                # Spring Boot Actuator 헬스 체크
                                if curl -f -s -m 15 "http://localhost:${APP_PORT}/api/actuator/health" | grep -q '"status":"UP"'; then
                                    echo "SUCCESS: Actuator 헬스 체크 통과"
                                    exit 0
                                fi
                                
                                # 기본 API 엔드포인트 확인
                                HTTP_CODE=\$(curl -s -o /dev/null -w "%{http_code}" -m 15 "http://localhost:${APP_PORT}/api")
                                echo "API 엔드포인트 응답 코드: \$HTTP_CODE"
                                if [ "\$HTTP_CODE" = "200" ] || [ "\$HTTP_CODE" = "404" ]; then
                                    echo "SUCCESS: API 엔드포인트 응답 확인 (HTTP \$HTTP_CODE)"
                                    exit 0
                                fi
                                
                                # 기본 포트 응답 확인
                                if curl -f -s -m 10 "http://localhost:${APP_PORT}" > /dev/null; then
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
=== 🎉 배포 성공 🎉 ===
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
                            
                            echo "=== PM2 로그 (최근 30줄) ==="
                            pm2 logs tribe-backend --lines 30 --raw || echo "로그를 가져올 수 없습니다"
                            
                            echo "=== 직접 로그 파일 확인 ==="
                            if [ -f ${DEPLOY_PATH}/logs/out.log ]; then
                                echo "--- stdout 로그 (최근 20줄) ---"
                                tail -20 ${DEPLOY_PATH}/logs/out.log
                            fi
                            
                            if [ -f ${DEPLOY_PATH}/logs/err.log ]; then
                                echo "--- stderr 로그 (최근 20줄) ---"
                                tail -20 ${DEPLOY_PATH}/logs/err.log
                            fi
                            
                            echo "=== 포트 사용 상태 ==="
                            ss -tlnp | grep ${APP_PORT} || echo "포트 ${APP_PORT}가 사용되지 않고 있습니다"
                            
                            echo "=== Java 프로세스 ==="
                            ps aux | grep java | grep -v grep || echo "Java 프로세스가 실행되지 않고 있습니다"
                            
                            echo "=== 시스템 리소스 ==="
                            echo "메모리:"
                            free -h
                            echo "디스크:"
                            df -h ${DEPLOY_PATH}
                            echo "CPU:"
                            uptime
                            
                            echo "=== 수동 JAR 실행 테스트 ==="
                            cd ${DEPLOY_PATH}
                            echo "JAR 파일 실행 가능 여부 확인:"
                            timeout 10 java -jar app.jar --help || echo "JAR 파일 실행 실패"
                            
                            echo "=========================================================="
                        '''
                        error "배포 실패 - 애플리케이션이 정상적으로 시작되지 않았습니다"
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

================================================
            """
            script {
                try {
                    def emailBody = """
${APP_NAME} 애플리케이션이 성공적으로 배포되었습니다.

배포 정보:
  • 애플리케이션: ${APP_NAME}
  • 포트: ${APP_PORT}
  • 프로파일: production (MariaDB)
  • 컨텍스트 패스: /api
  • 배포 시간: ${new Date()}
  • 빌드 번호: ${env.BUILD_NUMBER}

접속 URL:
  • 메인 API: http://${PUBLIC_IP}:${APP_PORT}/api
  • 헬스 체크: http://${PUBLIC_IP}:${APP_PORT}/api/actuator/health
  • API 문서: http://${PUBLIC_IP}:${APP_PORT}/api/swagger-ui.html
  • API 스펙: http://${PUBLIC_IP}:${APP_PORT}/api/api-docs

관리 명령어:
  • 로그 확인: pm2 logs tribe-backend
  • 재시작: pm2 restart tribe-backend
  • 중지: pm2 stop tribe-backend
  • 상태 확인: pm2 status

Jenkins 빌드: ${env.BUILD_URL}
"""
                    
                    mail to: 'jaeuu.dev@gmail.com, gktjdfhr22@gmail.com',
                         subject: "✅ [Jenkins] ${APP_NAME} 배포 성공 - Build #${env.BUILD_NUMBER}",
                         body: emailBody,
                         mimeType: 'text/plain'
                         
                    echo "배포 성공 이메일이 발송되었습니다."
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
            script {
                try {
                    def failureBody = """
${APP_NAME} 애플리케이션 배포가 실패했습니다.

실패 정보:
  • 애플리케이션: ${APP_NAME}
  • 빌드 번호: ${env.BUILD_NUMBER}
  • 실패 시간: ${new Date()}
  • 실패 단계: ${env.STAGE_NAME ?: '알 수 없음'}

조치 사항:
  1. Jenkins 콘솔 로그를 확인해주세요: ${env.BUILD_URL}console
  2. 서버에 접속하여 PM2 로그를 확인해주세요: pm2 logs tribe-backend
  3. 애플리케이션 설정을 점검해주세요
  4. 데이터베이스 연결 상태를 확인해주세요

빌드 URL: ${env.BUILD_URL}

Jenkins 자동 알림
"""
                    
                    mail to: 'jaeuu.dev@gmail.com, gktjdfhr22@gmail.com',
                         subject: "❌ [Jenkins] ${APP_NAME} 배포 실패 - Build #${env.BUILD_NUMBER}",
                         body: failureBody,
                         mimeType: 'text/plain'
                         
                    echo "배포 실패 이메일이 발송되었습니다."
                } catch (Exception e) {
                    echo "실패 이메일 발송 실패: ${e.message}"
                }
            }
        }
    }
}