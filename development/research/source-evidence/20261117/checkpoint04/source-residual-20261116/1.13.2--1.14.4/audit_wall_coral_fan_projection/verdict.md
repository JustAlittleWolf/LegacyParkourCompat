# Independent source audit: coral fan wall predicate

Verdict: **ACCEPT**, bounded to the ten listed coral-fan keys and the wall-neighbor predicate.

Audited report SHA-256: `2dcc12c64745d923cfdca8c60d54fe4231a62f9498a13091b77ef0a2a07a39e4`.

## Identity and roster

The report's source identities match `decompiled_minecraft/1.13.2/ornithe-feather.ready.json` and `decompiled_minecraft/1.14.4/ornithe-feather.ready.json`: the source-manifest SHA-256 values are respectively `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. Both source manifest files have those exact hashes. All eight Java source files named in the report were checked against their canonical bytes and the matching source-sidecar entries; every reported SHA-256 matched.

The cited unexamined crosswalk TSV hashes to `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`. It contains exactly ten rows selected by old concrete class `BaseCoralFanBlock` or `CoralFanBlock`: five dead-fan keys and five live-fan keys, each with `WATERLOGGED:2; product=2` on both endpoints. The old and target class assignments agree with the exact registrations. `WATERLOGGED` is a Boolean property, included in both endpoint state definitions; `BaseCoralPlantTypeBlock` sets its default true in both releases.

## Predicate check

At 1.13.2, the exact `BaseCoralPlantTypeBlock.getFaceShape` implementation returns `FaceShape.UNDEFINED` for every face. `BlockState.getFaceShape` delegates to that method. `BaseCoralFanBlock` supplies only the outline-shape override; `CoralFanBlock` inherits it and does not override the face-shape method. The ten registrations use `.noCollision()`. The old `WallBlock.shouldConnectTo` accepts `SOLID` (or its separate pole cases) after exclusions; `UNDEFINED` is neither accepted case, so all ten keys produce false neighbor bits for each horizontal direction and either registered state.

At 1.14.4, both fan subclasses continue to use `.noCollision()` for all ten registrations. `Block.Properties.noCollision()` sets `hasCollision=false`, and `Block.getCollisionShape` then returns the empty shape. `BlockState.m_87223014` delegates to `Block.isFaceSolid` on an uncached state; `WallBlock` passes that result to its connection predicate. An empty collision shape has no full face, so the face-solid input is false. The exact WALLS payload has hash `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` and lists only wall blocks; it contains none of the ten coral-fan keys. None of these keys is a fence gate. The reused resource-membership audit is `wall_resource_membership_audit/verdict-r2.md`, SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`.

Thus the packet's 80 cases (ten keys × two WATERLOGGED values × four horizontal faces) are correctly reported false→false for this neighbor predicate.

## Limits

Acceptance is restricted to this exact provider/predicate slice. It establishes no movement trajectory, unrelated shape parity, complete provider inventory, or pair freeze. The report correctly retains the r1 crosswalk TSV overwrite limitation; this audit verifies only the current exact rows used by this packet and does not re-audit that crosswalk or repair its historical byte-diff gap.
