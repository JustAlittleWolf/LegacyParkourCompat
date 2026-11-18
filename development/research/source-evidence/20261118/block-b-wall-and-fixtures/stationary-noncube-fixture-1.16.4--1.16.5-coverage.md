# 1.16.4–1.16.5 stationary fixture: noncube movement geometry

## Verdict

Bounded equivalence; no eligible difference found in the eight assigned providers. No individual finding draft is warranted. This does not freeze the full release pair or certify movement parity.

## Evidence identity and integrity

- 1.16.4: `decompiled_minecraft/1.16.4/mojmap`; SHA-256 of `.decompile-provenance.json`: `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` (verified). Its official client mapping input `client.txt` is recorded as `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe` in the provenance inputs.
- 1.16.5: source directory `decompiled_minecraft/1.16.5/mojmap`; `mojmap.ready.json` identifies version `1.16.5`, mapping `mojmap`, 3,523 source files and Vineflower/Java 25. Its SHA-256 is `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee`. SHA-256 of `mojmap.sources.sha256`: `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` (verified). The artifact manifest records `1.16.5/client_mappings.txt` SHA-256 `7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c`.
- All assigned provider classes and the shared selector/lookup sources listed below have intact decompiled bodies and byte-identical SHA-256 content across the pair. For 1.16.5, provider source hashes match the corresponding source-manifest entries. There is no missing or damaged source gate in this slice.

## Paired selectors, boxes and registered blocks

Line numbers are identical in both releases because each paired source file has the same SHA-256.

| Provider | Paired source and lines | Selector / occupied collision geometry | Registered block IDs |
|---|---|---|---|
| Cauldron | `net/minecraft/world/level/block/CauldronBlock.java` 38–53 | Fixed `SHAPE`: `Shapes.block()` minus the joined rim/bottom interior forms; ignores `LEVEL`. `getShape` returns `SHAPE`. | `cauldron` (`Blocks.java` 1003–1006). |
| Hopper | `HopperBlock.java` 36–44, 57–71 | Selects by `FACING`: DOWN/NORTH/SOUTH/WEST/EAST shapes. Shared base is union of top and funnel, minus `Hopper.INSIDE`; a directional outlet box is unioned onto it. Default fallback is `BASE`; valid registered states use the five listed directions. `ENABLED` does not select shape. | `hopper` (`Blocks.java` 1297–1302). |
| Anvil | `AnvilBlock.java` 32–40, 73–76 | Selects X-axis union or Z-axis union from `FACING` axis. Each is the same base + two legs + top arrangement with the axis-oriented coordinates shown at lines 32–40. | `anvil`, `chipped_anvil`, `damaged_anvil`, each constructed as `AnvilBlock` (`Blocks.java` 1232–1259). |
| Brewing stand | `BrewingStandBlock.java` 33, 51–53 | Fixed union of `(1,0,1)-(15,2,15)` plate and `(7,0,7)-(9,14,9)` stem; bottle flags do not select collision geometry. | `brewing_stand` (`Blocks.java` 997–1002). |
| Grindstone | `GrindstoneBlock.java` 29–92, 105–145 | `getVoxelShape` selects a union for `FACE` (FLOOR/WALL/CEILING) and `FACING`; collision returns that result. The static boxes define each orientation's two posts/pivots and grindstone body; each union is fully within this class's lines 29–92. Both collision and outline call the same selector. | `grindstone` (`Blocks.java` 2693–2698). |
| Stonecutter | `StonecutterBlock.java` 31, 65–67 | Fixed box `(0,0,0)-(16,9,16)`; no state selector. | `stonecutter` (`Blocks.java` 2703–2705). |
| Enchantment table | `EnchantmentTableBlock.java` 30, 42–44 | Fixed box `(0,0,0)-(16,12,16)`; no state selector. | `enchanting_table` (`Blocks.java` 993–996). |
| Lectern | `LecternBlock.java` 40–68, 108–125 | Collision returns fixed `SHAPE_COLLISION`, the base/post union plus `SHAPE_TOP_PLATE`; state-facing and `HAS_BOOK` selector in `getShape` is outline-only. `SHAPE_TOP_PLATE` is the zero-height box `(0,15,0)-(16,15,16)` as decompiled. | `lectern` (`Blocks.java` 2699). |

For each row, the listed source member and ranges are paired-identical between 1.16.4 and 1.16.5. The `Blocks.java` registry source is also byte-identical (SHA-256 `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`). The only multi-ID alias group directly confirmed in the assigned roster is the three AnvilBlock IDs. This slice records the direct `CAULDRON` registration and does not infer behavior for other cauldron-family blocks.

## Shared collision gates and local player reachability

These paired-identical sources establish that the providers are on the ordinary local entity collision path without depending on support probing or step-up logic:

- `BlockBehaviour.java` 524–529: context-free state lookup supplies `CollisionContext.empty()`; contextual lookup dispatches to the block's `getCollisionShape`.
- `CollisionContext.java` 10–15 and `EntityCollisionContext.java` 15–20, 26–60: empty context uses the empty entity-context singleton; entity context derives from an entity and carries its downward/height/item/fluid predicates. These fixture providers do not read the context.
- `Entity.java` 666–679 and 713–729: ordinary entity movement's collision helper is reachable from `Entity.move`; a non-riding player takes this path. `CollisionSpliterator.java` 33–45, 68–84: for non-null entity it constructs `CollisionContext.of(entity)` and calls state `getCollisionShape` for candidate block states. `CollisionGetter.java` 57–62 routes block lookup through this spliterator. This confirms a player movement collision lookup independent of the support/step candidate path.

Shared selector/lookup files were pair-identical as well: `BlockBehaviour.java` SHA-256 `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b`; `CollisionContext.java` `8d9e84cf4fdc62cc1a4fc437f828ddccfcc0a2cc9bddd1fd1f621e178beff59a`; `EntityCollisionContext.java` `75e9d0bde099b69f2d388210d82c4ef2ac6e5f7a8e1bea64e297472bbea52418`; `CollisionGetter.java` `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`; `CollisionSpliterator.java` `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`; `Entity.java` `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.

## Boundaries and open gates

This report covers only these eight stationary provider families, their fixed-state shape selectors and registration aliases, empty/entity collision-context defaults, and ordinary local non-riding collision lookup reachability. Support probing, stepping, callbacks, state production, fluids, moving blocks and unlisted providers were excluded by assignment. No trajectory delta is predicted because no source difference was found. Pair-wide movement inventory, independent audit, acceptance, implementation and runtime parity remain open.

