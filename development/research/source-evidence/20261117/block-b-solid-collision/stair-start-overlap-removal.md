# Stair start-overlap exclusion removal

Independent reviewer `/root/stair_exclusion_removal_audit` accepted the bounded comparison for the finding payload SHA-256 `65bd1bda660fc9c2a0080d17633796f671c4564f42fe5c5e8cef13d30b4f1ba9` (UTF-8 with LF). The original endpoint witness is retained with SHA-256 `ac7842519865941555ff8772bc508a61d66fc3c81c62f2d28d4533151a2a3c28` over its original bytes.

Use the loaded, dry, bottom, straight, south-facing stair that is the sole collision block in the envelope of the finding's airborne horizontal movement. Its lower half has a positive-volume start overlap and its upper step face-touches at local `maxZ = 0.5`. The requested Z motion is `0.0196000002324581146240234375`.

- 1.13, 1.13.1 and 1.13.2, Ornithe Feather Gen2 build 2. `Entity.move` supplies the start shape through `WorldView.getCollisions` to `WorldView.getBlockCollisions`. The lower-half overlap excludes the entire stair union. 1.13 `WorldView.java:113–176,191–210`; 1.13.1 and 1.13.2 `WorldView.java:112–151,154–164`; 1.13.2 `Entity.java:580–594`. The requested displacement passes.
- Exact 1.14, Ornithe Feather Gen2 build 2. Ordinary movement reaches `Entity.m_73670363` (lines 689–719), then `VoxelShapes.m_10425781` and `m_84822986` (lines 228–238,260–295). The Z scan finds the live stair state at the interior cell and passes its whole shape to `VoxelShape.calculateMaxDistance` (line 183 onward), with no whole-shape exclusion. `StairsBlock.getShape` and the half-cell table (lines 37–81,99–104) supply the touching upper step, which clips Z to zero. Step-up and the tiny-motion cutoff do not apply. The release manifest is `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`.
- Exact 26.2, unobfuscated. `Entity.collideBoundingBox` and `collideWithShapes` (lines 1195–1200,1244–1259) collect the swept stair through `BlockCollisions.computeNext` (lines 93–103). `StairBlock.getShape` and its table (lines 37–45,74–88) supply the touching upper step. `VoxelShape.collide` / `collideX` (lines 252–288) clips Z to zero. The release manifest is `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

The bounded whole-shape exclusion applies in 1.13 through 1.13.2. The separate persistence check below closes its removal throughout 1.14–1.14.4. General stair and collector coverage remain open. Runtime execution was outside the reviews.

## Exact source-file SHA-256

Hashes checked against the corresponding published source manifests.

- `1.13/ornithe-feather/net/minecraft/world/WorldView.java` `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543`
- `1.13.1/ornithe-feather/net/minecraft/world/WorldView.java` `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`
- `1.13.2/ornithe-feather/net/minecraft/world/WorldView.java` `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`
- `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`
- `1.14/ornithe-feather/net/minecraft/entity/Entity.java` `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9`
- `1.14/ornithe-feather/net/minecraft/block/shape/VoxelShapes.java` `ea5e88613c93c6d15b11a9ce8afcef4681875cd4113d51282b2dc26bd8d4be71`
- `1.14/ornithe-feather/net/minecraft/block/shape/VoxelShape.java` `45b5c03a644d4c80c1af221c4e458d1cffe68438229b727e4eaf724468f7dab7`
- `1.14/ornithe-feather/net/minecraft/block/StairsBlock.java` `b49cbd43a9be5e848623f25769dede5795f50435ba0e82bbdbbf571527396b9f`
- `26.2/unobfuscated/net/minecraft/world/entity/Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`
- `26.2/unobfuscated/net/minecraft/world/level/BlockCollisions.java` `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`
- `26.2/unobfuscated/net/minecraft/world/level/block/StairBlock.java` `2a8ad6eedc5a151af7a739ccd476f18cab4fe78e9df7c1ec601cb9687a77630b`
- `26.2/unobfuscated/net/minecraft/world/phys/shapes/VoxelShape.java` `419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854`

## Removal across the remaining 1.14 patches

Independent reviewer `/root/stair_exclusion_patch_persistence` confirmed this same sole-stair clipping route in exact 1.14.1 through 1.14.4. Each scanned provider tests the live stair shape against the swept query without dropping it for a positive overlap with the start box. The upper step remains eligible to clip the exact face contact. The source files and member locations were verified at these identities:

- 1.14.1 `yarn/net/minecraft/world/ViewableWorld.java`, `getCollisionShapes` lines 129–193, SHA-256 `67e88826a77d5ed6dbdf7701acab287bd6b34f1e394625a6a701c1181556fd2e`.
- 1.14.2 `yarn/net/minecraft/world/ViewableWorld.java`, `getCollisionShapes` lines 129–193, SHA-256 `67e88826a77d5ed6dbdf7701acab287bd6b34f1e394625a6a701c1181556fd2e`.
- 1.14.3 `yarn/net/minecraft/world/ViewableWorld.java`, `method_20812` lines 133–185, SHA-256 `3e6f183c66c98206cef362551c53dbf8ea7ac11e44123060c640da09c944d29d`.
- 1.14.4 `ornithe-feather/net/minecraft/world/WorldView.java`, `getBlockCollisions` lines 133–185, SHA-256 `fcdf8dc98f97781f4639c4b8cda8a238edf834f3963e401e6eeef6cb475c4909`.
