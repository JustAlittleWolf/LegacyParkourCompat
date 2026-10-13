# <ID>: <one independently describable behavioral change>

- Older version A:
- Newer version B:
- Mechanic / coverage slice IDs:
- Classification: changed behavior | added mechanic | removed mechanic
- Confidence: source-confirmed | candidate (state the unresolved evidence)
- Applicability: historical player behavior | modern-only mechanic | player interaction with another entity | unresolved
- First changed release: unknown within (A, B] unless separately evidenced
- Runtime validation: not performed

## Paired evidence

Repeat for A and B, copying the matching `Evidence artifact <ID>` record or linking to it unambiguously. Leave unavailable values `pending` while the report is active; never silently substitute a revised artifact for its original record.

### A artifact identity

- Evidence artifact record ID:
- Publication status: original-verified | revised-derived | pending
- Revision ID (revised evidence only):
- Immutable evidence path:
- Evidence artifact SHA-256:
- Evidence manifest path / SHA-256:
- Original artifact-manifest path / SHA-256:
- Original derived-artifact availability and SHA-256 or expected hash:
- Source/raw-input hash relation and verification reference:
- Revised-to-original derived-artifact equivalence and evidence reference:
- Provenance limitations:
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash:

### B artifact identity

- Evidence artifact record ID:
- Publication status: original-verified | revised-derived | pending
- Revision ID (revised evidence only):
- Immutable evidence path:
- Evidence artifact SHA-256:
- Evidence manifest path / SHA-256:
- Original artifact-manifest path / SHA-256:
- Original derived-artifact availability and SHA-256 or expected hash:
- Source/raw-input hash relation and verification reference:
- Revised-to-original derived-artifact equivalence and evidence reference:
- Provenance limitations:
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash:

For resources include jar entry, key/value and hash. Include only the short exact expression/guard necessary to show the difference, retaining casts and literal suffixes. For absence cite the inspected caller, registration, inheritance or replacement path rather than a failed string search.

## Source-level difference

Describe old and new behavior under the same concrete preconditions. List changed constants, conditions, operation order, callbacks, data or state lifetime precisely. Explain correspondence if code moved or was split.

## Reachability and dependencies

Client player entry point -> relevant calls -> changed expression/data -> affected player state. Cite helper/default/tag/attribute/equipment/effect dependencies on both sides. Identify server-supplied inputs and limits of the client-only conclusion.

## Consequence and uncertainty

Separate source-proven facts from predicted position/velocity/pose/flag consequences. Record interactions, decompiler ambiguity and unanswered questions. Do not claim an observed trajectory, confirmed bug reproduction or validated fix.

## Handoff

Independent delta description; related finding IDs; applicability constraints; unresolved release boundary. Implementation and testing decisions are deferred.
