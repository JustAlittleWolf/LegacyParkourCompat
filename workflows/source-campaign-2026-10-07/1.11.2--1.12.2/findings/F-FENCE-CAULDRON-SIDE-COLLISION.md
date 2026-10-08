# F-FENCE-CAULDRON-SIDE-COLLISION: fence side collision differs beside a cauldron

- Older version A: 1.11.2
- Newer version B: 1.12.2
- Mechanic / coverage slice IDs: S2.2, with collision resolution dependencies in S4.1/S4.2 and shape inventory S5.4/S5.5 still open
- Classification: changed behavior
- Confidence: source-confirmed by discovery author; independent blind review pending
- Applicability: direct player collision while flying, not spectator
- First changed release: unknown within (1.11.2, 1.12.2]
- Runtime validation: not performed

## Paired evidence

### A — 1.11.2

- Evidence artifact record ID `EA-FEATHER-R1-1.11.2`; revision `feather-r1-2026-10-07`; immutable JAR snapshot SHA-256 `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`.
- Revision manifest `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/revision.json`, SHA-256 `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`.
- Original artifact manifest `build/movement-campaign-2026-10-07/ready/1.11.2/artifacts.sha256`, SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`; source manifest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`.
- The original derived JAR is unavailable (expected SHA-256 `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`). Revised-to-original equivalence is unverified; the snapshot does not prove identity or a metadata-only change.
- `FenceBlock.java` whole-file SHA-256 `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7`; `addCollisions` lines 59-80, `getShape` 83-86, `shouldConnectTo(WorldView,BlockPos)` 124-134, `resolveVirtualProperties` 159-164. `addCollisions` method SHA-256 `b43668b59ddc239f60e4cd1eb4d2e426ee7810875f3702cdca33e2bd45fd5381`; `shouldConnectTo` `91d4f3c3f40f27116da22184f2a36fc5a9a603408598edbca9764f5a86597ff0`.
- `CauldronBlock.java` whole-file SHA-256 `8b95c69febce0002140c2f960cfa191737dd51dabf5ab99f7e74416dd382c0e7`; `addCollisions` lines 47-53 and `isCube` 66. Method hashes: `ece7d8b9ba9db7b7f01312b94adc8c2f0e8c401fb4cc9fcc799ebd9a8b46305e` and `1b65fae3a2209800c098ea8f213b171560d654fd8a65cace1210397810afa597` respectively.

### B — 1.12.2

