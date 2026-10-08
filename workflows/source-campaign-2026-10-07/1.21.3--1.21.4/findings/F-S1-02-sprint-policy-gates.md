# F-S1-02: 1.21.4 adds local sprint stop and slow-movement start gates

- Older version A: 1.21.3
- Newer version B: 1.21.4
- Mechanic / coverage slice IDs: local player sprint policy; S1-03
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.3, 1.21.4]
- Runtime validation: not performed

## Paired evidence

### A — 1.21.3

- Evidence artifact record ID: `A-1.21.3-MOJMAP`; publication status `original-verified`; revision `none`.
- Immutable evidence path: `build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/`; source manifest `build/movement-campaign-2026-10-07/ready/1.21.3/mojmap.sources.sha256`, SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`; mapped client JAR SHA-256 `9e5d42c42acaf0076ee92b41b13aa7440667967266ca67ecd50b08e5e4ba8024`; original artifact manifest `build/movement-campaign-2026-10-07/ready/1.21.3/artifacts.sha256`, SHA-256 `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`.
- Source/raw-input relation: verified by the paired `mojmap.ready.json` and `mojmap.provenance.json` records and successful exact-version batch log in `run.md`; the source-file hashes below also match the source manifest. No revised artifact is used.
- `net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep()V` lines 638-739, `isMovingSlowly()Z` lines 597-599, `canStartSprinting()Z` lines 1023-1031; SHA-256 `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`. A has no `shouldStopSprinting()` member; its `aiStep()` active-stop block and existing `canStartSprinting()` are the checked replacement path.
- `net/minecraft/world/entity/player/Player.java`, `Player#tryToStartFallFlying()Z` lines 1525-1533; SHA-256 `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de`.
- `net/minecraft/world/entity/LivingEntity.java`, `LivingEntity#hasEffect(Holder<MobEffect>)Z` lines 926-928; SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72881cfc52`.
- `net/minecraft/world/entity/Entity.java`, `Entity#isUnderWater()Z` lines 1281-1283 and `isVisuallyCrawling()Z` lines 2338-2340; SHA-256 `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`.

### B — 1.21.4

- Evidence artifact record ID: `B-1.21.4-MOJMAP`; publication status `original-verified`; revision `none`.
- Immutable evidence path: `build/movement-campaign-2026-10-07/ready/1.21.4/mojmap/`; source manifest `build/movement-campaign-2026-10-07/ready/1.21.4/mojmap.sources.sha256`, SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; mapped client JAR SHA-256 `56995548c9cb8bd7cdb9996b676daeafae02bde9d6ef4029b9eeca6bfd7dcc74`; original artifact manifest `build/movement-campaign-2026-10-07/ready/1.21.4/artifacts.sha256`, SHA-256 `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`.
- Source/raw-input relation: verified by the paired `mojmap.ready.json` and `mojmap.provenance.json` records and successful exact-version batch log in `run.md`; the source-file hashes below also match the source manifest. No revised artifact is used.
- `net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep()V` lines 643-753, `shouldStopSprinting()Z` lines 816-822, `isRidingCamel()Z` lines 824-826, `hasBlindness()Z` lines 828-830, `isMovingSlowly()Z` lines 602-604, `canStartSprinting()Z` lines 1053-1062; SHA-256 `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`.
- `net/minecraft/world/entity/player/Player.java`, `Player#tryToStartFallFlying()Z` lines 1528-1536; SHA-256 `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`.
- `net/minecraft/world/entity/LivingEntity.java`, `LivingEntity#hasEffect(Holder<MobEffect>)Z` lines 926-928; SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`.
- `net/minecraft/world/entity/Entity.java`, `Entity#isUnderWater()Z` lines 1302-1304 and `isVisuallyCrawling()Z` lines 2366-2368; SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`.

- `run.md` identifies the full exact source/artifact manifests and successful decompilation provenance. Java sources are exact-release decompiler outputs; no original-source archive equivalence is claimed.
## Source-level difference

B adds `shouldStopSprinting()` after crouching refresh and input sampling, before item-use scaling and the sprint-start checks. Its predicate is fall-flying, blindness, slow movement, a passenger who is not riding a camel, or item use while not a passenger and not underwater. A has no corresponding helper or early active-stop predicate. B also appends `!isMovingSlowly() || isUnderWater()` to the existing start predicate. The call order matters: B writes sprint=false before the ordinary start paths, and a later start path may write it true again in the same AI step when its own guards pass; slow movement permits that later start only underwater. A already uses blindness, item use, fall-flight and passenger eligibility to reject starts, so the source difference is the new earlier active-stop policy, the slow-start condition and the camel exception in that stop policy.

## Reachability and dependencies

The bounded path is `LocalPlayer.tick()` -> its inherited player AI tick -> `LocalPlayer.aiStep()` -> the stop predicate -> later sprint-start checks -> ordinary `LivingEntity` travel. On B, `LocalPlayer.tick()` must pass the separate client-load flag or timeout gate in F-S1-01 before this AI path executes. Within an executing AI step, crouching is refreshed from flight/swim/passenger/pose-fit and shift state before `isMovingSlowly()` is read. Fall-flight, blindness, item-use, passenger/vehicle and fluid values are current player/entity state; their relevant local readers and reachable entry/update paths are included in the closed S1-03 dependencies. Effect/fluid/vehicle or synchronized-state production is not emulated by this finding. The food-state producer is out of scope; the existing predicate read remains only a vanilla input.

## Consequence and uncertainty

Source proves the added B stop call and the additional start guard under the exact ordering above. For non-underwater slow movement, the later start predicate rejects sprinting; underwater slow movement can satisfy its exception, so the early false write alone does not prove the final sprint flag for that AI step. The finding does not measure trajectory or establish a runtime outcome. Vehicle physics and attack/damage resolution are outside scope; direct player sprint-state handling is in scope. The exact release introduction is unknown within the endpoint interval.

## Handoff

This finding is source-confirmed for the local sprint policy. Pair-level freeze and independent source audit remain open.
