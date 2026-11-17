# Crouch pose height and update order: source comparison

## Scope and evidence status

This is a bounded source comparison of the player crouch/sneaking dimensions and eye-height constants, default dimensions, pose-fit fallback, and local-player update order. It does not review other movement operations. Native block collision-shape behavior, collision-shape generation, and water sensing are not findings here.

Canonical trees examined:

- `decompiled_minecraft/1.12.2/ornithe-feather` (Feather); local provenance identifies release 1.12.2 and namespace `ornithe-feather`.
- `decompiled_minecraft/1.13/ornithe-feather`, `1.13.1/ornithe-feather`, and `1.13.2/ornithe-feather`; each local manifest identifies its exact release and namespace.
- `decompiled_minecraft/1.14/yarn`, `1.14.1/yarn`, `1.14.2/yarn`, and `1.14.3/yarn`; each contains a nested `yarn/.decompile-provenance.json`. The manifests identify exact Yarn builds, exact client/intermediary artifacts, and source hashes matching the selected current source files. Manifest and class identities are listed below.
- `decompiled_minecraft/1.14.4/mojmap`; its local provenance identifies release 1.14.4 and namespace `mojmap`. No 1.14.4 Yarn tree was present. Mojmap is used only to confirm the endpoint behavior, not to infer Yarn mapping identities.

## Findings

### 1.12.2 to 1.13: pose dimensions retain the 1.65F crouch height, then pose coverage expands

1.12.2 `PlayerEntity.updatePlayerPose` chooses (in order) 0.6F × 0.6F for fall-flying, 0.2F × 0.2F for sleeping, 0.6F × 1.65F for sneaking, and 0.6F × 1.8F otherwise. It tests a candidate box and calls `setSize` only if `world.getCollisions(box)` is empty. If the candidate does not fit, the current dimensions remain in place. `PlayerEntity.getEyeHeight` starts at 1.62F, selects 0.2F while sleeping, then subtracts 0.08F for sneaking or height 1.65F, and only afterward selects 0.4F for fall-flying or height 0.6F. Thus crouching uses height 1.65F and an eye offset of 1.54F; a simultaneous sneaking/fall-flying state takes the crouch branch first.

1.13, 1.13.1, and 1.13.2 retain 0.6F × 1.65F for the sneaking dimension and 0.6F × 1.8F standing default. Their `updatePlayerPose` adds swimming or spin-attack before sneaking in the dimension-selection ladder, both at 0.6F × 0.6F. The candidate fit check is `world.hasNoCollisions(null, box)`; failed fit still leaves dimensions unchanged. Their eye-height ordering puts swimming, fall-flying, or height 0.6F at 0.4F before the sneaking/height-1.65F branch, which returns 1.54F. This changes the selected eye offset when those states overlap with sneaking; it does not change the crouch dimension constant.

Witnesses:

- 1.12.2 Feather `net/minecraft/entity/living/player/PlayerEntity.java`, `updatePlayerPose` lines 291–315 and `getEyeHeight` lines 1755–1767.
- 1.13 Feather `net/minecraft/entity/living/player/PlayerEntity.java`, `updatePlayerPose` lines 333–360 and `getEyeHeight` lines 1853–1865. The same relevant constants, branch ordering, and fit behavior were checked in the 1.13.1 and 1.13.2 files at the corresponding methods (`updatePlayerPose` lines 334–361; `getEyeHeight` lines 1858–1870 in .1/.2; see source hashes below).
- 1.12.2 Feather `net/minecraft/entity/Entity.java`, `setSize` lines 274–290, for the size-to-box application; 1.13 Feather counterpart lines 285–301.

### 1.14: explicit SNEAKING pose is 1.5F tall with 1.27F eye height

The 1.14.0 Yarn `PlayerEntity` pose table defines standing as 0.6F × 1.8F and SNEAKING as 0.6F × 1.5F. Its eye-height switch returns 1.27F for SNEAKING, 0.4F for swimming/fall-flying/spin-attack, and 1.62F by default. The pose resolver derives the requested pose from state, selecting SNEAKING only when `isSneaking()` and not flying. It first gates the whole update on whether the SWIMMING pose can fit; when the requested pose cannot fit, it falls back to SNEAKING if that pose fits, otherwise SWIMMING. Unlike 1.12/1.13's direct size candidate check, this is an explicit pose choice with a lower-height fallback; selected pose is applied through `setPose` and the entity's pose-to-dimensions path.

