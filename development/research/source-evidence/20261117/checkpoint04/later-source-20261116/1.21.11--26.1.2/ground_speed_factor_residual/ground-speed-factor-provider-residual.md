# Ground speed-factor provider and dependency residual — 1.21.11→26.1.2

## Scope and source identity

This is a narrow source-only completion of the open ground speed-factor/provider inventory from `ground_friction_unobfuscated_revision_r2/report-r2.md` (SHA-256 `1b24ef978d38a2d5a89543f0ee8b208a288d371e8a29377ccc16fedac50b691f`) and its independent bounded acceptance (`ground_friction_unobfuscated_r2_audit/verdict.md`, SHA-256 `d0b5b3c433936bdaae6a6c581c3d2ca9540ea08a6d923905c67d107e941300e1`). It does not reopen that accepted S5.2 comparison.

Endpoint identities are those already bound in the accepted report: `decompiled_minecraft/1.21.11/mojmap/` (ready SHA `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`, provenance SHA `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`, source-manifest digest `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`, Mojmap) and `decompiled_minecraft/26.1.2/unobfuscated/` (ready SHA `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`, provenance SHA `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`, source-manifest digest `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c`, unobfuscated). The accepted prior packet verifies its six cited Java files; hashes below were freshly computed and checked against both endpoint manifests for the newly inspected files and the changed call-gate lines.

## Provider and caller inventory

- A full canonical Java-source search for `getBlockSpeedFactor()` finds the same override chain on both endpoints: `Entity` base implementation, `LivingEntity`, and `Player`. The only additional override in either whole source tree is `AbstractMinecart.getBlockSpeedFactor()` (rail special case), outside this direct player slice. `Player` returns `1.0F` when flying or fall-flying and otherwise delegates through `LivingEntity` to `Entity`; no player-specific block-speed provider override was omitted.
- `Block.getSpeedFactor()` and `Block.getFriction()` are the only declarations of those property getters in the two source trees. No block subclass overrides either getter. `Block` returns fields populated from `BlockBehaviour.Properties`; its defaults and sampled `Blocks` initializer literals are already covered by the accepted S5.2 packet. This search result closes the override inventory only, not a complete catalog of all block property assignments.
- The `Entity.move` consumer is in the post-collision `rest` path after clipped movement, collision flags and horizontal velocity-zeroing. Both versions call `getBlockSpeedFactor()` whenever the entity is not removed, then multiply delta movement by `(factor, 1.0, factor)`. The relevant line anchors are 1.21.11 `Entity.java:748-777` and 26.1.2 `Entity.java:767-796`; control flow and operation order match. The client/server and passenger checks around movement emission do not guard this speed-factor call. Dynamic dispatch therefore reaches the inherited `Player` override described above.
- `LivingEntity.getBlockSpeedFactor()` interpolates the `Entity` result toward `1.0F` using `(float)getAttributeValue(Attributes.MOVEMENT_EFFICIENCY)` on both endpoints. `Attributes.MOVEMENT_EFFICIENCY` is registered with default `0.0`, minimum `0.0`, maximum `1.0`; `LivingEntity` includes it in its attribute set on both sides. The Soul Speed `Enchantments` registration applies the same `+1.0F` movement-efficiency effect under the same movement condition on both endpoints. That source condition reads `BlockTags.SOUL_SPEED_BLOCKS`; it is distinct from the two still-open support-position tags below. No difference was observed in these attribute/provider paths.

## FENCES/WALLS resource dependency

`Entity.getOnPos(float)` uses the same support-position rule at both endpoints: with a supporting block, fence membership prevents the Y adjustment only when the offset is at most `0.5`; wall membership prevents it at all offsets; fence-gate type is a separate direct check. Relevant anchors are 1.21.11 `Entity.java:957-975` and 26.1.2 `Entity.java:986-1006`. `BlockTags.java` declares `FENCES = create("fences")` and `WALLS = create("walls")`; each `create` helper uses the default `minecraft` namespace. For these endpoint resource formats the expected archive names are `data/minecraft/tags/block/fences.json` and `data/minecraft/tags/block/walls.json`.

The exact resource-input packets located under `build/source-resource-inputs-20261117/movement-gates/` and their source-evidence checkpoint contain this pair's fluid and snow-support resources, but no FENCES/WALLS entries; exact pair coverage scratch likewise marks these memberships unexamined. Thus no existing exact resource packet binds either endpoint's packaged FENCES/WALLS payload or its membership. This is a real remaining dependency, not a source-code mismatch. Do not infer membership from the tag constants or `VanillaBlockTagsProvider`; archive-entry presence, payload hashes, and any effective resource replacement remain unverified here. An exact pinned-artifact resource packet for the four endpoint entries is the next actionable gate.

## Exact checked file identities

All values matched the corresponding endpoint source-manifest rows:

| Relative source file | 1.21.11 Mojmap SHA-256 | 26.1.2 unobfuscated SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `62129028c2f7beb6d2f87bcd3345e63a7be8fc3950a7ecdbc6ee21f8fb89f843` | `8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5` |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `c4de10d11104f209de97a002744c277a869a05a86f303b1d68a2a389c8f4c5e9` | `87c4d7799516cf33a0399398f4e7b503dc8ef1ebb8588fa2d5decfc33fa12157` |
| `net/minecraft/tags/BlockTags.java` | `9db30d02bbb896538a35267b2f0e9c957a00ffdbf20bcc0ea025b5a7bbec38e3` | `edb77ca338c6e831d47b41d799b2f2cad432f34ee465be33a4d1e69edf0f8c1f` |

## Bounded verdict and next gate

No source difference was found in the block-speed-factor override chain, the movement-efficiency attribute/effect source, or its `Entity.move` consumption gates. This closes the specific Java provider-override and consumer-gate residual only. FENCES/WALLS packaged membership is unbound and remains an explicit dependency; the pair and all other ordinary movement inventories remain partial. Next: independently verify exact pinned archive entries for `fences.json` and `walls.json` at both endpoints, including provenance and payload hashes, then evaluate whether source membership yields a reachable support-position consequence. No implementation or runtime claim is made.
