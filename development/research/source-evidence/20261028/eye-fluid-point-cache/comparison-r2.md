# Body-eye-fluid query closure

## Scope and verdict

Compared the exact `1.21.11`, `26.1`, `26.1.1`, `26.1.2`, and `26.2` decompiled trees. The body-eye-fluid producer and `isEyeInFluid` query are unchanged across the four 26.x trees. Between 1.21.11 and 26.1 the producer moved from `Entity.updateFluidOnEyes` to `EntityFluidInteraction.update`, and the cached query changed from a set of fluid tags sampled at the eye block to per-tag tracker state populated by a scan of the entity's fluid interaction box. The source comparison closes that operation across the assigned releases; it does not identify an intermediate release boundary.

## Exact source identity and integrity

All source roots below are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. For every listed Java source, its actual SHA-256 matched the corresponding entry in the release's `*.sources.sha256`. Each ready marker's version and mapping matched the source root; the source and artifact manifest SHA-256 values matched the hashes recorded in that marker. The detailed identity facts and the gap in retained cache-pin evidence are at the end of this section.

| Release / source root | `Entity.java` SHA-256 | `EntityFluidInteraction.java` SHA-256 |
| --- | --- | --- |
| `1.21.11/mojmap` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | Not present; producer/query are in `Entity.java` |
| `26.1/unobfuscated` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| `26.1.1/unobfuscated` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| `26.1.2/unobfuscated` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| `26.2/unobfuscated` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |

Other caller source hashes checked against their source manifests:

| Release(s) | File and SHA-256 |
| --- | --- |
| `1.21.11` | `world/entity/player/Player.java` `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; `client/player/LocalPlayer.java` `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; `world/entity/LivingEntity.java` `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `26.1`, `26.1.1`, `26.1.2` | These three files are byte-identical among the releases: `Player.java` `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; `LocalPlayer.java` `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `LivingEntity.java` `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `26.2` | `Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `LocalPlayer.java` `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |

