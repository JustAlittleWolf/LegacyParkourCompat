# Independent source audit verdict

## Claim identity

Audited frozen report: `c-glide-climb-1165-117-alias-gate/report.md`  
Expected and observed SHA-256: `2e9b9df4afc8292c20ad39a485c595f0777a803d2917617e63e19d62e655b684`.

## Verdict

**Accepted for the bounded no-difference claim.** In exact Mojmap `LivingEntity.travel` source for 1.16.5 and 1.17, the scoped dry route with fall-flying already active reaches the `isFallFlying()` branch before any move in the ordinary friction/climbable branch. The two fluid branches precede it, so the premise requires them not to match. The climbable route is reached only by the final `else`; it calls `handleRelativeFrictionAndCalculateMovement`, which applies `handleOnClimbable` and then its climbable collision/jump gate. It is bypassed in the scoped active-glide route.

The observed glide gate and write are the same: `if (vec36.y > -0.5) this.fallDistance = 1.0F;`. When the condition is false, this branch does not write `fallDistance` there. The slow-falling preamble precedes fluid/glide routing and can reset `fallDistance` to `0.0F` for nonpositive initial Y and the effect; the stated strict glide gate can overwrite that reset. `isFallFlying()` reads shared flag 7 on both versions. The branch's clear of flag 7 is guarded by `onGround && !level.isClientSide`; it cannot clear the flag in the client route audited here.

The source-level aliases do not invalidate this gate result: 1.16.5 reads `xRot`, while 1.17 calls `getXRot()`; both feed the same angle conversion and subsequent glide computation. The audited no-difference is limited to routing, the threshold/write, and the adjacent climbable path. It does not claim identical glide math throughout the branch: the horizontal-distance expression differs in spelling (`sqrt(getHorizontalDistanceSqr(vec36))` versus `vec36.horizontalDistance()`) and was not needed to decide this gate.

The report's bounded conclusion is supported. The added 1.17 powder-snow condition is in the ordinary friction/climbable collision/jump gate and does not change the active-glide route. This verdict does not establish full-pair coverage or runtime parity.

## Exact evidence and identity checks

- `decompiled_minecraft/1.16.5/mojmap.provenance.json` identifies `versionId: 1.16.5`, namespace `mojmap`, and exact-run mapping option `mojmap`; `mojmap.ready.json` identifies version `1.16.5` and mapping `mojmap`. Its source-manifest digest matches `mojmap.sources.sha256`; that manifest's `LivingEntity.java` entry matches the file hash below.
- `decompiled_minecraft/1.17/mojmap/.decompile-provenance.json` records input metadata `.../packages/0d9ace8a2ecfd1f4c782786f4b985a499240ff12/1.17.json` with hash `537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`, Mojang client hash `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`, and client mapping text hash `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`.
- 1.16.5 `LivingEntity.java` SHA-256: `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`; 1.17 SHA-256: `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed`. Both match the report's cited source identities.

## Source locations reviewed

- 1.16.5 `decompiled_minecraft/1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`: `travel` control gate and fluid ordering 1914–1979; glide threshold/write and branch 1979–2021; ordinary friction route 2022–2042; climbable handler and collision/jump gate 2062–2104; `onClimbable` feet-state/tag test 1363–1380; `isFallFlying` shared-flag read 2891–2893.
- 1.17 `decompiled_minecraft/1.17/mojmap/net/minecraft/world/entity/LivingEntity.java`: `travel` control gate and fluid ordering 1999–2064; glide threshold/write and branch 2064–2106; ordinary friction route 2107–2131; climbable handler and collision/jump gate 2151–2194; `onClimbable` feet-state/tag test 1423–1438; `isFallFlying` shared-flag read 3009–3011.

Excluded from this audit: Elytra activation, final tick/updateFallFlying behavior, first-move/contact outcomes, fluids as movement routes, block geometry, damage and other movement behavior.
