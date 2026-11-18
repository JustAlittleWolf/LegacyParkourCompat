# Independent blind source audit: vertical integration, 1.21.1 to 1.21.2

Status: phase 1 complete; frozen prefix. Audit scope is the assigned ordinary local-player path in dry air, with no vehicle, gliding/flying, jump writer, fluid, effect, climb, block-effect, or collision/support-provider behavior. Movement is fixed-profile on the logical client. Source facts below are kept separate from trajectory consequences.

## Evidence identity and integrity

- 1.21.1 ready marker: `decompiled_minecraft/1.21.1/mojmap.ready.json`, SHA-256 `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`. It identifies version `1.21.1`, mapping `mojmap`, version metadata `1.21.1`, and source manifest digest `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`.
- 1.21.1 provenance: `decompiled_minecraft/1.21.1/mojmap.provenance.json`, SHA-256 `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`.
- 1.21.1 source manifest: `decompiled_minecraft/1.21.1/mojmap.sources.sha256`, SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`.
- 1.21.2 provenance: `decompiled_minecraft/1.21.2/mojmap/.decompile-provenance.json`, SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`. It records the exact metadata input URL ending in `/1.21.2.json` (SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`), client artifact URL object `c7ac2d0d86f4ca416cab9064ff8a281852ad0c7b` (SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`), and official client mapping input (SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`). Namespace is `mojmap`; the recorded decompiler tool is Vineflower 1.12.0 on Java 25.0.3.

The following decompiled Java files were hashed locally and matched their corresponding per-file source-manifest entries (1.21.1 `mojmap.sources.sha256`; 1.21.2 provenance `sources` map). These exact full-file pins authenticate the cited intact method bodies:

| Source file | 1.21.1 SHA-256 | 1.21.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/world/level/LevelHeightAccessor.java` | `0cf60403d255c4b13d56d36daabdb97199e800fec4a6d36f90a05915ef239786` | `5cceabe9c20940ccf8206e6c71f97b699484b5a06a44b40a5a7ff11d26a0cf06` |

## Reachable ordinary path and paired coverage

`LocalPlayer.tick` delegates through the living-player tick into `LivingEntity.aiStep`: 1.21.1 `LocalPlayer.java:191`, `LivingEntity.java:2319-2360`; 1.21.2 `LocalPlayer.java:189-192`, `LivingEntity.java:2418-2459`. Both `aiStep` implementations first copy delta movement and zero each component whose absolute value is below `0.003`: 1.21.1 `LivingEntity.java:2608-2624`; 1.21.2 `LivingEntity.java:2697-2713`. The ordinary travel dispatch follows: 1.21.1 `LivingEntity.java:2665-2680`; 1.21.2 `LivingEntity.java:2755-2772`.

For dry, non-flying travel, 1.21.1 `LivingEntity.travel` selects its final air branch at `LivingEntity.java:2192-2212`; its `handleRelativeFrictionAndCalculateMovement` performs the movement request at `LivingEntity.java:2252-2263`. In 1.21.2, `travel` dispatches to `travelInAir` at `LivingEntity.java:2178-2189`; air integration is at `LivingEntity.java:2191-2214`, and the equivalent helper/request is at `LivingEntity.java:2351-2362`. Each helper moves with the current delta before air gravity and drag are written back. For ordinary `Player` rather than a `FlyingAnimal`, the vertical update is `movementY - gravity`, then multiplied by `0.98F`; the ordinary no-effect path uses the same gravity value in both releases. The new `getEffectiveGravity` helper (`LivingEntity.java:2173-2175`) returns `getGravity()` when slow-falling does not apply. The old code computed the same slow-fall cap in `travel` before its branch (`LivingEntity.java:2091-2097`).

The unloaded-client-chunk fallback remains in the air branch: old code writes `-0.1` when `getY() > getMinBuildHeight()`, otherwise zero (`LivingEntity.java:2199-2205`); new code writes `-0.1` when `getY() > getMinY()`, otherwise zero (`LivingEntity.java:2200-2206`). `LevelHeightAccessor` changes the accessor name from `getMinBuildHeight` to `getMinY` (`LevelHeightAccessor.java:7-13` on each release); no change to the air branch's constants, comparison operator, or fallback ordering was found. Exact concrete world-height values were not independently audited in this bounded task.

The ordinary common fall-state accumulator and landing reset match: 1.21.1 `Entity.java:1163-1178` and `LivingEntity.java:327-359`; 1.21.2 `Entity.java:1216-1232` and `LivingEntity.java:341-373`. Both accumulate a downward movement by subtracting `(float)deltaY` when not on ground, and reset after grounded handling. `Entity.resetFallDistance` is a direct `fallDistance = 0.0F` in both (`Entity.java:2392-2394` versus `2490-2492`). The fall-damage-resetting clip gate before movement remains `fallDistance != 0.0F && resolvedMovement.lengthSqr() >= 1.0`, then resets on a non-miss: 1.21.1 `Entity.java:620-627`; 1.21.2 `Entity.java:642-649`.

## Findings and verdicts

### Accepted source difference: resolved movement position-update threshold

- 1.21.1 `Entity.move`, `Entity.java:617-630`: after edge adjustment and collision resolution, it calls `setPos` only if resolved movement length-squared is greater than `1.0E-7`.
- 1.21.2 `Entity.move`, `Entity.java:639-652`: it calls `setPos` if resolved movement length-squared is greater than `1.0E-7` **or** requested length-squared minus resolved length-squared is less than `1.0E-7`.
- `setPos` writes raw position and rebuilds the bounding box in both (`Entity.java:388-395` versus `399-406`).
- Verdict: accepted, in-scope vertical integration difference. When a small vertical request is passed through unchanged, 1.21.2 reaches `setPos` even when the resolved vector's squared length is at or below the old threshold; 1.21.1 skips that position/bounding-box write. The same is true when the squared-length loss to collision is below the new tolerance. This is a position-commit gate difference; the source does not alter the requested or resolved displacement itself here. The preceding `0.003` deadzone is unchanged, so the case must be reachable after that gate or through a collision-adjusted result; collision-provider details are outside this audit.

### No accepted ordinary gravity, drag, ordering, or fall-state difference

For the scoped ordinary path, movement with the pre-gravity Y delta, then gravity subtraction, then `0.98F` vertical drag is unchanged. The abstraction of slow-fall-aware gravity is new, but without that excluded effect it returns ordinary gravity. The ground/downward fall-distance update and reset bodies are unchanged. No claim is made here for fluids, effects, jumping, gliding, climbing, or block callback behavior.

### Call-site boundary changes that do not change the assigned local-player case

1.21.2 gates `Entity.checkFallDamage` on server side or `isControlledByLocalInstance()` (`Entity.java:667-672`); 1.21.1 invokes it unconditionally (`Entity.java:645-648`). The assigned local player is the controlled local instance, so the call remains reachable. 1.21.2 also gates its changed fall-on callback on local control (`Entity.java:682-686`); the callback is a support/block boundary and was not audited. Neither changes the local-player common fall-state verdict above.

## Bounds

This audit covers ordinary local-player dry-air passive integration, the `0.003` pre-travel Y deadzone, the post-collision position write threshold, gravity/drag order, and common fall-distance accumulation/reset. It does not audit jump writers, effects, flying/gliding, fluids/swimming, block effects, damage calculation, collision/shape/support providers, or the behavior inside the callback boundaries. No implementation, runtime, build, test, mapping lookup, network, or source generation was performed.
