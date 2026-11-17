# Independent equipment attribute review

Status: **COMPLETED** for the bounded equipment movement modifier claim. The source difference and movement witness are accepted with a correction to the draft's provenance status. The stated intermediate provenance gap is closed by the hidden nested manifests described below. Gaps: none for this bounded claim. This does not complete a full-pair movement coverage audit or establish implementation/runtime parity. Static source review only; no code, tests, launches, or source generation performed.

## Immutable input

- Draft: `equipment-attribute-source/comparison.md`
- SHA-256: `27c1e40303178706f075368cbfe60203cbb8f605c82ef09c02b1abeb251c6b8c`

## Verdict

**Accept:** 1.8.9's `ItemStack.getAttributeModifiers()` includes decoded NBT modifiers with both UUID bit halves nonzero without reading `Slot`; 1.9.0's `getAttributeModifiers(EquipmentSlot)` adds a gate accepting a missing `Slot` or a string equal to the queried slot key. The present NBT modifier list replaces default item modifiers, while an absent list delegates to the item's defaults (slot-specific from 1.9 onward). The evidence supports the release cutover 1.8.9 → 1.9.0 and the same operation in each checked 1.9.x release through 1.9.4.

**Movement witness accepted with a scope qualification:** the eligibility difference can change the movement-speed attribute for a valid custom stack NBT modifier equipped in a different slot. Built-in item source under `net/minecraft/item` in both endpoints has no `MOVEMENT_SPEED` or `generic.movementSpeed` reference, so this is not a movement-speed modifier on ordinary built-in default equipment. Slotless custom NBT remains eligible in any slot; matching slot tags remain eligible. The conclusion is about custom/external NBT, not a claim that normal survival-generated items produce this modifier.

**Authority/lifecycle:** `LivingEntity` processes equipment changes under `!world.isClient`; it removes old-stack modifiers and adds new-stack modifiers there. It sends equipment packets from that branch. The server's tracked entity attribute packets carry the resulting attributes, and the client packet handler applies those entries to its local living entity attribute container. Therefore the client travel witness reads the synchronized local attribute; the review does not claim that the equipment-modifier lifecycle itself runs locally or that historical movement runs on the server.

At 1.9.4, `PlayerEntity.getSpeed()` returns `(float)this.getAttribute(EntityAttributes.MOVEMENT_SPEED).get()`. The ordinary grounded `LivingEntity.travel` path computes `t = 0.16277136F / (s * s * s)`, then `u = this.getSpeed() * t`, and passes `u` to `updateVelocity`. This closes a reachable movement consumer without making a predicted trajectory or numeric end-state claim.

## Provenance and exact-source checks

The immutable draft's hash matches the assignment. Endpoint provenance files identify a paired `decompileMinecraft --versions=1.8.9,1.9.4 --mappings=feather` run, Feather build.2 artifacts and their mapping files, Vineflower 1.12.0, with endpoint source manifests. The alleged missing middle-release evidence is present as hidden `decompiled_minecraft/<version>/ornithe-feather/.decompile-provenance.json` files for **all** of 1.9, 1.9.1, 1.9.2, and 1.9.3. Each records the exact Minecraft client input hash, Feather build.2 merged mapping artifact hash and Feather metadata hash, decompiler pipeline/runtime/tool versions, and SHA-256 values for all emitted source files. The on-disk hashes of `ItemStack.java`, `LivingEntity.java`, and `PlayerEntity.java` match their provenance entries for all six checked versions. Thus the draft's statement that 1.9–1.9.3 lack source provenance is incorrect and should be removed in reconciliation.

Exact source locations checked (all under `decompiled_minecraft/<version>/ornithe-feather/`):

- 1.8.9 `net/minecraft/item/ItemStack.java:667–687`; 1.9/1.9.1/1.9.2 `.../ItemStack.java:696–717`; 1.9.3/1.9.4 `.../ItemStack.java:700–721` for decoding, UUID validation, slot filtering and defaults.
- 1.9.4 `.../ItemStack.java:723–740` for NBT serialization of `AttributeName` and optional `Slot`.
- 1.8.9 `net/minecraft/entity/living/LivingEntity.java:368–388, 1274–1288`; 1.9.4 `.../LivingEntity.java:425–446, 1508–1535` for save-time modifier removal/restore and server equipment-change lifecycle.
- 1.8.9 `net/minecraft/entity/living/player/PlayerEntity.java:134, 1298–1300`; 1.9.4 `.../PlayerEntity.java:154–155, 1393–1395`; and 1.9.4 `net/minecraft/entity/living/LivingEntity.java:1356–1374` for base speed, player getter and grounded travel consumption.
- 1.8.9 and 1.9.4 `net/minecraft/server/TrackedEntity.java` attribute tracking/send path, plus `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` `handleEntityAttributes` for client application.

The draft's intermediate source hashes and the conclusion that the relevant `ItemStack.java` body is unchanged within 1.9–1.9.2 and within 1.9.3–1.9.4 match the local exact-source evidence. No finer uninspected patch boundary is needed for the claimed 1.8.9-to-1.9.0 release cutover.