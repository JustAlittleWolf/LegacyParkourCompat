# Lava travel branch — 26.1.2 → 26.2

Source-only endpoint comparison; this report covers only the lava-specific `LivingEntity.travel` branch and its direct `Player.travel` gate. Pair coverage remains partial. No intermediate introduction point, acceptance, implementation, or runtime parity is claimed.

## Exact source identities

Both inputs are exact unobfuscated client source trees. Ready markers, provenance sidecars, source manifests, artifact manifests, and the cited Java sources were rehashed for this revision. Every source-manifest entry was rehashed against its source file; file counts and total bytes match the ready markers.

- **26.1.2 unobfuscated** — ready marker `decompiled_minecraft/26.1.2/unobfuscated.ready.json`, SHA-256 `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7`; provenance `unobfuscated.provenance.json`, SHA-256 `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`; source manifest `unobfuscated.sources.sha256`, SHA-256 `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C` (6,882 files; 32,992,615 bytes); artifact manifest `artifacts.sha256`, SHA-256 `89F648229DE83B0109460695C9F1F3BED7EF36EFB84EDE9224E80CCE87118F92`. Ready metadata declares version `26.1.2` and mapping `unobfuscated`; the artifact manifest records client SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`. Provenance identifies Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, Mapping IO 0.9.1, ASM 9.10.1, TinyRemapper 0.14.1, Gson 2.14.0.
- **26.2 unobfuscated** — ready marker `decompiled_minecraft/26.2/unobfuscated.ready.json`, SHA-256 `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F`; provenance `unobfuscated.provenance.json`, SHA-256 `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1`; source manifest `unobfuscated.sources.sha256`, SHA-256 `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`; artifact manifest `artifacts.sha256`, SHA-256 `BA9DC53A41BCA9744EC1C8AB2CE9363764A22333C39EF64D5105F4E3BF5C98BA` (7,055 files; 33,813,717 bytes). Ready metadata declares version `26.2` and mapping `unobfuscated`; the artifact manifest records client SHA-256 `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`. Provenance identifies Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, Mapping IO 0.9.1, ASM 9.10.1, TinyRemapper 0.14.1, Gson 2.14.0.
- The ready markers' declared source-manifest hashes and artifact-manifest hashes match these sidecars. The client jar files were not present under the ready output paths; comparison uses the ready source trees and records the declared client identities above.

## Compared members and ordered coverage

1. `net/minecraft/world/entity/LivingEntity.java` — `LivingEntity.travel(Vec3)`: 26.1.2 lines 2392–2400; 26.2 lines 2430–2438. The outer fluid/fall-flying/air dispatch is unchanged in this slice.
2. `LivingEntity.shouldTravelInFluid(FluidState)`: 26.1.2 lines 2406–2408; 26.2 lines 2444–2446. The predicate remains `(isInWater() || isInLava()) && isAffectedByFluids() && !canStandOnFluid(fluidState)`.
3. `LivingEntity.travelInFluid(Vec3)` branch to `travelInLava`: 26.1.2 lines 2455–2465; 26.2 lines 2504–2514. The water-vs-lava selector remains `if (isInWater()) ... else travelInLava(...)`; shared setup and water travel are outside this slice. The method visibility changes from `private` in 26.1.2 to `protected` in 26.2. In the 26.2 source tree, `SulfurCube.travelInFluid(Vec3)` overrides it at `SulfurCube.java:184` and calls `super` before its Sulfur Cube-specific float effect. No `Player.travelInFluid` override was found; `Player.travel(Vec3)` delegates through `super.travel(input)` on the passenger, flying and ordinary routes in both releases (26.1.2 `Player.java:1381–1404`; 26.2 `Player.java:1402–1425`). The source-tree `travelInFluid` override found is on `SulfurCube`, not a Player subtype. Thus the ordinary vanilla Player lava branch uses the base `LivingEntity` route. The Sulfur Cube override's additional behavior is non-player behavior and is outside eligible player movement.
4. `LivingEntity.travelInLava(Vec3,double,boolean,double)`: 26.1.2 lines 2496–2512; 26.2 lines 2549–2565.
5. New 26.2 helper `LivingEntity.isInShallowFluid(TagKey<Fluid>)`: lines 2545–2547. It is `protected`, so it is an extension point; its base body is exactly `getFluidHeight(fluidTag) <= getFluidJumpThreshold()`.
6. `LivingEntity` lava jump gate: 26.2 lines 3100–3117. The gate also calls `isInShallowFluid(FluidTags.LAVA)` when in lava and on ground. No override of `isInShallowFluid` was found anywhere in the exact 26.2 source tree. No player-class override was found for `travelInFluid`; the base helper invocation in the vanilla Player path therefore retains the base predicate. A possible changed result from an external subclass override is not treated as an eligible Player movement difference without a proven Player-reachable override.

## Lava branch comparison

26.1.2 evaluates shallow lava inline as `getFluidHeight(FluidTags.LAVA) <= getFluidJumpThreshold()`. 26.2 calls the protected `isInShallowFluid(FluidTags.LAVA)`, whose base body is that same expression. The helper is also used by the lava jump gate described above. Therefore the base implementation preserves both the lava travel predicate and the added helper call site in that gate; subclass overrides can alter those call results, but no such override is present in the vanilla Player-reachable source paths reviewed here.

On the shallow path both versions, in order, apply `moveRelative(0.02F, input)`, `move(SELF, deltaMovement)`, multiply velocity by `(0.5, 0.8F, 0.5)`, pass it to `getFluidFallingAdjustedMovement(baseGravity, isFalling, ...)`, and store the returned movement. On the deep path both scale velocity by `0.5`. Both then, only when `baseGravity != 0.0`, add `(0.0, -baseGravity / 4.0, 0.0)`, and finally call `jumpOutOfFluid(oldY)`. No base lava-specific acceleration, depth comparison semantics, damping, vertical adjustment, operation order, or direct Player travel gate difference was found in this bounded comparison.

## Dependencies and limits

Shared fluid-state setup/current production, `isInLava`/fluid-height production and effective lava properties/effects/resources are not closed here. Subclass extension behavior is recorded structurally but is not accepted as Player movement absent a source-proven Player-reachable override. The source manifests establish the reviewed endpoint source trees; root ready/provenance/source/artifact sidecars and the cited source-file hashes were checked. This is only the assigned lava travel slice; air/climb, fluid exit, vertical fluid adjustment, water travel, and other assigned slices remain separately bounded.

## Cited source hashes

- 26.1.2 `LivingEntity.java`: `C3B64DE8DBABA8AD8A7CCD4F33255346D8206E66F12CA91260971BF5E5AC93BD`
- 26.2 `LivingEntity.java`: `7FFD9C70966EDC50C9CB4D9A8FE17A518E2678FF44C8026E763D0B94AC0AE51A`
- 26.1.2 `Player.java`: `44CF28E0C64E78D39FD13368E9991381DBEBAB67029070CB9DDC43F09D45D14D`
- 26.2 `Player.java`: `8DECC71B9C780664578DDB14591DB2A2F207C72C05B676EDDED6F8E964576531`
- 26.2 `SulfurCube.java`: `F313BDF1EE00386686970BC29D42553358FA30E3A1E21845386696526C606548`