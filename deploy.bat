@echo off
REM Script de déploiement pour Tomcat 10.1.28

set PROJECT_NAME=cafe
set TOMCAT_HOME="C:\Program Files\Apache Software Foundation\Tomcat 10.1"
set WAR_FILE=target\%PROJECT_NAME%-0.0.1-SNAPSHOT.war

REM Construction du projet
echo Construction du projet avec Maven...
call mvn clean package

if %errorlevel% neq 0 (
    echo Erreur lors de la construction du projet
    pause
    exit /b %errorlevel%
)

REM Vérification que le fichier WAR existe
if not exist "%WAR_FILE%" (
    echo Fichier WAR non trouvé: %WAR_FILE%
    dir /s target\*.war
    pause
    exit /b 1
)

REM Arrêt de Tomcat si en cours d'exécution
echo Arrêt de Tomcat...
call %TOMCAT_HOME%\bin\shutdown.bat

REM Suppression de l'ancienne version
echo Nettoyage de l'ancien déploiement...
if exist %TOMCAT_HOME%\webapps\%PROJECT_NAME%.war (
    del %TOMCAT_HOME%\webapps\%PROJECT_NAME%.war
)
if exist %TOMCAT_HOME%\webapps\%PROJECT_NAME% (
    rmdir /s /q %TOMCAT_HOME%\webapps\%PROJECT_NAME%
)

REM Copie du nouveau WAR
echo Copie du nouveau fichier WAR...
copy "%WAR_FILE%" %TOMCAT_HOME%\webapps\%PROJECT_NAME%.war

if %errorlevel% neq 0 (
    echo Erreur lors de la copie du fichier WAR
    pause
    exit /b %errorlevel%
)

REM Démarrage de Tomcat
echo Démarrage de Tomcat...
start "" %TOMCAT_HOME%\bin\startup.bat

echo Déploiement terminé avec succès!
echo L'application sera disponible à: http://localhost:8080/%PROJECT_NAME%/
pause