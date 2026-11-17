# Source queue: 1.16.1 to 1.16.2 local-player tick slice

## Terminal status

COMPLETED. Exact local Mojmap source was compared read-only. No in-scope movement finding was supported by this assigned slice, so there are no finding drafts. Do not treat the scope-adjacent excluded edits below as findings.

## Inputs and identity

- Older: Minecraft 1.16.1, Mojmap; `decompiled_minecraft/1.16.1/mojmap/`.
- Newer: Minecraft 1.16.2, Mojmap; `decompiled_minecraft/1.16.2/mojmap/`.
- Older source-manifest SHA-256: `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` (matches the ready record and the actual `mojmap.sources.sha256` bytes).
- Newer source-manifest SHA-256: `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` (matches the ready record and the actual `mojmap.sources.sha256` bytes).
- Focal file entries in each source manifest were checked against the actual source files: `LocalPlayer.java`, `Player.java`, `LivingEntity.java`, and `Entity.java`.

## Ordered coverage

| Queue segment | Compared source | Result | Closure |
| --- | --- | --- | --- |
| Input/prior control writers into local-player tick | `LocalPlayer.tick()` and `sendPosition()` (1.16.1 lines 183, 213; 1.16.2 lines 184, 214), `Player.tick()` (207 both), `LivingEntity.tick()` (2122 / 2124) | Method bodies identical across releases. | Closed for these methods. Input acquisition before these methods was not inventoried. |
| Sprint eligibility and timer writes | `LocalPlayer.setSprinting(boolean)` (475 / 450) and `LocalPlayer.aiStep()` (652 / 627) | Setter and sprint/timer/control logic identical. The only `aiStep` delta in its body is the four in-block calls, excluded here as suffocation handling. | Closed for sprint eligibility/timers, except excluded in-block response. |
| Jump cooldowns and flags | `LocalPlayer.aiStep()`, `Player.aiStep()` (483 both), `LivingEntity.aiStep()` (2366 / 2368), `LivingEntity.jumpFromGround()` (1880 both), and `Player.jumpFromGround()` (1369 / 1376) | Jump timer/flag and jump response methods identical. | Closed for these methods. |
| Pose and travel route choice | `Player.tick()`, `Player.travel(Vec3)` (1380 / 1387), and `LivingEntity.travel(Vec3)` (1912 / 1914) | Method bodies identical. | Closed for these methods. Pose/dimension providers outside this player-tick call slice were not inventoried. |
| Post-travel direct position/box write, ordinary dry state | `Entity.move(MoverType, Vec3)` and `Entity.setPos(double,double,double)` (321 / 331), plus newer `EntityDimensions.makeBoundingBox(double,double,double)` (1.16.2 lines 21-25) | `setPos` now delegates box construction. The 1.16.2 helper performs the same `float width / 2.0F`, `float height`, and `new AABB(x-halfWidth, y, z-halfWidth, x+halfWidth, y+height, z+halfWidth)` operations in the same order as 1.16.1 `setPos` lines 323-325. This is a relocation with equivalent arithmetic, not a reachable state change. `Entity.move` otherwise differs by clearing `isTouchingLava`, excluded liquid handling. | Closed for the ordinary dry post-travel writer and its direct box math. |

## Exclusions and open scope

- In `LocalPlayer.aiStep`, four `checkInBlock` calls become `moveTowardsClosestSpace`; this is the suffocation/in-block response, explicitly excluded by the assignment.
- In `Entity.move`, `isTouchingLava = false` is removed; this belongs to excluded liquid state.
- No differences were found in the assigned sprint, jump, route-choice, travel, or ordinary dry position-writer slice. This does not establish full-pair tick coverage: input gathering, pose/dimension providers and consumers, other entity/block effects, and unrelated direct responses remain unexamined.
- No product code, wiki, Git history, backlog, test, launch, or source generation was used.

## Finding drafts

None. A draft would misstate either the scope-excluded suffocation/liquid edits or the exact-equivalent bounding-box delegation as a movement difference.
