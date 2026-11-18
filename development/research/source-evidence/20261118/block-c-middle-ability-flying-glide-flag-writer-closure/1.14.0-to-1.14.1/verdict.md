# Independent source-audit verdict: 1.14 → 1.14.1

**Verdict:** Accept the bounded no-difference conclusion for the assigned scenario. This verdict covers the exact local-player sequence only; it does not accept a difference, close other pair operations, or freeze the full pair.

**Reviewed immutable report:** sibling `c-mid-114-1141-ability-flight-glide-clear-source/report.md`, SHA-256 `0ee0690692072030c93b0ddc0d2e1f2c3f1dc355d0170e98ce5f551eb91989a2`. The file hash matched the assignment before review.

## Independent trace

The comparison was performed blind to the report until the canonical sources, manifests, cache inputs, and cited source hashes below had been pinned. It confirms the report's scoped source conclusion:

1. `ClientPlayerEntity.tickMovement` reaches `super.tickMovement()` (1.14 Yarn line 762; same excerpt in 1.14.1). `LivingEntity.tickMovement` calls `initAi()` and then dynamically dispatches `this.travel(...)` at line 2204 on both sides. Existing fall-flying state is represented by entity flag 7 (`LivingEntity.isFallFlying`, line 2590; `Entity.getFlag`, lines 1822–1824).
2. For the stated pre-state, `PlayerEntity.travel` takes `abilities.flying && !hasVehicle()` (lines 1375–1387). Its `super.travel(arg)` invokes the `LivingEntity.travel` implementation before the outer player writes. In dry conditions, active flag 7 selects the Elytra path (lines 1772–1803): if current Y velocity is greater than `-0.5`, it first sets `fallDistance` to `1.0F`, computes the gliding velocity, and calls `move`.
3. The Elytra method's own flag clear is gated by `onGround && !world.isClient` (lines 1814–1816), so it does not clear flag 7 for this airborne local-client case. After the actual move returns, `PlayerEntity.travel` restores the temporary fly-speed field and then separately writes `fallDistance = 0.0F` followed by `setFlag(7, false)` (lines 1380–1385). `Entity.setFlag` clears the requested bit with `b & ~(1 << i)` (lines 1826–1832).
4. These gates and writes are the same in 1.14 and 1.14.1. The complete `PlayerEntity.java` bytes match; the compared `LivingEntity.travel` excerpt (1763–1910) and the `ClientPlayerEntity.tickMovement` excerpt (651–711) compare line-for-line equal. `EquipmentSlot.field_6174` still names the `chest` slot in both trees. The client Elytra start request itself requires `!abilities.flying`; this verdict treats the assignment's flag-active state as the precondition, and does not claim that request starts gliding while creative flight is already enabled.

## Frozen source identities

Canonical root: `D:/Javastuff/LegacyParkourCompat`; the root `.git` directory is the common Git directory. Sources are read-only under `decompiled_minecraft/`.

Hidden Yarn provenance manifests (SHA-256):

- 1.14: `decompiled_minecraft/1.14/yarn/.decompile-provenance.json` — `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341`.
- 1.14.1: `decompiled_minecraft/1.14.1/yarn/.decompile-provenance.json` — `308537c5adec3104c5ca243eb5010554f00978635da726b6bc472405ae91f4341`.

Both manifests record the `lpc-decompile-v1` single-thread Yarn pipeline and runtime `Eclipse Adoptium:25.0.3+9-LTS:Windows 11:amd64`. Exact client and mapping inputs recorded in those manifests were checked against their local cache pins and archive SHA-256 values:

| Release | Client jar SHA-256 | Version metadata SHA-256 | Yarn mapping archive SHA-256 | Intermediary archive SHA-256 | Yarn metadata SHA-256 |
| --- | --- | --- | --- | --- | --- |
| 1.14 | `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` | `3d05fc911c8559105767713365ea1c29bbbeccf57214f286c86a598ce51f22a6` | `yarn-1.14+build.21-v2.jar` `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64` | `intermediary-1.14-v2.jar` `45e354d58c74473865054faa62a229e30f46ba2019000affdb3fb748df3fde17` | `303a1e22ad9d0e9244057ae04288f2be1e2d608a306aaa4572d4245db531873d` |
| 1.14.1 | `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` | `71c39840d012fcbe8fedb7be876bd008365b62ec7b1c9c5c7bd34d9a29c8ca22` | `yarn-1.14.1+build.10-v2.jar` `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` | `intermediary-1.14.1-v2.jar` `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d` | `6d026a20d6165444f67df52ddcc29f575f0267ab4b545c3bb427e5803c434873` |

Cited Java source SHA-256 values were independently matched to the manifests and local files:

| Source | 1.14 | 1.14.1 |
| --- | --- | --- |
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | same |
| `net/minecraft/entity/LivingEntity.java` | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `92d0f97a9293f7bd57d49af00b70f13d78fc8b2202e7ed67edcfed917e0071be` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` |
| `net/minecraft/entity/Entity.java` | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |
| `net/minecraft/entity/EquipmentSlot.java` | `8417d14120153d3513c868e0dd7b22e32bc1dbd9ee63cfcbb306a05d6d87ca47` | same |

## Binding and limits

This independent verdict is bound to report SHA-256 `0ee0690692072030c93b0ddc0d2e1f2c3f1dc355d0170e98ce5f551eb91989a2` and the exact canonical manifest/source identities above. It applies only to ordinary local, dry, airborne, nonpassenger player movement with flag 7 already active, a usable default chest Elytra, and lawful creative flight ability active. No intermediate patch boundary, other tick slice, runtime parity, or full-pair coverage is inferred.
