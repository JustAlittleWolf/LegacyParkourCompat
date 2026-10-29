# Independent source audit: inherited tick method ledger (1.18.2 → 1.19)

## Verdict

The bounded ledger is supported by the canonical local Mojmap sources. The ledger SHA-256 is `e6ba2507ab02adf50d1b3e8f8132fcc52a9d45d01b2c93a5dd54cab5fc806033`; the selected method table SHA-256 is `506c1fce1c3cd3a9e1e069ae2b431e2c0b9413507014b457f8b963a827615f65`. Both match the supplied identities.

The table has 82 unique selected signatures: 65 paired body-identical methods, 13 paired changed methods, and four 1.19-only methods. I independently re-read each method body from the canonical source at its recorded declaration line, checked the signature and recalculated SHA-256 over the body. All 82 rows matched; there were no stale hashes, wrong source locations, or duplicate identities. This validates the table's selected inventory, not completeness of the whole pair's tick/dependency graph.

## Source identity

Canonical files were read from `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.18.2,1.19}\mojmap`.

- For both versions, the ready-marker SHA-256, provenance SHA-256, and `mojmap.sources.sha256` SHA-256 match the values in the ledger.
- The six listed `Entity.java`, `LivingEntity.java`, and `Player.java` file hashes match their entries in the respective source manifests. No source or mapping identity mismatch was found. The 1.19 ready marker says version `1.19`, mapping `mojmap`; its provenance records the exact-version decompile run and matching source-manifest identity.

## Changed and added bodies

The source-verified 13 changed signatures are:

- `Entity`: `checkFallDamage`, `getOnPos()`, `isCrouching`, `move`, `setPosRaw`.
- `LivingEntity`: `aiStep`, `isVisuallySwimming`, `onChangedBlock`, `tryAddFrost`, `tryAddSoulSpeed`, `updateFallFlying`.
- `Player`: `maybeBackOffFromEdge`, `tick`.

The four 1.19-only signatures are `Entity.getOnPos(float)`, `Entity.getOnPosLegacy()`, `Entity.getBlockStateOnLegacy()`, and `Entity.hasPose(Pose)`. The 1.18.2-only count is zero. These classifications and all stored hashes match the source. The ledger's dispositions are appropriately limited: for example, the `Player.tick` tracker addition is beneath the server-side gate, the new `hasPose` calls are aliases of `getPose() == pose`, and `Entity.move` has other changed hunks that the report assigns to already separate callback/support-query review.

## `Player.maybeBackOffFromEdge` positive-Y gate

In 1.18.2 `Player.java:1032–1078`, the override gates the 0.05-step x/z reduction on not flying, mover type `SELF` or `PLAYER`, `isStayingOnGroundSurface()`, and `isAboveGround()`. There is no Y-sign condition. In 1.19 `Player.java:1060–1107`, the same gate adds `request.y <= 0.0` before the mover-type and player-state checks. If the prior gates hold and the request has positive Y, 1.19 returns the original horizontal request from this method; 1.18.2 runs the horizontal reduction loops and preserves the original Y component in the returned vector. The recorded method hashes are correct.

The positive-Y case is source-reachable on the ordinary local-player path, not merely an arbitrary method argument:

1. `LocalPlayer.tick()` calls `super.tick()` (`LocalPlayer.java:198–200`). Through `Player.tick()` / `LivingEntity.tick()`, the virtual `aiStep()` reaches `LocalPlayer.aiStep()`; its jump input is assigned to `this.jumping` (`LocalPlayer.java:694`) before `super.aiStep()` (`:887`).
2. `LocalPlayer.isShiftKeyDown()` reads `input.shiftKeyDown` (`LocalPlayer.java:675–677`). `Player.isStayingOnGroundSurface()` calls `isShiftKeyDown()` (`Player.java:311–312`), so jump and shift can both be held on the local client.
3. For an on-ground jump, `LivingEntity.aiStep()` calls `jumpFromGround()` when the jump gate is met (`LivingEntity.java:2558–2567`). That writes positive vertical velocity (`LivingEntity.java:2014–2021`, jump power `0.42` before any boost).
4. The same AI step reaches virtual `travel(...)` (`LivingEntity.java:2585`). `Player.travel()` delegates to `LivingEntity.travel()` when not flying (`Player.java:1464–1474`); the ordinary movement path calls `move(MoverType.SELF, this.getDeltaMovement())` (`LivingEntity.java:2197`). `Entity.move()` invokes virtual `maybeBackOffFromEdge(request, moverType)` before `collide(request)` (`Entity.java:547–570`).

Thus a non-flying local player can request positive Y with `SELF` while holding shift and starting the tick on ground. `isAboveGround()` is true immediately in that on-ground case; on other cases it consults `Level.noCollision` (`Player.java:1114–1117`), so its world/shape provider semantics remain a dependency. The ledger does not claim a guaranteed final trajectory: collision resolution follows the adjusted request, and this audit did not establish the resulting displacement for any particular ledge geometry.

This is a distinct override-level change. Its invocation from the already changed `Entity.move()` body does not merge it with the callback/game-event hunks in that body. The remaining `noCollision` shape/provider dependency is correctly left open. I found no basis in this bounded source audit to widen the candidate into a callback/provider finding or to claim those separate scopes closed.

## Coverage limits

No missing method was found within the ledger's stated 82-signature inherited `Entity` / `LivingEntity` / `Player` selection. The ledger remains a bounded comparison, not a pair freeze. Local-player/input methods, collision solving and providers, callback behavior, and the listed liquid, modifier, pose, packet, and support-query dependencies remain separate coverage. This audit adds no pair-freeze claim and does not verify those closures.
