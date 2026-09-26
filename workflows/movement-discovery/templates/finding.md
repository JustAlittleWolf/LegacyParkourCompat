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

For each side: manifest artifact reference; relative source path; fully qualified class; member signature/descriptor; original line range; source hash. Include only the short exact expression/guard necessary to show the difference, retaining casts and literal suffixes. For resources include jar entry, key/value and hash. For absence cite the inspected caller, registration, inheritance or replacement path rather than a failed string search.

## Source-level difference

Describe old and new behavior under the same concrete preconditions. List changed constants, conditions, operation order, callbacks, data or state lifetime precisely. Explain correspondence if code moved or was split.

## Reachability and dependencies

Client player entry point -> relevant calls -> changed expression/data -> affected player state. Cite helper/default/tag/attribute/equipment/effect dependencies on both sides. Identify server-supplied inputs and limits of the client-only conclusion.

## Consequence and uncertainty

Separate source-proven facts from predicted position/velocity/pose/flag consequences. Record interactions, decompiler ambiguity and unanswered questions. Do not claim an observed trajectory, confirmed bug reproduction or validated fix.

## Handoff

Independent delta description; related finding IDs; applicability constraints; unresolved release boundary. Implementation and testing decisions are deferred.
