# Repeated-axis collision offset epsilon: bounded source finding

## Verdict

**ACCEPT, bounded to the supplied two-cube X-axis witness and the source paths below.** With player box maxX approximately 1.99999995, the first stone cube face at x=2 is about 5e-8 away. Given positive X movement of 2.0, Y/Z overlap with both cubes, loaded blocks, and no other collision shapes or border collision:

- **1.12.2:** the first cube clips the requested movement to the positive ~5e-8 gap; the second cube does not erase it. Box.intersectX has no small-delta early return.
- **1.13.2:** the two full blocks are accumulated into one IntVoxelShape. Its first blocking voxel returns the small residual. This pair alone does not trigger the between-shapes cutoff because the aggregate is the final shape.
- **26.2:** each full cube is emitted as an independent shape. The first clips to ~5e-8; before the second shape, Shapes.collide returns zero because abs(distance) < 1e-7.

This is a source-level consequence conditional on the stated collision neighborhood and box/delta. It is not a runtime reproduction or proof that ordinary input alone produces dx=2.0.

## Exact source identity and provenance

Canonical source root: D:\Javastuff\LegacyParkourCompat\decompiled_minecraft. I used version-local source files and metadata, plus only the specifically permitted accepted closure note.

| Version / namespace | Exact-run identity | Provenance SHA-256 |
|---|---|---|
| 1.12.2 / ornithe-feather | feather-gen2-1.12.2+build.2-mergedv2.jar and feather-gen2-1.12.2+build.2.tiny; run batches 1.10.2, 1.11.2, 1.12.2; ready marker says ready, source-manifest SHA-256 b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da | 747425A26DA363D1ABC8A2CECD83A74504AEB1F58BE93AA53F48C690E35318CE |
| 1.13.2 / ornithe-feather | feather-gen2-1.13.2+build.2-mergedv2.jar and feather-gen2-1.13.2+build.2.tiny; run 1.13.2; ready marker says ready, source-manifest SHA-256 2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211 | E25950B7855FC4F2AEE1A662F0BE6B0AF9F28788D9E27293075CFC5A3AA5D383 |
| 26.2 / unobfuscated | Exact version ID 26.2; mapping option unobfuscated; Vineflower 1.12.0; ready marker has 7,055 sources. Source-manifest SHA-256 a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894; artifact-manifest SHA-256 ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba | A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1 |

The requested cache path D:\.minecraft-decompile-cache was checked literally with Get-Item -Force and a hidden-item scan of D:\. PowerShell returned PathNotFound, not AccessDenied, and no matching directory appeared. Provenance records a different cache path under D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts; that cache was not inspected. Source identity rests on canonical source-local provenance, the 26.2 ready marker/source manifest, and the hashes below.

Decisive source-file SHA-256 values (relative to each version folder):

| Version | Source | SHA-256 |
|---|---|---|
| 1.12.2 | ornithe-feather/net/minecraft/util/math/Box.java | F788B8146B14F299CCB58EA0854609F845C298A963295D503DE2E30E15D66F3A |
| 1.12.2 | ornithe-feather/net/minecraft/entity/Entity.java | 80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A |
| 1.12.2 | ornithe-feather/net/minecraft/world/World.java | E9FA9B8D6D31AD57A5B876F5F63A5E3C554A23437DECF1845A493DAF48233594 |
| 1.13.2 | ornithe-feather/net/minecraft/block/shape/VoxelShape.java | D95B0607647DA11595E24E1E26E9615CB7DA7705EA7885908544362DF62275C1 |
| 1.13.2 | ornithe-feather/net/minecraft/block/shape/VoxelShapes.java | 313968D4E5855B5EC380272B6ABABCC5849D478AB771926355912A8CB6AA9C95 |
| 1.13.2 | ornithe-feather/net/minecraft/entity/Entity.java | 1D6EC8B80F74635401745C2C027BF36555C85348CA5693764F2668363B17D269 |
| 1.13.2 | ornithe-feather/net/minecraft/world/WorldView.java | 9577360B1A1BDDE9280F0B72593E5E6DDE45D9A54719CD7FF933F3ECD31B971F |
| 26.2 | unobfuscated/net/minecraft/world/phys/shapes/Shapes.java | 6ED9460D58727770BDEC59C4F3184F075644F3C411BE1EEAB2EAE17E8ECE1891 |
| 26.2 | unobfuscated/net/minecraft/world/phys/shapes/VoxelShape.java | 419A46BBCC8B48E1C075127954EB243DCC60D10D4087BF256D895CF91A2FF854 |
| 26.2 | unobfuscated/net/minecraft/world/entity/Entity.java | 7AFB9C1294893FFE73E3B1ACFFCAD41C648F15DE8378BFF3DFFAFF869BB811D5 |
| 26.2 | unobfuscated/net/minecraft/world/level/BlockCollisions.java | EB8A8F6F07B1D6384F17987818F056D41ADFD6B2AD120D26774BFA550F4FC51E |
| 26.2 | unobfuscated/net/minecraft/core/Cursor3D.java | 9A1FDC92BB95013C7E70DA467A0B5E5B1C9787DAAFCB0E7E3A8C950DD0E7287F |

## Source evidence

Ranges are one-based source lines.

