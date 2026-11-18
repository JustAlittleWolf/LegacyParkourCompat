# 1.14.3 → 1.14.4 yaw precision and ordinary movement use

## Bounded verdict

For the assigned slices, no yaw-math or stored-yaw movement difference was found between 1.14.3 Yarn and either 1.14.4 Feather or Mojmap. The direct local camera-turn operation preserves the same double scaling, float conversion, write order, and lack of yaw finite checking or wrapping. The ordinary dry ground/air travel branch consumes the stored yaw as a `float` and runs the same input-vector math before adding the resulting velocity. This closes only the assigned yaw setter and first ordinary dry ground/air movement-use slices; it is not a full-pair tick/inventory freeze.

## Source identities and hash authentication

- **1.14.3 Yarn:** `decompiled_minecraft/1.14.3/yarn/.decompile-provenance.json` is a legacy nested provenance record (no adjacent ready marker or source-manifest file). Its inputs identify the 1.14.3 client and Yarn `1.14.3+build.13`; its source hashes match the current files: `Entity.java` SHA-256 `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14`, `LivingEntity.java` SHA-256 `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b`.
- **1.14.4 Mojmap:** ready marker reports version `1.14.4`, mapping `mojmap`, 3,250 files. `mojmap.sources.sha256` SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` matches the marker. The relevant manifest entries match current file bytes: `Entity.java` `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`; `LivingEntity.java` `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`.
- **1.14.4 Ornithe Feather:** ready marker reports version `1.14.4`, mapping `ornithe-feather`, 3,250 files. `ornithe-feather.sources.sha256` SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc` matches the marker. The relevant manifest entries match current file bytes: `Entity.java` `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`; `LivingEntity.java` `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.

The two 1.14.4 provenance records name the pinned Feather Gen2 1.14.4 build.2 merged mapping and Tiny artifacts. The Mojmap and Feather ready markers and mapping-specific source manifests were checked independently.

## Direct local yaw update

- **1.14.3 Yarn:** `decompiled_minecraft/1.14.3/yarn/net/minecraft/entity/Entity.java`, `changeLookDirection(double d, double e)`, lines 314–325.
- **1.14.4 Feather:** `decompiled_minecraft/1.14.4/ornithe-feather/net/minecraft/entity/Entity.java`, `updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)`, lines 317–329.
- **1.14.4 Mojmap:** `decompiled_minecraft/1.14.4/mojmap/net/minecraft/world/entity/Entity.java`, `turn(double d, double e)`, lines 319–331.

All three compute the yaw delta in `double` as horizontal delta `* 0.15`, then assign yaw using `(float)(yaw + yawDelta)`. They subsequently update the previous-yaw field using the same `(float)(previousYaw + yawDelta)` expression. The update order is unchanged. This operation does not clamp or wrap yaw and has no finite-value guard. The pitch statements are outside this yaw-only comparison; input scheduling/callers are also outside the assigned slice.

The separate protected rotation setter is also unchanged: Yarn `Entity.setRotation` lines 300–303; Feather `Entity.setRotation` lines 303–306; Mojmap `Entity.setRot` lines 305–308. Each writes `yaw % 360.0F` (Java float remainder, not normalization into a signed half-turn interval), then does the corresponding pitch remainder. The direct camera-turn operation above does not call this setter.

The position-and-angle update path retains its yaw ordering and precision as well. Yarn `Entity.setPositionAnglesAndUpdate` lines 1091–1114; Feather `Entity.updatePositionAndAngles` lines 1098–1121; Mojmap `Entity.absMoveTo` lines 1116–1139. Each writes input yaw to current and previous yaw, computes previous-minus-input yaw into `double`, applies single `if` adjustments at `< -180.0` and `>= 180.0` using `360.0F`, then calls the modulo rotation setter. There is no yaw finite check. The separate simple position setters store the supplied yaw directly without modulo: Yarn `setPositionAndAngles` lines 1120–1133, Feather `setPositionAndAngles` lines 1127–1140, Mojmap `moveTo` lines 1145–1158. These are not the camera-turn operation.

## First stored-yaw use in normal dry ground/air travel

The relevant branch is the non-water, non-lava, non-fall-flying branch; for the ordinary local player it reaches the dry movement path under each version's existing movement gate.

- **1.14.3 Yarn:** `LivingEntity.travel(Vec3d arg)`, lines 1779–1840, selects ground friction at lines 1834–1836 and calls `updateVelocity(...)` at line 1837. `Entity.updateVelocity` lines 1055–1058 passes `this.yaw` directly to `movementInputToVelocity`; that helper lines 1060–1070 applies the `1.0E-7` squared-length cutoff, normalizes only when squared length is `> 1.0`, scales by the float speed, computes float sine/cosine from `yaw * (float)(Math.PI / 180.0)`, and rotates the input vector in the same operation order as the newer helper.
- **1.14.4 Feather:** the `LivingEntity.moveRelative(Vec3d vec3d)` travel wrapper, lines 1786–1796 and 1840–1846, reaches the matching dry branch and calls `Entity.updateVelocity` at line 1844. `Entity.updateVelocity` lines 1062–1065 passes `this.yaw`; its static vector helper lines 1067–1077 has the same cutoff, conditional normalization, float speed scale, float yaw/trig expression, and component operation order.
- **1.14.4 Mojmap:** `LivingEntity.travel(Vec3 vec3)`, lines 1782–1792 and 1836–1842, reaches `Entity.moveRelative` for dry ground/air travel. `Entity.moveRelative` lines 1080–1083 passes `this.yRot` directly to `getInputVector`; that helper lines 1085–1095 has the same cutoff, conditional normalization, float speed scale, float yaw/trig expression, and component operation order.

The API and names differ across mapping/source families (`updateVelocity` plus helper versus `moveRelative` plus helper), but the first yaw read is the same stored float and the rotation math is unchanged. Within this bounded ordinary travel path, there is no yaw-derived horizontal input-vector delta attributable to the patch comparison.

## Coverage boundary

Compared: direct local camera yaw update; rotation modulo setter; position-and-angle update and simple position setter yaw writes; enclosing local/normal travel gate and first stored-yaw use for ordinary dry ground/air movement, across 1.14.3 Yarn and both ready 1.14.4 source mappings.

Not examined here: pitch behavior, camera/input scheduling, wet/lava/fall-flying movement, remaining tick inventory, dependencies beyond the cited travel/setter paths, other 1.14.x patch boundaries, or runtime parity. No finding is asserted for these source slices.