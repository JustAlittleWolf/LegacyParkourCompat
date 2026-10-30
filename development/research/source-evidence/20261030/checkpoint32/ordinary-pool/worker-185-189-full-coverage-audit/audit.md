# Independent full-pair coverage audit: 1.8.5–1.8.9

## Verdict

**ACCEPT full-pair source freeze for the adjacent release chain 1.8.5 → 1.8.6 → 1.8.7 → 1.8.8 → 1.8.9, under the equal-effective-input and equal-received-movement-state boundary.** The previous `worker-fullfreeze-185-189/audit.md` verdict predates the complete adjacent Java matrix, its independent inventory review, the callback/effect/equipment supplement, and the collision-namespace closure correction. I do not carry forward its “not met” conclusion: those later artifacts together close the specific gaps it identified. This is a source freeze, not a claim of runtime parity or of equal trajectories after server-produced world state diverges.

The command-block-item dispenser behavior removed on 1.8.5→1.8.6 is a real server-side state producer. It remains a boundary for equal-initial-world/all-server-evolution claims; it does not invalidate this freeze because the comparison fixes the received vanilla movement-relevant state at each tick boundary. No changed edge reaches a player movement terminal under that condition.

## Scope and audit method

I followed the ordered local-player path from key sampling and input conversion through local tick/AI transfer, sprint/jump/flight decisions, player and living travel, entity movement, collision clipping, position/box/flag and velocity writes, then post-move landing/contact/fluid responses consumed by later ticks. I separately followed state entering those terminals from direct player packet responses, callbacks/entity contact, status effects and attributes, equipment, pose/dimension writers, block registrations and packaged resources.

For adjacent Java edges, the complete matrix has 1,612 paths per edge and records both endpoint SHA-256 values for every path. Its independent review recomputed all 6,448 endpoint hashes and statuses against the five exact source trees and manifests, confirming zero additions/removals and changed-path counts 21, 16, 28, 26. I reconciled each reachable terminal family below to the matrix’s complete path inventory and applicable changed-hunk dispositions. For the provider families, the correction independently filters all rows and shows zero changed paths throughout `net/minecraft/block/**`, `net/minecraft/entity/**`, and `net/minecraft/world/**` on all four edges. The 1.8.8→1.8.9 archive comparison covers all assets; the earlier pinned archive comparison covers 1.8.5–1.8.8. I read no product code/history, wiki material, backlog, or other discovery lane. No decompile, download, build, test, or game launch was performed.

## Tick graph and terminal/dependency closure

