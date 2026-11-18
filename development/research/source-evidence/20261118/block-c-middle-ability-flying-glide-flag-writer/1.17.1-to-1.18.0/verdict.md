# Independent source-audit verdict — 1.17.1 to 1.18

## Frozen evidence identity

Host: Windows PowerShell. Canonical checkout: `D:\Javastuff\LegacyParkourCompat` (source read-only). Mapping family: Mojmap on both sides. The 1.18 source tree represents exact release `1.18` (Minecraft 1.18.0), not 1.18.2.

- 1.17.1 provenance SHA-256: `DE197F77473955635E8C5730E113F227AF2B8AF9306B74E8A54A765EAFC5A333`; ready manifest SHA-256: `C7180DCE94D7B57B53A964445B0C788C71F23BA3BA40EEA593C11CC939185E9B`; source-manifest SHA-256: `93270D229ACFB751BF56DAF1E7BE26CE3DCFF26B29E94AA157DE621405A3463B`. Ready status identifies version 1.17.1, Mojmap, 4,142 files, client SHA-256 `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d`.
- 1.18 provenance SHA-256: `E642F075B25D1B6E74BAD05EDACD740421C70F5D8E0C2A38767523CF864124C5`; ready manifest SHA-256: `4537EDF0E88C88631215BEB1C14F1D67F5A5D6D021C982C0AF549B7B27856E5F`; source-manifest SHA-256: `A43C61223DDEDBDD8EDE4D2DAF6A750A58CBBA324B30C025429EE1030CFC0D78`. Ready status identifies version 1.18, Mojmap, 4,203 files, client SHA-256 `381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa`.

The source manifests' entries were checked against the exact local files relied on below. Relevant local source SHA-256 values:

| Source | 1.17.1 | 1.18 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812` | `E38D0BBB6E5B9DE2A698609B8491D0007406B3BECD42BA9B95289F533788C996` |
| `net/minecraft/world/entity/player/Player.java` | `724BB298499DABE489DFFD5CE7EC81C9773619A8D2C70044911EAE4C9E8FF481` | `1FB9DFB9F700323D77F4196DBC3618D01A65E56438B9CCE3EE38FA0B258BD7E2` |
| `net/minecraft/world/entity/LivingEntity.java` | `33FD081AADB2B6FDC9EBF487DB6DA5B38C54F4B8676572790EE2203690D15E6F` | `68FC9B15EB31690377AFE92ACFC783C86945FEDEC5739D28FAACD96DA4F9E0E7` |
| `net/minecraft/world/entity/Entity.java` | `AB28E1FBA924771EC048140DFD293EE5A46A7DFE81F71A1A0B1AECC1927232DE` | `69417675AE8E0A5BD88B7372BAEA1E73B1F5217154F39446C8AD5C92731437F1` |

## Bounded trace and verdict

Trace: a local player is dry, airborne, not a passenger, has a usable default chest-slot Elytra and `mayfly`; while already fall-flying, a legitimate local jump-edge flight toggle makes `abilities.flying` true. The toggle is gated by `mayfly` in `LocalPlayer.aiStep` (1.17.1 lines 728–754; 1.18 lines 726–752). The same method's Elytra start gate is separate and does not disqualify an already-gliding player. It then delegates to `LivingEntity.aiStep` / `travel` (1.17.1 `LivingEntity.java` line 2546; 1.18 line 2548).

With abilities flying and not a passenger, `Player.travel` takes its ability-flight branch in both versions (1.17.1 lines 1409–1436; 1.18 lines 1409–1436). It calls `super.travel` before restoring vertical velocity and flying speed. In `LivingEntity.travel`, the extant flag-7 glide state selects the fall-flying branch; that branch applies glide velocity and executes `move(MoverType.SELF, ...)` (1.17.1 lines 2068–2097; 1.18 lines 2070–2099). For this local-client path the `onGround && !level.isClientSide` clear is gated off. Control returns to `Player.travel`, which clears shared flag 7 directly after the move in both versions (line 1433). Therefore the gliding travel calculation and actual move occur for this tick, then flag 7 is cleared. This post-move direct clear and its reachability are unchanged across the compared releases.

Fall-distance handling is a separate source refactor in this operation: 1.17.1 `Player.travel` writes `fallDistance = 0.0F` at line 1432; 1.18 calls `resetFallDistance()` at the same position. The 1.18 `Entity.resetFallDistance` body at line 2101 is exactly `this.fallDistance = 0.0F;`. The concrete local player does not override this helper. Thus the helper has no changed player-visible effect in this trace and does not move or reorder the flag clear.

**Verdict: reject a claimed 1.17.1→1.18 player-movement change for this operation.** Ability-flight toggle eligibility, actual glide movement before ability-flight cleanup, post-move direct flag-7 clear, and fall-distance result/order are equivalent. Only the spelling of the fall-distance write was extracted into a helper. This audit makes no claim about unrelated operations, other input sequences, other releases, server physics, or runtime parity.
