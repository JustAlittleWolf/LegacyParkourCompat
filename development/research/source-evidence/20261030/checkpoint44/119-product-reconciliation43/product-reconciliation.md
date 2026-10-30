# Product reconciliation: pooled 1.19.x ordinary movement candidates

## Scope and integrity

Compared the assigned source-only reports with the current product hooks, changes, resolver and mixin dispatch. Product code was read-only. No product code, backlog, tests, build, game launch or generated source was changed or run. Repository instructions and the implementation workflow were read before reconciliation.

The three assigned report identities rehash exactly:

| Input | SHA-256 |
|---|---|
| `1182-1194-finish43/cross_review.md` | `0aaf0badf33108f2036d3d3e602613166f193320c0abfd86b18663261e08413c` |
| `1182-1194-finish43/fall_contact/source-applicability.md` | `295716e054e34d7d57d53d9e299dfd9b5bda0edb2e9e9a550fe0f90c4898dcb1` |
| `1182-1194-finish43/packet_state/packet_state_review.md` | `ca5476f18744860fcbbc295aa61ae9adaae6af1b5143505b1de401b20362dff1` |

Exact source roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. Ready-marker/source-manifest SHA-256 identities from the assigned review:

| Version | Ready marker | Source manifest |
|---|---|---|
| 1.19.2 | `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |
| 1.19.3 | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 | `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |

Relevant source files were rehashed in place; the 1.19.2/1.19.3 files listed in the assigned reviews match their manifests, and the 1.21.1/1.21.2 comparison was rehashed directly:

