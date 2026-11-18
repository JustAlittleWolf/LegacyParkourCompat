# Active Elytra scope disposition

**Signed source-scope disposition:** independent auditor `/root/c_glide_climb_1144_115_alias_gate_audit`, 2026-11-18. This attestation is bound to the immutable source report and verdict hashes below.

- Source report `c-glide-climb-1144-115-alias-gate/report.md` SHA-256: `E25712B211A7635126DF5A705A09B2D5F8DF5901D479CF48DE8D79A07C5D5D7E`
- Audit verdict `c-glide-climb-1144-115-alias-gate-audit/verdict.md` SHA-256: `972F8A5820F3819F29A37C237E696FA76124D8535D7E827B07C7948CA708F732`

## Disposition: bounded already-active path is source-equivalent through first glide move

For an ordinary dry, non-spectator local player with a usable chest Elytra, at ladder/vine feet, with fall-flying flag 7 already true and identical incoming movement state, **there is no 1.14.4 → 1.15 difference in active-flight validation or in the active Elytra travel branch through its first `move`**. `updateFallFlying` uses the same existing-flag, airborne, non-passenger and usable-chest-Elytra checks, and only rewrites the shared flag on the logical server (`1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java:2235-2253`; `1.15/mojmap/net/minecraft/world/entity/LivingEntity.java:2299-2317`). Both `aiStep` bodies call this updater immediately before `travel` (`1.14.4/.../LivingEntity.java:2221-2223`; `1.15/.../LivingEntity.java:2285-2287`).

The active travel branch sets `fallDistance = 1.0F` iff incoming `deltaMovement.y > -0.5` (strict `double` comparison), before glide-vector math and the first `move`; when `y <= -0.5`, that branch does not write fall distance. The operation sequence from branch selection through `move` is the same in both sources (`1.14.4/.../LivingEntity.java:1793-1822`; `1.15/.../LivingEntity.java:1842-1871`). The same feet predicate returns true for ladder/vine for non-spectators (`1.14.4/.../LivingEntity.java:1228-1238`; `1.15/.../LivingEntity.java:1226-1236`), but the active branch bypasses ordinary `handleOnClimbable` in both versions. This is a conditional operation-level equivalence, not a claim of whole-tick or runtime parity.

## Kept separate from startup and friction

The earlier **REVISE** applies to startup, outside this already-active scope: 1.14.4 local and server request gates require strict descent `y < 0.0` (`1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java:707-712`; `.../ServerGamePacketListenerImpl.java:1122-1127`). In 1.15 those gates omit descent (`1.15/mojmap/net/minecraft/client/player/LocalPlayer.java:715-719`; `.../ServerGamePacketListenerImpl.java:1137-1140`; `.../world/entity/player/Player.java:1586-1595`). The 1.15 local method calls `tryToStartFallFlying()`, which writes flag 7 through `startFallFlying()` before `LocalPlayer.aiStep` calls `super.aiStep()` and enters `updateFallFlying`/`travel` (`1.15/.../Player.java:1586-1600`; `.../LocalPlayer.java:715-719,778`; `.../LivingEntity.java:2285-2287`). That same-tick local-state change does not apply when flag 7 is pre-existing and is not evidence of a difference in the active branch above.

The friction-position delta belongs to the ordinary dry branch only: 1.14.4 reads at `minY - 1.0` (`1.14.4/.../LivingEntity.java:1836-1842`), while 1.15 calls the helper using `minY - 0.5000001` (`1.15/.../LivingEntity.java:1885-1891`; `.../Entity.java:600-602`). The active Elytra branch does not perform that lookup. Which blocks those two ordinary-branch positions select for a concrete ladder/vine setup remains an external geometry question and does not qualify or weaken this active-branch disposition.

**Attestation:** I independently compared the cited local exact-version source slices for this bounded path. No intermediate versions, support/collision geometry, whole-pair coverage, or runtime behavior are certified here.
