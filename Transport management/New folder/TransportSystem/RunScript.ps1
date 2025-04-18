Write-Host "Compiling Bangladesh Transport System..." -ForegroundColor Green
javac *.java

Write-Host "Running Bangladesh Transport System..." -ForegroundColor Green
java RunBTS

Write-Host "Press any key to continue..." -ForegroundColor Yellow
$host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown") | Out-Null 