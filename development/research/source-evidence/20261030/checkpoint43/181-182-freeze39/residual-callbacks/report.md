# 1.8.1–1.8.2 residual player callback closure

## Verdict and bounds

Completed the assigned residual callback comparison for the listed block callback overrides, their `Blocks` singleton/ID links, the dispatch gates in `Entity.move`, and the remaining census callback role `SlimeBlock.onFallenOn`. No new movement-relevant delta was found. This closes only this callback residual; it does not establish full pair coverage or freeze. No implementation sources, wiki material, tests, builds, game launches, or decompilation were consulted/run.

## Exact inputs and provenance

Read-only mapped source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.1\ornithe-feather` and `...\1.8.2\ornithe-feather`, Ornithe Feather gen2 `1.8.1+build.2` / `1.8.2+build.2`. Client-jar SHA-256 values are `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd` / `5cc5b24312f3d182bf06e7f1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9`; mapping-jar hashes are `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` / `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b`. Both sources use the `lpc-decompile-v1` pipeline recorded in their `.decompile-provenance.json`. Manifest SHA-256 values, as established by the supplied tick-inventory report, are `F3A3BAA039F0E0ADE6E656DFA11A4529909A4B709AC489703C4250262CEFB1CA` / `8AD11EA36F2DDA39AE30D57DAA53316A8C0889A2F3C0C84EE5D79B9B21C25472`.

Every source file below was hashed during this pass and its on-disk SHA-256 matched the `sources` entry in its own version’s provenance manifest:

| Paired source | 1.8.1 SHA-256 | 1.8.2 SHA-256 |
|---|---|---|
| `net/minecraft/block/AbstractPressurePlateBlock.java` | `65988423c93e2b5407352bb9cf42944150b819ad6e8614dd23d11bc2519676e1` | `85896b070be577d228e8a660f1f95cae8956467a957463c4fc839080d2e5b1cb` |
| `net/minecraft/block/ButtonBlock.java` | `b88b5febf175d1a7f8d6450aab436e10d7c6d4f7daa1c1b1c1b0996b3b2450` | `e79f9805438fc92233e709feffa83091febde8de2a3daae8280183ef251e5681` |
| `net/minecraft/block/CactusBlock.java` | `43662795dcc7c7814f4d30eefaee198d068985c0f9fa787c2a00807c20b4177b` | `9724244c881244e4bd050524bf6179c276fe49129f136164fb0eeeba18a791cd` |
| `net/minecraft/block/CauldronBlock.java` | `dd008f83f8256d8761276c3f7964ad55e3ec20a46247075500fe4bc86c0fec84` | `cbc1724ea6707ce7f22981867cb62d4931240499291d18bd5e45a81d7a33836a` |
| `net/minecraft/block/DetectorRailBlock.java` | `dbf007969b718b2446c8e4d6989bd5daf78fd4032fd09d2094dcd3f12e698cf2` | `4f8cafd027c333003a521b5ed65b5c3de382b028b8ad048f36175e8ce61d5e94` |
| `net/minecraft/block/EndPortalBlock.java` | `e7c305af824f5243c983f854720d157c0e84e58f782d4d6cafbe1822da01de92` | `1cb419f44592e68244e06b34799c6354a2bcc3d08bd849e0709fca36b54e1e40` |
| `net/minecraft/block/PortalBlock.java` | `88a91311e7bf6a8b4c14792379c1dd021a532fb223a9e0639d5c8819d4f48654` | `bb9d2cba26df71c8383408fcccc93b849eebecd3d36924a63ee80cba8734867e` |
| `net/minecraft/block/TntBlock.java` | `a8e4aa51cda0134c4c5fd9093d7cdce71b8a02a808a8535b79e4e7329b346424` | `1c8e1f9453079db13e60b484e182c1813a054e81a7d4c524aba8ac54e1babed7` |
| `net/minecraft/block/TripwireBlock.java` | `c2efa5cdb782f83e201796e31be9a91440a2b2ca62dc9ee861c4e72308c4ae2f` | `caad055a848650e80f73074b758222cfb72d9b928c38769a29038c5d11764be7` |
| `net/minecraft/block/RedstoneOreBlock.java` | `b2909858ec2cda4641245a8e12d7ca56b18dba93fbc51b3d10654356c1a86e6a` | `9b2efe542205a9ccba4f86f472f973a1beab413f3de3773dd28f4bf6915911db` |
| `net/minecraft/block/StairsBlock.java` | `b50094c97dadaaf056ada6466bae8005a72c820e9846ef352886f1fd78caa5cb` | `fadc964480227f6ffd5756471212b18d0172bffbcd0866ee0c278454ab079409` |
| `net/minecraft/block/SlimeBlock.java` (remaining fall callback) | `01ce3f9be824cf75aa8eca1cf59f23e966ac4b4b472e6812cdc95998abd6b5ce` | `fa00f5b8903fa580e860cd72cfc11827a06eaad4600d05bd0f209849b04cbafa` |
| `net/minecraft/block/Blocks.java` | `af42dc7d1fe95a3cc070427e32cd76213d6eab4df49118fee99aa3c7a3a004b0` | `1da2d85406ed991e8f6bc1f420babf0bd43598478f347c6a4dc9fea322ed0695` |
| `net/minecraft/block/Block.java` | `4f645cb1f25248ba16649d9d1ab17b3dbe4c7316191deb982f641beff437e67c` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| `net/minecraft/entity/Entity.java` | `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |


## Paired callback comparison and movement role

