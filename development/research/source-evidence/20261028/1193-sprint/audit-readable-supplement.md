# Readable correction supplement — 1.19.3 → 1.19.4 Mojmap audit

This is an additive correction to the immutable independent audit at `.native-source/20261028/frontier-c/1193-1194-audit/audit.md` (raw SHA-256 `7fc902616f9ba2b5f26f092bd71009019b7aacbc9e0f8dcc77a53358934d39b9`). It is bound to the source report `.native-source/20261028/frontier-c/1193-1194/report.md` (raw SHA-256 `edc5309334db7a7cda06a4734b95ae18e96f7e3e7f50b35c06579c734d2ca61d`). Neither original file was edited.

## Text repairs

The audit contains control-character corruption that obscures otherwise readable claims and anchors. These repairs describe intended text; they do not change either original artifact.

| Malformed audit text | Readable repair |
| --- | --- |
| `passenger, [U+000B]ehicle.canSprint()` | `passenger, vehicle.canSprint()` |
| `writing [U+000C]lyingSpeed = 0.02F` | `writing flyingSpeed = 0.02F` |
| `adding [NUL].006F` | `adding .006F` |
| `normal airborne movement helper consumes [U+000C]lyingSpeed` | `normal airborne movement helper consumes flyingSpeed` |
| Source anchor starts `- 1.19.3 ⏎et/minecraft/client/player/LocalPlayer.java...` and the second path also starts `⏎et/minecraft/...` | `- 1.19.3 net/minecraft/client/player/LocalPlayer.java:652–716; net/minecraft/world/entity/player/Player.java:496–517,1448–1474.` |
| Source anchor starts `- 1.19.4 ⏎et/minecraft/client/player/LocalPlayer.java...`; subsequent `Entity`, `Player`, and `LivingEntity` paths likewise start `⏎et/minecraft/...` | `- 1.19.4 net/minecraft/client/player/LocalPlayer.java:644–697,1022–1039; net/minecraft/world/entity/Entity.java:3163–3165; net/minecraft/world/entity/player/Player.java:499–518,1439–1464,2108–2118; net/minecraft/world/entity/LivingEntity.java:2197–2203.` |

Here `⏎` denotes the unintended line break and missing initial `n` in the raw audit text. The U+000B vertical tab, U+000C form feeds, and NUL shown above are labels for the actual control bytes, not literal replacement characters.

## Independent source recheck

I checked the affected claims against the canonical read-only Mojmap roots at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.19.3\mojmap` and `...\1.19.4\mojmap`. Both parent ready markers identify the exact release and `mojmap`, and their source-manifest digests match the on-disk manifests. Their provenance records identify the same 1.19.2/1.19.3/1.19.4 batch, Vineflower 1.12.0, and Java 25.0.3. Ready-marker SHA-256 values are `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` (1.19.3) and `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0` (1.19.4); source-manifest SHA-256 values are `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` and `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` respectively.

- **Local sprint-start eligibility — Confirmed, scoped to the normal local-player path.** In 1.19.3 `LocalPlayer.aiStep` permits auto sprint on ground, underwater, or as a passenger of a grounded vehicle, with its impulse, food, item-use, blindness, and input gates; key sprint has the water/input gates. In 1.19.4 both routes use `canStartSprinting`, which adds `!isFallFlying()` and, for passengers, `vehicle.canSprint() && vehicle.isControlledByLocalInstance()`. In 1.19.4 base `Entity.canSprint()` returns false while `Player.canSprint()` returns true. These statements are present at the corrected anchors above. This supplement does not extend the finding to new vehicle types.
- **Airborne sprint-speed sampling — Confirmed for ordinary airborne travel.** In 1.19.3 `Player.aiStep` calls `super.aiStep()` before writing `flyingSpeed = 0.02F` and adding `0.006F` when sprinting; `LivingEntity.getFrictionInfluencedSpeed` uses the stored field while airborne. In 1.19.4 that helper calls virtual `getFlyingSpeed`, and `Player.getFlyingSpeed` returns `0.025999999F` when sprinting or `0.02F` otherwise. `LocalPlayer.aiStep` updates the sprint state before its `super.aiStep()` call. The sampled value therefore reflects the current sprint state on that ordinary airborne travel step in 1.19.4. This recheck does not establish complete ridden-travel coverage.
- **Source identity — Confirmed at the ready-marker/provenance/manifest identity level.** This recheck confirms the metadata and digest linkage above; it does not repeat the audit's full per-file tree hashing or validate all inventory rows.

## Verdicts and gates retained

The position-correction/passenger packet-order dependency and the resource/tag dependency remain **open**; this supplement did not inspect the missing client artifacts or independently close those gates. The **full-pair coverage/freeze verdict remains not proven**. The correction does not renew a full inventory audit, revalidate its row counts or row hashes, or establish terminal-slice/dependency coverage. No original open gate is treated as resolved.