| Tick route or terminal family | Paired evidence and closure across all four edges |
|---|---|
| Input production/transfer | `Input`, `KeyboardInput`, `KeyBinding`, `ClientPlayerEntity`, and `LocalClientPlayerEntity` are paired by the core rosters and ordinary endpoint audit; no adjacent changed-path inventory introduces a changed input route. `GameOptions` deltas are UI/resource selection. |
| Sprint, jump, flight, and decision inputs | `LocalClientPlayerEntity`, `PlayerEntity`, `LivingEntity`, and ability consumers are included in the core/tick slices. Direct abilities, game-mode, entity metadata and related packet inputs are enumerated in the callback/effect supplement and their handler/packet paths are unchanged; the selected decision path therefore has paired coverage. |
| Ground/air/water/lava travel and acceleration | `LivingEntity`, `PlayerEntity`, `Entity`, and client-player roots are paired; the 1.8.8→1.8.9 ordinary audit traces input through ground/air travel and the earlier core roster binds the same roots through 1.8.8. Fluid/material implementations and callers are additionally closed by the full block/entity/world namespace hashes. |
| Position, velocity, box, collision flags and clipping | `Entity.move` and its central position/box/flag writers are in the paired core evidence. All concrete entity/player/world Java sources are within the exhaustive per-edge matrix and the entity/world namespaces have no changed or added/removed path. Direct packet velocity, explosion, correction, teleport/respawn and ability paths are enumerated in the supplement and paired by source path. `Box.java` is identical at all five releases (SHA-256 `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea`). |
| Block collision shapes and registrations | The matrix proves every `net/minecraft/block/**` path has identical endpoint hashes on each edge, with no path additions/removals. This includes shape overrides, inherited implementations, block-state/property declarations and `Blocks` registration. Changed paths outside that namespace are individually routed; no changed movement-provider registration remains. |
| Contacts, callbacks and landing responses | The supplement names the player-reachable `onEntityCollision`, `onFallenOn`, cobweb, portal and entity-contact dispatch paths and override set. Their source paths fall in unchanged block/entity namespaces at every adjacent edge; central callers are also hash-paired. Non-player-only callback overrides do not expand the player-response scope. |
| Fluids, currents and material predicates | All concrete block/material/fluid implementations and world/entity query paths are inside the complete unchanged provider namespaces. Given equal received block/fluid/neighbour state, the same providers and query callers receive the same values. This closes provider implementation and dependency-code coverage; it does not assert equality when world inputs differ. |
| Pose, dimensions and box recalculation | Player/entity pose, size, position and collision recalculation sources reside in the unchanged entity/world namespaces. Client packet, respawn and other direct player state paths are enumerated in the supplement and hash-paired. |
| Effects, attributes, modifiers and equipment | The supplement enumerates registration/consumption, player-reachable item/potion/beacon/entity effect sources, commands, packet receipt, attribute modifiers, item/equipment transfer and persistence. Their Java paths are in the exhaustive per-edge matrix; any changed `EffectCommand.java` hunk on .8→.9 was directly reviewed and preserves its movement-effect arguments and gates. Server-origin values are fixed as received state for this conclusion. |
| Resources and bootstrap | Verified pinned archives show the 1.8.5–1.8.8 asset changes are presentation text only; 1.8.8→1.8.9 has one English language entry change, with blockstates/models/block textures unchanged. Changed `Bootstrap` hunks are routed: command-block dispenser removal is the stated server-state boundary; empty skull-owner parsing has no movement registration effect. |

### Adjacent edge routing

- **1.8.5→1.8.6:** 21 changed Java files. No player movement terminal/provider path changes. The removed command-block-item dispense behavior is server-only and may change future received block state. Other placement/NBT, network, UI, resource-pack and diagnostic changes do not alter a response to equal received state.
- **1.8.6→1.8.7:** 16 changed Java files. Changes route to resource-pack download/reload, sign serialization, server/network, UI, HTTP and diagnostics; no movement terminal/provider change.
- **1.8.7→1.8.8:** 28 changed Java files. Resource-pack/options and UI changes affect selected presentation assets; `Bootstrap` changes empty skull-owner parsing; storage changes bookkeeping. No movement terminal/provider change.
- **1.8.8→1.8.9:** 26 changed Java files and no path additions/removals. There are no changes under block/entity/world/network namespaces. The changed effect-command locals preserve gates, values and constructor order; other reviewed deltas concern options, release labels, rendering/audio, books, Realms, server/network and diagnostics.

## State boundary and interpretation

Every terminal receives the same effective player inputs, movement runtime state (including effect/attribute/equipment state), and movement-relevant received vanilla block/world state in this verdict. Contextual shape/current logic can read neighbours, fluid levels, block-entity data and timing; its code is identical, while those values are part of the received/effective state boundary. The removed dispenser behavior can cause those received values to diverge in a wider server simulation. That does not constitute a difference in the movement response to equal values.

The release chain is exactly the five named releases. The verdict does not infer patch introduction points inside intervals, profile switching, runtime parity, or movement after different server/world/input histories.

## Inputs and immutable hashes

