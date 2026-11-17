# Independent source audit: tiny position write r4

**Verdict: ACCEPT (bounded source finding and explicit source-derived witness).** This verdict is bound to candidate SHA-256 `90A91D80CDC6082DDF07A570369241CB84755F7FA2D6727A5669EB62275B1A56` and source-check SHA-256 `0E34FAAD7C00BE35F470897F17EB24B5AC25125A098E39C610407D157F936981`. The audit was performed against exact source identities below, without runtime replay. It does not freeze or close 1.21.1→1.21.3 coverage and makes no implementation claim.

## Source identity binding

- 1.21.1 Mojmap ready-marker SHA-256 `988B38AC59491F9430D5BD1968DB0FDAACB777A63FEF745CA3E20342237A0310`; source-ledger SHA-256 `900F956E00F6FC1300BB3D689EA49DF2B1A57BCAA54617344EF456D95D47CB48`; provenance SHA-256 `6F84A596EA944A1D40C79B53EAD9D2CDD2B3C726D710A977758D15B5578CE7A9`.
- 1.21.2 in-tree `mojmap/.decompile-provenance.json` SHA-256 `00F399407DC447737EA83E3E46562418F8C712F66E3F51CB85D46AFA2ECAC575`. Its per-source hash for `net/minecraft/world/entity/Entity.java` matches the local file; 1.21.2 and 1.21.3 `Entity.java` are byte-identical.
- 1.21.3 Mojmap ready-marker SHA-256 `72385AA99D249A56D742B9C07823C1155C74C39DDA1FBC6EACC12C980CD12E02`; source-ledger SHA-256 `D673BB5464853E3A2A92ED9B1FFE1789D4E62C097BB884F67C336FCBB3B178CE`; provenance SHA-256 `24093437C0CD41D807AB0829068EF97828B65BBB95B38DFDC4F706883A8483D7`.
- Endpoint `Entity.java`, `Player.java`, `LivingEntity.java`, `TrapDoorBlock.java`, `LadderBlock.java`, `BlockBehaviour.java`, `Blocks.java`, `EntityType.java`, and `CollisionGetter.java` hashes cited in r4 match their local endpoint bytes and source ledgers. The 1.21.2 `Player.java` and `LivingEntity.java` local bytes likewise match its embedded provenance map.

## Independently checked behavior

1.21.1 `Entity.move` writes position only if resolved `collided.lengthSqr() > 1.0E-7`. 1.21.2 and 1.21.3 add `requested.lengthSqr() - collided.lengthSqr() < 1.0E-7` to the same condition. Comparisons are strict. When the request is collision-free, nonzero, and has squared magnitude below the threshold, the old branch skips `setPos`; the newer branch reaches it because the difference is zero. For the tick-11 request, `1.4372140044212282E-8 < 1.0E-7`; at center z≈0.338 the nonzero displacement is representable. The resulting `setPos` updates position and its bounding box.

The source path in r4 is valid: local player input reaches ordinary `LivingEntity` travel; travel multiplies `xxa`/`zza` by `0.98F`, airborne friction-influenced movement uses `0.02F`, movement input is then passed to `moveRelative`, and the resulting SELF move invokes `Entity.move`. Shift is the normal ladder-slide suppression predicate. The open-trapdoor fallback is evaluated at the player's current block position, and accepts an open trapdoor when the block below is exactly `Blocks.LADDER` with equal facing. It does not depend on tag membership.

I independently recomputed the listed float/double recurrence (`Math.fround(0.75F * 0.98F)`, `0.02F` promoted to double, `0.91F` friction, per-tick `<0.003` stored-component clamp, signs `--++--+--++`). The movement requests and stored values match `source-check-r4.md`; the eleven requests sum to `-0.16212122103327614`, and tick 11 is `-0.00011988386064943138`.

The geometric/support argument is sufficient under its stated environmental preconditions. At z center 0.5, player width 0.6 gives z bounds [0.2, 0.8]. Both north-facing open trapdoor and ladder collision shapes occupy z [13/16,1], so they do not intersect the player or the support probe. A backing column beginning at z=1 is also separated. `Player.canFallAtLeast` submits its full downward AABB to `CollisionGetter.noCollision`; with the entire support probe clear of block/entity/border collision, `canFallAtLeast` is true and `isAboveGround` false, so edge backoff is bypassed. The independently stated collision-free swept-AABB precondition leaves each requested horizontal displacement unchanged. This reasoning covers the stated witness and does not generalize beyond it.

## Resource-form audit

I checked the exact endpoint publication trees, prepared source-resource inputs, both relevant decompile-cache directories, and archive entries. No `data/minecraft/tags/block/climbable.json` payload is published for 1.21.1 or 1.21.3; the pinned client-jar path for those releases is absent from the recorded artifact location and their secondary cache paths. The prepared packet contains only 26.2. A cached 1.21.2 client jar does contain the tag entry; its SHA-256 matches the 1.21.2 provenance input (`613091DA0F62426AE8DCF9AFB30EB639485F493B81CDA3A2B96B1FB8A6B9976C`), and the extracted-in-memory `data/minecraft/tags/block/climbable.json` bytes are 295 bytes, SHA-256 `D0E3E76D7457F3F3F3D7219FE218C7746E4B2E7626D5C388FCF193089069E365`, including `minecraft:ladder`. Thus the r4 statement is accurate when read specifically as endpoint-resource availability: the two endpoint payloads are not directly hashed; the intermediate 1.21.2 payload is available and was checked here. The r4 witness is independent of all tag data.

## Remaining gates

Pair-wide source inventory, all remaining movement/collision callbacks, external writers, resource-pack overrides and an independent full-pair coverage audit remain open. This accepted bounded finding does not satisfy those gates.
