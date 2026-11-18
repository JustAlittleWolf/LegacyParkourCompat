# Ordinary block-contact movement: 1.21.9 to 1.21.10

## Exact source pins

- Older source root: `decompiled_minecraft/1.21.9/mojmap`. Its embedded `.decompile-provenance.json` records the version-specific metadata package URL ending in `/1.21.9.json` (package hash `91f383c21d8cd06abc9a569f2976b58da192a642`), Mojmap client mappings file SHA-256 `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`, and client JAR SHA-256 `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`.
- Newer source root: `decompiled_minecraft/1.21.10/mojmap`. `mojmap.ready.json` identifies version metadata `1.21.10`, mapping `mojmap`, and client JAR SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`. `mojmap.provenance.json` records a Mojmap exact run with 1.21.10 in its batch. The ready marker pins `mojmap.sources.sha256` by SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`.
- Each listed 1.21.9 file digest matches the embedded source hashes; each 1.21.10 digest matches its source-manifest entry and on-disk file.

| Consumed source | 1.21.9 SHA-256 | 1.21.10 SHA-256 |
|---|---|---|
| `net/minecraft/world/level/block/HoneyBlock.java` | `df26bbd9b72ac98b93b97c21c656d76d33c1968ccf136884b177a0b95dff4f61` | `7f8a0dca359d623f5db71e206679f20de8cd7c42f244d0f0230b876f5131f898` |
| `net/minecraft/world/level/block/WebBlock.java` | `ba24400cf87b00c88c5bd441c781a128b6973a46bff3df62c6144e9bfc5abb43` | `90c0b5274ca4cf037605019cd906ff121dca108dbfc0fc2060a038ea966218fc` |
| `net/minecraft/world/level/block/SweetBerryBushBlock.java` | `0f3629034c12f654701ca7dfb24f1db83a57024e8552247198b11e21dfcf57af` | `9797bf5172b1aec00752e06d8203e97a8cacac3f32938be2da7ad455dd07fb99` |
| `net/minecraft/world/entity/Entity.java` | `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |

## Bounded comparison

- Honey producer: `HoneyBlock.entityInside` and `doSlideMovement` (1.21.9 lines 63–70 and 106–116; 1.21.10 lines 63–70 and 106–116). The `isSlidingDown` gate, vertical-velocity transform and `-0.05` target, horizontal scaling condition, and fall-distance reset are equal. The 1.21.10 callback adds a boolean argument, which Honey does not read; its movement statements are unchanged.
- Web producer: `WebBlock.entityInside` (lines 27–34 in each version) uses the same `(0.25, 0.05F, 0.25)` multiplier, switches to `(0.5, 0.25, 0.5)` for living entities with Weaving, and calls `makeStuckInBlock` in both versions. The new callback argument is unused.
- Berry producer: `SweetBerryBushBlock.entityInside` (1.21.9 lines 82–84; 1.21.10 lines 82–84) has the same living-entity eligibility and fox/bee exclusions, then calls `makeStuckInBlock` with `(0.8F, 0.75, 0.8)` in both versions. The new callback argument is unused.
- Contact order and consumer: `LivingEntity` performs `travel` before `applyEffectsFromBlocks` (1.21.9 lines 2845–2853; 1.21.10 lines 2845–2853). `Entity.checkInsideBlocks` then dispatches block `entityInside` callbacks for intersecting shapes (1.21.9 lines 1134–1209; 1.21.10 lines 1137–1214). `LivingEntity.handleRelativeFrictionAndCalculateMovement` issues an ordinary `move(MoverType.SELF, ...)` (lines 2476–2485 in both versions). The ordinary SELF consumer in `Entity.move` multiplies requested displacement by a stored `stuckSpeedMultiplier`, clears it, and zeroes velocity in both versions (1.21.9 lines 687–690; 1.21.10 lines 687–694). `Entity.makeStuckInBlock` stores the multiplier and resets fall distance in both versions (1.21.9 lines 2783–2786; 1.21.10 lines 2788–2791).

## Result and limits

No source-confirmed difference was found in this bounded ordinary SELF movement path for Honey slide velocity and fall reset, Web slowdown, Berry slowdown, contact staging, or the first ordinary SELF consumer. No positive finding is warranted for this slice. This does not establish full-pair coverage or runtime parity. Excluded from this assignment: the PISTON path, damage, fluids, portals, attributes, sprint, geometry, support behavior, other full-pair movement slices, and runtime verification.
