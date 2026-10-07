# Discovery: 1.17.1 to 1.18.2

- Run status: partial
- Scope: direct client player movement, including direct player velocity/impulse/knockback application and resulting player state even when triggered by combat. A=1.17.1, B=1.18.2. Excludes health/food state production, attack/damage resolution, non-player movement, vehicle physics, and behavior for blocks/features absent in A.
- Repository revision and start date: base 002137b227676caea77f6832b9f4c8d0b6200bff; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojang official mappings, mojmap; exact metadata IDs verified.
- Source preparation owner / command / log / readiness marker: shared source owner; Gradle decompileMinecraft, explicit mojmap; build/movement-campaign-2026-10-07/mojmap.success.log; ready/{1.17.1,1.18.2}/mojmap.ready.json.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1, Java 25.0.3+9, Vineflower 1.12.0, ASM 9.10.1, mapping-io 0.9.1, Gson 2.14.0, TinyRemapper 0.14.1; 4G heap.
- Discovery author(s): Codex source-only pair worker.
- Independent reviewer (must differ from discovery authors): pending.

## Artifact manifest

SHA-256; paths relative to repository. Each 41-entry artifact manifest and source manifest verified against marker.
- A source root build/movement-campaign-2026-10-07/ready/1.17.1/mojmap; client a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d; client mappings 2b28ded68f8602aaf2f35ef92dd10ee41ba2cf9723e29570155d60e1723848e9; mapped jar 2a2be036174902e447865498741b8c59fa2e090d352d786a8507dccb7c23008c; source manifest 93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b (4,142 files); artifact manifest e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa; diagnostics e98f7769de9ed18eb77f5967a20643d5b67c48f0fb3914437b8bf3c8565d5dbe.
- B source root build/movement-campaign-2026-10-07/ready/1.18.2/mojmap; client 1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7; client mappings a2aa6ee1030bfef79e9b2e08e79de1637fdd7ecb5bf8891cf2e9a4b186042543; mapped jar 60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba; source manifest aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a (4,236 files); artifact manifest a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036; diagnostics 0bc8857ee048b0ede8e696f3b7b050006618b278936cf182b3464c6c99483ac8.
- Additional external source hashes: ClientPacketListener A a59ba067bb0b9cdf026159a87534fd456ed766ec287cfd23e24356f84f078a5a / B e718016022c2ae86a2c354af2d34fe4de7d6e36dc8792d2e2c1f08abb6b77b7b; ServerGamePacketListenerImpl A 270139c717fea2fbbe871c2163f9bd022371e0c01f5d1b87a55d45cff22a7f59 / B c979dd50b8d80d2d00190e08360a4fc689693715a84e248503099f340a8546c9; Boat A 70ae54899433bc5a7d3df16360732206ff1a754a9eb4ee29be6b2536f164d27d / B 4e09ba747b5ee6a6a63c9663bebc96c561013501126550fe55e506b9cce33fe4; Mth A 24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113 / B 32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e; ClipContext A ff14d98c5193dd2792270e36631aace4a034b5da0a2c48e13f260454fc85b210 / B c26cca8b2a2f185463b3785218518174831ea5a06268b04d9dd5c3a5287ac4ff; EntityGetter A 2491753cf00c868e42b1451b255a6ed2c1a4a3c4194445f47772bb592e6cc3e9 / B f7fa5641d3287d5084f4450e1c09cd4d5816c5cff5412c6f07b14779bb60215f. Water fluid tag entry SHA-256 698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18 is identical in both client jars. Source hashes: LocalPlayer A c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812 / B 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095; LivingEntity A 33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f / B db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782; Entity A ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de / B 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a; Player A 724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481 / B bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a; CollisionGetter A eb707479e75cf200731df4546a546fb984be33cf3e4a8e17d3065a916497f747 / B ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c; CollisionSpliterator A c4f8158bd6778159946ebf16c22a6c12f5a4bccb40e2f1215f4fc930bb218bd0; BlockCollisions B 11fecadc012d0a544097bf0febfa1a83da5a534e6182ee7df7b2334b6ee80127.
- B tag data/minecraft/tags/blocks/fall_damage_resetting.json SHA bda5807a4d0edf5e0641bc63312f7ca83e5bb58f9c34c09ac01b7829b0d1a850; values climbable, sweet_berry_bush, cobweb; A absent. Spot-check climbable tag same (7ba8e23faf48885e91af7458e0928c21fa74b20d2f1c5ce9a1161a4f481fd45c), soul_speed_blocks same (c86fbd7bcfa2b94c881f0ba9980a694b28150a5b0b628aec0cfedc552f68994f).
- Derived artifact revision feather-r1-2026-10-07 does not apply to this pair; it contains early Feather 1.8.9-1.13.2 only. This pair uses Mojmap and has no revised mapped-jar snapshot. Pair markers/manifests and cited source hashes were freshly checked; both markers still resolve exact IDs and 41 artifact entries.
- Movement diagnostics show cited bodies and no relevant warning/error markers; this does not close all source files.

