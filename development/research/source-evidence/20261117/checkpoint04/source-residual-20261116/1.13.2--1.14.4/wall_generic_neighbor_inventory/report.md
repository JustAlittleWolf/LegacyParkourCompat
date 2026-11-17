# Wall generic neighbor inventory

## Scope and identity

Bounded source-only follow-up to `wall_non_gate_neighbors/report.md`. Exact inputs are the 1.13.2 Ornithe Feather Gen2 source manifest `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and the 1.14.4 Ornithe Feather Gen2 source manifest `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. Source trees were consumed read-only. This report excludes fence-gate behavior and does not claim complete registered-block parity.

## Predicate comparison

- 1.13.2 `net/minecraft/block/WallBlock.java:51–65`: `shouldConnectTo` accepts `!isExceptionForConnection(block) && shape == FaceShape.SOLID`, or its pole-shape arm. For the generic non-gate case, only the first arm applies.
- 1.13.2 `WallBlock.java:57–65` composes `Block.isExceptionForAttachment` with additional exclusions for barrier, melon, pumpkins, frosted ice and TNT. `Block.java:244–256` shows the attachment chain: shulker boxes, leaves, the trapdoors tag, stained glass, beacon, cauldron, glass, glowstone, ice, sea lantern and conduit, plus piston, sticky piston and piston head.
- 1.14.4 `net/minecraft/block/WallBlock.java:48–52` computes `!isExceptionForAttachment(block) && faceSolid || wallOrGate`. Removing the gate arm leaves a generic face-solid path plus `BlockTags.WALLS` membership.
- 1.14.4 `net/minecraft/block/Block.java:260–267` has the narrower exception set: leaves blocks, barrier, carved/lit pumpkins, melon and pumpkin. `Block.java:376–377, 402–410` defines collision-shape-based face solidity and suppresses it for `BlockTags.LEAVES`. `net/minecraft/block/state/BlockState.java:327, 416` shows the state predicate and its cached per-state face values.

The old exception set and target exception set are not equivalent. A same-state difference requires evaluating both the old directional `FaceShape` and the target directional collision face; membership changes alone do not establish a wall-bit difference.

## Resource membership

Reused the already-verified extraction in `wall_resource_membership/extraction-record.md`; no download or extraction was performed here. Target 1.14.4 `leaves.json` SHA-256 is `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991` (six values: jungle, oak, spruce, dark-oak, acacia and birch leaves). `walls.json` SHA-256 is `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` (14 wall IDs listed in the extraction payload). TNT is in neither tag. The 1.14.4 raw client pin and source/resource identities are recorded in the prior extraction record; these facts are reused, not independently re-extracted.

## Examined concrete candidates

| Candidate | Source evidence and bounded result |
|---|---|
| TNT | Existing witness retained from `wall_non_gate_neighbors/report.md`: common registration; old extra exception rejects the full-face block; target lacks the exception and its face-solid test passes because TNT is absent from leaves. The target walls-tag arm is false. Old bit false, target bit true. |
| Common stained-glass blocks | Old `Block.isExceptionForPlacement` explicitly excludes `StainedGlassBlock`, hence old wall bits are false even where its directional face is `SOLID`. 1.14.4's attachment exception no longer includes stained glass. Both `Blocks` registries construct the stained-glass color variants as `StainedGlassBlock`; the class inherits the full-cube collision shape, and these states are not in the target leaves tag. Therefore their horizontal faces pass the target face-solid predicate and produce an additional concrete common-state arm-bit difference: old false, target true. Source registration evidence is 1.13.2 `net/minecraft/block/Block.java:1092–1154` and 1.14.4 `net/minecraft/block/Blocks.java:598–681`; predicate evidence is above. |
| Barrier, pumpkin, carved pumpkin, lit pumpkin, melon | Each is excluded by both endpoints' attachment/connection exception logic for this generic arm; no difference found in the examined exception lists. |
| Leaves | Both old and new exception paths reject leaves; target tag membership independently also makes face solidity false. The six-member target tag payload is recorded above. No difference found for registered leaf states examined at the class/tag level. |
| Piston, sticky piston, piston head | Old attachment exceptions explicitly exclude them; target attachment exceptions do not. The target classes have directional/state-dependent collision geometry, so this inventory does not assert a differing wall bit without completing the face/state crosswalk. |
| Trapdoors, shulker boxes, beacon, cauldron, glass, glowstone, ice, sea lantern, conduit | These are in the old placement-exception chain and are absent from the target attachment-exception list. Their target horizontal face results depend on class collision geometry and, for some candidates, state or world data. They remain unclosed beyond identifying the exact source classes and old gate. |
| Target walls tag | Tag payload is fixed above. Individual overlap with all 1.13.2 registered wall states was not crosswalked. Existing old wall blocks use the old `MIDDLE_POLE_THICK` arm; the 1.14.4 tag arm can preserve their connection independently of face solidity. Newer target-only wall registrations are outside the common-state comparison. |

Wall placement and horizontal neighbor updates consume this same predicate at 1.13.2 `WallBlock.java:69–116` and 1.14.4 `WallBlock.java:55–104`; the stored directional bits feed the wall collision shape. Thus the TNT and stained-glass outcomes are concrete state-production differences reachable by placing/updating a wall next to those blocks. This is a source-derived collision-state consequence, not a measured movement trajectory.

## Coverage limit and remaining closure

Examined the paired `WallBlock` predicates/call sites, both attachment-exception definitions, target `BlockState` face-solid caching, the old default solid-face rule, exact target leaves/walls payloads, and registered examples TNT, the exception-stable blocks and stained glass. Fence gates were excluded as instructed. No exhaustive crosswalk was made across all common registered block IDs, state variants, inheritance chains and target collision shapes.

To close the remaining inventory, compare the full 1.13.2 and 1.14.4 common block-registration crosswalk against each old-exception family above and all classes overriding collision shape or face behavior. At minimum this needs exact paired state/collision-face review for piston/piston-head, trapdoor, shulker-box, cauldron, conduit, and any old exception family whose target collision face could be full; then evaluate every common target `WALLS` member's registration and old pole shape. This report establishes the TNT and stained-glass differences only and does not claim complete neighbor parity, full pair coverage, or pair freeze.
