# Independent source audit — moving-block sweep witness r2

Verdict: **ACCEPT**, bounded to the immutable witness below. This is an independent source acceptance for the stated single move query; it does not close exact-pair inventory, general piston behavior, runtime parity, or any wider applicability question.

## Immutable bindings

- Claim/report: `build/source-189-19-20261117/input-travel/moving-block-stone-sweep-witness-r2.md`
- Report SHA-256: `05A2B44304FE83008B9896D7BB5BCF23B51EA85E3A4F38EE5A94174D87C6EC91`
- Older exact source namespace: `decompiled_minecraft/1.8.9/ornithe-feather/`; root provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`; root source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.
- Newer exact source namespace: `decompiled_minecraft/1.9/ornithe-feather/`; mapping-local `.decompile-provenance.json` SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.

The report file hash matches its binding. I recomputed the listed source hashes from the exact source trees; the 1.8.9 source sidecar and 1.9 provenance `sources` rows match the inspected `World.java`, `MovingBlock.java`, `MovingBlockEntity.java`, `Entity.java`, and `Box.java` files. `StoneBlock.java` is byte-identical across the compared namespaces.

## Audit findings

- The tick ordering is supported: each version processes regular entities before ticking block entities. `MovingBlockEntity.tick()` stores the previous progress and advances by `0.5F`; the next regular-entity phase can therefore observe `(progress,lastProgress)=(0.5F,0.0F)`. In this phase the player move occurs before the next moving-block tick.
- At that state, the 1.8.9 extending collision path samples `lastProgress=0`, converts it to `1.0F`, and trims the full cube's east-facing max X to produce a zero-width X box. More decisively for this witness, its `World.getCollisions` X loop is `floor(minX)` through (exclusive) `floor(maxX + 1)`: for query `[-1.6,-0.5]`, it visits `-2,-1` and omits cell `0`.
- In 1.9, the moving-block entity unions the current and previous progress shapes. At `0.5F` and `0.0F`, the eastward extension offsets are `-0.5` and `-1.0`, so the local full-cube union is `[-1,0.5] × [0,1] × [0,1]`. The collision hook translates this shape by the block position before intersection testing.
- The 1.9 X loop is `floor(minX)-1` through (exclusive) `ceil(maxX)+1`, so it includes cell `0` for this query. Its border/corner optimization does not skip the candidate `(0,0,0)`: X is on the scanned boundary, Z is interior, and the resulting gate value is `1`, not the rejected value `2`. With the report's loaded-chunk and empty-neighborhood preconditions, this cell supplies the moving-block collision box. The strict intersection checks admit overlap between the swept query and `[-1,0.5]`.
- `Entity.move` clips Y, then X, then Z in both versions. The 1.9 obstacle's min X and player max X both equal `-1`; the positive-X `intersectX` branch clips the requested `0.5` to a gap of `0`. The old scan produces no block candidate for the query, leaving X at `0.5`. The report correctly identifies the cause as the combined scan-bound and emitted-union difference, not as an isolated shape-only effect.
- The reported player dimensions and query are internally consistent with width `0.6F` and height `1.8F`. `dy=0` with `onGround=false` leaves the step-up predicate false; the witness uses the ordinary non-riding entity move path. The otherwise-empty collision neighborhood and clear world border are explicit assumptions, not claims that every world realizes this configuration.

No blocking dependency remains for this bounded source-level query. The report appropriately avoids claiming a general piston trajectory, universal reachability, or exact intermediate introduction boundary.

## Verdict identity

This verdict accepts only report SHA `05A2B44304FE83008B9896D7BB5BCF23B51EA85E3A4F38EE5A94174D87C6EC91` against the exact source identities above. Any change to the witness, source publication, geometry, gates, or applicability requires a renewed bounded audit. Full-pair coverage and freeze remain separate.
