# Step-up descent closure witness

## Result

The source delta is confirmed: `Entity.move` in 1.8 requests a descent of `-stepHeight`; in 1.8.1 it requests `-var33` or `-var81`, the selected raised route's clipped ascent. In 1.8.2 the 1.8.1 rule remains (`-n` / `-q`). `LivingEntity` assigns `stepHeight = 0.6F` in all three releases and its travel path calls inherited `move(velocityX, velocityY, velocityZ)`, so ordinary player entities reach this operation when the step gate is met.

The provider query closure is proven: the raised pass queries `getCollisions` using `startingBox.expanded(dx, +stepHeight, dz)`. `Box.expanded` leaves `minY` unchanged for positive Y. `World.getCollisions` only includes a block collision box when it strictly intersects that swept query; `Block.addCollisions` uses `shape.intersects(box)`, whose Y test rejects `provider.maxY <= query.minY`. Thus a support top at/below the starting bottom is not present to clip the extra descent. This establishes the geometric mechanism, but it does not alone establish an attainable ordinary per-tick player witness.

## Candidate witness checked and rejected

A grounded player at `y=1`, with a full floor at `y=0`, a carpet lip in the horizontal path at `y=1`, and a ceiling underside at `y=3` does **not** witness a below-start result while the raised endpoint still overlaps that carpet. In 1.8.1 the carpet provider has a collision box spanning its full X/Z footprint from `y=1` to `y=1.0625` (`CarpetBlock` sets the inherited shape to `(0,0,0)-(1,0.0625,1)`; `Block.getCollisionShape` builds that box). Because that box is in the reused list, the negative `intersectY` descent clips at the carpet top. The step-up ascent is limited to 0.2 by the ceiling, but both requested descents are clipped to the same carpet top if the endpoint overlaps it. This common suggested setup is therefore not a positive witness.

**Remaining applicability blocker:** a positive ordinary-player witness needs a selected raised horizontal endpoint that no longer overlaps any collision box from the upward swept query (including the obstacle that caused ordinary horizontal clipping), while the raised candidate still beats the ordinary clipped route. The closed inputs enumerate provider geometry and establish the omitted-lower-support rule, but do not prove such a configuration is reachable from vanilla player travel displacement/input. Do not mark player applicability closed on the basis of the carpet example or geometry alone. A bounded follow-up should inspect a specific narrow/offset collision-shape provider and its player travel displacement range, or keep this candidate's applicability gate open if no such case is established.

## Exact source anchors

- 1.8 `net/minecraft/entity/Entity.java`: `move(double,double,double)`, step gate and second-pass setup 459–470; ascent candidates and route choice 471–524; unconditional `dy = -this.stepHeight` and descent clip 526–532; final position/collision/ground writes 541–547.
- 1.8.1 same file/method: step gate and second-pass query 464–475; ascent candidate calculations and route choice 476–521; selected `dy = -var33` / `-var81` 522–530; descent clip and final writes 533–550.
- 1.8.2 same file/method: second-pass setup/query 460–471; ascent and route choice 472–518; selected `dy = -n` / `-q` 519–530; descent and final writes 533–548.
- 1.8 / 1.8.1 / 1.8.2 `net/minecraft/entity/living/LivingEntity.java`: constructor assignment `stepHeight = 0.6F` at line 131 / 133 / 133; ordinary travel calls inherited `move` at 1161, 1167, and 1165 respectively. The other travel branches also call `move` at 1186/1214, 1192/1220, and 1190/1218.
- 1.8.1 `net/minecraft/block/CarpetBlock.java`: constructor shape 21–28, specifically line 24; `resetShape`/`updateShape` 45–59, specifically line 58. `net/minecraft/block/Block.java`: `addCollisions` 299–304; `getCollisionShape` 306–310. `net/minecraft/world/World.java`: block collision enumeration/forwarding 873–907, especially 903. `net/minecraft/util/math/Box.java`: positive-Y expansion 31–55 and strict `intersects` 153–159; downward `intersectY` 113–128.

## Evidence identities

The supplied coverage inputs match the assigned SHA-256 values:

- `.native-source/20261030/early18-pool34/1.8--1.8.1/core-coverage.md`: `fcb0360dc6ea6d14e28a8777008f81ae7ee8df21b225370c5af1387ec1a43824`
- `development/research/.native-source/20261030/early18-pool34/1.8--1.8.1-collision/coverage.md`: `8d79cfe4cbfe9a5c514f61a4d821a744f0e6d8b4ee2505451cdc90150b1eef00`

Key relied-on source hashes (each obtained with `Get-FileHash -Algorithm SHA256 <path>`):

- 1.8 `Entity.java`: `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9`; `LivingEntity.java`: `645624913e265cad50293f659812a35c3c4303d1e8bdafdcca4eaf2e8ed5c69d`.
- 1.8.1 `Entity.java`: `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c`; `LivingEntity.java`: `fbfb0f0821a2d6b9d1d665387b2c5423fc0c1d8f5f36f7b5e1258b26d90c4c4f`; `CarpetBlock.java`: `991a6b1fce6e593803b73d44668091522551e3ea1443a8ab5d6685fa82b4ce15`; `World.java`: `f0873207c5c7a793189597470392fa0184513b3c5a682a6f8795e1c9ffde9f71`; `Block.java`: `4f645cb1f25248ba16649d9d1ab17b3dbe4c7316191deb982f641beff437e67c`; `Box.java`: `2d270470a11082e1cf9f772c94ecff9ecbf5430be78947dd152e2e72c20bbc52`.
- 1.8.2 `Entity.java`: `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`; `LivingEntity.java`: `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`.
- 1.8.2 `.decompile-provenance.json`: `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` (SHA-256). The manifest identifies Feather Gen2 `1.8.2+build.2`; its mapping artifact hash is `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b`.

PowerShell commands used to establish exact evidence:

```powershell
Get-FileHash -Algorithm SHA256 <coverage-or-source-path>
Get-Content <source-path> | Select-Object -Skip <zero-based-line-offset> -First <line-count>
```

Sources were read-only under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.8,1.8.1,1.8.2}\ornithe-feather`. No implementation, tests, decompilation, or runtime work was performed.
