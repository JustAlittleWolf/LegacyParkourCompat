# Independent frozen audit: 1.19.1 → 1.19.2 glide fall-distance / climbable gate

## Verdict

**Accept the report’s bounded claim.** I independently checked the canonical local Mojmap trees. The compared `LivingEntity.java`, `Entity.java`, and `Player.java` files are byte-identical across these releases. For an already-active fall-flying player whose ordinary non-water/non-lava travel reaches `LivingEntity.travel`, the dry fall-flying branch writes `fallDistance = 1.0F` exactly when the captured vertical delta satisfies the strict test `y > -0.5`; equality does not write. It does so before the Elytra velocity damping and `move(MoverType.SELF, getDeltaMovement())`. No 1.19.1→1.19.2 change is shown in the claimed slice.

The feet-block climbable witness does not select or suppress that dry fall-flying branch. `onClimbable()` recognizes `BlockTags.CLIMBABLE` at the feet position (or a usable trapdoor), but the Elytra branch does not call it. In `LivingEntity.travel`, the direct climbable response occurs in the water branch after its move; it is not a dry Elytra gate. Player climb/fall-flying movement-stat selection is later bookkeeping and does not alter this travel branch.

## Bounded caveats

- `Player.travel` at `Player.java:1464–1493` calls `super.travel` in the ordinary case; creative flight (`abilities.flying && !isPassenger`) takes its own branch and resets fall distance after `super.travel`. The audited ordinary Elytra state must therefore mean non-creative-flying travel. This routes to the `LivingEntity` method but does not prove how/when the active flag was set.
- The claim is conditional on already-active fall flying and on entering the dry branch. It does not audit the caller/input path or establish a particular tick of Elytra activation. `Player.tryToStartFallFlying()` (`Player.java:1602–1612`) shows a chest-slot `Items.ELYTRA` plus `ElytraItem.isFlyEnabled` gate for starting, but this audit does not infer upstream timing from that predicate.
- The pre-move `1.0F` write is not a guaranteed final tick value. `Entity.move` can reset fall distance before position application when nonzero and squared post-collision displacement is at least `1.0` and the fall-resetting clip hits (`Entity.java:547–579`); later `checkFallDamage` is called (`594–597`) and landing/falling updates are in `991–1002`. No final post-tick value or tick limit follows from this bounded review.

## Evidence checked

Source roots: `decompiled_minecraft/1.19.1/mojmap/` and `decompiled_minecraft/1.19.2/mojmap/`. Both ready markers identify the exact release and `mojmap`; both provenance sidecars identify the Mojmap namespace and exact release metadata. The 1.19.1 ready marker declares source manifest SHA-256 `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` and client SHA-256 `146d4018cfed50c5e77483940653091f186d2eea9ad42fc40939f5c72d6b7f52`. The 1.19.2 ready marker declares source manifest SHA-256 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` and client SHA-256 `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`.

Exact source paths and ranges, applicable to each release because the relevant files have identical SHA-256 bytes:

- `net/minecraft/world/entity/LivingEntity.java:2042–2178`: travel entry gate at `2043`; water/lava branch selection at `2052–2106`; dry fall-flying branch `2107–2178`; strict comparison and overwrite `2109–2110`; damping then move `2135–2136`.
- `net/minecraft/world/entity/LivingEntity.java:1462–1477`: `onClimbable()` and feet-block tag/trapdoor witnesses.
- `net/minecraft/world/entity/player/Player.java:1464–1493`: player travel dispatch; `1602–1612`: start-fall-flying predicate (scope caveat above); `1534–1554`: climbable-first then fall-flying movement-stat branches.
- `net/minecraft/world/entity/Entity.java:547–579`, `594–597`, and `991–1002`: bound move and subsequent fall-distance handling.

Independent SHA-256 comparison for the three source files (each side identical):

- `LivingEntity.java`: `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`
- `Entity.java`: `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`
- `Player.java`: `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1`

## Immutable inputs and output

- Reviewed report SHA-256: `fe17c39e0c8847e3b872645e81fa79afa0505dc0c99346d1b0d13e8b1047f1f8`
- Verdict output: sibling `c-glide-climb-1191-1192-alias-gate-audit/verdict.md` (SHA-256 recorded after write by the auditor).

Verdict-body SHA-256 (exact bytes above this identity line): `a72e78c89c7b5d3d7470eef2d656597a4219b885d098da18d2ceeddc5f2107736`.


