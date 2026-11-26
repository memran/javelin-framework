"""Combine per-suite performance JSON files into one CI-friendly report."""

import glob
import json
import os
from datetime import datetime, timezone

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SOURCES = os.path.join(ROOT, "modules", "javelin-deployment", "target", "performance-*.json")
OUTPUT = os.path.join(ROOT, "target", "performance-report.json")


def main():
    suites = []
    for path in sorted(glob.glob(SOURCES)):
        with open(path, encoding="utf-8") as source:
            suites.append(json.load(source))
    os.makedirs(os.path.dirname(OUTPUT), exist_ok=True)
    report = {
        "generated_at": datetime.now(timezone.utc).isoformat(),
        "suites": suites,
    }
    with open(OUTPUT, "w", encoding="utf-8") as target:
        json.dump(report, target, indent=2)
        target.write("\n")
    print(f"Aggregated {len(suites)} suite(s) into {OUTPUT}")


if __name__ == "__main__":
    main()
