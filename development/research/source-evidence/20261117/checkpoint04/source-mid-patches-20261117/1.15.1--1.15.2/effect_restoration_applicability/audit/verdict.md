# Independent audit: effect restoration applicability, 1.15.1 → 1.15.2

Verdict: **ACCEPT** for the bounded static player-state and movement-attribute applicability claim. Bound report SHA-256: `EE06CC078BCD7901B9F5B12740F94A401E226A87C4933A836F623C893052FC39`.

## Verified inputs and evidence

- The report’s exact SHA-256 matches the submitted identity. Endpoint root-ready records identify 1.15.1 and 1.15.2 in Mojmap. Source-manifest hashes are `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` and `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; provenance hashes are `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` and `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`. Ready records bind exact version IDs, namespace, and source manifests.
- All 11 listed source files on each endpoint were checked against the exact manifest entry and disk SHA-256; all 22 bindings match.
- The unchanged exact-pair source report r2 (`322E867BEA50AF506E4438F4809D3C893B5E2897B656F02C0855D41A43274D2B`) and its citation-only audit acceptance (`710103010D2473E0F9C9D3ECBD0051222F6CAD00D1382A90E91AC5B74104F0E3`) were reused for the underlying hidden-effect source difference. This audit addresses the additional applicability packet only.

## Applicability claim

`EffectCommands.register` exposes a permission-level-2 `effect give` command with target, effect, integer seconds, and amplifier inputs. Its give path converts non-instant durations to seconds × 20 ticks and calls `LivingEntity.addEffect`; therefore a command-enabled vanilla map can establish the stated player effect sequence. No survival-acquisition or generated-world occurrence is claimed.

For consecutive applications with no intervening effect tick, Speed I for 20 seconds creates a 400-tick amplifier-0 instance, then Speed II for 5 seconds supplies a 100-tick amplifier-1 instance. In 1.15.1, `MobEffectInstance.update` replaces the active effect and keeps no lower-effect backup. At the 100th effect tick the active instance reaches zero, and `LivingEntity.tickEffects` removes it and calls `onEffectRemoved`. In 1.15.2, the higher-amplifier/shorter-duration update keeps Speed I as `hiddenEffect`; hidden and active durations decrement together. At the same 100-tick boundary, Speed I is restored with 300 ticks remaining and the callback invokes `onEffectUpdated(instance, true)`.

`MobEffects.MOVEMENT_SPEED` declares a +0.2 `MULTIPLY_TOTAL` modifier, and `MobEffect.getAttributeModifierValue` scales it by amplifier + 1. Thus Speed II installs +0.4 and restored Speed I installs +0.2. On the older endpoint the active modifier is removed when Speed II expires; on the newer endpoint it is replaced by the lower-amplifier modifier. The claim is about the movement-speed attribute input, not a quantified position/trajectory.

The server-side effect callback applies/removes the modifier. `SharedMonsterAttributes.MOVEMENT_SPEED` is syncable; `ServerEntity.sendDirtyEntityData` sends dirty attributes through `broadcastAndSend`, including to the affected `ServerPlayer`. `ClientPacketListener.handleUpdateAttributes` installs the synchronized values. `Player.getSpeed` reads the movement-speed attribute, and the local player’s input/travel path consumes that value. The reported command/effect/attribute route is supported by the cited exact source.

## Limits

Acceptance is limited to this source-reachable, command-enabled state and the resulting movement-speed modifier change. The “300 ticks remaining” arithmetic assumes the two commands are applied consecutively without an intervening server effect tick. Server effect sync is source-level evidence; no runtime packet timing, trajectory, displacement, generated-world occurrence, broader pair closure, or pair freeze is established. Existing pair status remains PARTIAL.
