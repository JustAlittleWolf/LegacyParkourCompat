"""Render a read-only, hierarchical Markdown view of the movement backlog."""

import argparse
import json
import re
from collections import defaultdict
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
BACKLOG = ROOT / "development/research/backlog.json"
OUTPUT = ROOT / "development/research/PROGRESS.md"


def version_key(text):
    return tuple(int(part) for part in re.findall(r"\d+", text))


def markdown_text(value):
    return str(value).replace("|", "\\|").replace("\r", " ").replace("\n", " ").strip()


def short(value, limit=220):
    value = markdown_text(value)
    return value if len(value) <= limit else value[: limit - 1].rstrip() + "…"


def pair_from_finding(path):
    return Path(path).parent.name


def linked_change(path):
    return f"[`{path.rsplit('/', 1)[-1]}`]({('../../' + path).replace(' ', '%20')})"


def finding_review_note(finding):
    note = finding.get("sourceReview") or "No source-review summary recorded."
    decisions = [review.get("decision") for review in finding.get("sourceReviews", [])
                 if isinstance(review, dict) and review.get("decision")]
    suffix = f" Recorded review decisions: {len(decisions)}" + (f" ({'; '.join(decisions[:2])})" if decisions else " (none)") + "."
    return short(note + suffix, 220)


def issue_detail(issues):
    if not issues:
        return "0"
    details = [f"<details><summary>{len(issues)} open</summary>"]
    for issue in issues:
        if isinstance(issue, dict):
            missing = issue.get("missing", "Open issue")
            action = issue.get("next_action") or issue.get("nextAction")
            item = f"- {short(missing, 180)}"
            if action:
                item += f" Next: {short(action, 180)}"
        else:
            item = f"- {short(issue, 220)}"
        details.append(item)
    details.append("</details>")
    return "<br>".join(details)


def review_detail(finding):
    reviews = finding.get("sourceReviews", [])
    if not reviews:
        return ""
    items = [f"<details><summary>{len(reviews)} recorded review entries</summary>"]
    for review in reviews:
        if not isinstance(review, dict):
            continue
        decision = review.get("decision", "Decision not recorded")
        reviewer = review.get("reviewer")
        snapshot = review.get("snapshot", {})
        identity = snapshot.get("revision") or snapshot.get("sha256") if isinstance(snapshot, dict) else None
        ref = snapshot.get("path") if isinstance(snapshot, dict) else None
        item = "- " + short(decision, 220)
        if reviewer:
            item += " — " + short(reviewer, 100)
        if identity:
            item += " — evidence `" + short(identity, 100) + "`"
        if ref:
            item += " at `" + short(ref, 140) + "`"
        items.append(item)
    items.append("</details>")
    return "<br>".join(items)


def pair_group(pair):
    old = pair.split("--", 1)[0]
    parts = old.split(".")
    return ".".join(parts[:2]) if len(parts) > 1 else old