| Exact Mojmap source | SHA-256 |
|---|---|
| 1.19.2 `world/entity/Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| 1.19.3 `world/entity/Entity.java` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` |
| 1.21.1 `world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| 1.21.2 `world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| 1.19.2 `world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| 1.19.3 `world/entity/LivingEntity.java` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` |
| 1.19.3 `world/entity/player/Player.java` | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` |
| 1.19.2 and 1.19.3 `world/level/block/HoneyBlock.java` | `5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5` |
| 1.19.2 `client/multiplayer/ClientPacketListener.java` | `98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3` |
| 1.19.3 `client/multiplayer/ClientPacketListener.java` | `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e` |
| 1.19.2 `network/protocol/game/ClientboundRespawnPacket.java` | `4e2e903725e441d010529fd4b11a9b5086aecf26a73983c5fa2d9486eb8025b7` |
| 1.19.3 `network/protocol/game/ClientboundRespawnPacket.java` | `26261980683e598d52685eb07e053e1093fa4a4a98e5b5898bfdd8bf362beed9` |
| 1.19.2 `network/syncher/SynchedEntityData.java` | `b74cad96792db435d732688d668832bd4f35052133b947a8b838ee2a543e6018` |
| 1.19.3 `network/syncher/SynchedEntityData.java` | `e3f85824bbfd868b317214eaf36741e7151e9363d153c8a209fbd37ca6ac7848` |
| 1.19.3 `client/player/LocalPlayer.java` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` |
| 1.19.4 `client/player/LocalPlayer.java` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` |
| 1.19.4 `world/entity/player/Player.java` | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` |
| 1.19.4 `world/entity/LivingEntity.java` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` |

## Dispositions

| Candidate | Product result | Minimal operation / applicable range |
|---|---|---|
| 1.19.2→1.19.3 inside-block inset | **Already covered.** `InsideBlockContactHook` is intercepted at `EntityMixin`'s per-segment AABB `deflate` call. `change/v1_19/InsideBlockContact` returns `0.001`; `change/v1_21/InsideBlockContact` returns `1.0E-7`. With `ChangeResolver` selecting the closest `emulates >= selected` change, target 1.19.2 resolves the former, while targets 1.19.3–1.21.1 resolve the latter; the separate 1.21.2 change retains its newer native `1.0E-5` value. This matches the source cutover at 1.19.3 and later 1.21.2 boundary. No new hook/change is indicated. | Existing independent inset operation; versions 1.19.2 / 1.19.3–1.21.1 / 1.21.2+. Callback impact is conditional on queried cell inclusion and HoneyBlock's movement gates. |
| 1.19.2→1.19.3 elytra fall-distance writer | **Already covered for both sides of the pair.** `change/v1_19/GlideFallDistance` writes `1.0F` at fall-flying travel for the old profile when `dy > -0.5`, matching 1.19.2. The later profile does not resolve this v1_19 change; its `v1_21/GlideFallDistanceLimiterStage` keeps the current native limiter at the fall-flying travel stage for eligible historical Elytra players. Its applicability filter includes non-grounded local players in the 1.9–1.21 interval and excludes the climbable/flight/passenger cases that are outside the fall-flying branch in the relevant source. The retained native limiter's `fallDistance > 1` guard matches the reviewed 1.19.3 behavior. This is the operation seam that matters for the accepted `Player.isAboveGround` / `maybeBackOffFromEdge` state dependency; no separate `isAboveGround` hook is needed for this writer difference. | Existing travel-time fall-distance operation; old reset through 1.19.2, new conditional limiter from 1.19.3. Conditional source impact remains limited to the reviewed fall-distance and edge-backoff states. |
| 1.19.3→1.19.4 ordinary airborne sprint-speed timing | **Already covered.** `change/v1_19_3/AirSpeed` stores `0.02F` plus the historical `0.006F` float addition after inherited travel and returns the stored coefficient outside ability flight. That preserves the previous tick's sprint coefficient for 1.19.3; selected 1.19.4 does not resolve this older change and uses the current sprint-sensitive speed behavior. Product class comments document the distinct float bits and ground/ability-flight exclusions. | Existing AirSpeed hook; cached coefficient through 1.19.3, current sprint sample from 1.19.4. Ordinary airborne travel only. |
| 1.19.2→1.19.3 correction rotation | **Already covered.** `change/v1_19/PlayerCorrectionRotation` restores relative-angle composition, yaw `% 360.0F`, and pitch clamp/wrap from 1.19.2 `Entity.absMoveTo`; 1.19.3 target selection bypasses that old resolver change. It is a client correction response that can affect subsequent movement direction under the reviewed relative-angle condition. | Existing `PlayerCorrectionRotationHook`; 1.19.2 behavior. No additional seam. |
| 1.19.2→1.19.3 respawn entity-data/shared-flags copy | **Real missing minimal operation seam.** The source review accepts the movement response when the raw respawn keep-entity-data bit is clear and the old player has nondefault shared flags: 1.19.2 unconditionally assigns all old entity data to the replacement, while 1.19.3 skips that copy. Product respawn code has only `RespawnMovementAttributeHook`, dispatched after attribute copying; it restores movement-speed attributes and cannot represent the old-player entity-data/shared-flags transfer. No hook or change references the shared flags for this respawn operation. | New, independently resolved respawn player movement-state/entity-data copy hook. A 1.19.2-target change must emulate the unconditional copy under the source state condition; do not merge it into the movement-speed attribute hook. The supplied pair evidence establishes the 1.19.2/1.19.3 split only; broader intermediate cutover remains unproven. |

The resolver direction and exact tags are material here: `MovementChange` says the closest change whose version is not older than the selection wins, and `ChangeResolver.resolve` filters with `emulates().newerThanOrEqual(selected)`. Thus absence of a 1.19.3-specific class does not by itself mean a gap; the 1.21 inset and fall-limiter registrations are the selected candidates for 1.19.3, and their source-compatible behavior was checked above.

### Lower-priority dispositions

- The assigned packet-state review's position-correction rotation is already associated with existing code above. The packet/entity-data respawn behavior is the one uncovered movement-bearing item in that review.
- No item-interaction or entity-interaction source candidate was included in the three assigned evidence files. Keep those lower priority and do not associate them with an implementation seam from this reconciliation alone.
- The fall-contact source report explicitly leaves simultaneous normal-input reachability as a remaining acceptance gate. Its narrow conditional callback evidence is sufficient to confirm the already-present contact-inset hook is relevant, but this reconciliation does not promote acceptance or claim runtime parity.

## Exact source locations used

- 1.19.2 `Entity.checkInsideBlocks`, inset and block callback traversal: `world/entity/Entity.java:886–920`; 1.19.3 same method and `1.0E-7` inset: `world/entity/Entity.java:894–920`. 1.21.1 retains `1.0E-7` at `world/entity/Entity.java:1001–1004`; 1.21.2 changes the query-box inset to `1.0E-5F` at `world/entity/Entity.java:1038–1040`. Honey response: `world/level/block/HoneyBlock.java:56–63, 66–78, 91–100`.
- 1.19.2 fall-flying `LivingEntity.travel`, assignment: `world/entity/LivingEntity.java:2107–2113`; 1.19.3 `travel` call and `Entity.checkSlowFallDistance`: `world/entity/LivingEntity.java:2117–2122`, `world/entity/Entity.java:2147–2151`. Player movement read: 1.19.2 `Player.isAboveGround` / `maybeBackOffFromEdge`, `world/entity/player/Player.java:1061–1117`; 1.19.3, `:1050–1106`.
- 1.19.3→1.19.4 cached/current speed: 1.19.3 `Player.aiStep`, `LivingEntity.getFrictionInfluencedSpeed` and `LocalPlayer.aiStep`; 1.19.4 `Player.getFlyingSpeed` and `LivingEntity.getFrictionInfluencedSpeed`. The packet-state report gives precise member line ranges; source file digests are listed above.
- 1.19.2→1.19.3 correction: `ClientPacketListener.handleMovePlayer` at 1.19.2 `ClientPacketListener.java:563–628`, 1.19.3 `:592–662`; old angle operation `Entity.absMoveTo`, 1.19.2 `Entity.java:1194–1200`.
- Respawn: 1.19.2 `ClientPacketListener.handleRespawn` `ClientPacketListener.java:953–1003`, unconditional entity-data assignment at `:998`; 1.19.3 handler `:1006–1062`, keep-bit-gated entity-data copy at `:1051–1055`. Shared flags and travel consumers are cited in the assigned packet-state review.

## Product associations for the integrator

Already covered: `InsideBlockContactHook` + `InsideBlockContact` (v1_19, v1_21, v1_21_2); `GlideFallDistanceHook` + `GlideFallDistance` (v1_19) with `GlideFallDistanceLimiterStageHook` + its v1_21 stage; `AirSpeedHook` + `AirSpeed` (v1_19_3); `PlayerCorrectionRotationHook` + `PlayerCorrectionRotation` (v1_19). Missing association: none for conditional respawn shared-flag transfer. Existing `RespawnMovementAttributeHook` and its two Changes address a different attribute-reset behavior and are not an association for this candidate.





## Product references checked

| Product path | Relevant location |
|---|---|
| `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java` | resolver `resolve`, version filter and nearest-change choice |
| `src/main/java/me/wolfii/legacyparkourcompat/mechanic/MovementChange.java` | `emulates` applicability documentation |
| `src/client/java/me/wolfii/legacyparkourcompat/mixin/EntityMixin.java` | `checkInsideBlocks` AABB `deflate` redirect, line 727 onward |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_19/InsideBlockContact.java` | emulates 1.19 and returns `0.001`, lines 95–101 |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21/InsideBlockContact.java` | emulates 1.21 and returns `1.0E-7`, lines 17–22 |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_2/InsideBlockContact.java` | retains native inset from 1.21.2, lines 9–14 |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_19/GlideFallDistance.java` | emulates 1.19 and writes one under the old velocity gate, lines 33–43 |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21/GlideFallDistanceLimiterStage.java` | eligible historical-player stage selection and native limiter dispatch, lines 22–55 |
| `src/client/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java` | fall-flying distance dispatch, line 611 onward |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_19_3/AirSpeed.java` | emulates 1.19.3 and stores / returns historical coefficient, lines 31–44 |
| `src/client/java/me/wolfii/legacyparkourcompat/mixin/PlayerMixin.java` | stored coefficient write/read dispatch, lines 185–205 |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_19/PlayerCorrectionRotation.java` | emulates 1.19 correction rotation, lines 19–32 |
| `src/client/java/me/wolfii/legacyparkourcompat/mixin/network/ClientPacketListenerMixin.java` | correction rotation dispatch, lines 53 onward |
| `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/player/RespawnMovementAttributeHook.java` | movement-speed attribute-only contract |
| `src/client/java/me/wolfii/legacyparkourcompat/mixin/ClientPacketListenerRespawnMixin.java` | post-attribute-copy dispatch only, lines 25–54 |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_14_2/ResetRespawnMovementSpeed.java` | existing unrelated respawn movement-attribute reset |
