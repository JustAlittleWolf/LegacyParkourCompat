# C: Elytra glide versus climbable handling, 1.15.2 → 1.16

## Result

No player-movement difference for the assigned case: an ordinary, usable chest-slot Elytra on an on-foot local player, already fall-flying, on a dry ladder or vine. In both releases `LivingEntity.aiStep` calls `updateFallFlying()` before `travel(...)`. On the logical client, the updater validates the active flag, airborne/non-passenger state, and usable chest Elytra, but writes shared flag 7 only when `!level.isClientSide`; therefore this client path retains its preexisting true flag through the updater. `travel` then selects the dry fall-flying branch before the normal movement branch that performs climbable cleanup. The initial climbable location does not route this path through ladder/vine speed clamping or its later ladder jump response.

The glide branch's assigned ordering and boundary are the same: it captures current delta movement, tests the pre-move vertical component with strict `y > -0.5`, and writes `fallDistance = 1.0F` before calculating and moving the glide displacement. Exactly `y == -0.5` does not perform that write. The tested source bounds show the same glide equations, multiplier order, and move call in the two releases.

1.16 adds `!hasEffect(LEVITATION)` to `updateFallFlying`'s validation condition. For this ordinary-Elytra local-client case without levitation, this does not change the branch or flag outcome. More generally, the client still does not write the computed local `bl` back to shared flag 7 in the updater; the added gate therefore does not itself clear an already-true client flag here. This report does not claim server behavior parity or cover levitation-enabled glide trajectories.

## Exact source evidence

All paths below are relative to the canonical local `decompiled_minecraft/` root and are Mojmap. The ready markers identify exact versions `1.15.2` and `1.16` respectively; source-tree and per-file hashes were checked against their listed manifests.

- `1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `aiStep` lines 2283–2287: input scaling, updater, bounding-box capture, then `travel`.
- `1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel` lines 1840–1895: dry fall-flying branch and strict threshold/write at 1842–1846; glide operation sequence at 1848–1871; normal climbable cleanup and ladder response only in the following `else` at 1885–1895.
- `1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `updateFallFlying` lines 2299–2317: active-state/Elytra gate and server-only shared-flag write.
- `1.15.2/mojmap/net/minecraft/world/item/ElytraItem.java`, `isFlyEnabled` lines 19–20: durability gate.
- `1.16/mojmap/net/minecraft/world/entity/LivingEntity.java`, `aiStep` lines 2451–2457: same updater-before-travel ordering.
- `1.16/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel` lines 1921–2024: water/lava gates precede dry glide; strict threshold/write at 1977–1981; glide operation sequence at 1983–2006; normal climbable handling is after the glide `else` at 2020–2024, with climbable cleanup delegated to `handleRelativeFrictionAndCalculateMovement` at 2060–2065.
- `1.16/mojmap/net/minecraft/world/entity/LivingEntity.java`, `updateFallFlying` lines 2476–2494: added levitation condition and same server-only shared-flag write.
- `1.16/mojmap/net/minecraft/world/item/ElytraItem.java`, `isFlyEnabled` lines 17–18: unchanged durability gate.

## Evidence identity

- `decompiled_minecraft/1.15.2/mojmap.ready.json`: version `1.15.2`, mapping `mojmap`, status `ready`; source manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`.
- `decompiled_minecraft/1.16/mojmap.ready.json`: version `1.16`, mapping `mojmap`, status `ready`; source manifest SHA-256 `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`.
- Per-file SHA-256, also matching `mojmap.sources.sha256` entries:
  - 1.15.2 `LivingEntity.java`: `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`
  - 1.15.2 `ElytraItem.java`: `701e25f13e9ba8db74aca774f404a5282a58f4f7dc72e3aeabac76bbf28b43`
  - 1.16 `LivingEntity.java`: `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`
  - 1.16 `ElytraItem.java`: `d20a679b8822eaa57e5216ce14ed755c1f86a160eb5dad0dc9e3d8f4b5ea018d`

- Ready marker SHA-256: 1.15.2 `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`; 1.16 `5b6cb24f95e83b856c53f646d6477f2edae2276737dee56dd07c5bd745e4a2`.\r\n- Provenance SHA-256: 1.15.2 `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`; 1.16 `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9`. Both source-manifest files were hashed and matched the SHA-256 values in their ready markers; both source roots contain the marker-declared source-file count (3,332 and 3,572).\r\n\r\nBounds: source comparison only for updater state/ordering, usable Elytra validation, dry glide travel and its precedence over ladder/vine cleanup, strict pre-move vertical threshold and write. No runtime or trajectory test was performed. This is a bounded no-difference result, not full-pair coverage or acceptance.

