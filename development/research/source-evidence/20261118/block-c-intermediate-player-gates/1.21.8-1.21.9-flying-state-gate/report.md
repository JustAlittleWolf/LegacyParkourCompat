# 1.21.8 to 1.21.9: player flight-state transitions

## Bounded result

Source review of the assigned local-player ability-flight transitions and fall-flight start/reset gates found no change in the ordered gates or writes between the two source trees. This closes only the named method slices provisionally; it is not a pair freeze because 1.21.9 has no adjacent `mojmap.ready.json` marker.

## Exact source identity and readiness

- Older: Minecraft `1.21.8`, Mojang mappings (`mojmap`). `decompiled_minecraft/1.21.8/mojmap.ready.json` says `ready`, version `1.21.8`, mapping `mojmap`; SHA-256 `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`. Its wrapper provenance (`mojmap.provenance.json`, SHA-256 `90fcb3707371f2cfc5414e0089cfa231f505821d7aca45211c7c816332bb4cfa`) records an exact run for `1.21.8,1.21.10,1.21.11`; the source manifest SHA-256 is `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`.
- Newer: Minecraft `1.21.9`, Mojang mappings (`mojmap`). The mapped source directory has `.decompile-provenance.json` (SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`), but there is no `decompiled_minecraft/1.21.9/mojmap.ready.json` or sibling source-manifest/ready wrapper. Its provenance pins 1.21.9 metadata JSON hash `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`, client JAR hash `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`, and client mappings hash `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`. The three relevant source files' actual SHA-256 values match their entries in this source-hash map, but this does not establish full source-tree publication readiness.

Relevant source-file SHA-256 (actual bytes):

| File | 1.21.8 | 1.21.9 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |

## Compared slices and findings

- `LocalPlayer.aiStep`, 1.21.8 lines 746–768, 770–774, 787–800, 830–834; 1.21.9 lines 750–772, 774–778, 791–804, 834–838. The `mayfly` and always-flying gates, jump-trigger timer (`7`), swimming veto, flight toggle, grounded jump on enable, ability update notification, fall-flight request veto/order, `wasFallFlying` capture, controlled-camera vertical input and grounded reset are textually and operationally the same. No ordinary already-flying or dry-state transition delta found in these slices.
- `Player.tryToStartFallFlying` / `startFallFlying`, 1.21.8 lines 1519–1530; 1.21.9 lines 1376–1387. Both attempt only when not already fall-flying, `canGlide()` and not in water, then write shared flag 7 true and return true. The `Player.canGlide` override at 1.21.8 lines 1464–1466 and 1.21.9 lines 1321–1323 keeps the same `!abilities.flying && super.canGlide()` veto.
- `LivingEntity.travelFallFlying` / `stopFallFlying`, 1.21.8 lines 2353–2372; 1.21.9 lines 2383–2402. The climbable veto still switches to air travel and calls the same reset sequence; `stopFallFlying` writes shared flag 7 true and then false in both. This review did not reopen climbable geometry.
- `LocalPlayer`'s fall-flight edge cache (`wasFallFlying`) and shared-flag callback, 1.21.8 lines 142, 512; 1.21.9 lines 140, 516, retain the same edge test and cache update. The ability-to-water direct predicate `Player.isAffectedByFluids()` is also unchanged (`1.21.8` lines 1033–1035; `1.21.9` lines 886–888); downstream fluid/contact responses were not reviewed.

## Bounded adjacent dependency

1.21.9 adds `isSprintingPossible(boolean)` and passes `getAbilities().flying` from sprint start/stop decisions (`LocalPlayer` lines 841–848, 1055–1068). Its parameter gates `!isInShallowWater()` at line 1059. This is an actual flying-flag predicate difference at a shallow-water gate, so its fluid-dependent consequence is outside this assignment and remains for the fluid owner to reconcile. No broader sprint/modifier lifecycle claim is made here.

## Limits

No wiki, product implementation, product Git history, tests, launches, source generation or network access was used. The missing 1.21.9 ready marker is the open evidence-readiness gate. These observations do not establish full-pair coverage, independent audit, exact patch introduction beyond this pair, or runtime parity.