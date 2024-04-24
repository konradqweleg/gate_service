$jar_folder = "C:/mychatserver/build-code"

Get-ChildItem -Path $jar_folder -Filter "*.jar" | ForEach-Object {
    Write-Host "Starting $($_.Name)"
    Start-Process -FilePath "java" -ArgumentList "-jar `"$($_.FullName)`""
    Write-Host "$($_.Name) completed"
}