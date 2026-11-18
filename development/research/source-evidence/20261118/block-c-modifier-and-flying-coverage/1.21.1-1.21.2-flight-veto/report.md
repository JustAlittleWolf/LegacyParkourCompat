# COMPLETED — 1.21.1→1.21.2 flight veto and climbability range

## Evidence identity

- Mapping family: Mojmap.
- 1.21.2 `world/entity/player/Player.java` SHA-256: `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` (matches the assignment pin). Its nested `.decompile-provenance.json` exists and records the exact file hash in `sources`; the parent campaign-ready marker was not used as an identity gate.
- 1.21.1 canonical source files were read from the exact local `1.21.1/mojmap` tree. Relevant hashes: `Player.java` `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; `LocalPlayer.java` `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; `LivingEntity.java` `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`.
- 1.21.2 relevant `LocalPlayer.java` SHA-256: `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`.

## Finding: creative flight vetoes `canGlide`

1.21.1, Mojmap: `world/entity/player/Player.java`, `tryToStartFallFlying`, lines 1529–1537, directly checks `!onGround()`, `!isFallFlying()`, `!isInWater()`, and `!hasEffect(LEVITATION)`, followed by an enabled Elytra check. Its local caller `client/player/LocalPlayer.java`, `aiStep`, lines 741–745, gates the attempt on jump input, no just-triggered ability toggle, non-sleeping state, `!abilities.flying`, `!isPassenger()`, and `!onClimbable()`.

1.21.2, Mojmap: `world/entity/player/Player.java`, lines 1470–1473 adds `canGlide()` override returning `!abilities.flying && super.canGlide()`. `tryToStartFallFlying` at lines 1525–1530 now checks `!isFallFlying() && canGlide() && !isInWater()`. `LivingEntity.canGlide` at lines 2836–2844 checks qualifying equipped glide items. The new player override therefore makes abilities.flying an additional veto in the direct eligibility operation; it subsumes the former grounded and levitation checks through the inherited `canGlide` predicate and changes their ordering/implementation. LocalPlayer's attempt remains after ability-toggle handling and before `wasFallFlying` is updated; in 1.21.2 its caller no longer repeats the flying, passenger, or Elytra checks because these are handled by `canGlide`/the eligibility method. It still gates on jump, toggle/sleep state, and `!onClimbable()`.

Movement consequence: with valid glide equipment and otherwise eligible state, a player with abilities.flying cannot begin fall-flying through `tryToStartFallFlying`; the result is a direct movement-state eligibility change. This report does not claim differences to glide activation/trigonometry or the subsequent flight physics.

## Climbability read and bounded negative

`Player` has no `onClimbable` override in either release. Both inherit `LivingEntity.onClimbable`; it checks spectator, the feet block's `BlockTags.CLIMBABLE`, and the existing ladder/trapdoor special case. The relevant predicate bodies are at 1.21.1 `LivingEntity.java:1517–1537` and 1.21.2 `LivingEntity.java:1561–1586`. The ordinary `Blocks.LADDER` path remains a positive finite witness: standing at a ladder block position whose state is in `BlockTags.CLIMBABLE` returns true (unless spectator). The special fallback checks a trapdoor above and a ladder below with matching facing. No abilities.flying veto is added to this inherited predicate in this pair.

The inherited consumers are present on both sides: `LivingEntity.handleOnClimbable` clamps climb motion and resets fall distance; movement applies the 0.2 upward collision response on climbable contact; `Player.aiStep` reads `onClimbable` as a fall-flying initiation veto. Their mere call presence is not an additional finding here; the predicate body and ordinary ladder witness are unchanged in this bounded check. No claim is made about block-state producers or ladder/vine geometry.

## Travel boundary

`Player.travel` remains ordered as passenger delegation first, swimming adjustment second, abilities-flying branch third, and superclass travel fallback last. In the flight branch it preserves incoming vertical velocity, delegates to superclass travel, then restores vertical motion scaled by `0.6`. The paired body is 1.21.1 lines 1456–1477 and 1.21.2 lines 1445–1468. This consumer is unchanged and is reported only to bound the new eligibility veto; no historical velocity-stop or travel delta is claimed.

## Scope limits

Examined only the assigned flying-eligibility predicate/caller, inherited climbability predicate and read sites, immediate player travel consumer/order, and ordinary ladder witness. No full-pair coverage or independent audit is claimed. No code, tests, game launch, or source generation performed.