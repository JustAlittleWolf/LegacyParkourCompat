# Creative-flight follow-through: 1.14.4 to 26.2

Snapshot ID: `wiki-creative-flight-followthrough-1.14.4-26.2`

Status: bounded exact-source inventory recorded; independent Wiki-lane review pending. The local vertical flight-input aggregation remains present at the checked endpoints, but horizontal slow-input production, flight travel, takeoff, forced-flight predicates, and state consumers have observed path changes. This does not establish an end-to-end flight equivalence or universal movement result. No runtime validation was performed.

## Ready source inventory

All 24 exact endpoints below were checked from ready source trees and the `LocalPlayer.java` (or mapped equivalent) digest matched the exact ready source-manifest entry. Namespace transitions are explicit: 1.14.4 uses `ornithe-feather`; 1.15.2 through 1.21.11 use `mojmap`; 26.1.2 and 26.2 use `unobfuscated`.

| Version | Namespace | Local player source SHA-256 | Ready source-manifest SHA-256 |
|---|---|---|---|
| 1.14.4 | ornithe-feather | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce` | `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc` |
| 1.15.2 | mojmap | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16.1 | mojmap | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 | mojmap | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |
| 1.16.5 | mojmap | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |
| 1.17.1 | mojmap | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` |
| 1.18.2 | mojmap | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.19.2 | mojmap | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |
| 1.19.3 | mojmap | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 | mojmap | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |
| 1.20.1 | mojmap | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` |
| 1.20.2 | mojmap | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` |
| 1.20.4 | mojmap | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` |
| 1.20.5 | mojmap | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` |
| 1.20.6 | mojmap | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` |
| 1.21.1 | mojmap | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` |
| 1.21.3 | mojmap | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` |
| 1.21.4 | mojmap | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` |
| 1.21.5 | mojmap | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` |
| 1.21.8 | mojmap | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` |
| 1.21.10 | mojmap | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` |
| 1.21.11 | mojmap | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` |
| 26.1.2 | unobfuscated | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` |
| 26.2 | unobfuscated | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |

The namespace-specific ready source manifests above are the provenance bound for this checked endpoint inventory; the table does not claim that every intermediate snapshot or behavior outside those endpoints was examined.

## Bounded findings

1. **Vertical input aggregation persists at checked endpoints; horizontal slow-input topology changes.** 1.14.4 `LocalClientPlayerEntity.aiStep()` lines 728–742 and 26.2 `LocalPlayer.aiStep()` lines 871–882 aggregate simultaneous jump and down/sneak controls into one vertical scalar and apply one flight-speed vector addition. The same operation shape appears in the checked LocalPlayer endpoints listed above, despite names changing from `abilities.flying`, `flySpeed` and `isCamera()` to current equivalents. This is only the vertical input slice. Horizontal slow-input scaling is performed inside `KeyboardInput.tick()` at 1.14.4, while from 1.15.2 the input class scales slow movement before LocalPlayer's flight branch; that branch no longer divides both horizontal input axes to undo the scaling. This producer comparison was sampled at 1.14.4, 1.15.2/1.16.5, 1.17.1, 1.19.4/1.20.4/1.20.5, and 1.21.11/26.2. The slow-input/spectator/camera producers and all intervening versions were not closed.

2. **Flight travel speed source changes in 1.19.4.** In 1.19.2 and 1.19.3 `Player.travel(Vec3)` temporarily assigns sprint-adjusted ability speed to `flyingSpeed` around `super.travel`, then restores it; the branch also restores Y to saved-Y × 0.6, resets fall distance and clears flag 7. In 1.19.4 the temporary field write disappears and `Player.getFlyingSpeed()` supplies the sprint multiplier instead. Exact bounded interval: `(1.19.3, 1.19.4]`. This is an operation-path change; its final displacement consequence and the inherited `LivingEntity.travel` consumers are not derived here.

3. **A creative-flight toggle gains an on-ground jump impulse at 1.20.5.** The checked LocalPlayer toggle branch in 1.20.1/.2/.4 has no call to `jumpFromGround()` after toggling flight on. In 1.20.5/.6, a flight toggle that turns flying on while on ground calls `jumpFromGround()` before synchronizing abilities. The direct callee sets Y velocity to jump power when it exceeds `1.0E-5F`, may add the sprint horizontal impulse, and sets `hasImpulse`. The first observed source boundary is `(1.20.4, 1.20.5]`; the exact snapshot introduction is unknown. Jump-power and sprint-state producers remain dependencies.

4. **Flight toggle eligibility changes at 1.21.11.** In the checked 1.21.10 branch, forced-flight and landing-reset checks use `isAlwaysFlying()` and the double-tap toggle has no vehicle-eligibility predicate. In 1.21.11 and 26.2, forced-flight/landing checks use spectator mode and the toggle additionally requires no vehicle or an eligible `PlayerRideableJumping` vehicle whose `canJump()` succeeds. The first observed boundary is `(1.21.10, 1.21.11]`; vehicle-control and server/ability-sync producers remain open.

5. **Direct player flight consumers have relocated state writes.** The bounded comparison found the older player path saving/restoring Y around `super.travel`, resetting fall distance, and clearing flag 7 within the flight travel branch. By 1.21.11/26.2, Y restoration remains after `super.travel`, while fall-distance reset is in the player `aiStep()` path and flag 7 is not visibly cleared there. These checks identify a consumer-path difference; downstream flag-7 readers, fall-state ordering, and synchronization are unresolved.

## Exact changed-slice source bindings

| Claim | Exact source path/range | Source SHA-256 | Ready source-manifest SHA-256 |
|---|---|---|---|
| 1.19.2 temporary flight-speed write | `1.19.2/mojmap/net/minecraft/world/entity/player/Player.java`, `travel(Vec3)` 1464–1490 | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |
| 1.19.3 temporary flight-speed write | same mapped path, `travel(Vec3)` 1448–1474 | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 speed getter path | same mapped path, `travel(Vec3)` 1440–1467; `getFlyingSpeed()` 2112–2118 | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |
| 1.20.4 toggle and no ground-jump call | `1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()` 718–735 | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` |
| 1.20.5/.6 toggle and jump call | `1.20.5/.6/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()` 721–742 | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` (1.20.5); `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` (1.20.6) |
| 1.20.4 direct jump callee | `1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, `jumpFromGround()` 2008–2017 | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` |
| 1.20.5/.6 direct jump callee | same mapped path, `jumpFromGround()` 2069–2081 | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` (1.20.5); `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` (1.20.6) |
| 1.21.10 flight writer/consumer | `1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()` 751–772, 835–839; `Player.java`, `aiStep()` 459–464 and `travel()` 1295–1318 | LocalPlayer `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`; Player `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` |
| 1.21.11 flight writer/consumer | `1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()` 792–813, 876–880; `Player.java`, `aiStep()` 452–464, `travel()` 1360–1383 | LocalPlayer `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; Player `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` |
| 26.2 flight writer/consumer | `26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java`, `aiStep()` 831–852, 910–920; `Player.java`, `aiStep()` 442–454, `travel()` 1402–1425 | LocalPlayer `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; Player `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |

The selected `KeyboardInput`/`ClientInput` comparisons were sampled rather than checked at every endpoint: 1.14.4 `KeyboardInput.java` SHA-256 `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526`; 1.15.2/1.16.5 `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7`; 1.17.1 `ea41065c909e53f1a2cc29ecdb6a9a8f9265d2801cd8b95b996e182c318ecd69`; 1.19.4/1.20.4/1.20.5 `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`; 1.21.11/26.2 `ClientInput.java` `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`. Each digest was matched to the namespace-specific ready source manifest.

## Open dependencies

Creative flight remains partial. Continue with exact-version checks of the controlled-camera and slow-input producers (including spectator cases); trace the 1.19.4 `getFlyingSpeed()` result through `LivingEntity.travel()`; bind 1.20.5's jump power and sprint impulse producers; compare ability/can-fly writers and synchronization; and resolve 1.21.11 vehicle eligibility, fall-distance timing, and flag-7 readers. The endpoint roster and sampled unchanged vertical input branches do not close intervals or dependency producers that were not listed above.
