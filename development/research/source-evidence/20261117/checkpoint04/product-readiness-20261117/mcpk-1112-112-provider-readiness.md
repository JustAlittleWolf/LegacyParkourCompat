# Product readiness: 1.11.2 → 1.12 movement operations

**Scope:** effective-provider correspondence for five independently accepted source operations. The source pair remains partial; this artifact makes no pair-completion, full-parity, or runtime claim. No product code or Git history was changed.

## Frozen accepted inputs

Coverage report `build/source-early-patches-20261117/1.11.2--1.12/coverage.md` SHA-256 `da8f52d9b0e6e6445612cdf1f80096712e10b824f72c3d499f829b6024313514`.

| Operation | Accepted source report SHA-256 | Independent ACCEPT SHA-256 |
| --- | --- | --- |
| Fence neighbor-face collision | `3c5bb22d0700858739e0815ffc295c119aab0787d846705c02fd9db4835c6066` | `B2C3D12448447F22433AB648D92CB63E2BCB5E75037A38EF9BBD836268BC2075` |
| Wall neighbor-face collision | `d9012e7c589f6a72a0716f17e19ab43a060772c8c1e5bd93df371e5656ac1d8d` | `2FC8C608E6B5ECF75DABB530D4448EFE94466C6476754850048990CD15D7129F` |
| Pane neighbor-face collision | `a9d9c1ecc4ab32b8e8b805d38fdda431a6903988bdc1bfca624db4980c21ca2e` | `75592D9FEF09BDC430B91231934F2E052C48D7399C77AA56B9ABFD2D64BA6EDD` |
| Bed bounce response | `30b0194e7b5ac3b4da60e0b6a3a97fcb853774f1d7609e919d552f4fefd56d4a` | `3E425C3A10669F754574187122CB6A5C54B5258B0F10E320AAAC2065588E5F8B` |
| Liquid-flow face classification | `54839855489d9f07faf9b8444b20832919ffa507e350201f43ed9b620b120b74` | `A1C59025C49105711D0379605079A0A6C9F70BCC9EB3DA45AF1CEDA8C6C039CB` |

The coverage report and all five report/audit files were rehashed during this pass and matched these supplied identities.

## Resolver mapping

`1.11.2` maps to `V1_11_2` (ordinal 10); `1.12`, `1.12.1`, and `1.12.2` map to `V1_12` (ordinal 11). `ChangeResolver` picks, per interface/key, the lowest annotation ordinal greater than or equal to the selected profile. Consequently an older annotation can remain selected for a newer profile when no nearer provider exists.

## Dispositions

### Fence neighbor-face collision — GAP at V1_12; V1_11_2 covered

Canonical association: `development/research/findings/1.11.2--1.12.2/fence-stair-collision-arm.md` (the same neighbor predicate feeding collision arms); related exception finding: `fence-cauldron-side-collision.md`.

`FenceCollisionState @V1_11_2` is the latest provider for `FenceCollisionStateHook`, so it resolves for both selected profiles. The local `CrossCollisionBlockMixin` applies its old mask: any fence gate connects regardless of facing and stairs disconnect. This matches the accepted 1.11.2 side but conflicts with the 1.12 direction-sensitive face rule. `FenceLegacySolidConnections @V1_12` is a distinct `BlockCollisionShapeHook` for frosted ice/TNT; it does not supersede the active fence-state provider.

**Minimal contract:** bound the old fence-state behavior at V1_11_2 and implement or admit the accepted V1_12 direction-sensitive gate/stair behavior at V1_12. Native fallback is suitable only after the accepted inputs are shown to match current native behavior.

### Wall neighbor-face collision — COVERED_NO_CODE for bounded gate/stair masks

Canonical association: `development/research/findings/1.11.2--1.12.2/wall-neighbor-connectivity.md`.

`WallNeighborMask @V1_11_2` and `WallNeighborMask @V1_12` resolve at their matching profiles. `WallBlockMixin` dispatches the chosen mask. The older provider retains the 1.11.2 gate/stair rule; the V1_12 provider uses `WallNeighborMaskSelection` for directional face classification. This covers the accepted bounded mask cases only; broader wall-state and virtual-combination coverage remains partial.

### Pane neighbor-face collision — COVERED_NO_CODE for accepted leaves/stair arm cases

Canonical association: `development/research/findings/1.11.2--1.12.2/pane-stair-collision-arm.md`; related separate exception coverage: `pane-barrier-collision-arm.md`.

Both `PaneCollisionShape` and `PaneNeighborEligibility` have V1_11_2 providers; V1_12 has `PaneCollisionShape` and `PaneNeighborFaceShapePolicy`. The local-player `BlockStateCollisionShapeMixin` dispatches them through the eligibility hook. The old provider treats leaves as cube neighbors; the V1_12 finite face-shape helper recognizes the mapped leaves IDs but does not admit them as solid-face connections. The selected shape paths match the accepted distinction.

### Bed bounce collision response — GAP at V1_12; V1_11_2 covered

Canonical association: `development/research/findings/1.11.2--1.12.2/bed-bounce.md`.

The sole `BlockBounceHook` implementation is red-bed-keyed `BedBounce @V1_11_2`, which returns zero for Players. With no V1_12 provider, the old zero coefficient remains selected at V1_12. The `BlockLanding @V26_1` bed variant consumes this hook through the `EntityMixin` local-player collision-restition redirect. This matches 1.11.2 but suppresses the accepted 1.12 non-sneaking player response (`0.66F` coefficient for negative Y landing).

**Minimal contract:** add a V1_12 red-bed continuation that passes through the normal bed coefficient for the accepted non-sneaking player case, while retaining V1_11_2 zero-bounce behavior and native sneaking/non-player behavior.

### Liquid-flow face classification — GAP at V1_11_2; V1_12 native correspondence unresolved

Canonical association: `development/research/findings/1.11.2--1.12.2/level-eight-water-bed-face.md` (same `LiquidBlock.isFaceSolid` → `getFlow` → player-current operation; the new accepted stair input is distinct from its bed witness).

`FlowingFluidMixin` dispatches `FallingFluidFaceSturdyHook` on local-player flow queries. Relevant providers are bed-keyed `NativeWaterBedFallingFluidFace @V1_10_1` and `WaterBedFallingFluidFace @V1_11_2`; stair-keyed `NativeStairFluidFace @V1_12` returns the native face result; `LegacyStairFluidFace @V1_13_1` explicitly rejects horizontal water/stair faces. Thus selected V1_11_2 currently resolves a V1_12 native no-op for stairs, not the accepted old material-solid predicate. For selected V1_12, the same no-op does not itself enforce the accepted source rule that stairs are excluded; the current native result for the exact accepted stair input has not been established here, so V1_12 is **not** marked covered.

**Minimal contract:** restrict any continuation to era-existing stair IDs, horizontal water-face queries, and the accepted `LEVEL >= 8` current branch; preserve material-solid stair admission for V1_11_2 and the explicit stair exclusion for V1_12. Leave unrelated fluid types, unsupported/modern-only IDs, and other face classes native. First close native fallback correspondence for the exact V1_12 stair input before treating that profile as covered or adding a redundant provider.

## Separate accepted item held pending

The 1.8.9→1.9 explosion player-vector admission correspondence is not terminalized in this artifact. Its readiness remains pending the source coordinator’s exact applicability classification requested by root.

## Limits

This pass maps source-accepted operations to current selected-profile providers and client hook callsites only. It did not rediscover source behavior, review candidate code, edit product/history, or run builds/tests/runtime. The pair’s broader movement inventories and runtime parity remain open.
