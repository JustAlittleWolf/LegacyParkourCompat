# Independent bounded source review: 1.21.11–26.1.2 fluid checkpoints

- Review date: 2026-10-09
- Decision: **ACCEPT — bounded evidence only; mounted-player applicability remains open**
- Earlier resource checkpoint: `2f13f54bd011a67e10314fc674cb270f68612f1f` (parent `ea6ca59bd217e9ad36ede00122a6952be914839b`)
- Mounted-player checkpoint: `b85cb22afbdc1ffdc7406cec74c1c46ebfdd5a0b` (parent `7c0d08efc8f2817633a80f1f2903f3d36a404592`; the resource checkpoint is an ancestor)
- Frozen review worktree base: `ea6ca59bd217e9ad36ede00122a6952be914839b`
- Reviewed artifact: only `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/run.md` at the two exact commits and the exact source/resource evidence cited for the specified slices.
- Pair status remains active/partial. This is not full-pair acceptance or freeze.

## Publication and evidence integrity

The run's A Mojmap and B native-unobfuscated ready markers, source manifests, artifact manifests, diagnostics files, and exact client jars match their recorded hashes. The ready IDs are 1.21.11 and 26.1.2. All cited bubble, Player, Entity, boat, tracker, provider, and lava-consumer Java file hashes checked against the corresponding source manifests.

The B tag-key declarations resolve to the exact resource IDs: `FluidTags.BUBBLE_COLUMN_CAN_OCCUPY` is created from `bubble_column_can_occupy` (`FluidTags.java:13, 18–20`, SHA-256 `5973c1d50c0ed73bae0149cfc7ad6a68dbdadb815f76ef024bca1a11fba2b653`); `BlockTags.ENABLES_BUBBLE_COLUMN_DRAG_DOWN` and `...PUSH_UP` use their matching names (`BlockTags.java:203–204, 260–262`, SHA-256 `edb77ca338c6e831d47b41d799b2f2cad432f34ee465be33a4d1e69edf0f8c1f`). These declarations connect the provider constants to the resource paths recorded in the run.

B's exact client jar contains the three recorded entries with the matching hashes and values: `bubble_column_can_occupy.json` → water (`10bb807794df0a8039e67485102ca5f423897e66a24b286fe4653ed4a825d51a`); `enables_bubble_column_drag_down.json` → magma block (`c4aa173611f0f072f04e625e5d3d7a7b5e1cdb7f81a54103d7f89e4f4faa487b`); `enables_bubble_column_push_up.json` → soul sand (`34064a586d19afe34ef83fbd356eacfe026b9b3a3569efdf28cd88fea8c4bbb1`). The exact A client jar contains none of those entries. Its `BubbleColumnBlock#canExistIn()` and `getColumnState()` instead check existing bubble columns, full source water, soul sand, and magma directly (A `BubbleColumnBlock.java:93–104`).

## Bubble-column resource and Player path

The three declared D2 subedges are supported for exact vanilla defaults:

- B `FluidTagsProvider` registers water for `BUBBLE_COLUMN_CAN_OCCUPY` (line 23; SHA-256 `3e3fab74c346256ba325b8953f4d6a6d8cf0670b182652c111053b51de8bec05`). B `VanillaBlockTagsProvider` registers magma/soul sand for the drag-down/push-up tags (lines 1209–1210; SHA-256 `e34485a342b377f530a28e4eb81c40163d69a0697acf7cfd19ee13baaab7ffbe`).
- B `BubbleColumnBlock#canOccupy()` checks the occupancy tag, `LiquidBlock`, source state, and amount ≥ 8 (lines 104–114). B `getColumnState()` maps push-up to `DRAG_DOWN=false` and drag-down to `true` (lines 116–125). This is the exact tag-to-state writer edge; the run's D2 summary names the resulting state but does not repeat this method range. The paired A writer uses its hard-coded checks above.
- `BubbleColumnBlock#entityInside()` calls the Player response only on precise contact and chooses above versus inside contact from the block/fluid state above (A lines 50–58; B lines 53–70). The run's paired `Entity#checkInsideBlocks` route under S5.4 covers the call site and target-intersection precision. The Player overrides call the base handler when not flying (A `Player.java:319–329`; B `309–320`).
- The base handlers preserve X/Z and apply the same Y caps/increments. A's inside path resets fall distance through line 2762. The D2 summary cites B through line 2827, while the same run's complete B handler evidence correctly spans `Entity.java:2818–2829`, including the reset at line 2828; this short summary range does not leave the handler unverified.

Thus the recorded default data and hard-coded A cases reach the eligible direct Player vertical response. This accepts only the three exact-client-default resource edges, not arbitrary datapack/tag replacement or the whole bubble-column lifecycle. Vehicle bubble responses and vehicle movement remain outside the finding scope.

## Mounted Player box and lava-height consumer

The mounted route is source-reachable, and the checkpoint correctly leaves its behavioral applicability unresolved. Paired `AbstractBoat#interact()` bodies preserve the ordinary server-side `Player.startRiding()` path (A `AbstractBoat.java:694–703`; B `698–707`); `isUnderWater()` tests the same two boat statuses (A `764–766`; B `768–770`). A's water-current helper skips its WATER scan for a Player riding a boat that is not underwater, while the separate LAVA pass still scans the deflated Player box (A `Entity.java:1501–1522`, scan setup at `3509–3518`). B starts from the deflated Player box, calls the vehicle's passenger-box hook, and the boat returns the original box underwater, null when its top reaches the passenger top, or a box clipped upward to the boat top otherwise (B `Entity.java:4015–4027`; `AbstractBoat.java:783–795`). B's tracker uses that box for the configured WATER/LAVA height and eye scan; its update and loaded-region gate are in `EntityFluidInteraction.java:32–89, 91–101`.

The report does not supply an exact paired boat status/box, Player box, fluid-cell/height, and loaded-chunk witness that demonstrates a different Player result. It correctly keeps this applicability edge open. This review treats the boat's status and bounds only as inputs to the Player fluid query; it does not inspect or classify boat motion/physics.

The paired `LivingEntity#travelInFluid()` methods dispatch non-water fluid to `travelInLava()` (A `2368–2379`; B `2455–2466`). The lava consumer bodies (A `2409–2427`; B `2496–2514`) use the same order: relative movement, SELF movement, `getFluidHeight(LAVA) <= getFluidJumpThreshold()` branch, low-height damping/falling adjustment or high-height half-scaling, gravity quarter-step, then `jumpOutOfFluid`. This verifies the consumer checkpoint's body correspondence; it does not close the producer's mounted box or the threshold inputs.

## Open routes and next action

- Keep D2 open overall. The three bubble subedges are accepted only for the published exact client defaults and A's hard-coded replacements; datapack/tag overrides and the remaining fluid/resource inventory are still open.
- Keep S4.6 mounted-player applicability open until a bounded paired boat-box/status and fluid-cell/load witness either shows a Player response difference or proves the branch cannot produce one.
- Keep `getFluidJumpThreshold()` and eye-height inputs, the eye-state writer/order into `updateSwimming()`, and the other direct eye-state consumer open as the source run records. Do not infer these from the matching `travelInLava()` bodies.
- Preserve the source run's broader open inventories, findings, and reviewer events. The next review action is the named eye-state and mounted-box witness work, followed by a separate review of those exact additions. No other pair coverage is accepted here.
