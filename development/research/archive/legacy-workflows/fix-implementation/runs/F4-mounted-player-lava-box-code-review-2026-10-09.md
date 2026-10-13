# F-4 mounted-player lava-box code review

- Candidate reviewed: `8de4544ee3519981f42bb7787741be47b4c53318`.
- Result: **NEEDS CHANGES**.
- Reviewer: independent code reviewer `/root/f4_code_review`; review completed 2026-10-09.
- Finding: the candidate widens the single `EntityFluidInteraction.update` box used for the full fluid scan. This also widens WATER queries and can update water trackers for a non-underwater boat passenger, while 1.21.11 skips WATER updates for that case and separately scans LAVA using the deflated entity box.
- Requested correction: scope the historical bounds change to LAVA or preserve the old WATER skip without duplicating the fluid loop.
- Other reviewed points accepted: nullable box signature, player/boat/underwater gates, `V1_21_11` emulation key, single catalog registration, and fit with existing dispatch structure.
- Runtime/build checks: none, as instructed.
- Follow-up status: the candidate now adds a targeted fluid-state behavior to suppress WATER states for the same historical boat case while retaining the shared loop. Independent re-review is pending; this record does not accept that follow-up.

## Follow-up review

- Candidate: `c31c2caca4448f0dd143362a01a8217f13bcf2a1`.
- Result: bounded **ACCEPT**, independent focused re-review by `/root/f4_code_review`, 2026-10-09.
- The reviewer verified that the redirect matches `BlockGetter.getFluidState(BlockPos): FluidState` and captures the enclosing `Entity` argument; `Fluids.EMPTY.defaultFluidState()` is empty and therefore skips height, eye, tracker, and current processing for guarded WATER cells. The common traversal remains shared and still processes LAVA. Per-cell dispatch adds work but did not present a material correctness issue within the reviewed bounds.
- No build, test, or runtime checks were run.