## Blind-discovery freeze

- This status is the full-pair freeze; finding snapshots are tracked separately.
- Status: pending
- Freeze commit/checkpoint and timestamp: none; terminal coverage, closed dependencies and independent full-pair audit remain outstanding.
- Evidence inventory and finding IDs included at freeze: none; F-001 through F-005 are in the partial discovery catalog.
- Old mod implementation and isolated wiki-audit outputs opened: no; prior report used for navigation only.
- Source/mapping hashes: pair markers, manifests and hashes above.
## Correspondence and call order

A/B roles: LocalPlayer#aiStep client tick; KeyboardInput#tick input; LivingEntity#travel branches; Player#travel delegation; Entity#move resolution; Player#maybeBackOffFromEdge edge state. Auto-jump calls CollisionGetter and stops at first matching AABB. A collision query is block then entity and CollisionSpliterator can add border; B is entity then block, with border elsewhere. External path: LocalPlayer#tick -> sendPosition -> player movement packet -> ServerGamePacketListenerImpl#handleMovePlayer calls Player#move; inbound client corrections, velocity/explosion impulses and passenger updates are bounded in slices below. Full graph and other writers remain open.

## Required source inventories

- `INV-TICK` input/tick/travel: status=pending; slice_ids=T-INPUT,T-SPRINT,T-ELYTRA,T-AUTOJUMP-ORDER; evidence=LocalPlayer and LivingEntity paired source ranges; full call graph open.
- `INV-STATE` movement state producers/consumers: status=pending; slice_ids=T-SPRINT,T-FALL-RESET,T-EDGE-GATE; evidence=LocalPlayer, Entity and Player paired source ranges; remaining state paths open.
- `INV-COLLISION` collision query/shapes/callbacks: status=pending; slice_ids=T-AUTOJUMP-ORDER,T-AUTOJUMP-BORDER,T-ENTITY-COLLISION,T-PLAYER-CORRECTION,T-PLAYER-IMPULSE,T-BOAT-PASSENGER,T-POSITION-THRESHOLD; evidence=LocalPlayer, CollisionGetter, CollisionSpliterator and BlockCollisions ranges; providers open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties/resources: status=pending; slice_ids=T-WORLD-PROPERTIES,T-FALL-RESET; evidence=Blocks seed registrations and tag entry; exhaustive resource/property inventory open.
- `INV-MODIFIERS` attributes/effects/enchantments/equipment: status=pending; slice_ids=T-ELYTRA,T-MODIFIERS; evidence=LivingEntity travel and Jump Boost consumer; data/application paths open.
- `INV-EXTERNAL` player-only external inputs and direct velocity/impulse/knockback application: status=pending; slice_ids=T-PLAYER-CORRECTION,T-PLAYER-IMPULSE,T-BOAT-PASSENGER,T-POSITION-THRESHOLD; evidence=ClientPacketListener correction, motion/explosion and passenger handlers are now bounded below; all other external writers remain open.
- `INV-EXCLUSIONS` health/food state production, attack/damage resolution, non-player motion and vehicle physics: status=pending; evidence=scope exclusions above; direct player-motion response remains in scope; complete boundary audit open.

## Coverage ledger

