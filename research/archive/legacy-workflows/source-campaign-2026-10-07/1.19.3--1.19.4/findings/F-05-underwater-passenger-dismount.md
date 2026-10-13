# F-05: underwater passenger dismount gate depends on entity-type tag

- Older version A: 1.19.3
- Newer version B: 1.19.4
- Mechanic / coverage slice IDs: INV-TICK / I-TICK-UNDERWATER-DISMOUNT; INV-STATE; INV-EXTERNAL
- Classification: changed behavior
- Confidence: source-confirmed under the stated passenger and effective-tag preconditions
- Applicability: player passenger state and dismount position
- First changed release: unknown within (1.19.3, 1.19.4]
- Runtime validation: not performed

## Paired evidence

All source paths are repository-relative; cited Java hashes match the verified exact-version Mojmap source manifests. The built-in tag JSON is from the exact B client artifact; its artifact manifest and jar hashes are recorded in the pair run report.

- A `LivingEntity#baseTick()V`, lines 325–379, source SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`, runs the underwater passenger check on the server and calls `stopRiding()` when `!vehicle.rideableUnderWater()`. The inherited `LivingEntity#rideableUnderWater()Z`, lines 555–557, returns false. A Cow has no override (`Cow.java` SHA-256 `cc477894d8aeae38acea9ac52ffb03ecd1e188aa07dfb03e15eef09b48cf0f4a`).
- B `LivingEntity#baseTick()V`, lines 320–374, source SHA-256 `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`, uses the positive `vehicle.dismountsUnderwater()` predicate. `Entity#dismountsUnderwater()Z`, lines 1981–1983, source SHA-256 `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`, tests `EntityTypeTags.DISMOUNTS_UNDERWATER`. The built-in B `data/minecraft/tags/entity_types/dismounts_underwater.json` has SHA-256 `a46939b479591668df6b6c445487f759dd408e4925c6274e149223c7c92ce0f2` and omits Cow. B `RideCommand#mount`, lines 62–86, source SHA-256 `45e7148e117cf020f927542ebee7743f68520758d6f330bfc7af92aa3df89c80`, provides a B-side route that can set a player as passenger of an accepted entity target. A has no vanilla RideCommand source; the precondition is an already assigned server/external passenger state, not a claim about ordinary A player interaction.
- B `TagLoader#load(ResourceManager)`, lines 44–65, SHA-256 `0bf78c838fbbd5e034097a067757a9a25aa4373622de77b0b762b1985bf39e2b`, processes stacked tag resources and clears accumulated entries when a layer declares `replace`. `MultiPackResourceManager#listResourceStacks`, lines 97–106, SHA-256 `5c8722689bcca6179a849af89a312f4ae24938a6bfb850652012e77a500e2231`, supplies those resource stacks. The exact B client jar contains one matching built-in tag resource; this identifies the vanilla default only. Server data layers can extend or replace it. B `ClientPacketListener#handleUpdateTags(ClientboundUpdateTagsPacket)`, lines 1505–1507, source SHA-256 `bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715`, applies synchronized registry tags on the client.

## Source-level difference

For a player already riding an untagged LivingEntity such as Cow while underwater, A's inherited false predicate is negated by the server gate, so A detaches the player. With B's built-in tag, Cow is absent and the positive predicate is false, so that gate leaves the player mounted. The outcome in B depends on the effective server tag set.

Skeleton Horse was checked as a control: A explicitly returns true from its `rideableUnderWater` override, while B's built-in tag omits Skeleton Horse. Both versions therefore leave that passenger mounted under this gate; it is not the changed case.

## Server consequence and local player synchronization

In A, `LivingEntity#dismountVehicle()` selects a vehicle-provided passenger dismount position and dispatches through `ServerPlayer#dismountTo`; the player removes its vehicle and the connection sends the dismount position/rotation. `ServerGamePacketListenerImpl#dismount` teleports with the dismount flag. The client packet handler removes the local vehicle when it receives that flag and applies the supplied coordinates. Source hashes: A `ServerPlayer.java` `7df71c12ff99bec35b7c6b0e68fa24e5a5aa7293c6dd8f3342d433936ad35c7c`, `ServerGamePacketListenerImpl.java` `81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b`, and `ClientPacketListener.java` `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`.

In B, `ServerPlayer#dismountTo(DDD)V`, lines 937–942, removes the vehicle and writes the selected position. `ServerEntity#sendChanges()`, lines 80–89, sends passenger-list changes and, for a removed player passenger, a position teleport. Source hashes: B `ServerPlayer.java` `5eeea9be89db11000daa95cada0a4120c5eda1014aae9e868fba0bee8de05606` and `ServerEntity.java` `a597bbd4b7639ac0ce91dcc6ea53af4ae734d872beea1cc215a9fbfb9ed67347`.

A client-only compatibility hook can respond to the server's resulting passenger/position update, but it cannot change whether the authoritative B server executes `stopRiding()` or which dismount position it selects. Both decisions are made on the server using server state and its effective tag data.

## Reachability and scope

This finding is about direct player passenger state and the resulting player dismount position. It does not claim that ordinary vanilla A interaction mounts a Cow, and it does not include vehicle motion or other non-player physics. No network-arrival ordering relative to a local tick is inferred from source.

## Handoff

Source-only snapshot. No implementation recommendation or runtime validation is included. Release introduction is unknown within the compared interval.