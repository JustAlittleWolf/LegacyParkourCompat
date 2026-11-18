# Independent source audit: ability-flight glide flag clear

## Verdict

**Accepted as a bounded no-difference source result for the assigned operation.** The paired 1.11 and 1.11.1 Feather sources support the report's central ordering claim: for a locally controlled, non-riding player with `abilities.flying` true and flag 7 already live, the inherited glide branch can calculate glide motion and call `move(MoverType.SELF, ...)` inside `super.moveRelative(...)`; the player override clears flag 7 only after that call returns. The compared player override and glide-motion segments are identical across this pair. This does not establish full-pair coverage or runtime parity.

## Independent trace and gates

- `LocalClientPlayerEntity.isLocallyControlled()` returns `true` in both versions (`LocalClientPlayerEntity.java:467–470`), satisfying the outer `LivingEntity.moveRelative` authority check for the ordinary local player (`LivingEntity.java:1386–1388` in 1.11; `1388–1390` in 1.11.1).
- `PlayerEntity.moveRelative` enters its ability-flight branch only under `abilities.flying && !isRiding()`. It saves the vertical velocity and air speed, adjusts air speed, calls `super.moveRelative`, restores/scales those values, then writes `setFlag(7, false)` (`PlayerEntity.java:1385–1403` in 1.11; `1387–1405` in 1.11.1). The selected method text is identical after accounting for its two-line offset.
- The superclass glide gate is `isFallFlying()`, which returns `getFlag(7)` (`LivingEntity.java:2143–2144` in 1.11; `2145–2146` in 1.11.1). Its outer movement-authority gate is true for the local client player. The in-water/in-lava checks explicitly allow a `PlayerEntity` whose `abilities.flying` is true. The glide calculations then multiply velocity components and call `move(MoverType.SELF, ...)` (`LivingEntity.java:1390–1425` in 1.11; `1392–1427` in 1.11.1), before returning to the player override's flag clear.
- The superclass has a separate flag-7 clear under `onGround && !world.isClient` (`LivingEntity.java:1436–1438` in 1.11; `1438–1440` in 1.11.1). That server-only gate does not clear the flag on the local client path. The later player override clear has no `isFallFlying()` or client-side exclusion in its ability-flight branch.
- In 1.11.1, `Entity.move` adds piston delta handling only under `moverType == MoverType.PISTON` (`Entity.java:459–500`). The glide call passes `MoverType.SELF`; this added gate is not entered and does not alter the traced glide move.
- The local input code requests Elytra start only with `!isFallFlying()` and `!abilities.flying` (`LocalClientPlayerEntity.java:742–746` in both versions). That supports the stated limit: this audit does not claim ordinary input creates the conjunction in the same tick. It audits the source behavior when the stated ability and live-flag precondition already holds.

The result concerns the direct client player movement response and flag write order only. It makes no claim about packet timing, latency, NBT or start-flying behavior; `fallDistance` reset; artificial state injection; or runtime/A-B evidence.

## Input and exact source identity

Frozen input report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-early-111-1111-ability-flying-glide-flag-writer/report.md`, SHA-256 `bb7e71a24fbc90446a470779f1ca510dd403e1815b1aa569ef492340c87453fb`.

Read-only local evidence is under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`. Each exact version's ready marker identifies `ornithe-feather`, Feather Gen2 build 2, Vineflower, version ID and matching provenance/source-manifest digests. The local provenance files identify distinct exact client and mapping artifacts and successful pinned decompile runs. Local provenance SHA-256: 1.11 `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40`; 1.11.1 `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055`. Local source-manifest SHA-256: 1.11 `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`; 1.11.1 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`. Both source manifests match the ready markers and provenance records.

The exact artifact identity in those provenance records agrees with the frozen report: 1.11 client `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634`, merged Feather mapping JAR `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb100f8e2926eb95dec44`, Tiny `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0`; 1.11.1 client `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6`, merged Feather mapping JAR `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928`, Tiny `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`.

Actual local source-file hashes match their source-manifest entries and the frozen report:

| Relative source | 1.11 SHA-256 | 1.11.1 SHA-256 |
|---|---|---|
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` | `8b37fd286b2dac1b343bd708b80284b43bb31c61bafc63bdcf6620ba3a59065c` |
| `net/minecraft/entity/living/LivingEntity.java` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| `net/minecraft/entity/Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |

## Coverage boundary

Audited the paired player override, inherited glide movement and relevant authority/live-flag/local-input gates, plus the 1.11.1 piston-only `Entity.move` branch cited in the report. This closes only the assigned ability-flight/live-glide flag-order slice. It is not a full tick or full-pair inventory.