Exact source roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.8.5,1.8.6,1.8.7,1.8.8,1.8.9}/ornithe-feather/`, mapping family Ornithe Feather Gen2 build 2. Source identities and manifest hashes are given in the inventory report: 1.8.5 `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce`; 1.8.6 `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9`; 1.8.7 `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef`; 1.8.8 `588f83dc64ce0bdf0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1`; 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.

| Input | SHA-256 |
|---|---|
| `worker-185-189-complete-java-inventory/inventory.md` | `0731eac7a0a6452a53fc0baefb5fb1178a856308e9046f0c6f5a2a60dfe58961` |
| `worker-185-189-complete-java-inventory/adjacent-source-hashes.tsv` | `14e9dfe46e4ee15f898f36959028a0f66ad7b225d1e6e8b879cea3f130fbea6f` |
| `worker-185-189-inventory-review/review.md` | `c023d4d8cd775148c07d6e203ce04118d2dd7cfe49a20e3d4bc366923a3ca81d` |
| `worker-185-189-independent-tick-audit/audit.md` | `c8d8d280b5a2a19a5eb06824f99ebecbd649f79975940e2958f84cc56e277b02` |
| `worker-185-189-independent-tick-audit/callback-effect-equipment-supplement.md` | `d6d91b73b6df2c085b2ac8d92e724d84c07aa0305890c05a4d4e55eed130c458` |
| `worker-185-189-collision-inventory/report.md` | `a4587bbbf579d09ec852fc58656c3b2a1f2d46ff20b0a655aa1dcf9843ebe509` |
| `worker-185-189-collision-inventory/closure-correction.md` | `fb585b671b1aa9a3094cdfe5ef994579c35fed71a6a98827cc54957af794b240` |
| `worker-fullfreeze-185-189/audit.md` | `d3e654f4df75eac640967396b515bed75ce4f07d91344e6fb8ac5cda6dcdb432` |
| `worker-185-189-resource-bootstrap/audit.md` | `281d8ef34b9da7925991b0226230fc7e56e0d021f7fbd4d2591f0b1134846746` |
| `185-189-group-closure/closure.md` | `8e1d55b826a0221c9a0d53942334dac693e8d4b9ae158e5531fb75463e16f257` |
| `185-189-group-closure/resource-registration-supplement.md` | `5533875a8ebe54a180d9b45d7b5cdfb11895c55e1f53e886c24decf628b96493` |
| `185-189-group-closure/resource-correction.md` | `e1c11425af54a812ac2abdc426dae78f1e5ca6ae6af3ce8b15b2039de4215f1f` |
| `185-189-group-closure-review/review.md` | `1e6060e885ae7318b87e46b0bb8e4d0a656f169e1edc8f3fe6dc470f68809565` |
| `185-188-method-hash-sweep/report.md` | `d07773bc740feebde32a76f035556c23ab5496bab27e7e479d306fe85c96a7fb` |
| `185-188-method-hash-sweep/movement-core-sha256.corrected.tsv` | `54f735feddba839f19527ae7b9bee36d2f9000e5dda42cb7b36fe7b1c96814e6` |
| `185-188-method-hash-sweep/correction-note-v2.md` | `cb2c1084d9742ec75f403cd27966d59504fa759b1433bbea0630952068377d63` |
| `185-188-method-hash-sweep-audit/audit.md` | `336ed48262297ad046847c8d5a84d7fd234083461786943c81a3da6b19da84aa` |
| `188-189-movement-bridge/boundary-audit-1.8.8--1.8.9.md` | `06247fa2ec619c98c56b8d774a6340ed839edf7213ac35e1b9e013e2b221a97a` |
| `188-189-movement-bridge/changed-source-hashes.csv` | `46f89d86ca6d2b4a4d6e41e30b4e53f905550328bc553d33425ce04a8bef90a2` |
| `188-189-movement-bridge-audit/audit.md` | `19023d9ab5d805561b9e66640a48778c0c9a3a36661ebf23b8c127eb23363598` |
| `188-189-ordinary-movement/report.md` | `f2357c93a2e327a62f14d20e1fa68e1891e8efdad15b25bbb1c46ccb4b1fe86d` |
| `188-189-ordinary-movement-audit/audit.md` | `edba68edfeba36c16850957617a73bf9945b704df88f505b338f842697bd7de6` |

Reviewer report SHA-256 is to be recorded after this file is finalized.
