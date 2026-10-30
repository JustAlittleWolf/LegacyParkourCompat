# Audit supplement: old-side position-correction applicability

This supplement adds one source-only check to the sealed [pair audit](review.md). It does not modify `review.md` or its hash sidecar. It answers whether the constrained-pose external-position setup established in the pose/query closure can also place a 1.13.2 client into the analogous old-side precondition.

## Immutable bindings

- `pair-audit/review.md`: SHA-256 `9cb7a6b3769e688f61ba1fd61ee61aa601945cf363a0b36e7545de373a009530`.
- `pair-audit/review.sha256`: SHA-256 `83daac58ed834c29f2721a24b4b66611d82bd7b19cb70b0f3f3c2b4b70f249fc`.
- `pose-query-closure/closure-report.md`: SHA-256 `42d1ed3e7b94f0212a99b69b03edbde4c73aac98609a3aaadc3c0f7b5ad69711`.
- `pose-query-closure/correction-supplement.md`: SHA-256 `8ba82460e616a3184df7110fc57c61f48de63528fd5494779f533f1887a5043f`.

## Old-side client packet path

The 1.13.2 `ClientPlayNetworkHandler.java` SHA-256 is `4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11`, matching its `ornithe-feather.sources.sha256` entry. `handlePlayerMove(PlayerMoveS2CPacket)` at lines 621–670 applies relative position fields to the current player coordinates and leaves absolute fields as supplied (`:629–645`), then calls `playerEntity.updatePositionAndAngles(d, e, f, g, h)` directly at `:655`, accepts the teleport ID, and sends the resulting client position. No collision-fit predicate occurs in this handler path.

The 1.13.2 `Entity.java` SHA-256 is `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`, also matching its source manifest. `updatePositionAndAngles` clamps x/z to the world coordinate limit, writes x/y/z and rotation, then calls `setPosition` (`Entity.java:1096–1118`); it performs no `hasNoCollisions` check. The directly constructed box can therefore overlap the ceiling before the next pose update. At feet y=1 in the closure’s full-block-roof example, the old 1.13.2 standing and requested sneaking boxes both intersect the roof; the old pose updater retains the current dimensions when that requested size fails its direct `hasNoCollisions` check (`PlayerEntity.java:334–360`).

## Verdict

**Old-side external correction applicability is supported for the received player-position packet path.** A server-supplied absolute `PlayerMoveS2CPacket` can establish the analogous 1.13.2 precondition without a collision-fit gate, just as the 1.14.4 correction can. This closes only the old-side packet-application question; it does not prove that an operator command is authorized/in-scope for a particular movement finding, close all external response producers, or change the pair-level no-freeze verdict. The constrained fallback remains a source-supported difference reachable only through an external position setup in the stated static corridor evidence, not through a static ordinary-movement entry.
