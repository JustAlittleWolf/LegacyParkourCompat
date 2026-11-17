# Creative-flight fall-distance boundary and edge-backoff witness

## Bounded result

For this operation, the exact adjacent cutover is **1.21.1→1.21.2**. In 1.21.1, `Player.travel` clears fall distance after inherited travel has performed local movement. In 1.21.2, non-passenger creative flight clears it at the start of `Player.aiStep`, before inherited `LivingEntity.aiStep` reaches travel; `Player.travel` no longer clears it after travel. The retained post-flight value is read by the unchanged `Player.maybeBackOffFromEdge`/`isAboveGround` path and can change whether a later local-player SELF move is backed away from a ledge.

This is a source-derived, bounded creative-flight transition witness, not a runtime result or a full-pair claim. It does not generalize to movement with no preceding creative-flight tick.

## Source identity

- **1.21.1 Mojmap:** canonical `decompiled_minecraft/1.21.1/mojmap/`; ready marker `decompiled_minecraft/1.21.1/mojmap.ready.json` SHA-256 `988B38AC59491F9430D5BD1968DB0FDAACB777A63FEF745CA3E20342237A0310`, binding `versionId=1.21.1`, `mapping=mojmap`, and source manifest `mojmap.sources.sha256` SHA-256 `900F956E00F6FC1300BB3D689EA49DF2B1A57BCAA54617344EF456D95D47CB48`. Root provenance SHA-256 `6F84A596EA944A1D40C79B53EAD9D2CDD2B3C726D710A977758D15B5578CE7A9`.
- **1.21.2 Mojmap:** canonical `decompiled_minecraft/1.21.2/mojmap/`; mapping-local hidden provenance `.decompile-provenance.json` SHA-256 `00F399407DC447737EA83E3E46562418F8C712F66E3F51CB85D46AFA2ECAC575`. Its `sources` object binds the Java paths below directly. Its pinned client inputs are `client.jar` object `c7ac2d0d86f4ca416cab9064ff8a281852ad0c7b` / SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c` and `client.txt` object `ce0ed2eb99175df9ec5166324e3fdd1f87cee827` / SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`.

Every listed Java file hash was checked against the endpoint's ready source manifest or hidden provenance `sources` entry and matched its local bytes.

