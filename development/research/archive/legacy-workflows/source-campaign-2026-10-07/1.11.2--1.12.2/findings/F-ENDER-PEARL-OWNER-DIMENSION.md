# F-ENDER-PEARL-OWNER-DIMENSION: 1.12.2 clears a pearl's player thrower before dimension transfer

- Older version A: 1.11.2
- Newer version B: 1.12.2
- Mechanic / coverage slice IDs: S7.1 (player position writers); S7.3 (dimension-transfer entity recreation and player authority)
- Classification: changed behavior
- Confidence: source-confirmed by discovery author; independent blind source review pending
- Applicability: server-side Ender Pearl dimension transition; the player movement consequence requires the pearl's owner to be available in the destination world when the pearl later collides
- First changed release: unknown within (1.11.2, 1.12.2]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather`
- A source: `1.11.2/ornithe-feather/net/minecraft/entity/projectile/EnderPearlEntity.java`, whole-file SHA-256 `290f7923d6467e252d2c96dd0f6939c37eef3597a85faeb022fd4be8bb3b7714`. `EnderPearlEntity` ends after `tick()` (lines 101-110); it declares no `changeDimension(int)` override. `EnderPearlEntity::onCollision(HitResult)` lines 37-98 teleports a connected, same-world, awake `ServerPlayerEntity` to the pearl's coordinates after dismounting, then resets fall distance and applies the vanilla fall-damage call. The player position write is `livingEntity.teleport(this.x, this.y, this.z)` at line 87.
- B manifest artifact reference: `build/movement-campaign-2026-10-07/ready/1.12.2/ornithe-feather`
- B source: `1.12.2/ornithe-feather/net/minecraft/entity/projectile/EnderPearlEntity.java`, whole-file SHA-256 `ef91e2979a5027f5d28a40af9b809bc312bccb19cb2016b6d20f5c145cdd0e35`. `EnderPearlEntity::changeDimension(int)` lines 119-125 has exact body SHA-256 `8f76940b561cd7b870473d3c1f1b6736bc751f41fa9d1f7243e13040cd69410d`; when `this.thrower.dimension != dimension`, it clears `this.thrower` before delegating to `Entity.changeDimension`. The corresponding `onCollision(HitResult)` lines 40-107 has the same player teleport branch as A at line 94, with a B-only advancement criterion callback in the End Gateway branch.
- Transfer/recreation path: A/B `Entity::tick()` calls `this.changeDimension(j)` after server-side Nether portal timing (`Entity.java` A line 358 / B line 361). A/B `Entity::changeDimension(int)` method bodies match (body SHA-256 `d05c2d14c9f9d13e09f4cb205840c5f4f4789281bf53c271e03659e56385aa2d`); each removes the old entity, calls `copyNbtFrom(this)`, creates a same-class replacement in the destination world, restores its NBT and position, then adds it to that world. A/B `Entity::teleport(double,double,double)` also matches (body SHA-256 `a0b6145c49ef895ac70adce95613188731b379c3fe41e3d4e444e58105fbbad1`).
- Thrower persistence path: A/B `ThrownEntity::writeCustomNbt(NbtCompound)` lines 269-282 has body SHA-256 `6423c4cf25676c075ded7570d059149dc3dbe2a6ad0ca0d8d86164bf19ebbbbe3`; if `throwerName` is empty and the live thrower is a player, it sets `throwerName` from the player, then writes `ownerName`. A/B `readCustomNbt(NbtCompound)` lines 285-305 has body SHA-256 `11093596ff25e6d78f12f64c1e02a5f06151dc82c7fb35cf81e111509c621edb`; A/B `getThrower()` lines 307-323 has body SHA-256 `37c549c937a45499ead5e49a6b00a79b48f326239958f093dadaa9013328ec19` and resolves a nonempty saved owner name in the pearl's current world.
- Entry path: A/B `EnderPearlItem::startUsing(World,PlayerEntity,InteractionHand)` bodies match (body SHA-256 `14df5d0cbe755c522c5b3c5c3083548d97c7692c275d18795764d567e86d5e45`); server-side use constructs the pearl with the player as thrower and launches it. A/B `EndGatewayBlockEntity::teleport(Entity)` bodies match (body SHA-256 `439eebba7a9d9369f8fd93e7ab528d2116755326b802a47416f15b5102865f26`); this is a separate equal coordinate teleport path for an entity touching an available gateway.
- Source preparation/provenance: both exact endpoint source manifests and immutable `feather-r1-2026-10-07` snapshots are recorded in the run manifest. The original derived mapped JARs remain unavailable; revised snapshots do not establish identity with those originals.

## Source-level difference

The generic server portal tick reaches the virtual `changeDimension` method for a pearl that has entered a Nether portal and completed its portal timer. On A, the inherited transfer serializes the live player thrower into `ownerName` before recreating the pearl, so a matching player in the destination world can be resolved as its thrower afterward. On B, the pearl override first clears the live thrower when the target dimension differs; for a newly thrown pearl whose `throwerName` has not already been populated, the inherited serializer therefore writes an empty owner name and the replacement pearl cannot resolve that player.

When the transferred pearl later collides, both versions use the same player response guard: connected server player, player world equal to pearl world and player not sleeping. If that guard holds, A can teleport the player to the pearl and B has no thrower to enter the branch. If it does not hold, neither version teleports that player through the ordinary pearl collision branch. A previously populated owner name may remain serialized in B because the override clears `thrower`, not `throwerName`; the finding therefore states the fresh-pearl precondition rather than claiming all dimension transfers lose ownership.

## Consequence and uncertainty

Source establishes a difference in player thrower retention across a pearl dimension transfer, and a reachable conditional difference in the later player position write. It does not establish how often players encounter that sequence or a measured trajectory. The B-only End Gateway advancement callback does not itself write movement state. Entity dimension transfer, packet authority and other external movement writers remain in S7.3/S7.4; no damage behavior is in scope.

## Handoff

S7.1 owns the direct pearl teleport consequence; S7.3 owns dimension-transfer recreation and received authority. Keep S7.1 open for its remaining external-writer census. First-changed release within the interval remains unknown. The discovery author verified the cited source bodies; a separate blind source reviewer must accept the exact replacement finding snapshot before implementation handoff.
