# Product readiness: V1_12 native stair-face fallback

Disposition: **GAP at selected V1_12** for the bounded horizontal falling-water face query. This is a static current-source/provider comparison only; no code or history was changed.

## Accepted operation and profile

The accepted 1.11.2→1.12 liquid-face report is `build/source-early-patches-20261117/1.11.2--1.12/findings/liquid-flow-face-classification.md`, SHA-256 `54839855489d9f07faf9b8444b20832919ffa507e350201f43ed9b620b120b74`; its independent ACCEPT is `build/source-early-patches-20261117/1.11.2--1.12/findings/liquid-flow-face-classification/audit/verdict.md`, SHA-256 `A1C59025C49105711D0379605079A0A6C9F70BCC9EB3DA45AF1CEDA8C6C039CB`. It says 1.12 side faces explicitly reject stairs. The selected profile is V1_12 ordinal 11, covering 1.12, 1.12.1 and 1.12.2.

## Effective product/native path

`FallingFluidFaceSturdyHook` is the operation (`fluid.falling_face_sturdy`). `NativeStairFluidFace @V1_12` is the sole matching stair-key provider for these selections and simply returns `vanilla.get()`. `FlowingFluidMixin#legacyparkourcompat$fallingCurrentFace` wraps `BlockState.isFaceSturdy(BlockGetter, BlockPos, Direction)` during `FlowingFluid.isSolidFace` and resolves the hook by neighbor block for the local-player current query. Thus the current V1_12 provider deliberately delegates the stair result to 26.2 native behavior.

Bound current source identity is the ready 26.2 unobfuscated decompile (`decompiled_minecraft/26.2/unobfuscated.ready.json`, SHA-256 `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F`; source sidecar SHA-256 `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`). Relevant source file SHA-256 values:

- `world/level/material/FlowingFluid.java` `F8C0C4430E81EFDF39B221968272ED8E3A4D030FA5192E04E04D34C9FBD37B28`
- `world/level/block/StairBlock.java` `2A8AD6EEDC5A151AF7A739CCD476F18CAB4FE78E9DF7C1EC601CB9687A77630B`
- `world/level/block/SupportType.java` `EA19A02F2559AFE4A8E4C0F6E12CD1480ABB0F62FE6853E37FA93771BD7DBEDC`
- `world/level/block/Block.java` `CEC6A05E644E4A7FEB8253CC4CA772A98F0E116FB098A1B7EE7302984AC7ECAB`
- `world/level/block/state/BlockBehaviour.java` `9C7A103492D0714C90397DA88EB696912FF6A9CA1C005D984C4746D52637FD1E`
- `world/phys/shapes/VoxelShape.java` `419A46BBCC8B48E1C075127954EB243DCC60D10D4087BF256D895CF91A2FF854`

`FlowingFluid.isSolidFace` returns false for same-liquid neighbors and ice, true for UP, and otherwise calls `state.isFaceSturdy`. The no-argument-support overload uses `SupportType.FULL`; FULL asks `Block.isFaceFull` on the block support shape, whose default is the collision shape. `StairBlock.getShape` constructs the straight stair as the bottom half plus the upper half-width step and rotates it by facing. Consequently a horizontal end-face slice of a straight stair can occupy the full square. For a north-facing bottom straight stair queried on its north face, native full-face sturdiness can be true. The accepted 1.12 rule excludes stair side faces regardless of that native geometry. Therefore the V1_12 native-predicate fallback does not preserve the accepted result for every eligible stair orientation/state; the no-op provider is not coverage.

## Scope and limit

This establishes an effective-provider gap for the accepted stair input only. The independently assigned V1_11_2 candidate remains bounded to restoring the older material-solid result. The V1_12 correction should be reconciled by the product integrator with the existing V1_12 keyed native stair provider so that horizontal water stair queries return the accepted explicit exclusion, while unsupported keys/directions/fluids preserve native fallback. No general stair behavior, movement trajectory, runtime parity, or pair-wide claim is made. The loaded-area preflight, flow aggregation, and other face cases are outside this disposition.
