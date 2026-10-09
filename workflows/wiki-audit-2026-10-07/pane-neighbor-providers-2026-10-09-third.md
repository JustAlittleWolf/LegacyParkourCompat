# Pane neighbor-provider slice 3: 1.8.9 to 1.9.4

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-third`

Status: three additional shared registrations classified from exact source; independent review pending. This additive slice does not close the registered-provider census or all world-reachable pane masks. No runtime validation was performed.

## Source identity and revision lineage

This slice uses the canonical ready roots `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready records report `ready`. The source-manifest SHA-256 values remain `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (1,612 files) and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` (1,819 files). Artifact-manifest hashes remain `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

Verification records report identical source trees and raw input artifacts. Original derived JARs remain unavailable; regenerated immutable JARs do not establish equivalence with those originals or prove that the recorded hash difference is metadata-only. Preserve the limitation recorded in the [preceding source memo](pane-neighbor-providers-2026-10-09-next.md).

Every source-file SHA-256 below was recomputed from the exact ready tree and matches its source-manifest entry.

| Source | Relevant range | SHA-256 |
|---|---|---|
| 1.8.9 `Block.java` | `isOpaque()` 154–156; constructor 198–207; `setOpacity()` 218–221; base `isSolidRender()` 352–354; registrations 1190–1195 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | neighbor resolution 100–108; predicate 134–140 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `PrismarineBlock.java` | constructor/default variant 15–25 | `45eea93882fe94012fbad98ab1f119dc5628f44e2ee0abfd012853fa854daf8f` |
| 1.8.9 `SeaLanternBlock.java` | class/constructor 12–16 | `32de8657db032566276de91396b3aa394179c93bf48da13d24dc34cd408ffdc6` |
| 1.8.9 `CarpetBlock.java` | default state and constructor 18–29; `isSolidRender()`/`isCube()` 35–43 | `7b3f5725008ede42fd23d5fab0cbdcfc27736ce9c71094bd56cf9d03c2458a01` |
| 1.9.4 `Block.java` | constructor 174–181; `setOpacity()` 193–196; base `isSolidRender(state)` 357–359; base `isCube(state)` 222–225; registrations 1109–1116 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | neighbor resolution 104–110; predicate 133–140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `PrismarineBlock.java` | constructor/default variant 15–25 | `45eea93882fe94012fbad98ab1f119dc5628f44e2ee0abfd012853fa854daf8f` |
| 1.9.4 `SeaLanternBlock.java` | class/constructor 12–16 | `32de8657db032566276de91396b3aa394179c93bf48da13d24dc34cd408ffdc6` |
| 1.9.4 `CarpetBlock.java` | default state and constructor 19–28; `isSolidRender(state)`/`isCube(state)` 40–48 | `df593afbe1972c6b04997a17703a0f7278ce473114428b74acc9d2df3d96e791` |
| 1.9.4 `StateDefinition.java` | resolved state's `isCube()` delegates at 268–269 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

## Predicate and mask dispositions

In 1.8.9, `PaneBlock` tests `block.isOpaque()`, which returns `opaqueCube`, cached by the `Block` constructor from virtual `isSolidRender()`. In 1.9.4, it tests `block.defaultState().isCube()`; the state definition delegates to `block.isCube(state)`. Both pane resolvers examine four cardinal directions, using the provider block's default state in the new predicate.

| Registration | 1.8.9 `isOpaque()` | 1.9.4 default-state `isCube()` | Registration/default/state dependency | One-east-neighbor pane mask |
|---|---:|---:|---|---|
| `prismarine`, ID 168 (`PrismarineBlock`) | true | true | Both registries instantiate the same class with `Material.STONE`; default `VARIANT=ROUGH`. It inherits base `isSolidRender()`/`isCube()` true in 1.8.9 and base state-taking equivalents in 1.9.4. | E in both versions; no changed mask. |
| `sea_lantern`, ID 169 (`SeaLanternBlock`) | true | true | Both registries instantiate `SeaLanternBlock(Material.GLASS)`. The class declares no block-state properties or opacity/cube overrides; it inherits base solid-render and cube behavior. Material translucency does not change either selected predicate. | E in both versions; no changed mask. |
| `carpet`, ID 171 (`CarpetBlock`) | false | false | Both defaults use `COLOR=WHITE`. The class returns false for old `isSolidRender()` and new `isCube(state)`. The registry calls `setOpacity(0)`; that setter changes `opacity` only, while old `isOpaque()` returns the separate cached `opaqueCube` value, already false. | No E arm in either version; no changed mask. |

No provider in this slice changes the pane predicate result. These are predicate/default-state dispositions only; this report does not classify collision geometry or state-update/placement lifecycles.

## Remaining census and next slice

Three additional registrations are classified here. The remaining era-existing registry entries still require old-opacity versus new default-state-cube classification and directional-mask coverage; this is not a full census.

Next bounded shared-registration slice, selected by registry ID and not evaluated here: `hardened_clay` ID 172, `double_plant` ID 175, and `standing_banner` ID 176. Their constructors, default-state dependencies, predicates, and reachable masks remain open. Explicit pane/glass allowlist cases remain separate, and blocks without a 1.8.9 counterpart remain outside this shared comparison. Independent review, complete reachable-mask coverage, and original derived-JAR equivalence remain open.
