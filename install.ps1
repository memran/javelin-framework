param(
    [switch] $SkipBuild
)

$ErrorActionPreference = "Stop"

$Root = Split-Path -Parent $MyInvocation.MyCommand.Path
$Jar = Join-Path $Root "modules\javelin-console\target\javelin-console-0.1.0-SNAPSHOT-all.jar"
$Bin = Join-Path $HOME ".javelin\bin"
$Launcher = Join-Path $Bin "javelin.cmd"

function Require-Command($Name, $Fix) {
    if (-not (Get-Command $Name -ErrorAction SilentlyContinue)) {
        Write-Error "$Name is required. $Fix"
    }
}

Require-Command "java" "Install Java 25+ and make sure java is available on PATH."
if (-not $SkipBuild) {
    Require-Command "mvn" "Install Maven 3.9+ and make sure mvn is available on PATH."
    Push-Location $Root
    try {
        mvn -pl :javelin-console -am package -DskipTests
    } finally {
        Pop-Location
    }
}

if (-not (Test-Path $Jar)) {
    Write-Error "CLI jar was not found at $Jar. Run without -SkipBuild first."
}

New-Item -ItemType Directory -Force -Path $Bin | Out-Null

$LauncherContent = @"
@echo off
setlocal
java -jar "$Jar" %*
"@

Set-Content -Path $Launcher -Value $LauncherContent -Encoding ASCII

$UserPath = [Environment]::GetEnvironmentVariable("Path", "User")
$PathParts = @()
if ($UserPath) {
    $PathParts = $UserPath -split ';' | Where-Object { $_ -ne "" }
}

if ($PathParts -notcontains $Bin) {
    $NewPath = if ($UserPath) { "$UserPath;$Bin" } else { $Bin }
    [Environment]::SetEnvironmentVariable("Path", $NewPath, "User")
}

Write-Host "Javelin installed." -ForegroundColor Green
Write-Host "Launcher: $Launcher"
Write-Host "Open a new terminal, then run: javelin --help"