The endpoint 1.14.4 Mojmap source confirms the same 1.5F/1.27F constants, same state precedence and fit fallback, and default standing height 1.8F. `Entity.canEnterPose` tests the proposed entity bounding box with `level.noCollision`; `Entity.refreshDimensions` applies the pose's dimensions and eye height and rebuilds the player's bounding box. These are pose-fit and dimension-application witnesses, not evidence of a change to any native block shape.

Witnesses:

- 1.14 Yarn `net/minecraft/entity/player/PlayerEntity.java`, pose table lines 108–117, `tick` and `updateSize` call lines 191–265, resolver lines 326–354, `getActiveEyeHeight` lines 1779–1790, `getSize` lines 1941–1943.
- 1.14 Yarn `net/minecraft/entity/Entity.java`, tracked pose declaration line 160, `setPose` near line 291, `wouldPoseNotCollide` near line 1579, and pose dimension / bounding-box refresh near lines 2196–2199.
- 1.14.4 Mojmap `net/minecraft/world/entity/player/Player.java`, dimension table lines 109–119, `updatePlayerPose` lines 344–372, `getStandingEyeHeight` lines 1801–1812.
- 1.14.4 Mojmap `net/minecraft/world/entity/Entity.java`, `setPose` lines 297–299, `canEnterPose` lines 1641–1643, and `refreshDimensions` lines 2259–2290.

### Local update order

The 1.12.2 and 1.13.x `PlayerEntity.tick` methods call `updatePlayerPose()` at the end of their player tick, after `super.tick()` and the intervening player updates. The 1.14 Yarn `PlayerEntity.tick` likewise calls `super.tick()` first and `updateSize()` last. In the 1.14 client player movement method, the pre-tick pose condition is sampled from the existing pose/state, then `input.tick(...)` updates local input before the method delegates to `super.tickMovement()`; `LivingEntity.tick` calls `tickMovement()` before returning. Consequently the player tick's final pose resolution uses the resulting state, while the client input update itself consults the pre-resolution pose. This is a call-order observation only; no trajectory or runtime behavior is inferred.

Witnesses:

- 1.12.2 Feather `net/minecraft/entity/living/player/PlayerEntity.java`, `tick` lines 234–246; local `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `tick` lines 165–168.
- 1.13 Feather `net/minecraft/entity/living/player/PlayerEntity.java`, `tick` lines 245–254; local `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `tick` lines 179–183. The .1/.2 local tick wrappers and PlayerEntity tail order were checked at the corresponding methods.
- 1.14 Yarn `net/minecraft/entity/player/PlayerEntity.java`, `tick` lines 191–265; `net/minecraft/client/network/ClientPlayerEntity.java`, `tickMovement` lines 607–620 and 762; `net/minecraft/entity/LivingEntity.java`, `tick` call to `tickMovement` at line 2023.
- 1.14.4 Mojmap `net/minecraft/world/entity/player/Player.java`, `tick` lines 209–282 and `updatePlayerPose` lines 344–372; local `net/minecraft/client/player/LocalPlayer.java`, `tick` starts at line 178.

## Exact patch review and local provenance

The 1.14.0, 1.14.1, 1.14.2, and 1.14.3 Yarn manifests were read from `decompiled_minecraft/<release>/yarn/.decompile-provenance.json`. Their exact mapping artifacts are `1.14+build.21`, `1.14.1+build.10`, `1.14.2+build.7`, and `1.14.3+build.13`. Each manifest records that release's exact client artifact and intermediary mapping input. For each release, the manifest hashes for `PlayerEntity.java`, `Entity.java`, and `ClientPlayerEntity.java` match the current canonical source bytes. The exact manifest SHA-256 and those selected source hashes are recorded below.

Across the inspected boundary, the crouch dimension changes from 0.6F × 1.65F in 1.13.2 to the explicit 0.6F × 1.5F SNEAKING dimension in 1.14.0, and the crouch eye offset changes from 1.54F to 1.27F. The inspected 1.14.1–1.14.3 sources retain the 1.14.0 values and state precedence. The 1.14.4 outer `mojmap.provenance.json` identifies release 1.14.4 / namespace `mojmap`; its `.sources.sha256` sidecar and live source hashes agree for the selected Player, Entity, and LocalPlayer classes. The 1.14.4 endpoint is therefore provenance-backed as Mojmap, not Yarn. No sources were generated or modified.

