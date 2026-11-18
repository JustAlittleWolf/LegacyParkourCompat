# 1.21.10 to 1.21.11 equipment source comparison

## Inputs and pin verification

- Exact pair: 1.21.10 -> 1.21.11; mappings: Mojmap on both sides.
- Canonical read-only roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.10/mojmap/` and `.../1.21.11/mojmap/`.
- Both `mojmap.ready.json` records say `status=ready`, their exact `versionId`, and `mapping=mojmap`. Each ready record's SHA-256 for `mojmap.sources.sha256` and `artifacts.sha256` matches the corresponding manifest. The two source manifests are distinct; the shared artifact manifest SHA-256 is `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`.
- Provenance manifests identify Mojmap, mapping I/O 0.9.1, Vineflower 1.12.0 and Java 25.0.3+9-LTS; their version IDs match the ready records.
- Every source file named below was SHA-256 checked against its version's `mojmap.sources.sha256` manifest. Exact hashes are included in the evidence table.

| Version | Source-relative file | SHA-256 |
|---|---|---|
| 1.21.10 | `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| 1.21.10 | `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| 1.21.10 | `net/minecraft/world/item/Item.java` | `564e768acd45f794416ecd16948524ce76f2abeecc0440e3763493b0ce5d26ef` |
| 1.21.10 | `net/minecraft/world/item/Items.java` | `2331d554a1365defd3e89c033e3321bec64cdcd9e5c92764744a788c376a2ce8` |
| 1.21.11 | `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| 1.21.11 | `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| 1.21.11 | `net/minecraft/world/item/Item.java` | `53e371efb8ef6eca5e8585a4598b3251dc37bd005b6d4c068c1f8c67d4efeee3` |
| 1.21.11 | `net/minecraft/world/item/Items.java` | `4abbdcd5f3ba6d906d80e1bf3b42610fc0afbfa87a13b4be7d9bb579a429e1ad` |
| 1.21.11 | `net/minecraft/world/item/component/UseEffects.java` | `bb69726562d4f68006732a159abceb04e63685e8c05d2338b15d69a53f60bb6f` |

## Bounded movement coverage

Compared the local input conversion and held-item speed source, its application order, and its first movement consumer. `LocalPlayer.applyInput` passes `input.getMoveVector()` through `modifyInput`, then writes the result to `xxa` and `zza` (1.21.10 lines 612-624; 1.21.11 lines 653-665). `LivingEntity.aiStep` forms the travel vector from those fields and calls `travel`; the dry-air path reaches `handleRelativeFrictionAndCalculateMovement` and then `moveRelative` (1.21.10 lines 2840-2849 and 2476-2478; 1.21.11 lines 2966-2975 and 2534-2536).

For equipment-derived movement attributes, jump strength and gravity, checked the paired `LivingEntity`, `Player`, `Entity`, item attribute component and attribute-map/instance/ modifier sources. No corresponding movement calculation or operation-order difference was found in those bounded methods. Attribute identifier/type migrations and non-movement item-attribute display math were not treated as movement changes. This is not a full pair freeze or audit of all tick branches.

## Positive difference

`LocalPlayer.modifyInput` still returns zero input unchanged and first scales nonzero input by `0.98F` in both releases. The following step differs: 1.21.10 scales by fixed `0.2F` when `isUsingItem() && !isPassenger()`; 1.21.11 calls `itemUseSpeedMultiplier()` under the same gate. That helper reads `this.useItem`'s `DataComponents.USE_EFFECTS.speedMultiplier()`, defaulting to `UseEffects.DEFAULT`, whose multiplier is `0.2F` (UseEffects.java lines 9-26). Sneaking-speed scaling and square-movement adjustment remain after this step in the same order.

There is a source-confirmed reachable witness in 1.21.11: `Item.Properties.spear` supplies `new UseEffects(true, false, 1.0F)` (Item.java lines 463-509), and `Items` registers the wooden through netherite spears through that builder (Items.java lines 1897-1916). During ordinary dry, non-riding local movement while using a spear, directional input therefore keeps its full 1.0 use multiplier rather than the 1.21.10 hardcoded 0.2 multiplier, before the later sneak and square-input stages. These spears are newly registered in 1.21.11; no claim is made that the old release had the same item or resource content.

## Residuals

- This batch closes only the equipment/attribute and held-item input slices described above. It does not close the pair's full locomotion inventory or independent audit.
- Elytra, abilities, sprint/sneak state transitions, effects, fluids, collision, portals, vehicles, timers, rotation, networking, and environmental/resource content were outside this assignment. Sprint behavior was not included in the finding; `UseEffects.canSprint` is intentionally not analyzed here.
- No tests, builds, launches, product files, research index/backlog, or source trees were modified.
