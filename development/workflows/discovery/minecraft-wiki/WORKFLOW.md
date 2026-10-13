# Minecraft Wiki discovery

Read the [shared discovery rules](../WORKFLOW.md) first. This lane starts from Minecraft Wiki release/version pages, including their bugfix lists. Receive a bounded page/topic and release scope, read-only source identities and owned candidate finding paths. Do not use the source lane's findings, product implementation or the other wiki lane as the discovery checklist.

## Collect hints

1. Inventory each assigned exact release page, its change sections and bugfix entries. Follow movement-relevant linked descriptions or issue evidence when accessible. Do not limit collection to headings that explicitly say movement; collision, pose, fluids, equipment and direct player state responses can change movement.
2. For each relevant hint, capture its URL, page title and section, page revision/permalink when available (otherwise retrieval date), claimed versions, the specific claim and why it may affect player movement. Preserve uncertainty or contradictory claims explicitly. Report inaccessible pages as open gaps, not completed coverage.
3. Send the inventory and checked pages/sections, including areas with no relevant hints, to the master in chat. The master keeps this lane's hints separately in the relevant coverage record. Group related hints into bounded verification assignments; there is no required one-agent-per-hint split.

## Verify in source

4. Locate the exact older/newer source operations with enclosing gates, callers, dependencies and numeric order. Follow the shared evidence rules and distinguish what the wiki claims from what the paired source proves. Do not invent unavailable source ranges or exact intermediate patch boundaries.
5. For a confirmed independent source difference, write the shared permanent finding format at the assigned `development/research/findings/<older>--<newer>/<topic>.md` path. Send wiki provenance and the hint-to-finding association to the master. Unverified hints remain open centrally; contradicted or excluded hints receive a concise disposition, not a fabricated finding.
6. Obtain independent review of the exact source evidence before acceptance. A wiki claim or a source location alone is insufficient. This lane's completion means its bounded hint inventory has been checked and all remaining gaps disclosed; it never establishes complete player-tick coverage.

## Reconcile after source freeze

Keep hints and candidate findings isolated from blind source workers and reviewers. The master reconciles this inventory with the frozen source inventory only after full-pair freeze, mapping duplicates to the existing finding and assigning missing or conflicting claims for bounded follow-up verification. Do not overwrite another lane's finding. Preserve independent provenance and reopen affected evidence gates when a claim changes.
