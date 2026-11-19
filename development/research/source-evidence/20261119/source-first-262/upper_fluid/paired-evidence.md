# Local player fluid jump impulse, 26.1.2 to 26.2

Bounded source coverage verdict: continuity of the immediate vertical impulse and its call eligibility for the same local player inputs. The shallow-lava comparison was factored through a helper with a narrower reread and an equivalent outcome on this path. No standalone movement difference was found in this slice.

## Release and source identity

Both exact releases use the published unobfuscated namespace, decompiled by Vineflower 1.12.0 on Java 25.0.3+9-LTS. For each side the ready marker version and version metadata ID agree, and the source, artifact and diagnostics manifests/files checked against their recorded SHA-256. The 26.2 success log is a preserved excerpt. The input JAR and metadata hashes below are artifact manifest entries. The Java file hashes were computed from the active trees and verified against the published source manifests.

| Evidence SHA-256 | 26.1.2 | 26.2 |
| --- | --- | --- |
| `unobfuscated.sources.sha256` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |
| `artifacts.sha256` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` |
| `client.jar` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` | `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290` |
| `version.json` | `739c0e0520086c1a6d3dcd3ceec4f9c209848146e8a6012503241a3ba8117775` | `4d6b3a5a27fed3c0faab2cb4b6dee7318be310bc3c627a78951519ffefaf1d0c` |
| `client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `client/player/AbstractClientPlayer.java` | `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` | same |
| `world/entity/Avatar.java` | `01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78` | same |
| `world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` | same |
| `world/phys/Vec3.java` | `57b08ae818868a4fffdc9b5dadba2b127138c8945c2194f6f4b01e6150cd3bcb` | `026c8461113b32116f0183ec42fa1cb4c7414cec38b685484d2309f9a0cb9a33` |

## Paired trace

Paths are under `decompiled_minecraft/<release>/unobfuscated/net/minecraft/`. Paired ranges are in 26.1.2, 26.2 order.

1. `client/player/LocalPlayer.java:227–230` in both versions enters the inherited player tick once the connection has loaded the client. The inherited chain uses `client/player/AbstractClientPlayer.java:44–46` in both, `world/entity/player/Player.java:232–259, 232–260` and `world/entity/LivingEntity.java:2699–2700, 2765–2766`. `world/entity/Entity.java:500–524, 507–531` updates fluid interaction during the base tick. If the entity remains present, `LivingEntity.java:2738–2740, 2804–2806` calls virtual `aiStep`.
2. `LocalPlayer.java:767–914` in both versions samples input at 786 before delegating. Its loading-screen check at 772 reads `Minecraft.screen` in 26.1.2 and `Minecraft.gui.screen()` in 26.2, a field getter in `client/gui/Gui.java:215–217`. `AbstractClientPlayer.java:76–79` and `Player.java:442–454` both delegate down to `LivingEntity.aiStep`. It decrements positive `noJumpDelay` up front. Virtual `LocalPlayer.applyInput` writes `jumping = input.keyPresses.jump()` while controlling the camera, per `LocalPlayer.java:692–702, 744–746` in both. Normal keyboard sampling reads the jump key in `client/player/KeyboardInput.java:23–35`, byte-identical in both.
3. `LivingEntity.java:3019–3032, 3086–3099` applies input, clears `jumping` when immobile, and enters the jump section only when `jumping && isAffectedByFluids()`. For players these predicates use inherited dead/dying or sleeping (`LivingEntity.java:2299–2301, 2336–2338` and `Player.java:870–872` both) and `!abilities.flying` (`Player.java:875–877` both). When the section is skipped it sets `noJumpDelay = 0`.
4. `LivingEntity.java:3032–3056, 3099–3123` first queries `isInLava()`, reads lava height if true and water height otherwise. Next it computes `isInWater() && fluidHeight > 0.0`, then reads the double fluid jump threshold. Water runs `jumpInLiquid(WATER)` when that water-and-positive-height predicate is true and `onGround() && !(fluidHeight > fluidJumpThreshold)` is false. Thus the water branch retains priority when both fluids are in contact and the outer test chooses water, and its positive-depth test can use the selected lava height. If the outer water test chooses the other branch, lava runs `jumpInLiquid(LAVA)` when `isInLava()` is true and the grounded shallow test is false. Neither impulse checks or writes `noJumpDelay`. The ground-jump fallback tests `(onGround() || inWaterAndHasFluidHeight && fluidHeight <= fluidJumpThreshold) && noJumpDelay == 0`, invokes `jumpFromGround()`, then sets the delay to 10.
5. `LivingEntity.jumpInLiquid(TagKey<Fluid>)` at 2370–2372 and 2407–2409 evaluates `getDeltaMovement()`, adds `(0.0, 0.04F, 0.0)`, and passes the new vector to `setDeltaMovement`. The literal `0.04F` is rounded as a float and widened to double (`0.03999999910593033`). `world/phys/Vec3.java:116–118` in both versions evaluates the component sums in x, y, z order as `this.x + x, this.y + y, this.z + z`. `Entity.java:3664–3672, 3737–3745` reads current velocity and stores the sum only if all components are finite (`Vec3.java:340–342, 344–346`). The tag parameter has no effect in this body. Tree search found other definitions only in non-player mobs. The local player inherits this body through `AbstractClientPlayer → Player → Avatar → LivingEntity`. The impulse precedes the travel section (`LivingEntity.java:3058–3074, 3125–3141`).

## Lava shallow test

The earlier inner dispatch uses `!isInLava() || onGround() && !(fluidHeight > fluidJumpThreshold)` at `LivingEntity.java:3043`. The later dispatch uses `!isInLava() || onGround() && isInShallowFluid(FluidTags.LAVA)` at 3110. The 26.2 helper at 2545–2547 reads `getFluidHeight(LAVA)`, then `getFluidJumpThreshold()`, and returns `height <= threshold`.

In both versions `Entity.isInLava()` requires `!firstTick` and `fluidInteraction.isInFluid(LAVA)`, per `Entity.java:1655–1657, 1742–1744`. `EntityFluidInteraction.java:139–146` is identical and implements that last test as the cached height `> 0.0`, ruling out NaN on the dispatch path that reaches the helper. The intervening reads of cached lava height, water contact (`Entity.java:1509–1511, 1596–1598`), on-ground flag (`Entity.java:700–702, 707–709`) and eye height do not write the corresponding state. With the same cached positive lava height and a threshold always returned as double `0.0` or `0.4` from the eye-height comparison (`Entity.java:3610–3616, 3683–3689`), the predicates select the same branch at and on either side of the threshold.

`Entity.updateFluidInteraction` at 1558–1582 and 1645–1669 is text-identical. The tracker resets each height to `0.0`, scans contents of the fluid interaction box when available, and records `Math.max(fluidTop - entityY, tracker.height)` for the tag (`EntityFluidInteraction.java:32–89, 139–146, 159–164` in both). The scan uses a double block Y plus the float height from `FluidState.getHeight` (`world/level/material/FluidState.java:49–51` both). The same `FlowingFluid.java:466–478` in both computes `1.0F` with the same fluid above and otherwise amount divided by `9.0F`. `FluidState`, `FlowingFluid`, `EmptyFluid`, `WaterFluid`, `LavaFluid` and `tags/FluidTags.java` each have matching paired source hashes.

## Remaining coverage

Input-device and GUI lifecycle, complete tick topology and pose/box writers, fluid tag resources and block fluid-state registrations, fluid currents, glide and swimming, ground jump math, and travel/gravity/collision after the impulse remain separate coverage. The new shallow helper is also called from 26.2 lava travel at `LivingEntity.java:2552`, which falls outside the impulse comparison. No runtime run or independent audit was performed in this assignment.

