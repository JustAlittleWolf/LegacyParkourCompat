# MCPK 1.17 swimming entry gate: implementation reconciliation

## Disposition

**Implemented for the project’s 1.16.2 profile group.** The 1.16.5 endpoint has an independent player swimming-entry predicate that current vanilla no longer matches. This source comparison does not identify the first changed 1.17 patch, does not freeze the source pair, and does not establish runtime parity.

## Accepted evidence

- Immutable snapshot: `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.17-swimming-snapshot-r2.md`, content SHA-256 `17284668382004ed3101a20c9f810d3b5cdb42721bc3efca8a7d5dfc641b3a32`.
- Clean independent review: `6e045f3d50f9248ab329c5bd698caa935e8a9be3:workflows/wiki-audit-2026-10-07/mcpk-clean-rereview-dispositions-2026-10-08.md`; disposition **ACCEPT**. The reviewer verified the player flying guard and call order.
- The preserved MCPK page fetch was HTTP 403. This implementation is based on the exact vanilla sources; it does not claim fresh verification of the live page wording or revision.

## Source behavior and current implementation

Canonical historical sources are in the read-only `build/movement-campaign-2026-10-07/ready/` trees. In 1.16.5, `Entity.baseTick()` updates fluid state, updates eye-fluid state, then calls `updateSwimming()`. The already-swimming branch retains swimming only for sprinting, in water, and not a passenger; the entry branch requires sprinting, underwater, and not a passenger. `Player.updateSwimming()` forces swimming false while flying and otherwise delegates to `Entity.updateSwimming()`.

In 1.17.1 the `baseTick()` call remains after fluid and eye-fluid updates, and the player flying guard is unchanged. Only the non-swimming entry branch adds `level.getFluidState(blockPosition).is(FluidTags.WATER)`. The already-swimming branch is unchanged. Current 26.2 retains that entry gate in `Entity.updateSwimming()` and the same player flying override.

Before this change, `SwimmingBehavior` only changed `Player.isSwimming()` at return for the 1.12 profile; it did not control `Entity.updateSwimming()`. No existing hook represented the endpoint delta. `ChangeResolver` selects changes at or after the selected profile, so a new `V1_16_2` mechanic covers the project profile containing 1.16.5; `V1_17` and later profiles keep vanilla behavior. The exact first changed release within the endpoints remains unknown.

## Implementation

- New `SwimmingUpdateBehavior` uses mechanic key `player.swimming.update`, separate from the existing `player.swim` state-query hook.
- `EntityMixin` injects at `Entity.updateSwimming()V` HEAD. For a player with a resolved behavior, it writes the returned flag via `Player.setSwimming()` and cancels the native method. Other entities, profiles after `V1_16_2`, and `CURRENT` retain vanilla. The snapshots prove the `1.17.1` endpoint; they do not establish the behavior of `1.17.0` or the first changed release.
- `Player.updateSwimming()` checks `abilities.flying` before its `super.updateSwimming()` call in 1.16.5, 1.17.1, and 26.2. The injection is in `Entity.updateSwimming()`, so it does not bypass the existing flying path; flying players remain swimming=false.
- `change.v1_16_2.SwimmingUpdate` copies the two 1.16.5 branches in order and omits only the later block-position water condition. It returns false for selected profiles before 1.13, where swimming is not a historical movement feature. Its provider is registered in `fabric.mod.json`.

## Exact source identities

- 1.16.5 Mojmap source manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; artifact manifest `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`. `Entity.java` SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; `Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.
- 1.17.1 Mojmap source manifest SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; artifact manifest `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa`. `Entity.java` SHA-256 `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de`; `Player.java` `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481`.
- Current 26.2 is canonical `unobfuscated`: source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; artifact manifest `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`. `Entity.java` SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.

## Validation and limits

No build, tests, client, server, or runtime movement validation were run, per assignment. The exact first changed 1.17 release and runtime trajectory effects remain unverified. The broader source pair remains partial.