def render(data):
    findings_by_pair = defaultdict(list)
    for finding in data["findings"]:
        findings_by_pair[pair_from_finding(finding["finding"])].append(finding)
    coverage_by_pair = {item["pair"]: item for item in data["coverage"]}
    claims_by_pair = defaultdict(list)
    for claim in data.get("unresolvedClaims", []):
        claims_by_pair[claim["pair"]].append(claim)

    statuses = {status: sum(x["implementation"] == status for x in data["findings"])
                for status in ("present", "partial", "open")}
    versions = sorted(set(findings_by_pair) | set(coverage_by_pair) | set(claims_by_pair),
                      key=lambda pair: tuple(version_key(v) for v in pair.split("--")))
    pairs_by_group = defaultdict(list)
    for pair in versions:
        pairs_by_group[pair_group(pair)].append(pair)

    lines = [
        "# Movement progress by Minecraft version",
        "",
        "> Generated snapshot from `backlog.json`; regenerate with `python development/workflows/orchestration/render_progress.py`.",
        "> Read-only view: this script never writes to the backlog. Findings are operation-level records. `present` means static implementation correspondence; it does not mean the operation is accepted, complete for a release pair, or runtime-parity verified.",
        "",
        "## Snapshot",
        "",
        f"- Product evidence revision: `{data.get('productRevision', 'not recorded')}`",
        f"- Recorded findings (operations): {len(data['findings'])} — {statuses['present']} present, {statuses['partial']} partial, {statuses['open']} open implementation correspondence.",
        f"- Version-pair coverage records: {len(data['coverage'])}; unresolved claims: {len(data.get('unresolvedClaims', []))}; implementation evidence gaps: {len(data.get('implementationEvidenceGaps', []))}.",
        f"- Runtime: {markdown_text(data.get('runtime', 'not recorded'))}",
        "- These totals describe backlog records only. They do not measure coverage of all release differences or tick-by-tick parity.",
        "",
        "## Version map",
        "",
    ]
    for group in sorted(pairs_by_group, key=version_key):
        links = [f"[`{pair}`](#{pair.replace('.', '')})" for pair in pairs_by_group[group]]
        lines.append(f"- **{group}.x:** " + ", ".join(links))

    lines.extend(["", "## Pair detail", ""])
    for group in sorted(pairs_by_group, key=version_key):
        lines.extend([f"### Minecraft {group}.x", ""])
        for pair in pairs_by_group[group]:
            coverage = coverage_by_pair.get(pair, {})
            pair_findings = sorted(findings_by_pair.get(pair, []), key=lambda x: x["finding"])
            pair_statuses = {status: sum(x["implementation"] == status for x in pair_findings)
                             for status in ("present", "partial", "open")}
            lines.extend([f"#### {pair}", ""])
            if not coverage:
                lines.append("No pair coverage record is present in the backlog.")
                lines.append("")
            else:
                lines.extend([
                    f"- **Coverage:** {short(coverage.get('sourceCoverage', 'not recorded'), 190)}",
                    f"- **Dependency closure:** {short(coverage.get('dependencyClosure', 'not recorded'), 190)}",
                    f"- **Independent coverage audit:** {short(coverage.get('independentCoverageAudit', 'not recorded'), 190)}",
                    f"- **Next action:** {short(coverage.get('nextAction', 'not recorded'), 260)}",
                ])
                examined = coverage.get("examinedSlices", {})
                if isinstance(examined, dict):
                    slices = [f"{len(examined.get(key, []))} {key}" for key in ("noDifference", "finding", "partial", "excluded") if examined.get(key)]
                    if slices:
                        lines.append("- **Current examined-slice ledger:** " + "; ".join(slices) + ".")
                elif examined:
                    lines.append(f"- **Current examined-slice ledger:** {short(examined, 220)}")
                pending = coverage.get("unexaminedSlices", [])
                dependencies = coverage.get("openDependencies", [])
                if pending:
                    lines.append(f"- **Unexamined slices:** {len(pending)} recorded; " + short("; ".join(pending), 260))
                if dependencies:
                    lines.append(f"- **Open dependencies:** {len(dependencies)} recorded; " + short("; ".join(dependencies), 260))
                evidence = coverage.get("evidence", {})
                if evidence.get("revision"):
                    ref = evidence.get("path")
                    label = f"`{evidence['revision'][:12]}`"
                    if ref and (ROOT / ref).is_file():
                        label = f"[`{evidence['revision'][:12]}`]({('../../' + ref).replace(' ', '%20')})"
                    suffix = f"; `{ref}`" if ref and not (ROOT / ref).is_file() else ""
                    lines.append(f"- **Coverage evidence:** {label}{suffix} (historical evidence identity).")
                if coverage.get("recordLimit"):
                    lines.append(f"- **Record limit:** {short(coverage['recordLimit'], 210)}")
                lines.append("")

            lines.append(f"**Findings ({len(pair_findings)} operations: {pair_statuses['present']} present, {pair_statuses['partial']} partial, {pair_statuses['open']} open)**")
            lines.append("")
            if not pair_findings:
                lines.extend(["No finding records.", ""])
            else:
                lines.extend(["| Finding | Implementation | Recorded source-review note | Changes | Open issues |", "|---|---|---|---|---:|"])
                for finding in pair_findings:
                    path = finding["finding"]
                    title = Path(path).stem.replace("-", " ")
                    finding_link = f"[`{title}`]({('../../' + path).replace(' ', '%20')})"
                    changes = ", ".join(linked_change(change) for change in finding.get("changes", [])) or "—"
                    issue_summary = issue_detail(finding.get("openIssues", []))
                    review_summary = finding_review_note(finding) + ("<br>" + review_detail(finding) if finding.get("sourceReviews") else "")
                    lines.append(f"| {finding_link} | `{finding['implementation']}` | {review_summary} | {changes} | {issue_summary} |")
                lines.append("")

            if claims_by_pair[pair]:
                lines.extend([f"**Unresolved claims ({len(claims_by_pair[pair])})**", ""])
                for claim in claims_by_pair[pair]:
                    lines.append(f"- `{claim['claim']}` — {short(claim.get('openIssues', 'Open claim; see backlog record.'), 230)}")
                lines.append("")

    lines.extend([
        "## Reading the statuses",
        "",
        "- **Coverage** describes the bounded inventory captured by the pair record. `complete` or a closed dependency statement is historical evidence scoped to that record and its cited identity; it is not a current independent audit by itself.",
        "- **Finding source-review note** preserves the backlog's short review statement. Inspect the linked finding and its source-review evidence before treating a result as accepted; reviews are bound to their source snapshot and publication identity.",
        "- **Implementation** shows the backlog's static correspondence field. Check linked Change files and implementation limits for the actual scope.",
        "- **Runtime parity** is separate from discovery acceptance, implementation correspondence, and compilation. The backlog currently records runtime parity as unverified.",
        "",
    ])
    return "\n".join(lines)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--check", action="store_true", help="fail if PROGRESS.md differs from the rendered backlog")
    args = parser.parse_args()
    data = json.loads(BACKLOG.read_text(encoding="utf-8-sig"))
    output = render(data)
    if args.check:
        current = OUTPUT.read_text(encoding="utf-8") if OUTPUT.exists() else ""
        if current != output:
            raise SystemExit("PROGRESS.md is stale; run render_progress.py to refresh it")
        print(f"PROGRESS.md is current ({len(data['findings'])} findings, {len(data['coverage'])} pairs).")
        return
    OUTPUT.write_text(output, encoding="utf-8", newline="\n")
    print(f"Wrote {OUTPUT.relative_to(ROOT)} ({len(data['findings'])} findings, {len(data['coverage'])} pairs).")


if __name__ == "__main__":
    main()