### Slice T-INPUT: keyboard sampling
- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: KeyboardInput#tick slowdown; ClientPacketListener replacement sites located.
- A evidence: KeyboardInput.java SHA ea41065c909e53f1a2cc29ecdb6a9a8f9265d2801cd8b95b996e182c318ecd69; ClientPacketListener about lines 376,961.
- B evidence: KeyboardInput.java SHA 281622f8481654035196a7bc1554d5251c1040518375e3ac6f6439e5ec894a75; ClientPacketListener about lines 375,960.
- State producers/writers -> consumers/readers: key state -> impulses -> LocalPlayer/LivingEntity.
- Parent slices / dependencies / closure evidence: D-INPUT-ASSIGNMENTS open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A double 0.3 then float cast; B 0.3F. Normal inputs -1,0,1; assignment closure open.
- Finding IDs or checked absence/replacement path: none.

### Slice T-SPRINT: minor collision stop gate
- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer#aiStep stop expression and Entity#move flag/classifier.
- A evidence: build/movement-campaign-2026-10-07/ready/1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep, lines 715-724, SHA-256 c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812; Entity.java::Entity#move, lines 553-565, SHA-256 ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de.
- B evidence: build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep, lines 708-718, SHA-256 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095; #isHorizontalCollisionMinor, lines 998-1014, same hash; Entity.java::Entity#move, lines 543-584 and base classifier lines 676-678, SHA-256 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a.
- State producers/writers -> consumers/readers: resolved movement -> collision flags -> sprint decision.
- Parent slices / dependencies / closure evidence: flag edge traced; full solver open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): A stops for any horizontal collision; B exempts minor collision if other stop terms are false.
- Finding IDs or checked absence/replacement path: F-001.

### Slice T-ELYTRA: fall-flying coefficient
- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: LivingEntity#travel cosine to lift/pull.
- A evidence: build/movement-campaign-2026-10-07/ready/1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#travel, lines 2074-2090, SHA-256 33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f.
- B evidence: build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java::LivingEntity#travel, lines 2080-2099, SHA-256 db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782.
- State producers/writers -> consumers/readers: pitch/look/velocity -> coefficient -> velocity.
- Parent slices / dependencies / closure evidence: D-ELYTRA-ENTRY open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): A float Mth.cos/cast; B double Math.cos retained in same equations.
- Finding IDs or checked absence/replacement path: F-002.

### Slice T-AUTOJUMP-ORDER: first probe candidate
- Inventory ID(s): INV-TICK, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: updateAutoJump query, first-match break, candidate height/timer.
- A evidence: build/movement-campaign-2026-10-07/ready/1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#updateAutoJump, lines 904-990, SHA-256 c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812; world/level/CollisionGetter.java::getCollisions, lines 55-59, SHA-256 eb707479e75cf200731df4546a546fb984be33cf3e4a8e17d3065a916497f747.
- B evidence: build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#updateAutoJump, lines 920-990, SHA-256 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095; world/level/CollisionGetter.java::getCollisions, lines 65-75, SHA-256 ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c.
- State producers/writers -> consumers/readers: shapes -> first maxY -> autoJumpTime.
- Parent slices / dependencies / closure evidence: D-ENTITY-COLLISIONS resolved by paired EntityGetter and Entity.canCollideWith source; D-SHAPE-PROVIDERS open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): A block-first, B entity-first; different heights can change candidate; entity filters unclosed.
- Finding IDs or checked absence/replacement path: F-003.

### Slice T-AUTOJUMP-BORDER: border query
- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: auto-jump query and border producer.
- A evidence: build/movement-campaign-2026-10-07/ready/1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java::updateAutoJump, lines 956-959, SHA-256 c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812; world/level/CollisionSpliterator.java::worldBorderCheck, lines 103-116, SHA-256 c4f8158bd6778159946ebf16c22a6c12f5a4bccb40e2f1215f4fc930bb218bd0.
- B evidence: build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java::updateAutoJump, lines 952-953, SHA-256 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095; world/level/CollisionGetter.java::getCollisions/getBlockCollisions, lines 67-80, SHA-256 ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c; borderCollision is not called on this path.
- State producers/writers -> consumers/readers: border -> A iterator -> probe.
- Parent slices / dependencies / closure evidence: D-BORDER-MOVE-PATH open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): A may include nearby border shape; B query omits it; consequence depends on geometry.
- Finding IDs or checked absence/replacement path: F-004.