- Evidence artifact record ID `EA-FEATHER-R1-1.12.2`; revision `feather-r1-2026-10-07`; immutable JAR snapshot SHA-256 `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`.
- Revision manifest `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/revision.json`, SHA-256 `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`.
- Original artifact manifest `build/movement-campaign-2026-10-07/ready/1.12.2/artifacts.sha256`, SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`; source manifest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- The original derived JAR is unavailable (expected SHA-256 `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b`). Revised-to-original equivalence is unverified; the snapshot does not prove identity or a metadata-only change.
- `FenceBlock.java` whole-file SHA-256 `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e`; `addCollisions` lines 59-80, `getShape` 83-86, `shouldConnectTo(WorldView,BlockPos,Direction)` 124-130, `resolveVirtualProperties` 163-168. `addCollisions` method SHA-256 is unchanged at `b43668b59ddc239f60e4cd1eb4d2e426ee7810875f3702cdca33e2bd45fd5381`; B `shouldConnectTo` SHA-256 `edeb2bddd8ccc03b76df9c41adf8a21c22c7f97b778ae889d73830f9ac767011`.
- `CauldronBlock.java` whole-file SHA-256 `0aff4a0de147e0c677f4b931cd9a881154666bb288f128c9d8041b70c38d63f9`; `addCollisions` lines 47-53 and `isCube` 66 have the same method hashes as A. B `getFaceShape` at lines 253-257 has method SHA-256 `e53a6bdd68d234e395a472070accb82e82a6e2c6322534398a7d2513f114b25c`; A has no `FaceShape` query method.
- The B attachment-exception and block collision-dispatch source is `1.12.2/ornithe-feather/net/minecraft/block/Block.java`, whole-file SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`; A `Block.java` hash `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`. The exact A/B `World.java` collision-collector hashes are `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` / `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`.
- Player path: A/B `PlayerEntity.java` hashes `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` / `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; `PlayerEntity.tick()` sets `noClip=isSpectator()` in both, and `moveRelative` enters its flying branch under `abilities.flying && !isRiding()`. A/B `Entity.java` hashes `ce8104a17ce783df639cf9726e7b1cd0936563ea7ba308603335bbe05d49440` / `80f091bf32166c88cf8b8bd31caf72d84fa16224410733c7d2a0f00563f294a0a`; `Entity.move` is at A line 459 / B line 462, with movement collision clipping followed by resolved box/position updates. The B-only difference inside that body is guarded flap-sound accounting; it does not alter the collision sequence used here.

## Source-level difference

The paired `FenceBlock.addCollisions` methods match. Both resolve virtual properties when `forceShape` is false, add the center post, then add each rail selected by the resolved directional properties. The paired player collision collector in `World.getCollisions(Box)` reaches block-state `addCollisions` with `forceShape=false` through the unchanged state/block dispatch (S2.2 records the method ranges and whole-file hashes).

In A, `FenceBlock.shouldConnectTo` accepts a non-fence neighbor only if its material is solid-blocking and its state is a cube, except pumpkins; cauldron is `Material.IRON` but `isCube` returns false, so the east property is false. In B, the fence asks the east neighbor for its west `FaceShape`; `CauldronBlock.getFaceShape` returns `SOLID` for horizontal faces. Cauldron is not in `Block.isExceptionForAttachment` or the additional explicit fence exceptions, so the B east property is true.

With a fence at `(0,0,0)` and a cauldron at `(1,0,0)`, the fence's B east collision rail is x `[0.625,1]`, z `[0.375,0.625]`, y `[0,1.5]`; the A center post in both versions is x/z `[0.375,0.625]`, y `[0,1.5]`. The cauldron's five collision boxes are unchanged: a base to y `0.3125` and four walls to y `1.0`. Its outline `getShape` is full cube in both versions, but movement collision dispatch uses its five `addCollisions` boxes.

## Reachability and player consequence

Both `PlayerEntity.tick()` bodies set `noClip` to `isSpectator()`. Thus a non-spectator creative-flight player remains subject to `Entity.move` collision clipping. `PlayerEntity.moveRelative` takes the flight branch when `abilities.flying && !isRiding()` and still reaches movement through the inherited entity movement path; flight does not itself set `noClip`. Those player/entity source methods are recorded in S1.5, S3.1/S3.2 and S4.1; full movement inventory remains open.

Concrete source-level geometry: a 0.6-wide player with feet at y `1.1`, centered at x `1.5`, z `0.5`, fits within the cauldron's interior width/depth of `0.75`. The AABB clears every cauldron collision box vertically because its minimum y `1.1` exceeds their maximum y `1.0`. At z `0.5`, its width overlaps the fence post and east rail. A westward requested movement clips against the B-only rail when player minX reaches `1.0`, leaving player center x `1.3`. In A no east rail exists; the center post only clips when player minX reaches `0.625`, leaving center x `0.925`. This is a source-derived collision endpoint for the stated isolated arrangement, not a runtime-measured trajectory.

## Consequence and uncertainty

The versions differ in a reachable direct-player collision response for a flying, non-spectator player above a cauldron beside a fence: B connects the fence to the cauldron's horizontal face and adds a side rail; A does not. The paired methods do not establish the first release where this changed within the interval. Runtime validation was not performed. Other fence-neighbor types and the complete state/default/provider inventory remain outside this finding and open in S2.2/S5.4/S5.5.

## Handoff

Independent blind review must accept this exact immutable snapshot before implementation handoff. This finding depends on `EA-FEATHER-R1-1.11.2` and `EA-FEATHER-R1-1.12.2`; the original derived JAR equivalence remains unverified. It closes only the stated fence/cauldron/player arrangement and does not close S2.2 or the pair inventories.
