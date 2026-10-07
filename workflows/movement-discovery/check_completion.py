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
REQUIRED_SECTIONS = {
    "Artifact manifest",
    "Blind-discovery freeze",
    "Correspondence and call order",
    "Required source inventories",
    "Coverage ledger",
    "Dependency queue and blockers",
    "Finding index",
    "Resume checkpoint",
    "Implementation reconciliation",
    "Independent source audit",
    "Source audit closure",
}
SHA256 = re.compile(r"^[0-9a-fA-F]{64}$")


def value(block: str, label: str) -> str | None:
    match = re.search(rf"^- {re.escape(label)}:\s*(.*?)\s*$", block, re.M)
    return match.group(1) if match else None


def concrete(item: str | None) -> bool:
    if item is None:
        return False
    normalized = item.strip().lower()
    if normalized in {placeholder.lower() for placeholder in PLACEHOLDERS}:
        return False
    if "<...>" in normalized or re.search(r"<[^<>]*\s[^<>]*>", normalized):
        return False
    return True


def source_evidence(item: str | None) -> bool:
    if not concrete(item):
        return False
    return bool(re.search(r"\blines\s+\d+\s*[-–]\s*\d+\b", item, re.I) or re.search(r"checked absence", item, re.I))


def heading_blocks(section: str, heading: str) -> list[tuple[str, str]]:
    matches = list(re.finditer(rf"^### {re.escape(heading)} (.+?)\s*$", section, re.M))
    blocks: list[tuple[str, str]] = []
    for index, match in enumerate(matches):
        end = matches[index + 1].start() if index + 1 < len(matches) else len(section)
        blocks.append((match.group(1).strip(), section[match.start() : end]))
    return blocks


def section_content(content: str, heading: str) -> str | None:
    match = re.search(rf"^## {re.escape(heading)}\s*$([\s\S]*?)(?=^## |\Z)", content, re.M)
    return match.group(1) if match else None


def check_evidence_identity(block: str, label: str, errors: list[str], *, require_complete: bool) -> None:
    publication = value(block, "Publication status")
    if publication not in {"original-verified", "revised-derived", "pending"}:
        errors.append(f"{label}: missing or invalid publication status")
        return
    if publication == "pending":
        if require_complete:
            errors.append(f"{label}: pending publication identity prevents completion")
        return

    required = [
        "Immutable evidence path",
        "Evidence artifact SHA-256",
        "Evidence manifest path",
        "Evidence manifest SHA-256",
        "Original artifact-manifest path",
        "Original artifact-manifest SHA-256",
    ]
    if publication == "revised-derived":
        required.extend([
            "Revision ID",
            "Original derived-artifact availability",
            "Original derived-artifact SHA-256 or expected hash",
            "Source/raw-input hash relation",
            "Revised-to-original derived-artifact equivalence",
            "Provenance limitations",
        ])
        if value(block, "Revision ID") == "none":
            errors.append(f"{label}: revised-derived evidence requires a revision ID")
    for field in required:
        item = value(block, field)
        if not concrete(item):
            errors.append(f"{label}: missing concrete `{field}`")
        if field.endswith("SHA-256") and concrete(item) and not SHA256.fullmatch(item.strip()):
            errors.append(f"{label}: `{field}` must contain 64 hexadecimal characters")
    if publication == "original-verified" and value(block, "Revision ID") not in {None, "none"}:
        errors.append(f"{label}: original-verified evidence must use revision ID `none`")


def check_snapshot_identity(block: str, label: str, errors: list[str]) -> None:
    publication = value(block, "Publication status")
    if publication not in {"original-verified", "revised-derived", "pending"}:
        errors.append(f"{label}: missing or invalid publication status")
        return
    if publication == "pending":
        return
    fields = [
        "Evidence artifact record IDs",
        "Immutable evidence path(s)",
        "Evidence artifact SHA-256(s)",
        "Evidence manifest path(s)",
        "Evidence manifest SHA-256(s)",
        "Original artifact-manifest path(s)",
        "Original artifact-manifest SHA-256(s)",
        "Original derived-artifact availability/hash",
        "Source/raw-input hash relation",
        "Revised-to-original equivalence",
        "Provenance limitations",
    ]
    if publication == "revised-derived":
        fields.append("Revision ID(s)")
    for field in fields:
        item = value(block, field)
        if not concrete(item):
            errors.append(f"{label}: missing concrete `{field}`")
    for field in ("Evidence artifact SHA-256(s)", "Evidence manifest SHA-256(s)", "Original artifact-manifest SHA-256(s)"):
        item = value(block, field)
        if concrete(item) and not re.search(r"(?<![0-9a-fA-F])[0-9a-fA-F]{64}(?![0-9a-fA-F])", item):
            errors.append(f"{label}: `{field}` must include a 64-character hexadecimal SHA-256")