### Slice T-FALL-RESET: movement clip writer
- Inventory ID(s): INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: Entity#move after edge-backoff/collision, before position update.
- A evidence: build/movement-campaign-2026-10-07/ready/1.17.1/mojmap/net/minecraft/world/entity/Entity.java::Entity#move, lines 553-566, SHA-256 ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de; checked absence of matching clip/reset in this body.
- B evidence: build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/net/minecraft/world/entity/Entity.java::Entity#move, lines 562-575, SHA-256 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a; tag evidence in manifest.
- State producers/writers -> consumers/readers: clip/tag/fluid -> fallDistance -> next Player edge gate.
- Parent slices / dependencies / closure evidence: D-RESET-CLIP-FILTER resolved by B ClipContext block/fluid predicates, resetFallDistance writer, and matching water tag; B-only fall_damage_resetting tag recorded above.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): new writer may change later gate; current gate runs before reset; damage excluded.
- Finding IDs or checked absence/replacement path: F-005.

### Slice T-EDGE-GATE: player edge predicate
- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Player#maybeBackOffFromEdge/#isAboveGround.
- A evidence: build/movement-campaign-2026-10-07/ready/1.17.1/mojmap/net/minecraft/world/entity/player/Player.java::maybeBackOffFromEdge, lines 1017-1044 and #isAboveGround, lines 1069-1072, SHA-256 724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481.
- B evidence: build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/net/minecraft/world/entity/player/Player.java::maybeBackOffFromEdge, lines 1032-1060 and #isAboveGround, lines 1082-1085, SHA-256 bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a.
- State producers/writers -> consumers/readers: onGround/fallDistance/maxUpStep/noCollision -> edge gate.
- Parent slices / dependencies / closure evidence: D-RESET-CLIP-FILTER is resolved; collision/shape closure remains open and can affect when the predicate sees support.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): paired predicate and shifted-box test match; F-005 changes a producer.
- Finding IDs or checked absence/replacement path: F-005.

### Slice T-ENTITY-COLLISION: solver and state writes
- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: Entity#move/#collide axis/step resolution, flags, velocity.
- A evidence: Entity/CollisionGetter/CollisionSpliterator identified and hashed; body splitting pending.
- B evidence: Entity/CollisionGetter/BlockCollisions identified and hashed; body splitting pending.
- State producers/writers -> consumers/readers: delta -> position/flags/velocity/fallDistance -> player gates.
- Parent slices / dependencies / closure evidence: D-SHAPE-PROVIDERS,D-BORDER-MOVE-PATH open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): solver changed; axes, ties, providers and post-move writes not closed.
- Finding IDs or checked absence/replacement path: downstream F-001,F-004,F-005.

### Slice T-WORLD-PROPERTIES: block/fluid registrations
- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: movement factors and historical shape/contact providers.
- A evidence: Blocks seed entries inspected; complete list pending.
- B evidence: paired seed entries inspected; complete list pending.
- State producers/writers -> consumers/readers: block/fluid -> shape/factor/callback -> movement.
- Parent slices / dependencies / closure evidence: D-SHAPE-REGISTRY,D-BLOCK-CALLBACKS,D-MOVEMENT-TAGS open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): selected ice/soul sand/honey/slime properties align; exhaustive inventory open.
- Finding IDs or checked absence/replacement path: none.

### Slice T-MODIFIERS: attributes/effects/equipment
- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: defaults and modifier application/removal.
- A evidence: travel/Player anchors inspected; data pending.
- B evidence: paired anchors/tags inspected; data pending.
- State producers/writers -> consumers/readers: modifiers -> speed/jump/fluid/Elytra.
- Parent slices / dependencies / closure evidence: D-EFFECT-DATA,D-ATTRIBUTE-REGISTRY,D-ENCHANTMENT-DATA open.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): F-002 and Jump Boost consumer are partial.
- Finding IDs or checked absence/replacement path: F-002.

