# Independent product review: Shulker sneak-edge integration

**Verdict: REQUEST CHANGES**
**Reviewed product revision:** `c70c63aeee043bdeb08cf688743480b3000309d6` (`fix/shulker-edge-product-20261030`)
**Accepted source witness:** corrected Shulker finding SHA-256 `078650b8e08933ed95a40f760416cb7d52f1b99a2b42f8e754f559c523df0086`; alias audit `piston-shulker-alias-audit33` accepts 1.9–1.10.2 applicability.
**Review scope:** composed sneak-edge hook, resolver/discovery, mixin call and descriptor, player/client scope, native fallback. Read-only; no build, tests, game launch, decompile, or product edit.

## Actionable finding

**[P2] The pre-Shulker mover variant does not keep modern Shulker movement native for profiles before 1.9.**

`src/client/java/me/wolfii/legacyparkourcompat/change/v1_8_9/PreShulkerSneakEdgeMover.java` leaves `MoverType.SHULKER` unchanged. However, it only rewrites the mover argument supplied to `maybeBackOffFromEdge`; it does not bypass the independently resolved `SneakEdgeHook`. For a 1.8.9 profile, `ChangeResolver` selects the 1.10.1 `SneakEdge` implementation because no earlier `SneakEdgeHook` change exists. That implementation ignores its `moverType` argument and, for any grounded sneaking player, returns the result of `SneakEdgeBackoff.apply` (`change/v1_10_1/SneakEdge.java`, `maybeBackOffFromEdge`). `PlayerMixin.legacyparkourcompat$maybeBackOffFromEdge` intercepts the current method and uses that resolved hook for every player call, including a current-version `SHULKER` push.

Consequently, when a modern Shulker pushes a grounded sneaking player under a pre-1.9 profile, the “unchanged” `SHULKER` argument still reaches the historical unconditional hook and its edge probe. The 26.2 `Player.maybeBackOffFromEdge` predicate itself admits only `SELF` and `PLAYER`, so vanilla returns the SHULKER movement unchanged; the mixin replaces that native response. This violates the profile boundary that features introduced after the selected profile retain modern behavior. The issue also affects earlier profiles that resolve this same hook, not just 1.8.9.

**Suggested correction:** represent the pre-1.9 behavior at the edge-operation hook boundary as well as at the mover-argument hook, so `SHULKER` preserves native result for profiles before 1.9 while 1.9–1.10.2 still apply their accepted untyped-call behavior. Keep the accepted piston argument policy intact.

## Checks that passed

- The product consolidates the behavior into the existing `SneakEdgeMoverHook` and `EntityMixin`; there is no duplicate Shulker entity hook or separate player mixin. The mover rewrite runs only for `Entity instanceof Player`. Both files are in the client source set, so the change adds no server execution path.
- `EntityMixin` changes only argument 1 at the `maybeBackOffFromEdge` invocation. It does not replace the caller's mover type for `Entity.move`, piston limiting, collision, or subsequent movement. The 26.2 `minecraft-client.jar` bytecode (`javap -c -p`) confirms `Entity.move(MoverType, Vec3)` invokes `maybeBackOffFromEdge(Vec3, MoverType)`; the exact invocation descriptor is `(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/MoverType;)Lnet/minecraft/world/phys/Vec3;`. The injection descriptor and `index = 1` match that call.
- Mapping `PISTON`/`SHULKER` to `SELF` is equivalent to `PLAYER` for the 1.11-era edge predicate, which accepts either `SELF` or `PLAYER`; the historical 1.10.1 edge implementation does not inspect this argument. Piston limiting continues to use the actual `PISTON` mover.
- For 1.9–1.10.2, the v1.10.1 mover policy maps `SHULKER` to `SELF`, allowing the accepted pre-1.11 edge response. For 1.11 and later, v1.10.1 is too old for the resolver; no mover rewrite applies, so the modern `SHULKER` value reaches the native typed gate. `CURRENT` resolves to no historical changes.
- `MovementChangeDiscovery` scans version folders and registers each implemented `@MechanicType` interface; both new/modified change classes have public no-argument constructors and `@MovementChange` annotations. No manual registration is needed.

## Evidence limits

The 1.10.2-to-1.11 accepted witness and eight-release alias audit establish the stated source contract and applicability, not runtime parity. Product review is static. The descriptor check was against the cached 26.2 client bytecode, not a runtime mixin application. No claim of tick-level parity is made.
