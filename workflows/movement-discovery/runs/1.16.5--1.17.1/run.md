# Discovery: 1.16.5 to 1.17.1

- Status: active
- Scope: client player movement; A = `1.16.5`; B = `1.17.1`.
- Repository revision/start date: `c133c29`; 2026-09-26.
- Namespace: official Mojang names; explicit `mojmap` CLI mode on both sides. Each release uses its own official client mappings artifact and is independently remapped to `named`.
- B command: `gradlew.bat --no-daemon --rerun-tasks decompileMinecraft --versions=1.17.1 --mappings=mojmap`; successful log at ignored `build/movement-discovery/1.17.1-mojmap.log`.
- A: source owner and coordinator confirmed exact 1.16.5 Mojmap task success. A's raw log is not in the accessible worktree; artifact hashes are recorded below, and the raw log remains a provenance closure item.
- Toolchain: Gradle 9.7.1; JDK 25.0.3+9-LTS for Vineflower (B log); target bytecode Java 16 (B); Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; Gson 2.14.0; ASM 9.10.1; default 4G heap and repository options.

## Artifact manifest

Hashes are SHA-256 unless identified as publisher SHA-1. Source roots are under the shared ignored `decompiled_minecraft` directory. Cache paths below are local and ignored.

### A — 1.16.5

- Requested/resolved `1.16.5`; exact resolution and successful task completion reported by source owner.
- Source root: `decompiled_minecraft/1.16.5/mojmap/`.
- Owner cache client jar `build/minecraft-decompile-cache/1.16.5/client.jar`: SHA-256 `00B5EBBC33E95EA88C1AB80601599C9827E9AA861D93CCC2C7E3AAA281AB00C8`; Mojang SHA-1 `37fd3c903861eeff3bc24b71eed48f828b5269c8`.
- `version.json` SHA-256 `3EEEAB7B3165CC5263DC26FF8FE114FDB718B75A0244EFEAD9D19635D090BA72`.
- `mojmap`; official mapping artifact URL `https://piston-data.mojang.com/v1/objects/374c6b789574afbdc901371207155661e0509e17/client.txt`; cached `client_mappings.txt` SHA-256 `7931ED6D723ECEB1D621D05A76E10DDF643BF468C6ECF1C4ECF377BD72CF8B8C`; Mojang SHA-1 `374c6b789574afbdc901371207155661e0509e17`.
- Remapped `client-mojmap.jar` SHA-256 `18DA764651703F18941A800D4421C4FC28DB39C1586060C2C7E3AAA281AB00C8`.
- Raw successful decompiler log unavailable locally; do not claim a persisted log.

### B — 1.17.1

- Requested/resolved `1.17.1`; exact ID confirmed in successful log.
- Source root: `decompiled_minecraft/1.17.1/mojmap/` (worktree junction to shared ignored root).
- `client.jar` SHA-256 `A49B4A56C5BBE15C9ED9FE53EFA9A591F265A1F5BA7D6AA9739A58EA7A92B79D`; Mojang SHA-1 `8d9b65467c7913fcf6f5b2e729d44a1e00fde150`.
- `version.json` SHA-256 `B6125F5A4410C3A71C1CF177DBDBF8C9E409BDE8E435FB094BEDD5AD6CE07902`.
- `mojmap`; official mapping artifact URL `https://piston-data.mojang.com/v1/objects/e4d540e0cba05a6097e885dffdf363e621f87d3f/client.txt`; `client_mappings.txt` SHA-256 `2B28DED68F8602AAF2F35EF92DD10EE41BA2CF9723E29570155D60E1723848E9`; Mojang SHA-1 `e4d540e0cba05a6097e885dffdf363e621f87d3f`.
- Remapped `client-mojmap.jar` SHA-256 `E85FCB0A8656EACE49020D2B5824D33FAD25FB53E7CCABA0C3DE2DBC6CC0E923`.
- Log `build/movement-discovery/1.17.1-mojmap.log`; successful completion, plus two Vineflower duplicate-lambda processing notices in `ModelBakery`.

