# Independent source audit verdict

**Verdict: ACCEPTED for the assigned operation slice.** The report's no-difference conclusion and its ordering claim are supported by the exact sources. This verdict closes only the bounded 1.12 → 1.12.1 slice covering dry local-player movement with active chest Elytra and overlapping `abilities.flying`; it does not assert full release-pair coverage.

## Independent checks

- Read the two canonical Ornithe Feather trees read-only. Independently recomputed SHA-256 for every manifest-listed source: 1.12 has 2,048/2,048 matching files and 1.12.1 has 2,050/2,050. The provenance manifests hash to `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` and `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` respectively. Pinned release and Feather input hashes in the report agree with the manifests.
- The exact `LocalClientPlayerEntity.java` SHA-256 is `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` on both sides. `LivingEntity.java` is also byte-identical (`190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`). The differing `PlayerEntity.java` files differ only at the out-of-scope saved `DataVersion` integer; the compared method bodies are identical.
- Independently extracted and hashed `PlayerEntity.moveRelative(float,float,float)`: both sides are 765 UTF-8 characters with SHA-256 `4f6cfd1cd93faa4f02f96f20a5c2a48c05297254287277c8068de73ad9cf1e27`. The `LivingEntity` method is 8,659 characters with SHA-256 `c336b9240c99be7f4bc066c2536dedea38f6f5ec3e9439b5adaf8fa23bd1e03c` on both sides.
- Source order confirms that the player branch tests `abilities.flying && !isRiding()`, saves `velocityY`, and calls `super.moveRelative(...)` before restoring/scaling velocity, resetting fall distance, and clearing flag 7 (`PlayerEntity.java:1385–1401`). While flag 7 is set, the superclass glide branch invokes `this.move(MoverType.SELF, ...)` (`LivingEntity.java:1425–1477`), so that actual move occurs before the direct player clear. The local override delegates to `super.move(...)` (`LocalClientPlayerEntity.java:841–846`). In dry conditions the superclass's water/lava gate passes; local-control eligibility also holds (`LocalClientPlayerEntity.java:483–486`).
- The superclass also has a distinct `onGround && !world.isClient` flag-7 clear after its glide move (`LivingEntity.java:1475–1477`). It is not a client-local clear and does not change the player override's post-super ordering. The client glide-start input gate excludes `abilities.flying` (`LocalClientPlayerEntity.java:759–763`); this does not negate an already-present overlap state in the ordinary movement call under review.

## Bound artifact

Reviewed report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-early-112-1121-ability-flying-glide-flag-writer/report.md`

Report SHA-256: `2f9fe3265fcad94d6da11e65d4d639a63f8df702ecc05c658f60f11e3a814ed6`

Signed: Codex independent source auditor `/root/c_early_112_1121_ability_flying_glide_flag_writer_audit`, 2026-11-18 (Windows PowerShell; no source generation, product/wiki/history access, runtime launch, tests, or delegation).
