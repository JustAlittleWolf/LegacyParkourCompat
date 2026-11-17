# Source comparison: 1.15.1 → 1.15.2

Status: **PARTIAL — source author report; independent audit and full pair closure remain open.** This report covers only the exact adjacent Mojmap source pair. It does not infer an onset outside this edge and does not reuse the frozen 1.14.4→1.15.2 endpoint comparison.

## Bound inputs

| Endpoint | Exact root | Ready identity | Source manifest | Provenance |
|---|---|---|---|---|
| 1.15.1 | `decompiled_minecraft/1.15.1/mojmap/` | `mojmap.ready.json` SHA-256 `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504`; `versionId=1.15.1`, `mapping=mojmap`, `status=ready`, 3,329 sources | `mojmap.sources.sha256` SHA-256 `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `mojmap.provenance.json` SHA-256 `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` |
| 1.15.2 | `decompiled_minecraft/1.15.2/mojmap/` | `mojmap.ready.json` SHA-256 `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`; `versionId=1.15.2`, `mapping=mojmap`, `status=ready`, 3,332 sources | `mojmap.sources.sha256` SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `mojmap.provenance.json` SHA-256 `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` |

The sidecars at the paths and hashes above were read directly. Both ready markers bind their exact version ID, Mojmap namespace, and source-manifest hash. The input locator `build/source-inputs-115-116-20261117/README.md` identifies official mapping artifact hashes `f46186d0ed9d17e073e16ae2c98675dbbc70b4a29495700837aa5de9c1b42c34` and `65ad295b6cf63821f5d8f961c128128475e6d39386358d22eb238a3ce6e31777`, respectively; locator scope is input readiness only.

The comparison used the two exact source manifests to enumerate path/hash differences (3,329 old paths, 3,332 new paths: 89 changed paths, 4 added, 1 removed); changed Java bodies in the bounded movement/player/dependency areas below were inspected side by side. Source trees remained read-only. Listed Java hashes below are bound by those source manifests; the key movement files were also directly SHA-256 checked on disk.

## In-scope source differences found

### Elytra start eligibility while climbing

`LocalPlayer.aiStep` adds `&& !this.onLadder()` to the jumping-driven Elytra start condition. The exact 1.15.1 line 715 condition checks jump input, two preexisting local gates, not flying, and not passenger; 1.15.2 adds the ladder predicate immediately before entering the unchanged chest-slot/Elytra enabled/`tryToStartFallFlying()` checks and sending `START_FALL_FLYING`. Thus the exact pair differs in the client trigger gate: if the earlier guards pass but `LivingEntity.onLadder()` is true, 1.15.1 may attempt the start and 1.15.2 does not. `LivingEntity.onLadder()` is byte-identical in the pair (source SHA-256 `.../world/entity/LivingEntity.java`: `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` vs `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; the files differ elsewhere only due effect ticking and a death guard). It returns false for spectators; otherwise checks ladder, vine, scaffolding, or usable open trapdoor backed by a matching ladder. The downstream Elytra item eligibility implementation is unchanged.

Java hashes: `LocalPlayer.java` 1.15.1 `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823`; 1.15.2 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`. `ElytraItem.java` is unchanged, SHA-256 `701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43` on both endpoints.

### Effect replacement and expiry behavior

`MobEffectInstance` adds a nested `hiddenEffect` chain. When a higher-amplifier effect replaces a shorter-duration active effect, the prior effect is retained; when a lower-amplifier effect has a longer duration, it is stored as hidden (or merged into the existing hidden chain). Duration ticking now decrements hidden durations too. On the active effect reaching zero, the next hidden effect's details are restored and the callback runs. `LivingEntity.tickEffects` supplies `onEffectUpdated(instance, true)` for this restoration; that callback removes and reapplies the effect's attribute modifiers server-side. This creates a movement-relevant source path for effects which modify movement attributes (including the unchanged Speed and Slowness declarations), while their attribute amounts and tick formulas are unchanged. This report makes no claim about item/potion acquisition or a particular world's effect state.

Java hashes: `MobEffectInstance.java` 1.15.1 `80cc2a91b77d4687a9b36bcb38e3107e3b2b8f859c69893e4eec0e98b4941b5e`; 1.15.2 `4999d12cd468e402506f067dbd8694b1c8125dd9131e4f8a2fcc1848f616f180`. `LivingEntity.java` 1.15.1 `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79`; 1.15.2 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`. `MobEffect.java` and `MobEffects.java` are unchanged (hashes `f218a040ff9f77272395245c4ffecc3f2266335600b79be0a5b2823c46ad7dd0` and `472b25ede6bed3ad3ed1090db3310cea9708b637f111981bcf083f492c030412`; see source manifest for full path binding).

