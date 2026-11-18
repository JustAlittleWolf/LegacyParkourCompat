# Independent packaged source-only dependency audit: 1.21.5–1.21.8

## Immutable inputs and identity

The frozen manifest supplement was independently hashed as SHA-256 `1ca61b7ae2f38dfaee5a26981281239b7106aa10c015b22297038f724a28037e`, matching the assigned identity.

Exact manifest bytes:

- `development/research/source-evidence/20261103/checkpoint03/1.21.5-resource-manifest.json`: SHA-256 `22257a196c00ce94b3ee1862f9405d1c2ecb5077703a085491bb3016110351a0`.
- `development/research/source-evidence/20261103/checkpoint03/1.21.8-resource-manifest.json`: SHA-256 `13e0dee9c6fc414d71e929b27eda860f310396da6524a700c10cdb67928b9283`.

The manifests identify versions 1.21.5 and 1.21.8. Their `clientSha256` values are respectively `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` and `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`. Both match the corresponding historical source-publication pins stated in the frozen supplement; the manifest identity fields also state that client bytes match those historical pins. This audit did not open client jars or other provenance records.

## Recomputed inventory result

Parsed both exact JSON manifests and compared their listed `files` entries. The 1.21.5 manifest contains 583 entries and the 1.21.8 manifest contains 593. Each contains exactly 22 paths matching `data/minecraft/tags/enchantment/*.json`; the two path sets are identical and all 22 SHA-256 digests match. There were zero path-set differences and zero digest mismatches in this tag subset.

The equal listed paths are:

- `data/minecraft/tags/enchantment/curse.json`
- `data/minecraft/tags/enchantment/double_trade_price.json`
- `data/minecraft/tags/enchantment/exclusive_set/armor.json`
- `data/minecraft/tags/enchantment/exclusive_set/boots.json`
- `data/minecraft/tags/enchantment/exclusive_set/bow.json`
- `data/minecraft/tags/enchantment/exclusive_set/crossbow.json`
- `data/minecraft/tags/enchantment/exclusive_set/damage.json`
- `data/minecraft/tags/enchantment/exclusive_set/mining.json`
- `data/minecraft/tags/enchantment/exclusive_set/riptide.json`
- `data/minecraft/tags/enchantment/in_enchanting_table.json`
- `data/minecraft/tags/enchantment/non_treasure.json`
- `data/minecraft/tags/enchantment/on_mob_spawn_equipment.json`
- `data/minecraft/tags/enchantment/on_random_loot.json`
- `data/minecraft/tags/enchantment/on_traded_equipment.json`
- `data/minecraft/tags/enchantment/prevents_bee_spawns_when_mining.json`
- `data/minecraft/tags/enchantment/prevents_decorated_pot_shattering.json`
- `data/minecraft/tags/enchantment/prevents_ice_melting.json`
- `data/minecraft/tags/enchantment/prevents_infested_spawns.json`
- `data/minecraft/tags/enchantment/smelts_loot.json`
- `data/minecraft/tags/enchantment/tooltip_order.json`
- `data/minecraft/tags/enchantment/tradeable.json`
- `data/minecraft/tags/enchantment/treasure.json`

This establishes equal digests for these 22 listed tag payloads only. It does not independently parse their contents or interpret membership/value semantics.

## Explicit residual scope

Neither manifest lists any `data/minecraft/enchantment/*.json`, `data/minecraft/attribute/*.json`, or `data/minecraft/item/*.json` modifier-definition path. The exact listed inventory therefore cannot establish the existence, completeness, equality, or differences of underlying enchantment/attribute/item modifier definitions or equipment slots. Absence from these manifests is not proof that no such definitions exist in the packaged client or elsewhere. The packaged modifier-definition dependency remains open. No payloads were downloaded or generated.

## Verdict

**COMPLETED** — independent identity and bounded tag-manifest audit complete. Exact whole-file hashes are recorded above. Residual scope is limited to the unlisted modifier definitions/slots and the lack of payload bytes/content interpretation stated above.
