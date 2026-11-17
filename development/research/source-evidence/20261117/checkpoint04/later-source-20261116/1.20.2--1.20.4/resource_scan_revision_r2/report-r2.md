# 1.20.2–1.20.4 resource/tag/data-input slice — partial evidence

Assignment: Mojmap 1.20.2 → 1.20.4, residual DEP-02 only. This is not a pair-completion verdict.

## Source gate

Canonical inputs: `decompiled_minecraft/1.20.2/mojmap/` and `decompiled_minecraft/1.20.4/mojmap/`.
Provenance identities match the assigned SHA-256 values:
- 1.20.2 `mojmap.provenance.json`: `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05`.
- 1.20.4 `mojmap.provenance.json`: `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385`.

Both ready markers report `status=ready`, exact requested version and `mapping=mojmap`. For each side the ready marker's source-manifest hash, count and byte total matched; every listed source file's SHA-256 matched and the tree contained no unlisted files. The ready marker's artifact-manifest hash/count and client hash reference also matched its ledger. The source inventories are 4,904 and 5,048 `.java` files respectively. These checks establish the source-tree identity; they do not validate/read the external artifacts' bytes.

## Checked Java registry/tag declarations (no movement-relevant declaration delta established)

Compared both source files byte-for-byte: `net/minecraft/tags/BlockTags.java`, `FluidTags.java`, `ItemTags.java`, `world/effect/MobEffects.java`, `world/item/enchantment/Enchantments.java`, and `world/level/material/Fluids.java`; all are identical. These contain tag keys or built-in key declarations, not data-pack tag membership or all effective registry data.

`net/minecraft/tags/EntityTypeTags.java` adds `ZOMBIES`, `UNDEAD`, `CAN_BREATHE_UNDER_WATER`, `DEFLECTS_ARROWS`, `DEFLECTS_TRIDENTS`, `CAN_TURN_IN_BOATS`; `DamageTypeTags.java` adds `BREEZE_IMMUNE_TO` and `CAN_BREAK_ARMOR_STAND`. The obvious new keys are breeze/boat/armor-stand or breathing categories; this scan did not establish any player movement effect or determine their default membership. Relevant existing key uses include `Entity.java` `isStateClimbable(BlockState)` (1.20.2 lines 726–728; 1.20.4 lines 727–729), which feeds `vibrationAndSoundEffectsFromBlock` step-sound/vibration handling rather than physical climbing; physical movement's `LivingEntity.onClimbable()` begins at 1.20.2 line 1511 and 1.20.4 line 1508, reading `BlockTags.CLIMBABLE` at lines 1517 and 1514 respectively. Other uses include `Entity.java` `canFreeze` (around 3282), fluid pushing (around 1182–1212); `LivingEntity.java` soul-speed block test (around 472), jump/slow-fall/liquid movement (around 1561, 2005, 2023–2083, 2566–2583), and freeze-equipment test (around 3383); `Player.java` water eye check/Aqua Affinity (around 735) and elytra-start gate (around 1507). Line positions shift between releases. No claim is made here that these entire operations were re-audited.

Tag loading/network evidence is present in both trees: `net/minecraft/tags/TagNetworkSerialization.java` serializes registry tag payloads; `net/minecraft/network/protocol/common/ClientboundUpdateTagsPacket.java` carries those payloads to `handleUpdateTags`. Effective tag membership is therefore data-pack/reload input and can be server-synchronized; source key declarations alone do not establish the player's effective membership. This slice did not resolve all registry synchronization/default-data paths.

## Unexamined / required next gate

The canonical decompiled source trees contain Java only. Exact packaged data-pack JSON/tag membership, effective default tag contents, and other movement-relevant packaged registry entries are in the version artifacts, which were not inspected under this read-only-source assignment. `Blocks.java` and several broader registry classes differ between endpoints but their changed entries were not exhaustively classified for this slice. Direct consumers/resources remain partially open. No finding is drafted: this evidence does not prove a concrete eligible movement difference or no-difference across packaged resources.

Coverage verdict: **PARTIAL / NOT AUDITED**. Java declarations above: checked, with identical files or noted new keys; exact resource memberships and the full movement consumer/resource closure: unexamined. No product finding, acceptance, implementation or runtime-parity claim.