### Cited source hashes

Paths are relative to each version's Mojmap source root; hashes are SHA-256.

| Role | Path | A | B |
| --- | --- | --- | --- |
| Local player | `net/minecraft/client/player/LocalPlayer.java` | `6011569E766BB1568609147BE9AA14E9C08C51948E3D3A60FD066E848F6A8C2B` | `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812` |
| Keyboard producer | `net/minecraft/client/player/KeyboardInput.java` | `746EA654CF4F46A5F4B94A237C4307652252A44807A488B606DC993E088396F7` | `EA41065C909E53F1A2CC29ECDB6A9A8F9265D2801CD8B95B996E182C318ECD69` |
| Input state | `net/minecraft/client/player/Input.java` | `367C3A9B0B21D8F106A21FD2C73A3018685DBF07D9C8A9340E2D4C9D73359201` | same |

Resource inventory is pending except for the cited B powder-snow entity tag. Neither client jar includes `data/minecraft/dimension_type/`; resolve version-matched server/datapack dimension-type data before closing the levitation slice.


### Additional sources cited by findings

| Role | Path relative to Mojmap root | A SHA-256 | B SHA-256 |
| --- | --- | --- | --- |
| Player jump wrapper / abilities field | `net/minecraft/world/entity/player/Player.java` | `D2E26589BDB6A20DC914266DB06AA48F50811EFC792D6E63B3008C9199914960` | `724BB298499DABE489DFFD5CE7EC81C9773619A8D2C70044911EAE4C9E8FF481` |
| ItemStack item predicate | `net/minecraft/world/item/ItemStack.java` | `2BB228453C39F9792DC1496802E46220738DBF32855442696BAAA0288F791156` | `BA25D48580A3C586233956558905467DC183502EFC59334090B65D46F598DA59` |
| Entity horizontal squared distance | `net/minecraft/world/entity/Entity.java` | `F9A9A073FE3105A0AA53D0F21EC72E59084E8D21A14C1CD3BE75703865EE2666` | `AB28E1FBA924771EC048140DFD293EE5A46A7DFE81F71A1A0B1AECC1927232DE` |
| Vector distance | `net/minecraft/world/phys/Vec3.java` | `B889010C321C45B497E71FF782D108A718ED23A5F605D6162904045E1E01FD48` | `EB52A20224767DAEB5DDC500552C673DF2DAF9CD6868D7E74356411E94EF402E` |
| Math helper | `net/minecraft/util/Mth.java` | `0E4424FFA12923314C309AE13E012768271DFCACE4B5A84727F74E8170AFE290` | `24515C4549E01E985017227DCCF7159166A675B232BE9135D5F896022A9CB113` |
| Level height lookup | `net/minecraft/world/level/LevelReader.java` | `D301B992D71B05306A3139E69F3F1529190F82404791EC60F9A693188D29577A` | `4D041CDBC702D532C5B9DA800C34BD1C23F8F3D3B554ECBDD89DECBB765A6B451A` |
| Dimension type | `net/minecraft/world/level/dimension/DimensionType.java` | `E2F87508C846871CE9A302509E5057E925AB242FA0A5C1BB953FB2B8BFB20DAC` | `2DC118F16B671D3B1B9736C10E960E199790F7930CFA18983F379BEB8D00F27B` |
| Block registry | `net/minecraft/world/level/block/Blocks.java` | `3B39D5CC4CD22F146ED3195AA30CBB9FDFCA49F63783FABF9924A6CE7795FA12` | `87D72A113A3F8937A6A585EF917A4FD29CC5B335C00A858F6800E38F3C1BF7A8` |
| Powder snow | `net/minecraft/world/level/block/PowderSnowBlock.java` | absent in A | `4069B3EF70E2D0CE146BBFF79BE54B2BE99B7C7B32818C1EB45F11FD66F99AA3` |

