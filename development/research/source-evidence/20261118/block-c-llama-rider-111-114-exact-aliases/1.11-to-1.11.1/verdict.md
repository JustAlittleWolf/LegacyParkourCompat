# Independent source audit: 1.11 → 1.11.1 Llama rider position

**Verdict: accepted for the bounded claim.** The frozen report's no-change conclusion is supported: the exact `LlamaEntity.java` bytes match, and both releases contain the same direct position expression and reachable player callback/guards. This establishes no 1.11.1 boundary for this operation; it does not establish when the operation first appeared.

## Bound immutable claim and evidence

- Audited input: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-llama-111-1111-position-intermediate/report.md`
- Frozen report SHA-256: `9442871bc0e3081c8bd1f0b142c68fc09560b6b09d1d785299e8e82944964698`.
- Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` (read-only).
- 1.11 marker: `1.11/ornithe-feather.ready.json`, SHA-256 `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45`; status `ready`, exact `versionId=1.11`, mapping `ornithe-feather`.
- 1.11 provenance: `1.11/ornithe-feather.provenance.json`, SHA-256 `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40`; `versionId`, requested/resolved exact version are `1.11`, namespace `ornithe-feather`, mapping option `feather`.
- 1.11 source manifest: `1.11/ornithe-feather.sources.sha256`, SHA-256 `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`.
- 1.11.1 marker: `1.11.1/ornithe-feather.ready.json`, SHA-256 `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf`; status `ready`, exact `versionId=1.11.1`, mapping `ornithe-feather`.
- 1.11.1 provenance: `1.11.1/ornithe-feather.provenance.json`, SHA-256 `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055`; `versionId`, requested/resolved exact version are `1.11.1`, namespace `ornithe-feather`, mapping option `feather`.
- 1.11.1 source manifest: `1.11.1/ornithe-feather.sources.sha256`, SHA-256 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`.

The exact canonical source file SHA-256 values below were recomputed and agree with each corresponding source manifest:

| Source | 1.11 | 1.11.1 |
| --- | --- | --- |
| `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java` | `1577cf77a5c1d0e47dde0e13327dc333fcd65aa86c7e9073f49b2c678f0257b1` | `1577cf77a5c1d0e47dde0e13327dc333fcd65aa86c7e9073f49b2c678f0257b1` |
| `net/minecraft/entity/Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` | `8b37fd286b2dac1b343bd708b80284b43bb31c61bafc63bdcf6620ba3a59065c` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |

## Independent trace and math

Both releases' `LlamaEntity.java:60-63` constructor sets the ordinary default dimensions with `setSize(0.9F, 1.87F)`. Neither compared `updateRiderPositon` body adds an age/local-player condition. At `LlamaEntity.java:144-150`, the callback first checks `this.hasPassenger(rider)`, computes yaw sine/cosine from `bodyYaw`, and calls `setPosition` with Y exactly `this.y + this.getMountHeight() + rider.getRideHeight()`.

At `LlamaEntity.java:153-155`, `getMountHeight()` returns `this.height * 0.67`; `height` is a float field and `0.67` is a double literal, so the multiplication produces a double. `PlayerEntity.java:947-950` returns the double literal `-0.35` for player ride height. Thus the default adult player's direct Y write retains the source's left-associated double expression `this.y + (this.height * 0.67) + (-0.35)` (with the constructor's float `1.87F` in `height`); do not fold or recast it. The Llama callback's same X/Z offsets are present but outside this Y-only audit.

Reachability and guard chain are unchanged:

- `LocalClientPlayerEntity.java:797-805` calls `super.rideTick()`; its later boat-only input handling does not change player position. The local subclass's `isLocal()` returns true, while base `PlayerEntity.isLocal()` returns false (`PlayerEntity.java:1184-1185` in 1.11; `1186-1187` in 1.11.1); neither value gates this callback.
- `PlayerEntity.java:378-399` routes into `super.rideTick()`, except when `!world.isClient && isSneaking() && isRiding()`, in which case it stops riding and clears sneaking. This is an authority/sneak guard, not a client-only guard. The ordinary local client player does not satisfy `!world.isClient`.
- `Entity.java:1510-1522` (1.11) / `1569-1581` (1.11.1) captures `entity = getMount()`. If still riding and that mount is removed it calls `stopRiding()`; otherwise it zeroes all three rider velocities, ticks the rider, and calls the captured mount only if `isRiding()` remains true after the tick. Virtual dispatch reaches the Llama override.
- `Entity.java:1689-1691` / `1748-1750` defines `isRiding()` as `getMount() != null`. The Llama override separately checks `hasPassenger(rider)`; `Entity.java:2296-2304` / `2355-2363` confirms that membership check iterates the mount's passenger list and compares the rider. This closes the actual captured-parent/membership guard: if the rider ceased to be this Llama's passenger during its tick, the direct placement is skipped.

The cited Llama body and constructor are byte-identical across the pair; the relevant parent/player callback bodies above also match, and the complete local-player source files are byte-identical. No difference in direct rider Y placement or its reachability was found.

## Bounds

This verdict covers only the default ordinary adult Llama's direct rider position write and the local `PlayerEntity` ride-tick route in exact 1.11 versus 1.11.1 Feather sources. It excludes other mount actors, vehicle physics, other position writers, trajectory/runtime parity, and pair-complete movement coverage. It does not prove the operation's introduction boundary before 1.11.

