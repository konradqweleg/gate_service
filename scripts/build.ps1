$repo_friend_service = "C:/mychatserver/friends_service"
$repo_gate_service = "C:/mychatserver/gate_mychat_server"
$repo_messages_container = "C:/mychatserver/MessageContainer"
$repo_users_service = "C:/mychatserver/UsersServices_myChatServer"
$repo_messages_history = "C:\mychatserver\message_history"

$destination_folder = "C:/mychatserver/build-code"

$script_folder = Get-Location

$repos_folders = $repo_friend_service, $repo_gate_service, $repo_messages_container, $repo_messages_history, $repo_users_service

$build_failed = $false
$failed_builds = @()

Clear-Host

$start_time = Get-Date

foreach ($folder in $repos_folders) {
    Set-Location $folder
    & .\gradlew clean build
    if ($LASTEXITCODE -ne 0) {
        $build_failed = $true
        $failed_builds += $folder
    }

    Set-Location "$folder/build/libs"
    Get-ChildItem -Recurse -Filter "*.jar" | Where-Object { $_.Name -notmatch "plain" } | ForEach-Object {
        Copy-Item $_.FullName $destination_folder
    }
}

Set-Location $script_folder

$end_time = Get-Date

$elapsed_time = ($end_time - $start_time).TotalSeconds

if ($build_failed) {
    Write-Host "Some builds failed: $($failed_builds -join ', ')" -ForegroundColor Red
} else {
    Write-Host "All builds succeeded." -ForegroundColor Green
}

Write-Host "Script took $elapsed_time seconds to execute."