### Slice T-PLAYER-CORRECTION: inbound position correction

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: ClientPacketListener#handleMovePlayer applies relative/absolute position, velocity and rotations, then sends teleport acceptance and current position.
- A evidence: build/movement-campaign-2026-10-07/ready/1.17.1/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java::handleMovePlayer, lines 549-613, SHA-256 a59ba067bb0b9cdf026159a87534fd456ed766ec287cfd23e24356f84f078a5a.
- B evidence: build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java::handleMovePlayer, lines 541-605, SHA-256 e718016022c2ae86a2c354af2d34fe4de7d6e36dc8792d2e2c1f08abb6b77b7b.
- State producers/writers -> consumers/readers: server position packet/relative flags -> player position, delta movement and rotations -> subsequent local tick.
- Parent slices / dependencies / closure evidence: correction producer is external input; compared only the client application path.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): paired movement application and response packet values match; A's trailing startup-screen state is outside the movement boundary.
- Finding IDs or checked absence/replacement path: no movement difference in this bounded receiver path.

### Slice T-PLAYER-IMPULSE: inbound entity motion and explosion knockback

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: clientbound entity motion and explosion knockback writes to client entities/player.
- A evidence: ClientPacketListener#handleSetEntityMotion, lines 445-454, and #handleExplosion, lines 973-993; ClientPacketListener SHA-256 a59ba067bb0b9cdf026159a87534fd456ed766ec287cfd23e24356f84f078a5a; Entity#lerpMotion lines 1890-1892, Entity SHA-256 ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de.
- B evidence: ClientPacketListener#handleSetEntityMotion, lines 441-447, and #handleExplosion, lines 972-977; SHA-256 e718016022c2ae86a2c354af2d34fe4de7d6e36dc8792d2e2c1f08abb6b77b7b; Entity#lerpMotion lines 1885-1887, Entity SHA-256 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a.
- State producers/writers -> consumers/readers: server-supplied velocity/knockback -> lerpMotion or player delta movement -> next local movement tick.
- Parent slices / dependencies / closure evidence: external packet is an input; Entity#lerpMotion delegates to same setDeltaMovement operation on both sides.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): when the motion packet targets the local player, both versions use the same velocity setter; both explosion handlers add the packet knockback vector to the player's current delta movement. Damage and explosion simulation remain excluded.
- Finding IDs or checked absence/replacement path: no movement difference in these bounded client writers.

### Slice T-BOAT-PASSENGER: player yaw on passenger-list updates

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer#startRiding and ClientPacketListener#handleSetEntityPassengersPacket boat yaw writes.
- A evidence: LocalPlayer#startRiding, lines 154-169, SHA-256 c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812; ClientPacketListener handler lines 825-843, SHA-256 a59ba067bb0b9cdf026159a87534fd456ed766ec287cfd23e24356f84f078a5a.
- B evidence: LocalPlayer#startRiding, lines 153-163, SHA-256 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095; ClientPacketListener handler lines 826-850, SHA-256 e718016022c2ae86a2c354af2d34fe4de7d6e36dc8792d2e2c1f08abb6b77b7b.
- State producers/writers -> consumers/readers: passenger packet -> player yRotO/yRot/yHeadRot -> player-facing direction and later movement input.
- Parent slices / dependencies / closure evidence: packet handler computes whether the local player was already an indirect passenger before ejecting/rebuilding the passenger list.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): A's successful LocalPlayer#startRiding resets all three yaw fields whenever the vehicle is a Boat. B removes that local write and resets them in the passenger packet handler only when the player was not already an indirect passenger. On a passenger-list refresh while the player is already aboard the same boat, A resets yaw to boat yaw and B preserves the current player yaw. This is a player-facing transition; vehicle physics is outside this slice.
- Finding IDs or checked absence/replacement path: F-006.

### Slice T-POSITION-THRESHOLD: client movement packet cadence

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: LocalPlayer#sendPosition displacement predicate, packet branch and position baseline reset.
- A evidence: LocalPlayer#sendPosition, lines 240-269, SHA-256 c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812.
- B evidence: LocalPlayer#sendPosition, lines 232-261, SHA-256 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095; Mth#square(double), lines 739-741, and #lengthSquared(double,double,double), lines 799-801, SHA-256 32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e.
- State producers/writers -> consumers/readers: local position delta -> position packet send and xLast/yLast/zLast reset -> ServerGamePacketListenerImpl#handleMovePlayer processes each received player move using Player#move; server handler hashes are recorded in artifact manifest addendum.
- Parent slices / dependencies / closure evidence: D-PACKET-RECONCILIATION open; packet reaches server player movement, but corrective response and net movement outcome are not closed.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): Preconditions: the local player is not a passenger, controlled-camera is true, and positionReminder less than 20, so the reminder fallback does not send. In that reachable branch A sends only when squared displacement is greater than 9.0E-4, while B sends when squared displacement is greater than Mth.square(2.0E-4) = 4.0E-8. Any squared displacement strictly between 4.0E-8 and 9.0E-4 therefore sends a position packet in B and none in A; after either send, both versions update the position baseline.
- Finding IDs or checked absence/replacement path: F-007.
## Dependency queue and blockers

