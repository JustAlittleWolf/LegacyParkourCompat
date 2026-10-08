# Independent blind source review: F-1 shallow water current

- Decision: **ACCEPT** (finding snapshot only)
- Review date: 2026-10-08
- Pair: 1.21.11 (A) → 26.1.2 (B)
- Pair status: remains `PARTIAL`; this review does not freeze or complete the pair.
- Scope: exact finding evidence, its player reachability and finding-specific dependencies. No implementation, implementation review, wiki or MCPK material was inspected.

## Immutable snapshot binding

- Finding: `F-1-water-current-shallow-overlap.md`
- Snapshot commit: `6374ff904018e102afd8353864d3cbcbb1b99ddd`
- Repository path: `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-1-water-current-shallow-overlap.md`
- Git blob: `90c8a48282c036e6082c85f0be7a69e041ac0cb7`
- Raw finding SHA-256: `9d2c273a1c3b358eac7ebaa1e4cbe9e91a126648a90e89d8d6bdeb1953159cdd`

## Verified source publications

The exact ready markers identify 1.21.11 Mojmap and native unobfuscated 26.1.2 sources. Their source-manifest and artifact-manifest bytes match the hashes declared by their ready markers:

| Version / namespace | Source manifest SHA-256 | Artifact manifest SHA-256 | Client jar SHA-256 |
|---|---|---|---|
| 1.21.11 / Mojmap | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` |
| 26.1.2 / unobfuscated | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` |

The mapped 1.21.11 client jar cited through its artifact manifest hashes to `7055b6a734a8f9f0d80229c2438ba12d10952a57c3795f32b8611020b34eb89a`. It is source provenance; resource contents were checked in the original client jars. In both exact original jars, `data/minecraft/tags/fluid/water.json` has SHA-256 `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004` and contains `minecraft:water` and `minecraft:flowing_water`.

Every source file hash cited by the finding was compared against its bytes and the corresponding exact-version source manifest. The uppercase `DC` characters in the finding's 26.1.2 `Vec3.java` digest are hexadecimal case only; normalized, it matches both bytes and manifest.

The decisive source ranges re-walked for this review are:

| Path rooted at the ready source directory | Ranges / behavior |
|---|---|
| `1.21.11/mojmap/net/minecraft/world/entity/Entity.java` | 1509–1521 caller; 3509–3573 deflated scan, float top addition, overlap scaling, Player exception and impulse; 3576–3582 loaded-chunk guard |
| `26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` | 499–525 entity base tick route; 1558–1581 tracker update/application; 3601–3607 unloaded-chunk query; 4015–4027 deflated fluid box and vehicle modification |
| `26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java` | 32–89 box scan and accumulated height/current; 91–118 full-chunk/section gate; 121–129 tag lookup; 132–150 tracker consumers; 153–188 reset, strict squared-vector gate, Player averaging and velocity addition |
| `1.21.11/mojmap/net/minecraft/world/entity/player/Player.java`; `26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java` | A 238–266 and B 231–259 Player tick to superclass; A 1678–1681 and B 1672–1675 fluid-push gate |
| `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`; `26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` | A 2610–2651 and B 2699–2739 superclass tick to `aiStep` and subsequent player movement |
| `1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java`; `26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java` | Both 55–100 current calculation; A 118–158 and B 118–157 spread path; A 160–195 and B 161–195 amount/drop-off chain; A 197–215 and B 198–220 wall gate; A 411–427 and B 443–459 scheduled fluid tick |
| `1.21.11/mojmap/net/minecraft/world/level/material/WaterFluid.java`; `26.1.2/unobfuscated/net/minecraft/world/level/material/WaterFluid.java` | Both 107–109 water drop-off; A 131–157 and B 131–157 flowing/source amount semantics |
| `1.21.11/mojmap/net/minecraft/world/level/material/FluidState.java`; `26.1.2/unobfuscated/net/minecraft/world/level/material/FluidState.java` | A 95–97 and B 95–97 flow delegation |
| `1.21.11/mojmap/net/minecraft/world/phys/Vec3.java`; `26.1.2/unobfuscated/net/minecraft/world/phys/Vec3.java` | A 80–83 and B 83–86 normalization floor; squared-length methods cited in the finding |
| `1.21.11/mojmap/net/minecraft/core/Direction.java`; `26.1.2/unobfuscated/net/minecraft/core/Direction.java` | Horizontal direction-plane declaration, A 568 and B 574 |
| `1.21.11/mojmap/net/minecraft/world/entity/EntityType.java`; `26.1.2/unobfuscated/net/minecraft/world/entity/EntityType.java` | Player box dimensions, A 1191–1198 and B 1189–1196 |

