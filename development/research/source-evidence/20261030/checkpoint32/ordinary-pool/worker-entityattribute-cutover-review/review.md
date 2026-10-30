# Independent cutover review: 1.8.4 → 1.8.5 movement-speed attribute cap

## Verdict

**Retain `EntityAttributes.java` as an actual changed movement finding in the full-pair freeze.** The independent author audit's conditional freeze wording makes this operation explicit; it should not be elided by the equal-input condition. Under the relevant equal-input comparison, the input is the same raw attribute update (base and modifier entries) on both clients. The changed client-side attribute definition then clamps the same computed value differently when that value exceeds 1024.0. Thus the post-clamp effective attribute must not be treated as an already-equal input.

The change is reachable through the client player movement-speed consumer. There is no client packet-handler gate restricting the incoming base/modifiers to a computed movement speed at or below 1024.0. An attribute update with base 1025.0 and no modifiers is a concrete source-level witness: the identical raw update produces 1025.0 in 1.8.4 and 1024.0 in 1.8.5. `PlayerEntity.getSpeed()` returns that value as a float. This is an input witness, not a claim that an unmodified vanilla server ordinarily emits exactly this update.

## Exact changed source and identity

Both sources are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`, Ornithe Feather Gen2 build 2, with provenance identities matching the supplied audits.

| Version | Source member/range | Relevant registration | SHA-256 |
|---|---|---|---|
| 1.8.4 | `net/minecraft/entity/living/attribute/EntityAttributes.java:18–20`, static `MOVEMENT_SPEED` initialization | `generic.movementSpeed`, default `0.7F`, minimum `0.0`, maximum `Double.MAX_VALUE`; tracked | `945f39931934b378359d428a2f0fb0b57c97bb2e162dcec7e5d8562efaf663aa` |
| 1.8.5 | same path/member/range | `generic.movementSpeed`, default `0.7F`, minimum `0.0`, maximum `1024.0`; tracked | `573689391528a81df3a1e221cc5055c4fbc30b3ad3a55adc0acf14647b5456c0` |

The field's declaration and chaining are otherwise unchanged in the supplied source comparison. The pair inventory identifies this as one of the 17 changed Java paths. `SHA256SUMS.txt` pins the inventory report and changed-path CSV; this supplemental review separately rehashed both `EntityAttributes.java` sources.

## Clamp and raw-packet path

The clamp occurs when the attribute instance computes its effective value, not when raw packet base data is stored:

- `net/minecraft/entity/living/attribute/RangedEntityAttribute.java:10–24,36–39`: constructor retains `min` and `max`; `clamp(value)` returns `MathHelper.clamp(value, min, max)`. This class is hash-identical across the pair (`907be80906539d0a2323ffc7d37f6c8e19498bcb2671b23395f342c2c57d5b03` each side).
- `net/minecraft/entity/living/attribute/ModifiableEntityAttributeInstance.java:42–47`: `setBase` stores its supplied double directly and marks dirty; no range clamp is performed there. At lines 137–155, effective value computation adds operation-0 modifiers to the base, calculates operation-1 additions from that intermediate value, applies operation-2 multipliers, then calls `this.attribute.clamp(e)`. This class is also hash-identical (`3a8ddba11c80ee90640a1b0b85fd498d5e557459ed5b8bb64ea6a30ab7a0157d` each side).
- `net/minecraft/network/packet/s2c/play/EntityAttributesS2CPacket.java:21–30,34–51,55–69`: the packet carries each attribute ID, raw base double, and modifier UUID/amount/operation. Read/write does not clamp or normalize these values. Hash-identical on both sides (`b24f98567111cb698abb89770b24607000a6b8c5b2d2a970db076ee69e53ce41`).
- `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:1581–1605`: for an existing living entity, each entry is looked up by ID; the handler registers a ranged attribute only for an unknown ID. For the player's existing `generic.movementSpeed`, it directly calls `setBase(entry.getBaseValue())`, clears modifiers and adds the received modifiers. It does not compare the base or computed movement speed against 1024.0. This consumer is unchanged across the pair (`7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3`).
- `net/minecraft/entity/living/player/PlayerEntity.java:1297–1300`: `getSpeed()` returns `(float) this.getAttribute(EntityAttributes.MOVEMENT_SPEED).get()`. It is unchanged (`e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`). The inherited player movement code consumes `getSpeed()` in `net/minecraft/entity/living/LivingEntity.java:1135` (movement-speed-scaled travel branch) and `:1214` (ground acceleration interpolation); this class is also hash-identical across the pair (`082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`).

## Reachability condition and disposition

For a concrete update, the target is the local player entity already present in the client's world; the packet contains the known ID `generic.movementSpeed`, base `1025.0`, and no modifiers. The same packet is parsed and dispatched in either release. `setBase(1025.0)` stores that raw value on either side; the effective getter clamps it only afterward. The 1.8.4 attribute maximum permits 1025.0, while the 1.8.5 maximum reduces it to 1024.0. Both are representable as distinct `float` values when `PlayerEntity.getSpeed()` casts the result, so the direct movement-speed input differs. More generally, the cap changes the result exactly when the post-modifier computed value is greater than 1024.0 (for ordinary finite values); at or below 1024.0, this changed upper bound has no effect. No minimum-side difference is introduced because the lower bound remains 0.0.

Record this as the **attribute cap operation**: same raw received base/modifier state, changed ranged-attribute registration, clamp during computed-value evaluation, then player `getSpeed()` consumer. Do not redefine the comparison input as an already-final clamped attribute value; doing so would erase the operation's actual effect. Preserve the separate Beacon/effect caveat and equal-received-state qualification as independent issues.

## Immutable audit identity and review identity

Author full-pair audit: `.native-source/20261030/ordinary-pool/worker-fullfreeze-184-185/audit.md`, SHA-256 `0a081c452cbece81edc1d3775251a2bedde7a65a2d7f80967db9e5b0254a1729`.

Reviewed source inventory identities: `report.md` SHA-256 `56103dee13e9bc5e6e56775beec20d9d7841bbf76bf9699414fb54338502bbcc`; `changed-source-paths.csv` SHA-256 `2408d2f8ee64b0c4063eff54ccaa1fdcb4ddb9ec1907a8ec1f9026fd19c8cabb`; original independent audit at `.native-source/20261028/frontier-c/184-185-ordinary-movement-audit/audit.md` SHA-256 `fb88518c29a91ed0c203b75db9a35b8ee25ce66fea36e826596f421e1836014c`.

This review covers only the assigned EntityAttributes cutover and its raw packet/effective-value/player consumer path. It does not revise the pair-wide verdict on other operations, establish an exact intermediate patch boundary, or claim runtime parity.
