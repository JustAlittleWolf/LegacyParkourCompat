# Native applicability and tag-resource gate — player entity push

Status: COMPLETED (source-level native applicability closed; 26.2 default resource JSON bytes unavailable in this source export)

## Binding

Assigned finding: `development/research/findings/1.21.10--1.21.11/player-entity-push.md`

Finding SHA-256: `948856DC680F13BDA9CE47A18F65182BC4B3AE9D4862A0F55009DBCD36BF0670` (verified unchanged).

## Verified native applicability

At 1.21.10, `LivingEntity.onClimbable()` tests `BlockTags.CLIMBABLE`; `LivingEntity.isPushable()` is `isAlive() && !isSpectator() && !onClimbable()`. At 1.21.11, `onClimbable()` first returns false when the entity is fall-flying and the current state is in `BlockTags.CAN_GLIDE_THROUGH`, then performs the same climbable check. `isPushable()` retains the same predicate. Thus an alive nonspectator fall-flying Player occupying a state in both tags is not pushable under the old predicate and is pushable under the new predicate.

The endpoint default tag providers establish seven overlapping block IDs/states: vine; twisting vines and twisting vines plant; weeping vines and weeping vines plant; cave vines and cave vines plant. Tag membership is by block ID, so every state variant of each listed block is included. In 1.21.10 these seven are in CLIMBABLE and there is no CAN_GLIDE_THROUGH provider definition. In 1.21.11 they remain CLIMBABLE and are included in CAN_GLIDE_THROUGH through its direct entries and CAVE_VINES expansion.

The native argument-side gate input is the Player's `isPushable()` result at the already independently confirmed `Entity.push(Entity)` argument branch (`!arg.isVehicle() && arg.isPushable()`, followed by the argument's direct vector push). Historical behavior can be expressed locally by using the old Player eligibility predicate only when the argument is the Player. This does not require replaying other entities' movement. This note records the applicability/input shape only; it does not re-audit or reaccept the direct velocity writer.

## Exact source references and identities

Endpoint sources are canonical local Mojmap output. Both endpoint provenance records identify the same `decompileMinecraft` batch for 1.21.8, 1.21.10, and 1.21.11 with `--mappings=mojmap`; tool metadata records Java 25.0.3, Gradle 9.7.1, Vineflower 1.12.0.

1. `1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java:1662-1681` — `onClimbable()`, CLIMBABLE-only path. File SHA-256 `B8B49D60769203F7BD5AFE4A1BFFCDCDBEC30BE28960324CDC43A2DF85A6EB66`.
2. `1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java:3077-3079` — `isPushable()` predicate; same file hash as item 1.
3. `1.21.10/mojmap/net/minecraft/data/tags/VanillaBlockTagsProvider.java:289-300,396` — default CLIMBABLE block entries and CAVE_VINES expansion. SHA-256 `2393020635C9DBBE3CB2FA259870044D232ED215E45ED9ACF048114B0904A980`.
4. `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java:1669-1685` — fall-flying/CAN_GLIDE_THROUGH early false, followed by the CLIMBABLE and trapdoor branches. SHA-256 `19ED2D565858C401C69A06750B054A633A20EA864CAB3A753B5C767F0BDD60E8`.
5. `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java:3203-3205` — unchanged `isPushable()` predicate; same file hash as item 4.
6. `1.21.11/mojmap/net/minecraft/data/tags/VanillaBlockTagsProvider.java:289-300,320-325,401` — CLIMBABLE, CAN_GLIDE_THROUGH and CAVE_VINES default entries. SHA-256 `8C17F2A6C6654721019E575377523EDB0AC68C71B8325188A4E6412962C83474`.
7. `1.21.10/mojmap/net/minecraft/world/entity/Entity.java:1720-1749` — pair's direct push entry/argument guard reference; writer was independently confirmed by the other worker, not re-audited here.
8. `1.21.11/mojmap/net/minecraft/world/entity/Entity.java:1738-1767` — pair's direct push entry/argument guard reference; writer was independently confirmed by the other worker, not re-audited here.
9. `26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java:1722-1738,3376-3378` — modern default fall-flying tag guard and pushability predicate. SHA-256 `7FFD9C70966EDC50C9CB4D9A8FE17A518E2678FF44C8026E763D0B94AC0AE51A`.
10. `26.2/unobfuscated/net/minecraft/data/tags/VanillaBlockTagsProvider.java:252-258,293-299,387` — modern default CLIMBABLE and CAN_GLIDE_THROUGH entries and CAVE_VINES expansion. SHA-256 `3F6060116130EA23FF1E16B68B5BAAB1EE7A1EE8D00F7F69D11F7BB38B389E4B`.

The 26.2 `unobfuscated.ready.json` identifies version 26.2, mapping `unobfuscated`, 7,055 files, and source-manifest SHA-256 `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`; the manifest file itself has the same SHA-256. The 26.2 tag provider keeps the same seven-member overlap, with `GLOW_BERRY_CROP` replacing the former CAVE_VINES block identifier alongside CAVE_VINES_PLANT.

## Exact scope caveat

The 26.2 canonical export contains `VanillaBlockTagsProvider.java` as Java source evidence for vanilla default tag generation. It does not contain generated `data/minecraft/tags/block/climbable.json` or `can_glide_through.json`; the exported `data/minecraft/tags/block` tree has no files. Therefore exact 26.2 generated resource bytes/content and data-pack overrides are not verified by this export. The provider establishes the vanilla defaults only. No product source, implementation/history, wiki, backlog, tests, build, launch, or source generation was inspected or performed for this gate.
