# Entity.move position/box reconstruction closure

## Verdict and bounded claim

The 1.16.5-to-1.17 change is a real source-level LocalPlayer position and (for the witness below) bounding-box change under identical resolved movement input. It is introduced in 1.17 and the relevant `Entity.move` writer remains the same in 1.17.1. This closes the arithmetic/reachability question for this operation; it does not establish that every preceding collision query produces the same resolved vector, or establish runtime trajectory parity.

## Exact route and applicable gates

The ordinary local-player route is `LocalPlayer.aiStep()` → inherited `LivingEntity.aiStep()` → virtual `Player.travel()` → inherited `LivingEntity.travel()` → `handleRelativeFrictionAndCalculateMovement()` → `move(MoverType.SELF, getDeltaMovement())` → `LocalPlayer.move()` → `Entity.move()`. The source anchors are `LocalPlayer.aiStep()`'s `super.aiStep()` (1.16.5:788; 1.17:812; 1.17.1:810), the inherited `LivingEntity.aiStep()` virtual `travel(...)` call (2459; 2542; 2546), and the ordinary ground helper's SELF move (2065; 2154; 2158). `Player.travel()` delegates to `LivingEntity.travel()` outside its swim and flight adjustments; the ground/friction branch calls the movement helper directly. `LocalPlayer.move()` wraps `super.move()` and then runs auto-jump using the before/after X/Z difference (863; 895; 893). The bounded tick-graph inventory gives the paired route and ordinary gates; its erratum corrects only the unrelated Jump Boost release boundary.

Applicable `Entity.move` branches and gates:

- With `noPhysics == true`, both versions move without collision resolution. 1.16.5 translates the current box and reconstructs position from its bounds; 1.17/1.17.1 calls `setPos(position + requested displacement)`. The write is unconditional in this branch, including a zero vector.
- With `noPhysics == false`, `MoverType.PISTON` first passes through `limitPistonMovement`; a resulting zero vector returns early. Ordinary LocalPlayer travel uses `MoverType.SELF`, so this piston-only gate does not apply to the demonstrated route.
- The physics branch next applies a nonzero stuck-speed multiplier when its squared length exceeds `1.0E-7`, then calls `maybeBackOffFromEdge` and `collide`. The position/box write occurs only if the resolved vector's squared length exceeds `1.0E-7`. For a full-support flat route (or a non-edge airborne route) with no collision, the resolved vector is the same vector in both versions. The witness uses resolved `(0.04, 0, 0)`, whose squared length is `0.0016000000000000001`, so the write gate passes.
- Collision flags, on-ground calculation, fall check, blocked-axis velocity clears and callbacks follow the write in both versions. Those downstream gates do not reverse the already-written position or box difference.

## Paired source math

1.16.5 Mojmap `net/minecraft/world/entity/Entity.java:485–509` moves the current `AABB` by the requested/resolved vector and calls `setLocationFromBoundingbox()`. `AABB.move(double,double,double)` adds the displacement separately to each min/max coordinate (`world/phys/AABB.java:185–187`). `Entity.setLocationFromBoundingbox()` sets raw position to `((minX + maxX) / 2.0, minY, (minZ + maxZ) / 2.0)` (`Entity.java:801–804`); `setPosRaw` updates position/block-position/callbacks but does not rebuild the box. The same box-translate/reconstruct sequence is used in the no-physics branch.

1.17 Mojmap `Entity.java:534–557` and 1.17.1 `Entity.java:534–557` instead call `setPos(getX()+dx, getY()+dy, getZ()+dz)` in the no-physics branch and, after the same `lengthSqr() > 1.0E-7` write gate, in the resolved-collision branch. `setPos` calls `setPosRaw` and then rebuilds the box using `makeBoundingBox()` (`Entity.java:360–370`), which delegates to `EntityDimensions.makeBoundingBox(position)`. In all three inspected releases, `EntityDimensions.makeBoundingBox` uses `float width / 2.0F` and bounds `x ± halfWidth`, `y` to `y + height`, `z ± halfWidth` (`EntityDimensions.java:17–25`). Standing player dimensions are `EntityDimensions.scalable(0.6F, 1.8F)` in all three `Player.java` files (1.16.5:112; 1.17 and 1.17.1:124). The writer change therefore alters floating-point operation order for horizontal position reconstruction, not the intended player anchor convention.

The `Entity.move` position writer and threshold are unchanged between 1.17 and 1.17.1. The 1.17.1 Y clamp is in entity NBT loading, not this player movement route.

## Concrete binary64 witness

Use the same valid standing-player state and same resolved input on both sides: start at `x = 0.1`, with width `0.6F`, and resolve `dx = 0.04` in an unobstructed SELF move. The binary32 half-width is `0.30000001192092896`; all following arithmetic is binary64, in the source operation order.

