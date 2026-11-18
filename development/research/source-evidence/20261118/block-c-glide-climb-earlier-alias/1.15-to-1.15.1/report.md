# Elytra glide versus climbable handling: 1.15 → 1.15.1

## Result

No source difference was found in the assigned operation across this adjacent pair. For an ordinary dry local player already fall-flying with an enabled chest-slot Elytra while their feet touch a ladder or vine, both releases enter the same local travel gate, refresh the glide flag at the same point, choose the glide branch before non-glide climbable handling, and execute the same strict fall-distance write immediately before movement. This is a bounded no-difference result, not full-pair coverage or a claim about other patch boundaries.

## Exact source identity and integrity

Both inputs are the literal adjacent releases, not an alias: `decompiled_minecraft/1.15/mojmap/` and `decompiled_minecraft/1.15.1/mojmap/`. Their adjacent `mojmap.provenance.json` files share run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`, declare `requestedVersionId`/`versionId` respectively `1.15` and `1.15.1`, and declare namespace `mojmap`.

The provenance binds distinct exact inputs: 1.15 client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`, official client mappings SHA-256 `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a`; 1.15.1 client SHA-256 `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358`, official client mappings SHA-256 `f46186d0ed9d17e073e16ae2c98675dbbc70b4a29495700837aa5de9c1b42c34`.

Each adjacent `mojmap.sources.sha256` hash matches the hash declared by that version's provenance and was checked against the target source files. The four movement files are byte-identical across the pair; each actual file hash also matches both version manifests:

| Relative source | SHA-256 in both trees |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |

Manifest SHA-256: 1.15 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`; 1.15.1 `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`.

## Compared operation and bounds

- `LocalPlayer.aiStep()` (`LocalPlayer.java:625–778`): the locally controlled player override returns `true` from `isEffectiveAi()` (`:490`). The ordinary jump-to-glide condition (`:713–718`) invokes `tryToStartFallFlying`; `Player.tryToStartFallFlying()` (`Player.java:1586–1595`) checks airborne/not-already-gliding/not-in-water, then `startFallFlying()` writes shared flag 7 true (`:1598–1600`). `wasFallFlying` is captured at `LocalPlayer.java:722`; `super.aiStep()` follows at `:778`.
- `LivingEntity.aiStep()` (`LivingEntity.java:2207–2296`): after the local player's superclass call, `updateFallFlying()` runs before `travel()` (`:2285` then the travel call). Its flag validation checks on-ground/passenger/Elytra state; the shared flag is rewritten only under `!level.isClientSide` (`:2299–2317`). Thus the local client does not clear an already-active flag at this boundary. No ladder/vine predicate participates in that update.
- `LivingEntity.travel(Vec3)` (`:1831–1987`): the gate is `isEffectiveAi() || isControlledByLocalInstance()` (`:1832`). Once the dry/flight-allowed path reaches `isFallFlying()` (`:1842`), the active-glide branch precedes the `else` branch containing `handleOnClimbable()` (`:1890`) and ladder handling (`:1892` onward). The glide branch tests the original vertical velocity with strict `vec36.y > -0.5` and writes `fallDistance = 1.0F` (`:1844–1845`) before glide math and `move(MoverType.SELF, ...)` (`:1871`). The later `Entity.move()` path calls `checkFallDamage` (`Entity.java:484`), so collision/vertical travel can then update or reset fall distance. The glide branch itself does not run the ladder reset in `handleOnClimbable()` (`LivingEntity.java:1989–2004`).

These enclosing gates and operation order are identical in both exact source trees. The difference inventory is limited to local Elytra activation/flag order, the local `travel` gate, active-glide versus climbable branch ordering, the strict threshold and pre-move write, and the immediate `move`/fall-distance update boundary. No trajectory outcome was simulated.