## Source byte identities

SHA-256 of the examined canonical class files (hexadecimal, case-insensitive):

| Release / mapping | Player source | Player SHA-256 | Entity source | Entity SHA-256 | Local client source / SHA-256 |
|---|---|---|---|---|---|
| 1.12.2 / Feather | `net/minecraft/entity/living/player/PlayerEntity.java` | `E4E0FDBE07A7D0A0AE4A70CBB6739A287C9D045A4A12B409895C220B5D91FE1E` | `net/minecraft/entity/Entity.java` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` / `01A58E94D8C6FF98A8E3794227CDC76A5FCBDBAD795C70C9CF28854AFF9823CC` |
| 1.13 / Feather | `net/minecraft/entity/living/player/PlayerEntity.java` | `4A57CE65405436AEB211D7A04A9A9F6AE1A661D033FD3BA2424909BFB42B8ACD` | `net/minecraft/entity/Entity.java` | `09514488D9055449C47E2E4BCD23B5773203CA634B7ECBA2323A99D0AE016642` | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` / `A188E1BC87831DD9DA1EC1366A49ACC4104C18E6979129097D02D5D3617E6A08` |
| 1.13.1 / Feather | `net/minecraft/entity/living/player/PlayerEntity.java` | `4C1B14B4727116071B1BA9E3EEAE84B8077F3D767E10E49B6A68A2556DA32272` | `net/minecraft/entity/Entity.java` | `1D6EC8B80F74635401745C2C027BF36555C85348CA5693764F2668363B17D269` | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` / `2583495F3A02B4791AA036E6A8D354D7596C4984761969A0F29B29D8D9BF42BF` |
| 1.13.2 / Feather | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ED22F6C5A3C55D67EED782070AC722201DF4D624ADBC90779F1FD29C2876633` | `net/minecraft/entity/Entity.java` | `1D6EC8B80F74635401745C2C027BF36555C85348CA5693764F2668363B17D269` | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` / `2583495F3A02B4791AA036E6A8D354D7596C4984761969A0F29B29D8D9BF42BF` |
| 1.14 / Yarn | `net/minecraft/entity/player/PlayerEntity.java` | `EBDA4AD25A265F8C85E88345EFCCE753B78E248A29A9B910C5CFD311A4A69319` | `net/minecraft/entity/Entity.java` | `F52483D5E1434114B81B1C353E89F246D1557BDA7A95400832F5650B89CC744A` | `net/minecraft/client/network/ClientPlayerEntity.java` / `92D0F97A9293F7BD57D49AF00B70F13D78FC8B2202E7ED67EDCFED917E0071BE` |
| 1.14.1 / Yarn | `net/minecraft/entity/player/PlayerEntity.java` | `EBDA4AD25A265F8C85E88345EFCCE753B78E248A29A9B910C5CFD311A4A69319` | `net/minecraft/entity/Entity.java` | `CE429699F2D8F0A54F4BE516783A474C6C7808572950164508CEED0BAF68ED5B` | `net/minecraft/client/network/ClientPlayerEntity.java` / `6AF7E75E80619AD77CE2C84692136B0A1D562A44D0E7910DCC3F6040B2DC9933` |
| 1.14.2 / Yarn | `net/minecraft/entity/player/PlayerEntity.java` | `333BB39DB18AF4CEF320EB73B7ABE359D438FB2A6CA7D8798EE449DA6C684398` | `net/minecraft/entity/Entity.java` | `7C0E9CAB0842A45E42FA9F27C9661DE3FD0FD076C35FF989AA0477198EB38EDC` | `net/minecraft/client/network/ClientPlayerEntity.java` / `90C96701849C9212EA7714F02E7BC9011D0A7D0AAF1F7B46464497BA3CA14947` |
| 1.14.3 / Yarn | `net/minecraft/entity/player/PlayerEntity.java` | `E3F361AC4E369D01473F3D84044EE33F9685D182C4485E2E80B7F62756950963` | `net/minecraft/entity/Entity.java` | `9B204A5F9AF6CA50C09DB4B6185FF37C39980C1A8CD1B5FD004713ECFFAE5C14` | `net/minecraft/client/network/ClientPlayerEntity.java` / `28B6B4031E02AFD4A2E6B4A80E5A7CA06F16DAC42CA0F92CAB25E2C2DC9E14AE` |
| 1.14.4 / Mojmap | `net/minecraft/world/entity/player/Player.java` | `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12` | `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `net/minecraft/client/player/LocalPlayer.java` / `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F` |