| Coordinate constructor/floor | `net/minecraft/core/Vec3i.java` | `2C04952E6A5CECBC641E69EA6376AF91A13EBC4D8D6F427FC5392B50A5619FC2` | not cited in B |
| Section-coordinate conversion | `net/minecraft/core/SectionPos.java` | — | `D17E6D4FF5E7573DDE74FDDAEB2733A39ECAC9C9EF4621A10BF868CC0513F52A` |
B client-jar resource cited by the powder-snow finding: `data/minecraft/tags/entity_types/powder_snow_walkable_mobs.json`, SHA-256 `A1D2F5C240C8D21446675AC242F898CC23EF3C343A2BC5FE125249192C9FBC3E`.
## Correspondence and call order

See [navigation.md](navigation.md). Names and signatures are resolved independently in both exact Mojmap trees; no name-only inference is used for a behavioral conclusion.

## Coverage ledger

Each row below is one scoped slice. `pending` means no paired comparison has been completed; it is not evidence of equivalence.

- `S1-INPUT-KEYBOARD`, stage 1: `compared-no-difference`, scoped to `KeyboardInput.tick(boolean)` and `Input` state access. Both sides sample directional/jump/shift controls, derive impulses with identical equality/ternary expressions, then when the same boolean argument is true scale each impulse by `(float)(impulse * 0.3)`. B declares `MOVING_SLOW_FACTOR = 0.3`, but the method uses the same `0.3` double literal. The expression order and cast match; `Input.java` is byte-identical by SHA-256. The meaning of the boolean argument and other local-player input branches are separate slices.
- `S1-LOCAL-AISTEP`, stage 1: `compared-no-difference`, scoped to full `LocalPlayer.aiStep()` movement-gating method (A lines 627–793, B lines 649–823). Checked branches retain the same input/tick order, item-use slowdown, auto-jump, sprint timing/gates, water and vehicle handling, flight gates and jump dispatch. Source edits are direct `abilities` reads to B `getAbilities()` (returns same field) and A `getItem() == Items.ELYTRA` to B `ItemStack.is(Items.ELYTRA)` (returns `getItem() == item`).
- `S1-LOCAL-TICK-GATE`, stage 1: `compared-no-difference`. A constructs `BlockPos(x,0,z)` and `hasChunkAt(BlockPos)` uses floored X/Z shifted right by 4; B uses `getBlockX/Z` kept current by `setPosRaw` flooring and `SectionPos.blockToSectionCoord`, also right shift by 4. Y is ignored on both. B rotation getters return the fields A reads directly.
- `S1-ENTITY-TICK-ORDER`, stage 1: `pending`; compare `LocalPlayer.tick`, parent `Entity.tick`, `LivingEntity.tick` and server/client AI ordering beyond the bounded local-player guard/packet slice. Navigation: `LocalPlayer.java`, `Entity.java`, `LivingEntity.java` in both trees.
- `S1-LOCAL-SPRINT-JUMP-AUTOJUMP-GATES`, stage 1: `pending`; separately close sprint timers, jump/auto-jump and riding/flight branch preconditions and state writes in `LocalPlayer.aiStep()`.
- `S2-PLAYER-STATE-POSE`, stage 2: `pending`; compare player abilities/food/effects defaults, pose and dimensions, eye height, swimming and active-item writers plus reachable local-player gates.
- `S3-JUMP-BOOST`, stage 3: `findings`; source-confirmed precision difference on the player jump path. See [finding](findings/S3-jump-boost-precision.md).
- `S3-POWDER-SNOW-CLIMB`, stage 3: `findings`; modern-only climb assist; related block registration/resource slice `S5-POWDER-SNOW` is recorded below. See [finding](findings/S3-powder-snow-climb.md).
- `S3-LEVITATION-UNLOADED-CHUNK`, stage 3: `blocked` for environment-specific disposition pending exact active dimension data and its source/synchronization path. See [finding](findings/S3-levitation-dimension-min.md).
- `S3-GLIDE-DISTANCE-MATH`, stage 3: `compared-no-difference`, scoped to horizontal distance and animation speed helper expressions: A `Entity.getHorizontalDistanceSqr` returns `x*x + z*z` before `Math.sqrt`; B `Vec3.horizontalDistance` computes the same terms in the same order then calls `Math.sqrt`. A `Mth.sqrt(double)` returns `(float)Math.sqrt(d)`, matching B's explicit cast in `calculateEntityAnimation`.
- `S3-DISCARD-FRICTION-PLAYER`, stage 3: `not-applicable`; B field defaults false, and the only B vanilla writers are `LongJumpToRandomPos<E extends Mob>` and `LongJumpMidJump extends Behavior<Mob>`. B writer source SHA-256 values: `4EC55D5D7915C32BF1A649B119A0E325ACB2ABC1D1A356890BEE093C42287FD2` and `36F42F3F7B9A5ECC4F3145A07F0C14EB710AF6BCC28031CB99520313255CE473`; this mob-only state path is not reachable by the local player.
- `S3-TRAVEL-BRANCHES`, stage 3: `pending`; separately compare ground/air, water/lava, climb, fluid, friction and post-travel state paths beyond the bounded slices above.
- `S4-ENTITY-COLLISION`, stage 4: `pending`; compare `Entity.move`, axis clipping, stepping, shape iteration, callbacks and position/box updates.
- `S5-BLOCK-FLUID-PROPERTIES`, stage 5: `pending`; enumerate player-reachable block/fluid friction, speed/jump factors, flow and callbacks, registrations and tags beyond powder snow.\n- `S5-POWDER-SNOW`, stage 5: `findings`; the powder snow registration, block class and tag data are source-confirmed modern-only; see [finding](findings/S3-powder-snow-climb.md). It is excluded from historical behavior for A-era maps.
- `S6-EFFECT-ENCHANTMENT-EQUIPMENT`, stage 6: `pending` except the Jump Boost consumer path documented under `S3-JUMP-BOOST`; compare effect application, enchantment formulas/conditions, attribute aggregation and equipment writers/data.
- `S7-EXTERNAL-STATE`, stage 7: `pending`; compare packet-driven velocity/position corrections, pushes, explosions, pistons and riding transitions, distinguishing client-computed movement from server-supplied state.
## Dependency queue and blockers