Resolved: D-ENTITY-COLLISIONS (paired LocalPlayer entity query and EntityGetter/Entity.canCollideWith); D-RESET-CLIP-FILTER (B ClipContext block/fluid predicates, resetFallDistance assignment, tag and water resource entries). Open: D-INPUT-ASSIGNMENTS,D-SHAPE-PROVIDERS,D-BORDER-MOVE-PATH,D-ELYTRA-ENTRY,D-EFFECT-DATA,D-ATTRIBUTE-REGISTRY,D-ENCHANTMENT-DATA,D-SHAPE-REGISTRY,D-BLOCK-CALLBACKS,D-MOVEMENT-TAGS,D-EXTERNAL-VELOCITY,D-MOUNT-INPUT,D-PACKET-RECONCILIATION. Each can affect movement; pair discovery owns retrieval. No external blocker.

## Finding index

- [F-001 minor collision sprint-stop](findings/F-001-minor-horizontal-collision-sprint.md) — source-confirmed.
- [F-002 Elytra cosine precision](findings/F-002-elytra-cosine-precision.md) — source-confirmed.
- [F-003 auto-jump candidate order](findings/F-003-autojump-candidate-order.md) — candidate; filters/geometry open.
- [F-004 auto-jump border candidate omitted](findings/F-004-autojump-border-shape.md) — source-confirmed query delta.
- [F-005 long movement fallDistance reset before later edge check](findings/F-005-fall-distance-reset-edge-gate.md) — source-confirmed writer/consumer and clip filter closed.
- [F-006 boat passenger-list refresh yaw behavior](findings/F-006-boat-passenger-yaw-refresh.md) — source-confirmed player yaw write change under repeated passenger update.
- [F-007 client movement packet displacement threshold](findings/F-007-position-packet-threshold.md) — source-confirmed packet condition; movement reconciliation consequence remains open.
- Discarded: KeyboardInput literal precision alone (normal inputs -1,0,1; assignments open); camera bob literal change (visual-only in inspected path). Prior report was navigation; F-001/F-002 rechecked.

## Resume checkpoint

- Last completed: T-SPRINT,T-ELYTRA,T-AUTOJUMP-ORDER,T-AUTOJUMP-BORDER,T-FALL-RESET.
- Next: close T-EDGE-GATE after collision support semantics; continue Entity#collide, B CollisionGetter#borderCollision, BlockCollisions, Shapes#collide; KeyboardInput/ClientPacketListener assignment; ClipContext/reset/tag.
- Outstanding: D-* above.
- Assumptions: entity filter equivalence, probe overlap, B border path, qualifying clip.

## Finding snapshots (not pair freeze)

No finding snapshot has been submitted or accepted. Findings remain source-discovery items; implementation handoff requires a separate blind source reviewer to accept an immutable finding snapshot with its dependencies closed. This does not change pair status.

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: none; old/current implementation not opened.
- Finding dispositions: deferred until after blind freeze review.
- Existing implementation without finding: not inspected.
- Gaps routed: D-*.

## Independent source audit

- Reviewer: pending; must differ from author.
- Status: pending
- Inventories/call graph re-walked: none.
- Missed-slice routes: pending; misses routed: pending; evidence/date: pending.

## Source audit closure

- Coverage counts: pending=2; in-progress=4; compared-no-difference=2; findings=7; not-applicable=0; blocked=0.
- Required inventories: all seven pending.
- Open dependencies: above.
- Unresolved gaps: partial; resources, shape providers, external inputs and full call graph open.
- Evidence/hash audit: exact markers/manifests and cited class/resource hashes recorded; schema validation is not proof.
- Accepted finding snapshots: none.
- Full-pair blind freeze: pending; implementation reconciliation: pending; independent audit: pending.
- Source discovery: partial.
- Runtime validation: not performed; no tests or gameplay run.