### 1.12.2

- Box.java:185-201, Box.intersectX: first requires strict Y/Z overlap; positive movement clips to this.minX - box.maxX where that gap is smaller than the current limit. There is no epsilon cutoff for a positive sub-1e-7 limit.
- Entity.java:567-589, Entity.move: obtains a List<Box> from the swept box, processes Y then X, updates the same x scalar for each collider, and moves the player box only after the X loop. The first cube reduces 2.0 to ~5e-8; the next cube is too far to reduce it further.
- World.java:964-1015, World.getCollisions: block scan is X outermost, then Z, then Y; x=2 is collected before x=3 for this witness. World.java:1017-1040 appends entity collisions after block collisions, so the witness assumes no colliding entities.

### 1.13.2

- Entity.java:578-598, Entity.move: obtains a collision stream and applies calculateMaxOffset for Y, X, Z; the positive X pass is at lines 586-590.
- VoxelShapes.java:194-206, calculateMaxOffset: before each next shape, returns 0.0 if abs(maxDist) < 1e-7; otherwise delegates to VoxelShape.calculateMaxDistance.
- VoxelShape.java:188-242, calculateMaxDistance: empty shapes preserve the delta; a nonempty shape returns zero for an incoming sub-1e-7 delta. Positive traversal searches voxel indices, accepts a gap >= -1e-7, reduces maxDist, then returns at the first full voxel.
- WorldView.java:112-152, getBlockCollisions: full-block singleton shapes are recorded in one shared VoxelSet rather than emitted individually; after other shapes, one generated IntVoxelShape represents the accumulated full blocks. WorldView.java:158-163 builds the swept voxel region with +/-1e-7 offsets. BlockPos.java:212-239 shows the region iterator increments X fastest.
- Therefore the two full stone cells alone yield ~5e-8 from the first blocking voxel of the aggregate. As that aggregate is the final stream element, there is no next-shape iteration to trip calculateMaxOffset's cutoff. A non-full collider after a sub-epsilon reduction could trigger it, but that is outside this witness.

### 26.2

- BlockCollisions.java:31-62,78-109: constructs a Cursor3D and emits a translated unit shape per full-block cell; it does not aggregate the cubes. Cursor3D.java:20-53 has X fastest, so x=2 precedes x=3 at equal Y/Z.
- Entity.java:1195-1229,1244-1259: collider order is entity colliders, optional world-border shape, then block colliders; the witness excludes entities and border. collideWithShapes calls Shapes.collide for each nonzero axis with this ordered list.
- Shapes.java:234-244: before each collider, returns zero when abs(distance) < 1e-7; otherwise delegates to the shape.
- VoxelShape.java:256-304, collideX: also returns zero for a sub-1e-7 delta on a nonempty shape. Positive traversal uses findIndex bounds offset by 1e-7, accepts newDistance >= -1e-7, clips with min, and returns at its first full voxel. The x=2 shape therefore yields ~5e-8; before x=3, Shapes.collide returns zero.

## Player caller closure and evidence class

- 1.12.2 and 1.13.2: the specifically permitted accepted evidence .native-source/20261028/ordinary-closure/pane-voxel/closure.md has SHA-256 AA0878F762CB3E6691717AD4F863C134A9D7B2BBAA63038379F5F5AB194B25CE. It records an ordinary local-player movement route and a **packet-assisted** witness: absolute PlayerMoveS2CPacket correction establishes position/box; a subsequent EntityVelocityS2CPacket supplies X velocity 2.0; the locally controlled airborne tick reaches Entity.move. It explicitly does not establish input-only reachability. This finding relies on that accepted note for the old-version caller; it does not re-audit the separate pane witness.
- 26.2: LocalPlayer.tick calls super.tick when the client is loaded (client/player/LocalPlayer.java:227-230); LivingEntity.tick calls aiStep (world/entity/LivingEntity.java:2804-2806); LocalPlayer.aiStep delegates to Player.aiStep (client/player/LocalPlayer.java:914), which delegates to LivingEntity.aiStep (world/entity/player/Player.java:442-454). Player.canSimulateMovement and isEffectiveAi allow a client local player (Player.java:1285-1297; LocalPlayer.isLocalPlayer at client/player/LocalPlayer.java:378). LivingEntity's travel branch passes through these gates (world/entity/LivingEntity.java:3137-3141); ordinary air travel calls moveRelative and then move(MoverType.SELF, getDeltaMovement()) (LivingEntity.java:2462-2464). This closes the ordinary caller to movement, not the exact dx=2.0 state. No 26.2 packet setup or input-only trajectory was traced.

## Dependencies and open boundary

Direct dependencies: the stated box and two loaded full-cube states; strict Y/Z overlap; collector ordering and representation; the positive sub-epsilon residual; and the local-player movement caller. Observable consequence: small positive X displacement in 1.12.2 and the isolated two-full-cube case in 1.13.2; zero X displacement in 26.2 after the second independent cube is visited.

Open: runtime parity and end-to-end trajectory; input-only reachability to the exact box and dx in any endpoint; packet-assisted 26.2 reachability; additional colliders, entities, borders, or different state-dependent collision shapes; and pair-wide coverage beyond this dependency. No tests, build, gameplay/server launch, or source generation were performed.
