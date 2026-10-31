# Three-release exact source path inventory and boundary triage

## Scope and source identity

This is a read-only Mojmap source inventory for 1.16.5, 1.17, and 1.17.1, with exact boundary comparison triage. It is not a full-pair acceptance or runtime parity verdict. Sources are under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.16.5,1.17,1.17.1}\mojmap`.

The 1.16.5 parent sidecars are `mojmap.provenance.json` SHA-256 b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d, `mojmap.ready.json` e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee, `mojmap.sources.sha256` 9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b, and `artifacts.sha256` f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c. The 1.17.1 parent sidecars are `mojmap.provenance.json` SHA-256 de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333, `mojmap.ready.json` c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b, `mojmap.sources.sha256` 93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b, and `artifacts.sha256` e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa.

The embedded 1.17 `mojmap/.decompile-provenance.json` SHA-256 is afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357; its `sources` map has 4141 entries and all current source hashes matched (mismatches: 0). Exact counts are 3,523 / 4,141 / 4,142 Java files. The full ordinal case-sensitive three-release path/hash/status table is `case-sensitive-java-inventory-3way.tsv`; every Java source file in all three exact trees was hashed for that table.

## Whole-tree path counts

- 1.16.5 → 1.17: 4205 union paths; same 1,047, changed 2,412, added in 1.17 682, removed after 1.16.5 64.
- 1.17 → 1.17.1: 4144 union paths; same 4,010, changed 129, added in 1.17.1 3, removed after 1.17 2.

The counts are case-sensitive paths and content hashes, not semantic member coverage. Added/removed paths include renames and new classes, which require source-level triage before attributing behavior.

## Movement member triage at 1.17 → 1.17.1

### Direct player operation candidate

`net/minecraft/world/entity/LivingEntity.java`, `getJumpBoostPower()` and `jumpFromGround()` also change at this boundary. 1.16.5 and 1.17 store the float base jump power plus float boost addition; 1.17.1 adds the float-derived boost helper to the base term in a double and writes that double to Y velocity. `LivingEntity.aiStep()` dispatches the jump under the existing gate, and Player delegates to the superclass. A jumping player with Jump Boost supplies the reachable effect path; there is no new gate. This is a source-level velocity-write difference, not a runtime trajectory result.

`net/minecraft/world/entity/Entity.java`, `updateSwimming()` changes only on the not-already-swimming branch. Mojmap 1.17 permits `isSprinting() && isUnderWater() && !isPassenger()`; 1.17.1 adds `level.getFluidState(blockPosition).is(FluidTags.WATER)`. The already-swimming branch is unchanged. `Entity.tick()` calls fluid-contact updates, eye-fluid update, then `updateSwimming`; `isUnderWater()` is the conjunction of prior eye-water and water-contact flags. Player/LocalPlayer inherits the base update unless flying, and the player travel path consumes `isSwimming()`. This is an exact 1.17.1 boundary candidate, conditional on a reachable state where the player satisfies the old gates while the center feet block's fluid is not WATER. The concrete vanilla fixture and independent audit are still required; do not claim a runtime trajectory.

### Other changed paths and member dispositions

| Path/member | Paired source result and disposition |
| --- | --- |
| `Entity.java` `load(CompoundTag)` | 1.17.1 clamps loaded Y to `[-2.0E7, 2.0E7]`; 1.17 does not. Inspected callers are `EntityType.loadEntityRecursive`, reached by server `PlayerList` / summon / entity-persistence flows. No client LocalPlayer load caller is in this graph; client position corrections use packet handlers. This is server/state persistence input, outside local historical movement execution. |
| `LivingEntity.java` `jumpFromGround()` / `getJumpBoostPower()` | Concrete 1.17.1 boundary: 1.16.5 and 1.17 use a float sum; 1.17.1 extracts the boost helper and adds the base to it in double before writing Y velocity. Reachable through the player jump gate with Jump Boost active. |
| `Entity.java` `push(Entity)` | The `Mth.sqrt`→`Math.sqrt` expression change is already in 1.17 and unchanged in 1.17.1; the cutover belongs to 1.16.5→1.17. |
| `LocalPlayer.java` `drop(boolean)` / `Player.java` `drop(boolean)` | Inventory removal and drop method ownership move between classes; no player position, velocity, box, movement gate, input or direct response is written by this path. Item-entity physics is excluded. |
| `ClientPacketListener.java` | 1.17.1 changes entity-removal packet pluralization, inventory/menu synchronization state IDs, and missing-team handling. The movement-position and entity-motion response members are bounded separately in `received-responses/coverage.md`; no changed position/motion math was found there. |
| `ItemStack.java`, `EnchantmentHelper.java` | 1.17.1 extracts enchantment NBT helpers and clamps levels when reading generic enchantment maps. Existing movement lookups such as Depth Strider/Soul Speed use the item-specific or entity-specific level reader, already clamped in 1.17. Generic `getEnchantments(ItemStack)` source consumers are non-player AI in the inspected tree. The assigned item/modifier audit covers exact paths and any remaining call-edge gap. |
| `CauldronBlock.java`, `LayeredCauldronBlock.java` | Precipitation-state production/random gate changes; the cauldron has no movement collision response path. World state production is outside this client's movement procedure. |
| `DoublePlantBlock.java`, `TallGrassBlock.java` | Placement/growth now copies waterlogged state; these are state-production calls. No movement collision or shape member changed. `SmallDripleafBlock` is a 1.17-introduced feature, outside 1.16.5 historical scope; the 1.17.1 boundary retains its shape method. |
| Other changed `world/entity` classes | Remaining changed paths are mob AI, animals, projectiles, inventory, or non-player entities, outside player-only physics; base `Entity`/`LivingEntity`/`Player` members are individually listed above. |
| Other changed `world/level` classes | Chunk persistence, generation, lighting, skull, and world storage are not local-player movement operations; entity persistence's `Entity.load` reachability is listed separately. |

## Pair coverage boundaries

The three-way path inventory is complete for Java paths and current content hashes. Movement-member work is represented in the disjoint tick, received-response, provider/callback, swim-gate, and item/modifier source inventories as those finish. These slices provide evidence for endpoint and adjacent-version boundaries but do not yet constitute an independent full movement-terminal inventory audit. Still open: full exact changed-member closure across the player tick and external state/provider/callback graph, closure of any additional direct player response producers, and independent blind full-pair audit. No builds, tests, launches, decompilation, product code/history, wiki, or research/backlog were consulted.
