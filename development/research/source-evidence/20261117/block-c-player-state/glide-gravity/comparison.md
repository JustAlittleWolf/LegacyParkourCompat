# Fall-flying gravity term and player no-gravity boundary

## Scope and result

Compared the exact Mojmap sources in 1.20.2, 1.20.4, 1.20.5 and 1.20.6 for the fall-flying branch of `LivingEntity.travel`, the `Entity` gravity/no-gravity state, and ordinary player start-flight and persistence paths. The glide branch's gravity input changes between observed 1.20.4 and 1.20.5: earlier it is a travel-local fixed `0.08` (or slow-falling `0.01`); in later sources it is `this.getGravity()` (or `Math.min(getGravity(), 0.01)` for the slow-falling case). The glide equations and subsequent operation order are otherwise the same in these source slices. This change is conditional for no-gravity: the getter resolves to zero if the entity's `DATA_NO_GRAVITY` flag is true. The source inventory has no vanilla ordinary-player call to `setNoGravity`; the normal player construction state is false. A player can still hold a true flag when that state is externally set or supplied in player NBT, which the generic entity save/load path preserves. Thus no-gravity does not arise merely from ordinary vanilla gliding.

The default ordinary player path remains gravity 0.08 in the newer source. Custom non-default gravity attributes are outside this note; the newer generic attribute feature is not assessed here. The evidence establishes an endpoint change between 1.20.4 and 1.20.5, not the exact first release containing it.

## Paired source evidence

All references are under `decompiled_minecraft/<version>/mojmap/`; line numbers below are inclusive.

- 1.20.2, Mojmap: `world/entity/LivingEntity.java:2038-2044` initializes `$$1 = 0.08`, changed to `0.01` for downward slow-falling. The fall-flying branch at `2102-2128` uses `$$1` in `getDeltaMovement().add(0.0, $$1 * (-1.0 + $$19 * 0.75), 0.0)` before the unchanged lift/steering operations and `multiply(0.99F, 0.98F, 0.99F)`. `world/entity/Entity.java:212-214, 251-257, 1091-1096` declares and initializes `DATA_NO_GRAVITY` false and implements `isNoGravity`/`setNoGravity` through entity data.
- 1.20.4, Mojmap: `world/entity/LivingEntity.java:2035-2041, 2099-2125` retains the same fixed travel gravity and fall-flying term. `world/entity/Entity.java:213-257, 1092-1097` retains the false initialization and entity-data accessors.
- 1.20.5, Mojmap: `world/entity/LivingEntity.java:2099-2102, 2104-2110, 2168-2194` supplies `$$1` from `this.getGravity()`; for downward slow-falling it uses `Math.min($$1, 0.01)`. The fall-flying formula and following operations retain their 1.20.4 ordering. `world/entity/Entity.java:214, 258, 1093-1106` initializes the no-gravity data false and returns `0.0` from final `getGravity()` when `isNoGravity()` is true; otherwise it calls `getDefaultGravity()`. `LivingEntity.java:2100-2102` implements that default with the gravity attribute, excluded from analysis here except as the getter's default path.
- 1.20.6, Mojmap: `world/entity/LivingEntity.java:2100-2110, 2168-2194` and `world/entity/Entity.java:214, 258, 1093-1106` match the 1.20.5 relevant logic. The compared source files for `Entity`, `LivingEntity`, `Player`, `ServerPlayer`, `PlayerList` and `LocalPlayer` have identical SHA-256 values in 1.20.5 and 1.20.6.

For a true `DATA_NO_GRAVITY` flag, the concrete glide consequence is that the added vertical term becomes `0.0` in 1.20.5+; in 1.20.2/1.20.4 the glide branch still applies the fixed gravity term despite the flag. For the normal false flag, the default gravity input remains 0.08, so this particular state gate does not change ordinary glide gravity. Trajectory effects beyond that source-level consequence are not simulated.

## Player-state reachability and authority

- `world/entity/Entity.java:200-214, 258, 1093-1098` uses a synchronized `EntityDataAccessor<Boolean>` for `DATA_NO_GRAVITY`, initialized to false by the entity constructor. `setNoGravity` is a public generic setter. It is independent of `DATA_SHARED_FLAGS_ID`'s `FLAG_FALL_FLYING` bit 7 (`Entity.java:200-207`); the two flags do not implicitly set each other.
- `world/entity/player/Player.java:1534-1548` starts glide only when airborne, not already gliding, not in water, not under levitation, and wearing an enabled Elytra; `startFallFlying` sets shared flag bit 7. `client/player/LocalPlayer.java:746-747` sends `START_FALL_FLYING` after its local eligibility check. `server/network/ServerGamePacketListenerImpl.java:1410-1413` rechecks `tryToStartFallFlying()` server-side and clears glide if invalid. This is the vanilla glide authority path; it contains no no-gravity write. `server/level/ServerEntity.java:291-294` sends dirty synchronized entity data to trackers, so a server-set generic no-gravity value is the synchronized state a client reads.
- The 1.20.2/1.20.4/1.20.5/1.20.6 Mojmap source search for `setNoGravity(` found only generic entity/NBT handling and vanilla writers for non-player cases (for example Vex, flying-mob control, armor trim display entities, and bee passengers), not a writer on the ordinary player glide path. This is a bounded source inventory, not a claim about mods or every possible caller.
- Player persistence inherits the generic entity tag path. In 1.20.5, `world/entity/Entity.java:1581-1614` writes `NoGravity` only when true and `Entity.java:1715-1718` reads it with `setNoGravity`. `world/entity/player/Player.java:761-762, 807-810` calls `super` in read and write methods. `server/players/PlayerList.java:318-327` obtains saved player data, and `world/level/storage/PlayerDataStorage.java:74-83` calls `Player.load` on the decoded tag; a saved true flag can therefore persist if supplied/set before saving. This is a reader/persistence route, not evidence that vanilla creates true for an ordinary player.
- The local-player reader `client/player/LocalPlayer.java:520-533` reacts to synchronized shared flags for Elytra sound; it does not create no-gravity. `world/entity/Entity.java:1093-1098` is the player-inherited direct reader/setter for that state.

## Exact source identity

The canonical read-only source roots are `decompiled_minecraft/<version>/mojmap`. Each tree's `mojmap.ready.json` identifies the exact release ID, Mojmap mapping, source-manifest SHA-256 and client SHA-256. The provenance records report Vineflower 1.12.0 and Java 25. The compared ready-marker identities are:

| Release | Source manifest SHA-256 | Client SHA-256 |
|---|---|---|
| 1.20.2 | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda` |
| 1.20.4 | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` |
| 1.20.5 | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` |
| 1.20.6 | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` | `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2` |

`LivingEntity.java` SHA-256 by release: 1.20.2 `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828`; 1.20.4 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; 1.20.5 and 1.20.6 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`. `Entity.java` SHA-256: 1.20.2 `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b`; 1.20.4 `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`; 1.20.5 and 1.20.6 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`.

## Limits

This report covers the named gravity/no-gravity operation and player glide/persistence authority path only. It does not establish full-pair movement coverage, acceptance, runtime parity, or the earliest release of the newer gravity path. It does not evaluate customized gravity attribute values, mod/plugin callers, arbitrary external NBT editing, or server policy outside the vanilla code cited above.