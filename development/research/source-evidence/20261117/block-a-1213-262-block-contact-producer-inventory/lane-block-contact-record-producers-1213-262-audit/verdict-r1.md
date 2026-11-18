# Independent r1 evidence rebind verdict

Verdict: **ACCEPT, bounded to the corrected inventory and roster identity.** This rebind confirms the specified corrected-r1 artifacts and their deltas; it does not reopen the broader source inventory or establish runtime behavior.

## Bound inputs and retained originals

- Corrected primary inventory: `inventory.corrected-r1.md` — SHA-256 `a0945dc7b1bf076cfd3732b6524ec0d190b6c266d9b610be446d6e5d8080b0ca`.
- Corrected primary roster: `frozen-roster.corrected-r1.tsv` — SHA-256 `7207b8b90fece803e627353c4b09d9628b9cae24f97a88b3503cb3941155338f`.
- Original primary inventory: `inventory.md` — SHA-256 `7f46b7ca997c677fc48acb9d6e2a3e64f02a9e785cedab3e5eaa2b8611222b72`.
- Original primary roster: `frozen-roster.tsv` — SHA-256 `486f4ee2bb856388a524461b366e4249eb3bec0d5abc50be7211102c4eea855b`.
- Independent precomparison roster: audit scratch `frozen-roster.tsv` — SHA-256 `e6524f3be107e6b74dee9276a5ef8cb978eeca181543745bf9cf400a23b12d13`.
- Final canonical audit roster: `audit-roster.tsv` — SHA-256 `96871f858e90a2060b2bb7826f3686dd6e602720042f3018fd2dd1ae3960932b`.
- Prior independent verdict retained: `audit-verdict.md` — SHA-256 `76eb7be109028a4896b300d83b12aa8e89393a66e238f3b241dc5f573fbc24ce`.

## Rebind checks

The original and corrected primary rosters each contain 61 rows. A programmatic row comparison found **exactly ten changes, all in `relative_path` fields**; pair, slice and SHA-256 columns are unchanged in every row. The ten corrected paths resolve to the exact ready, provenance, source-manifest, artifact-manifest and diagnostics sidecars. Each resolved file’s SHA-256 equals its roster value (10/10; zero missing paths or hash mismatches).

The corrected inventory includes the conditional 26.2 movement-record threshold `movementLength > 1.0E-7 || delta.lengthSqr() - movementLength < 1.0E-7`, the earlier `noPhysics` branch that bypasses recording, and the Piston-limited `Vec3.ZERO` early return. It also states that Piston continues after `Entity.move`, invokes explicit block effects, then removes the latest recording. Its `isAffectedByBlocks()` note distinguishes the common scan gate (`!isRemoved() && !noPhysics`) from the following queue-tail removal: a no-physics entity skips the scan, while the caller still reaches removal; removal is no-op only when the live queue is empty. These statements are consistent with the prior bounded exact-source audit and bind here to the corrected inventory SHA above.

## Scope

Accepted: corrected-r1 artifact identity, exact ten-path correction, and the stated movement/Piston branch conditions. No broader source review, implementation, product, wiki, backlog, test, build, game/runtime, network or Git work was performed for this rebind. No runtime prevalence or parity conclusion follows.
