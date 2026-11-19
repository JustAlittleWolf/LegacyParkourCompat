# Bytecode author payload: vehicle collision exclusion

## Bound claim

Existing finding: `1.8.9--1.9.4/vehicle-collision-exclusion.md`.

Immutable claim identity supplied by the coordinator: launch `583f8a9b`, Git blob `8c0fa8a4c3bf34a9641fc501b1428736b123c5c4`, LF SHA256 `51d28a19c4efc7943c7168e0609f059e3c500d57ca6bbd7c821bc44ec640b86b`. The working file was hashed before reading and matched that SHA.

## Exact regenerated 1.8.9 evidence identity

- Mapping family: Ornithe Feather.
- Fresh isolated Feather Gen2 build.2 mapped jar: `client-ornithe-feather.jar`, SHA256 `e7f172281a7a5bc0b17b667dfd3883c3db0e2673c4f743dc5edfbf1e868c80d2` (independently recomputed).
- Raw client SHA256: `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298` (pinned input identity supplied for this task).
- Feather Gen2 mergedv2 mapping jar SHA256: `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da`; Tiny mapping SHA256: `de2023ea2cca9921402fbfcfe6e475f41da4932ea6dbc609e35c505b76a32c63` (pinned identities supplied for this task).
- Regenerated complete source set matches the published source-manifest SHA256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (verified by preparation owner).
- Regenerated `World.class` entry SHA256: `c56509d9f9e5226c8110a90182033ba8cbfdee87c6e8e55cd97de43f02ce24fa`.
- The prior mapped jar identity `e36a366f…` remains unavailable. This report does not claim the fresh jar is byte-equivalent to it.

## Bytecode operand proof

Inspected only `net/minecraft/world/World.class::getCollisions(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;` from the fresh mapped jar using `javap -c -p -s`; no class was executed.

Relevant offsets:

- `268–283`: call `getEntities(entity, grownBox)` and store its returned `List` in local 17 (`astore 17`).
- `300–306`: load argument entity's `rider` field, then load local 17; `if_acmpeq 402` skips the remainder of the iteration if those references are identical.
- `309–318`: load argument entity's `vehicle` field, then local 17; `if_acmpne 321` enters the callbacks when they differ, otherwise `goto 402` skips the iteration.
- `321–336`: only after both guards, evaluate `local17.get(index)` and call that candidate entity's `getCollisionShape()`.
- `361–377`: again evaluate `local17.get(index)` and pass that candidate to the query entity's `getCollisionAgainstShape(candidate)`.
- Both callback results are separately tested for non-null and intersection with the query box before being appended to the collision-result list; iteration skip target is offset `402`.

Thus the 1.8.9 guard compares `rider` and `vehicle` to the *result List object*, not to the indexed entity candidate. The candidate is first loaded at offsets `321–330` and `361–371`, after the guard. A mounted player's vehicle entity therefore is not excluded by those reference checks and can reach both shape callback sites, subject to the existing null/intersection gates. The source maps the same expression to `World.java:930–940`; the bytecode resolves the decompiler's operand ambiguity.

## Bounded player movement reachability

The checked 1.8.9 Feather source shows an ordinary local mounted-player path into this query:

- `World.java:1330–1346`: `tickEntity` dispatches `rideTick()` when the entity has a vehicle; `World.java:1387–1393` recursively ticks its rider.
- `Entity.java:1275–1284`: `rideTick()` calls virtual `this.tick()` and then updates the rider position.
- `PlayerEntity.java:182–229` delegates to `LivingEntity.tick`; `LivingEntity.java:1258–1296` calls `mobTick`; `LivingEntity.java:1446–1451` reaches `moveRelative`.
- `PlayerEntity.java:1279–1292`: the mounted branch (including the non-flying branch because flying movement is gated on `vehicle == null`) delegates to `super.moveRelative`.
- `LocalClientPlayerEntity.java:381–383` returns `true` from `isLocallyControlled`; `LivingEntity.java:1119–1122` admits that local movement path, and the ordinary movement branch calls `this.move(...)` (e.g. line 1165).
- `Entity.java:371–376` uses its collision-aware move path when `noClip` is false; normal non-spectator player tick sets `noClip` from `isSpectator()` at `PlayerEntity.java:183–186`.
- `LocalClientPlayerEntity.java:105–113` runs `super.tick()` while riding and sends the local rider's angle/input packets in that branch.

This confirms a local mounted player's ordinary player movement can query collision shapes. It does not model or claim the vehicle's own physics or calculate a trajectory.

## Canonical 1.9 comparison (bounded)

`decompiled_minecraft/1.9/ornithe-feather/net/minecraft/world/World.java:935–949` stores the indexed entity candidate as `entity2`, checks `!entity.hasSameVehicle(entity2)`, then calls both collision-shape callbacks with that candidate. `Entity.java:2209–2221` shows `getVehicle()` walking through mounts to the root vehicle and `hasSameVehicle` comparing those root references. A mounted player and its own boat therefore share a root, so the candidate is skipped before either callback. This confirms corrected filtering by 1.9; it does not establish an intermediate patch cutoff or expand provider coverage.


## Source-file digests

The following SHA256 digests bind the source excerpts used above:

- Fresh 1.8.9 `World.java`: `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`.
- Fresh 1.8.9 `Entity.java`: `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`.
- Fresh 1.8.9 `LivingEntity.java`: `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`.
- Fresh 1.8.9 `PlayerEntity.java`: `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.
- Fresh 1.8.9 `LocalClientPlayerEntity.java`: `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`.
- Canonical 1.9 `World.java`: `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98`.
- Canonical 1.9 `Entity.java`: `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`.
## Finding disposition

Supports the existing `vehicle-collision-exclusion` claim: 1.8.9's rider/vehicle checks compare against the result list and admit the vehicle candidate; 1.9 uses a candidate-based shared-root vehicle filter. Keep the original finding immutable. The prior 1.9.4 source evidence remains as previously bound; no additional 1.9.4/provider review was performed.

No code was changed, and no tests, game launches, builds, or network access were used.