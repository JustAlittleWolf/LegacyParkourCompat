# Independent audit: 1.15.1 → 1.15.2 candidate resource dependency

Verdict: **ACCEPT — bounded `NO_PACKAGED_RESOURCE` disposition for the two reviewed movement candidates.** This verdict is bound to report SHA-256 `A6BCBE4A99275E07A654478051D9DD2C3F2F51C7E2F591BB5E010A38A9DD570B`.

## Bound evidence and exact source identity

The candidate-resource report bytes hash to the assigned SHA-256. Its linked source report (`ordinary_player/source-report-r2.md`) hashes to `322E867BEA50AF506E4438F4809D3C893B5E2897B656F02C0855D41A43274D2B`; its citation-correction audit hashes to `ordinary_player/audit-r2/verdict-r2.md` SHA-256 `710103010D2473E0F9C9D3ECBD0051222F6CAD00D1382A90E91AC5B74104F0E3`. The Elytra applicability packet hash is `594626744739A9DE195E85BA205230EABC8119714779BCDEB2C56C11580631F3`; the effect-restoration applicability packet hash is `EE06CC078BCD7901B9F5B12740F94A401E226A87C4933A836F623C893052FC39`.

Both endpoints are exact Mojmap trees. The 1.15.1 ready marker, source manifest, and provenance hashes are `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504`, `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`, and `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461`. The 1.15.2 values are `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`, `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`, and `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`. Disk hashes recomputed from the 11-file table in the candidate-resource report match all 22 endpoint manifest entries; zero mismatches.

The candidate report’s additional built-in registry / command support sources were checked against both endpoint manifests and disk bytes. These eight paired files are identical across endpoints:

| Source | SHA-256 at both endpoints |
|---|---|
| `net/minecraft/world/item/Items.java` | `6315626ed87b078d65b190ae534de2f6f9fa6a58745ac8fa3f0e8fe1c21aebbc` |
| `net/minecraft/world/item/Item.java` | `fa02b36b2db6ccdc7014cb862ac3e06d2b82f82079e8816f48ff9c082f011613` |
| `net/minecraft/world/level/block/Blocks.java` | `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9` |
| `net/minecraft/world/level/block/TrapDoorBlock.java` | `0de8aeb2981cefcbf8fb296e08401668e5fcaf3e003419bf1d45d56a06d81b86` |
| `net/minecraft/world/level/block/LadderBlock.java` | `36014d99536a74a5761cd11cfa3c1ab6ac6aa6947609808852679dddb5625108` |
| `net/minecraft/world/level/block/VineBlock.java` | `3cf8e12e44c88728cd86c3191055df05b81562d71bcf5d977a935fada43f8a5d` |
| `net/minecraft/world/level/block/ScaffoldingBlock.java` | `9a9e7a7e26e2b001a285e3944178b94b1ff149b5000d3a0a5b9b719392662d84` |
| `net/minecraft/server/commands/EffectCommands.java` | `97864d37c315ac1040bed12d49dd2dba355954548fce4a3e2d3d2b1d3b93086e` |

## Candidate dependency audit

**Elytra ladder start.** The movement call path in `LocalPlayer.aiStep()` reads the jump input and player flags, then calls `onLadder()` as the only changed gate before the existing Elytra path. `LivingEntity.onLadder()` reads the current `BlockState`, compares it with built-in `Blocks.LADDER`, `Blocks.VINE`, and `Blocks.SCAFFOLDING`, or reads `TrapDoorBlock.OPEN` / `FACING` and the lower built-in ladder block. The remaining trigger reads the chest-slot `Items.ELYTRA`, `ItemStack` damage, and the direct `ElytraItem.isFlyEnabled()` predicate. `Items`, `Blocks`, and block/item classes are Java static registrations with source-checked hashes above; this candidate gate contains no `BlockTags`, `ItemTags`, tag membership, JSON lookup, or resource-manager read.

`ElytraItem` registers the `ResourceLocation("broken")` property used by the item-model predicate system. The local movement branch calls the static `ElytraItem.isFlyEnabled()` method directly; it does not evaluate that model property or load its resource-backed model predicate. Thus the model property is not a packaged resource input to this movement candidate.

**Effect restoration.** `MobEffectInstance.update()/tick()` retains/restores in-memory `MobEffect` references plus numeric duration/amplifier/visibility fields. `LivingEntity.tickEffects()` invokes its update callback when the hidden effect is restored, and `onEffectUpdated()` removes/reapplies modifier entries from the built-in `MobEffect` object into `AttributeInstance`. `MobEffects.MOVEMENT_SPEED` and `MOVEMENT_SLOWDOWN` are statically registered in Java with hard-coded `SharedMonsterAttributes.MOVEMENT_SPEED` modifiers; modifier magnitude comes from the source-defined amplifier calculation. The enabled `/effect` command supplies target, effect, duration, and amplifier through `EffectCommands` and the built-in effect registry. The client packet handlers resolve the effect/attribute packet fields and mutate in-memory entities/attributes; the local player reads its movement-speed attribute. These candidate operations do not load JSON, tag payloads, data-driven effect definitions, or other packaged resource files.

The effect instance’s NBT and packet data, the player’s current block state, item damage and attribute values are dynamic state inputs. They are not packaged resource payloads and are not treated as such by this bounded report.

## Disposition and limits

`NO_PACKAGED_RESOURCE` is accepted only for the Elytra ladder trigger and movement-effect restoration candidates listed above. No resource extraction is required to establish these two candidate dependencies. This does not close pair-wide resource/tag/provider coverage: the accepted exact-pair source report still leaves broader operations, dependencies and full-pair coverage open. No datapack-state, runtime, trajectory, exact patch-onset, or pair-freeze claim is made.
