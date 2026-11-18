# C: 1.9.1 → 1.9.2 glide/climb alias gate

## Result

**No exact 1.9.1 → 1.9.2 source boundary is supported for this claim.** The requested claim is a non-difference in the canonical local Feather source pair: the four source files that cover local activation, player travel/glide and climbing, server packet acceptance, and server player flag writes are byte-identical across the two releases. This is bounded evidence, not full-pair coverage and not a movement finding. Do not substitute 1.19.x or infer an intermediate boundary.

## Exact local source identity

- Older: Minecraft `1.9.1`, `decompiled_minecraft/1.9.1/ornithe-feather/`.
  - Feather artifact URL recorded by the local provenance: `https://maven.ornithemc.net/releases/net/ornithemc/feather-gen2/1.9.1+build.2/feather-gen2-1.9.1+build.2-mergedv2.jar`
  - Artifact SHA-256: `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`
  - Local provenance manifest SHA-256: `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`
- Newer: Minecraft `1.9.2`, `decompiled_minecraft/1.9.2/ornithe-feather/`.
  - Feather artifact URL recorded by the local provenance: `https://maven.ornithemc.net/releases/net/ornithemc/feather-gen2/1.9.2+build.2/feather-gen2-1.9.2+build.2-mergedv2.jar`
  - Artifact SHA-256: `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`
  - Local provenance manifest SHA-256: `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`.
- Both manifests name the same local pipeline (`lpc-decompile-v1`, single-threaded) and runtime (Eclipse Adoptium 25.0.3+9-LTS, Windows 11 amd64). Each selected source file SHA-256 below matches its own manifest `sources` entry; the pair hashes also match each other.
## Bounded source coverage and evidence

All paths are relative to `decompiled_minecraft/<release>/ornithe-feather/`. For each row, the listed digest is the validated SHA-256 on both sides.

| Source | Relevant member/range | SHA-256, both releases | Gate result |
|---|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `tick` 142–155; input/action handling 642–710 | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | Identical |
| `net/minecraft/entity/living/LivingEntity.java` | `isClimbing` 900–912; `moveRelative` 1293–1385; travel ordering 1697–1703; `flyingTick` 1709–1727; `isFallFlying` 2042–2044 | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | Identical |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `handlePlayerMovementAction`, especially `START_FALL_FLYING`, 806–859 | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` | Identical |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `setFlying` / `clearFlying`, 1040–1046 | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` | Identical |

## Exact gates and order (both releases)

- The local client sends `START_FALL_FLYING` when jumping is held, the previous `bl` jump state is false, the player is airborne, `velocityY < 0.0`, the player is not already fall-flying, and abilities flight is off (LocalClientPlayerEntity 704). The comparison is strict and the literal is `0.0`; the gate does not call `isClimbing`. Then it checks the chest slot for exactly `Items.ELYTRA` and `ElytraItem.canFly`, sends the packet, and plays the Elytra sound (705–709). This packet request does not locally set flag 7.
- The server packet handler repeats the strict airborne/negative-vertical-velocity/not-already-gliding/dry gate (ServerPlayNetworkHandler 846–851), then the same exact chest-item/durability gate before `setFlying`. In the `else` for the first gate it calls `clearFlying` (852–854). The server methods write flag 7 true in `setFlying`; `clearFlying` writes true and immediately false (ServerPlayerEntity 1040–1046).
- At travel, `flyingTick` is called before `moveRelative` (LivingEntity 1697–1702). It maintains an already-set flag only while airborne, not riding, and with a usable Elytra in the chest slot (1709–1727); it does not test `velocityY` or `isClimbing`. Flag maintenance writes back only when `!world.isClient` (1725–1726). With active flag, `moveRelative` selects the glide branch before the ordinary movement branch where ladder/vine climbing clamps and fall-distance reset occur (1297 onward versus 1346–1385). Thus an already-active, dry, valid Elytra glide at ladder/vine feet is not rejected by a climb predicate in these gates.
- Glide-branch landing cleanup is `onGround && !world.isClient`, then `setFlag(7, false)` (1343–1345). Loss of airborne/riding/item eligibility is cleared by server-side `flyingTick`; client-side `flyingTick` does not write flag 7. The separate player abilities-flight override clears fall distance and flag 7 after `super.moveRelative` (PlayerEntity 1370–1381); it is distinct from ordinary survival Elytra activation and unchanged across this pair.
- `isClimbing` reads the block at `(floor(x), floor(getShape().minY), floor(z))`; ladder or vine returns true (900–912). The active glide branch is chosen before the non-glide climbing handling, so this predicate does not override the active glide gate in either release.

## Bounds / remaining gate

Reviewed only the adjacent exact 1.9.1 → 1.9.2 Feather pair and the four listed player movement/network source files. The relevant full files were independently SHA-checked against both local provenance manifests and each other. No decompilation, source generation, product/history/wiki inspection, tests, build, or launch was performed. The alleged 1.9.1/1.9.2 alias gate is closed as an unsupported difference; any other claim or exact intermediate patch boundary needs its own evidence.


