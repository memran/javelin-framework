#!/usr/bin/env sh
set -eu

ROOT=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
JAR="$ROOT/modules/javelin-console/target/javelin-console-0.1.0-SNAPSHOT-all.jar"
BIN="${HOME}/.javelin/bin"
LAUNCHER="${BIN}/javelin"
PROFILE="${HOME}/.profile"

SKIP_BUILD=0
if [ "${1:-}" = "--skip-build" ]; then
  SKIP_BUILD=1
fi

need_command() {
  if ! command -v "$1" >/dev/null 2>&1; then
    echo "$1 is required. $2" >&2
    exit 1
  fi
}

need_command java "Install Java 25+ and make sure java is available on PATH."

if [ "$SKIP_BUILD" -eq 0 ]; then
  need_command mvn "Install Maven 3.9+ and make sure mvn is available on PATH."
  (cd "$ROOT" && mvn -pl :javelin-console -am package -DskipTests)
fi

if [ ! -f "$JAR" ]; then
  echo "CLI jar was not found at $JAR. Run without --skip-build first." >&2
  exit 1
fi

mkdir -p "$BIN"

cat > "$LAUNCHER" <<EOF
#!/usr/bin/env sh
exec java -jar "$JAR" "\$@"
EOF

chmod +x "$LAUNCHER"

case ":$PATH:" in
  *":$BIN:"*) ;;
  *)
    if [ -f "$PROFILE" ]; then
      if ! grep -F "$BIN" "$PROFILE" >/dev/null 2>&1; then
        printf '\nexport PATH="$HOME/.javelin/bin:$PATH"\n' >> "$PROFILE"
      fi
    else
      printf 'export PATH="$HOME/.javelin/bin:$PATH"\n' > "$PROFILE"
    fi
    ;;
esac

echo "Javelin installed."
echo "Launcher: $LAUNCHER"
echo "Open a new terminal, then run: javelin --help"
