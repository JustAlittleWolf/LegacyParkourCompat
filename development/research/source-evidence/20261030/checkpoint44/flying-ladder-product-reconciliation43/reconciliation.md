# Flying-ladder product reconciliation

## Identity and disposition

- Accepted source candidate: `.native-source/20261030/1152-1165-pool33/flying-ladder-independent-audit/audit.md`
- Candidate SHA-256: `1C256677F07F01444AD6F2EF006D613953A182C68F37F77B7E1545496CBDA627`.
- Product examined: task root and `.native-integrations/movement-query-product39` (`fix/movement-query-product-20261030`, HEAD `471bc992`). The relevant hooks and mixins agree in both snapshots.
- Disposition: **not already covered; bounded missing capability; no code change.** The accepted 1.15.2/1.16.5 pair does not close the exact version boundary, so do not implement a release range yet.

## Existing coverage

The product has `PlayerClimbabilityFlyingGateHook`, dispatched from `PlayerMixin` around the flying-field read in `Player.onClimbable()Z`. Its operation is the early flying veto that determines whether the inherited climbability query runs. `NativePlayerClimbabilityFlyingGate` and `InheritedPlayerClimbability` register separate outcomes for that operation. It does not affect `LocalPlayer.isSuppressingSlidingDownLadder()`.

Other climbable hooks concern trapdoor classification, fall-flying, climbing water impulse, and climb impulse. None controls whether a flying local player suppresses downward velocity while on a climbable block. `LivingEntityClimbingMixin` wraps the 26.2 trapdoor predicate only.

## Source and target seam

The accepted audit establishes the conditional pair difference: 1.15.2 `LocalPlayer` inherits the shift-only `LivingEntity.isSuppressingSlidingDownLadder()`; 1.16.5 overrides it with `!abilities.flying && super.isSuppressingSlidingDownLadder()`. Both `LivingEntity.handleOnClimbable` implementations clamp Y to at least `-0.15` and zero negative Y when the suppression predicate passes, the feet state is not scaffolding, and the entity is a Player.

The read-only 26.2 Mojmap source and merged-deobfuscated target bytecode confirm the current native path matches the later form:

- `LocalPlayer.isSuppressingSlidingDownLadder()Z` returns `!getAbilities().flying && super.isSuppressingSlidingDownLadder()` (`decompiled_minecraft/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java:383–384`).
- `LivingEntity.handleOnClimbable(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;` invokes `LivingEntity.isSuppressingSlidingDownLadder()Z` only after the negative-Y and non-scaffolding checks, before checking `instanceof Player` and zeroing Y. The bytecode preserves the same clamp/branch ordering as the audited pair.
- `LivingEntity.isSuppressingSlidingDownLadder()Z` natively returns `isShiftKeyDown()`.

The explicit call in `handleOnClimbable` is a viable shared movement seam; alternatively the client-only `LocalPlayer.isSuppressingSlidingDownLadder()Z` body is a narrower source-aligned seam. The 26.2 local-player method has a direct flying gate followed by the superclass suppression predicate.

## Bounded integration suggestion

Once the exact 1.16 applicability boundary is closed, add a separate hook for the local-player ladder-slide suppression operation. Keep it independent of `PlayerClimbabilityFlyingGateHook`; the two predicates are called by different methods and have different movement effects. A client-only dispatch at `LocalPlayer.isSuppressingSlidingDownLadder()Z` can restore the inherited shift-only result for the source-confirmed older profile range while preserving native behavior for later profiles. `LocalPlayerMixin` is the natural host; a `ModifyExpressionValue` on the flying gate or a return-level hook must preserve the superclass shift predicate and local-player scope. Do not substitute the `Player.onClimbable` hook.

Use the exact boundary release as the change annotation/version cut and follow the resolver's smallest `emulates >= selected` rule. The pair alone only bounds the change to after 1.15.2 and by 1.16.5. The already-existing 1.21 climbability-gate changes do not close this earlier ladder-slide predicate boundary.

No edits, source generation, builds, tests, launches, or backlog writes were made.
