# Independent source audit — ordinary ground/air travel r2

Verdict: ACCEPT for the bounded endpoint source comparison at candidate SHA-256 `616E1B9D6382B99BD92AEF9908BF7B905DEDD1086DD609F5D477C93F573F83D1`.

## Identity and hashes

The candidate's corrected 1.21.11 `Player.java` digest is accurate: canonical bytes at `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java` and its `mojmap.sources.sha256` entry both hash to `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`. I verified the other seven cited Java files against both their on-disk bytes and endpoint source ledgers; all match. Both ready markers, source manifests, artifact manifests, and provenance JSON digests match the report. The two `versionId` values and `mojmap` mapping IDs match the requested endpoints.

## Source trace

- In 1.20.6 `Player.travel(Vec3)` (`Player.java:1485–1492`), the flying and non-passenger branch saves vertical velocity, calls `super.travel`, restores `y` to saved `y * 0.6`, then resets fall distance and clears shared flag 7.
- In 1.21.11 `Player.travel(Vec3)` (`Player.java:1375–1381`), the same branch saves vertical velocity, calls `super.travel`, restores `y` to saved `y * 0.6`, and has no post-travel reset or flag clear. Passenger handling remains outside the flight branch.
- In 1.21.11 `Player.aiStep` (`Player.java:459–463`), flying and non-passenger fall distance is reset before `super.aiStep`. There is no analogous pre-super reset at the cited 1.20.6 `Player.aiStep` (`Player.java:515–533`). `LocalPlayer.aiStep` reaches that superclass operation at both endpoints (`LocalPlayer.java:807` and `875`).
- The local-player travel reachability claim is supported: 1.20.6 `LocalPlayer` returns true for `isLocalPlayer` and `isEffectiveAi`; 1.21.11 `Player` implements `canSimulateMovement`/`isEffectiveAi` as true for the local player. Both `LivingEntity.aiStep` paths reach `travel` when not using the controlling-passenger path. In 1.21.11 the local-player simulation/effective-AI gates admit this path.
- For an airborne, non-water, non-passenger flying tick with negative displacement and no landing, both `Entity.move` paths reach `checkFallDamage`. Old `Entity.checkFallDamage` accumulates negative displacement; current does so when not in water. Thus, after movement, 1.20.6's `Player.travel` reset leaves zero fall distance, while 1.21.11's pre-travel reset is followed by accumulation. The post-travel `y * 0.6` remains in both methods.
- `FLAG_FALL_FLYING` is bit 7 in both `Entity` sources, confirming the old code clears the fall-flying flag. The report does not claim an ordinary movement consequence for that flag, and no such consequence is demonstrated by this bounded slice.

## Scope note

The candidate does not claim a position or velocity trajectory difference and does not hand off implementation. It correctly leaves exact introduction boundary, other movement helpers, and the downstream significance of retained `fallDistance` open. One concrete in-scope dependency should be retained for follow-up: `Player.maybeBackOffFromEdge` calls `isAboveGround`, which reads `fallDistance`, and `Entity.move` invokes that hook before collision resolution for eligible `SELF`/`PLAYER` moves. A flight-off transition followed by shift-held movement may therefore make retained fall distance affect later edge-backoff; this audit has not established a reachable trajectory witness or the exact comparison for that later move. Keep this as an open gate, not as an accepted trajectory claim.

Pair coverage is partial. This verdict accepts only the immutable bounded endpoint source report above; it establishes neither exact intermediate boundary, complete tick coverage, implementation readiness, nor runtime parity.