## Other changed source in adjacent coverage

- `Player.java` changes sleeping/bed range and nearby-monster AABB center arithmetic only; project scope excludes bed-entry eligibility.
- `ConcretePowderBlock.java` factors the existing solidification/liquid checks into `shouldSolidify` and applies it on landing. The examined change affects concrete powder placement/landing block-state selection, not its collision shape or a direct player response.
- `Level.java` adds profiler counters to entity query methods; query behavior is otherwise unchanged in the inspected hunks.
- `BeehiveBlock`, `BeehiveBlockEntity`, and `Bee` changes concern bee release/anger, item drops, and bee save data; bees are non-player entities and these changes do not alter player movement responses.
- Remaining added/removed/changed paths in the manifest census were renderer/UI, server/level administration, datafix, profiler, world generation/decoration, other entities, or unrelated utility code. No player movement code was identified in them. In particular, `FallingBlockEntity` concerns a non-player falling-block entity.

## Operation coverage read against source identity

| Surface | Pair result in examined source |
|---|---|
| `LocalPlayer` tick/input | Source changed only at Elytra start ladder gate in the inspected player tick path; `Input.java` and `KeyboardInput.java` byte-identical. |
| Player jump, ground/air travel, fluid/climb travel, fall-distance/fall-flying control, direct position/velocity writes | `Entity.java` is byte-identical; `LivingEntity.java` differs only in effect tick callback and the `die` guard (health/damage excluded). `Player.java` only differs in bed logic (excluded). No changed direct player movement writer was observed. |
| Collision lookup and shape dispatch | `CollisionGetter.java`, `BlockGetter.java`, `VoxelShape.java`, `BlockState.java`, `Block.java`, and `FluidState.java` byte-identical. `Level.java` adds counters only to entity-query methods. |
| Movement effect definition / direct attribute implementation | `MobEffect.java`, `MobEffects.java`, and `AttributeInstance.java` byte-identical; MobEffectInstance stacking/restoration differs as detailed above. |
| Elytra item gate | `ElytraItem.java` and `Items.java` byte-identical. |
| Block state provider/shape/resource dependencies | Only inspected concrete-powder solidification and beehive release changes were found in the changed block paths. No changed block collision-shape/provider implementation was found. No resource extraction was performed or authorized by this assignment. |

Representative unchanged manifest-bound hashes: `Entity.java` `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `CollisionGetter.java` `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`; `BlockGetter.java` `7c144c5890245b8189faea94a56c628d469e4c142977645f753deac7f62f9545`; `VoxelShape.java` `5faceea0e4ce9ad0f2a1a2c72a89d9a768ab877c8728ae2968fe34aa1e03d3ea`; `BlockState.java` `77320fd1e50e58d7a3ed593ccbcf21853c97d125fccebde2e0e305dc47524f21`; `FluidState.java` `f88bff7d54ea0220ee67e0fe36a78df67871e52ae636e584ea26dd107f652c15`; `ElytraItem.java` `701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43`; `Items.java` `6315626ed87b078d65b190ae534de2f6f9fa6a58745ac8fa3f0e8fe1c21aebbc`.

## Open gates / disposition

1. This is not an independent audit and is not a pair freeze. A fresh, source-blind auditor must bind to this report's exact SHA-256 before acceptance.
2. The source pass uses manifest path/hash census plus inspection of changed files relevant to player movement and direct dependencies; it is not a second full-tree hash operation. The queue README records complete endpoint file inventories and the previous source-input owner verified each Java file against the manifest. If independent audit identifies another changed path with an in-scope dependency, scope must be reopened for that path.
3. No bundled-resource extraction was authorized or performed. No resource dependency was found for the two identified source deltas; complete resource/provider closure remains an explicit audit gate if an auditor finds a path requiring packaged data.
4. No runtime or map reachability claim is made. The Elytra condition is a source-level eligibility delta; no gameplay outcome was tested.