| Value | 1.16.5 translated-box path | 1.17 / 1.17.1 position path |
| --- | ---: | ---: |
| Initial `minX` | `-0.20000001192092895` | `-0.20000001192092895` |
| Initial `maxX` | `0.40000001192092893` | `0.40000001192092893` |
| Final `minX` | `-0.16000001192092894` | `-0.16000001192092894` |
| Final `maxX` | `0.44000001192092891` | `0.44000001192092897` |
| Written player `x` | `0.13999999999999999` | `0.14000000000000001` |

The old final X is the midpoint of its translated box endpoints; the new X is `0.1 + 0.04`. The exact unequal player X and maxX are a source-level state consequence under equal raw/resolved input. Both final boxes remain valid and contain the same intended width. This is an arithmetic witness for a reachable LocalPlayer state and collision-resolved displacement, not an executed trajectory. No claim is made that the later float conversion in `LocalPlayer.move()`'s auto-jump argument differs for this particular pair, or that another tick necessarily diverges.

## Coverage boundary and remaining gates

Closed here: standing LocalPlayer width and anchor convention; route into the inherited writer; old/new position and box operation order; no-physics writer; the SELF collision-resolved write threshold; and an explicit finite player-state witness passing that threshold. The same writer applies to other player poses, with that pose's current `EntityDimensions` width.

Not closed by this slice: complete equivalence of the `collide` result, all block/provider/neighbor shape dependencies, every external direct player response or piston producer, exact upstream production of this particular input vector, downstream trajectory impact, and independent full-pair movement audit. Collision39's reconciliation bounds its separate shape-engine/provider evidence and does not substitute for those closures. Candidate applicability still requires a LocalPlayer call reaching `Entity.move`; in the physics branch its resolved displacement must survive edge/collision processing with squared length greater than `1.0E-7`. The no-physics branch has no such length gate. No-physics/spectator, piston and collision providers are separate branch conditions; the arithmetic witness specifically uses the ordinary `SELF`, `noPhysics == false` route.

## Source identity and verification

Sources were read-only Mojmap trees at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.16.5,1.17,1.17.1}\mojmap`. I rehashed each cited source file and checked it against its version's source manifest: 1.16.5 and 1.17.1 endpoint manifests, and the embedded 1.17 provenance `sources` map. All six files below matched their exact declarations. The 1.16.5/1.17.1 provenance and ready marker hashes also match the parent sidecars. The 1.17.1 source-manifest SHA-256 below is the value mechanically read from disk; the prior tick inventory's transcribed value has a typo and is not repeated here.

| Version | `Entity.java` | `LocalPlayer.java` | `LivingEntity.java` | `Player.java` | `EntityDimensions.java` | `AABB.java` |
| --- | --- | --- | --- | --- | --- | --- |
| 1.16.5 | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `6a4ded79d936bb740232ae923372e47d24a297a35eaba22e9d18fbd637a7eedf` | `514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878` |
| 1.17 | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` | `08dbb8615cc6c7776f35ce269223699d39f758d15786babfe11e497421c5f4d4` | `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed` | `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516` | `6a4ded79d936bb740232ae923372e47d24a297a35eaba22e9d18fbd637a7eedf` | `dd143ccd01d0d05e50619910bceb98a0cec0ca526e161e9812ae1e0db6eb7b7e` |
| 1.17.1 | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` | `6a4ded79d936bb740232ae923372e47d24a297a35eaba22e9d18fbd637a7eedf` | `dd143ccd01d0d05e50619910bceb98a0cec0ca526e161e9812ae1e0db6eb7b7e` |

| Publication sidecar | SHA-256 |
| --- | --- |
| 1.16.5 `mojmap.provenance.json` | `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d` |
| 1.16.5 `mojmap.ready.json` | `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee` |
| 1.16.5 `mojmap.sources.sha256` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |
| 1.17 embedded `.decompile-provenance.json` | `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357` |
| 1.17.1 `mojmap.provenance.json` | `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333` |
| 1.17.1 `mojmap.ready.json` | `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` |
| 1.17.1 `mojmap.sources.sha256` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` |

Source-record identities checked: `.native-source/20261031/1165-1171-finish45/tick-graph/inventory.md` SHA-256 `004508c02730a54c4a3e7bc55f4e430799daa4fe854c00713601edb13311226b`; its erratum SHA-256 `c33d8915c7fbbe768c86c6a61fa347c8bc2ee600f1aab825a65440ffd9280612`; and `.native-source/20261030/1165-1171-collision39/reconciliation.md` SHA-256 `e26958574b69110bbcbf1faf7e7ffbc3f4c68d51dd2dae0dc0545db1ccdcd3e7`.

No product source, history, research/backlog or wiki was read. No source generation, build, test, runtime launch or game interaction was performed.