### Nested 1.14 Yarn manifest identities

| Release | Yarn build | Manifest SHA-256 | PlayerEntity.java SHA-256 | Entity.java SHA-256 | ClientPlayerEntity.java SHA-256 |
|---|---|---|---|---|---|
| 1.14 | `1.14+build.21` | `EA70A955A12F377CD3B9E8D5F346F4625B044D55DA726B6BC472405AE91F4341` | `EBDA4AD25A265F8C85E88345EFCCE753B78E248A29A9B910C5CFD311A4A69319` | `F52483D5E1434114B81B1C353E89F246D1557BDA7A95400832F5650B89CC744A` | `92D0F97A9293F7BD57D49AF00B70F13D78FC8B2202E7ED67EDCFED917E0071BE` |
| 1.14.1 | `1.14.1+build.10` | `308537C5ADEC3104C5CA243EB5010554F00978635ED74F0D7801C804DD4D4907` | `EBDA4AD25A265F8C85E88345EFCCE753B78E248A29A9B910C5CFD311A4A69319` | `CE429699F2D8F0A54F4BE516783A474C6C7808572950164508CEED0BAF68ED5B` | `6AF7E75E80619AD77CE2C84692136B0A1D562A44D0E7910DCC3F6040B2DC9933` |
| 1.14.2 | `1.14.2+build.7` | `786546F99684423CC2563BE45C9C3334DFCC2C7DB58DDB74BA39CE403BD0869A` | `333BB39DB18AF4CEF320EB73B7ABE359D438FB2A6CA7D8798EE449DA6C684398` | `7C0E9CAB0842A45E42FA9F27C9661DE3FD0FD076C35FF989AA0477198EB38EDC` | `90C96701849C9212EA7714F02E7BC9011D0A7D0AAF1F7B46464497BA3CA14947` |
| 1.14.3 | `1.14.3+build.13` | `81EA0021EB122D4E3340E20D47D6ECB7079365BA767A038D26A8264CDDACC28A` | `E3F361AC4E369D01473F3D84044EE33F9685D182C4485E2E80B7F62756950963` | `9B204A5F9AF6CA50C09DB4B6185FF37C39980C1A8CD1B5FD004713ECFFAE5C14` | `28B6B4031E02AFD4A2E6B4A80E5A7CA06F16DAC42CA0F92CAB25E2C2DC9E14AE` |

For each row, the three source hashes equal both the manifest's `sources` entries and freshly calculated hashes of the canonical files. The manifest hash is of the exact `.decompile-provenance.json` bytes.

### 1.14.4 Mojmap outer provenance

`decompiled_minecraft/1.14.4/mojmap.provenance.json` identifies `versionId=1.14.4`, `namespace=mojmap`; manifest SHA-256 is `9D1AD405E1E787D2FCA5ED1897E6797B1F3CD79527DA50D713232F46E2F30D97`. The selected `Player.java`, `Entity.java`, and `LocalPlayer.java` hashes in the existing source-byte table match entries in `mojmap.sources.sha256` and the current files.

## Coverage and audit disposition

Examined: 1.12.2; 1.13, 1.13.1, 1.13.2; 1.14, 1.14.1, 1.14.2, 1.14.3, and 1.14.4 for the stated pose, dimensions/eye height, local tick order, and pose-fit gate.

Closed source coverage: all listed releases have local source provenance for the cited mapping trees (the 1.14.0–1.14.3 Yarn manifests are nested under each `yarn/` directory; 1.14.4 uses outer Mojmap provenance). The first inspected release with the 1.5F / 1.27F crouch values is 1.14.0, after 1.13.2's 1.65F / 1.54F values. No 1.14.4 Yarn tree was present, so that endpoint is verified as Mojmap only. This remains a bounded comparison, not a complete movement-tick inventory.

Independent source audit verdict `d698aa6a686167a7ef30b87aa492c69f95202167a1ad64b95b9f4202bcb373dc` confirms the behavior columns and exact Yarn manifest evidence. No wiki, product, backlog, Git, tests, runtime, decompilation, or generated artifacts were consulted or changed.