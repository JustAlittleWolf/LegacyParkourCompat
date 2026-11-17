# Passenger fluid-gate comparison — corrected revision 1

## Scope and source identity

Windows host, Windows PowerShell. Exact read-only Mojmap sources are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`. Each version's sibling `mojmap.provenance.json` records its exact `versionId`, `namespace=mojmap`, and toolchain (Java `25.0.3+9-LTS`, Vineflower `1.12.0`). The following source hashes were checked against each version's `mojmap.sources.sha256` manifest and independently rehashed from the files.

| Version | `Entity.java` SHA-256 | `LocalPlayer.java` SHA-256 |
| --- | --- | --- |
| 1.18.2 | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| 1.19 | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` |
| 1.19.1 | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |
| 1.19.2 | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |
| 1.19.3 | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` |
| 1.19.4 | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` |
| 1.20.1 | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |

Relevant 1.19.3 dependency hashes: `LivingEntity.java` `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`; `Player.java` `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`; `ClientLevel.java` `8c8a6134a4409c899c098012e1a11e7eee736edac3403b89211f8ae8fa06df85`; `ServerGamePacketListenerImpl.java` `81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b`; `ServerPlayer.java` `7df71c12ff99bec35b7c6b0e68fa24e5a5aa7293c6dd8f3342d433936ad35c7c`; `ClientPacketListener.java` `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`.

The prior `comparison.md` SHA-256 is `157489f54edddf6efda3e404bad00a67b3bb3abf7680d83f8f224afdf6aad7dc`; the independent read-only verdict used for this correction is `lane-passenger-fluid-audit/verdict.md`, SHA-256 `af045fc5f2ab00752e642cf13fb85ff73b71e362fd7ce2113d9150d50e8ea8f3`. The original comparison is preserved unchanged.

## Predicate and exact applicability boundary

The exact paired source is `Entity.updateInWaterStateAndDoWaterCurrentPushing()`:

- 1.19.2, Mojmap, `net/minecraft/world/entity/Entity.java:1069-1083`: any `getVehicle() instanceof Boat` sets `wasTouchingWater=false` and skips water-height/current sampling.
- 1.19.3, Mojmap, `net/minecraft/world/entity/Entity.java:1074-1087`: the skip is narrowed to `getVehicle() instanceof Boat && !boat.isUnderWater()`. Surfaced boats still skip. Underwater boats proceed to `updateFluidHeightAndDoFluidPushing(FluidTags.WATER, 0.014)`.
- The exact tested intermediary sources 1.18.2, 1.19, 1.19.1 and 1.19.2 have the all-boats skip; 1.19.4 and 1.20.1 retain the surfaced-only skip. The checked boundary is therefore 1.19.2 → 1.19.3.

`Entity.updateFluidHeightAndDoFluidPushing()` in 1.19.3 (`Entity.java:2826-2890`) intersects the player's bounding box deflated by `0.001` with water cells, computes maximum fluid height, averages flow from intersecting water, and for `Player` does not normalize that average. It scales the vector by `0.014`; if the existing horizontal velocity is below `0.003` and the scaled vector length below `0.0045000000000000005`, it raises the vector to that minimum, then adds it to `deltaMovement`. The write requires a loaded chunk, water intersecting the deflated player box, nonzero aggregate flow, and `isPushedByFluid()`. `Player.isPushedByFluid()` and `Player.isAffectedByFluids()` both return `!abilities.flying` (1.19.3 `Player.java:1853-1856` and `:1044-1047`), so flight disables both the current write and subsequent fluid travel. With still water there is no current-vector write, but an underwater boat can still change player `wasTouchingWater`/water-height state.

## Client passenger tick and resulting player state

`Entity.baseTick()` performs the water-state/current update, then eye-fluid update and swimming update (`1.19.3 Entity.java:407-430`). On a logical client, `ClientLevel.tickPassenger()` invokes `rideTick()` for passenger players (`ClientLevel.java:270-280`). `LocalPlayer.rideTick()` calls its superclass before boat input bookkeeping (`LocalPlayer.java:874-881`); inherited `Entity.rideTick()` has this order: set delta to `Vec3.ZERO`, call the full dynamic player `tick()`, then position the rider if it remains a passenger (`Entity.java:1704-1709`). Within that player tick, `LivingEntity.aiStep()` calls `travel(...)` (`LivingEntity.java:2547-2553`); `LocalPlayer.isEffectiveAi()` is true (`LocalPlayer.java:492-495`), and the water travel branch applies when `isInWater() && isAffectedByFluids() && !canStandOnFluid(...)` (`LivingEntity.java:2052-2095`). The flow vector thus affects the player's own travel and is friction/fall-adjusted into a new player delta. The later `positionRider()` changes player position, not velocity. There is no later passenger-tick delta reset in this path: the local player's tick-end `deltaMovement` differs when eligible underwater water-current conditions hold.

This is a bounded tick-end player movement-state difference even though the rider's final position is set back to the vehicle position. If the player is in flowing water and not flying, the water current can leave altered horizontal delta at tick end. The separate shift-down branch in `LocalPlayer.aiStep()` (`LocalPlayer.java:758-761`) calls inherited `goDownInWater()` and adds `-0.04F` Y when `isInWater()`, shift is held and fluids affect the player. That input-conditioned response follows from the changed water predicate but the `goDownInWater()` operation itself is unchanged across the comparison; it is not claimed as a separate independent finding here.

## Reset and ordinary dismount boundary

The altered delta is not persistent post-dismount motion. If the local player remains a passenger, the next `rideTick()` zeros delta at its start. For ordinary shift dismount, the client sends the shift state (`LocalPlayer.java:186-189`); server `Player.rideTick()` stops riding when `wantsToStopRiding()` sees shift (`Player.java:301-303, 473-485`). `ServerPlayer.stopRiding()` sends the dismount correction (`ServerPlayer.java:957-964`); `ServerGamePacketListenerImpl.dismount()` creates it with an empty relative-argument set and requests dismount (`ServerGamePacketListenerImpl.java:976-978`). The client correction handler removes the vehicle and sets each velocity component to zero when its corresponding relative flag is absent (`ClientPacketListener.java:592-647`); the empty set means all three components are zeroed. When the correction is applied, the player is detached and all three delta components are zero before its next ordinary on-foot movement tick. If it remains attached until another passenger tick, that tick zeros the response at its start instead. Thus the end-of-passenger-tick delta is a real movement-state difference, while neither normal timing path carries it into free-player motion.

## Disposition

The direct water-current write into the local player's `deltaMovement` is an independently significant, bounded player movement-state difference beginning at 1.19.3 for eligible underwater boat passengers. Keep the claim specific to local player water-current input and tick-end delta. Do not infer persistent post-dismount movement or boat motion. The crouch-descent branch is only a conditional consequence of the newly changed water state, not a separate independently version-different operation.