# Corrective supplement: 1.8.4 → 1.8.5 movement-speed attribute cap

## Correction and disposition

This immutable supplement corrects the equal-input wording of the 1.8.4 → 1.8.5 full-pair source audit. Preserve `EntityAttributes.java` as an actual movement-source difference in the frozen pair. For this operation, equal input means the same raw received attribute entry (attribute ID, base value, and modifier entries) is applied to the same existing player under equal relevant movement/world state. It does not mean that the final computed, locally clamped movement attribute is already equal: that definition removes the changed operation from comparison and erases its effect.

With identical raw `generic.movementSpeed` packet data of base `1025.0` and no modifiers, the existing player computes speed `1025.0` in 1.8.4 and `1024.0` in 1.8.5. This is a concrete reachable source-level input witness; it does not claim an unmodified vanilla server ordinarily emits that value. The changed cap affects a finite post-modifier value exactly when it exceeds `1024.0`; at or below `1024.0`, the changed upper bound has no effect. The unchanged lower bound is `0.0` on both sides.

The pair-wide conditional closure for other paths can retain its equal received state/world/player constraints, but that condition must not absorb this independently changed local computation. The accepted operation remains part of the full-pair freeze, not an exception that disappears under a redefinition of input.

## Exact source trace and identities

Canonical read-only roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.4/ornithe-feather/` and `.../1.8.5/ornithe-feather/`, Ornithe Feather Gen2 build 2. Their provenance hashes are 1.8.4 `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` and 1.8.5 `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce`.

| Release | Exact changed member | `generic.movementSpeed` registration | Source SHA-256 |
|---|---|---|---|
| 1.8.4 | `net/minecraft/entity/living/attribute/EntityAttributes.java:18–20`, static `MOVEMENT_SPEED` | default `0.7F`, min `0.0`, max `Double.MAX_VALUE`, trackable | `945f39931934b378359d428a2f0fb0b57c97bb2e162dcec7e5d8562efaf663aa` |
| 1.8.5 | same member/range | default `0.7F`, min `0.0`, max `1024.0`, trackable | `573689391528a81df3a1e221cc5055c4fbc30b3ad3a55adc0acf14647b5456c0` |

The clamp path is otherwise identical on both sides. `RangedEntityAttribute.java:10–24,36–39` stores the range and implements `clamp(value)` with `MathHelper.clamp(value, min, max)` (same SHA-256 `907be80906539d0a2323ffc7d37f6c8e19498bcb2671b23395f342c2c57d5b03`). `ModifiableEntityAttributeInstance.java:42–47` stores `setBase` input directly and marks the instance dirty; it does not clamp on receipt. `computeValue():137–155` applies operation-0 additions to the base, operation-1 additions using that intermediate base, then operation-2 multipliers, and clamps the result last (same SHA-256 `3a8ddba11c80ee90640a1b0b85fd498d5e557459ed5b8bb64ea6a30ab7a0157d`).

`EntityAttributesS2CPacket.java:21–30,34–51,55–69` carries and reads/writes attribute ID, raw base double, and modifier UUID/amount/operation without this movement-speed clamp; it is identical on both sides (SHA-256 `b24f98567111cb698abb89770b24607000a6b8c5b2d2a970db076ee69e53ce41`). For an entity already in the client world, `ClientPlayNetworkHandler.handleEntityAttributes` at `ClientPlayNetworkHandler.java:1581–1605` gets the existing attribute by ID, sets the received base, clears modifiers, and adds the received modifiers. It only creates a ranged attribute for an unknown ID; no 1024 gate limits the known player movement-speed entry. This handler is identical (SHA-256 `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3`).

The local movement consumer is `PlayerEntity.getSpeed()` at `PlayerEntity.java:1297–1300`, which casts the effective `MOVEMENT_SPEED` attribute value to `float` (same SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`). `LivingEntity.java:1135` consumes it in the grounded travel acceleration branch and `:1214` in ground acceleration interpolation (same SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`). The two witness values remain distinct after the float cast.

## Immutable report and review bindings

This supplement amends the interpretation of, but does not alter, these historical immutable records:

- Ordinary pair report: `.native-source/20261028/frontier-c/184-185-ordinary-movement/report.md`, SHA-256 `56103dee13e9bc5e6e56775beec20d9d7841bbf76bf9699414fb54338502bbcc`.
- Ordinary independent audit: `.native-source/20261028/frontier-c/184-185-ordinary-movement-audit/audit.md`, SHA-256 `fb88518c29a91ed0c203b75db9a35b8ee25ce66fea36e826596f421e1836014c`.
- Full-pair author audit: `.native-source/20261030/ordinary-pool/worker-fullfreeze-184-185/audit.md`, SHA-256 `0a081c452cbece81edc1d3775251a2bedde7a65a2d7f80967db9e5b0254a1729`.
- Independent attribute cutover review: `.native-source/20261030/ordinary-pool/worker-entityattribute-cutover-review/review.md`, SHA-256 `776f834d6300e3d3dd2f9658a447b31c353a8e42b6b753631b75aa595d9fafe6`.

The full-pair audit explicitly preserves the cap as a separate accepted operation; this supplement makes the equal-input boundary precise so the freeze wording cannot be read to condition away its demonstrated local effect. The adjacent pair comparison establishes only that the cap changed somewhere between 1.8.4 and 1.8.5. The exact intermediate patch introduction boundary remains unknown.

## Bounds

This is a source-only author correction for this attribute operation. It does not revise the Beacon/effect producer analysis, establish an exact cutover within the interval, or claim runtime parity. No product code/history, wiki, backlog, other source lane, decompilation, download, build, test, or game process was used.
