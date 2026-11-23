#!/usr/bin/env sh
# Build static assets and/or the Java jar. Usage: ./build.sh [assets|java|all]
set -eu

ROOT=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
cd "$ROOT"
MODE=${1:-all}

build_assets() {
  if command -v bun >/dev/null 2>&1; then
    echo "Building assets with bun"
    bun install
    bun run build
  elif command -v npm >/dev/null 2>&1; then
    echo "Building assets with npm"
    npm install
    npm run build
  else
    echo "npm or bun is required to build assets" >&2
    exit 1
  fi
}

build_java() {
  PARENT=$(dirname "$ROOT")
  if [ -f "$PARENT/pom.xml" ]; then
    echo "Building $ROOT as a Maven module"
    (cd "$PARENT" && mvn -B -q -DskipTests -pl "$(basename "$ROOT")" -am package)
  else
    echo "Building $ROOT as a standalone Maven project"
    mvn -B -q -DskipTests package
  fi
}

case "$MODE" in
  assets) build_assets ;;
  java) build_java ;;
  all) build_assets; build_java ;;
  *)
    echo "Usage: $0 [assets|java|all]" >&2
    exit 1
    ;;
esac
