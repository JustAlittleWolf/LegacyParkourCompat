# Bed bounce source audit

Verdict: ACCEPT

Claim revision SHA-256: `a728828983aacfa8df3d622a6e804cefb321d5eb499acf7dada7c865844c994d`
Reviewed input: `build/mcpk-20261116/pipeline-early-rules/bed-bounce-source/bed-bounce-source-facts.md`

## Evidence identity audit

- 1.11.2 ready marker SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; provenance SHA-256 `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`. Ready marker version/mapping, manifest names and declared hashes agree with the authenticated in-tree manifests; provenance names Feather build.2 mapping artifacts.
- 1.12 mapping-local `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`. It binds the Feather 1.12 metadata, Mojang 1.12 client and version metadata by hashes, and has a `sources` object binding paths to source digests.
- 1.12.1 mapping-local `.decompile-provenance.json` SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`. It binds the Feather 1.12.1 metadata, Mojang 1.12.1 client and version metadata by hashes, and has a `sources` object binding paths to source digests.
- 1.12.2 ready marker SHA-256 `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`; provenance SHA-256 `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`. Ready marker version/mapping, manifest names and declared hashes agree with the authenticated in-tree manifests; provenance names Feather build.2 mapping artifacts.
- All seven cited source files (BedBlock, Block, Entity, LivingEntity, PlayerEntity, ClientPlayerEntity and LocalClientPlayerEntity) were rehashed for each release and matched their root SHA-256 source manifest (1.11.2 and 1.12.2) or mapping-local provenance `sources` entry (1.12 and 1.12.1). The exact hashes are the ones listed in the reviewed facts file. The 1.12, 1.12.1 and 1.12.2 BedBlock file bytes are identical (SHA-256 `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369`).

## Claim audit

- **Cutover and callback:** 1.11.2 BedBlock has no `beforeCollision` override; its superclass `Block.beforeCollision` sets `velocityY = 0.0` at `Block.java:624-626`. In 1.12, 1.12.1 and 1.12.2, BedBlock overrides it at `BedBlock.java:141-150`. The checked evidence supports first appearance among these four named releases and identical response source through 1.12.2. The facts correctly leave the exact introduction interval open rather than claiming an intermediate patch boundary.
- **Gates, operation order and numeric types:** The 1.12 callback first checks `entity.isSneaking()` and delegates to `super` (zeroing vertical velocity) when true. Otherwise it only acts under `entity.velocityY < 0.0`, assigning `-entity.velocityY * 0.66F`, then multiplying by `0.8` only for entities not `LivingEntity`. Since `velocityY` is `double`, Java promotes the `0.66F` operand to `double`; the facts preserve the source float literal and do not assert a float intermediate. Non-sneaking upward and zero velocity remain unchanged. The callback body does not consult `world`, block state, or a logical-side predicate. Players are LivingEntity instances and therefore skip the non-Living `0.8` factor.
- **Reachability:** In each checked release, the common `Entity.move(MoverType, double, double, double)` has a no-clip early path, then on its collision path obtains `Block block2 = blockState.getBlock()` and invokes `beforeCollision(this.world, this)` under `o != y` (1.11.2 `Entity.java:722-725`; 1.12 family `Entity.java:725-728`). That callback site has no logical-side guard. `LivingEntity` movement calls `this.move(MoverType.SELF, ...)`. `LocalClientPlayerEntity` overrides that signature in all four releases, but its override calls `super.move(moverType, x, y, z)` before auto-jump processing; the other player classes do not declare this signature. The hierarchy sources show `LocalClientPlayerEntity -> ClientPlayerEntity -> PlayerEntity -> LivingEntity -> Entity` in every release. Thus the ordinary local-player path reaches the common collision callback. This verifies the static source path, not runtime execution.
- **Scope/disposition:** This evidence is limited to the direct vertical collision response and its inherited player path. It supports neither bed-entry eligibility nor damage/health behavior, and makes no claims about those excluded operations, runtime parity, other releases, or whole-pair tick coverage.

## Limits

Source-only independent audit of the immutable facts revision and the four named exact source trees. No product implementation, canonical research record, wiki/source lane, Git history, runtime behavior, tests, or build was inspected. The verdict does not establish whole-pair coverage or runtime parity.