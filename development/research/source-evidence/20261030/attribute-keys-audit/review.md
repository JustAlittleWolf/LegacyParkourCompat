# Independent source review: 1.21.1 → 1.21.2 movement attribute keys

## Immutable review binding

- Draft: `.native-source/20261028/frontier-c/1211-1212-attribute-key-inputs/report.md`
- Draft SHA-256 reviewed: `c5fd86ab659252e22ea31f37129c1676180b737803d30c3ed2273dbff33caca7`
- Verdict: **accept the bounded source finding**, with the caveats and gates below. This is an independent review of the assigned attribute-key slice; it is not pair-wide coverage acceptance.

## Evidence checked

I read exact Mojmap sources under the read-only canonical roots `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.1/mojmap/net/minecraft/` and `.../1.21.2/mojmap/net/minecraft/`.

The 1.21.1 readiness record identifies version `1.21.1`, Mojmap, ready status, 5,363 sources and source manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; its readiness record hash is `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`. The 1.21.2 mapping root has the embedded `.decompile-provenance.json` (SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`); its embedded source map has 5,654 paths. Selected file hashes in the draft match the files I rehashed, and the 1.21.2 embedded map entries for `Attributes.java` and `ClientPacketListener.java` match those files.

In `world/entity/ai/attributes/Attributes.java`, the checked definitions preserve the three holder fields and numeric/sync properties:

| Holder | 1.21.1 key | 1.21.2 key | Default and range | Sync |
|---|---|---|---|---|
| `GRAVITY` | `generic.gravity` | `gravity` | `0.08`, `[-1.0, 1.0]` | yes; neutral sentiment |
| `MOVEMENT_SPEED` | `generic.movement_speed` | `movement_speed` | `0.7`, `[0.0, 1024.0]` | yes |
| `WATER_MOVEMENT_EFFICIENCY` | `generic.water_movement_efficiency` | `water_movement_efficiency` | `0.0`, `[0.0, 1.0]` | yes |

The descriptions also lose the `generic.` component. Both versions use the same local `register` helper, which applies `ResourceLocation.withDefaultNamespace` and registers the attribute holder. No compatibility alias for these old keys appears in the checked Java definitions. The registration path establishes an identity change; it does not by itself establish data-pack migration, raw wire compatibility, or cross-version decoding.

I checked the cited attribute calculation and receiver paths in both versions: `AttributeInstance.calculateValue`, `AttributeMap` holder lookup/value access, `LivingEntity.getAttributeValue`, `LivingEntity.getDefaultGravity`, the water-efficiency read in `LivingEntity.travel`, `Player.getSpeed`/attribute use and player base setup, plus `ClientPacketListener.handleUpdateAttributes` and the packet snapshot/codec. The direct value consumers continue to read the corresponding holder and use the same effective numeric value operations under equal decoded holder/value state. The attribute calculation retains the same operation ordering for base and modifiers. The packet codec is the same, and the receiving handler performs the same local target lookup and base/modifier updates, including when that target is the local player. This supports the draft’s conditional conclusion: the key rename alone does not change these local movement calculations when effective attribute state is equal.

## Residual gates and limits

- **External key compatibility remains separate and open.** The source change means a consumer naming the prior built-in `minecraft:generic.*` key cannot be assumed to resolve to the new built-in identity in 1.21.2. The checked Java sources expose no alias. Determining impact to data packs, commands, registry-aware mods, or migration behavior requires their actual lookup/codec paths and any relevant non-Java resources; this audit does not claim such breakage is or is not movement-reachable.
- **Client receiver behavior does not prove equal server-produced state.** The handler accepts already-decoded `Holder<Attribute>` snapshots. This audit did not inspect server-side packet production or compare dynamic registry IDs/encoding across separately running versions. Do not infer cross-version server/client compatibility or that a 1.21.1 server will produce equivalent values for a 1.21.2 client from the unchanged receiver alone.
- **Other 1.21.2 movement changes remain outside this attribute-key verdict.** In particular, the slow-falling/effective-gravity travel factoring noted by the draft is independent of the key identity check. The attribute finding does not accept, reject, or characterize it.
- This review accepts only the bounded three-attribute identity and direct client movement-consumer claim. It closes no additional tick inventory, intermediate patch boundary, or pair-wide audit requirement.

No product sources/history, wiki material, backlog, tests, build, decompilation, server or runtime were read or run. The canonical source roots were treated as read-only.
