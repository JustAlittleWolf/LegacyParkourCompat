# Llama local-player Y placement: 1.15 → 1.15.1

## Verdict

No difference in the bounded direct local-player Y placement operation. The exact `Llama.positionRider` body, the Llama ride-height formula, player riding offset, client passenger dispatch and the relevant source identities are byte-identical in the two adjacent releases. This is an operation slice, not full-pair coverage or runtime-parity evidence.

For an ordinary adult/default Llama or TraderLlama, the written Y is, in both releases:

```text
llamaY + (getBbHeight() * 0.67) + (-0.35)
```

The default registered height is `1.87F`. `getBbHeight()` returns a `float`; `0.67` is an unsuffixed double literal, so the product is evaluated as double after float promotion. Both additions in the source are left-associative double additions. There is no new cast or rounding point between releases. The terms and their order are unchanged.

## Exact source evidence

All paths below are relative to each local Mojmap source root, `decompiled_minecraft/<version>/mojmap/`. Manifest paths use forward-slash relative names prefixed with `*`; the SHA-256 values below match both the manifest entry and the local source file bytes.

| Source | 1.15 range | 1.15.1 range | SHA-256 in both source manifests |
|---|---:|---:|---|
| `net/minecraft/world/entity/animal/horse/Llama.java` | 149–160 | 149–160 | `bbe98226c5afaddce8ca7458c721fbefff592cc75e02bc261d6b440ca6877f19` |
| `net/minecraft/world/entity/Entity.java` | 1544–1571, 2662–2664 | 1544–1571, 2662–2664 | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/player/Player.java` | 459–475, 986–988 | 459–475, 986–988 | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `net/minecraft/client/multiplayer/ClientLevel.java` | 164–204 | 164–204 | `4ed37f68a8c25ab5a780c7d646e2f1f4f375e39e4591e0a3115c8d637e500835` |
| `net/minecraft/client/player/LocalPlayer.java` | 825–826 | 825–826 | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/world/entity/EntityType.java` | 208, 285–287 | 208, 285–287 | `9a3ac5b01ebf9cb783e5b7073ae227ee2f58dd96437936f6aa21134d72a8d818` |
| `net/minecraft/world/entity/animal/horse/AbstractChestedHorse.java` | 56–58, 139–195 | 56–58, 139–195 | `0d875b09b0218c120abdad559dc87a56255f6e1c072456560e669bcaf9f93d47` |
| `net/minecraft/world/entity/animal/horse/AbstractHorse.java` | 476–484, 899–917 | 476–484, 899–917 | `a967d0834d3c28080725a8b639d2c8c182ee6a7209a361d462fb0225a2836e6d` |
| `net/minecraft/world/entity/animal/horse/TraderLlama.java` | 22, 61–66 | 22, 61–66 | `495d65da1c1903ba769793ee4dbccd6248ef96137c1709af6daa00c563c03dd4` |

In both `Llama.java` files, `positionRider(Entity)` first checks `this.hasPassenger(entity)` (line 150), then directly calls `entity.setPos(...)` (line 154). Its Y argument is exactly `this.getY() + this.getRideHeight() + entity.getRidingHeight()`. The same override returns `this.getBbHeight() * 0.67` (line 160). It does not call `super.positionRider`, so AbstractHorse's separate standing-animation reposition at `AbstractHorse.java:907–916` is not reached through this Llama override. The base `Entity.positionRider(Entity, MoveCallback)` path at `Entity.java:1552–1559` also does not supply Llama's actual direct writer: `Entity.rideTick()` dynamically calls `vehicle.positionRider(this)` at lines 1544–1549, dispatching to Llama's override.

The default `LLAMA` and `TRADER_LLAMA` registrations both use `.sized(0.9F, 1.87F)` (`EntityType.java:208`, `285–287`). Llama overrides inherited `AbstractChestedHorse.getRideHeight()` itself, so its formula is not the ancestor's `super.getRideHeight() - 0.25` expression. Player's `getRidingHeight()` returns `-0.35` (`Player.java:986–988`). `TraderLlama extends Llama` and inherits the same placement override; its `doPlayerRide` guard only prevents initiating a ride while its leash holder is a Wandering Trader (`TraderLlama.java:61–66`).

On the client, `ClientLevel.tickPassenger` stops stale passenger links when removed or when `getVehicle() != entity` (lines 185–187); otherwise a `Player` is admitted by the player-or-ticking-chunk condition (188), and must still be `inChunk` before `rideTick()` executes (192–195). `LocalPlayer.rideTick()` delegates to `super.rideTick()` (825–826), and Player's server-only stop-riding condition is false on the logical client before it delegates to `Entity.rideTick()` (Player.java:459–470). If it remains a passenger after its tick, Entity invokes the vehicle placement method. `Llama.positionRider` then applies its own `hasPassenger` guard before the direct `setPos` write. The vehicle reaches this passenger loop through `ClientLevel.tickNonPassenger`: non-players need a ticking chunk to be processed (164–165), and passenger traversal is guarded by `entity.inChunk` (176–180). These client group guards are identical in the adjacent releases.

An adult non-vehicle Llama can reach `AbstractChestedHorse.mobInteract`'s `doPlayerRide` fallthrough (190–195); `AbstractHorse.doPlayerRide` starts riding on the server only (476–484), after which the client passenger relation exercises the client tick path. For TraderLlama, the same witness requires it not be attached to a Wandering Trader. This establishes a reachable local-player path; it does not imply that the local client initiates server-authoritative mounting.

## Source identity and provenance

Both local trees have `status: ready`, `mapping: mojmap`, and exact source directory IDs `1.15` and `1.15.1`. The first release's official version metadata ID is `1.15` (not a synthesized `1.15.0`); the second is `1.15.1`. Both provenance files identify one exact preparation batch, `prep-1.15-1.15.1-mojmap-2026-10-09-r1`.

| Identity | 1.15 | 1.15.1 |
|---|---|---|
| `mojmap.sources.sha256` digest (also ready marker `sourceManifestSha256`) | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` |
| `mojmap.ready.json` client SHA-256 | `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b` | `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358` |
| exact metadata ID and metadata SHA-256 | `1.15`; `d154eaf07885a7fd6dfc6014bb9caf08fecc19ccd9a0862b31b1c7d1639e7785` | `1.15.1`; `dda0859bca7447ff5f071643006b35a51f54e2fbef851a6665b424cf5743c598` |
| official client mappings SHA-256 | `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a` | `f46186d0ed9d17e073e16ae2c98675dbbc70b4a29495700837aa5de9c1b42c34` |
| `mojmap.provenance.json` SHA-256 | `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` | `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` |

The ready/provenance records' historical output and source-root paths point into `build/movement-campaign-2026-10-07/ready/<version>/mojmap`, while the read-only published copies examined here are at `decompiled_minecraft/<version>/mojmap`. I did not infer source identity from those path strings: I verified that each local manifest's bytes match the digest in its ready marker, then checked every listed relevant source-file hash against both the manifest and the local file. The normalized manifest keys are relative slash-separated paths such as `net/minecraft/world/entity/animal/horse/Llama.java`; the current local filesystem paths append those keys beneath the `mojmap` root. I did not regenerate sources or inspect Git history.

## Scope fence

This comparison covers only the direct Y placement of a local player riding an ordinary adult/default Llama or eligible TraderLlama and the relevant client-side dispatch/guards. It excludes all other actors, passengers and carriers, llama/vehicle physics, and unrelated horse-family placement. It does not establish complete tick-graph or pair coverage, an exact behavior boundary before/after this pair, or runtime parity.