| Canonical relative Java path | 1.21.1 SHA-256 | 1.21.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `2a0994e8a6a2fdf9e76480227216db4a4252734fca8ca0aedd25dde77be75ba6` |
| `net/minecraft/world/entity/player/Abilities.java` | `a4f952ada7bc3b21406feaf13c171bfa22d36b01e265e3b987612f28b5609edc` | `a4f952ada7bc3b21406feaf13c171bfa22d36b01e265e3b987612f28b5609edc` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c` | `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea` |

## Boundary and player call path

- **1.21.1:** `Player.aiStep` reaches `super.aiStep()` at `Player.java:530–533` without a flight fall-distance reset. In `Player.travel`, the non-passenger flight branch saves vertical velocity, calls `super.travel`, applies the `* 0.6` vertical velocity write, then calls `resetFallDistance()` and clears shared flag 7 (`Player.java:1468–1475`). Thus its reset follows that tick's inherited local movement.
- **1.21.2:** `Player.aiStep` checks `abilities.flying && !isPassenger()`, calls `resetFallDistance()`, then calls `super.aiStep()` (`Player.java:551–555`). The flight branch in `Player.travel` now only calls `super.travel` and applies the `* 0.6` vertical write; it has no post-travel reset (`Player.java:1445–1466`). The adjacent source pair therefore establishes the first observed release for this relocation.
- On the local client, `LocalPlayer.aiStep` handles shift plus creative flight by adding `-1 * abilities.getFlyingSpeed() * 3.0F` to vertical velocity (`LocalPlayer.java:752–764` in 1.21.2); `Abilities.flyingSpeed` defaults to `0.05F` (`Abilities.java:11`), giving a reachable `-0.15` velocity addition. `LocalPlayer.aiStep` then calls `super.aiStep()` (`LocalPlayer.java:795`), reaching the new pre-super reset before movement. The 1.21.1 equivalent uses `input.shiftKeyDown`, the same multiplier, and then calls `super.aiStep()` (`LocalPlayer.java:761–773, 804`).
- `LivingEntity.aiStep` dispatches through `this.travel(...)` (`LivingEntity.java:2762–2772` in 1.21.2). `LivingEntity.travel` runs for a locally controlled entity and reaches `travelInAir` (`LivingEntity.java:2178–2187`); its relative-friction movement reaches `move(MoverType.SELF, ...)` (`LivingEntity.java:2351–2354`). `LocalPlayer.isEffectiveAi()` returns true (`LocalPlayer.java:478–480`), so `Entity.move` also runs local-client fall-distance accounting (`Entity.java:669–672`).
- In 1.21.2, `Entity.move` invokes `maybeBackOffFromEdge` before collision clipping (`Entity.java:639–643`) and calls `checkFallDamage` after the moved position is applied (`Entity.java:669–672`). `Entity.checkFallDamage` adds `-dy` to `fallDistance` on a negative displacement while not on ground (`Entity.java:1216–1231`). With no slow-falling/levitation effect and no collision, the example flight tick therefore leaves `fallDistance=0.15` after `Player.aiStep` returns. The old 1.21.1 `Player.travel` clears that increment after the inherited movement completes.

## Source-derived edge-backoff witness

The `Player.maybeBackOffFromEdge`, `isAboveGround`, and `canFallAtLeast` method bodies are byte-for-byte identical between 1.21.1 and 1.21.2 (snippet SHA-256 values respectively `C1D605B27EE848EC7F4A9C4DD96F12DAB321199FB0C7597D1E79DE26825EDF2A`, `080A8D1C7542D5AD4E57C3B9D62695F33841B4A9D69C988D32EEAA3E4F6F1520`, `3F0BF60157F86041DD963C717E7B3BAD9F3770945E5D3917D6D25AEF6D070B28`). In both versions:

- `Player.isStayingOnGroundSurface()` is `isShiftKeyDown()` (`Player.java:338–340` in 1.21.1; `345–347` in 1.21.2).
- The SELF/PLAYER backoff gate requires not flying, requested `dy <= 0`, shift held, and `isAboveGround(maxUpStep)` (`Player.java:1067–1076` in 1.21.1; `1066–1075` in 1.21.2).
- `isAboveGround(s)` is `onGround || (fallDistance < s && !canFallAtLeast(0, 0, s - fallDistance))`; `canFallAtLeast` performs a collision query over the downward AABB (`Player.java:1118–1127` in 1.21.1; `1117–1124` in 1.21.2).
- A non-passenger's default step height is `0.6` (`Attributes.STEP_HEIGHT`; `LivingEntity.maxUpStep()` returns the attribute unless a player is controlling a vehicle: `LivingEntity.java:3500–3503` in 1.21.1 and `3588–3591` in 1.21.2; `Attributes.java:91–94` / `81–84`).

A concrete source-geometry construction is: use one full support block whose edge is at `z=0`, an airborne player with bounding-box `minZ=-0.001` (so only 0.001 overlaps the block), and put the player's feet `0.525` above its top. Require `isFallFlying() == false` (the fall-flying shared flag is clear, with no active glide state selecting `travelFallFlying`) before both this flight tick and the following edge-backoff tick; otherwise `LivingEntity.aiStep` can run `updateFallFlying` and `LivingEntity.travel` can select a different branch. This is off-ground. On the 1.21.1 profile the previous flight tick leaves `fallDistance=0`, so `canFallAtLeast(0,0,0.6)` intersects the block and `isAboveGround(0.6)` is true. On 1.21.2 the preceding downward flight tick leaves `fallDistance=0.15`; `isAboveGround` queries only `0.6-0.15=0.45` downward, which does not reach the block, so it is false. The `1.0E-5F` query extension does not close the 0.075 gap.

Now disable flight, keep shift held, and press forward toward the edge with yaw `0` (so forward movement is along `+Z`). With no sprint, no initial horizontal velocity, and default `SNEAKING_SPEED=0.3` (`Attributes.java:75–77` in 1.21.2), `KeyboardInput.tick` sets forward input to `0.3F` (`KeyboardInput.java:22–37`). Before travel, inherited `LivingEntity.aiStep` multiplies `zza` by `0.98F` (`LivingEntity.java:2755–2772`), producing the binary32 value `0.2940000295639038F`. In the airborne non-flying branch `Player.getFlyingSpeed()` is `0.02F` (`Player.java:2085–2090`), and `Entity.getInputVector` scales this sub-unit input without normalization (`Entity.java:1403–1417`); with yaw `0`, this yields a `+Z` request of about `0.005880000459849821` blocks. The 1.21.1 backoff loop sees that the projected box no longer overlaps the block; since `0.005880000459849821 <= 0.05`, it zeroes that horizontal component. The 1.21.2 `isAboveGround` gate is false, so it passes the same request to `Entity.collide`. At a `0.525` vertical gap there is no horizontal block collision, yielding about `0.005880000459849821` blocks of local-player displacement along `+Z` difference for this constructed state.

This isolates the direct player movement consequence from fall damage or damage calculation. It assumes ordinary full-block collision, default step/sneaking attributes, a nonpassenger local player, `isFallFlying() == false` before both ticks, no collision/effect reset, the stated edge placement, and a flight-off transition before the next tick. It is static source reasoning only.

## Remaining scope

This closes only the reset-order boundary and the demonstrated post-flight edge-backoff dependency for the named adjacent release cutover. Other tick operations, resources, movement branches, and all remaining 1.20.6→1.21.11 release pairs remain outside this slice. No product, implementation, build, test, or runtime state was inspected or changed.
