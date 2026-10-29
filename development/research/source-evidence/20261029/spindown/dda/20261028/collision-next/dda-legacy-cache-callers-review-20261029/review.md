# Independent review: 1.8.9 shared Block-bounds caller inventory

Snapshot: 2026-10-29.

## Verdict

**ACCEPT**, bounded to the source-visible 1.8.9 shared-bounds caller and reader inventory. This does not establish runtime scheduling, full cache lifecycle validity, state translation, or DDA adapter readiness.

## Reviewed inputs

| Artifact | SHA-256 |
|---|---|
| Author report `dda-legacy-cache-callers-20261029/report.md` | `AB3399383A2A479650CA0F1F4330658698BD3DF612B22D983BA118019294CAB8` |
| `source-hashes.json` | `A3310A4724F345C4B6D8990F8EB99FE37FDF4DA3202768E15C76ADEF114506FA` |
| `ray-call-sites.txt` | `9E7D77F266AACBE3F35DCBA38BA86CFAE34D56B67462C8CFB9BE8BE72C218EB5` |
| `shape-refresh-call-sites.txt` | `09A302FB0FEA66AD171C0C6A1E4AB01155A7B96D218E628D9504EA25D262516A` |
| `collision-read-call-sites.txt` | `C2B74CA010EF0FD1E9DB77FB2AAFED16BE930A1673CBAB5FB68AAEE08A3C5D3E` |
| `block-instance-writers.txt` | `EB90C5450FF42838153FAA2AB752DA8685FF1046CF7889B9C57D89EBC2B5BAC5` |
| Independent source checklist `independent-source-checklist.md` | `041D5C5649A972B8CA592EF6B39C692A64513109CC73C1E9EA4B55C3B476573B` |
| Canonical 1.8.9 source manifest `ornithe-feather.sources.sha256` | `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004` |

The review rechecked all 32 source-file hashes listed by the author and all six artifact hashes against current bytes; there were zero mismatches. A separate whole-source search independently confirmed the direct `World.rayTrace` call families and the additional `updateShape` paths noted below.

## Findings

- The report correctly treats `Block` bounds as mutable fields on shared registered block instances. It binds the base ray writer, collision/outline readers, and the `World.getCollisions` route used by ordinary entity movement.
- All direct world-ray paths found by independent search are accounted for: camera pick and third-person camera obstruction; item target acquisition and boat placement; living-entity line of sight; water navigation; projectile, thrown-item, arrow and fishing ticks; and explosion block-density samples. The accepted caller flags, including the conditional collision-shape prefilter, are retained as inputs rather than assumed identical.
- Non-ray writer paths found independently are included and properly qualified: block-model rendering, liquid rendering (whose `LiquidBlock.updateShape` inherits the base no-op), block-outline rendering, weather/precipitation, rain particles, dye particles, Arrow's in-ground update, and MovingBlock's moved-state delegation. The report distinguishes real block-bounds writes from unrelated same-name `updateShape` methods.
- The writer inventory covers both `setShape` and direct inherited-field assignments. It recognizes that movement/provider callbacks can themselves refresh shared bounds; it does not call such callbacks universal resets.
- The report correctly distinguishes `World.getBlockCollisions` and `getCollisions` from `World.isUnobstructed`: in this source, `isUnobstructed` checks entities only. It also corrects the tempting base `Entity.pushAwayFrom` reader claim: local `LocalClientPlayerEntity` overrides that method and does not call the base block-bounds path. The direct ordinary local-player cache reader remains `Entity.move` through `World.getCollisions`.
- The accepted ID 146 correction/review is explicitly bound alongside the writer-matrix report, so the report's Anvil 145, Chest 54, and Trapped Chest 146 movement-consumer count does not inherit the matrix's superseded two-consumer wording.
- The final limitation is appropriately narrow. Source-visible rendering, item, projectile, AI, explosion, and world paths can mutate the shared instances; no universal invalidation/observation boundary or cache replay is shown. The report leaves actor timing, remaining consumer reachability, and adapter validity open instead of assuming those paths harmless because they are outside the product's movement behavior.

No source omission or unsupported lifecycle-closure claim was found within the stated engine-source inventory. External mod/plugin callers and runtime interleavings are not established by this audit and remain outside the acceptance.

## Evidence and conclusion

The independent checklist is preserved in the same review directory. Its source roots, exact source hashes, call-site distinctions, and review checklist are available for rechecking. The author report satisfies the requested source-level inventory and accurately retains the remaining lifecycle gate. No product code, tests, builds, or runtime were inspected or used.
