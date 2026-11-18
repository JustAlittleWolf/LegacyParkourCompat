# Product correspondence for the prismarine slab edge probe

The [bounded source witness](binding.md) is covered by the existing 1.21.4 edge-probe bounds operation. On the selected 1.21.4 profile, PlayerMixin replaces canFallAtLeast(DDD) with EdgeBackoffProbeBounds. It invokes the same level.noCollision player query with un-inset X/Z and Y minimum formed by casting the depth to float and subtracting it followed by 1.0E-5F. The hook has no block whitelist. The closest-later resolver selects it at 1.21.4 and leaves the 1.21.5 profile to the native query. This verifies the two adjacent profiles for the audited partial-shape case.

Exact 26.2 Player.maybeBackOffFromEdge retains the gate and ordered X, Z and joint loops. Its native canFallAtLeast retains the 1.21.5 horizontal 1.0E-7 insets and lower-bound 1.0E-7 subtraction. The loaded dry bottom prismarine slab still supplies a non-full 16 by 8 shape, and ordinary CollisionGetter.noCollision passes that shape to BlockCollisions' BooleanOp.AND query. No special movement-collector context is used by this check. The target Shapes and IndirectMerger preserve the 1.0E-7 endpoint merge that distinguishes the audited partial-shape band after the native inset.

For the configured high-speed crouch move with the three documented slabs, 1.21.4 retains approximately (0.45000015, 0, 0.1) and 1.21.5 backs off to approximately (0.35000015, 0, 0). This is static correspondence for the pre-collision backoff result. Earlier profiles, other provider geometries, ordinary default-speed trajectories and runtime parity remain outside this disposition. No product code is needed for the bounded witness.

Exact 26.2 unobfuscated source-manifest SHA-256 a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894 and artifact-manifest SHA-256 ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba.

| Exact source member | Lines | SHA-256 |
|---|---|---|
| net/minecraft/world/entity/player/Player.java | 880-948 | 8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531 |
| net/minecraft/world/level/CollisionGetter.java | 47-76 | 8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c |
| net/minecraft/world/level/BlockCollisions.java | 88-103 | eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e |
| net/minecraft/world/level/block/Blocks.java | 3028-3036 | f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435 |
| net/minecraft/world/level/block/SlabBlock.java | 34-36, 52-65 | fd5f80e73fd577605ff2924adccfcd7ba5037bb400e481cd142363a385c45ba1 |
| net/minecraft/world/phys/shapes/Shapes.java | 21, 175-215, 307-321 | 6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891 |
| net/minecraft/world/phys/shapes/IndirectMerger.java | 36, 52 | 9905ea12b115a355e7546e512532e23f6f9bf720168a61789f2a93a0347605c3 |