- `Entity.move`: grounded step dispatch is 1.8.1 `Entity.java:579–589`, 1.8.2 `:575–585`; after movement, `checkBlockCollisions()` is called at `:611–618` / `:607–614`, and it dispatches `Block.onEntityCollision` at `:652–670` / `:648–666`. `Entity`’s `onFallenOn` callback path is reached by the movement landing/fall path already described by the collision report. The dispatch methods are generic `Entity` paths and can be used for local players; provider-level world-side gates below determine whether they affect client movement.
- `AbstractPressurePlateBlock.onEntityCollision` (`:98–105` / `:103–110`) is equivalent: `!world.isClient`, read signal, and update only when signal is zero. Server-only redstone state production; no local movement delta.
- `ButtonBlock.onEntityCollision` (`:205–213` both) is equivalent: server-only, wooden-only, unpowered gate then `updatePressed`. Server-only state production; no local movement delta.
- `DetectorRailBlock.onEntityCollision` (`:44–50` / `:55–61`) is equivalent: server-only unpowered gate and output-state update. Server-only state production; no local movement delta.
- `TripwireBlock.onEntityCollision` (`:151–157` both) is equivalent: server-only unpowered gate and output-state update. Server-only state production; no local movement delta.
- `CactusBlock.onEntityCollision` (`:98–100` / `:97–99`) calls `entity.takeDamage(DamageSource.CACTUS, 1.0F)` identically. The callback is reached by an intersecting player shape but only damage is changed; damage calculation is explicitly excluded. Its separate cactus shape is covered by the supplied collision-provider report.
- `CauldronBlock.onEntityCollision` (`:67–74` both) reads water level, computes `pos.y + (6.0F + 3 * level) / 16.0F`, and when server-side, burning, nonempty, and `entity.getShape().minY <= threshold`, extinguishes the entity and decrements level. Operation/gates match; this is fire/block-state handling, not movement. `addCollisions` (`:36–49` both) also matches in its floor plate, 0.125F wall order, and shape reset; the geometry was independently covered in the collision report.
- `EndPortalBlock.onEntityCollision` (`:61–65` both) requires no vehicle/rider and `!world.isClient` before dimension transfer. It is server-only; no local player movement response.
- `PortalBlock.onEntityCollision` (`1.8.1 :169–173`, `1.8.2 :162–166`) requires no vehicle/rider and calls `Entity.onPortalCollision(pos)` on both sides. The reached `Entity.onPortalCollision` (`1.8.1 :1404+`, `1.8.2 :1397+`) only recalculates portal coordinates/offsets on `!world.isClient`; client entry can reset a positive portal cooldown but does not set a movement velocity/position. Eventual server-side dimension transition is outside tick movement parity. No movement delta.
- `TntBlock.onEntityCollision` (`:91–99` / `:91–104`) has identical server-only arrow and fire gates, then primes/removes TNT. It cannot trigger from the local player entity and modifies block/explosion state rather than local player movement. Damage/explosion handling is excluded.
- `RedstoneOreBlock.onSteppedOn` (`:40–43` both) is called only after grounded-step dispatch and invokes the same `interact` then base callback. `interact` (`:51–56` both) adds particles and switches unlit to lit ore; no player velocity/shape/state write occurs. The block-state/particle production is not a movement delta.
- `StairsBlock.onSteppedOn` (`1.8.1 :470–472`, `1.8.2 :468–470`) delegates to `baseBlock.onSteppedOn` identically. No player movement effect. Its multi-shape collision builder (`:386–396` / `:384–394`) is also same ordered base/inner/optional-step additions and reset; the shape behavior itself is already in the supplied collision-provider coverage.
- Remaining census callback: `SlimeBlock.onFallenOn` (`:24–30` both) has the same sneaking fallback and `takeFallDamage(fallDistance, 0.0F)` branch. The latter changes damage only; the movement-changing slime response is `beforeCollision` / `onSteppedOn`, already checked in the tick-inventory report and collision report. No remaining unpaired movement callback delta found in the supplied census.

## Registrations checked

`Blocks.java` pair retains the same singleton lookup/alias entries and numeric line positions for the callback providers and stair family: detector rail (245), TNT (263), stairs IDs (270, 289, 339–340, 345, 359, 365–367, 388–410), pressure plates (292, 294, 378–379), redstone ores (295–296), stone/wood buttons (299, 374), cactus (303), nether portal (317), cauldron/end portal (349–350), tripwire hook/tripwire (362–363). The class source hash differs, but the exact paired assignments point to the same IDs/classes; registrations were checked, not inferred from class names. `Block.java` default callback declarations and `Entity` dispatch order were also paired and match the exact paths recorded in the supplied collision/tick reports.

## Differences, dependencies, and residual coverage

Differences in the paired files are decompiler spelling/layout only where shown: local names (`var5`/`i`, `var4`/`f`, etc.), typed generic annotations (`List` vs `List<Box>`), and formatting of the TNT `ignite` invocation. Callback predicates, literals, casts, state writes, call ordering, and provider registrations show no behavioral change.

Dependencies closed for this slice: supplied collision report’s shape/state provider census; supplied tick report’s `Entity.move` callback dispatch and independent `SlimeBlock` step/before-collision checks. This report does not close broader player tick/travel, fire or damage semantics, scheduled block ticks, resource/tag inputs, or the still-open independent pair audit. The server-gated piston predicate source difference recorded by the tick-inventory report remains a separate disposition; this callback report does not determine its ownership/applicability.

## Next actions

Merge these callback closure/no-delta results into the pair coverage record. Retain the piston NORTH/DOWN finding as its own source-only disposition until the owning role and any client event consequence are reconciled. Continue the open registration, packet, effect/attribute, and independent full-pair coverage audit named in the supplied tick-inventory report.
