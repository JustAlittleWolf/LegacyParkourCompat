# Independent verdict: 1.21.10 → 1.21.11 fluid contact

## Adjudication target

Compared the immutable source report at `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-ac27-7712-9259-3041950bc340/lane-fluid-contact-12110-12111/comparison.md`, SHA-256 `d4bee3bc70d586f446f32824d074a06d46fe4bcc30a30326792c937a9558b4a2`. I independently verified its UTF-8 bytes, absent BOM, absent CR bytes, and hash. Verdict scope: lava-current coefficient input and built-in dimension defaults; water/lava contact scan and unloaded-chunk preflight; ordinary on-foot player water/lava vertical fall and collision exit.

## Source identity

Canonical Mojmap roots are `decompiled_minecraft/1.21.10/mojmap/` and `decompiled_minecraft/1.21.11/mojmap/`. Version-root ready markers name the matching releases and mapping. I revalidated each full source manifest: every listed file exists and hashes correctly, source count and bytes match the marker, and the declared source-manifest, artifact-manifest, and diagnostics SHA-256 values match disk. The marker/provenance/source-manifest hashes are:

| Release | Files / bytes | ready marker | provenance | source manifest |
| --- | ---: | --- | --- | --- |
| 1.21.10 | 6,386 / 28,999,290 | `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0` | `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` |
| 1.21.11 | 6,622 / 29,464,971 | `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf7b5fcae555`

The frozen relevant source roster was verified against those manifests. Key source SHA-256 values:

| Release | Source path | SHA-256 |
| --- | --- | --- |
| 1.21.10 | `net/minecraft/world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` |
| 1.21.11 | `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 1.21.10 | `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| 1.21.11 | `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| 1.21.10 | `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| 1.21.11 | `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| 1.21.10 | `net/minecraft/world/level/dimension/DimensionType.java` | `bcfe113b27408b48c0ed09308910306ce2f85338297623bf95a967f9b194bead` |
| 1.21.10 | `net/minecraft/data/worldgen/DimensionTypes.java` | `c7b5c103321338501b11e2b4e065bc54f85d8ef2582822b79997877655b454c3` |
| 1.21.11 | `net/minecraft/world/level/dimension/DimensionType.java` | `fc67fd2e178bbb95a252028b346268ee4097f1b0734649c2557ae41f26c8ec23` |
| 1.21.11 | `net/minecraft/data/worldgen/DimensionTypes.java` | `532319fc7694da6007767e3ce521e5e7ea3a7926c1e782eba132caf03cd202d3` |
| 1.21.11 | `net/minecraft/world/attribute/EnvironmentAttributes.java` | `a35798b671dc8c2467c0b3278a697af846b184567b8493fc0d9c4141085e2a46` |
| 1.21.11 | `net/minecraft/world/attribute/EnvironmentAttributeMap.java` | `5a851614620201fd26c9c8de8d08958f26bb6d0fd5719db2f8865fbe0ae0a41e` |
| 1.21.11 | `net/minecraft/world/attribute/EnvironmentAttributeSystem.java` | `a8ad8c05fec7e241e2ba63ad377a3d2e363be4b9bd5e8482b3d32552a3f4c542` |
| 1.21.11 | `net/minecraft/world/level/material/LavaFluid.java` | `8fabbb9a75d7620963f7a44141280bb08e0363ece7a425fcd37334e422af5abb` |

## Verdicts

### Conditional lava-current movement difference: the source report underclassifies it

The report correctly identifies the operation change but its blanket “out-of-scope input change” conclusion is too strong for player movement. In 1.21.10, `Entity.updateInWaterStateAndDoFluidPushing` passes `level().dimensionType().ultraWarm() ? 0.007 : 0.0023333333333333335` to the lava-tagged fluid-pushing scan (`Entity.java:1483–1488`). In 1.21.11, the same call uses `level.environmentAttributes().getDimensionValue(EnvironmentAttributes.FAST_LAVA)` (`Entity.java:1501–1506`). For a non-flying player in loaded flowing lava, that scalar directly scales the current vector added to player velocity. If the two input values differ, the current impulse changes; low-current minimum handling can affect the final magnitude, so it is not always a simple 3× final-velocity delta.

The inputs are both data-driven but are not the same declared field. The old `DimensionType` record contains `ultraWarm`; its codec reads the required `ultrawarm` boolean (`DimensionType.java:25–56`). The new `DimensionType` has no `ultraWarm` component; its codec accepts an optional `attributes` map defaulting to `EnvironmentAttributeMap.EMPTY` (`DimensionType.java:19–39, 71–97`). `FAST_LAVA` is separately registered as `minecraft:gameplay/fast_lava` with default `false` (`EnvironmentAttributes.java:126–128`); `EnvironmentAttributeSystem` layers the selected DimensionType's attribute map and falls back to the attribute default when no value exists (`EnvironmentAttributeSystem.java:39–47, 96–103`). Thus a missing new `FAST_LAVA` declaration means false; it does not mean “reuse the old `ultrawarm` value.”

For built-in bootstrap values only, the inputs agree: resource keys are `minecraft:overworld`, `minecraft:the_nether`, and `minecraft:the_end` in both `BuiltinDimensionTypes.java` files. The 1.21.10 `DimensionTypes.bootstrap` supplies old `ultraWarm` values false/true/false (record order at `DimensionType.java:25–40`; registrations at `DimensionTypes.java:14–53`). In 1.21.11's `DimensionTypes.bootstrap`, the shared overworld attribute map and End map omit `FAST_LAVA`, so the attribute default gives false; the Nether map explicitly sets it true (`DimensionTypes.java:26–54, 71–85, 104–113`). This proves default bootstrap agreement, not equivalence for arbitrary datapack-defined dimensions.

I found no exact dimension-type JSON/world payload for the target parkour world in the decompiled source publication or supplied comparison. Therefore the exact custom-world applicability remains unresolved. The defensible result is a **source-confirmed conditional player-current delta with an open world-input gate**: retain the delta as a candidate; do not claim it affects a particular map until both releases' dimension-type data are examined. This finding is directly about the player lava-current impulse, not a claim of global mismatch for built-in dimensions.

Adjacent `LavaFluid` methods also replace dimension `ultraWarm` reads with `FAST_LAVA` for slope-find distance, drop-off and tick delay (old `LavaFluid.java:151–177`; new `LavaFluid.java:150–175, 234–235`). Those affect fluid spread/world block-state production and do not establish player movement divergence with a fixed set of fluid states; they are outside this movement verdict. No exact resource payload was supplied to establish whether those inputs were migrated equivalently either.

### No-difference scan/preflight: uphold, conditional on fixed fluid-state inputs

`Entity.updateFluidHeightAndDoFluidPushing(TagKey<Fluid>, double)` is text-identical across the releases (independently extracted full-method SHA-256 `4032b3f085235fd42e9e8d0715bc2f40d4007e8541d625762e80e446fb615e99` on both sides). The unloaded-chunk preflight `touchingUnloadedChunk()` is also text-identical (SHA-256 `3c1b1f3030c288ad4b64c9fb31701ceabf1a2b03ea6a4b13e89082a67372127b`). The scan still returns before reading fluid cells when its inflated player box touches an unloaded chunk, then deflates by `0.001`, uses the same floor/ceil and x/y/z bounds, tag predicate, surface acceptance, maximum depth, shallow-flow scaling, contribution averaging and low-current adjustment. `FluidState.getFlow`, `FluidState.getHeight`, and `FlowingFluid.getFlow` are also text-identical. The new coefficient described above is a separate input to the unchanged scan.

The native tag identities remain `minecraft:water` and `minecraft:lava`; the report's claim that the contact scan uses these tags is supported. This verdict establishes scan algorithm parity at fixed fluid states, tags and coefficient, not fluid-world evolution parity. In particular, `LavaFluid` changed dimension-input reads as noted above.

### No-difference vertical fall, water/lava travel and local-player dispatch: uphold

`LivingEntity.getFluidFallingAdjustedMovement(double, boolean, Vec3)` is text-identical (SHA-256 `ce5ebc4714b96d9f513356e3fe0ab075596c47fe5882eab793ec2e69c27c8974`). The descending `-0.003` branch, gravity/16 subtraction, sprint/zero-gravity gates, and copied x/z values are unchanged.

The 1.21.11 `LivingEntity.travel` split into `shouldTravelInFluid`, water/lava helpers and `jumpOutOfFluid` preserves the same ordinary fluid predicate and movement equations/order from 1.21.10: water branch before lava, movement and collision Y override, water drag and falling adjustment; shallow-lava scaling/falling adjustment or deep-lava scale, gravity addition, then horizontal-collision/free-space exit test and Y `0.3F`. The new `floatInWaterWhileRidden` helper is guarded by vehicle/tag/depth conditions and does not run for an ordinary on-foot player. `Player.travel` still calls `super.travel` for the ordinary on-foot, non-flying client path; `Player.isPushedByFluid` is still `!abilities.flying` in both versions. No player-specific vertical fall/exit difference was found in this slice.

## Overall disposition

**Partial uphold.** The report is correct on the unchanged loaded-chunk scan, fluid scan procedure, vertical fall adjustment and ordinary on-foot fluid travel/exit. Revise its lava-current disposition: the input replacement is a direct player movement change for data-driven dimensions whose old `ultraWarm` and new `FAST_LAVA` values differ. Built-in defaults agree, but the supplied evidence contains no target world/datapack resource identity, so map-specific applicability must stay open. No runtime parity was tested or inferred.