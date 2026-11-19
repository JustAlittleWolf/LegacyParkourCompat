# Independent applicability verdict: underwater Boat passenger travel

Verdict: **ACCEPT** the late-vintage extension bound to the complete draft bytes with SHA-256 `52d5a2d86dc6c2b97a0ca6de487ead94c2beae68fa9f8ff1dfffc9e915ab4d0f` (Git SHA-256 object format, author draft commit `f2478a11eb842f535dc44d0caa95e26b066e9ff2525ec505a0500af9ad6384dd`). This verdict covers only the extension's 1.18, 1.18.1, 1.18.2, 1.19, 1.19.1 and 1.19.2 applicability claims. The previously accepted 1.19.2→1.19.3 core finding is reused as authorized; this review does not reopen it.

## Evidence identity

For each exact release, the canonical ready marker reports the same `versionId`, `mapping: mojmap`, and `status: ready`. The ready marker's `sourceManifestSha256` was recomputed against the corresponding source manifest. All four draft-listed class-file SHA-256 values per release (`Entity.java`, `LocalPlayer.java`, `LivingEntity.java`, `Player.java`) were independently recomputed and matched. The manifest hashes are:

| Release | `mojmap.sources.sha256` SHA-256 |
|---|---|
| 1.18 | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` |
| 1.18.1 | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.19 | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| 1.19.1 | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` |
| 1.19.2 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |

## Scoped finding

In each listed `net/minecraft/world/entity/Entity.java`, `updateInWaterStateAndDoWaterCurrentPushing` takes `getVehicle() instanceof Boat` and unconditionally sets `wasTouchingWater` false before the ordinary water-fluid check. It has no underwater-Boat exception. Each source's `isInWater()` returns this flag. Thus water overlap alone cannot make the mounted player water-contact true through this update while its vehicle is a Boat.

The mounted local-player path is live in all six releases. `ClientLevel.tickPassenger` admits a valid Player passenger and calls `rideTick`; `LocalPlayer.rideTick` calls `super.rideTick`; `Entity.rideTick` clears delta movement, ticks the entity, then positions it from the vehicle if still a passenger. `LocalPlayer.tick` reaches `super.tick` only when `level.hasChunkAt(getBlockX(), getBlockZ())`. On that loaded path, the inherited entity/living tick reaches `LivingEntity.aiStep`, which calls `travel`; `LocalPlayer.isEffectiveAi()` returns true. `Player.travel` delegates to `LivingEntity.travel` while mounted when flying is disabled. With fall-flying also disabled, water contact enabled, and the fluid not standable, the water-versus-land branch follows from `isInWater()`.

The cited method ranges in the draft were checked at the stated releases. `1.18` and `1.18.1` have identical listed class bytes and method lines. `1.19.1` and `1.19.2` likewise have identical listed class bytes. The vanilla class `net/minecraft/world/entity/vehicle/Boat.java` exists in each release and declares `Boat extends Entity`; this verdict is limited to passengers whose vehicle matches that exact class predicate.

## Limits

A water contact update is also conditional on the fluid scan succeeding: its exact method can return false when touching an unloaded chunk and tests a bounding box deflated by `0.001` against sampled fluid surface heights. Accordingly, “bounds intersect water” means a qualifying overlap after those exact checks, with the passenger's own chunk loaded so `LocalPlayer.tick` runs. This does not alter the branch conclusion for the stipulated ordinary loaded local tick.

These six exact releases support the claimed earlier control through 1.19.2, including 1.18; they do not establish an introduction boundary before 1.18, every unsampled version, any release after 1.19.2, or applicability to a separately introduced raft/vessel class. No vehicle movement was inspected or claimed. The review is source acceptance only and establishes no runtime trajectory.