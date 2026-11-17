# Product readiness: 1.8.9→1.9 Fence / End Portal Frame collision query

**Disposition:** COVERED_NO_CODE for the accepted local `Entity.move` collision-query witness. The V1_8_9 provider supplies the End Portal Frame arm; V1_9 and V1_9_3 selections do not resolve that old provider and retain the native no-frame-arm behavior, matching the accepted 1.9 endpoint. This correspondence is limited to the accepted End Portal Frame neighbor predicate/query case; it makes no pair-completion or runtime trajectory claim.

## Bound inputs

- Accepted query-reachability report: `build/source-189-19-20261117/dependencies/fence-endportal-query-reachability.md`, SHA-256 `6e2c66b465162fc18a8c23ca3e89707995d3ee8118f57809066622e06236703e`.
- Independent audit: `build/source-189-19-20261117/dependencies/fence-endportal-query-reachability-audit/verdict.md`, SHA-256 `bcbf1090ce814fc7978e9541550a870a4d6e8ca3dae471be757fc75f07989748`.
- Canonical operation: `development/research/findings/1.8.9--1.9.4/fence-end-portal-frame-connection.md`.

## Profile resolution and provider path

Literal aliases and enum order: 1.8.9 → `V1_8_9` ordinal 3; 1.9/1.9.1/1.9.2 → `V1_9` ordinal 4; 1.9.3/1.9.4 → `V1_9_3` ordinal 5. The relevant keyed operation is `BlockCollisionShapeHook`, looked up by fence block ID. The discovered fence-keyed providers are:

- `FencePortalFrameConnection @V1_8_9`, with variants for the six wood fences and nether-brick fence. It is the resolved provider for selected V1_8_9 and adds a directional arm when the live adjacent state is `Blocks.END_PORTAL_FRAME` and the stored fence connection bit is absent.
- `FenceLegacySolidConnections @V1_12`, with the same fence variants. It is the closest later provider for selected V1_9 and V1_9_3. Its collision-shape method preserves native fallback when the restored-solid-neighbor mask is zero; the separately selected `LegacyFenceWallNeighborConnectionHook` does not add a mask for End Portal Frames.

The V1_8_9 provider is therefore not mistakenly extended to 1.9 profiles by the resolver. The separate `FenceCollisionStateHook` operation does not add an End Portal Frame arm; its nearest `FenceCollisionState @V1_10_1` provider returns empty, leaving the collision-shape hook/native path to decide this case.

## Query and fallback correspondence

`BlockStateCollisionShapeMixin` dispatches the keyed `BlockCollisionShapeHook` only for the local `LocalPlayer` while movement emulation is active. It runs in the player-context collision-shape query used by the accepted `Entity.move → World.getCollisions` source path. Thus, for selected V1_8_9, a matching fence key selects `FencePortalFrameConnection`, which reads the current neighboring End Portal Frame and supplies the old collision arm even when the native fence state bit is unset.

For selected V1_9/V1_9_3, the selected V1_12 keyed provider returns empty for the accepted setup, so native collision geometry remains active. In current 26.2 native behavior, `FenceBlock.getStateForPlacement` and `updateShape` set fence bits through `connectsTo` and `neighborState.isFaceSturdy(..., queriedFace)`. End Portal Frames are not a connection-exception block; their collision shape is a 13/16-height full-footprint base, optionally with a centered eye column. `BlockState.isFaceSturdy` defaults to `SupportType.FULL`, which requires a full collision-shape face. The frame's horizontal faces remain short of full height with or without the eye, so they are not sturdy; the native fence does not connect to the frame. This is the accepted 1.9 endpoint predicate (false) and keeps the modern native fallback aligned for the witness.

## Limits

The report and audit establish the exact 1.8.9→1.9 conditional query witness. This readiness pass checked current profile aliases, all keyed fence shape providers, hook dispatch, and the 26.2 fallback path. It does not newly establish patch-by-patch applicability across every alias member, other fence neighbor predicates, fence-arm trajectories, or full 1.8.9→1.9.4 pair parity. No code, findings, backlog, or Git history were changed; no build/tests/runtime were run.