- `A-LOG`: obtain the 1.16.5 owner's raw successful log or preserve its transcript location; completion was confirmed, but no raw log is present in the accessible owner build directory.
- `RESOURCES`: inspect exact client-jar resources and relevant server-supplied inputs for stages 5–7.
- `DECOMPILER-WARNINGS`: determine whether B's two `ModelBakery` duplicate-lambda warnings intersect any movement dependency; otherwise disposition them as unrelated.

## Finding index

- [Jump Boost addition precision](findings/S3-jump-boost-precision.md) — source-confirmed; player behavior.
- [Powder snow climb assist](findings/S3-powder-snow-climb.md) — source-confirmed; modern-only mechanic.
- [Levitation dimension minimum](findings/S3-levitation-dimension-min.md) — candidate; blocked pending external dimension data.

## Resume checkpoint

- Compared/resolved slices: `S1-INPUT-KEYBOARD`, `S1-LOCAL-AISTEP`, `S1-LOCAL-TICK-GATE`, `S3-JUMP-BOOST`, `S3-POWDER-SNOW-CLIMB`, `S3-GLIDE-DISTANCE-MATH`, `S3-DISCARD-FRICTION-PLAYER`; `S3-LEVITATION-UNLOADED-CHUNK` remains blocked for environment-specific disposition.
- Next: compare stage 1 parent entity/living tick order, then close stage 2 player state and stage 3 travel branches.
- Outstanding: A raw log, source hashes and dependencies for future slices, resource/data review.
- Runtime validation: not performed.

## Source audit closure

Partial and in progress. Three stage-1 slices are compared; one stage-3 source-confirmed player finding, one modern-only finding, one blocked external-data candidate, one bounded no-difference math slice and one not-applicable player slice are recorded. All remaining stage rows are explicitly pending in the ledger. Outstanding dependencies include the A raw log, dimension-type data and source path, client resources, and the relevance disposition for B decompiler notices. This catalog does not claim exhaustive equivalence.
