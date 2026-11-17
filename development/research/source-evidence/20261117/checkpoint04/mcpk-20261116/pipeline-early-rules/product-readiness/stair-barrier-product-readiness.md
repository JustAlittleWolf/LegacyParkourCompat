# Stair and barrier product readiness

Scope: product mapping for the independently accepted 1.11.2→1.12 source operations in `pipeline-early-rules/stair-barrier-source/source-facts.md` (SHA-256 `8ebc5dbfc6608f93bd8e7df1001473f5304275c464b770f4d2f91f4da5c1e5ca`) and `pipeline-early-rules/stair-barrier-audit/audit-verdict.md` (SHA-256 `3e49b9d6e0fe6844ca1390832dc90d74fd66c96436c567a7371a659fdbf9bfdc`). This is product readiness only; it does not claim complete pair coverage, whole-pair parity, or runtime parity.

## Selected aliases and resolution

The literal `ParkourVersion` declaration orders `V1_11_2` at ordinal 10, `V1_12` at 11, and `V1_13` at 12. The aliases are `V1_11_2` = 1.11.2; `V1_12` = 1.12, 1.12.1, 1.12.2. `ChangeResolver` resolves each mechanic to the lowest registered annotation ordinal greater than or equal to the selected ordinal.

## Pane to stair: COVERED_NO_CODE

For selected `V1_12`, `PaneNeighborEligibilityHook` resolves `PaneNeighborFaceShapePolicy @MovementChange(V1_12)`. `PaneCollisionShape @V1_12` passes each neighboring state and direction through that policy using `PaneCollisionComponents.selected`; the player-context `BlockStateCollisionShapeMixin` invokes the custom shape path during block collision queries. `PaneNeighborFaceShape.connects` routes recognized stair IDs to `stairFaceIsSolid`, accounting for local straight/inner/outer stair shape and the supplied face direction. Unknown cases retain native behavior. This implements the accepted pane directional stair-face predicate for the mapped stair family.

## Pane to barrier: COVERED_NO_CODE

The same V1_12 policy routes barrier through the audited historical-ID set, but barrier is not in the positive pane/base-solid/slab/stair categories; the bounded historical result is `false`, so no arm is emitted toward it. At selected `V1_11_2`, the older `PaneNeighborEligibility @V1_11_2` is selected instead and its old cube-provider set includes barrier, preserving the pre-change behavior. `PaneNeighborEligibilityNative @V1_11_1` preserves native fallback before that.

## Wall to directional stair face: GAP at V1_12

`WallNeighborMaskHook` has `NativeBeforeWallNeighborMask @V1_11_1` (native fallback) and `WallNeighborMask @V1_11_2`. The latter returns a complete old mask for its bounded air/gate/stair domain and sets connection bits only for gates, preserving the 1.11.2 rule. At selected `V1_12`, both annotations are older than the selected alias, so neither resolves. `WallBlockMixin` then uses the current `WallSide` state and native collision shape; no V1_12 wall-neighbor mask provider recomputes the accepted directional stair-face result. Current decompiled `WallBlock` derives ordinary connections from `BlockState.isFaceSturdy`; the default FULL-support path evaluates the stair collision shape, which does not establish equivalence to 1.12's explicit directional `StairsBlock.getFaceShape` SOLID classification. Existing wall shape/geometry providers do not supply the missing neighbor-to-side-bit decision. Candidate scope should therefore remain the minimal V1_12 wall-to-stair neighbor-mask semantics; pane behavior and gate handling are already covered.

## Evidence limits

This disposition is limited to the accepted 1.11.2 and 1.12/.1/.2 operations and the mapped in-product providers described above. It makes no assertion about unexamined releases, runtime behavior, or general wall/pane parity.
