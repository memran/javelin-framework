param([string]$Mode = "all")

# Build static assets and/or the Java jar. Usage: .\build.ps1 [-Mode assets|java|all]
$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $MyInvocation.MyCommand.Path
Push-Location $Root
try {
    function Build-Assets {
        if (Get-Command bun -ErrorAction SilentlyContinue) {
            Write-Host "Building assets with bun"
            bun install
            if ($LASTEXITCODE -ne 0) { throw "bun install failed" }
            bun run build
            if ($LASTEXITCODE -ne 0) { throw "bun run build failed" }
        } elseif (Get-Command npm -ErrorAction SilentlyContinue) {
            Write-Host "Building assets with npm"
            npm install
            if ($LASTEXITCODE -ne 0) { throw "npm install failed" }
            npm run build
            if ($LASTEXITCODE -ne 0) { throw "npm run build failed" }
        } else {
            throw "npm or bun is required to build assets"
        }
    }

    function Build-Java {
        $parent = Split-Path -Parent $Root
        if (Test-Path (Join-Path $parent "pom.xml")) {
            Write-Host "Building $Root as a Maven module"
            Push-Location $parent
            try {
                mvn -B -q "-pl" (Split-Path -Leaf $Root) "-am" "-DskipTests" package
                if ($LASTEXITCODE -ne 0) { throw "Maven build failed" }
            } finally {
                Pop-Location
            }
        } else {
            Write-Host "Building $Root as a standalone Maven project"
            mvn -B -q -DskipTests package
            if ($LASTEXITCODE -ne 0) { throw "Maven build failed" }
        }
    }

    switch ($Mode) {
        "assets" { Build-Assets }
        "java" { Build-Java }
        "all" { Build-Assets; Build-Java }
        default { throw "Unknown mode: $Mode (use assets, java or all)" }
    }
} finally {
    Pop-Location
}
