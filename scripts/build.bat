@echo off
setlocal EnableDelayedExpansion

set "repo_friend_service=C:/mychatserver/friends_service"
set "repo_gate_service=C:/mychatserver/gate_mychat_server"
set "repo_messages_container=C:/mychatserver/MessageContainer"
set "repo_users_service=C:/mychatserver/UsersServices_myChatServer"
set "repo_messages_history=C:\mychatserver\message_history"

set "destination_folder=C:/mychatserver/build-code"

set "script_folder=%cd%"

set "repos_folders=%repo_friend_service% %repo_gate_service% %repo_messages_container% %repo_messages_history% %repo_users_service%"

set "build_failed=false"
set "failed_builds="

cls >nul

for /f "tokens=1-4 delims=:." %%a in ("%time%") do (set /A "start_time=(((%%a*60)+%%b)*60+%%c)*100+%%d")

for %%i in (%repos_folders%) do (
    cd %%i || exit /b
    call gradlew clean build
    if errorlevel 1 (
        set "build_failed=true"
        set "failed_builds=!failed_builds! %%~nxi"
    )

    cd %%i/build/libs || exit /b
    for /R %%i in (*.jar) do (
        echo %%~nxi | findstr /V /I "plain" >nul && (
            copy "%%i" "%destination_folder%"
        )
    )
)

cd "%script_folder%"

for /f "tokens=1-4 delims=:." %%a in ("%time%") do (set /A "end_time=(((%%a*60)+%%b)*60+%%c)*100+%%d")

set /A "elapsed_time=(%end_time%-%start_time%)/100"

if "%build_failed%"=="true" (
    echo Some builds failed: %failed_builds%
) else (
    echo All builds succeeded.
)

echo Script took %elapsed_time% seconds to execute.

if "%build_failed%"=="false" (
    echo All tests passed.
)