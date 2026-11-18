# Exact source comparison: 1.14 → 1.14.1

Scope: portal-adjacent ordinary passenger dismount and its placement/correction chain, plus the concrete XYZ write reached while a logical-client player remains mounted. This is source-only endpoint coverage, not full-pair coverage or runtime acceptance.

## Source identity

Both sides use the same mapping family, Yarn, with each release's locally pinned mapping build. 1.14 has Yarn 1.14+build.21 (artifact SHA-256 0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64) and intermediary 1.14 (SHA-256 45e354d58c74473865054faa62a229e30f46ba2019000affdb3fb748df3fde17). 1.14.1 has Yarn 1.14.1+build.10 (artifact SHA-256 f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630) and intermediary 1.14.1 (SHA-256 486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d). These are the exact pinned input identities in the per-side provenance manifests; mapping build numbers are not assumed equal.

Manifest SHA-256: 1.14/yarn/.decompile-provenance.json ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341; 1.14.1/yarn/.decompile-provenance.json 308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907.

| Release | Exact local source | Actual file SHA-256 (matches provenance sources entry) |
| --- | --- | --- |
| 1.14 | decompiled_minecraft/1.14/yarn/net/minecraft/entity/LivingEntity.java | 8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6 |
| 1.14.1 | decompiled_minecraft/1.14.1/yarn/net/minecraft/entity/LivingEntity.java | 331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda |
| 1.14 | decompiled_minecraft/1.14/yarn/net/minecraft/server/network/ServerPlayerEntity.java | 6e7b36cc3ef188fe1d144685bcd6620c363f2b95ac9e52b41df310babb325bae |
| 1.14.1 | decompiled_minecraft/1.14.1/yarn/net/minecraft/server/network/ServerPlayerEntity.java | 6e7b36cc3ef188fe1d144685bcd6620c363f2b95ac9e52b41df310babb325bae |
| 1.14 | decompiled_minecraft/1.14/yarn/net/minecraft/entity/Entity.java | f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a |
| 1.14.1 | decompiled_minecraft/1.14.1/yarn/net/minecraft/entity/Entity.java | ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b |
| 1.14 | decompiled_minecraft/1.14/yarn/net/minecraft/client/network/ClientPlayerEntity.java | 92d0f97a9293f7bd57d49af00b70f13d78fc8b2202e7ed67edcfed917e0071be |
| 1.14.1 | decompiled_minecraft/1.14.1/yarn/net/minecraft/client/network/ClientPlayerEntity.java | 6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933 |

The source hashes above were recomputed from the local Java files and checked against their provenance entries. The provenance input pins include client JAR SHA-256 93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a (1.14) and 7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814 (1.14.1).

## Compared slices and disposition

| Operation/member | Exact source ranges | Result |
| --- | --- | --- |
| LivingEntity.stopRiding and generic dismount placement helper method_6038 | LivingEntity.java:2305–2312, 1650–1722 on both sides | No behavior difference found. The method body range SHA-256 is fc734d00d7c07a8d014035bfa62deb75b42bc5708a4a46a52d73db5d955f36cc for stopRiding, and 573f1f7b36d5d2fb8ecfdb2a123746bcbc3a8e45c74b858485f1d891278b00ad for the placement helper, equal on both sides. stopRiding snapshots the old vehicle, calls super.stopRiding(), and only invokes placement if the vehicle changed and !world.isClient. The helper excludes boats and horses; for other mounts it uses the mount movement direction and collision/support checks to select a stand-up location, then calls requestTeleport for selected/fallback coordinates. |
| Portal eligibility adjacent to dismount | Entity.java:1672–1695 on both sides | No behavior difference found; exact method range SHA-256 d687d075d067f101162c46ffdf4075c0a105751b3e820e0f6e16dd2703321ed009 on both sides. Portal ticking is gated to world instanceof ServerWorld; transfer requires Nether enabled, !hasVehicle(), and the portal-time threshold. This is a server portal eligibility predicate, not a client movement replay or an ordinary dismount placement rule. |
| ServerPlayerEntity.stopRiding outer correction and requestTeleport override | ServerPlayerEntity.java:758–766, 1080–1084 on both sides | No behavior difference found. Range hashes: a0280be243a46c97ceab1bdd65a026676bfdd0fd628d98f753621a792048eb7d and c7549287e3a6e931ff9d90d4a67eb0063232624275871159b3c65d671d7cbdce, respectively, equal on both sides. The override snapshots the prior vehicle, calls super.stopRiding(), then sends an absolute position/rotation correction when vehicle identity changed and networkHandler != null. If the inherited living-entity helper reaches requestTeleport, virtual dispatch resolves to the server-player override, which also requests an absolute correction; the outer correction follows if its gate passes. |
| Mounted passenger logical-client XYZ write | Entity.java:1522–1534, 304–311 on both sides; reached through inherited tick flow from ClientPlayerEntity.tickRiding (ClientPlayerEntity.java:808–817) | No behavior difference found. Entity.tickRiding zeroes velocity, ticks, then asks the vehicle to update passenger position if still mounted. Generic updatePassengerPosition writes (vehicle.x, vehicle.y + mountedHeight + passengerHeightOffset, vehicle.z) through setPosition; setPosition assigns x, y, z and rebuilds the bounding box. The client override adds boat-input forwarding only; Yarn's mapped method spelling changes from method_7535 to setInputs, but it contains no XYZ assignment. Corresponding range hashes are 9ffbe693876ae536b99c65822f060d01797b80e431e7e859cc38f19bc6dfbee3 and d8f4dc74ad6289dabe301b9869ab5be4b5222462059c1d9732779e09c79519e2, equal on both sides. |

## Bounded claim / remaining gap

No source difference was found in the assigned 1.14 → 1.14.1 dismount, portal gate, server-player correction, or mounted passenger XYZ writer slices. The vehicle-passenger path and exact local/server gates are covered for these members and this adjacent release pair. This does not establish the intermediate introduction boundary outside this pair, other vehicle/passenger geometry, other tick paths, full-pair coverage, or runtime parity. No finding is proposed for this pair from these slices.