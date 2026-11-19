# Ordinary solid collision and vertical signed zero, 26.1.2 to 26.2

Confirmed at the source operation boundary in both directions. The opposite sign persists at the end of a dry zero-gravity jump tick when a solid ceiling clips upward movement. Default gravity masks the sign during the same dry movement tick. The comparison uses the published unobfuscated namespace from each exact version. The existing gravity attribute is syncable and accepts zero in both releases.

## Ordered player path

All file paths start at `decompiled_minecraft/<version>/unobfuscated/net/minecraft/`. Each paired range lists 26.1.2 first.

- `world/entity/ai/attributes/Attributes.java` 45–50 / 55–60 supply gravity and default float jump strength `0.42F`. `world/entity/LivingEntity.java` 2352–2364 / 2389–2401 writes positive jump velocity using `Math.max(jumpPower, movement.y)`. A normal standing jump in a dry two-block-high stone passage clips at the ceiling with positive input. Plain stone is registered at `world/level/block/Blocks.java` 56–59 in both versions and uses default properties.
- `world/entity/player/Player.java` 1381–1404 / 1402–1425 delegates ordinary non-flying travel to LivingEntity. Its dry helper invokes the self-move and reloads its resulting velocity at `world/entity/LivingEntity.java` 2623–2633 / 2676–2686. With neither climbable nor relevant powder snow there is no intermediate Y replacement.
- `world/entity/Entity.java` 754–797 / 761–798 computes separation between requested and clipped Y, marks below by requested Y < 0.0, checks fall contact, then runs the relevant vertical response for the local simulating player. In 26.1.2 this dispatches on the underfoot effect block even when the obstacle was at the ceiling. Plain stone and the ordinary default path use `world/level/block/Block.java` 498–500.
- The new response in `world/entity/Entity.java` 802–852 starts with entity restitution. A floor can replace it with max of entity and block coefficients, subject to its falling-speed, bounce-suppression and block tag gates. A ceiling keeps the entity coefficient. The player inherits the default `BOUNCINESS` of `0.0` from 26.2 `world/entity/LivingEntity.java` 2202–2204 and `world/entity/ai/attributes/Attributes.java` 31. Stone's property uses default `+0.0F` (`world/level/block/state/BlockBehaviour.java` 113 and 995) and supplies no positive restitution. For the zero restitution branch, compensation is double positive zero and effective drag is double 1.0. The subtraction negates the sign of nonzero incoming Y before multiplication by zero. Opposite signs at the operation are exact, with raw bits zero for positive zero and hexadecimal `8000000000000000` for negative zero.
- The `Vec3` component assignment and finiteness-guarded setter preserve the raw sign (`world/phys/Vec3.java` 65–69, 160–166 and 285–290 / 65–69, 160–166 and 289–294; `world/entity/Entity.java` 3664–3682 / 3737–3755). End-of-move block speed scaling multiplies Y by double 1.0 (26.1.2 `Entity.move` around 794 and 26.2 795–796).
- With a loaded chunk and no levitation, the dry travel suffix subtracts effective gravity and multiplies by positive vertical drag (`world/entity/LivingEntity.java` 2430–2452 / 2468–2496, with 26.2 friction math at 514–515 and default modifier at `Attributes.java` 10–12). Gravity reads the attribute through `LivingEntity.java` 2383–2389 / 2420–2427 and `Entity.java` 1438–1440 / 1521–1523. `+0.0 - +0.0` is `+0.0`, while `-0.0 - +0.0` is `-0.0`. Both become the same negative nonzero value with default gravity. The alternate gravity therefore changes sign persistence while keeping the collision operation and jump controls vanilla.
- The remainder of the collision tick applies inside and step effects, animation and entity pushing (`LivingEntity.java` 3070–3110 / 3137–3177), then ordinary player bookkeeping (`Player.java` 442–478, 258–284 / 442–478, 259–285) and local packet bookkeeping (`client/player/LocalPlayer.java` 228–251 in both). Use a dry otherwise empty stone passage with no entities, impulses, or active movement effects so none of those paths replaces Y. On the next normal AI tick the `abs(y) < 0.003` gate writes literal positive zero (`LivingEntity.java` 3012–3016 / 3079–3083).

## Evidence identities

| SHA-256 | 26.1.2 | 26.2 |
| --- | --- | --- |
| Source manifest | 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 | a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894 |
| Client artifact | b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0 | 40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290 |
| Entity.java | 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf | 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5 |
| Block.java | 1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977 | cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab |
| LivingEntity.java | c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd | 7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a |
| Attributes.java | 8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5 | 4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c |
| Vec3.java | 57b08ae818868a4fffdc9b5dadba2b127138c8945c2194f6f4b01e6150cd3bcb | 026c8461113b32116f0183ec42fa1cb4c7414cec38b685484d2309f9a0cb9a33 |

Source digests were checked against the published manifests. Nonzero restitution, simultaneous horizontal clipping, and fluid travel are separate coverage. This is source evidence for the vertical operation and dry fixture, with runtime parity still unverified.
