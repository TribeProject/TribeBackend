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
                echo "DEPLOY PATH: ${DEPLOY_PATH}"
                echo "APPLICATION PORT: ${APP_PORT}"
                sh 'java -version'
                sh './gradlew --version'
            }
        }
        
        stage('코드 체크아웃') {
            steps {
                echo 'SOURCE CODE CHECKOUT'
                checkout scm
            }
        }
        
        stage('설정 파일 배포') {
            steps {
                echo 'CONFIG FILE DEPLOY'
                script {
                    withCredentials([file(credentialsId: 'application-config', variable: 'CONFIG_FILE')]) {
                        sh '''
                            # CONFIG FILE DIRECTORY CREATION
                            mkdir -p src/main/resources
                            
                            # COPY CONFIG FILE FROM JENKINS SECRET FILE
                            cp ${CONFIG_FILE} src/main/resources/application.yml
                            
                            echo "CONFIG FILE DEPLOYED"
                            echo "CONFIG FILE EXISTENCE CHECK:"
                            ls -la src/main/resources/application.yml
                            
                            echo "CONFIG FILE BASIC INFORMATION CHECK:"
                            grep -E "(application:|name:|port:|context-path:)" src/main/resources/application.yml | head -5 || true
                        '''
                    }
                }
            }
        }
        
        stage('빌드') {
            steps {
                echo 'BUILD JAR FILE'
                sh './gradlew build -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx512m"'
                
                sh '''
                    echo "=== BUILDED JAR FILE CHECK ==="
                    ls -la build/libs/
                    if [ ! -f "build/libs/${JAR_NAME}" ]; then
                        echo "ERROR: JAR FILE NOT CREATED: ${JAR_NAME}"
                        exit 1
                    fi
                    echo "SUCCESS: JAR FILE CREATED: ${JAR_NAME}"
                    
                    # JAR FILE SIZE CHECK
                    du -h "build/libs/${JAR_NAME}"
                '''
            }
        }
        
        stage('배포 준비') {
            steps {
                echo 'DEPLOY ENVIRONMENT PREPARATION'
                sh '''
                    mkdir -p ${DEPLOY_PATH}
                    mkdir -p ${DEPLOY_PATH}/backup
                    mkdir -p ${DEPLOY_PATH}/logs
                    chmod 755 ${DEPLOY_PATH}
                    chmod 755 ${DEPLOY_PATH}/logs
                    echo "DEPLOY DIRECTORY PREPARED"
                '''
            }
        }
        
        stage('기존 애플리케이션 중지') {
            steps {
                echo 'STOP EXISTING APPLICATION'
                script {
                    sh '''
                        # BACKUP EXISTING JAR FILE
                        if [ -f ${DEPLOY_PATH}/app.jar ]; then
                            BACKUP_NAME="app-$(date +%Y%m%d_%H%M%S).jar"
                            cp ${DEPLOY_PATH}/app.jar ${DEPLOY_PATH}/backup/${BACKUP_NAME}
                            echo "EXISTING JAR FILE BACKUP COMPLETED: ${BACKUP_NAME}"
                        fi
                        
                        # 기존 프로세스 종료
                        if [ -f ${DEPLOY_PATH}/app.pid ]; then
                            PID=$(cat ${DEPLOY_PATH}/app.pid 2>/dev/null || echo "")
                        else
                            PID=$(pgrep -f "java.*${DEPLOY_PATH}/app.jar" || echo "")
                        fi
                        
                        if [ ! -z "$PID" ] && kill -0 $PID 2>/dev/null; then
                            echo "EXISTING PROCESS($PID) STOPPING"
                            kill -TERM $PID || true
                            sleep 5
                            
                            if kill -0 $PID 2>/dev/null; then
                                echo "FORCE STOP"
                                kill -KILL $PID || true
                                sleep 2
                            fi
                            echo "EXISTING PROCESS STOPPED"
                        else
                            echo "NO RUNNING APPLICATION"
                        fi
                        
                        # PID 파일 정리
                        rm -f ${DEPLOY_PATH}/app.pid
                    '''
                }
            }
        }
        
        stage('JAR 파일 배포') {
            steps {
                echo 'DEPLOY JAR FILE'
                sh '''
                    # JAR FILE EXISTENCE CHECK
                    if [ ! -f "build/libs/${JAR_NAME}" ]; then
                        echo "ERROR: JAR FILE NOT FOUND: build/libs/${JAR_NAME}"
                        exit 1
                    fi
                    
                    # JAR 파일 복사
                    cp "build/libs/${JAR_NAME}" "${DEPLOY_PATH}/app.jar"
                    chmod +x "${DEPLOY_PATH}/app.jar"
                    
                    echo "JAR FILE DEPLOYED"
                    ls -la "${DEPLOY_PATH}/app.jar"
                '''
            }
        }
        
        stage('애플리케이션 시작') {
            steps {
                echo 'START NEW APPLICATION'
                script {
                    withCredentials([
                        string(credentialsId: 'rds-host', variable: 'DB_HOST'),
                        string(credentialsId: 'rds-database', variable: 'DB_NAME'),
                        string(credentialsId: 'rds-username', variable: 'DB_USERNAME'),
                        string(credentialsId: 'rds-password', variable: 'DB_PASSWORD')
                    ]) {
                        sh '''
                            cd ${DEPLOY_PATH}
                            
                            echo "RDS CONNECTION INFO LOADED"
                            echo "DATABASE: MariaDB (${DB_HOST}:3306/${DB_NAME})"
                            
                            # START SCRIPT CREATION
                            cat > start.sh << "SCRIPT_EOF"
#!/bin/bash

# Java 옵션 설정
export JAVA_OPTS="-Xms128m -Xmx256m -XX:+UseG1GC -server"
export SERVER_PORT=${APP_PORT}
export SPRING_PROFILES_ACTIVE=prod

# RDS MariaDB 연결 정보
export DB_HOST="${DB_HOST}"
export DB_PORT="3306"
export DB_NAME="${DB_NAME}"
export DB_USERNAME="${DB_USERNAME}"
export DB_PASSWORD="${DB_PASSWORD}"

echo "APPLICATION STARTING..."
echo "PORT: ${APP_PORT}"
echo "PROFILE: ${SPRING_PROFILES_ACTIVE}"
echo "DATABASE: MariaDB (${DB_HOST}:3306/${DB_NAME})"
echo "START TIME: $(date)"

# 애플리케이션 시작
nohup java $JAVA_OPTS -Dserver.port=${APP_PORT} -jar app.jar > logs/app.log 2>&1 &
APP_PID=$!
echo $APP_PID > app.pid

echo "APPLICATION STARTED (PID: $APP_PID)"
echo "LOG FILE: ${DEPLOY_PATH}/logs/app.log"
SCRIPT_EOF
                            
                            # 스크립트 실행 권한 부여 및 실행
                            chmod +x start.sh
                            ./start.sh
                            
                            # 시작 확인
                            sleep 5
                            if [ -f app.pid ] && kill -0 $(cat app.pid) 2>/dev/null; then
                                echo "PROCESS RUNNING (PID: $(cat app.pid))"
                                echo "MEMORY USAGE: $(ps -o pid,vsz,rss,comm -p $(cat app.pid) 2>/dev/null || echo 'N/A')"
                            else
                                echo "ERROR: PROCESS START FAILED"
                                echo "=== RECENT LOG ==="
                                tail -20 logs/app.log 2>/dev/null || echo "LOG FILE NOT FOUND"
                                exit 1
                            fi
                        '''
                    }
                }
            }
        }
        
        stage('배포 확인') {
            steps {
                echo 'DEPLOY STATUS CHECK'
                script {
                    echo "APPLICATION INITIALIZATION WAITING..."
                    sh "sleep 30"
                    
                    def maxAttempts = 10
                    def attempt = 0
                    def success = false
                    
                    while (attempt < maxAttempts && !success) {
                        attempt++
                        echo "Health Check: ${attempt}/${maxAttempts}"
                        
                        def healthCheck = sh(
                            script: """
                                # PROCESS STATUS CHECK
                                if [ -f ${DEPLOY_PATH}/app.pid ] && kill -0 \$(cat ${DEPLOY_PATH}/app.pid) 2>/dev/null; then
                                    echo "PROCESS RUNNING (PID: \$(cat ${DEPLOY_PATH}/app.pid))"
                                else
                                    echo "ERROR: PROCESS NOT RUNNING"
                                    exit 1
                                fi
                                
                                # HTTP Health Check
                                echo "HTTP Health Check..."
                                
                                # Spring Boot Actuator Health Check
                                if curl -f -s -m 15 http://localhost:${APP_PORT}/api/actuator/health; then
                                    echo "SUCCESS: Actuator Health Check"
                                    exit 0
                                fi
                                
                                # 기본 API 엔드포인트 확인
                                if curl -f -s -m 15 http://localhost:${APP_PORT}/api; then
                                    echo "SUCCESS: API ENDPOINT RESPONSE"
                                    exit 0
                                fi
                                
                                # 포트 응답 확인
                                if curl -f -s -m 10 http://localhost:${APP_PORT}; then
                                    echo "SUCCESS: DEFAULT PORT RESPONSE"
                                    exit 0
                                fi
                                
                                echo "ERROR: ALL HTTP HEALTH CHECK FAILED"
                                exit 1
                            """,
                            returnStatus: true
                        )
                        
                        if (healthCheck == 0) {
                            success = true
                            echo """
배포 성공
API 주소: http://${PUBLIC_IP}:${APP_PORT}/api
Health Check: http://${PUBLIC_IP}:${APP_PORT}/api/actuator/health
Swagger UI: http://${PUBLIC_IP}:${APP_PORT}/api/swagger-ui.html
                            """
                        } else {
                            if (attempt < maxAttempts) {
                                echo "RETRY WAITING (${attempt}/${maxAttempts})..."
                                sh "sleep 15"
                            }
                        }
                    }
                    
                    if (!success) {
                        echo "ERROR: HEALTH CHECK FAILED, DETAILED DIAGNOSIS RUNNING"
                        sh '''
                            echo "==================== DEPLOY FAILED DIAGNOSIS ===================="
                            
                            echo "=== PROCESS STATUS ==="
                            ps aux | grep java | grep ${DEPLOY_PATH} || echo "Java PROCESS NOT FOUND"
                            
                            echo "=== RECENT APPLICATION LOG (LAST 100 LINES) ==="
                            if [ -f ${DEPLOY_PATH}/logs/app.log ]; then
                                tail -100 ${DEPLOY_PATH}/logs/app.log
                            else
                                echo "LOG FILE NOT FOUND"
                            fi
                            
                            echo "=== PORT USAGE STATUS ==="
                            ss -tlnp | grep ${APP_PORT} || echo "PORT ${APP_PORT} NOT IN USE"
                            
                            echo "=== NETWORK CONNECTION CHECK ==="
                            curl -v http://localhost:${APP_PORT} || echo "LOCAL CONNECTION FAILED"
                            
                            echo "=== DISK USAGE ==="
                            free -h
                            
                            echo "=========================================================="
                        '''
                        error "DEPLOY FAILED - HTTP HEALTH CHECK FAILED"
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
✅ DEPLOY SUCCESS

DEPLOY INFO:
  APPLICATION: ${APP_NAME}
  PORT: ${APP_PORT}
  PROFILE: production (MariaDB)
  CONTEXT PATH: /api
  DEPLOY TIME: ${new Date()}

ACCESS URL:
  MAIN API: http://${PUBLIC_IP}:${APP_PORT}/api
  HEALTH CHECK: http://${PUBLIC_IP}:${APP_PORT}/api/actuator/health
  API DOCUMENTATION: http://${PUBLIC_IP}:${APP_PORT}/api/swagger-ui.html
  API SPECIFICATION: http://${PUBLIC_IP}:${APP_PORT}/api/api-docs

MANAGEMENT COMMANDS:
  LOG FILE: tail -f ${DEPLOY_PATH}/logs/app.log
  RESTART: cd ${DEPLOY_PATH} && ./start.sh
  STOP: kill \$(cat ${DEPLOY_PATH}/app.pid)
  STATUS: ps aux | grep java | grep ${DEPLOY_PATH}
  BACKUP: ls -la ${DEPLOY_PATH}/backup/

================================================
            """
        }
        failure {
            echo """
❌ DEPLOY FAILED
            """
        }
    }
}