# B04 independent source-claim audit

Status: COMPLETED (source-only; no code changes, tests, gameplay, or pair-freeze claim).

## Immutable identities and evidence integrity

- Audited claim: `b04-horizontal-restitution.md`, SHA-256 `a67ba09560a43dac641476c9374189eef99e156ff601f3fc803cb32a0bea5f54` (matched).
- Approved control artifact: `build/source-lane-262-collision-restitution/26.1.2--26.2/collision-restitution.md`, SHA-256 `22da61a0ec3f02d1e9759788480cd5e47127540961b5f5d55b669dc4e2a8036d` (matched).
- Release-root manifests matched the supplied pins in order: 26.1 `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`; 26.1.1 `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`; 26.1.2 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c`; 26.2 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Each `unobfuscated.ready.json` confirms its exact version, `unobfuscated` mapping and manifest digest.
- Every listed source member hash was checked against both that release's `unobfuscated.sources.sha256` entry and the actual file. `Entity.java`, `Player.java`, and `Block.java` for 26.1/26.1.1/26.1.2 matched the claim's hashes. The 26.2 Entity, Player, LivingEntity, Attributes, AttributeInstance, AttributeMap, AttributeCommand and Block files also matched. Full Entity.java byte identity across the three 26.1 patch releases verifies intact bodies, including `Entity.move`; the 26.2 full helper bodies were read directly.

## Per-edge verdicts

- **26.1 → 26.1.1: COMPLETED / accepted, no change in the assigned clipped-axis response.** `Entity.move` determines `xCollision` and `zCollision` by `!Mth.equal(delta.component, movement.component)`, ORs them for `horizontalCollision`, and independently zeros only each collided velocity component (`26.1/unobfuscated/net/minecraft/world/entity/Entity.java:752-779`; identical source file in 26.1.1). This supports the claim's no-change verdict and scope.
- **26.1.1 → 26.1.2: COMPLETED / accepted, no change in the assigned clipped-axis response.** Same complete `Entity.java` hash and method body on both endpoints; same axis-wise zeroing. This supports the second no-change verdict.
- **26.2 control: COMPLETED / accepted as changed behavior, not as an exact patch-boundary finding.** In 26.2, after collision flags and fall-damage handling, `Entity.move` invokes restitution only if `canSimulateMovement()` and `(movedVertically && verticalCollision || horizontalCollision)`. The helper writes clipped X and Z components as `-currentMovement.component * restitution`, then writes the resulting velocity once. Positive effective entity bounciness therefore reverses/scales a clipped axis; zero bounciness or shift-key bounce suppression yields zero on that axis. The block restitution and `SUPPRESSES_BOUNCE` block tag are consulted in the vertical branch only; `isSuppressingBounce()` is separately used in horizontal restitution and is shift-key based. The claim's exclusion and distinction are accurate. The nonzero witness is bounded to its permissioned command route: 26.2 `Attributes.BOUNCINESS` is default 0 with range [0,1], `LivingEntity` reads it as entity bounciness, and `/attribute ... base set` can set a positive value on a target living entity under gamemaster permission. The default path remains zero.

## Gates, order, and remaining applicability

- `Player.canSimulateMovement()` in 26.1.2 and 26.2 returns `!level.isClientSide() || isLocalPlayer()`. Thus the local client player can run the response, while a remote player on the logical client cannot. The 26.2 condition also requires an actual relevant collision; `noPhysics` bypasses the response branch.
- 26.1 response order is collision resolution/position update, collision flags, local-authoritative fall-damage callback, then clipped-axis velocity zeroing; movement emission and block-speed X/Z scaling follow. In 26.2 the restitution branch occupies the corresponding post-callback position before movement emission and block-speed scaling. Statements about those neighboring operations are order observations only, not findings about their own behavior.
- No source in 26.1.3–26.2 was inspected. The exact introduction boundary remains unknown within 26.1.2→26.2. Applicability is established only for the stated fixed-profile local-player horizontal clipped-axis operation, with positive effective bounciness needed for nonzero rebound. This audit does not claim full-pair coverage or independent tick-graph closure.

## Procedure limits

Reviewed the permitted README/buildSrc README and discovery/source workflows. Excluded landing callbacks, bed/slime/honey, vertical restitution, general step/solver behavior, and any product, wiki, backlog, Git-history or other-lane evidence beyond the specifically authorized 26.2 control artifact.
