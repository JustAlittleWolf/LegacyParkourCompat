# Independent source review: F-4 mounted player lava box

- Decision: **ACCEPT**, for the exact bounded source witness in this finding snapshot.
- Reviewer: independent Codex source reviewer, task `review-mounted-lava-f4-2026-10-09`.
- Reviewed: 2026-10-09 18:04 UTC.
- Finding: `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-4-mounted-player-lava-box.md`.
- Immutable finding snapshot: commit `019d5fcef181f6dde2a9630e0b882acfa99d6de9`; Git blob `bcd14cf6c38270229b035a85f929291254c3664b`; raw-file SHA-256 `a49bfdd5e363f9faae5b99fe29cfefe393a958019460063fbb175ef2b053e034`.
- Pair run remains partial/active. This acceptance is not a pair freeze, implementation decision, or runtime result.

## Source and artifact identity

Readiness markers and source-manifest bytes match the identities cited by F-4:

- A: 1.21.11 Mojmap; source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; artifact manifest SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`.
- B: 26.1.2 unobfuscated; source manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; artifact manifest SHA-256 `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`.

I independently recalculated the SHA-256 values of the cited `Entity.java`, B `EntityFluidInteraction.java`, paired `AbstractBoat.java`, paired `Boat.java`, paired `Player.java`, `Avatar.java`, `EntityDimensions.java`, `EntityType.java`, `LivingEntity.java`, and `FlowingFluid.java`; they match the finding. The marker-bound client artifacts are A `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` and B `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`.

## Bounded verification

The mounted Player route is real in both versions: `AbstractBoat.interact()` can call `Player.startRiding(this)` for a normal single-passenger boat. The fixed source geometry is sufficient to establish the queried Player box; this review does not simulate boat motion or infer a boat-physics change.

For the cited yaw-zero `OAK_BOAT` at `(100.5, 65.5, 100.5)`, the paired boat dimensions and `rideHeight()` give `0.5625 / 3 = 0.1875`. The paired rider-placement formula subtracts the Player vehicle attachment `(0, 0.6, 0)`, placing the Player at Y `65.0875`. The Player's standing dimensions are `0.6 x 1.8`; this pose is reachable for the stated non-sleeping, non-swimming, non-fall-flying Player. The Player box clears the Y=64 floor, and its deflated minimum Y is `65.0885`. The fixed boat's dry status is not underwater, so B's passenger-box modifier applies.

In A, `Entity.updateInWaterStateAndDoFluidPushing()` keeps the separate LAVA scan after the mounted-boat WATER early return. `updateFluidHeightAndDoFluidPushing()` scans Y from `floor(65.0885) = 65` through (exclusive) `ceil(66.8865) = 67`, so it visits the Y=65 cell. For the level-1 lava cell, source height is `1/9`; its top is about `65.1111145`, above the scan box minimum. The overlap is positive (about `0.0226145`). With the cited level-2 east neighbor and the remaining stated fluid row, the paired `FlowingFluid.getFlow()` arithmetic yields a nonzero horizontal vector. A's Player path retains the per-cell vector, scales it by the positive overlap and Nether lava coefficient, passes its nonzero-vector check, and reaches the minimum-impulse branch with zero horizontal velocity. `Player.isPushedByFluid()` is true when the stated Player is not flying. The write is to that Player's delta movement.

In B, `Entity.getFluidInteractionBox()` first deflates the Player box and then invokes the vehicle modifier. For this boat state, B clips the interaction box minimum Y to boat `maxY = 66.0625`. `EntityFluidInteraction.update()` calculates `y0 = floor(box.minY) = 66` and `y1 = ceil(box.maxY) - 1 = 66`; its Y loop visits only cell 66. Therefore it never enumerates the target Y=65 cell. The later `fluidTop < box.minY` filter is not what rejects that target. The expanded loaded-region gate covers the same loaded chunk and the vertical section `[64,80)`, which contains Y=65; the target cell is omitted by the scan bounds despite being in a loaded section. B's tracker consequently records no LAVA current from that target, and `Player.isPushedByFluid()` permits current application for the stated non-flying Player. The source-supported current consumer is the Player tracker application, not a boat impulse.

The fixed level-1-to-source row is consistent with the paired `FlowingFluid` spread and flow formulas under the stated Nether `FAST_LAVA` setting. The east level-2 neighbor produces the westward nonzero current described by the finding. The exact fluid layout, fixed geometry/status, and vanilla source state are the boundary of this acceptance.

## Remaining dependencies and limits

- Broader S4.6 fluid/provider and fluid-consumer coverage remains open; this bounded witness does not close that slice.
- The Player jump-input producer and surrounding pre-travel coverage remain with S1/S7; they are not prerequisites for this direct fluid-current write.
- First introduction remains `unknown within (1.21.11, 26.1.2]` because no intermediate version was reviewed.
- Full-pair blind freeze and independent call-graph audit remain pending. Runtime validation was not performed.
- Eligibility is limited to the direct mounted Player fluid box/current response. No boat movement, buoyancy, boat trajectory, or other non-player physics is accepted or proposed for emulation.

This is an independent **ACCEPT** of the cited immutable F-4 snapshot only. Preserve earlier verdicts and mirror this identity-bound decision in the owning run ledger separately.
