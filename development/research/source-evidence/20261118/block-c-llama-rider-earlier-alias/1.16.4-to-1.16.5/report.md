# 1.16.4 → 1.16.5: ordinary adult Llama rider Y placement

## Verdict

No source difference in the bounded operation. An ordinary adult player riding either a standard `Llama` or `TraderLlama` is placed with the same Y expression and call path in exact 1.16.4 and 1.16.5 Mojmap sources. No release-specific variant is required by this pair. This closes only the assigned passenger-Y operation, not either pair's full movement inventory.

## Exact behavior and operation bounds

- `net/minecraft/world/entity/animal/horse/Llama.java`, `Llama#positionRider(Entity)` lines 152–159 and `Llama#getPassengersRidingOffset()` lines 161–164: `positionRider` first gates on `this.hasPassenger(entity)`, then calls `entity.setPos(...)`. The Y argument is evaluated as `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()`. The Llama offset is `this.getBbHeight() * 0.67`: `getBbHeight()` returns a float, while `0.67` is a double literal, so multiplication yields a double. The player offset is added afterward as a double. No intermediate cast or rounding is introduced by these expressions.
- `net/minecraft/world/entity/player/Player.java`, `Player#getMyRidingOffset()` lines 988–991: returns double literal `-0.35`. Thus the exact Y is `llamaY + (llamaBoundingHeight * 0.67) + (-0.35)`, left-associative double addition. The Y path does not depend on body yaw; Llama's float trigonometry in `positionRider` affects only X/Z.
- `net/minecraft/world/entity/animal/horse/TraderLlama.java`, class declaration and constructor lines 22–27: `TraderLlama extends Llama`; it has no `positionRider` or `getPassengersRidingOffset` override. Both variants therefore use the inherited Llama implementation.
- Client effective call path: `net/minecraft/client/multiplayer/ClientLevel.java`, `ClientLevel#tickPassenger(Entity, Entity)` lines 204–217 ticks passenger players with `rideTick()` when `inChunk`; `net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#rideTick()` lines 836–845 delegates to `super.rideTick()`; `net/minecraft/world/entity/player/Player.java`, `Player#rideTick()` lines 451–465 delegates to `super.rideTick()` unless its stop-riding branch fires; `net/minecraft/world/entity/Entity.java`, `Entity#rideTick()` lines 1577–1583 calls `this.getVehicle().positionRider(this)` if still a passenger. Dynamic dispatch reaches Llama's override. Each compared file and operation body is identical by SHA-256.

The adjacent-version comparison does not establish where this behavior was introduced. It only shows no 1.16.4→1.16.5 boundary for this operation.

## Exact-source readiness and provenance

All paths below are relative to the project root `D:/Javastuff/LegacyParkourCompat`.

- **1.16.4, Mojmap:** exact sources exist under `decompiled_minecraft/1.16.4/mojmap/`. The hidden `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json` records the exact 1.16.4 client jar input SHA-256 `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`, client mapping input SHA-256 `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`, and 1.16.4 metadata input SHA-256 `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`. It records source hashes for all five files listed below. This version directory has no separate `mojmap.ready.json`, source manifest, or version-root provenance sidecar; the exact hidden source provenance is present, but a separate readiness-marker claim is unavailable. No version alias was used.
- **1.16.5, Mojmap:** `decompiled_minecraft/1.16.5/mojmap.ready.json` identifies exact version `1.16.5`, status `ready`, 3,523 source files, and source manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; the file's current SHA-256 matches. Its client SHA-256 is `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`. The ready record's artifact manifest hash is `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`, and its diagnostics hash is `676ea98fca6e2b73e0db8214057397af8112b0b9d37b72ad7d9b1025bd77ec50`; current files match these values. `mojmap.provenance.json` records exact version and `mappingOption: mojmap`, with Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0, ASM 9.10.1, Mapping IO 0.9.1, Gson 2.14.0, and Tiny Remapper 0.14.1. It points to the exact 1.16.5 prepared output. The current source files match their entries in `mojmap.sources.sha256`.
- **Tool-hash limit:** the 1.16.4 hidden provenance records tool-jar SHA-256 values: Vineflower 1.12.0 `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d`; Tiny Remapper 0.14.1 `c4d3b3e27089a5b96d21eb5184a75898c6e5062ebca4fee5f92db34c389b8952`; Mapping IO 0.9.1 `aedc94b25a2fe7c09c898144ce33a1ef396680e984b07525d34922d6ee3f4`; ASM 9.10.1 `ed825d10ab1399c8c0cb669e688cf0c8c82629b4c8399b58352b68e92ca10fcb`. The 1.16.5 sidecar records tool versions, not tool-jar hashes; its exact tool hashes are therefore not independently asserted here.

## Compared source hashes

Each listed file has the same SHA-256 in exact 1.16.4 and 1.16.5 Mojmap trees, and the value agrees with the available source provenance/manifest entry:

| Relative source file | SHA-256 |
| --- | --- |
| `net/minecraft/world/entity/animal/horse/Llama.java` | `18bf21b8d7559e4c9a3195126dc0b30b691e8e869fa1c214956a6395534e5f2d` |
| `net/minecraft/world/entity/animal/horse/TraderLlama.java` | `2d5d0d9bbb694ec542d6d167f1e98c95a0c1991b66b6ef69c7881b326c54c8f3` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/client/multiplayer/ClientLevel.java` | `96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93` |
