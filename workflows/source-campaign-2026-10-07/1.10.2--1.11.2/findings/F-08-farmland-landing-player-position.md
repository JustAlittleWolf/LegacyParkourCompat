# F-08: The 1.11.2 farmland collapse raises intersecting players to the dirt top

- Older version A: 1.10.2
- Newer version B: 1.11.2
- Mechanic / coverage slice IDs: landing callback; block replacement position response; `S4-farmland-fall-player-position`, `S4-callbacks`, `INV-WORLD-MOVEMENT`, `INV-EXTERNAL`, `INV-STATE`
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: historical player movement response on the server
- First changed release: unknown within (1.10.2, 1.11.2]
- Runtime validation: not performed

## Paired evidence

### A artifact identity

- Evidence artifact record ID: A in `../run.md`
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.10.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`
- Evidence manifest path / SHA-256: sibling `revision.json` / `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.10.2/artifacts.sha256` / `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`
- Original derived-artifact availability and SHA-256 or expected hash: original mapped JAR unavailable; original manifest expected `ab7aa536f52e94c4c099999bc731020c11a1b29e2979a566cedcc2ffb00e701d`
- Source/raw-input hash relation and verification reference: unchanged source manifest `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather.sources.sha256` / `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; raw input manifest is the original artifact manifest above. Source owner and worker verified these manifests and cited source hashes; independent ops verification covered the six-bundle revision set.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified; the original mapped JAR is unavailable and the snapshot hash differs.
- Provenance limitations: source-tree provenance is established by the unchanged publication records; identity with the unavailable original mapped JAR is not established.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java::FarmlandBlock#onFallenOn`, lines 61-71, `63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f`; `.../net/minecraft/entity/Entity.java::Entity#move/checkFallDamage/setPosition`, lines 630-660, 808-817 and 282-288, `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`; `.../net/minecraft/block/Block.java` farmland registration ID 60, lines 856-857, `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`.

### B artifact identity

- Evidence artifact record ID: B in `../run.md`
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`
- Evidence manifest path / SHA-256: sibling `revision.json` / `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.11.2/artifacts.sha256` / `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`
- Original derived-artifact availability and SHA-256 or expected hash: original mapped JAR unavailable; original manifest expected `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`
- Source/raw-input hash relation and verification reference: unchanged source manifest `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather.sources.sha256` / `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; raw input manifest is the original artifact manifest above. Source owner and worker verified these manifests and cited source hashes; independent ops verification covered the six-bundle revision set.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified; the original mapped JAR is unavailable and the snapshot hash differs.
- Provenance limitations: source-tree provenance is established by the unchanged publication records; identity with the unavailable original mapped JAR is not established.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java::FarmlandBlock#onFallenOn/setDirt`, lines 61-81, `09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8`; `.../net/minecraft/entity/Entity.java::Entity#move/checkFallDamage/setPosition`, lines 690-721, 873-882 and 295-301, `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`; `.../net/minecraft/world/World.java::World#getEntities(Entity,Box)`, lines 2127-2147, `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff849a9a7d0cb82efb58`; `.../net/minecraft/world/chunk/WorldChunk.java::WorldChunk#getEntities(Entity,Box,List,Predicate)`, lines 667-688, `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f`; `.../net/minecraft/block/Block.java` farmland registration ID 60, lines 861-862, and full-cube shape / collision delegation, lines 289-291 and 368-370, `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`.

For both sides, `EntityFilter.NOT_SPECTATOR` is the default entity-query filter; an ordinary server player in the intersecting query box is included. A and B source hashes are recorded in `../run.md`.

## Source-level difference

Both endpoints register farmland as block ID 60 and invoke `FarmlandBlock.onFallenOn` when `Entity.move` lands with positive accumulated fall distance. The collapse guard is the same: server world, `random.nextFloat() < fallDistance - 0.5F`, a `LivingEntity`, a player or mob-griefing permission, and `width * width * height > 0.512F`. For a normal player with fall distance `2.0F`, the random comparison is always true because `nextFloat()` is below `1.0F`.

A replaces the farmland block with dirt and then follows the base fall callback. B performs the same block replacement in `setDirt`, obtains the new dirt block's full collision box, queries non-spectator entities intersecting that box, and calls `setPosition(entity.x, box.maxY, entity.z)` on each. A player landing at the center of the farmland block has its feet at the farmland collision top, `posY + 0.9375`; its box intersects the new dirt box up to `posY + 1.0`, so B includes that player and writes its y coordinate to `posY + 1.0`.

## Reachability and dependencies

The local/server player movement path clips downward movement, sets `onGround`, reads the landed block at `floor(y - 0.2F)`, and calls `checkFallDamage`. With positive fall distance, `checkFallDamage` dispatches to the landed block's `onFallenOn`. A player landing on registered farmland reaches the unchanged collapse guard. B's dirt-box query includes intersecting non-spectator players and directly updates their position; `Entity.setPosition` rebuilds the bounding box from that new y coordinate.

## Consequence and uncertainty

The source proves an immediate server-side player y-position write to the new dirt top in B for the stated conditions; A makes no corresponding position write. This finding covers only that direct movement response. The adjacent fall-damage call remains excluded from the campaign's damage simulation scope. Runtime validation and client correction timing were not performed. Original mapped-JAR equivalence remains unverified; blind source review is pending.

## Handoff

Keep separate from F-06's moving-fence query scan and F-07's pose-fit query. Applicability is limited to a qualifying server-side player landing on farmland that changes to dirt. Introduction is unknown within the endpoint pair.
