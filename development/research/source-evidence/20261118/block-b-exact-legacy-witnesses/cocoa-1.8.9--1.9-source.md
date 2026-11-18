# COMPLETED — Cocoa age-two 1.9 cutover

## Result

The earlier boundary is isolated to **1.8.9 → 1.9** for the fixed legal EAST-facing age-two cocoa geometry and grounded +X witness. **1.9, 1.9.1, 1.9.2, 1.9.3 and 1.9.4** all use the smaller EAST age-two box. The existing source-only result for 1.9.4 and later aliases remains unchanged; this work does not edit it.

| Exact release | Source identity | EAST age-two local bounds | EAST support |
|---|---|---|---|
| 1.8.9 | Version-parent `ornithe-feather.sources.sha256`, verified SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `[0.4375,0.1875,0.25]–[0.9375,0.75,0.75]` (`updateShape`: `j=8`, `k=9`) | `(1,0,0)` jungle log for cocoa at `(0,0,0)` |
| 1.9 | `.decompile-provenance.json`, verified SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | `[0.5625,0.3125,0.3125]–[0.9375,0.75,0.6875]` | `(1,0,0)` jungle log |
| 1.9.1 | `.decompile-provenance.json`, verified SHA-256 `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | same small age-two bounds | `(1,0,0)` jungle log |
| 1.9.2 | `.decompile-provenance.json`, verified SHA-256 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | same small age-two bounds | `(1,0,0)` jungle log |
| 1.9.3 | `.decompile-provenance.json`, verified SHA-256 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | same small age-two bounds | `(1,0,0)` jungle log |
| 1.9.4 | Version-parent `ornithe-feather.sources.sha256`, verified SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | same small age-two bounds | `(1,0,0)` jungle log |

Every source release computes support by offsetting the cocoa position in its `FACING` direction and requiring the jungle-log block/state. At age two, 1.8.9’s `j=4+2*age=8`, `k=5+2*age=9`, `f=j/2=4`; its EAST `updateShape` yields the expanded bounds above. In 1.9 onward the age-two array entry is identical to age one and the EAST selector returns the small box.

## Grounded +X witness

Use cocoa at `(0,0,0)`, `FACING=EAST`, `AGE=2`; a jungle log at `(1,0,0)`; floor with top `y=0`; and an ordinary player box `[-0.2,0,0.2]–[0.4,1.8,0.8]` (center `(0.1,0.9,0.5)`) at rest on that floor. Request `dx=+0.1`, `dy=dz=0`, with `noClip=false`, not sneaking, no jump, and grounded state. This is an ordinary horizontal displacement supplied to the existing entity move route. For 1.8.9, the swept player box reaches `maxX=0.5`, overlaps cocoa in Y and Z, and extends past its `minX=0.4375`; the X clip is `0.4375 - 0.4 = 0.0375`. Its 0.6 step attempt leaves the player intersecting the cocoa up to `y=0.75`, so it cannot improve the horizontal displacement. For 1.9 through 1.9.4, the same swept box stops at `x=0.5 < 0.5625`; this cocoa box cannot contribute a clip, leaving full `dx=0.1`.

The ordinary call path is `LivingEntity.tick` travel dispatch (`LivingEntity.java`, 1.9.3 lines 1705–1712) → `moveRelative` → the normal dry movement branch’s `move(this.velocityX, this.velocityY, this.velocityZ)` (`LivingEntity.java`, 1.9.3 lines 1430–1450). Living entities use `stepHeight=0.6F` (1.8.9 `LivingEntity.java:133`; 1.9.3 `LivingEntity.java:163`). `Entity.move` preserves the non-sneaking path, queries block collisions for the swept shape, clips vertical then X then Z, then evaluates the grounded step branch (1.8.9 `Entity.java:371–459, 460–540`; 1.9.3 `Entity.java:441–532, 533–621`). World collision queries visit block states and add their collision shapes (`World.java`, 1.8.9 `getCollisions` lines 891–925; 1.9.3 lines 899–940). The 1.9 `Entity.java` body is byte-identical for 1.9–1.9.2; 1.9.3 and 1.9.4 are byte-identical. Cocoa source hashes are byte-identical for 1.9–1.9.2 and for 1.9.3–1.9.4; each representative body and the exact 1.9.3 movement path were inspected.

## Inputs and integrity

- Canonical, read-only sources: the six specified `decompiled_minecraft/<version>/ornithe-feather` roots.
- The four intermediate provenance manifests and both endpoint version-parent source manifests match the supplied SHA-256 values above. All relevant source files and method bodies were present and readable.
- Prior lane result consumed as supplied: `b-source-cocoa-earlier-aliases/COMPLETED.md`, SHA-256 `9ec5f024eabf5dd93dd209d3fc88d5980b444c6bcd3a624342bfd8e45912ff3f`.
- Accepted earlier finding reference supplied by the parent (`1.10.2--1.11/cocoa-age-two-collision-shape.md`), SHA-256 `a9422edf0988b22e12503f4dff492e6865f0278d1ee5035d00867538e4d1b219`; that local path was not present in the primary checkout, and no attempt was made to locate another copy. Its supplied digest is retained as the reference identity.
- Draft eligible earlier finding: `development/research/findings/1.8.9--1.9/cocoa-age-two-collision-shape.md`.
- No implementation, tests, build, decompilation, gameplay/TAS/Gym/server launch, or shared research/backlog edit was performed.

## Residuals

This result establishes the Cocoa age-two operation’s concrete release boundary for the stated geometry/support and grounded +X witness only. It does not establish full-pair movement coverage, unrelated Cocoa behavior, or runtime parity. No uninspected 1.8.x patch behavior is inferred. Independent review and pair-level coverage audit remain with the master.
