# Independent technical review: modifier-order candidate (revised)

## Scope and verdict

Reviewed sanitized candidate commit `e08b3673db3986f1d36532cd0855dd2b7c85d51e` against neutral baseline `4c5acc9`, with the prior source/arithmetic audit retained and the revision diff checked. This final verdict supersedes the initial review of `1e1b72c5e6df5abf0f9a9bb34a97d7315a1a87e1`.

**Verdict: ACCEPT.** The two requested corrections are present: the mixin gates the override on the local client player receiver, and the redundant `V1_20_5` no-op class is removed. No new issue was found in the revision. Earlier profiles grouped as `V1_20_2` continue to resolve forward to the `.2` change; later profiles with no applicable hook retain native behavior.

## Revision checks

- `PlayerMovementSpeedMixin` returns before hook lookup unless `Minecraft.getInstance().player == player` (`src/client/java/me/wolfii/legacyparkourcompat/mixin/PlayerMovementSpeedMixin.java:16-22`). The exact 26.2 client source declares `Minecraft.player` as nullable `LocalPlayer` and exposes `Minecraft.getInstance()` (`client/Minecraft.java:332, 2529-2531`), so this identity check gates to the actual client-controlled player. `MovementRuntime` additionally gates the lookup to a player in a client-side level. The client mixin cannot replay historical movement on a logical server; an integrated-server Player receiver is not the local client `LocalPlayer` instance.
- `NativeMovementSpeedModifierOrder` no longer exists in the candidate tree. With the export resolver selecting the closest change emulating the selected profile or later, the `.2` change applies to the grouped `V1_20_2` profile (1.20.2–1.20.4). At `.5` and later, the `.2` change is older than the selection, so no hook resolves and the mixin leaves vanilla untouched.

## Retained technical review

- The accepted finding and applicability review establish that 1.20.2–1.20.4 use the same UUID-keyed `HashSet` operation buckets and 1.20.5 switches to UUID-keyed maps. The candidate's `.2` emulation annotation matches the export's grouped `ParkourVersion.V1_20_2`.
- Reconstruction preserves legacy `AttributeInstance#calculateValue` order: additions; each base multiplier adding `base * amount` against the post-addition base; sequential total multipliers; final sanitation and float cast for `Player#getSpeed()`. The 1.20.2, 1.20.4 and 1.20.5 exact Player sources have `getSpeed()F` returning the movement-speed attribute value cast to `float`; the candidate injection target is consistent.
- Hash recovery matches Java HashSet/HashMap behavior: UUID hash, `h ^ (h >>> 16)`, power-of-two buckets, and ascending bucket iteration. It rejects bucket collisions and any pair whose relative order changes at capacities 16 through `1 << 30`, covering retained capacity after growth/removal without inferring historical capacity from current size.
- The 26.2 `AttributeModifierIdFix#ID_MAP` conversion gives otherwise-unmapped valid legacy UUIDs canonical `minecraft:<lowercase-uuid>` IDs, which the candidate accepts only when canonical. Explicit aliases cover the legacy movement-speed modifiers for sprinting, powder snow, speed, and slowness. It deliberately declines the lossy `minecraft:enchantment.soul_speed` alias, for which the fix maps two UUIDs to one ID. Non-`minecraft` UUID-like IDs, unknown semantic IDs, noncanonical identifiers, duplicate reconstructed UUIDs, absent attributes, operation groups over the supported size, and unproven bucket orders fall back to native behavior.
- Source references checked: `.native-source/20261030/1204-1206-pool44/legacy-item-order-review/review.md`; `.native-source/20261031/modifier-alias-boundary50/review.md`; exact sources under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.2,1.20.4,1.20.5}/mojmap` and `26.2/unobfuscated/net/minecraft/util/datafix/fixes/AttributeModifierIdFix.java`.

No candidate or product source was modified by this review. No build, tests, or runtime launch was performed.
