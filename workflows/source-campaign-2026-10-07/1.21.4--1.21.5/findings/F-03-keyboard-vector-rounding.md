# F-03: Keyboard diagonal input is normalized in float before travel in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S1-INPUT-SAMPLE, S1-INPUT-SCALE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `net/minecraft/client/player/KeyboardInput.java`, `KeyboardInput#tick()` lines 20-31 (SHA-256 `c885bcc709e445c3ff91f03bf37acf9320e17fc9daf1bd2ba46ba20487f1d9ed`) writes raw `leftImpulse` and `forwardImpulse` values from `calculateImpulse`. `ClientInput.java` lines 5-13 (SHA-256 `0f2e4d03749ab7495e386af05aa77a0414b5d1c3ef76256db2b514f42cc6e3c4`) exposes those float fields. `LocalPlayer#serverAiStep()` lines 607-617 and `LivingEntity#aiStep()` lines 2766-2784 copy the raw fields into `xxa`/`zza`, multiply them by `0.98F`, then travel. `Entity#getInputVector(Vec3, float, float)` lines 1429-1441 in Entity.java (SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`) performs Vec3 length/normalization and yaw rotation.
- B: `ready/1.21.5/mojmap.sources.sha256`; `KeyboardInput.java`, `KeyboardInput#tick()` lines 22-31 (SHA-256 `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`) stores `new Vec2(left, forward).normalized()`. `Vec2#normalized()` lines 48-51 (SHA-256 `b637ef6899de5d8892e19273b114ff0397761e509170a56d52dd068f54bffe`) computes a float square root and float divisions. `LocalPlayer#modifyInput(Vec2)` / `modifyInputSpeedForSquareMovement(Vec2)` lines 623-658 (LocalPlayer.java SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`) apply float scales and square-speed correction before setting `xxa`/`zza` in `applyInput` lines 607-621. `LivingEntity#aiStep()` lines 2712-2717 invokes `applyInput` before jump/travel; B `Entity#getInputVector(Vec3, float, float)` lines 1458-1470 (Entity.java SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`) still performs the shared Vec3 length test and yaw rotation.

## Source-level difference

For simultaneous forward-plus-side keyboard input, 1.21.4 keeps two raw unit impulses through the local-player setup and reaches `Entity#getInputVector` with the scaled Vec3, which normalizes in double precision when its squared length exceeds `1.0`. 1.21.5 first normalizes the two keyboard axes as a float `Vec2`, then scales and applies the new square-movement correction before the same Vec3 consumer. The client player path is supplied by `KeyboardInput`; the source inventory found it as the only `ClientInput` subclass and both `ClientPacketListener` construction sites install it for the local player.

The ideal diagonal direction and magnitude are normalized in both paths. The operation order and precision differ: B rounds the direction in float before the later movement speed and yaw operations, while A carries the scaled raw components into a double Vec3 normalization. The new correction also preserves scaled diagonal movement under the item-use and sneaking multipliers.

## Reachability and dependencies

Local key state -> `KeyboardInput#tick` -> `LocalPlayer.aiStep` input sampling -> A `serverAiStep` / B `applyInput` -> `LivingEntity.aiStep` -> player travel -> `Entity#getInputVector` -> acceleration/velocity. A uses the public `ClientInput` impulses; B reads `ClientInput.moveVector`. Item-use and sneaking modifiers are state inputs. `Attributes.SNEAKING_SPEED` supplies the sneaking scale; its producer/default inventory remains open in S6.

## Consequence and uncertainty

The source proves the precision and operation-order change. This can change low-order float/double bits in diagonal per-tick acceleration, which later travel arithmetic can propagate into position. It does not establish a measured trajectory or the size/persistence of any difference. Single-axis and zero keyboard input do not exercise diagonal normalization. Exact rounding through the downstream movement branches remains part of the open travel slices.

## Handoff

Independent delta: keyboard diagonal input normalization and its float operation order. Related finding IDs: none. Applicability is local keyboard diagonal movement, with item-use/sneaking modifiers included in the path. Exact first release within the pair is unknown.
