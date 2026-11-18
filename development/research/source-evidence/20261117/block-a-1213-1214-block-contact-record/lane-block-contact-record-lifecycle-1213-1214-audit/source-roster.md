# Frozen blind source roster: 1.21.3 → 1.21.4 block-contact record lifecycle

Frozen before opening the assigned candidate claims. Scope: LocalPlayer block-inside environmental-support dependency, movement record creation/span, list lifecycle, record AABB, BlockGetter cell traversal and callback argument, and reachable after-travel client path. No collision-shape results; no non-player or other movement-record writers.

## 1.21.3 Mojmap source inputs

Ready marker: mojmap.ready.json; source manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce. Provenance: mojmap.provenance.json. Mapping: Mojmap; exact release: 1.21.3.

- `net/minecraft/world/entity/Entity.java` — SHA-256 `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`
- `net/minecraft/client/player/LocalPlayer.java` — SHA-256 `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`
- `net/minecraft/client/multiplayer/ClientLevel.java` — SHA-256 `f99ca9b24f796bed5428e6a3b010c489bfac2f43a35e90ff4fd8dad6d2e45aa1`
- `net/minecraft/world/level/BlockGetter.java` — SHA-256 `5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39`
- `net/minecraft/core/BlockPos.java` — SHA-256 `aa1017d0cd97ddf59c45efa21e3c8970284fb237d6335e7e34f5627ed37a69b3`
- `net/minecraft/world/phys/AABB.java` — SHA-256 `1990e0e1058e9ac5201c32e51c6a5f9b671e7ed00ba863aacf2c5b7b0adff217`
- `net/minecraft/world/phys/Vec3.java` — SHA-256 `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d`
- `net/minecraft/world/level/block/state/BlockState.java` — SHA-256 `a3eec38ae13ae1e1305960fa13e34d8843c1ae9480c75d6ca1450d590ba9bb7b`
- `net/minecraft/world/level/block/Block.java` — SHA-256 `4ba7c916ae7c4ee9174c468e9a1f56218d2bcac16f74dd6080c561f945ea4d3a`
- `net/minecraft/world/entity/LivingEntity.java` — SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`
- `net/minecraft/world/entity/player/Player.java` — SHA-256 `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de`

## 1.21.4 Mojmap source inputs

Ready marker: mojmap.ready.json; source manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0. Provenance: mojmap.provenance.json. Mapping: Mojmap; exact release: 1.21.4.

- `net/minecraft/world/entity/Entity.java` — SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`
- `net/minecraft/client/player/LocalPlayer.java` — SHA-256 `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`
- `net/minecraft/client/multiplayer/ClientLevel.java` — SHA-256 `d21332aed2f9460594f838bbac2c813572fe5addf2ef0b9f9f4e9ffbd74ad313`
- `net/minecraft/world/level/BlockGetter.java` — SHA-256 `8bd936e3ecac5720a36b675f4910801e9d201e899b34c316c6d0be9a12d4da20`
- `net/minecraft/core/BlockPos.java` — SHA-256 `a01661d6880dab86f2adb8c8bc203484f63bc0e10eaa14e36525e7123a2d4d15`
- `net/minecraft/world/phys/AABB.java` — SHA-256 `1990e0e1058e9ac5201c32e51c6a5f9b671e7ed00ba863aacf2c5b7b0adff217`
- `net/minecraft/world/phys/Vec3.java` — SHA-256 `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d`
- `net/minecraft/world/level/block/state/BlockState.java` — SHA-256 `a3eec38ae13ae1e1305960fa13e34d8843c1ae9480c75d6ca1450d590ba9bb7b`
- `net/minecraft/world/level/block/Block.java` — SHA-256 `857a7f02c86cbaff6b889032145677a0cc2255814dd983c0fae393cab0cb8a68`
- `net/minecraft/world/entity/LivingEntity.java` — SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`
- `net/minecraft/world/entity/player/Player.java` — SHA-256 `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`

## Roster basis

Entity and LocalPlayer contain movement recording, tick dispatch and callback entry points. ClientLevel and inherited LivingEntity/Player close the reachable local-player tick path. BlockGetter, BlockPos, AABB and Vec3 cover traversal inputs and span geometry; BlockState and Block cover block-state membership and virtual callback dispatch. The roster is source-derived without relying on the submitted candidate documents.

## Roster addendum

During tick-chain audit, AbstractClientPlayer.java was added to close the superclass edge between LocalPlayer and Player; it is not a candidate claim source. $v Mojmap SHA-256: `fa15cd69238b46b410bc709c8ae0a629d5f3f817a6bc454e70a1d60bf7aa785c`; manifest row: `fa15cd69238b46b410bc709c8ae0a629d5f3f817a6bc454e70a1d60bf7aa785c *net/minecraft/client/player/AbstractClientPlayer.java`.
