# Source comparison: ordinary vertical air integration, 1.21.2 to 1.21.3

## Verdict

No source difference found in the assigned ordinary local-player vertical air path. Every compared Java file below has the same SHA-256 on both sides, and the relevant methods and their enclosing tick/travel gates are intact. No canonical finding is warranted.

## Authenticated source identity

- 1.21.2: exact tree `decompiled_minecraft/1.21.2/mojmap`; `.decompile-provenance.json` SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`. Its input records exact client artifact `c7ac2d0d86f4ca416cab9064ff8a281852ad0c7b/client.jar` (SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`) and Mojang `client.txt` mappings SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`.
- 1.21.3: release-level Mojmap publication identifies `versionId=1.21.3`, `namespace=mojmap`; `mojmap.ready.json` SHA-256 `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02`, `mojmap.provenance.json` SHA-256 `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`, and `mojmap.sources.sha256` SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`. The provenance exact run selects `mappingOption=mojmap` and includes exact batch version ID `1.21.3`.
- Mapping family compared: Mojmap (official Mojang mappings). Release and mapping identities were read from the local source publication manifests; no online mapping lookup used.

## Paired Java evidence

Paths are relative to each release's `mojmap/` tree. All listed byte hashes were independently computed from the actual Java files; within each pair the hashes are identical.

| Source | Member/range (1.21.2 and identical 1.21.3) | SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `aiStep()`, lines 638-795 (dispatches to `super.aiStep()` at 795) | `FBD40F1F47ADFA66DDA9B15188E5DCE82AF3E8E8D7C3DD0543E602A354AD3FE0` |
| `net/minecraft/world/entity/player/Player.java` | `aiStep()`, lines 543-555 (super call); `travel(Vec3)`, lines 1445-1467 | `A803203E92AA4729D5F5C9B16085B6A43CE51D9907D309EB96736E9C7C1340DE` |
| `net/minecraft/world/entity/LivingEntity.java` | `getDefaultGravity()`, line 2169; `getEffectiveGravity()`, lines 2173-2176; `travel(Vec3)` / `travelInAir(Vec3)`, lines 2178-2214; `handleRelativeFrictionAndCalculateMovement(Vec3,float)`, lines 2351-2362; `aiStep()`, lines 2680-2713 and ordinary travel dispatch/fall reset, lines 2754-2775; player `checkFallDamage(...)`, lines 341-369 | `087390495F4FDFD14B9E12230E7AEA4FDC50913BB4D1D91119E72892881CFC52` |
| `net/minecraft/world/entity/Entity.java` | `move(MoverType,Vec3)`, lines 619-701; `getGravity()`/`applyGravity()`, lines 1197-1205; `checkFallDamage(...)`, lines 1216-1231; `checkSlowFallDistance()`/`resetFallDistance()`, lines 2484-2492 | `A93719C302A0381A972AF75EA360465E2E3551708DD07C34D4D40B7E5173C2B9` |

The matching whole-file hashes establish byte identity for each compared method and its surrounding call site, including the intact bodies used for the path below.

## Reachable path, gates and behavior

1. A local player reaches `LocalPlayer.aiStep()` and then `Player.aiStep()`/`LivingEntity.aiStep()`. Player's ordinary non-flying, non-swimming, non-passenger travel dispatch reaches `LivingEntity.travel()`; it must be controlled by the local instance, and the dry non-gliding path enters `travelInAir()` (LivingEntity 2178-2189; Player 1445-1467). This comparison assumes no fluids, climbables, powder snow, Slow Falling or Levitation, as assigned. No jump cause or horizontal-input behavior is assessed.
2. In `LivingEntity.aiStep()`, a vertical velocity with `abs(y) < 0.003` is set to exactly `0.0` before the travel profiler section (2680-2713, 2754-2775). `travelInAir()` obtains the relative-friction movement result, then subtracts `getEffectiveGravity()` from Y, and multiplies the result by `0.98F` unless `shouldDiscardFriction()` is true (2173-2214). Ordinary living gravity is `0.08` (`getDefaultGravity()` at 2169); `getGravity()` returns zero only for no-gravity entities, excluded for the ordinary player assumption (Entity 1197-1205). With no Slow Falling effect, effective gravity is that same `0.08` (LivingEntity 2173-2176).
3. Concrete arithmetic witness: entering this section with `vy=-0.001` causes the pre-travel threshold to write `vy=0.0`. With no vertical input/effect and ordinary friction, the air integration writes `(0.0 - 0.08) * 0.98F`, approximately `-0.0784000015258789` (the `0.98F` operand promotes its exact float value to double before multiplication). `move(SELF, deltaMovement)` requests that Y displacement. Absent collision, Y position changes by approximately `-0.0784000015258789`; absent collision side effects, post-move vertical velocity remains that value. If collision clips Y, `Entity.move` sets position by the clipped vector, compares requested and actual Y, and `LivingEntity`/`Entity.checkFallDamage` accumulates only actual negative moved Y as `fallDistance -= (float)movedY`; on ground it resets fall distance. Collision arithmetic/providers are out of scope, but the boundary contract and state writes are shown (Entity 619-701, 1216-1231; LivingEntity 341-369).
4. The interval between the deadzone and integration is unchanged. The `0.98F` vertical drag/order, gravity subtraction, `SELF` movement request, clipped-position write, and fall-distance threshold/reset operations are byte-identical across the authenticated pair. No numeric divergence exists within this bounded path.

## Coverage and boundaries

Examined paired complete bodies and surrounding dispatch for LocalPlayer, Player, LivingEntity and Entity as listed above. This closes the assigned path from local-player AI step through Y request, position/velocity update and common fall-state write/reset. No differences found.

Excluded by assignment: jump causation; statuses/equipment and their creation; rotations; horizontal velocity and ground acceleration; Elytra, swimming and all fluid movement; block/collision/support/provider implementation; rendering, networking and visual state; non-player/vehicle paths; other fall damage, health and damage calculations. Boundary methods were followed only far enough to establish the Y request, position, velocity and fall-state consumers.


