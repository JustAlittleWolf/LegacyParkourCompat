# Independent verdict: 1.15 → 1.15.1 ability flight over Elytra glide

**Verdict: ACCEPT, scoped no-difference.** The clean replacement report is the requested immutable input: SHA-256 `7f086392fc12505575a6cd2f34b8835728ca9c5098f79544e49f2565e7ec689f`. I independently confirm its bounded claim for an ordinary local player who has lawful `mayfly` permission, is airborne, dry, not riding, has active flag 7 and a fly-enabled chest Elytra, and enters the tick with ability flight enabled. No behavior difference is present in this operation from 1.15 to 1.15.1. This does not close full-pair coverage or establish any intermediate patch boundary.

## Independent trace

- `LocalPlayer.aiStep` permits `abilities.flying` when `mayfly` is available (jump-trigger toggle or always-flying mode). The Elytra start-input branch is gated by `!abilities.flying`; thus the overlap is reachable by enabling lawful flight while flag 7 from an existing glide is active. On an airborne tick, its post-super grounded-flight shutdown does not apply.
- `LivingEntity.aiStep` invokes `updateFallFlying()` immediately before virtual `travel(...)`. With the stated client-side preconditions, `updateFallFlying` retains active flag 7: airborne, nonpassenger, fly-enabled chest Elytra. The flag rewrite is server-only. `isFallFlying()` reads flag 7.
- `Player.travel` selects its `abilities.flying && !isPassenger()` branch, saves incoming vertical velocity and current flying speed, applies the ability speed, and calls `super.travel` before any wrapper clear. In a locally controlled, dry player, `LivingEntity.travel` therefore executes the active Elytra steering branch and calls `move(MoverType.SELF, ...)` with glide-adjusted velocity. Its grounded glide clear is also server-only.
- After that movement returns, `Player.travel` restores vertical velocity as saved `i * 0.6`, restores flying speed, writes `fallDistance = 0.0F`, then directly clears shared flag 7 with `setSharedFlag(7, false)`. The flag clear is unconditional inside this ability-flight/nonpassenger branch. The fall-distance reset and flag clear are separate ordered writes.

The compared method/file bodies match byte-for-byte on both releases, including the local input setup, active glide path, wrapper order and flag semantics. The author's conclusion and scope are supported. The observed glide move and post-move state cleanup are shared behavior, not a 1.15.1 change.

## Source and provenance verification

Canonical read-only Mojmap sources are `decompiled_minecraft/1.15/mojmap/` and `decompiled_minecraft/1.15.1/mojmap/`. I checked each ready/provenance/source-manifest digest against the bytes on disk. Both ready records state `ready`, Mojmap, and 3,329 source files; provenance identifies exact versions and run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`.

| Release | provenance SHA-256 | ready SHA-256 | source manifest SHA-256 | client SHA-256 |
|---|---|---|---|---|
| 1.15 | `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` | `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b` |
| 1.15.1 | `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` | `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358` |

For both releases, the source-manifest entries equal my direct SHA-256 checks for all four trace files:

| Source file | SHA-256 (both releases) |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |

No implementation, tests, runtime, Git, product, wiki, or network actions were performed.
