# Independent audit: 1.12.2 → 1.13.0 ability-flight glide clear

**Verdict: ACCEPT the bounded no-difference claim for the direct local flag-7 clear and its timing; QUALIFY any broader claim that the movement path is behaviorally identical.** The flag clear is present, reachable, and ordered after one real glide movement response in both exact releases. The gliding movement calculations are not wholly identical across the pair.

## Audited claim and scope

I independently audited the pinned exact-release sources for a local, dry player with a valid ordinary chest-slot Elytra, active fall-flying flag 7, no vehicle/passenger, and legal flight permission (`abilities.canFly`). The bounded claim is whether `abilities.flying` can overlap with an active glide and, if so, whether the local flight branch performs real glide movement before directly clearing flag 7. This is not a full pair-coverage verdict or runtime proof.

## Findings

- **The overlap is source-reachable.** With `abilities.canFly`, the local player can toggle `abilities.flying` on a second eligible jump. The 1.12.2 path has the second-tap toggle at `LocalClientPlayerEntity.java:747–762`; 1.13.0 adds `!this.isSwimming()` to that toggle arm at `:756–771`. For the assigned dry witness the added condition passes. 1.13.0 `PlayerEntity.isSwimming()` also returns `!abilities.flying && !isSpectator() && super.isSwimming()` (`PlayerEntity.java:1814–1815`), so it is false once ability flight is active. With a live flag 7 and a valid chest Elytra, this allows the glide and ability-flight states to overlap.
- **The separate Elytra-start input is unchanged.** Both local classes require jumping, non-sprinting input, airborne state, negative vertical velocity, `!isFallFlying()`, and `!abilities.flying`, then check the chest item identity and `ElytraItem.canFly`: 1.12.2 `LocalClientPlayerEntity.java:764–769`; 1.13.0 `:773–778`. This start gate does not explain the already-live overlap and is not the swimming toggle gate.
- **The ordinary dry, nonpassenger direct-clear branch matches.** 1.12.2 `PlayerEntity.moveRelative` (`:1386–1401`) and 1.13.0 (`:1435–1458`) both enter `abilities.flying && !isRiding()`, save vertical velocity and `speedInAir`, set fly speed, call `super.moveRelative(...)`, restore vertical velocity times `0.6` and saved speed, write `fallDistance = 0.0F`, then call `setFlag(7, false)`. In 1.13.0 the new swimming movement prelude at `:1439–1445` precedes that branch; it does not change this dry witness, and the `isSwimming()` predicate is false while `abilities.flying` is true.
- **Real glide movement precedes the direct clear.** The superclass dispatch reaches `LivingEntity.moveRelative` under its locally-controlled gate. With live flag 7, its `isFallFlying()` branch performs its glide velocity updates and calls `this.move(MoverType.SELF, ...)` before returning (1.12.2 `LivingEntity.java:1425–1477`, especially `:1464`; 1.13.0 `:1459–1517`, especially `:1504`). Only after this `super.moveRelative` returns does the player override execute its `fallDistance` write and direct `setFlag(7, false)`. Therefore the direct clear does not prevent that invocation’s glide branch from observing flag 7 or performing its real move.
- **Separate server writers do not replace the local clear.** `LivingEntity.flyingTick` only commits its recomputed flag under `!world.isClient` in both releases (1.12.2 `:1854–1873`; 1.13.0 `:1919–1938`). The grounded clear inside the Elytra movement branch is also guarded by `!world.isClient` (1.12.2 `:1475–1477`; 1.13.0 `:1515–1517`). These do not block the client-side `PlayerEntity` clear.
- **Do not merge the fall-distance write into the flag-clear claim.** The player override writes `fallDistance = 0.0F` immediately before flag 7 in both versions. 1.13.0 additionally has a slow-falling prelude that may write fall distance before the movement gates (`LivingEntity.java:1461–1465`); that is a separate effect-specific write. On the overlap path, the player override’s later reset remains after superclass movement and before the direct flag clear.
- **Qualify movement equivalence.** The Elytra movement that occurs before the clear is not literally the same source procedure. In 1.12.2 `LivingEntity.moveRelative` uses the `-0.08 + m * 0.06` vertical term and an upward-pitch branch guarded by `i < 0`; in 1.13.0 the normal/slow-falling factor `d` feeds `d * (-1.0 + n * 0.75)`, and the upward-pitch branch also requires a nonzero horizontal look magnitude (`k > 0`). Thus the direct clear’s existence/order/timing is a no-difference result, but it must not imply the entire real glide movement response is behaviorally identical.

## Source identities

Windows native PowerShell; primary checkout `D:\Javastuff\LegacyParkourCompat`; source trees read-only. Exact pair is **1.12.2 → 1.13.0** (`decompiled_minecraft/1.13/ornithe-feather`, ready marker `versionId: 1.13`), not 1.13.2.

- **1.12.2 / Ornithe Feather:** source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; ready-marker SHA-256 `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`; Feather build.2 mapping jar SHA-256 `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`, Tiny SHA-256 `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`.
- **1.13.0 / Ornithe Feather:** source-manifest SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`; ready-marker SHA-256 `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99`; Feather build.2 mapping jar SHA-256 `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897`, Tiny SHA-256 `7fa6b27aab7245c2d47ecbfe86ad9178272d7b1fd9ba69a482a41659387ffd84`.
- Reliance-critical source SHA-256s match the source manifests:
  - `LocalClientPlayerEntity.java`: 1.12.2 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; 1.13.0 `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08`.
  - `PlayerEntity.java`: 1.12.2 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; 1.13.0 `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd`.
  - `LivingEntity.java`: 1.12.2 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; 1.13.0 `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864`.

## Reviewed report identity

The independently reviewed source report was read only after exact 1.12.2 and 1.13.0 source identities and relevant class hashes were frozen. Report SHA-256: `78995290d9003999bb1d9f6919c97f01dca158bf2e128219ff8f59aa0e282cc0`.

No product files, research backlog, wiki records, or source trees were changed. No build, test, network, decompilation, or runtime checks were performed.
