# Independent review: 1.8.9 Tripwire Hook provider and alias closure

Verdict: **ACCEPT within the stated alias, movement-collision, and ray-shape provider scope.**

## Evidence identity

The input report SHA-256 is `7EA5C2DAFCF6235E14A7B8F63AD8128E6EEC2FBFE9BCEDF8CFA37662D9302D17`; its `source-hashes.sha256` manifest SHA-256 is `2DB580D0911516CEE3E7FC74AE8936FF6A1447CB42859E316445EFA2AE80BC6E`. I rehashed all 20 entries against the canonical read-only sources. Every selected Java file matched its corresponding canonical source-manifest entry, and the ready-marker/provenance identities match 1.8.9 `ornithe-feather` (Feather Gen2 1.8.9+build.2) and 26.2 `unobfuscated`.

## Verified claims

- 1.8.9 registers old ID 131 as `tripwire_hook`, and `Blocks.TRIPWIRE_HOOK` resolves that same key. `getStateFromMetadata` masks low four bits: horizontal direction from `metadata & 3`, `POWERED` from bit 3, and `ATTACHED` from bit 2. All 0–15 values are valid because the direction input is always 0–3. `SUSPENDED` is virtual from the block below and is not read by `updateShape`.
- The old movement collision provider returns `null`; `Block.addCollisions` appends a collision only after a non-null provider result. Thus Tripwire Hook contributes no movement-collision box in any state.
- The old ray shape is a separate path. `Block.rayTrace` calls `updateShape` before reading shared bounds; `TripwireHookBlock.updateShape` selects a facing-dependent box and writes those fields. The report's four rotated horizontal bounds follow the source's `f = 0.1875F` formulas. The base `resetShape` is a no-op, but this class refreshes its bounds for each old `Block.rayTrace` call, so the report correctly does not infer stale prior-facing bounds for that call.
- 26.2 registers `minecraft:tripwire_hook` with `Properties.noCollision()`, and the base collision provider returns empty when `hasCollision` is false. This agrees with the old empty movement-collision result.
- The current block's state-indexed outline shape is selected by `FACING`; its NORTH base `boxZ(6,0,10,10,16)` gives X `[5/16,11/16]`, Y `[0,10/16]`, and Z `[10/16,1]`. Horizontal rotation maps the remaining facings. Its Y interval differs from old ray bounds `[0.2F,0.8F]`; these current outline bounds are not a replacement for the old ray shape.
- `ClipContext` separately selects collision shape for `COLLIDER` and outline `getShape` for `OUTLINE`. The source closure correctly leaves the caller's selected mode and concrete DDA/ray route open.

## Scope limits

No finding is made about DDA visitation, ray-provider ordering, which current clip mode is used by a particular camera path, a concrete movement consequence, runtime reachability, or tick-level parity. The review accepts only the stated old/current key and state mapping and the bounded collision/ray-shape provider facts.

No product code, build, tests, game or runtime were used.
