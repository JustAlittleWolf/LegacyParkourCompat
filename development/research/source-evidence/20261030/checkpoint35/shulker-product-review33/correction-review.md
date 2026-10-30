# Corrective supplement: Shulker sneak-edge integration

**Verdict: ACCEPT the reviewed correction**
**Reviewed product revision:** `6762ef365086c4c460b628589dc9d58155f6b3c4` (`fix/shulker-edge-product-20261030`)
**Prior review:** `review.md` at product revision `c70c63aeee043bdeb08cf688743480b3000309d6` requested changes because the resolved pre-1.9 sneak-edge hook applied to an untranslated `SHULKER` mover.

## Correction verified

The composed path now preserves the intended boundary:

- For profiles before 1.9, `PreShulkerSneakEdgeMover` leaves `SHULKER` unchanged and still maps `PISTON` to `SELF`. The closest resolved `SneakEdgeHook` is `v1_10_1/SneakEdge`; its new early check returns `Optional.empty()` for mover types other than `SELF` and `PLAYER`. `PlayerMixin` only replaces the native result when the resolved hook returns a value, so an untranslated modern `SHULKER` call falls through to vanilla. In 26.2, `Player.maybeBackOffFromEdge` itself restricts the edge guard to `SELF` or `PLAYER`, preserving native handling for `SHULKER` and `SHULKER_BOX`.
- For 1.9–1.10.2, the closest mover change is `v1_10_1/SneakEdgeMover`, which translates the historically untyped piston and Shulker calls to `SELF` only for the edge-operation argument. The new check therefore admits those old-call equivalents and the original grounded+sneaking backoff runs. The actual `Entity.move` mover type remains unchanged.
- For 1.11 and later, the v1.10.1 hook is older than the selected profile and is not resolved. The native typed `SELF`/`PLAYER` predicate remains in force.

The check is in the existing independently resolved sneak-edge operation. No duplicate Shulker hook or mixin was added. The path remains player-only and client-only. Automatic discovery still sees the annotated changes and their `SneakEdgeMoverHook` / `SneakEdgeHook` interfaces; no registration edit is needed.

The corrected eligibility check resolves the pre-1.9 native-fallback bug from the prior review while preserving the accepted 1.9–1.10.2 source behavior. I found no remaining actionable product issue in this bounded review.

## Limits

This is static product review only. No build, tests, game/runtime launch, decompilation, or movement parity verification was performed. Source acceptance and this review do not establish tick-level runtime parity; root remains the build owner.