def check(path: Path) -> list[str]:
    manifest = path / "run.md" if path.is_dir() else path
    errors: list[str] = []
    if not manifest.is_file():
        return [f"run manifest not found: {manifest}"]
    content = manifest.read_text(encoding="utf-8")
    # Section-local slice/freeze/reviewer statuses also use `- Status:`. Only
    # inspect the metadata block before the first H2, accepting the old and
    # current template label for compatibility with already-created reports.
    header = re.split(r"(?m)^## ", content, maxsplit=1)[0]
    top_statuses = re.findall(r"^- (?:Run status|Status):\s*(\S+)\s*$", header, re.M)
    if len(top_statuses) != 1:
        errors.append(f"expected exactly one top-level status, found {len(top_statuses)}")
        top_status = "invalid"
    else:
        top_status = top_statuses[0]
    if top_status not in {"active", "partial", "blocked", "complete"}:
        errors.append(f"invalid top-level status: {top_status}")

    sections = set(re.findall(r"^## (.+?)\s*$", content, re.M))
    for missing_section in sorted(REQUIRED_SECTIONS - sections):
        errors.append(f"missing required section: {missing_section}")

    artifact_section = section_content(content, "Artifact evidence identities")
    artifact_records = heading_blocks(artifact_section or "", "Evidence artifact")
    if top_status == "complete" and not artifact_records:
        errors.append("Artifact evidence identities: complete report has no evidence artifact records")
    for artifact_id, block in artifact_records:
        check_evidence_identity(
            block,
            f"evidence artifact {artifact_id}",
            errors,
            require_complete=top_status == "complete",
        )

    snapshot_section = section_content(content, "Finding snapshots (not pair freeze)")
    for snapshot_id, block in heading_blocks(snapshot_section or "", "Snapshot event"):
        publication = value(block, "Publication status")
        event_status = value(block, "Snapshot status") or value(block, "Status")
        handoff = value(block, "Implementation handoff")
        needs_identity = event_status in {"accepted", "source-confirmed"} or handoff == "ready"
        if publication == "revised-derived":
            check_snapshot_identity(block, f"snapshot {snapshot_id}", errors)
        elif needs_identity:
            if publication not in {"original-verified", "revised-derived"}:
                errors.append(f"snapshot {snapshot_id}: accepted/ready handoff requires a declared publication status")
            else:
                check_snapshot_identity(block, f"snapshot {snapshot_id}", errors)
        if handoff == "ready" and not concrete(value(block, "Verified implementation boundary/evidence, or unresolved boundary reason")):
            errors.append(f"snapshot {snapshot_id}: ready handoff lacks a concrete verified boundary")

    inventory_rows = re.findall(r"^- `(INV-[A-Z-]+)` (.+)$", content, re.M)
    inventories = {inventory_id: row for inventory_id, row in inventory_rows}
    if len(inventory_rows) != len(inventories):
        errors.append("duplicate required inventory IDs")
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
        required_fields = (
            "Inventory ID(s)",
            "Exact behavior boundary and enclosing guards/order checked",
            "A evidence",
            "B evidence",
            "State producers/writers -> consumers/readers",
            "Parent slices / dependencies / closure evidence",
            "Disposition and rationale (including concrete reachability/preconditions)",
            "Finding IDs or checked absence/replacement path",
        )
        for label in required_fields:
            if value(block, label) is None:
                errors.append(f"slice {slice_id}: missing required field `{label}`")
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
        return [f"non-complete report status `{top_status}` is structurally valid; completion is not claimed"]
    return ["schema/status gate passed; source truth still requires independent review"]


def main() -> int:
    if len(sys.argv) != 2:
        print("usage: python check_completion.py <run-folder-or-run.md>", file=sys.stderr)
        return 2
    results = check(Path(sys.argv[1]))
    for result in results:
        print(result)
    return 0 if len(results) == 1 and (
        results[0].startswith("schema/status gate passed")
        or results[0].startswith("non-complete report status")
    ) else 1


if __name__ == "__main__":
    raise SystemExit(main())
