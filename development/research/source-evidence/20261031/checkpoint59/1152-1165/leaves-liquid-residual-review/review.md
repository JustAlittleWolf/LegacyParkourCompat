# Independent source review: LeavesBlock and LiquidBlock residual

## Verdict and immutable input

**REVISE** the bounded residual report before acceptance, bound to report SHA-256 `526DAA778A4F5948CE93118EC63D66B06D8DA83A30C59F25D5F589FAE031D780` (`leaves-liquid-provider-residual/report.md`). Its source ledger SHA-256 is `A157F147D8FEF6BC92C9659FCEF6AFC66E1190216B9B4BD917D1890E65897A45`; all 13 listed source-file hashes were recomputed against the named 1.15.2/1.16.5 Mojmap roots and matched. The review ledger `reviewer-source-hashes.tsv` (SHA-256 `739589450151F9203AB263208ABCA07964B814DA29B3CB5083C96613F7847821`) adds the files needed to check the no-override and registration/collision-context claims.

## Required citation corrections

The source facts and conditional dispositions below are supported, but two cited line ranges do not point to the pinned source members:

- The 1.15.2 `Block.java:398–416` citation does not include `isFaceSturdy`, which is at lines 419–421. `canSupportCenter` is lines 413–416; `canSupportRigidBlock` is lines 405–411. Replace the cited range with the member-specific ranges.
- The 1.16.5 `LiquidBlock.java:31` citation points at `LEVEL`, while `STABLE_SHAPE` is at line 34. The provider is lines 51–57 (the submitted `50–56` range starts at `@Override` and omits the closing line).

These citation defects are against files whose hashes match the submitted ledger. Correcting them changes the report revision and requires a new review bound to that revision. No semantic rewrite is required by this review; the applicability gate below remains material.

## Findings

- **LeavesBlock: report conclusion is correct, with an explicit applicability gate.** In 1.15.2, LeavesBlock has no support-shape override. Its ordinary block registration retains collision, while `Block.isFaceSturdy` returns false for `BlockTags.LEAVES` and otherwise tests the collision face for fullness. In 1.16.5, LeavesBlock returns `Shapes.empty()` from `getBlockSupportShape`; `SupportType.FULL` evaluates that shape, so full support is false. Thus a state effectively tagged `LEAVES` at the old endpoint is false on both sides. If the state is not in the 1.15.2 effective `LEAVES` tag, old FULL support can be true while new support is false. With equal effective membership this means the queried state is excluded at both endpoints. A state removed from the tag only at 1.16.5 does not by itself establish this delta, because it was already rejected by the old tag gate. Java sources do not pin the effective tag contents, so applicability remains conditional as the submitted report says.
- **The tag-gated change reaches an in-scope local-player fluid-current operation.** Both FlowingFluid versions use the same falling-fluid branch and `isSolidFace` gates: for horizontal neighbors of a falling fluid cell, non-same-fluid, non-ice side faces are checked through `BlockState.isFaceSturdy`; a true face changes the vector by normalizing it and adding `(0,-6,0)`, followed by final normalization. On 1.15.2, an untagged LeavesBlock collision face can satisfy that query; the 1.16.5 empty support shape cannot. In 1.16.5, Entity's fluid-height scanner calls `FluidState.getFlow` for matched cells whose surface reaches the deflated player-box bottom and adds the resulting vector when `isPushedByFluid()` is true. Player sets that gate to `!abilities.flying`; therefore a non-flying local player with positive fluid submersion can receive the changed current contribution. This is a concrete, conditional player-movement dependency, not an unconditional default-leaves finding. Cancellation by other fluid cells remains possible; a one-cell neighborhood is a sufficient witness.
- **LiquidBlock `entityInside` removal is already closed by the cited fluid evidence.** The 1.15.2 callback sets the entity lava flag when its held fluid is lava. The cited pool33 lava-occupancy and fluid-height reports trace the 1.16.5 replacement through the fluid-height scanner and `isInLava`; no duplicate finding is warranted here.
- **The new stable-shape provider is closed for LocalPlayer.** In 1.16.5 it returns the half-block `STABLE_SHAPE` only after the geometric, `LEVEL == 0`, and `canStandOnFluid` gates pass. `EntityCollisionContext` forwards that last gate to a LivingEntity method reference; `LivingEntity.canStandOnFluid` returns false. Player and LocalPlayer add no override, and Entity collision queries use `CollisionContext.of(this)`. Therefore a LocalPlayer query returns `Shapes.empty()` regardless of the other gates. The report's no-player-delta disposition is supported.

## Referenced pool33 fluid evidence

Only the four fluid reports referenced by the submitted report were read. Their hashes are recorded here so this verdict does not float with later edits:

- `fluid-flow-vector-closure/report.md` — `1E2055FD46BE326E258A009804B6E0BB447B60ED4901427BF32D0A1BF876BF51`
- `fluid-height-branch-closure/report.md` — `8D64ADB543F7B6509557FE08BD598A18EEA8F7DCCB2D5B2E6712C3BDC818DC8F`
- `collision-contact/lava-fluid-occupancy.md` — `FDBDBBD5875C3D5CAD6DEE44AE62B434F6E2AED1C5967F88B0AFAD64C208D9E3`
- `collision-contact/fluid-current-minimum-push.md` — `D7F40478D006E10DF4F346732F839AFCB0788AE92BAB778B86BACCD7B625275C`

No product, wiki, backlog, build, test, game launch, or decompilation work was performed.
