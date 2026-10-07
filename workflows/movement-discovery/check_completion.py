#!/usr/bin/env python3
"""Check movement discovery run structure and closure markers (not source truth)."""

from __future__ import annotations

import re
import sys
from pathlib import Path


INVENTORY_IDS = {
    "INV-TICK",
    "INV-STATE",
    "INV-COLLISION",
    "INV-WORLD-MOVEMENT",
    "INV-MODIFIERS",
    "INV-EXTERNAL",
    "INV-EXCLUSIONS",
}
TERMINAL = {"compared-no-difference", "findings", "not-applicable"}
VALID_SLICE_STATES = TERMINAL | {"pending", "in-progress", "blocked"}
PLACEHOLDERS = {"", "...", "<...>", "<IDs>", "<start-end>"}


def value(block: str, label: str) -> str | None:
    match = re.search(rf"^- {re.escape(label)}:\s*(.*?)\s*$", block, re.M)
    return match.group(1) if match else None


def concrete(item: str | None) -> bool:
    if item is None:
        return False
    normalized = item.strip().lower()
    return (
        normalized not in {placeholder.lower() for placeholder in PLACEHOLDERS}
        and "<" not in normalized
        and ">" not in normalized
    )


def source_evidence(item: str | None) -> bool:
    if not concrete(item):
        return False
    return bool(re.search(r"\blines\s+\d+\s*[-–]\s*\d+\b", item, re.I) or re.search(r"checked absence", item, re.I))


def check(path: Path) -> list[str]:
    manifest = path / "run.md" if path.is_dir() else path
    errors: list[str] = []
    if not manifest.is_file():
        return [f"run manifest not found: {manifest}"]
    content = manifest.read_text(encoding="utf-8")
    top_statuses = re.findall(r"^- Status:\s*(\S+)\s*$", content, re.M)
    if len(top_statuses) != 1:
        errors.append(f"expected exactly one top-level status, found {len(top_statuses)}")
        top_status = "invalid"
    else:
        top_status = top_statuses[0]
    if top_status not in {"active", "partial", "blocked", "complete"}:
        errors.append(f"invalid top-level status: {top_status}")

    inventory_rows = re.findall(r"^- `(INV-[A-Z-]+)` (.+)$", content, re.M)
    inventories = {inventory_id: row for inventory_id, row in inventory_rows}
    missing = INVENTORY_IDS - inventories.keys()
    extra = inventories.keys() - INVENTORY_IDS
    if missing:
        errors.append("missing required inventories: " + ", ".join(sorted(missing)))
    if extra:
        errors.append("unknown inventory IDs: " + ", ".join(sorted(extra)))
    for inventory_id, row in inventories.items():
        status = re.search(r"status=(pending|complete)", row)
        if not status:
            errors.append(f"{inventory_id}: missing valid inventory status")
        elif top_status == "complete" and status.group(1) != "complete":
            errors.append(f"{inventory_id}: inventory is not complete")
        evidence = re.search(r"evidence=(.*)$", row)
        if top_status == "complete" and (not evidence or not concrete(evidence.group(1))):
            errors.append(f"{inventory_id}: missing concrete inventory evidence")
        if top_status == "complete" and inventory_id != "INV-EXCLUSIONS":
            slices = re.search(r"slice_ids=(.*?);", row)
            if not slices or not concrete(slices.group(1)):
                errors.append(f"{inventory_id}: missing coverage slice IDs")

    slice_matches = list(re.finditer(r"^### Slice (.+)$", content, re.M))
    if not slice_matches:
        errors.append("no bounded coverage slice entries found")
    open_slices: list[str] = []
    for index, match in enumerate(slice_matches):
        end = slice_matches[index + 1].start() if index + 1 < len(slice_matches) else len(content)
        block = content[match.start() : end]
        slice_id = match.group(1).split(":", 1)[0].strip()
        status = value(block, "Status")
        if status not in VALID_SLICE_STATES:
            errors.append(f"slice {slice_id}: missing or invalid status")
            open_slices.append(slice_id)
            continue
        if status not in TERMINAL:
            open_slices.append(slice_id)
            continue
        for label in ("Inventory ID(s)", "State producers/writers -> consumers/readers", "Parent slices / dependencies / closure evidence", "Disposition and rationale (including concrete reachability/preconditions)"):
            item = value(block, label)
            if not concrete(item):
                errors.append(f"slice {slice_id}: missing concrete `{label}`")
        for label in ("A evidence", "B evidence"):
            if not source_evidence(value(block, label)):
                errors.append(f"slice {slice_id}: `{label}` must cite a source range or checked absence")
        if status == "findings" and not concrete(value(block, "Finding IDs or checked absence/replacement path")):
            errors.append(f"slice {slice_id}: findings disposition has no finding ID")

    if top_status == "complete" and open_slices:
        errors.append("non-terminal coverage slices: " + ", ".join(open_slices))
    if top_status == "complete":
        for heading, expected in (
            ("Blind-discovery freeze", "frozen"),
            ("Implementation reconciliation", "complete"),
            ("Independent source audit", "passed"),
        ):
            section = re.search(rf"^## {re.escape(heading)}\s*$([\s\S]*?)(?=^## |\Z)", content, re.M)
            if not section or not re.search(rf"^- (?:Status|Reconciliation status):\s*{expected}\s*$", section.group(1), re.M):
                errors.append(f"{heading}: expected status `{expected}`")
        audit = re.search(r"^## Independent source audit\s*$([\s\S]*?)(?=^## |\Z)", content, re.M)
        if audit:
            for label in ("Reviewer", "Inventories and call-chain ranges re-walked", "Concrete missed-slice routes (or `none found`)", "Misses routed to slice/finding IDs and owners", "Reviewer evidence / date"):
                if not concrete(value(audit.group(1), label)):
                    errors.append(f"Independent source audit: missing concrete `{label}`")
        if not re.search(r"^- Open dependencies:\s*none\s*$", content, re.M):
            errors.append("open dependencies remain or are not explicitly marked none")

    if errors:
        return errors
    if top_status != "complete":
        return [f"run status is `{top_status}`; this is not a completion pass"]
    return ["schema/status gate passed; source truth still requires independent review"]


def main() -> int:
    if len(sys.argv) != 2:
        print("usage: python check_completion.py <run-folder-or-run.md>", file=sys.stderr)
        return 2
    results = check(Path(sys.argv[1]))
    for result in results:
        print(result)
    return 0 if len(results) == 1 and results[0].startswith("schema/status gate passed") else 1


if __name__ == "__main__":
    raise SystemExit(main())