## Source and reachability review

1. **Actual water current:** `WaterFluid` reports drop-off 1; a flowing state with amount 1 has own height `1 / 9.0F`, and amount 2 has `2 / 9.0F`. `getNewLiquid` computes the highest horizontal neighbor amount minus that drop-off and creates a non-falling flowing state when positive. Thus a source at x=107 can sustain a supported horizontal chain through amounts 7…1 at x=106…100. A floor blocks downward spread; full side walls block north/south spread. The west empty neighbor at x=99 has no water below and therefore contributes no fallback current.
2. **Flow direction and normalization:** for the x=100 amount-1 cell, the east amount-2 neighbor contributes `1/9F - 2/9F = -1/9F` on X; north, south and west contribute zero. Both `FlowingFluid.getFlow` implementations iterate the same horizontal direction plane and normalize this real flow vector to `(-1, 0, 0)`. `FluidState.getFlow` delegates to that implementation in each release. This is a source-derived fluid state, not an arbitrary vector.
3. **One occupied scan cell:** standing Player dimensions are `0.6F × 1.8F` in both releases. At x/z `(100.5,100.5)`, the deflated interaction box spans only block column `(100,100)` horizontally. Its vertical bounds cover y=64 and y=65; the only water is at y=64, with no same-fluid cell above. A's strict loop bounds and B's inclusive `ceil(max)-1` bounds therefore each count exactly one matching water state. The source at x=107 and the amount-2 neighbor at x=101 do not enter the entity's scan; the latter supplies the current used for the scanned cell.
4. **Loaded and tag gates:** A's `touchingUnloadedChunk()` checks the one-block-inflated player box; with these coordinates its chunk range remains within chunk `(6,6)`. B expands the fluid scan by one block before calling `hasFluidAndLoaded`; x/z 99…101 also remain within that same fully loaded chunk and the y range stays in one section. A full loaded chunk with the described water satisfies both guards. The exact water tag is present in both client jars as verified above.
5. **Player gates and tick route:** `Player.tick()` calls `super.tick()` in both releases, through `LivingEntity.tick()` to `Entity.tick()/baseTick()`. The fluid update occurs there before the later living/player movement step. A and B `Player.isPushedByFluid()` both return `!abilities.flying`; a non-flying player enables current collection and application. Choosing an unmounted player makes B's vehicle-specific fluid-box modification inapplicable and satisfies the requested passenger condition; no passenger physics is used.
6. **A arithmetic and write:** A deflates the bounding box by `0.001`, computes `fluidTop` as `int blockY + float height` before storing it in a `double`, and scales flow by overlap height when below `0.4`. For the finding's shallow position, the float addition rounds the top about `3.39e-6` higher than B's double addition. The resulting A overlap is about `0.001`; after the `0.014` water scale its vector length is about `1.4e-5`, above `Vec3.normalize()`'s strict `1.0E-5F` zero cutoff. With zero X/Z delta, A reaches the strict `< 0.0045000000000000005` minimum-impulse branch, normalizes the nonzero vector and adds the 0.0045 impulse to player delta movement.
7. **B tracker and earlier cutoff:** B's `getFluidInteractionBox()` also deflates by `0.001`, but tracker height is measured from the un-deflated entity `minY`. The fluid top remains at or above the interaction-box minimum, so the cell is included; tracker height is about `0.002` (about `0.0019966` if the position is fixed from A's float-rounded top). Its accumulated current has squared length about `4.0e-6`, below the strict `1.0E-5F` gate in `Tracker.applyCurrentTo()`. B returns before Player averaging, water scaling, the minimum-impulse branch or `addDeltaMovement`.
8. **Direct movement consequence:** the differing branch writes player delta movement directly. The same tick then proceeds into normal player/living movement, so this is a reachable movement input. No health, food, attack, damage, non-player or vehicle behavior is needed for the witness.

## Decision basis and limits

The finding's source chain, count, box and loading conditions, normalization threshold, operation order and player gates are supported by both exact source trees and the verified resource tag. The small difference between A's float-added fluid top and B's double-added top does not invalidate the witness: both preserve the positive-overlap inclusion condition, A remains above its normalization floor, and B remains below its squared-vector cutoff.

The witness proves a conditional source-level velocity difference for the stated reachable fluid/player configuration. It does not prove a full pair audit, first-introduced version, runtime trajectory, every fluid type, mounted-player reachability or closure of the wider S4.6/S2.3/S3.6 inventories. Those remain outside this finding-specific acceptance. No runtime validation was performed.