Literal output records checked: each `<release>/<mapping>.provenance.json`, `<release>/<mapping>.ready.json`, `<release>/<mapping>.sources.sha256`, and `<release>/artifacts.sha256`. The `1.21.11` ready marker says `versionMetadataId=1.21.11`, `clientSha256=1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; its artifact manifest has the same `1.21.11/client.jar` hash. Its provenance records the exact 1.21.8/1.21.10/1.21.11 Mojmap run, staging output root, tool versions, and staging cache path. The ready marker records the canonical source root. The recorded staging cache directory is no longer present, so its original metadata `.pin.json` could not be re-read.

For `26.1` and `26.1.1`, provenance records exact metadata IDs, metadata SHA-256 values (`68e8d6e01e06e6a9a740a7ad474061f9303b004ceedb8224b085a58616390e49`, `86b232149a2ae224b06443730fe32c46c306f035a1599e3477c5237df1c950ef`) and client SHA-256 values (`bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840`, `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3`); the client hashes match their artifact manifests. The `26.1.2` ready marker records `versionMetadataId=26.1.2` and its client SHA-256, which matches the artifact manifest. For `26.2`, the ready marker records `versionMetadataId=26.2` but has no `clientSha256`; its provenance has neither a metadata hash nor client hash. `artifacts.sha256` lists `26.2/client.jar` with SHA-256 `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`, but this artifact inventory does not bind that client hash as provenance input. The provenance-referenced build cache/staging directories for these runs are absent, so original input `.pin.json` sidecars could not be independently checked. Source files remain hash/manifest bound; the `26.2` client artifact binding and the staged pin sidecars remain open.

## Producer, cache, and query

**1.21.11.** `world/entity/Entity.java`, `updateFluidOnEyes`, lines 1524–1536, first assigns `wasEyeInWater` from the *previous* `fluidOnEyes` set, clears the set, and obtains `getEyeY()`. Unless a non-underwater `AbstractBoat` bounding box spans the eye Y (`1528`), it samples only `BlockPos.containing(getX(), eyeY, getZ())`, gets that block's `FluidState`, computes `blockY + getHeight(level, pos)`, and adds the state's tags when the fluid top is strictly greater than eye Y. `isEyeInFluid(TagKey<Fluid>)` at lines 1600–1602 is `fluidOnEyes.contains(tag)`. This is per-entity cached state refreshed in `baseTick` after `updateInWaterStateAndDoFluidPushing` and before `updateSwimming` (`Entity.java:504–506`).

**26.x.** The matching `EntityFluidInteraction.java` source in all four releases resets the registered trackers at each `update` (`32–33`). It obtains `entity.getFluidInteractionBox()` and, if non-null, scans integer block coordinates spanning its min/max (`34–41`). Before scanning, `hasFluidAndLoaded` must find full chunks and at least one fluid-bearing relevant section in a one-block-expanded X/Z extent (`42`, `91–118`). For each nonempty state whose top is not below the box minimum (`56–60`), it resolves that fluid to a registered tag tracker (`61–66`). The eye flag is set when block X/Z equal `entity.getBlockX()/getBlockZ()` and `eyeY` is inclusively between fluid bottom and top (`68–70`). The tracked answer is reset each update and retained in `Tracker.eyesInside` (`153–164`); `isEyeInFluid` reads that flag for the requested tag (`148–151`).

The entity wrapper calls `fluidInteraction.update(this, !this.isPushedByFluid())` in `updateFluidInteraction` (`26.1` `Entity.java:1558–1560`; `26.1.1`/`.2` same byte-identical file; `26.2` `Entity.java:1645–1647`). Its eye result is sampled into `wasEyeInWater` immediately before that update in `baseTick` (`26.1` through `.2` `Entity.java:523–524` for the first three, `26.2` `530–531`). This preserves the prior-sample meaning of `wasEyeInWater`: `isUnderWater` combines it with current body water (`26.1`–`.2` `Entity.java:1526–1528`; `26.2` `1613–1615`). The current eye-query cache itself is refreshed by the interaction scan during the same base tick.

The default box is the entity bounding box deflated by `0.001`; a vehicle may modify it (`Entity.java:4015–4027` in 26.1, `4088–4100` in 26.2). `AbstractBoat.modifyPassengerFluidInteractionBox` returns null when a non-underwater boat's max Y reaches the passenger box max Y; otherwise it clips the passenger box minimum Y to the boat top (`26.1` `vehicle/boat/AbstractBoat.java:783–795`, identical behavior in 26.2). This is the new passenger-side gate paired with the old direct eye-vs-boat-box gate; they are expressed at different geometric boundaries.

## Ordinary-player callers and consumers

- `Player.updateIsUnderwater` directly reads the cached water-eye result and stores/returns `wasUnderwater` (`1.21.11` `world/entity/player/Player.java:313–315`; `26.1`–`.2` `303–305`). In 1.21.11 the local-player `isUnderWater` accessor is at `client/player/LocalPlayer.java:1137–1140`, and `updateIsUnderwater` refreshes that cache at `1142–1159`, including the spectator early return at `1146–1148` and final return at `1159`. In 26.1–26.2 the accessor and refresh are at `1176–1198`, with spectator early return at `1185–1187` and final return at `1198`.
- `Entity.isUnderWater` uses `wasEyeInWater && isInWater`, and `updateSwimming` uses it in the branch that starts swimming (`1.21.11` `Entity.java:1469–1474, 1493–1497`; `26.1`–`.2` `1526–1531, 1548–1555`, with shifted 26.2 line numbers). For an ordinary player these affect underwater classification and swimming state.
- The ordinary local player's tick checks eye-water for water-vision time (`LocalPlayer.java:824–829` in 1.21.11; `863–868` in 26.x); `getWaterVision` also gates on it (`1115–1128`; `1154–1168`). This is a direct consumer, though the rendered vision result is outside movement scope.
- `Player.tick` gates turtle-helmet ticking on not being eye-submerged (`Player.java:285–287` in 1.21.11; `278–280` in 26.x). `Player.getDestroySpeed` checks it before applying submerged mining speed (`1.21.11` `Player.java:617–619`; `26.1`–`.2` `Player.java:607–609`); both are non-movement effects.
- `LivingEntity` checks eye-water during its server-side air/drowning path, subject to the eye block not being a bubble column and breathing/invulnerability gates (`1.21.11` `LivingEntity.java:423–432`; `26.1`, `26.1.1`, and `26.1.2` `LivingEntity.java:417–432`; `26.2` `LivingEntity.java:436–450`). Air, health, and damage results are outside the requested movement closure.
- Local-player travel/sprint predicates also consume `isUnderWater` (`LocalPlayer.java:1107–1108` in 1.21.11; `1146–1147` in 26.x), preserving the same cached field path. The adjacent `goDownInWater` branch is gated by body `isInWater`, shift input, and `isAffectedByFluids`, not by eye-fluid state (`820–822`; `859–861`).

## Difference and movement meaning

The changed operation is a source-confirmed producer/query delta, not a claim about a measured trajectory. The former eye-block test uses strict `fluidTop > eyeY`; the new tracker test includes `eyeY == fluidTop` when that cell is within the scanned interaction box. The new query also depends on the deflated/vehicle-adjusted box and the full-chunk/section-fluid fast gate, then resolves membership through the configured fluid trackers. This changes the predicate available to ordinary-player underwater/swimming consumers at the exact fluid-surface boundary and in cases affected by the new box/scan gates. The previous-sample `wasEyeInWater` ordering is retained across the refactor.

## Closure limits

Closed: the producer, per-tick cache refresh, query accessor, ordinary-player movement-related consumers and relevant source gates were traced across all five assigned releases; the `26.1`, `26.1.1`, and `26.1.2` relevant producer and caller sources are byte-identical, and the `26.2` fluid interaction implementation is byte-identical to those three.

Open: the exact introduction release for the 26.x producer/query change is not established between `1.21.11` and `26.1`; no intermediate releases were inspected. Retained input cache pin sidecars could not be independently re-read from the cache/staging paths named in the provenance records, as detailed above. This report does not claim pair-wide movement coverage, runtime parity, or a broader endpoint cutoff.
