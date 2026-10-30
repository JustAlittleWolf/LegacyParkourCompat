# 1.8.4 → 1.8.5 Beacon, received status, attributes, and packaged-resource closure review

## Scope and verdict

Independent bounded review of the existing same-input closure material, limited to the changed Beacon effect filter, its effect/status/attribute consumers, default packaged resources, and exact input identity. I did not inspect product code/history, wiki inventories, backlog, or other discovery lanes. I did not decompile, download, build, test, or launch anything.

**Verdict:** the bounded same-input conclusion closes for equal received status state, equal received movement-attribute state, and equal movement-relevant world/player state. Beacon input can differ across versions and can lead to unequal server-produced client state; that is an upstream input divergence, not a same-input client movement delta. `EntityAttributes.java` is a separate changed edge and remains deliberately out of this closure. No pair-wide freeze is claimed.

## Exact inputs and hashes

Canonical source roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.4/ornithe-feather/` and `.../1.8.5/ornithe-feather/`; both are Ornithe Feather Gen2 build 2, as recorded by the exact source provenance files. Source-manifest hashes: 1.8.4 `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00`; 1.8.5 `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce`.

Cached release artifacts checked read-only: 1.8.4 `client.jar` SHA-256 `d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b`, pin SHA-256 `14284fc531dca3c32cb1d9f023add11e043d27dab62c6cb427244dcfb089634`, version metadata SHA-256 `c7220eabc36645636c8597b167d9ca67d16580611ed2d7d8cd7a3c0aec904587`, version pin SHA-256 `cd53dd22e126e7be298b175125db8d2b9f23f5fc7a9ffd ddb6c0b569525d32ecd` (whitespace removed: `cd53dd22e126e7be298b175db8d2b9f23f5fc7a9ffdddb6c0b569525d32ecd`). 1.8.5 `client.jar` SHA-256 `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d`, pin SHA-256 `c379e50db72eef9a17c77885e02ecc4a620fa6a3a141263df4b368e199b15e51`, version metadata SHA-256 `4fe7e25babf0c16244675812332c1403f0a30f8dac03b9e7c9cfd40ba39c7597`, version pin SHA-256 `7715269257faefe9a94c06fd2f4dd1a6dc507c0e46e3dc652370cf47b32f727c5`. These match identities already independently reconciled in the supplied closure/review reports.

Exact source file hashes checked directly:

| Source file | 1.8.4 SHA-256 | 1.8.5 SHA-256 |
| --- | --- | --- |
| `BeaconBlockEntity.java` | `dc4a5b60ec087b2f4715769dfb242a20b319eab76309130b711cf767327ac361` | `a8be3009efe15803df64b9e88b54cfb8e4033946d1c478b64ea0dcf08ab2a1cf` |
| `ServerPlayNetworkHandler.java` | `c2de662af504d7128a855efd1b105097667f662a80919f569bdc922787d5197a` | `de2a90e177d03d7ce674c8e93ce1d730b036a4e05d3ec0819932087e41166692` |
| `BeaconScreen.java` | `85a485004f675d7bbfcc7638f2434e663db4686b51121a72d4dbeb2b0d555bec` | same (per source-map and prior independent review) |
| `StatusEffect.java` | `f9bb4d1839337229cb6f6d8dc9ae17a49cfad757a42388e00abbaa68be2b21a3` | same |
| `LivingEntity.java` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | same |
| `ClientPlayNetworkHandler.java` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` | same |
| `PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | same |
| `EntityAttributes.java` | `945f39931934b378359d428a2f0fb0b57c97bb2e162dcec7e5d8562efaf663aa` | `573689391528a81df3a1e221cc5055c4fbc30b3ad3a55adc0acf14647b5456c0` |

## Paired methods and dependency closure

- `BeaconBlockEntity.EFFECTS` (1.8.4 lines 31–33; 1.8.5 same): normal selector table has only Speed, Haste, Resistance, Jump Boost, Strength, Regeneration. The unchanged `BeaconScreen` builds buttons from this table and sends selected IDs; normal UI selection cannot select Slowness ID 2.
- `BeaconBlockEntity.readNbt` (1.8.4 lines 206–210; 1.8.5 lines 222–227): old side stores `Primary` and `Secondary` raw; new side routes both through `getBeaconEffect`, returning 0 for unsupported IDs.
- `BeaconBlockEntity.setData` (1.8.4 lines 327–337; 1.8.5 lines 343–354): old side stores raw effect IDs; new side filters IDs 1 and 2 through `getBeaconEffect`.
- `BeaconBlockEntity.getBeaconEffect` (new side lines 205–219): retains exactly the six effects in the UI list. `spreadEffects` (both sides lines 56–80) is unchanged; it requires active beacon, positive levels, server world (`!world.isClient`) and primary ID > 0, then applies the selected effect to nearby players.
- `ServerPlayNetworkHandler.handleCustomPayload`, `MC|Beacon` branch (old lines 1046–1059; new lines 1076–1089): both read two raw ints and, if the menu is a `BeaconMenu` and payment slot has an item, pass them to `setData(1, k)`/`setData(2, l)` without validating effect IDs. Therefore ID 2 is a concrete injected state path in both versions: 1.8.4 retains/spreads it; 1.8.5 filters it. This is not a normal vanilla UI choice.
- `StatusEffect` Slowness registration (lines 28–31, identical): ID 2 has `MOVEMENT_SPEED` modifier `-0.15F`.
- `ServerPlayerEntity.onStatusEffectApplied` (old report lines 772–780; unchanged): sends `EntityStatusEffectS2CPacket`. `ClientPlayNetworkHandler.handleEntityStatusEffect` (lines 1133–1142, unchanged) resolves entity and adds the received effect. `LivingEntity.onStatusEffectApplied` (lines 578–582, unchanged) installs effect attribute modifiers only on `!world.isClient`; the client-side status receipt does not itself install the movement-speed modifier.
- `TrackedEntity` attribute update writers and `EntityAttributesS2CPacket` (both sides identical, prior correction supplement lines 264–271, 317–323) carry authoritative values/modifiers separately. `ClientPlayNetworkHandler.handleEntityAttributes` (lines 1581–1605, unchanged) installs them. `PlayerEntity.getSpeed` and its movement path read `MOVEMENT_SPEED` (lines 449–459, 1297–1300, unchanged).

Changed-edge disposition: Beacon selection/storage/spread is a server-owned producer; network status and attribute state are separate incoming movement inputs. If produced/received state differs, the client inputs are unequal. Under equal received effect state, equal received movement attributes, and equal relevant world/player state, no Beacon-specific client movement divergence remains. `EntityAttributes.java` is independently changed and excluded here; equal effective attribute state is an explicit condition, not a conclusion that the separate cap change has no effect.

## Same effective packaged resources

After checking cached client JAR hashes against source manifests/pins, I enumerated and SHA-256 compared every non-directory ZIP entry by path and uncompressed contents. Each archive contains 5,597 entries; paths are equal, with zero additions/removals. There are 43 content differences. Exactly four differing entries are non-class: `META-INF/MANIFEST.MF`, `META-INF/MOJANGCS.RSA`, `META-INF/MOJANGCS.SF`, and `assets/minecraft/texts/splashes.txt`. No packaged data/registry JSON or movement-default resource changes. Thus default packaged resources are equal for the relevant movement input domain. User packs, world data, and server-produced state are not covered.

## Evidence reused and next queue opportunities

Reused only the assigned prior reports: `.native-source/20261030/ordinary-pool/184-185-same-input-closure/report.md` (SHA-256 `9cce308ea50f348e52a720b3612bb9c2a2a47c771497c2bed2feca1a4d2d1f18`), `.native-source/20261030/ordinary-pool/184-185-same-input-closure-review/review.md` and `correction-supplement.md`, and the explicitly cited 1.8.4→1.8.5 ordinary report/audit and 1.8.3→1.8.4 residual pair reports. Their full-pair coverage claims are not expanded here. The existing correction's concrete raw-payload route is independently confirmed.

Next bounded opportunities: close any remaining 1.8.4→1.8.5 pair inventory gates separately from this same-input supplement; resolve the already tracked 1.8.3→1.8.4 portal/vine scope boundaries on their own evidence; and, if the assignment broadens, review the distinct `EntityAttributes.java` operation under its existing accepted finding rather than folding it into Beacon closure. This report does not establish pair-wide freeze, exact introduction version, runtime parity, or implementation readiness.
