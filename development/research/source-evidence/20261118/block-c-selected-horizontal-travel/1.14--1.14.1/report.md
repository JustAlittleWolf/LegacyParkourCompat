# Java 1.14 → 1.14.1: ordinary local horizontal travel

## Disposition

No source-confirmed horizontal travel delta in the assigned slice. Under the same input vector, movement-speed attribute, block slipperiness, `onGround`, yaw, and dry/unmounted/non-sprinting travel conditions, both releases execute the same input-to-acceleration and horizontal drag operations, in the same order and with the same types/constants. This is a bounded source comparison, not a runtime parity claim.

## Immutable source identity

Both trees are canonical `decompiled_minecraft/<version>/yarn` sources. The SHA-256 of each `.decompile-provenance.json` matches the assigned identity:

| Release | Manifest SHA-256 | Yarn | Client artifact SHA-256 |
|---|---|---|---|
| 1.14 | `EA70A955A12F377CD3B9E8D5F346F4625B044D55DA726B6BC472405AE91F4341` | `1.14+build.21` (pinned mapping SHA-256 `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64`) | `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` |
| 1.14.1 | `308537C5ADEC3104C5CA243EB5010554F00978635ED74F0D7801C804DD4D4907` | `1.14.1+build.10` (pinned mapping SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`) | `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` |

The complete source-file hashes below match the `sources` entries in each manifest. For byte-different enclosing classes, the exact assigned method/caller slices were separately hashed and compare equal across releases.

| Yarn source | 1.14 SHA-256 | 1.14.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/input/Input.java` | `abb37170563ca28cafcf20b27b75036b4ee99cb0ec9775702beef6f97cebc351` | same |
| `net/minecraft/client/input/KeyboardInput.java` | `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0` | same |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `92d0f97a9293f7bd57d49af00b70f13d78fc8b2202e7ed67edcfed917e0071be` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` |
| `net/minecraft/entity/Entity.java` | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |
| `net/minecraft/entity/LivingEntity.java` | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | same |

## Paired caller and operation coverage

- `client/input/KeyboardInput.java:12–26` and `client/input/Input.java:5–21`: opposing key pairs cancel; remaining key values are `-1/0/1`. Sneak/pose reduction multiplies sideways and forward input by `0.3` under the same gates. Both slices match exactly.
- `client/network/ClientPlayerEntity.java:607–767`: local-player input tick, item-use input reduction (`0.2`), sprint/flying gates, and call to the superclass movement tick match exactly. `tickNewAi` at `:588–600` copies the current input to `sidewaysSpeed` and `forwardSpeed` only for the camera/local player; that slice also matches exactly. Sprint activation/transitions are outside this nonsprinting assignment.
- `entity/LivingEntity.java:2168–2205`: the inherited tick calls `tickNewAi`, then multiplies sideways and forward speed by `0.98F`, then calls `travel(new Vec3d(sidewaysSpeed, upwardSpeed, forwardSpeed))`. This ordering is identical. `PlayerEntity.tickMovement` calls this superclass tick at `entity/player/PlayerEntity.java:475–503` in both versions.
- `entity/player/PlayerEntity.java:1362–1408`: in the ordinary non-swimming, non-flying player branch, `travel` delegates to `super.travel`; `getMovementSpeed()` returns `(float) getAttributeInstance(MOVEMENT_SPEED).getValue()`. Identical.
- `entity/LivingEntity.java:1763–1944`: for dry, non-lava, non-fall-flying travel, the ordinary branch obtains slipperiness `v` at `:1818–1820`; chooses horizontal drag `w = onGround ? v * 0.91F : 0.91F`; adds input acceleration using `method_18802(v)`; moves; then multiplies the resulting X/Z velocity by `w` at `:1843`. Identical.
- `method_18802(float)` at `entity/LivingEntity.java:1938–1940`: on ground, acceleration is `getMovementSpeed() * (0.21600002F / (v * v * v))`; in air, it is `field_6281`. Identical, including float constants, multiplication grouping and branch.
- `entity/player/PlayerEntity.java:498–503`: after the inherited travel call, the player resets `field_6281` to `0.02F`, adds `0.005999999865889549` when sprinting, then sets movement speed from the attribute value. This timing and code match. Thus steady non-sprinting air travel uses the `0.02F` air acceleration input; a prior sprint transition can leave the previously written value for the current travel and is excluded with sprint/timer behavior.
- `entity/Entity.java:1009–1024`: `updateVelocity` adds `movementInputToVelocity(arg, f, yaw)` to current velocity. The helper returns zero when squared input length `< 1.0E-7`; normalizes only when length squared `> 1.0`; multiplies by acceleration; rotates with float sine/cosine of yaw in degrees; and returns the rotated vector. Identical.

Hashes of paired slices (UTF-8 text with LF separators, trailing LF):

| Slice | SHA-256, both releases |
|---|---|
| `Input.java:5–21` | `91C98A4B15473FE9B6762D1FF430B8F1D89E8D9611E6654A9BB1EE1A4E7B59FF` |
| `KeyboardInput.java:12–26` | `0C889E3DD32130A1DEE5ACCBFE74A51235AF0DA9BFD4E90E85117CE22E56B686` |
| `ClientPlayerEntity.java:588–600` (`tickNewAi`) | `55B36E0D130D08BA9E8DB179A589954AC435EBA80F69DE445A2DECD7182DC49C` |
| `ClientPlayerEntity.java:607–767` | `41392A6E37343CECAC746A909D729132ADB96F3DC49366011AA5F5AA9347D36D` |
| `Entity.java:1009–1024` | `A7C70FD4C33E6C1D916BD04E6B268DE3F4EC63C5ADF7DCB8FA1312AE761D1300` |
| `LivingEntity.java:1763–1944` | `6F7651F2A6A04DA951151C7B09521E774C6B1AF83A51B0A0D82ABAA07BA35582` |
| `LivingEntity.java:1938–1944` | `57D023A7CE5D66B3E30D0F89F1BD9487AA3295F169D888C8C8A491961BA1E4BB` |
| `LivingEntity.java:2168–2205` | `2E0C4FED55CF7722EA728EA36ABFE4AB19532BE95F6F0397FDFAA23D44B3F0A4` |
| `LivingEntity.java:2197–2205` | `70E861FDF4DC7CC49F44D2ACE9F0E90594B712F704392ABD9BFADD92D5C29A2F` |
| `PlayerEntity.java:475–503` | `2AF34945F77BF35745CAD598DDA64FAD96DEF3809D16B616E3F66796987E8230` |
| `PlayerEntity.java:1362–1408` | `702ED4955FC3A008B6FFA46F221AF3FE0357F3690618EA4BC8E231CAE1532A6B` |

## Bounds and unresolved inputs

The proof treats block slipperiness, `onGround`, yaw, movement input, velocity, and movement-speed attribute value as supplied tick inputs. Collision resolution/support selection is not compared; the pre-travel block coefficient is held equal as assigned. Dry/unmounted/non-sprinting and ordinary non-swimming/non-flying/non-climbing travel are assumed. Vertical gravity, velocity thresholding, jump/climb behavior, sprint initiation/timers, swimming, lava, Elytra, abilities flight, camera rotation/setters, and collision/support/stepping/providers are excluded. The report establishes no difference in the reviewed source slices only; pair-wide source coverage and independent audit remain open.

